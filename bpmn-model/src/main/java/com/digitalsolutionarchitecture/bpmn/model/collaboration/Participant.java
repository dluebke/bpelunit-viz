package com.digitalsolutionarchitecture.bpmn.model.collaboration;

import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;
import com.digitalsolutionarchitecture.bpmn.model.layout.BpmnShapeLayouts;
import com.digitalsolutionarchitecture.bpmn.model.process.Process;

public class Participant extends BaseElement implements InteractionNode {
	
	private String name;
	private Process process;
	private BpmnShapeLayouts layouts = new BpmnShapeLayouts(this);
	
	public Participant(String id) {
		this(id, null);
	}
	
	public Participant(String id, String name) {
		super(id);
		setName(name);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
	
	public Process getProcess() {
		return process;
	}
	
	@Override
	public String toString() {
		return "Participant " + getId() + ": " + getName();
	}
	
	public BpmnShapeLayouts getLayouts() {
		return layouts;
	}
}
