package MyGameGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.fruits;
import Server.game_service;
import Server.robot;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.Nodedata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.Fruit;

import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_graph;

import java.awt.*;
import javax.swing.*;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;




public class Window extends JFrame implements ActionListener, MouseListener {
	
	
	
	
	private String results ="";
	private static double r_minx=999999998;
	private static double r_maxx=-999999998;
	private boolean r;
	private boolean t;
	private static double r_miny=999999998;
	private static double r_maxy=-999999998;
	private game_service game=null;
	private static DGraph g0 = new DGraph();
	private ArrayList<node_data> g3 = new ArrayList<node_data>();
	private ArrayList<Integer> g4 = new ArrayList<Integer>();
	private ArrayList<Nodedata> roobet = new ArrayList<Nodedata>();
	private static ArrayList<Fruit> fruit = new ArrayList<Fruit>();
	private String syso = "ches graph";
	private String syso1 = "";
	private String syso2 = "";
	private String syso3 = "";
	private String syso4 = "";
	private String w = "";
	private int src = 0;
	private int dest = 0;
	private int count = 0;
	private int countSave = 0;
	private int numGraph = -1;
	private int countRoboot = 0;
	private int robotTemp =-1;
	private int numRoboot =0;
	private double valWin =0;
   
	

	private void setGrafh() {
		ArrayList<node_data> a=new ArrayList<node_data>(g0.getV());
		for (int i = 0; i < a.size(); i++) {
			if(r_maxx<a.get(i).getLocation().x())r_maxx=a.get(i).getLocation().x();
			if(r_minx>a.get(i).getLocation().x())r_minx=a.get(i).getLocation().x();
		}
		for (int i = 0; i < a.size(); i++) {
			if(r_maxy<a.get(i).getLocation().y())r_maxy=a.get(i).getLocation().y();
			if(r_miny>a.get(i).getLocation().y())r_miny=a.get(i).getLocation().y();
		}
		ArrayList<node_data> dataNode=new ArrayList<node_data>(g0.getV());
		for (int i = 0; i <dataNode.size(); i++) {
			dataNode.get(i).setLocation(new Point3D(scaleX(r_maxx,r_minx,dataNode.get(i).getLocation().x()),scaleY(r_maxy,r_miny,dataNode.get(i).getLocation().y())));
		}


	}
	public Window() {
		initGUI();
	}


	private void initGUI() {
		this.setSize(1300, 1200);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		Menu syso = new Menu();
		menuBar.add(syso);
		this.setMenuBar(menuBar);


		Menu algo = new Menu("algo");
		menuBar.add(algo);
		this.setMenuBar(menuBar);

		Menu game = new Menu("game");
		menuBar.add(game);
		this.setMenuBar(menuBar);

		Menu Choosegraph = new Menu("Choose a graph");
		menuBar.add(Choosegraph);
		this.setMenuBar(menuBar);

		MenuItem item1 = new MenuItem("savePic");
		item1.addActionListener(this);

		MenuItem item2 = new MenuItem("clearAll");
		item2.addActionListener(this);

		MenuItem item4 = new MenuItem("shortestPathDist");
		item4.addActionListener(this);

		MenuItem item5 = new MenuItem("path");
		item5.addActionListener(this);

		MenuItem item6 = new MenuItem("TSP");
		item6.addActionListener(this);

		MenuItem item8 = new MenuItem("endTSP");
		item8.addActionListener(this);

		MenuItem item7 = new MenuItem("clearWayAns");
		item7.addActionListener(this);


		MenuItem item16 = new MenuItem("putRobet");
		item16.addActionListener(this);

		MenuItem item18 = new MenuItem("TimeToEnd");
		item18.addActionListener(this);

		MenuItem item19 = new MenuItem("moveRobetToEdge");
		item19.addActionListener(this);

		
		MenuItem item17;
		for (int i = 0; i < 24; i++) {
			item17 = new MenuItem(""+i);
			item17.addActionListener(this);
			Choosegraph.add(item17);
		}
		menu.add(algo);
		menu.add(item1);
		menu.add(item2);
		menu.add(item7);
		algo.add(item4);
		algo.add(item5);
		algo.add(item6);
		algo.add(item8);

		game.add(item16);
		game.add(item18);
		game.add(item19);
		this.addMouseListener(this);

	}
	private static double scaleX(double r_max, double r_min, double data){

		double res = ((data - r_min) / (r_max-r_min)) * (1250- 	30) + 30;
		return res;
	}
	private static double scaleY(double r_max, double r_min, double data){

		double res = ((data - r_min) / (r_max-r_min)) *(650 - 	150) + 150;

		return 815-res;
	}






