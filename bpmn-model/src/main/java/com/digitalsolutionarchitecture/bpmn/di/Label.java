package com.digitalsolutionarchitecture.bpmn.di;

public class Label extends DiagramElement {

	private Bounds bounds;
	
	public Label(String id) {
		super(id);
	}
	
	public Bounds getBounds() {
		return bounds;
	}
	
	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}
}
