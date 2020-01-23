package dataStructure;


import java.util.Collection;
import java.util.HashMap;


import MyGameGUI.Point3D;


import java.io.*;


public class DGraph implements graph, Serializable{
	private HashMap<Integer, node_data>  dataNode= new HashMap<Integer, node_data>();//20
	private HashMap<Integer, HashMap<Integer,edge_data>>  edgedataNode= new  HashMap<Integer, HashMap<Integer,edge_data>>();//20
	private int sizeEdge=0;
	private  int MC=0;


	public  DGraph() {
		 HashMap<Integer, node_data>  dataNode= new HashMap<Integer, node_data>();//20
		 HashMap<Integer, HashMap<Integer,edge_data>>  edgedataNode= new  HashMap<Integer, HashMap<Integer,edge_data>>();//20
		int sizeEdge=0;
			  int MC=0;
	}
	public  DGraph(DGraph p) {
		this.dataNode= new HashMap<Integer, node_data>(p.getVHash());
		this.edgedataNode= new  HashMap<Integer, HashMap<Integer,edge_data>>(p.getEHash());//לשנותתתתת
		this.sizeEdge=p.sizeEdge;
		this.MC=p.getMC();
	}
	public  DGraph(graph p) {
		dataNode= new HashMap<Integer, node_data>(((DGraph) p).getVHash());
		edgedataNode= new  HashMap<Integer, HashMap<Integer,edge_data>>(((DGraph) p).getEHash());
		this.sizeEdge=p.edgeSize();
		this.MC=p.getMC();
	}
	@Override
	public node_data getNode(int key) {
		if(dataNode.get(key)==null )throw new RuntimeException("ERR the key  not Exists , got: key-"+key);
		return dataNode.get(key);
	}
	public void init(String s) {

		int index =s.indexOf("pos")+2;
		int count=0;
		double x=0;
		double y=0;
		double w=0;
		for (int i = index; i < s.length(); i++) {
			
			int key=0;
			String t="";
			while((s.charAt(i)>='0'&&s.charAt(i)<='9')||s.charAt(i)=='.'||s.charAt(i)=='-') {
				t+=s.charAt(i);
				i++;
			}
			if(!t.equals("")) {
				count++;
				if(count==1) x=Double.valueOf(t);
				if(count==2)y=Double.valueOf(t);
				if(count==3);
				if(count==4) {
					key=Integer.valueOf(t);
					this.addNode(new Nodedata(new Point3D(x, y), key));
					count=0;
					
				}
			}
		}
		count=0;
		int dest=0;
		 w=0;
		int src=0;
			for (int i = 0; i < s.length()&&s.charAt(i)!='N'; i++) {
				String t="";
				while((s.charAt(i)>='0'&&s.charAt(i)<='9')||s.charAt(i)=='.'||s.charAt(i)=='-') {
					t+=s.charAt(i);
					i++;
				}
				if(t.length()!=0) {
					count++;
					if(count==1) src=Integer.valueOf(t);
					if(count==2)w=Double.valueOf(t);
					if(count==3) {
						dest=Integer.valueOf(t);
						this.connect(src, dest, w);
						count=0;	
					}
				}


			}


		}
	
	

	@Override
	public edge_data getEdge(int src, int dest) {
		if(dataNode.get(src)==null ||dataNode.get(dest)==null )throw new RuntimeException("ERR the src or dest no Exists , got: src-"+src+" dest-"+dest);
		if(edgedataNode.get(src)==null)throw new RuntimeException("ERR the edge_data  not Exists , got: src-"+src);
		//if(edgedataNode.get(src).get(dest)==null)throw new RuntimeException("ERR the edge_data  not Exists , got: dest-"+dest);
		return edgedataNode.get(src).get(dest);

	}
	public Edgedata getEdgeE(int src, int dest) {
		if(dataNode.get(src)==null ||dataNode.get(dest)==null )throw new RuntimeException("ERR the src or dest no Exists , got: src-"+src+" dest-"+dest);
		if(edgedataNode.get(src)==null)throw new RuntimeException("ERR the edge_data  not Exists , got: src-"+src);
		//if(edgedataNode.get(src).get(dest)==null)throw new RuntimeException("ERR the edge_data  not Exists , got: dest-"+dest);
		return (Edgedata) edgedataNode.get(src).get(dest);

	}
	@Override
	public void addNode(node_data n) {
		Nodedata a=new Nodedata(n);
		MC++;
		if(dataNode.get(n.getKey())!=null &&!a.equals(dataNode.get(n.getKey())))MC++;
		dataNode.put(n.getKey(), n);
	}
	
	
	public void connect(int src, int dest, double w,Double f) {
		if(src==dest)throw new RuntimeException("ERR the src and dest need to different: got: src-"+src+" dest-"+dest);
		if (w<0)throw new RuntimeException("ERR the weight shold be positive, got: w-"+w);
		if(dataNode.get(src)==null ||dataNode.get(dest)==null )throw new RuntimeException("ERR the src or dest no Exists , got: src-"+src+" dest-"+dest);
		if(w>=999999999)w=999999998;
		edge_data a=new Edgedata(src, dest, w);
		if(edgedataNode.get(src)==null) {
			HashMap<Integer,edge_data> b= new HashMap<Integer,edge_data>();
			edgedataNode.put(src, b);
		}
		MC++;
		edgedataNode.get(src).put(dest, a);
		sizeEdge++;
	}
	@Override
	public void connect(int src, int dest, double w) {
		if(src==dest)throw new RuntimeException("ERR the src and dest need to different: got: src-"+src+" dest-"+dest);
		if (w<0)throw new RuntimeException("ERR the weight shold be positive, got: w-"+w);
		if(dataNode.get(src)==null ||dataNode.get(dest)==null )throw new RuntimeException("ERR the src or dest no Exists , got: src-"+src+" dest-"+dest);
		if(w>=999999999)w=999999998;
		edge_data a=new Edgedata(src, dest, w);
		if(edgedataNode.get(src)==null) {
			HashMap<Integer,edge_data> b= new HashMap<Integer,edge_data>();
			edgedataNode.put(src, b);
		}
		MC++;
		edgedataNode.get(src).put(dest, a);
		sizeEdge++;
	}

