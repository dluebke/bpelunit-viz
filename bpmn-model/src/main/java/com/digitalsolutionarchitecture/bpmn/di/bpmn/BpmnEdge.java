package com.digitalsolutionarchitecture.bpmn.di.bpmn;

import com.digitalsolutionarchitecture.bpmn.di.DiagramElement;
import com.digitalsolutionarchitecture.bpmn.di.LabeledEdge;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;

public class BpmnEdge extends LabeledEdge {

	private DiagramElement sourceElement;
	private DiagramElement targetElement;
	private BpmnLabel label;
	private BaseElement bpmnElement;
	
	public BpmnEdge(String id) {
		super(id);
	}
	
	public DiagramElement getSourceElement() {
		return sourceElement;
	}

	public void setSourceElement(DiagramElement sourceElement) {
		this.sourceElement = sourceElement;
	}

	public DiagramElement getTargetElement() {
		return targetElement;
	}

	public void setTargetElement(DiagramElement targetElement) {
		this.targetElement = targetElement;
	}

	public BpmnLabel getLabel() {
		return label;
	}

	public void setLabel(BpmnLabel label) {
		this.label = label;
	}

	public BaseElement getBpmnElement() {
		return bpmnElement;
	}

	public void setBpmnElement(BaseElement bpmnElement) {
		this.bpmnElement = bpmnElement;
	}


	
}
