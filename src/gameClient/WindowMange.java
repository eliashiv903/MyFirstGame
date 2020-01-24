package gameClient;

import java.awt.BorderLayout;
import java.awt.Color;

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

import java.io.IOException;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import MyGameGUI.Point3D;
import Server.Game_Server;

import Server.game_service;

import algorithms.Graph_Algo;
import dataStructure.DGraph;

import dataStructure.Nodedata;
import dataStructure.edge_data;

import dataStructure.node_data;
import gameClient.Fruit;


public class WindowMange extends JFrame implements ActionListener, MouseListener {



	private JTextField textField_1;
	private JButton btnNewButton;
	private JPanel panel;
	private JPasswordField idField;
	private char[] id = new char[10];
	private int id2=-1;
	private String levelYouWantToPlay="";
	private int levelYouWantToPlayInt=-1;
	private String results ="";
	private static double r_minx=999999998;
	private static double r_maxx=-999999998;
	private static double r_miny=999999998;
	private static double r_maxy=-999999998;
	private game_service game=null;
	private static DGraph g0 = new DGraph();
	private ArrayList<Nodedata> roobet = new ArrayList<Nodedata>();
	private static ArrayList<Fruit> fruit = new ArrayList<Fruit>();
	private String levelMax = "";
	private String  numPlayTody;
	private String  myBestScore[]=new String[11];
	private int levelInfoPrint;
	boolean toPrintInfo = false;
	private HashMap<Integer, String[]> infoScore=new HashMap<Integer, String[]>();
	
	private BufferedImage imgFruit2 = null;
	private 	BufferedImage imgFruit = null;
	private BufferedImage roobetImg1 = null;
	private	BufferedImage roobetImg2 = null;
	private BufferedImage roobetImg3 = null;
	

	
	public void setLevelMax() {
		Game_Server.login(id2);
		game_service game = Game_Server.getServer(0);
		String s=game.toString();
		levelMax="you in level:"+s.substring(s.lastIndexOf("user")+12,s.lastIndexOf("robots")-2);

	}

