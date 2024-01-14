package com.digitalsolutionarchitecture.bpmn.model.layout;

import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnEdge;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;

public class BpmnEdgeLayouts extends Layouts<BpmnEdge> {

	private BaseElement bpmnElement;

	public BpmnEdgeLayouts(BaseElement bpmnElement) {
		this.bpmnElement = bpmnElement;
	}

	@Override
	protected BpmnEdge createNewDiagramElement() {
		BpmnEdge result = new BpmnEdge("EDGE-" + bpmnElement.getId());
		result.setBpmnElement(bpmnElement);
		return result;
	}

}