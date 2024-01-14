package com.digitalsolutionarchitecture.bpmn.model.common;

import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.process.LaneSet;

public interface FlowElementsContainer {

	public List<FlowElement> getFlowElements();
	
	public void setLaneSet(LaneSet ls);
	public LaneSet getLaneSet();
	
}
