package com.digitalsolutionarchitecture.bpmn.model.activity;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;

public class ServiceTask extends Task {

	private String implementation;

	public ServiceTask(String id, FlowElementsContainer parent) {
		super(id, parent);
	}
	
	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}
	
}
