package com.digitalsolutionarchitecture.bpmn.model.foundation;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;

public class FlowElement extends BaseElement {

	private String name;
	private FlowElementsContainer parent;
	
	public FlowElement(String id, FlowElementsContainer parent) {
		super(id);
		this.parent = parent;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public FlowElementsContainer getParent() {
		return parent;
	}
}
