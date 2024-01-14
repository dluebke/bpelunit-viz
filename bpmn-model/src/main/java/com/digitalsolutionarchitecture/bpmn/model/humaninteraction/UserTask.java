package com.digitalsolutionarchitecture.bpmn.model.humaninteraction;

import com.digitalsolutionarchitecture.bpmn.model.activity.Task;
import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;

public class UserTask extends Task {

	private String implementation;

	public UserTask(String id, FlowElementsContainer parent) {
		super(id, parent);
	}

	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}
	
}
