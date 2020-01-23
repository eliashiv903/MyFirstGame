package gameClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import MyGameGUI.Point3D;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.edge_data;
import dataStructure.node_data;

public class AlgoGameWhereTheRoobotsGo {
	ArrayList<ArrayList<Edgedata>> roobotsSrcAndDest=new ArrayList<ArrayList<Edgedata>>() ;
	private  ArrayList<Fruit> fruit = new ArrayList<Fruit>();
	private DGraph g0 = new DGraph();
	private  ArrayList<List<node_data>> dest1 = new ArrayList<List<node_data>>();

	public ArrayList<List<node_data>> getDest1(){
		return dest1;
	}

	AlgoGameWhereTheRoobotsGo (game_service game,ArrayList<Integer>src, int game_num){

		String info=game.toString();
		int numR=Integer.valueOf(""+info.charAt(info.indexOf("graph")-3));
		String g = game.getGraph();
		g0=new DGraph();
		g0.init(g);	
		setList(game);
		String s=game.toString();
		Edgedata a[]=new Edgedata[src.size()];
		for (int i = 0; i < numR; i++) {
			roobotsSrcAndDest.add(new ArrayList<Edgedata>());
			for(Fruit f:fruit) {
				roobotsSrcAndDest.get(i).add(g0.getEdgeE(f.getSrc(), f.getDest()));
			}
			Collections.sort( roobotsSrcAndDest.get(i), new  distance_Comperator(src.get(i),g0));
			a[i]=roobotsSrcAndDest.get(i).get(0);
		}
		//		if(game_num==20) {
		//			samFruitAndRoobot(game);
		//			//samFruitAndRoobot(game,src,a);
		//			findNewDest(src);
		//return;
		//			
		//		}


		if(src.size()>1)sameftuit(src,a);
		if(game_num==16)sameWay16(src, a, game_num);
		else if(src.size()>1)sameWay(src,a,game_num);
		findNewDest(src);

	}

