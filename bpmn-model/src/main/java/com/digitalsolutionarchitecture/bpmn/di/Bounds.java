package com.digitalsolutionarchitecture.bpmn.di;

public class Bounds {

	private double x;
	private double y;
	private double width;
	private double height;

	public Bounds() {
	}

	public Bounds(double x, double y, double width, double height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getBottom() {
		return getY() + getHeight();
	}

	public double getRight() {
		return getX() + getWidth();
	}

	public double getMiddle() {
		return getY() + getHeight() / 2;
	}

	public boolean isAbove(Bounds bounds) {
		return getBottom() < bounds.getY();
	}
	
	public boolean isBelow(Bounds bounds) {
		return getY() > bounds.getBottom();
	}

	public static Bounds withSmallerWidth(Bounds b1, Bounds b2) {
		return b1.getWidth() <= b2.getWidth() ? b1 : b2;
	}
	
	@Override
	public Bounds clone() {
		return new Bounds(x, y, width, height);
	}
}
