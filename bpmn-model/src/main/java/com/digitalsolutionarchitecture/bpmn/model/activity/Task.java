package com.digitalsolutionarchitecture.bpmn.model.activity;

import com.digitalsolutionarchitecture.bpmn.model.collaboration.InteractionNode;
import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;

public abstract class Task extends Activity implements InteractionNode {
	
	public Task(String id, FlowElementsContainer parent) {
		super(id, parent);
	}
	
}
