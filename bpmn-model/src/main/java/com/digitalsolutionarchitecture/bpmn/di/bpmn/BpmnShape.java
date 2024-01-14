package com.digitalsolutionarchitecture.bpmn.di.bpmn;

import com.digitalsolutionarchitecture.bpmn.di.LabeledShape;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;

public class BpmnShape extends LabeledShape {

	private BaseElement bpmnElement;
	private Boolean isHorizontal;
	private Boolean isExpanded;
	private Boolean isMarkerVisible;
	private Boolean isMessageVisible;
	private BpmnLabel label;
	private Color borderColor;
	private Color backgroundColor;

	public BpmnShape(String id) {
		super(id);
	}
	
	public BaseElement getBpmnElement() {
		return bpmnElement;
	}

	public void setBpmnElement(BaseElement bpmnElement) {
		this.bpmnElement = bpmnElement;
	}

	public Boolean getIsHorizontal() {
		return isHorizontal;
	}

	public void setIsHorizontal(Boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}

	public Boolean getIsExpanded() {
		return isExpanded;
	}

	public void setIsExpanded(Boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public Boolean getIsMarkerVisible() {
		return isMarkerVisible;
	}

	public void setIsMarkerVisible(Boolean isMarkerVisible) {
		this.isMarkerVisible = isMarkerVisible;
	}

	public Boolean getIsMessageVisible() {
		return isMessageVisible;
	}

	public void setIsMessageVisible(Boolean isMessageVisible) {
		this.isMessageVisible = isMessageVisible;
	}

	public BpmnLabel getLabel() {
		return label;
	}
	
	public void setLabel(BpmnLabel label) {
		this.label = label;
	}

	public Color getBorderColor() {
		return this.borderColor;
	}
	
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
}
