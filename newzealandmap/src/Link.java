import java.util.ArrayList;

public class Link {
	public City city1;
	public City city2;
	public ArrayList<Attraction> attractions;
	public double time;
	public double dist;
	public String name;
	
	public Link() {
		this.time = 0;
		this.dist = 0;
		this.city1 = null;
		this.city2 = null;
		attractions = new ArrayList<Attraction>();
	}
	
	public double cost(boolean sortByDistance) {
		if(sortByDistance) return dist;
		return time;
	}
	
	public void addAttraction(Attraction attraction) {
		attractions.add(attraction);
	}
	
	public void setCity1(City city) {
		city1 = city;
	}
	
	public void setCity2(City city) {
		city2 = city;
	}
	
	public void setTime(double time) {
		this.time = time;
	}
	
	public void setDist(double dist) {
		this.dist = dist;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAttractions(ArrayList<Attraction> attractions) {
		this.attractions = attractions;
	}
}

	