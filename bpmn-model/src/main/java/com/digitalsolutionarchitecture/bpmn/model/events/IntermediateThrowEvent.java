package com.digitalsolutionarchitecture.bpmn.model.events;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;

public class IntermediateThrowEvent extends ThrowEvent {
	
	private Boolean isInterrupting;

	public IntermediateThrowEvent(String id, FlowElementsContainer parent) {
		super(id, parent);
	}
	
	public Boolean getIsInterrupting() {
		return isInterrupting;
	}

	public void setIsInterrupting(Boolean isInterrupting) {
		this.isInterrupting = isInterrupting;
	}
	
}
