package com.digitalsolutionarchitecture.bpmn.model.events;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;

public abstract class CatchEvent extends Event {

	public CatchEvent(String id, FlowElementsContainer parent) {
		super(id, parent);
	}

	
}
