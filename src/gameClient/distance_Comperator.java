package gameClient;
import java.util.Comparator;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Edgedata;
public class distance_Comperator implements Comparator<Edgedata> {

private int n;
private DGraph g0;

	public distance_Comperator(int n,DGraph g0) {
		this.n=n;
		this.g0=g0;
		;}
	public  int compare(Edgedata o1, Edgedata o2) {
		Graph_Algo a=new Graph_Algo (g0);
				return Double.compare(a.shortestPathDist(n, o1.getSrc()), a.shortestPathDist(n, o2.getSrc()));
	}


}