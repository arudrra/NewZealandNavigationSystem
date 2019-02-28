import java.awt.geom.Point2D;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class Graph {
	private City destination;
	private City origin;
	private ArrayList<City> route;
	private ArrayList<Attraction> attractions;
	private Hashtable<String, City> cities;
	private double cost;
	private boolean sortByDistance;
	final int degreesToKmConversion = 111;

	public Graph() {
		try {
			cities = read("Cities.xml");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		route = new ArrayList<City>();
		attractions = new ArrayList<Attraction>();
	}


	public void findShortestPath(String orig, String dest){
		origin = cities.get(orig);
		destination = cities.get(dest);

		Comparator<City> comparator = new CostComparator();
		PriorityQueue<City> queue = new PriorityQueue<City>(11, comparator);

		queue.add(origin);
		City currentCity = origin;
		while(!currentCity.name.equals(destination.name) && !queue.isEmpty()) {
			currentCity = queue.poll();
			for(Link link : currentCity.links) {
				City city;
				if(link.city1.name.equals(currentCity.name)) city = link.city2;
				else city = link.city1;
				if(city.distanceToReach == 0 || city.distanceToReach > currentCity.distanceToReach + link.cost(sortByDistance)) {
					city.distanceToReach = currentCity.distanceToReach + link.cost(sortByDistance);
					if(sortByDistance) {
						city.estimatedCost = city.distanceToReach + (city.coordinates.distance(destination.coordinates) * degreesToKmConversion);
					} else {
						city.estimatedCost = city.distanceToReach + (city.coordinates.distance(destination.coordinates) * degreesToKmConversion / 79);
					}
					queue.offer(city);
				}
			}
		}

		origin.distanceToReach = 0;
		route.add(destination);
		while(route.get(route.size() - 1).name != origin.name) {
			currentCity = route.get(route.size() - 1);
			for(Attraction attraction : currentCity.attractions) attractions.add(attraction);
			for(Link link : currentCity.links) {
				if(link.city1.name.equals(currentCity.name)) {
					if(link.cost(sortByDistance) + link.city2.distanceToReach == currentCity.distanceToReach) {
						route.add(link.city2);
						for(Attraction attraction : link.attractions) attractions.add(attraction);
						break;
					}
				} else {
					if(link.cost(sortByDistance) + link.city1.distanceToReach == currentCity.distanceToReach) {
						route.add(link.city1);
						for(Attraction attraction : link.attractions) attractions.add(attraction);
						break;
					}
				}
			}
		}
		cost = destination.distanceToReach;
	}

	public class CostComparator implements Comparator<City> {
		public int compare(City city1, City city2) {
			if(city1.estimatedCost == city2.estimatedCost) return 0;
			return (city1.estimatedCost < city2.estimatedCost) ? -1 : 1;
		}

	}

	public static void write(Hashtable<String, City> city, String filename) throws Exception{
		XMLEncoder encoder =
				new XMLEncoder(
						new BufferedOutputStream(
								new FileOutputStream(filename)));
		encoder.writeObject(city);
		encoder.close();
	}

	public static Hashtable<String, City> read(String filename) throws Exception {
		XMLDecoder decoder =
				new XMLDecoder(
						new BufferedInputStream(
								new FileInputStream(filename)));
		Hashtable<String, City> l = (Hashtable<String, City>) decoder.readObject();
		decoder.close();
		return l;
	}

	public ArrayList<City> getRoute(){ return route; }
	public ArrayList<Attraction> getAttractions(){ return attractions; }
	public double getCost() { return cost; }


	public void setSortValue(boolean b) {
		this.sortByDistance = b;
	}
}