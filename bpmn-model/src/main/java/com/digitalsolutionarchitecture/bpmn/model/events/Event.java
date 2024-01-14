package com.digitalsolutionarchitecture.bpmn.model.events;

import com.digitalsolutionarchitecture.bpmn.model.collaboration.InteractionNode;
import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowNode;

public abstract class Event extends FlowNode implements InteractionNode {

	private EventDefinition eventDefinition;
	
	public Event(String id, FlowElementsContainer parent) {
		super(id, parent);
	}
	
	public EventDefinition getEventDefinition() {
		return eventDefinition;
	}
	
	public void setEventDefinition(EventDefinition eventDefinition) {
		this.eventDefinition = eventDefinition;
	}
}
