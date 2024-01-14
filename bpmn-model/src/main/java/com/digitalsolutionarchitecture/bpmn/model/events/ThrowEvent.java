package com.digitalsolutionarchitecture.bpmn.model.events;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;

public abstract class ThrowEvent extends Event {
	
	public ThrowEvent(String id, FlowElementsContainer parent) {
		super(id, parent);
	}
	
}
