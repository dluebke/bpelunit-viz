package com.digitalsolutionarchitecture.bpmn.model.events;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;

public class StartEvent extends CatchEvent {
	
	public StartEvent(String id, FlowElementsContainer parent) {
		super(id, parent);
	}

	private Boolean isInterrupting;

	public Boolean getIsInterrupting() {
		return isInterrupting;
	}

	public void setIsInterrupting(Boolean isInterrupting) {
		this.isInterrupting = isInterrupting;
	}
	
}
