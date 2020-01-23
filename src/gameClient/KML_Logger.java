package gameClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

import MyGameGUI.Point3D;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;


public class KML_Logger {
	public static Thread KmlThread;
	DGraph g0;
	game_service game;
	Kml kmlAriel;
	Document doc;
	
	
	public KML_Logger(DGraph g0) {
		this.g0 = g0;
	}
	
	
	public void setGame (game_service game) {
		this.game = game;
	}

	public void kml_Graph() {
		this.kmlAriel = new Kml();
		this.doc = kmlAriel.createAndSetDocument().withName("new KML").withOpen(true);
		//  floder
		Folder floder = doc.createAndAddFolder();
		floder.withName("KML new floder").withOpen(true);
		// icon 
		Icon icon1 = new Icon();
		icon1.withHref("http://maps.google.com/mapfiles/kml/paddle/purple-blank.png");
		Style style1 = doc.createAndAddStyle();
		style1.withId("place Node").createAndSetIconStyle().withIcon(icon1).withScale(1);
		Collection<node_data> Nodes = g0.getV();
		for (node_data node : Nodes) {
			Placemark place = doc.createAndAddPlacemark();
			place.withName("" + node.getKey()).withStyleUrl("#place Node");
			place.createAndSetPoint().addToCoordinates(node.getLocation().x(), node.getLocation().y());
			//  edage
			Style Edge = doc.createAndAddStyle();
			Edge.withId("Edge").createAndSetLineStyle().withColor("ff43b3ff").setWidth(2.5);
			Collection<edge_data> Edegs = g0.getE(node.getKey());
			Point3D p1 = node.getLocation();
			for (edge_data edge : Edegs) {
				Point3D p2 = g0.getNode(edge.getDest()).getLocation();
				Placemark place2 = doc.createAndAddPlacemark().withStyleUrl("#Edge");
				place2.createAndSetLineString().withTessellate(true).addToCoordinates(p1.x() , p1.y()).addToCoordinates(p2.x(), p2.y());
			}			
		}
	}

	
	public void save (String fileName ) {
		try {
			kmlAriel.marshal(new File(fileName));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void setFruit(String time , String endTime) {
		//icon 
		Icon icon1 = new Icon();
		icon1.withHref("http://www.up2me.co.il/imgs/9186186.png"); // change
		//style
		Style style1 = doc.createAndAddStyle();
		style1.withId("place drgon").createAndSetIconStyle().withIcon(icon1).withScale(1.5);
		// icon 
		Icon icon2 = new Icon();
		icon2.withHref("http://www.up2me.co.il/imgs/11361140.png"); // change
		//style
		Style style2 = doc.createAndAddStyle();
		style2.withId("place king").createAndSetIconStyle().withIcon(icon2).withScale(1.5);
		List <String> fruit = game.getFruits();
		for (String string : fruit) {
			try {
				double y=0,x=0,z=0,count=0;
				String l = "";
				JSONObject obj = new JSONObject(string);
				JSONObject fruits1 =obj.getJSONObject("Fruit");
				String p1=(String) fruits1.get("pos");
				for (int j = 0; j < p1.length(); j++) {
					if (p1.charAt(j) != ',') {
						l+=p1.charAt(j); 
						if (count==2 && j == p1.length()-1) {
							z= Double.parseDouble(l);
							count=0;
							l="";
						}
					}
					else {
						if (count==0) {
							x= Double.parseDouble(l);
							count++;
							l="";
						}
						else if (count==1) {
							y= Double.parseDouble(l);
							count++;
							l="";
						}
					}
				}
				Point3D p2 = new Point3D(x,y,z);
				int TYPE =  fruits1.getInt("type");
				Placemark place_Fruit = doc.createAndAddPlacemark();
				if(TYPE ==1 ) {
					place_Fruit.setStyleUrl("#place drgon");
				}
				else {
					place_Fruit.setStyleUrl("#place king");
				}
				place_Fruit.createAndSetPoint().addToCoordinates(x , y);
				place_Fruit.createAndSetTimeSpan().withBegin(time).withEnd(endTime);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * this function set the robot by pass all the robots and 
	 * each robot will place at the kml file.
	 * @param time
	 * @param endTime
	 */
	public void setRobot (String time , String endTime) {
		//icon 
		Icon icon = new Icon();
		icon.withHref("http://www.up2me.co.il/imgs/62303573.jpg"); // change
		//style
		Style style = doc.createAndAddStyle();
		style.withId("place robot").createAndSetIconStyle().withIcon(icon).withScale(1.5);
		List<String> robots = game.getRobots();
		for (String string : robots) {
			try {
				double y=0,x=0,z=0,count=0;
				String l = "";
				JSONObject obj = new JSONObject(string);
				JSONObject Robot =obj.getJSONObject("Robot");
					String p1=(String) Robot.get("pos");
					for (int j = 0; j < p1.length(); j++) {
						if (p1.charAt(j) != ',') {
							l+=p1.charAt(j); 
							 if (count==2 && j == p1.length()-1) {
									z= Double.parseDouble(l);
									count=0;
									l="";
								}
						}
						else {
							if (count==0) {
								x= Double.parseDouble(l);
								count++;
								l="";
							}
							else if (count==1) {
								y= Double.parseDouble(l);
								count++;
								l="";
							}
						}
					}
					Point3D p2= new Point3D(x,y,z);
					int id =  Robot.getInt("id");
					Placemark place = doc.createAndAddPlacemark();
						place.setStyleUrl("#place robot");
					place.createAndSetPoint().addToCoordinates(x , y);
					place.createAndSetTimeSpan().withBegin(time).withEnd(endTime);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
}
	
	

	
	public static void threadKml(game_service game , KML_Logger kml) {
		KmlThread= new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (game.isRunning()) {
					try{
						Thread.sleep(200);
						String time = java.time.LocalDate.now()+"T"+java.time.LocalTime.now();
						LocalTime end = java.time.LocalTime.now(); 
						end = end.plusNanos(100*1000000);
						String endTime = java.time.LocalDate.now()+"T"+end;
						kml.setRobot(time , endTime);
						kml.setFruit(time , endTime);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		KmlThread.start();
	}
}