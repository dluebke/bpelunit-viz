package com.digitalsolutionarchitecture.bpmn.model.layout;

import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnShape;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;

public class BpmnShapeLayouts extends Layouts<BpmnShape> {

	private BaseElement bpmnElement;

	public BpmnShapeLayouts(BaseElement flowNode) {
		this.bpmnElement = flowNode;
	}

	@Override
	protected BpmnShape createNewDiagramElement() {
		BpmnShape result = new BpmnShape("SHAPE-" + bpmnElement.getId());
		result.setBpmnElement(bpmnElement);
		return result;
	}

}
