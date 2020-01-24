package gameClient;

import MyGameGUI.Point3D;


/**
 *Saves the data on cows conveniently:
 * the value of the fruit, the type, the location,
 *  and on which edge it can be eaten
 * 
 */
public class Fruit {
	private double value=0;
	private int type=0;
	private Point3D pos;
	private int src=-1;
	private int dest=-1;
	public Fruit() {
	}

	public Fruit(double value,int type ,Point3D pos) {
		this.value=value;
		this.type=type;
		this.pos=pos;
	}
	public void setLocation(Point3D p) {
		pos=p;
	}
	
	public void setDestAndSrc(int src,int dest) {
		this.src=src;
		this.dest=dest;
	}
	public double getValue() {
		return value;
	}
	public int getSrc() {
		return src;
	}
	public int getDest() {
		return dest;
	}
	public int getType() {
		return type;
	}
	public Point3D getPos() {
		return pos;
	}
}
