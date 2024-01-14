package com.digitalsolutionarchitecture.bpmn.model.activity;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowNode;

public abstract class Activity extends FlowNode {

	private Boolean isForCompensation;
	private Integer startQuantaty;
	private Integer completionQuantity;

	public Activity(String id, FlowElementsContainer parent) {
		super(id, parent);
	}
	
	public Boolean isForCompensation() {
		return isForCompensation;
	}

	public void setForCompensation(Boolean isForCompensation) {
		this.isForCompensation = isForCompensation;
	}

	public Integer getStartQuantaty() {
		return startQuantaty;
	}

	public void setStartQuantaty(Integer startQuantaty) {
		this.startQuantaty = startQuantaty;
	}

	public Integer getCompletionQuantity() {
		return completionQuantity;
	}

	public void setCompletionQuantity(Integer completionQuantaty) {
		this.completionQuantity = completionQuantaty;
	}
	
}
