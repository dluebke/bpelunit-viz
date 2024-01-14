package com.digitalsolutionarchitecture.bpmn.model.common;

import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowNode;
import com.digitalsolutionarchitecture.bpmn.model.layout.BpmnEdgeLayouts;

public class SequenceFlow extends FlowElement {

	private boolean isImmediate;
	private FlowNode source;
	private FlowNode target;
	private BpmnEdgeLayouts layouts = new BpmnEdgeLayouts(this);
	
	public SequenceFlow(String id, FlowElementsContainer parent) {
		super(id, parent);
	}
	
	public SequenceFlow(String id, FlowNode source, FlowNode target, FlowElementsContainer parent) {
		super(id, parent);
		setSource(source);
		setTarget(target);
	}
	
	public boolean isImmediate() {
		return isImmediate;
	}
	
	public void setImmediate(boolean isImmediate) {
		this.isImmediate = isImmediate;
	}
	
	public void setSource(FlowNode source) {
		if(this.source != null) {
			this.source.getOutgoing().remove(this);
		}
		this.source = source;
		this.source.getOutgoing().add(this);
	}
	
	public FlowNode getSource() {
		return source;
	}
	
	public void setTarget(FlowNode target) {
		if(this.target != null) {
			this.target.getIncoming().remove(this);
		}
		this.target = target;
		this.target.getIncoming().add(this);
	}
	
	public FlowNode getTarget() {
		return target;
	}
	
	public BpmnEdgeLayouts getLayouts() {
		return layouts;
	}

}
