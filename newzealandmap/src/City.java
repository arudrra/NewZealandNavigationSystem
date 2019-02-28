import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;


public class City implements Serializable {
	public Point2D coordinates;
	public ArrayList<Link> links;
	public String name;
	public double estimatedCost;
	public double distanceToReach;
	public Point2D pointOnPicture;
	public ArrayList<Attraction> attractions;
	
	public City() {
		estimatedCost = 0;
		distanceToReach = 0;
		links = new ArrayList<Link>();
	}
	
	public void addLink(Link link) {
		links.add(link);
	}
	
	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}
	
	public void setEstimatedCost(double cost) {
		this.estimatedCost = cost;
	}
	
	public void setDistanceToReach(double dist) {
		this.distanceToReach = dist;
	}
	
	public void setCoordinates(Point2D coordinates) {
		this.coordinates = coordinates;
	}

	public void setPointOnPicture(Point2D position){
		this.pointOnPicture = position;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Point2D getCoordinates() {
		return coordinates;
	}

	public Point2D getPointOnPicture(){
		return pointOnPicture;
	}
	
	public ArrayList<Link> getLinks(){
		return links;
	}
	
	public String getName() {
		return name;
	}
	
	public double getEstimatedCost() {
		return estimatedCost;
	}
	
	public void setAttractions(ArrayList<Attraction> attractions) {
		this.attractions = attractions;
	}
	
	public double getDistanceToReach() {
		return distanceToReach;
	}
	
}