package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import dataStructure.DGraph;
import dataStructure.Nodedata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms  {
	private  graph smart=new DGraph ();
	public  Graph_Algo(graph g) {
		smart=new DGraph(g);
	}
	public  Graph_Algo() {

	}
	
	public void init(graph g) {
		smart=(DGraph) g;
	}

	@Override
	public void init(String file_name) {
		try   {    
			FileInputStream file = new FileInputStream(file_name); 
			ObjectInputStream in = new ObjectInputStream(file); 
			this.smart = (DGraph)in.readObject(); 
			in.close(); 
			file.close(); 
			System.out.println("Object has been deserialized"); 
		} 
		catch(IOException ex)  { 
			System.out.println("IOException is caught"); 
		}  
		catch(ClassNotFoundException ex)   { 
			System.out.println("ClassNotFoundException is caught"); 
		} 
	}

	@Override
	public void save(String file_name) {
		try  {   
			file_name=file_name+".txt";
			FileOutputStream file = new FileOutputStream(file_name); 
			ObjectOutputStream out = new ObjectOutputStream(file); 
			out.writeObject(smart);
			out.close(); 
			file.close(); 
			System.out.println("Object has been serialized"); 
		}   
		catch(IOException ex)   { 
			System.out.println("IOException is caught"); 
		} 
	} 


	@Override
	public boolean isConnected() {
		HashMap<Integer, node_data>  dataNode= new HashMap<Integer, node_data>(((DGraph) smart).getVHash());
		HashMap<Integer, HashMap<Integer,edge_data>>  edgedataNode= new  HashMap<Integer, HashMap<Integer,edge_data>>(((DGraph) smart).getEHash());
		for ( HashMap.Entry   entry :  dataNode.entrySet() ) {
			if(edgedataNode.get(entry.getKey())==null)return false;
			boolean a= concat((int)entry.getKey(),dataNode,edgedataNode);
			if(!a)	return false;
			for ( HashMap.Entry   entry2 :  dataNode.entrySet() ) 		dataNode.get(entry2.getKey()).setTag(0);
		}
		return true;
	}

	private boolean concat(int entry, HashMap<Integer, node_data> dataNode, HashMap<Integer, HashMap<Integer, edge_data>> edgedataNode) {
		dataNode.get(entry).setTag(1);
		if( edgedataNode.get(entry)!=null) {
			for ( HashMap.Entry   entry1 :  edgedataNode.get(entry).entrySet() ) {
				if(dataNode.get(edgedataNode.get(entry).get(entry1.getKey()).getDest()).getTag()!=1)concat(edgedataNode.get(entry).get(entry1.getKey()).getDest(),dataNode,edgedataNode);
			}
		};
		for ( HashMap.Entry   entry2 :  dataNode.entrySet() ) 	if(dataNode.get(entry2.getKey()).getTag()!=1) return false;
		return true;
	}
	private boolean concatTsp(int entry, HashMap<Integer, node_data> dataNode, HashMap<Integer, HashMap<Integer, edge_data>> edgedataNode, List<Integer> targets) {
		dataNode.get(entry).setTag(1);
		if( edgedataNode.get(entry)!=null) {
			for ( HashMap.Entry   entry1 :  edgedataNode.get(entry).entrySet() ) {
				if(dataNode.get(edgedataNode.get(entry).get(entry1.getKey()).getDest()).getTag()!=1)concatTsp(edgedataNode.get(entry).get(entry1.getKey()).getDest(),dataNode,edgedataNode,targets);
			}
		}
		for (int i = 0;targets.contains(entry)&& i < targets.size(); i++) 	if(dataNode.get(targets.get(i)).getTag()!=1) return false;
		return true;
	}


	@Override
	public double shortestPathDist(int src, int dest) {
		HashMap<Integer, node_data>  dataNode= new HashMap<Integer, node_data>(((DGraph) smart).getVHash());
		HashMap<Integer, HashMap<Integer,edge_data>>  edgedataNode= new  HashMap<Integer, HashMap<Integer,edge_data>>(((DGraph) smart).getEHash());
		if(dataNode.get(src)==null ||dataNode.get(dest)==null  )throw new RuntimeException("ERR the src or dest no Exists , got: src-"+src+" dest-"+dest);
		dataNode.get(src).setWeight(0);
		double ans= shortestPathDist1( src,  dest,dataNode,edgedataNode,new ArrayList<node_data>(),-1);
		return ans;
	}

	private double shortestPathDist1(int src, int dest, HashMap<Integer, node_data> dataNode, HashMap<Integer, HashMap<Integer, edge_data>> edgedataNode, ArrayList<node_data> s, int i2) {
		dataNode.get(src).setTag(1);
		if(i2!=-1&&i2<s.size())s.remove(i2);
		double whiteSrc=dataNode.get(src).getWeight();
		if(edgedataNode.get(src)!=null)
			for ( HashMap.Entry   entry1 :  edgedataNode.get(src).entrySet() ) {
				if(dataNode.get(edgedataNode.get(src).get(entry1.getKey()).getDest()).getWeight()>edgedataNode.get(src).get(entry1.getKey()).getWeight()+whiteSrc) dataNode.get(edgedataNode.get(src).get(entry1.getKey()).getDest()).setWeight(edgedataNode.get(src).get(entry1.getKey()).getWeight()+whiteSrc);
				if(dataNode.get(edgedataNode.get(src).get(entry1.getKey()).getDest()).getTag()!=1)	s.add(dataNode.get(edgedataNode.get(src).get(entry1.getKey()).getDest()));
			}
		Collections.sort( s, new  node_data_Comperator());
		for (int i = 0; i < s.size(); i++) {
			if(dataNode.get(s.get(i).getKey()).getKey()==dest)return  dataNode.get(dest).getWeight();
			if(dataNode.get(s.get(i).getKey()).getTag()!=1)	shortestPathDist1(s.get(i).getKey(),dest,dataNode,edgedataNode,s,i);
		}
		return dataNode.get(dest).getWeight();
	}


	public List<node_data> shortestPath(int src, int dest) {
		HashMap<Integer, node_data>  dataNode= new HashMap<Integer, node_data>(((DGraph) smart).getVHash());
		HashMap<Integer, HashMap<Integer,edge_data>>  edgedataNode= new  HashMap<Integer, HashMap<Integer,edge_data>>(((DGraph) smart).getEHash());
		if(dataNode.get(src)==null ||dataNode.get(dest)==null  )throw new RuntimeException("ERR the src or dest no Exists , got: src-"+src+" dest-"+dest);
		dataNode.get(src).setWeight(0);
		ArrayList<Integer> ans= shortestPathDist3( src,  dest,new HashMap<Integer, ArrayList<Integer>>(),-1,0,dataNode,edgedataNode,new ArrayList<node_data>(),-1);
		List<node_data> ans1=new ArrayList<node_data>();
		if (ans==null)return ans1;
		for (int i = 0; i < ans.size(); i++) ans1.add(dataNode.get(ans.get(i)));
		return ans1;
	}

	private ArrayList<Integer>  shortestPathDist3(int src, int dest,HashMap<Integer, ArrayList<Integer>> go, int srcBeind,int return1, HashMap<Integer, node_data> dataNode, HashMap<Integer, HashMap<Integer, edge_data>> edgedataNode, ArrayList<node_data> s, int i2) {
		dataNode.get(src).setTag(1);
		if(i2!=-1&&i2<s.size())s.remove(i2);
		double whiteSrc=dataNode.get(src).getWeight();
		if(srcBeind==-1){
			ArrayList<Integer> way1=new ArrayList<Integer>();
			way1.add(src);
			go.put(src, way1);
		}
		if(edgedataNode.get(src)!=null)for ( HashMap.Entry   entry1 :  edgedataNode.get(src).entrySet() ) {
			if(dataNode.get(edgedataNode.get(src).get(entry1.getKey()).getDest()).getWeight()>edgedataNode.get(src).get(entry1.getKey()).getWeight()+whiteSrc) {
				go.put(edgedataNode.get(src).get(entry1.getKey()).getDest(),  new ArrayList<Integer>(go.get(src)));
				go.get(edgedataNode.get(src).get(entry1.getKey()).getDest()).add(edgedataNode.get(src).get(entry1.getKey()).getDest());
				dataNode.get(edgedataNode.get(src).get(entry1.getKey()).getDest()).setWeight(edgedataNode.get(src).get(entry1.getKey()).getWeight()+whiteSrc);				}
			s.add(dataNode.get(edgedataNode.get(src).get(entry1.getKey()).getDest()));
		}
		Collections.sort( s, new  node_data_Comperator());
		for (int i = 0; i < s.size(); i++) {
			if(dataNode.get(s.get(i).getKey()).getKey()==dest)return  go.get(dest);
			if(dataNode.get(s.get(i).getKey()).getTag()!=1) shortestPathDist3(s.get(i).getKey(),dest,go,src,return1,dataNode,edgedataNode,s,i);
		}
		return go.get(dest);
	}

	public List<node_data> TSP(List<Integer> targets) {
		if(targets.size()==0)throw new RuntimeException("ERR list dont shold be empty");
		HashMap<Integer, node_data>  dataNode= new HashMap<Integer, node_data>(((DGraph) smart).getVHash());
		HashMap<Integer, HashMap<Integer,edge_data>>  edgedataNode= new  HashMap<Integer, HashMap<Integer,edge_data>>(((DGraph) smart).getEHash());
		for (int i = 0; i < targets.size(); i++) 	if(dataNode.get(targets.get(i))==null  )throw new RuntimeException("ERR all the node in the list need to be Exists, got node: "+targets.get(i));
		for ( HashMap.Entry   entry :  dataNode.entrySet() ) {
			boolean a= concatTsp((int)entry.getKey(),dataNode,edgedataNode,targets);
			if(!a)	return new ArrayList<node_data>();
			for ( HashMap.Entry   entry2 :  dataNode.entrySet() ) 		dataNode.get(entry2.getKey()).setTag(0);
		}
		return sort(targets,edgedataNode);
	}

	public List<node_data> sort(List<Integer> targets,HashMap<Integer, HashMap<Integer, edge_data>> edgedataNode) {
		List<node_data> ans1=new ArrayList<node_data>();
		List<node_data> ans=new ArrayList<node_data>();
		for (int i = 0; i < targets.size(); i++) ans.add(smart.getNode(targets.get(i)));
		node_data w=new Nodedata();
		while(ans.size()!=0) {
			w=new Nodedata(ans.get(0));
			ans.remove(0);
			if(ans.size()>0)sortLitel(ans,w);
			for (int i = 0; i < ans.size(); i++) {
				if(ans.size()>0&&shortestPath(w.getKey(), ans.get(i).getKey())!=null) {
					if(ans.size()>0)  ans1.addAll(shortestPath(w.getKey(), ans.get(i).getKey()));
					if(ans.size()!=1 &&ans.size()!=0 )ans1.remove(ans1.size()-1);
					break;
				}
				else return new ArrayList<node_data>();//???chnge to null
			}
		}
		return ans1;
	}

	private void sortLitel(List<node_data> ans, node_data w) {
		double a=0;
		double lital=999999999.9;
		int j=0;
		Nodedata b=new Nodedata();
		for (int i = 0; i < ans.size(); i++) {
			double c=shortestPathDist(w.getKey(),ans.get(i).getKey());
			if(c<lital) {
				lital=c;
				b=new Nodedata(ans.get(i));
				j=i;
			}
		}
		ans.remove(j);
		if(ans.size()>0) {	
			ans.add(ans.get(0));
			ans.remove(0);
		}
		ans.add(0, b);
	}
	@Override
	public graph copy() {
		return new DGraph(smart) ;
	}
	
}