	public void setGrafh() {
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

	public WindowMange() {
		initGUI();
		setImg();

	}


	private void setImg() {
		roobetImg1 = null;
		try {
			roobetImg1 = ImageIO.read(new File("data\\jon.jpg"));
		} catch (IOException e) {	
		}
		
		roobetImg2 = null;
		try {
			roobetImg2 = ImageIO.read(new File("data\\tiron2.png"));
		} catch (IOException e) {
		}
		
		roobetImg3 = null;
		try {
			roobetImg3 = ImageIO.read(new File("data\\dianeriz.png"));
		} catch (IOException e) {	
		}

		imgFruit = null;
		try {
			imgFruit = ImageIO.read(new File("data\\king3.png"));
		} catch (IOException e) {	
		}

		try {
			imgFruit2 = ImageIO.read(new File("data\\dragon1.png"));
		} catch (IOException e) {
		}		
	}
	
	public void setGame(game_service game) {
		this.game=game;
	}
	
	public void setRoobet(ArrayList<Nodedata> roobet) {
		this.roobet=roobet;
	}
	
	public void setG0Graph(DGraph p) {
		this.g0=p;
	}
	
	private void initGUI() {
		this.setSize(1300, 1200);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("id");
		panel.add(lblNewLabel, "cell 0 1,alignx trailing,aligny center");

		idField = new JPasswordField();
		idField.setColumns(13);
		panel.add(idField, "cell 1 1,alignx center");

		JLabel lblNewLabel_1 = new JLabel("level to play");
		panel.add(lblNewLabel_1, "cell 2 1,alignx center,aligny center");

		textField_1 = new JTextField();
		panel.add(textField_1, "cell 3 1,alignx left,aligny center");
		textField_1.setColumns(4);

		btnNewButton = new JButton("play to start");

		btnNewButton.addActionListener(this);
		panel.add(btnNewButton, "cell 4 1");


		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		Menu syso = new Menu();
		menuBar.add(syso);
		this.setMenuBar(menuBar);


		Menu game = new Menu("game");
		menuBar.add(game);
		this.setMenuBar(menuBar);

		Menu info = new Menu("Score and game information");
		menuBar.add(info);
		this.setMenuBar(menuBar);

		MenuItem item33;
		int b[]= {0,1,3,5,9,11,13,16,19,20,23};
		for (int i = 0; i < b.length; i++) {
			item33 = new MenuItem("info for level:"+b[i]);
			item33.addActionListener(this);
			info.add(item33);
		}


		MenuItem item1 = new MenuItem("savePic");
		item1.addActionListener(this);

		MenuItem item2 = new MenuItem("clearAll");
		item2.addActionListener(this);


		menu.add(item1);
		menu.add(item2);

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



	public void paintComponnet(Graphics g) {
		super.paint(g);
	}


	public void paint(Graphics g) {

		super.paint(g);

		if(toPrintInfo) {
			g.setColor(Color.ORANGE);
			setLevelMax();
			g.drawString(levelMax, 300, 140);

			g.drawString(numPlayTody, 300, 120);
			for (int i = 0,j=0; i < myBestScore.length; i++,j=j+25) {
				g.drawString(myBestScore[i], 50, 150+j);
			}
			g.drawString("in level:"+levelInfoPrint+" the 10 best score:", 790, 150);
			for (int i = 0,j=0; i < 10; i++,j=j+25) {
				g.drawString(infoScore.get(levelInfoPrint)[i],800, 175+j);
			}

			toPrintInfo=false;
		}

		if(game!=null&&!game.isRunning()) {
			results="result for graph:"+levelYouWantToPlayInt+":"+game.toString();
		}

		g.setColor(Color.RED);
		for (int i = 0; i < fruit.size(); i++) {
			if(fruit.get(i).getType()==-1) {
				g.drawImage(imgFruit, fruit.get(i).getPos().ix()-10,fruit.get(i).getPos().iy()+17, 35, 35, this);
				g.drawString(""+fruit.get(i).getValue(),fruit.get(i).getPos().ix()+10,fruit.get(i).getPos().iy()+17);
			}
			else {
				g.drawImage(imgFruit2, fruit.get(i).getPos().ix()-10,fruit.get(i).getPos().iy()+17, 35, 35, this);
				g.drawString(""+fruit.get(i).getValue(),fruit.get(i).getPos().ix()+10,fruit.get(i).getPos().iy()-17);
			}

		}
		g.setColor(Color.ORANGE);

		if(!results.equals(""))g.drawString(results, 99, 133);

		for (int i = 0; i < roobet.size(); i++) {
			if(i==0)	g.drawImage(roobetImg1, roobet.get(i).getLocation().ix()-10, roobet.get(i).getLocation().iy()+17, 50, 50, this);
			if(i==1)g.drawImage(roobetImg2, roobet.get(i).getLocation().ix()-10, roobet.get(i).getLocation().iy()+17,50,50, this);
			if(i==2)g.drawImage(roobetImg3, roobet.get(i).getLocation().ix()-10, roobet.get(i).getLocation().iy()+17,50,50, this);
		}


	}


	public void setId(){  
		String s="";
		for (int i = 0; i < id.length; i++) {
			s+=id[i];
		}
		id2= Integer.valueOf(s);            
	}
	public int  getId(){  
		return    id2;        
	}
	public int getLevelPlay(){
		return  levelYouWantToPlayInt;     
	}
	public void setPlay(){
		levelYouWantToPlayInt= Integer.valueOf(levelYouWantToPlay);     
	}
	@Override

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnNewButton){
			if (idField.getPassword().length >0){

				id = idField.getPassword().clone();   
			}
			levelYouWantToPlay = textField_1.getText();

			setId();
			setPlay();
			clean();
			results="";
			setGraphBeakgrond();
			//repaint();
		//	findAlgoAndRun();
		}

		String str = e.getActionCommand();

		if(str.indexOf("info for level:")==0){
			String s=str.substring(str.indexOf(":")+1, str.length());
			levelInfoPrint=Integer.valueOf(s);
			getInfo();
			toPrintInfo = true;
		}


		else if (str.equals("savePic")) {
			Graph_Algo a = new Graph_Algo();
			a.init(g0);
			save_paint("gamePic");
		}
		else	if (str.equals("clearAll")) {
			clean();
		}

		repaint();
	}


