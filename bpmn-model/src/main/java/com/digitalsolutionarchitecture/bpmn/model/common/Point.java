package com.digitalsolutionarchitecture.bpmn.model.common;

public class Point {
	private double x;
	private double y;
	
	public Point() {
	}
	
	public Point(double x, double y) {
		setX(x);
		setY(y);
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(this.getClass() != o.getClass()) return false;
		
		Point other = (Point)o;
		return
			other.getX() == this.getX()
			&&
			other.getY() == this.getY()
		;
	}
	
	@Override
	public int hashCode() {
		return (int)x ^ (int)y;
	}
}
