package com.digitalsolutionarchitecture.bpmn.model.foundation;

import java.util.ArrayList;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.layout.BpmnShapeLayouts;

public abstract class FlowNode extends FlowElement {

	private List<SequenceFlow> incoming = new ArrayList<>();
	private List<SequenceFlow> outgoing = new ArrayList<>();
	
	private BpmnShapeLayouts layouts = new BpmnShapeLayouts(this);
	
	public FlowNode(String id, FlowElementsContainer parent) {
		super(id, parent);
	}

	public List<SequenceFlow> getIncoming() {
		return incoming;
	}
	
	public List<SequenceFlow> getOutgoing() {
		return outgoing;
	}
	
	public BpmnShapeLayouts getLayouts() {
		return layouts;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "@" + getId();
	}
}