	@Override
	public Collection<node_data> getV() {
		return dataNode.values();
	}
	public HashMap<Integer, node_data> getVHash() {
		HashMap<Integer, node_data>  a= new HashMap<Integer, node_data>();//20
		for ( HashMap.Entry   node_id :  dataNode.entrySet() ) {
			Nodedata s=new Nodedata(this.dataNode.get(node_id.getKey()));
			a.put((Integer)node_id.getKey(), s);
		}
		return a;
	}
	@Override

	public Collection<edge_data> getE(int node_id) {
		if(dataNode.get(node_id)==null )throw new RuntimeException("ERR the node_id  not Exists , got: src-"+node_id);
		if(edgedataNode.get(node_id)==null)throw new RuntimeException("ERR the edge_data  not Exists , got: src-"+node_id);
		return edgedataNode.get(node_id).values();
	}
		public HashMap<Integer, HashMap<Integer,edge_data>>  getEHash() {
		HashMap<Integer, HashMap<Integer,edge_data>>  a= new  HashMap<Integer, HashMap<Integer,edge_data>>();//20
		for ( HashMap.Entry   node_id :  dataNode.entrySet() ) {
			if(edgedataNode.get(node_id.getKey())!=null) {
				HashMap<Integer,edge_data> c= new HashMap<Integer,edge_data>();
				for ( HashMap.Entry   entry1 :edgedataNode.get(node_id.getKey()).entrySet() ) {
					Edgedata s= new Edgedata(this.edgedataNode.get(node_id.getKey()).get(entry1.getKey()));
					c.put(this.edgedataNode.get(node_id.getKey()).get(entry1.getKey()).getDest(),s);
				}
				a.put((Integer) node_id.getKey(), c);
			}
		}
		return a;
	}
	@Override
	public node_data removeNode(int key) {
		if(dataNode.get(key)==null )throw new RuntimeException("ERR the key  not Exists , got: key-"+key);
		for ( HashMap.Entry   entry :  dataNode.entrySet() ) {
			if(edgedataNode.get(entry.getKey())!=null&&edgedataNode.get(entry.getKey()).get(key)!=null) {
				edgedataNode.get(entry.getKey()).remove(key);
				sizeEdge--;
				MC++;
			}
		}
		if(edgedataNode.get(key)!=null) {
			MC+=edgedataNode.get(key).size();
			sizeEdge-=edgedataNode.get(key).size();
			edgedataNode.remove(key);
		}
		MC++;
		return dataNode.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if(dataNode.get(src)==null ||dataNode.get(dest)==null )throw new RuntimeException("ERR the src or dest no Exists , got: src-"+src+" dest-"+dest);
		if(edgedataNode.get(src)==null||edgedataNode.get(src).get(dest)==null) throw new RuntimeException("ERR the src or dest no Exists , got: src-"+src+" dest-"+dest);
		sizeEdge--;
		MC++;
		return edgedataNode.get(src).remove(dest);
	}

	@Override
	public int nodeSize() {
		return dataNode.size();
	}

	@Override
	public int edgeSize() {
		return sizeEdge;
	}

	@Override
	public int getMC() {
		return MC;
	}
	public String toStringedgedataNode() {
		String a="edgedataNode[";
		for ( HashMap.Entry   entry :  dataNode.entrySet() ) {
			if(edgedataNode.get(entry.getKey())!=null)	for ( HashMap.Entry   entry1 :this.edgedataNode.get( entry.getKey()).entrySet() ) {
				if(edgedataNode.get(entry.getKey())!=null&&edgedataNode.get(entry.getKey()).get(entry1.getKey())!=null) 
					a+=entry.getKey()+"-"+entry1.getKey()+",";
			}
		}
		if(a.charAt(a.length()-1)=='[')a+=" ]";
		else	a=a.subSequence(0, a.length()-1)+"]";
		return a;
	}
	public String toStringDataNode() {
		String a="dataNode[";
		for ( HashMap.Entry   entry :  dataNode.entrySet() ) 	a+=entry.getKey()+",";
		if(dataNode.size()==0)a+=" ]";
		else a=a.subSequence(0, a.length()-1)+"]";
		return a;
	}

}

