package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import MyGameGUI.Point3D;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.Nodedata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;


public class Graph_algoAndDGrphTesters {
	

	@SuppressWarnings("deprecation")
	@Test

	public void testErrInputGraph_algorithms() {
		int i=0;
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),1));
			graph_algorithms a=new Graph_Algo(q2);

			a.shortestPathDist(1, 2);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 1);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),2));
			graph_algorithms a=new Graph_Algo(q2);
			a.shortestPathDist(1, 2);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 2);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),2));
			q2.addNode(new Nodedata(new Point3D(35, 165),3));
			q2.connect(2, 3, 6.7);
			graph_algorithms a=new Graph_Algo(q2);
			if(a.shortestPath(3, 2).size()==0)throw new RuntimeException("ERR" );
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 3);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),2));
			q2.addNode(new Nodedata(new Point3D(35, 165),3));
			q2.connect(2, 3, 6.7);
			graph_algorithms a=new Graph_Algo(q2);
			a.shortestPath(3,5);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 4);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),2));
			q2.addNode(new Nodedata(new Point3D(35, 3),3));
			q2.addNode(new Nodedata(new Point3D(35, 700),7));
			q2.connect(2, 3, 6.7);
			graph_algorithms a=new Graph_Algo(q2);
			ArrayList<Integer> nodes =new ArrayList<Integer>();
			nodes.add(2);
			nodes.add(7);
			nodes.add(9);
			a.TSP(nodes);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 5);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),2));
			q2.addNode(new Nodedata(new Point3D(35, 3),3));
			q2.addNode(new Nodedata(new Point3D(35, 700),7));
			q2.connect(3, 7, 4.7);
			q2.connect(2, 2, 6.7);
			graph_algorithms a=new Graph_Algo(q2);
			ArrayList<Integer> nodes =new ArrayList<Integer>();
			nodes.add(2);
			nodes.add(7);
			nodes.add(9);
			a.TSP(nodes);	
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 6);

	}

	public void testErrInputGraph() {

		int i=0;
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),1));
			q2.getNode(4);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 1);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),1));	
			q2.addNode(new Nodedata(new Point3D(35, 43),4));	
			q2.connect(1, 4 ,34);
			q2.getEdge(1, 7);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 2);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),1));	
			q2.addNode(new Nodedata(new Point3D(35, 43),7));	
			q2.getEdge(1, 7);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 3);
		try{
			graph q2=new DGraph();	
			q2.getEdge(1, 7);	
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 4);
		try{
			graph q2=new DGraph();
			q2.connect(1, 3, 2);			}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 5);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),1));	

			q2.connect(1, 3, 2);	
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 6);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),3));	
			q2.connect(1, 3, 2);	
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 7);

		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),3));	
			q2.getE(3)	;
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 8);
		try{
			graph q2=new DGraph();
			q2.getE(3)	;		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 9);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),3));
			q2.removeNode(1);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 10);

		try{
			graph q2=new DGraph();
			q2.removeNode(1);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 11);

		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),3));
			q2.addNode(new Nodedata(new Point3D(56, 165),12));
			q2.removeEdge(3, 12);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 12);
		try{
			graph q2=new DGraph();
			q2.addNode(new Nodedata(new Point3D(35, 165),3));
			q2.addNode(new Nodedata(new Point3D(56, 165),12));
			q2.connect(3, 12, 567);
			q2.removeEdge(3, 5);
		}
		catch(Exception f) {
			i++;
		}
		assertEquals(i, 13);

	}
	
	@SuppressWarnings("deprecation")

	@Test
	void testGraph_AlgoAndDGraphAndAll() {

		Graph_Algo a=new Graph_Algo();
		DGraph q2=new DGraph();
		Nodedata b1=new Nodedata(new Point3D(35, 165),1);
		Nodedata b2=new Nodedata(new Point3D(575, 176),2);
		Nodedata b3=new Nodedata(new Point3D(41, 580),3);
		Nodedata b4=new Nodedata(new Point3D(566, 579),4);
		Nodedata b5=new Nodedata(new Point3D(230, 200),5);
		Nodedata b6=new Nodedata(new Point3D(143,230),6);
		Nodedata b7=new Nodedata(new Point3D(80, 300),7);
		Nodedata b8=new Nodedata(new Point3D(200, 400),8);
		Nodedata b9=new Nodedata(new Point3D(400, 370),9);
		Nodedata b10=new Nodedata(new Point3D(234, 415),10);
		Nodedata b11=new Nodedata(new Point3D(500,220),11);
		Nodedata b12=new Nodedata(new Point3D(280, 300),12);
		Nodedata b13=new Nodedata(new Point3D(180, 230),13);
		Nodedata b14=new Nodedata(new Point3D(480, 500),14);
		Nodedata b15=new Nodedata(new Point3D(92, 210),15);
		q2.addNode(b1);
		q2.addNode(b2);
		q2.addNode(b3); 
		q2.addNode(b4);
		q2.addNode(b5);
		q2.addNode(b6);
		q2.addNode(b7);
		q2.addNode(b8);
		q2.addNode(b9);
		q2.addNode(b10);
		q2.addNode(b11);
		q2.addNode(b12);
		q2.addNode(b13);
		q2.addNode(b14);
		q2.addNode(b15);
		double w=30;
		//q2.connect(b1.getKey(), b2.getKey(), w);
		q2.connect(b1.getKey(), b2.getKey(), 10);
		q2.connect(b2.getKey(), b3.getKey(), 100);
		q2.connect(b3.getKey(), b4.getKey(),20);
		q2.connect(b4.getKey(), b5.getKey(), 70);
		q2.connect(b5.getKey(), b6.getKey(), 30);
		q2.connect(b6.getKey(), b7.getKey(), 53);
		q2.connect(b7.getKey(), b8.getKey(), 30);
		q2.connect(b8.getKey(),b9.getKey(), 289.98); 
		q2.connect(b9.getKey(),b10.getKey(), w); 
		q2.connect(b10.getKey(),b11.getKey(), 180); 
		q2.connect(b11.getKey(),b12.getKey(), 180); 
		q2.connect(b12.getKey(), b13.getKey(), 35);
		q2.connect(b14.getKey(),b15.getKey(), 400); 
		q2.connect(b15.getKey(),b1.getKey(), 20); 
		q2.connect(b2.getKey(),b7.getKey(), w);
		q2.connect(b9.getKey(),b10.getKey(), 32); 
		q2.connect(b13.getKey(), b11.getKey(), w);
		q2.connect(b1.getKey(),b12.getKey(), w); 
		q2.connect(b13.getKey(),b1.getKey(), w); 
		q2.connect(b13.getKey(),b1.getKey(), 156); 
		q2.connect(b1.getKey(),b14.getKey(), w); 
		q2.connect(b12.getKey(),b11.getKey(), w); 
		q2.connect(b1.getKey(),b13.getKey(), w); 
		q2.connect(b14.getKey(),b3.getKey(), 730); 
		q2.connect(b3.getKey(),b15.getKey(), 900);
		q2.connect(b1.getKey(),b3.getKey(), w);
		q2.connect(b4.getKey(),b15.getKey(), 540);
		q2.connect(b14.getKey(),b1.getKey(), 78); 
		q2.connect(b12.getKey(),b14.getKey(), 93.5); 
		q2.connect(b13.getKey(),b2.getKey(), 23.56); 
		//	q2.connect(b13.getKey(),b13.getKey(), 23.56);
		a.init(q2);
		assertEquals(a.shortestPathDist(3, 15), 560);
		assertEquals(a.shortestPathDist(1, 14), 30);
		assertEquals(a.shortestPathDist(9, 10), 32);
		assertEquals(a.shortestPathDist(10, 11), 180);
		assertEquals(a.shortestPathDist(2, 8), 60);
		assertEquals(a.shortestPathDist(15, 4), 70);


		//	System.out.println(a.shortestPathDist(70, b1.getKey())+"hbbh");
		List<node_data>  x=new ArrayList<node_data> ();
		List<Integer>  xInt=new ArrayList<Integer> ();
		List<node_data>  y=new ArrayList<node_data> ();
		y=a.shortestPath(1, 6);
		List<Integer>  yInt=new ArrayList<Integer> ();
		for (int i = 0; i <y.size(); i++) yInt.add(y.get(i).getKey());
		List<Integer>  yAns=new ArrayList<Integer> ();
		yAns.add(1);
		yAns.add(3);
		yAns.add(4);
		yAns.add(5);
		yAns.add(6);
		assertEquals(yInt, yAns);
		y=a.shortestPath(4, 15);
		yInt=new ArrayList<Integer> ();
		for (int i = 0; i <y.size(); i++) 	yInt.add(y.get(i).getKey());
		yAns=new ArrayList<Integer> ();
		yAns.add(4);
		yAns.add(15);
		assertEquals(yInt, yAns);
		y=a.shortestPath(9, 2);
		yInt=new ArrayList<Integer> ();
		for (int i = 0; i <y.size(); i++) 	yInt.add(y.get(i).getKey());
		yAns=new ArrayList<Integer> ();
		yAns.add(9);
		yAns.add(10);
		yAns.add(11);
		yAns.add(12);
		yAns.add(13);
		yAns.add(2);
		assertEquals(yInt, yAns);
		List<Integer>  e=new ArrayList<Integer> ();
		e.add(1);
		e.add(3);
		e.add(15);
		y=a.TSP(e);
		yInt=new ArrayList<Integer> ();
		for (int i = 0; i <y.size(); i++) 	yInt.add(y.get(i).getKey());
		yAns=new ArrayList<Integer> ();
		yAns.add(1);
		yAns.add(3);
		yAns.add(4);
		yAns.add(15);
		assertEquals(yInt, yAns);
		e=new ArrayList<Integer> ();
		e.add(2);
		e.add(6);
		e.add(1);
		e.add(4);
		e.add(11);
		y=a.TSP(e);
		yInt=new ArrayList<Integer> ();
		for (int i = 0; i <y.size(); i++) 	yInt.add(y.get(i).getKey());
		yAns=new ArrayList<Integer> ();
		yAns.add(2);
		yAns.add(3);
		yAns.add(4);
		yAns.add(5);
		yAns.add(6);
		yAns.add(7);
		yAns.add(8);
		yAns.add(9);
		yAns.add(10);
		yAns.add(11);
		yAns.add(12);
		yAns.add(14);
		yAns.add(1);
		assertEquals(yInt, yAns);
		/// remove and isConnected
		assertEquals(a.isConnected(), true);
		assertEquals(q2.removeEdge(15, 1).getSrc(), 15);
		assertEquals(a.isConnected(), false);
		q2.connect(15, 1, 22);
		assertEquals(a.isConnected(), true);
		assertEquals(q2.removeNode(1).getKey(), 1);
		assertEquals(a.isConnected(), false);
		/// deep copy algo
		DGraph q3=new DGraph(a.copy());  //copy
		Graph_Algo b=new Graph_Algo();
		b.init(q3);
		assertEquals(b.isConnected(), false);
		q3.connect(15, 2, 22);
		assertEquals(b.isConnected(), true);
		assertEquals(a.isConnected(), false);
		/// graph getV && getE
		ArrayList<node_data> f=new ArrayList<node_data>(q3.getV());
		assertEquals(f.get(0).getKey(),2);
		ArrayList<edge_data> f1=new ArrayList<edge_data>(q3.getE(f.get(0).getKey()));
		assertEquals(f1.get(0).getDest(),3);
		/// test edgeSize && nodeSize && getMC
		assertEquals(q3.edgeSize(),23);
		assertEquals(q3.nodeSize(),14);
		assertEquals(q3.getMC(),57);
		/// getNode   getEdge
		assertEquals(q3.getNode(2).getLocation(),new Point3D(575, 176));
		assertEquals(q3.getEdge(2, 3).getWeight(),100);



	}

}