	private void getInfo() {
		GameResultsInformation a=new GameResultsInformation();
			//a.allUsers(id2);
		a.printLog(id2);
		//a.notSameId();
		int playTody=a.getNumPlayTody();
		HashMap<Integer, Integer> arrMyBest=a.getArrMyBest();
		HashMap<Integer, ArrayList<Integer[]>> arrAllBest=a.getArrAllBest();
		numPlayTody="youPlay:"+playTody+" in the game";
		int b[]= {0,1,3,5,9,11,13,16,19,20,23};

		for (int i = 0; i < b.length; i++) {
			myBestScore[i]="in level:"+b[i]+"- you best score:"+arrMyBest.get(b[i])+".\n";	 
		}
		for (int i = 0; i < b.length; i++) {

			String arrAll[]=new String[10];
			for (int j = 0; j < 10&&j<arrAllBest.get(b[i]).size(); j++) {
				int place=j+1;
				arrAll[j]="place:"+place+"- id:"+arrAllBest.get(b[i]).get(j)[0]+" score:"+arrAllBest.get(b[i]).get(j)[1]+" move:"+arrAllBest.get(b[i]).get(j)[2];
			}
			infoScore.put(b[i], arrAll);
		}
	}
	private void setGraphBeakgrond() {
		remove(panel);
		//remove(this);
		new JFrame();
		JLabel background=new JLabel(new ImageIcon("data\\imageGR"+levelYouWantToPlayInt+".jpg"));
		getContentPane().add(background);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		try {
			setBackground(new Color(0, 0, 0, 0));
		}
		catch (Exception e) {
			
		}
		
		setVisible(true);
	}
	private void clean() {
		if(game!=null)game.stopGame();
		
		fruit = new ArrayList<Fruit>();
		g0 = new DGraph();
		roobet =new ArrayList<Nodedata>();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
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


	public void save_paint(String a) {
		try {
			BufferedImage image = new BufferedImage(this.getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = image.createGraphics();
			this.paint(graphics2D);
			File outputfile = new File("ima"+a+".jpg");
			ImageIO.write(image, "jpg", outputfile);
			System.out.println("save_paint ok");

		} catch (Exception exception) {
			System.out.println("no image");

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
		if(Math.abs(g0.getNode(e.getSrc()).getLocation().y())-Math.abs(g0.getNode(e.getDest()).getLocation().y())>0) {
			if(f.getType()==1) return false;
		}
		else {
			if(f.getType()==-1) return false;
		}

		double distSrc=f.getPos().distance2D(g0.getNode(e.getSrc()).getLocation());
		double distDest=f.getPos().distance2D(g0.getNode(e.getDest()).getLocation());
		double distEdge=g0.getNode(e.getDest()).getLocation().distance2D(g0.getNode(e.getSrc()).getLocation());
		if(distDest+distSrc-distEdge >=-0.01&&distDest+distSrc-distEdge <=0.00001)return true;

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
public void findAlgoAndRun(){

	int numGraph=levelYouWantToPlayInt;
	if((numGraph>=0 &&numGraph<=10)||numGraph==12||numGraph==15||numGraph==18||numGraph==21) {
		AutomaticPlay play1 =new AutomaticPlay();
		play1.test1(this);
	}
	else if(numGraph==11||numGraph==17||numGraph==20||numGraph==23||numGraph==14) {
		AutomaticPlay3 play3 =new AutomaticPlay3();
		play3.test3(this);
	}
	else {
		AutomaticPlay2 play2 =new AutomaticPlay2();
		play2.test2(this);
	}


}
	public static void main(String[] args) {

		WindowMange window = new WindowMange();
		window.setVisible(true);


	}
}
