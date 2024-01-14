package com.digitalsolutionarchitecture.bpmn.model.process;

import java.util.ArrayList;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.common.FlowElementsContainer;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.foundation.RootElement;

public class Process extends RootElement implements FlowElementsContainer {

	// private ProcessType processType;
	private boolean isClosed;
	private boolean isExecutable;
	private List<FlowElement> flowElements = new ArrayList<>();
	private LaneSet laneSet;
	
	public Process(String id) {
		super(id);
	}
	
	public boolean isClosed() {
		return isClosed;
	}
	
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	public boolean isExecutable() {
		return isExecutable;
	}
	
	public void setExecutable(boolean isExecutable) {
		this.isExecutable = isExecutable;
	}

	@Override
	public List<FlowElement> getFlowElements() {
		return flowElements;
	}

	@Override
	public LaneSet getLaneSet() {
		return laneSet;
	}
	
	@Override
	public void setLaneSet(LaneSet laneSet) {
		this.laneSet = laneSet;
	}
}
