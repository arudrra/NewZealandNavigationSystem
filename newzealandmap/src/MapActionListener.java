import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class MapActionListener implements ActionListener {
		private JComboBox<String> origin;
		private JComboBox<String> destination;
		private JComboBox<String> sortOption;
		private Graph graph;
		private MapPanel panel;
		private JLabel text;
		
		public MapActionListener(JComboBox<String> origin , JComboBox<String> destination, JComboBox sortOption, Graph graph, MapPanel panel, JLabel text){
			this.origin = origin;
			this.destination = destination;
			this.sortOption = sortOption;
			this.graph = graph;
			this.panel = panel;
			this.text = text;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (origin.getSelectedIndex() != -1 && destination.getSelectedIndex() != -1 && sortOption.getSelectedIndex() != -1) {
				if (sortOption.getSelectedItem().toString().compareTo("Distance") == 0) graph.setSortValue(true);
				else graph.setSortValue(false);
				
				long startTime = System.currentTimeMillis();
				graph.findShortestPath(origin.getSelectedItem().toString(), destination.getSelectedItem().toString());
				long endTime = System.currentTimeMillis();
				
				ArrayList <City> route = graph.getRoute();
				ArrayList <Attraction> attractions = graph.getAttractions();
				
				try { panel.map = ImageIO.read(new File("NewZealandMapDeSat.jpg"));}
				catch (IOException e) { System.out.println(e.getMessage()); }
				BufferedImage image = panel.map;
				
				for(int i = 1; i < route.size(); i++){
					Graphics2D g2 = image.createGraphics();
					g2.setColor(Color.green);
					g2.setStroke(new BasicStroke(12));
					City city1 = route.get(i-1);
					City city2 = route.get(i);
					g2.draw(new Line2D.Double(city1.getPointOnPicture().getX(), city1.getPointOnPicture().getY(), city2.getPointOnPicture().getX(), city2.getPointOnPicture().getY()));
				}
				
				String output = "<html><b>ROUTE:</b><br>";
				for(int x = route.size() - 1; x > -1; x--) {
					output = output + route.get(x).name + "<br>";
				}
				output = output + "<br>ATTRACTIONS:<br>";
				for(int x = attractions.size() - 1; x > -1; x--) {
					output += attractions.get(x).name + "<br>";
				}
				if(sortOption.getSelectedItem().toString().compareTo("Distance") == 0) {
					output += "<br>DISTANCE: " + Math.floor(graph.getCost()) + " Kilometers";
				} else {
					output += "<br>TIME: " + Math.floor(graph.getCost() / 60) + " Hours and " + Math.floor(graph.getCost() % 60) + " Minutes";
				}
				output += "<br>Pathfinding Runtime: " + Math.floor(endTime - startTime) + " Milliseconds";
				output += "</html>";
				text.setText(output);
			}
		}
	}