	private void samFruitAndRoobot(game_service game) {
		roobotsSrcAndDest.get(1).add(0,roobotsSrcAndDest.get(0).get(roobotsSrcAndDest.get(0).size()-1));
		roobotsSrcAndDest.get(2).add(0,roobotsSrcAndDest.get(0).get(roobotsSrcAndDest.get(0).size()-3));

	}
	private void sameWay16(ArrayList<Integer> src, Edgedata[] a, int game_num) {

		ArrayList<List<node_data>> z=new ArrayList<List<node_data>>();
		ArrayList<List<Integer>> k=new ArrayList<List<Integer>>();

		Graph_Algo v=new Graph_Algo (g0);

		for (int i = 0; i < a.length; i++) {
			z.add(v.shortestPath(src.get(i), a[i].getSrc()));
			z.get(i).remove(0);
			k.add(new ArrayList<Integer>());
			for (int j2 = 0; j2 < z.get(i).size(); j2++) {
				k.get(i).add(z.get(i).get(j2).getKey());
			}	
		}


		if(game_num==16) {
			roobotsSrcAndDest.get(0).add(g0.getEdgeE(12, 11));
			roobotsSrcAndDest.get(1).add(g0.getEdgeE(27, 28));

			for (int i = 0; i < a.length; i++) {

				if(k.get(0).contains(31)||k.get(0).contains(5)||k.get(0).contains(20)) {
					System.out.println(":)");
					roobotsSrcAndDest.get(0).remove(0);
					a[0]=roobotsSrcAndDest.get(0).get(0);
					z.remove(0);
					k.remove(0);
					z.add(0, v.shortestPath(src.get(0), a[0].getSrc()));
					k.add(0, new ArrayList<Integer>());

					for (int j2 = 0; j2 < z.get(0).size(); j2++) {
						k.get(0).add(z.get(0).get(j2).getKey());
					}
				}
				if(k.get(1).contains(32)||k.get(1).contains(6)||k.get(1).contains(19)) {
					System.out.println(":)");
					roobotsSrcAndDest.get(1).remove(0);
					a[1]=roobotsSrcAndDest.get(1).get(0);
					z.remove(1);
					k.remove(1);
					z.add(1, v.shortestPath(src.get(1), a[1].getSrc()));
					k.add(1, new ArrayList<Integer>());
					for (int j2 = 0; j2 < z.get(1).size(); j2++) {
						k.get(1).add(z.get(1).get(j2).getKey());
					}		
				}
			}
		}
	}
	private void sameWay(ArrayList<Integer> src, Edgedata[] a, int game_num) {
		ArrayList<List<node_data>> z=new ArrayList<List<node_data>>();
		ArrayList<List<Integer>> k=new ArrayList<List<Integer>>();

		Graph_Algo v=new Graph_Algo (g0);

		for (int i = 0; i < a.length; i++) {
			z.add(v.shortestPath(src.get(i), a[i].getSrc()));
			z.get(i).remove(0);
			k.add(new ArrayList<Integer>());
			for (int j2 = 0; j2 < z.get(i).size(); j2++) {
				k.get(i).add(z.get(i).get(j2).getKey());
			}	
		}

		int u=2;
		for (int x = 0; x <u; x++) {
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a.length&&1<roobotsSrcAndDest.get(j).size(); j++) {
					if(i!=j &&k.get(i).contains(a[j].getSrc())) {
						roobotsSrcAndDest.get(j).remove(0);
						a[j]=roobotsSrcAndDest.get(j).get(0);
						z.remove(j);
						k.remove(j);
						z.add(j, v.shortestPath(src.get(j), a[j].getSrc()));
						k.add(j, new ArrayList<Integer>());
						for (int j2 = 0; j2 < z.get(j).size(); j2++) {
							k.get(j).add(z.get(j).get(j2).getKey());
						}
					}
				}
			}
		}
	}
	private void sameftuit(ArrayList<Integer> src, Edgedata[] a2) {

		if(src.size()==2&& a2[0].getSrc()==a2[1].getSrc()) {
			double dis0=g0.getNode(src.get(0)).getLocation().distance2D(g0.getNode(a2[0].getSrc()).getLocation());
			double dis1=g0.getNode(src.get(1)).getLocation().distance2D(g0.getNode(a2[1].getSrc()).getLocation());
			if(dis1<dis0)roobotsSrcAndDest.get(0).remove(0);
			else if(dis1>dis0)roobotsSrcAndDest.get(1).remove(0);
			else roobotsSrcAndDest.get(0).remove(0);
		}

		if(src.size()==3&& (a2[0].getSrc()==a2[1].getSrc()|| a2[0].getSrc()==a2[2].getSrc()|| a2[1].getSrc()==a2[2].getSrc()))  {
			ArrayList<Integer> arrSame=new ArrayList<Integer>();
			for (int i = 0; i < a2.length; i++) {
				for (int j = i; j < a2.length; j++) {
					if(i!=j&&a2[i].getSrc()==a2[j].getSrc()) {
						arrSame.add(i);

					}
				}
			}
			if(arrSame.size()==2) {
				double dis0=g0.getNode(src.get(arrSame.get(0))).getLocation().distance2D(g0.getNode(a2[arrSame.get(0)].getSrc()).getLocation());
				double dis1=g0.getNode(src.get(arrSame.get(1))).getLocation().distance2D(g0.getNode(a2[arrSame.get(1)].getSrc()).getLocation());
				if(dis1<dis0)roobotsSrcAndDest.get(arrSame.get(0)).remove(0);
				else if(dis1>dis0)roobotsSrcAndDest.get(arrSame.get(1)).remove(0);
				else roobotsSrcAndDest.get(arrSame.get(0)).remove(0);
			}
			if(arrSame.size()==3) {
				double dis0=g0.getNode(src.get(0)).getLocation().distance2D(g0.getNode(a2[0].getSrc()).getLocation());
				double dis1=g0.getNode(src.get(1)).getLocation().distance2D(g0.getNode(a2[1].getSrc()).getLocation());
				double dis2=g0.getNode(src.get(2)).getLocation().distance2D(g0.getNode(a2[2].getSrc()).getLocation());
				if(dis1<dis0 &&dis2<dis0) {
					roobotsSrcAndDest.get(1).remove(0);
					roobotsSrcAndDest.get(2).remove(0);
				}
				else if(dis2<dis1 &&dis0<dis1) {
					roobotsSrcAndDest.get(0).remove(0);
					roobotsSrcAndDest.get(2).remove(0);
				}
				else if(dis1<dis2 &&dis0<dis2) {
					roobotsSrcAndDest.get(0).remove(0);
					roobotsSrcAndDest.get(1).remove(0);
				}
				else {
					roobotsSrcAndDest.get(1).remove(0);
					roobotsSrcAndDest.get(2).remove(0);
				}
			}
		}
	}

	private void findNewDest(ArrayList<Integer> src) {
		for (int i = 0; i <  src.size(); i++) {
			List<node_data> n=new ArrayList<node_data>();
			Graph_Algo a=new Graph_Algo (g0);
			Edgedata c=roobotsSrcAndDest.get(i).get(0);
			n=	a.shortestPath(src.get(i),  c.getSrc());
			dest1.add(n);
			dest1.get(i).remove(0);
			dest1.get(i).add(g0.getNode(c.getDest()));		
		}

	}

	public void setFruit() {
		for (int j = 0; j < fruit.size(); j++) {
			if(!set(fruit.get(j),0.00000000001,j)) j--;	
		}
	}
	private boolean set(Fruit f, double d, int i2) {
		ArrayList<node_data> a=new ArrayList<node_data>( g0.getV());
		boolean noFound=true;
		for (int i = 0; i < a.size()&&noFound; i++) {
			int node_id=a.get(i).getKey();
			ArrayList<edge_data> b=new ArrayList<edge_data>(g0.getE(node_id));
			for(edge_data e:b) {
				if(findOn(e,f,d)) {
					e.setFruit(f);
					noFound=false;
					break;
				}
			}
		}
		d=d*100;
		if(noFound)	 set(f,d,i2);	
		return true;
	}
	public boolean findOn(edge_data e, Fruit f, double d) {
		if(e.getSrc()>e.getDest()) {
			if(f.getType()==1) return false;
		}
		else {
			if(f.getType()==-1) return false;
		}
		double distSrc=f.getPos().distance2D(g0.getNode(e.getSrc()).getLocation());
		double distDest=f.getPos().distance2D(g0.getNode(e.getDest()).getLocation());
		double distEdge=g0.getNode(e.getDest()).getLocation().distance2D(g0.getNode(e.getSrc()).getLocation());
		if(distDest+distSrc-distEdge >=-d&&distDest+distSrc-distEdge <=d)return true;
		return false;
	}

	public   void setList( game_service game ) {
		Iterator<String> f_iter = game.getFruits().iterator();
		String sF="";
		while(f_iter.hasNext()) {
			sF=""+f_iter.next();	
			JSONObject line;
			try {
				line = new JSONObject(sF);
				JSONObject ttt = line.getJSONObject("Fruit");
				double value = ttt.getDouble("value");
				int type = ttt.getInt("type");
				String p = ttt.getString("pos");
				Point3D pos = new Point3D(p);
				fruit.add(new Fruit(value, type, pos));
			}
			catch (JSONException r) {r.printStackTrace();}
		}
		setFruit();
	}

}
