package gameClient;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import MyGameGUI.*;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.edge_data;
import dataStructure.node_data;

public class AlgoGameRooboteStart {
	int idR[] ;
	private  ArrayList<Fruit> fruit = new ArrayList<Fruit>();
	private DGraph g0 = new DGraph();
	private  Edgedata EdgedataMaxVal[];


	public Edgedata[] getEdgedataMaxVal() {
		return EdgedataMaxVal;
	}
	public AlgoGameRooboteStart(game_service game,int numGame){
		String info=game.toString();
		int numR=Integer.valueOf(""+info.charAt(info.indexOf("graph")-3));
		idR=new int[numR];
		EdgedataMaxVal=new Edgedata[numR];
		String g = game.getGraph();
		g0=new DGraph();
		g0.init(g);	
		setList(game);

		if(numR==1)getEdgeMaxValueFruit1();
		else if (numR==2)getEdgeMaxValueFruit2();
		else getEdgeMaxValueFruit3();
		if(numGame==0)EdgedataMaxVal[0]=new Edgedata(9, 8, 1);
		if(numGame==3)EdgedataMaxVal[0]=new Edgedata(3, 2, 1);
		if(numGame==16) {
			EdgedataMaxVal[0]=new Edgedata(39, 10, 1);
			EdgedataMaxVal[1]=new Edgedata(16, 17, 1);

		}
		if(numGame==20) {
			EdgedataMaxVal[0]=new Edgedata(40, 39, 1);
			EdgedataMaxVal[1]=new Edgedata(10, 7, 1);
			EdgedataMaxVal[2]=new Edgedata(47, 46, 1);
		}
		if(numGame==11) {
			EdgedataMaxVal[0]=new Edgedata(13, 12, 1);
			EdgedataMaxVal[1]=new Edgedata(9, 23, 1);
			EdgedataMaxVal[2]=new Edgedata(6, 7, 1);
		}if(numGame==13) {
			EdgedataMaxVal[0]=new Edgedata(40, 41, 1);
			EdgedataMaxVal[1]=new Edgedata(21, 22, 1);
		}
		if(numGame==23) {
			EdgedataMaxVal[0]=new Edgedata(47, 46, 1);
			EdgedataMaxVal[1]=new Edgedata(21, 32, 1);
			EdgedataMaxVal[2]=new Edgedata(3, 12, 1);
		}
	}
	public void getEdgeMaxValueFruit3() {
		Edgedata max1=new Edgedata();
		Edgedata max2=new Edgedata();
		Edgedata max3=new Edgedata();
		ArrayList<node_data> a=new ArrayList<node_data>( g0.getV());
		int sumBig1=0;
		int sumBig2=0;
		int sumBig3=0;
		for (node_data n:a) {
			ArrayList<edge_data> b=new ArrayList<edge_data>(g0.getE(n.getKey()));
			for(edge_data e:b) {
				int sum=0;
				for(Fruit f:e.getFruit()) {
					sum+=f.getValue();
				}
				if(sum>sumBig3) {
					if(sum>sumBig1) {
						sumBig3=sumBig2;
						max3=max2;
						sumBig2=sumBig1;
						sumBig1=sum;
						max2=max1;	
						max1=new Edgedata(e);

					}
					else if(sum>sumBig2){
						sumBig3=sumBig2;
						sumBig2=sum;
						max3=max2;
						max2=new Edgedata(e);	
					}
					else{
						sumBig3=sum;
						max3=new Edgedata(e);	
					}

				}	
			}
		}
		EdgedataMaxVal[0]=max1;
		EdgedataMaxVal[1]=max2;
		EdgedataMaxVal[2]=max3;
	}
	public void getEdgeMaxValueFruit2() {
		Edgedata max1=new Edgedata();
		Edgedata max2=new Edgedata();
		ArrayList<node_data> a=new ArrayList<node_data>( g0.getV());
		int sumBig1=0;
		int sumBig2=0;
		for (node_data n:a) {
			ArrayList<edge_data> b=new ArrayList<edge_data>(g0.getE(n.getKey()));
			for(edge_data e:b) {
				int sum=0;

				for(Fruit f:e.getFruit()) {

					sum+=f.getValue();

				}
				if(sum>sumBig2) {
					if(sum>sumBig1) {
						sumBig2=sumBig1;
						max2=max1;
						sumBig1=sum;
						max1=new Edgedata(e);
					}
					else {
						sumBig2=sum;
						max2=new Edgedata(e);

					}
				}	
			}
		}

		EdgedataMaxVal[0]=max1;
		EdgedataMaxVal[1]=max2;
	}
	public void getEdgeMaxValueFruit1() {
		Edgedata max=new Edgedata();
		ArrayList<node_data> a=new ArrayList<node_data>( g0.getV());
		int sumBig=0;
		for (node_data n:a) {
			ArrayList<edge_data> b=new ArrayList<edge_data>(g0.getE(n.getKey()));
			for(edge_data e:b) {
				int sum=0;

				for(Fruit f:e.getFruit()) {

					sum+=f.getValue();

				}
				if(sum>sumBig) {
					sumBig=sum;
					max=new Edgedata(e);
				}	
			}
		}

		this.EdgedataMaxVal[0]=max;
	}



	public void setFruit() {
		for (Fruit f:fruit) {
			ArrayList<node_data> a=new ArrayList<node_data>( g0.getV());
			boolean noFound=true;
			for (int i = 0; i < a.size()&&noFound; i++) {
				int node_id=a.get(i).getKey();
				ArrayList<edge_data> b=new ArrayList<edge_data>(g0.getE(node_id));
				for(edge_data e:b) {
					if(findOn(e,f)) {
						e.setFruit(f);
						noFound=false;
						break;
					}
				}
			}
		}
	}
	public boolean findOn(edge_data e, Fruit f) {
		if(e.getSrc()>e.getDest()) {
			if(f.getType()==1) return false;
		}
		else {
			if(f.getType()==-1) return false;
		}

		double distSrc=f.getPos().distance2D(g0.getNode(e.getSrc()).getLocation());
		double distDest=f.getPos().distance2D(g0.getNode(e.getDest()).getLocation());
		double distEdge=g0.getNode(e.getDest()).getLocation().distance2D(g0.getNode(e.getSrc()).getLocation());
		if(distDest+distSrc-distEdge >=-0.00001&&distDest+distSrc-distEdge <=0.00001)return true;

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
