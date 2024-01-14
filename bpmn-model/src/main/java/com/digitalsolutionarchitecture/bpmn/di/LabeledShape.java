package com.digitalsolutionarchitecture.bpmn.di;

public class LabeledShape extends DiagramElement {

	public LabeledShape(String id) {
		super(id);
	}
	
	private Bounds bounds;
	
	public Bounds getBounds() {
		return bounds;
	}
	
	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}
	
}