	public void paint(Graphics g) {

		super.paint(g);

		if(numGraph!=-1) {
			BufferedImage imgGraph  = null;
			try {
				imgGraph = ImageIO.read(new File( "data\\imageGR"+numGraph+".jpg"));
			} catch (IOException e) {
				syso="no seccs fruit";
			}

			g.drawImage(imgGraph , 0, 0, 1297 , 737, this);
		}
		
		BufferedImage imgFruit = null;
		try {
			imgFruit = ImageIO.read(new File("data\\king3.png"));
		} catch (IOException e) {
			syso="no seccs fruit";
		}

		BufferedImage imgFruit2 = null;
		try {
			imgFruit2 = ImageIO.read(new File("data\\dragon1.png"));
		} catch (IOException e) {
			syso="no seccs fruit";
		}
		g.setColor(Color.WHITE);
		for (int i = 0; i < fruit.size(); i++) {
			if(fruit.get(i).getType()==-1) {
				g.drawImage(imgFruit, fruit.get(i).getPos().ix(),fruit.get(i).getPos().iy(), 32, 32, this);
				g.drawString(""+fruit.get(i).getValue(),fruit.get(i).getPos().ix()+18,fruit.get(i).getPos().iy());

			}
			else {
				g.drawImage(imgFruit2, fruit.get(i).getPos().ix(),fruit.get(i).getPos().iy(), 32, 32, this);
				g.drawString(""+fruit.get(i).getValue(),fruit.get(i).getPos().ix()+18,fruit.get(i).getPos().iy());
			}

		}
		g.setColor(Color.ORANGE);
		g.drawString(syso, 99, 73);
		if(!syso2.equals(""))g.drawString(syso2, 99, 85);
		if(!syso1.equals(""))g.drawString(syso1, 99, 97);
		if(!syso3.equals(""))g.drawString(syso3, 99, 109);
		if(!syso4.equals(""))g.drawString(syso4, 99, 121);
		if(!results.equals(""))g.drawString(results, 99, 133);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("data\\jon.jpg"));
		} catch (IOException e) {
			syso="no seccs robot";
		}
		for (int i = 0; i < roobet.size(); i++) {
			g.drawImage(img, roobet.get(i).getLocation().ix(), roobet.get(i).getLocation().iy(), 44, 44, this);
		}

	}


    @Override
	public void actionPerformed(ActionEvent e) {

   
		String str = e.getActionCommand();
		Graph_Algo a = new Graph_Algo();
		if(str.equals("TimeToEnd")) {
			System.out.println(game.timeToEnd());
			if(game.isRunning())results="TimeToEnd:"+game.timeToEnd();
			else results="The game dont start";
		}
		else if(str.equals("moveRobetToEdge")) {
			syso="ches roobet to move"; 
			w="moveRobetToEdge";
		}
		else if(str.equals("new game")) {
			setVisible(false); //you can't see me!
			dispose();
			Window window = new Window();
			window.setVisible(true);
		}
		else if(str.equals("putRobet")) {
			if(roobet.size()<numRoboot)w="putRobet";
			else {
				syso="only"+ numRoboot+" robets in this graph";
				w="";

			}
		}

		else	if(str.charAt(0)>='0' &&str.charAt(0)<='9') {
			clean();
			results="";
			numGraph=Integer.valueOf(str);
			game = Game_Server.getServer(numGraph); 
			String g = game.getGraph();
			g0=new DGraph();
			g0.init(g);	
			setGrafh();
			setList(game);
			setNumRoobot();
			syso="ches were put roobots";
			repaint();
		}

		else if(!str.equals("savePic")) {
			syso1="";
			syso2="";
			syso3="";
			syso4="";
		}
		
		else  if (str.equals("savePic")) {
			a.init(g0);
			save_paint("picGame");
		}
		else if (str.equals("clearAll")) {
			valWin=0;
			fruit = new ArrayList<Fruit>();
			w = "";
			count = 0;
			src = 0;
			dest = 0;
			g3 = new ArrayList<node_data>();
			g0 = new DGraph();
			g4 = new ArrayList<Integer>();
			syso = "All clear";
			syso1="";
			syso2="";
			syso3="";
			syso4="";
			roobet =new ArrayList<Nodedata>();
		}
		else if (str.equals("dataGraphToString")) {
			syso = g0.toStringDataNode();
			syso1=g0.toStringedgedataNode();
			syso2="size DataNode:"+g0.nodeSize();
			syso3="size edgedataNode:"+g0.edgeSize();
			syso4="MC:"+g0.getMC();
		}
		else if (str.equals("clearWayAns")) {
			w = "";
			count = 0;
			src = 0;
			dest = 0;
			g3 = new ArrayList<node_data>();
			g4 = new ArrayList<Integer>();
			syso1="";
			syso2="";
			syso3="";
			syso4="";
			syso="the wayAns clear";

		}

		else if (str.equals("isconnected")) {
			a.init(g0);
			boolean cv = a.isConnected();
			syso = "" + cv;
			System.out.println(cv);
		}
		else if (str.equals("shortestPathDist")) {
			w = str;
			syso = "Choose a src and dest";
		}

		else if (str.equals("path")) {
			syso = "Choose a src and dest";
			w = str;
		}
		else if (str.equals("endTSP")) {
			a.init(g0);
			g3 = new ArrayList<node_data>(a.TSP(g4));
			ArrayList<Integer> g2 = new ArrayList<Integer>();
			for (int i = 0; i < g3.size(); i++)
				g2.add(g3.get(i).getKey());
			syso = "TSP targets:"+g4.toString();
			syso1=".TSP ans:" + g2.toString();
			System.out.println("TSP ans:" + g2.toString());
			g4 = new ArrayList<Integer>();
			w = "";
		}
		else if (str.equals("TSP")) {
			w = str;
			syso = "Choose a point or end";
		}
		
		
		
		repaint();
	}


	private void clean() {
		if(game!=null)game.stopGame();

		countRoboot=0;
		valWin=0;
		fruit = new ArrayList<Fruit>();
		w = "";
		count = 0;
		src = 0;
		dest = 0;
		g3 = new ArrayList<node_data>();
		g0 = new DGraph();
		g4 = new ArrayList<Integer>();
		syso = "ches graph";
		syso1="";
		syso2="";
		syso3="";
		syso4="";
		roobet =new ArrayList<Nodedata>();

	}
	private void setNumRoobot() {
		String info=""+game.toString();
		numRoboot=Integer.valueOf(""+info.charAt(info.indexOf("robots")+8));
		syso=""+numRoboot;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("mouseClicked");
	}

	@Override
	public void mousePressed(MouseEvent e) {

		syso = "";
		syso1="";
		syso2="";
		syso3="";
		syso4="";
		if (w.equals("putRobet")) {
			if(roobet.size()<numRoboot) {
				double x = e.getX();
				double y = e.getY();
				Nodedata n=new Nodedata( setnode(x, y));
				game.addRobot(n.getKey());
				roobet.add(n);
				game.startGame();
				if(game.isRunning()) {			
					syso2="game start";
					trehd(game);
					trehd1(game);
				}else {
					syso2="put mor roobot";
				}
			}
			else {
				w="";
				syso="only"+ numRoboot+" robets in this graph";
			}
		}

		else if (w.equals("path") || w.equals("shortestPathDist")||w.equals("removeEdgeFromGraph")||w.equals("removeNodeFromGraph")) {
			double x = e.getX();
			double y = e.getY();
			ArrayList<node_data> g1 = new ArrayList<node_data>(g0.getV());
			for (node_data a : g1) {
				Point3D p1 = new Point3D((int) a.getLocation().x(), (int) a.getLocation().y());
				if (x < p1.x() + 15 && x > p1.x() - 15 && y < p1.y() + 15 && y > p1.y() - 15) {
					if (count == 0)src = a.getKey();
					else dest = a.getKey();
				}
			}
			count++;
			if (count == 1) {
				if(w.equals("removeNodeFromGraph")) {
					g0.removeNode(src);
					syso="remove node:"+src;
					System.out.println(syso);
					w = "";
					count = 0;
					src = 0;
					dest = 0;
				}
				else {
					syso = "in list: " + src + ", choose dest";
					System.out.println("in list: " + src + "," + dest);
				}
			} else {
				if(w.equals("removeEdgeFromGraph")) {
					g0.removeEdge(src, dest);
					syso="remove edge from :"+src+" to "+dest;
					System.out.println(syso);
				}
				else if (w.equals("shortestPathDist")) {
					Graph_Algo a = new Graph_Algo();
					a.init(g0);
					double ans = a.shortestPathDist(src, dest);
					syso = "from src:"+src+"  to dest:"+dest+"  dist=" + ans;
					System.out.println(ans);
				}
				else if (w.equals("path")) {
					Graph_Algo a = new Graph_Algo();
					a.init(g0);
					g3 = new ArrayList<node_data>(a.shortestPath(src, dest));
					ArrayList<Integer> g2 = new ArrayList<Integer>();
					for (int i = 0; i < g3.size(); i++)
						g2.add(g3.get(i).getKey());
					syso = "from src:"+src+" to dest:"+dest;
					syso1="Path ans:" + g2.toString();
					System.out.println("Path ans:" + g2);

				}
				w = "";
				count = 0;
				src = 0;
				dest = 0;
			}
		} else if (w.equals("TSP")) {
			double x = e.getX();
			double y = e.getY();
			ArrayList<node_data> g1 = new ArrayList<node_data>(g0.getV());
			for (node_data a : g1) {
				Point3D p1 = new Point3D((int) a.getLocation().x(), (int) a.getLocation().y());
				if (x < p1.x() + 15 && x > p1.x() - 15 && y < p1.y() + 15 && y > p1.y() - 15)
					g4.add(a.getKey());
			}
			syso = "in list:" + g4;
			System.out.println("in list:" + g4.get(g4.size() - 1));
		}

		else if(w.equals("moveRobetToEdge")) {
			double x = e.getX();
			double y = e.getY();
			if(countRoboot==0) {
				setRoboot(x,y);
				syso="chas whre node to move";
			}
			else {

				moveRoobotToEdge(x, y);
				countRoboot=0;
				w="";
			}
			repaint();
		}
		repaint();
	}

	private void moveRoobotToEdge(double x, double y) {

		node_data nodeTemp=new Nodedata();
		nodeTemp=setnode(x, y);
		if(nodeTemp.getKey()!=-1) {
			int robootPlace=roobet.get(robotTemp).getKey();
			node_data d=g0.getNode(nodeTemp.getKey());
			roobet.get(robotTemp).copy(d);
			way(robootPlace,nodeTemp.getKey(),nodeTemp.getKey());
		}
		w="";
		if(!game.isRunning()) {
			results="result for graph:"+numGraph+":"+game.toString();
			clean();
		}
	}

	private double getVal() {
		double sum=0;
		for (String r: game.getRobots()) {
			String s=r.substring(r.indexOf("value")+7, r.indexOf("src")-2);
			sum+=Double.valueOf(s);
		}
		return sum;
	}
	private void way(int src, int srcFruit, int destFruit) {
		Graph_Algo b=new Graph_Algo();
		b.init(g0);
		g3=(ArrayList<node_data>) b.shortestPath(src, srcFruit);
		if(srcFruit!= destFruit	)g3.add(g0.getNode(destFruit));//only to furit
		ArrayList<Integer> g2 = new ArrayList<Integer>();
		for (int i = 0; i < g3.size(); i++)   g2.add(g3.get(i).getKey());
		syso1="Path :" + g2.toString();
		for (int i = 0; i < g3.size()-1; i++) {
			Edgedata e=g0.getEdgeE(g3.get(i).getKey(),g3.get(i+1).getKey());
			if(e.getFruit().size()!=0)t=true;
			else t=false;
			if(!game.isRunning()) {
				return;
			}
			r=true;
			valWin=getVal();
			game.move();
			while(r&&game.isRunning()) 		moveRobots(e.getDest());
			System.out.println(t+"t");
			if(t&&valWin!=getVal())t=false;
			ArrayList<Fruit> f=new ArrayList<Fruit>(e.getFruit());
			for (Fruit j: f) 	valWin+=j.getValue();
			fuirtRandom(e.getDest());

		}

	}
	private  void moveRobots(int nodeDest) {
		while(game.getRobots().get(robotTemp).toString().lastIndexOf("-")<0) {
		}
		game.chooseNextEdge(robotTemp, nodeDest);
		r=false;	
	}

	private void fuirtRandom(int i) {
		for (Fruit j: fruit) {
			g0.getEdgeE(j.getSrc(),j.getDest()).deletFruit();
		}
		fruit=new ArrayList<Fruit>();
		setList(game);
	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	public void setRoboot(double x, double y) {

		for (int i = 0; i < roobet.size(); i++) {
			if (x<roobet.get(i).getLocation().x()+40 && x>roobet.get(i).getLocation().x()-40 && y<roobet.get(i).getLocation().y()+40 && y>roobet.get(i).getLocation().y()-40) {
				robotTemp=i;
				countRoboot=1;
				break;
			}
		}
		syso1=""+robotTemp;
	}
	public node_data setnode(Double x, Double y) {
		ArrayList<node_data> g1= new ArrayList<node_data>(g0.getV());
		for (node_data a: g1) {
			if (x<a.getLocation().x()+40 && x>a.getLocation().x()-40 && y<a.getLocation().y()+40 && y>a.getLocation().y()-40) {
				return a;
			}
		}
		return null;
	}

	public void save_paint(String a) {
		try {
			BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = image.createGraphics();
			this.paint(graphics2D);
			File outputfile = new File(a+".jpg");
			ImageIO.write(image, "jpg", outputfile);
			System.out.println("save_paint ok");
			syso="save_paint ok";
			syso1="";
			syso2="";
			syso3="";
			syso4="";
		} catch (Exception exception) {
			System.out.println("no image");
			syso="no image";
			syso1="";
			syso2="";
			syso3="";
			syso4="";
		}
	}

	private void setFruitOne(Fruit f) {
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
	private static void setFruit() {
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
	private static boolean findOn(edge_data e, Fruit f) {
		if(e.getSrc()>e.getDest()) {
			if(f.getType()==1) return false;
		}
		else {
			if(f.getType()==-1) return false;
		}
		double distSrc=f.getPos().distance2D(g0.getNode(e.getSrc()).getLocation());
		double distDest=f.getPos().distance2D(g0.getNode(e.getDest()).getLocation());
		double distEdge=g0.getNode(e.getDest()).getLocation().distance2D(g0.getNode(e.getSrc()).getLocation());
		if(distDest+distSrc-distEdge >=-0.0000001&&distDest+distSrc-distEdge <=0.0000001)return true;
		return false;
	}


	public static  void setList( game_service game ) {
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
		for (int i = 0; i <fruit.size(); i++) {
			fruit.get(i).setLocation(new Point3D(scaleX(r_maxx,r_minx,fruit.get(i).getPos().x()),scaleY(r_maxy,r_miny,fruit.get(i).getPos().y())));
		}
		setFruit();

	}
	public  void trehd(game_service game) {
		Thread a=new  Thread(new Runnable() {
			@Override
			public void run() {
				while(game.isRunning()) {
					String t=game.getRobots().get(0).toString();


					if((t.lastIndexOf("-")<0  ) &&game.timeToEnd()>72) {
						game.move();
					}
					try {
						Thread.sleep(60);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
		});
		a.start();
	}
	public  void trehd1(game_service game) {
		Thread a=new  Thread(new Runnable() {
			@Override
			public void run() {
				while(game.isRunning()) {
					results="TimeToEnd:"+game.timeToEnd();
					repaint();
					try {
						Thread.sleep(2250);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
		});
		a.start();
	}

	public static void main(String[] args) {

		Window window = new Window();
		window.setVisible(true);


	}
}
