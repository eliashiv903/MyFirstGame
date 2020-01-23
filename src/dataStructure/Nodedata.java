package dataStructure;
import java.io.*;

import MyGameGUI.Point3D;

public class Nodedata implements node_data, Serializable  {
private Point3D point;
private Integer key;
private double weight=999999998;
private int tag;
private String info;
public  Nodedata( ) {
}
public  Nodedata( node_data p) {
	this.weight=p.getWeight();
	this.tag=p.getTag();
	this.info=p.getInfo();
	this.key=p.getKey();
	this.point=new Point3D(p.getLocation().x(),p.getLocation().y());
	
}
public  Nodedata( Point3D point,Integer key) {
	this.key=key;
	this.point=point;
}
public void copy(node_data p) {
	this.weight=p.getWeight();
	this.tag=p.getTag();
	this.info=p.getInfo();
	this.key=p.getKey();
	this.point=new Point3D(p.getLocation().x(),p.getLocation().y());
}
public void setKey(Integer key) {
	this.key=key;
}
	@Override
	public int getKey() {
		
		return key;
	}

	@Override
	public Point3D getLocation() {
		return point;
	}

	@Override
	public void setLocation(Point3D p) {
		point=p;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double w) {
		if(w>=0)weight=w;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public void setInfo(String s) {
		info=s;
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void setTag(int t) {
		tag=t;
		
	}
	public boolean equals(Nodedata p) {
		return this.info.equals(p.info) &&this.tag==p.tag&&this.weight==p.weight &&this.key==p.key &&this.getLocation().equalsXY(p.getLocation());
		
	}

}
