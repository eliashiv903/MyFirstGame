package gameClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import MyGameGUI.Window;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.Nodedata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;


/**
*Automatically runs the stage for 3 robots
* and sends a snapshot of the real-time traffic in the Ariel settlement saved in kml
 * 
 */
public class AutomaticPlay3 {
	private static KML_Logger kmlAriel;
	
	//Initializes the game and puts the bots according to the algorithm

	public  void test3(WindowMange window) {		
		int scenario_num=window.getLevelPlay();
		Game_Server.login(window.getId());
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		kmlAriel=new KML_Logger(gg);
		kmlAriel.kml_Graph();
		ArrayList<Nodedata> roobet = new ArrayList<Nodedata>();
		AlgoGameRooboteStart A=new AlgoGameRooboteStart(game,scenario_num);
		Edgedata []E=A.getEdgedataMaxVal();
		ArrayList<Integer> srcR = new ArrayList<Integer>();
		for (int j = 0; j < E.length; j++) {
			game.addRobot(E[j].getSrc());
			roobet.add(new Nodedata(gg.getNode(E[j].getSrc())));
			srcR.add(E[j].getSrc());
		}

		window.setG0Graph(gg);
		window.setGame(game);
		window.setRoobet(roobet);
		window.setGrafh();
		window.setList( game);
		window.repaint();

		int dests[]=new int[E.length];
		for (int i = 0; i < dests.length; i++) {
			dests[i]=E[i].getDest();
		}

		game.startGame();
		kmlAriel.setGame(game);
		kmlAriel.threadKml(game, kmlAriel);
		thread(game);

		while(game.isRunning()) {
			moveRobots(game, gg,roobet ,window,dests,srcR,scenario_num);
		}
		try {
			kmlAriel.save("ariel"+scenario_num + ".kml");
			String s = goKML(""+scenario_num + ".kml");
			game.sendKML(s); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String results = game.toString();
		System.out.println(results);
	}
//Moves the bots according to what they received from the algorithm
	private static void moveRobots(game_service game, DGraph gg, ArrayList<Nodedata> roobet, WindowMange window,int []dests, ArrayList<Integer> src, int scenario_num) {
		ArrayList<List<node_data>> dest1 = new ArrayList<List<node_data>>();		
		int dest;

		for (int j = 0; j < dests.length; j++) {
			while(game.isRunning() &&game.getRobots().get(j).toString().lastIndexOf("-")<0) {
			}

			if(dest1.size()==0 ) {
				AlgoGameWhereTheRoobotsGo R=new AlgoGameWhereTheRoobotsGo(game,src,scenario_num);
				dest1=R.getDest1();
			}
			dest=dest1.get(j).get(0).getKey();
			src.set(j, dest);
			dest1.get(j).remove(0);
			game.chooseNextEdge(j, dest);
			roobet.get(j).copy(new Nodedata(gg.getNode(dest)));
		}
		window.repaint();
		WindowMange.setList(game);
		window.repaint();
	}

/**
* Thread:While the game is taking steps for a certain amount of time throughout the game.
* 
 */
	public static  void thread(game_service game) {
		Thread a=new  Thread(new Runnable() {
			@Override
			public void run() {
				while(game.isRunning()) {
					String c=game.getRobots().get(0).toString();
					String z=game.getRobots().get(1).toString();
					String t=game.getRobots().get(2).toString();
					if((c.lastIndexOf("-")<0 ||z.lastIndexOf("-")<0 ||t.lastIndexOf("-")<0) &&game.timeToEnd()>72) {

						game.move();
					}
					try {
						Thread.sleep(106);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
		});
		a.start();
	}

	public String goKML(String numLevel) {
		String s = "";
		
		try {
			File file = new File(numLevel);
			BufferedReader reader = new BufferedReader( new FileReader(file));
			String r= "";
			while ((r = reader.readLine()) != null) {
				s = s+ r+"\n";
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return s;		
	}
	
}