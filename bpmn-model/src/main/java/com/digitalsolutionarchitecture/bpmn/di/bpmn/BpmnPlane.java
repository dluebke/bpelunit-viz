package com.digitalsolutionarchitecture.bpmn.di.bpmn;

import java.util.ArrayList;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.di.Plane;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;

public class BpmnPlane extends Plane {
	
	private BaseElement bpmnElement;
	private List<BpmnShape> shapes = new ArrayList<>();
	private List<BpmnEdge> edges = new ArrayList<>();
	
	public BpmnPlane(String id) {
		super(id);
	}
	
	public BaseElement getBpmnElement() {
		return bpmnElement;
	}
	
	public void setBpmnElement(BaseElement bpmnElement) {
		this.bpmnElement = bpmnElement;
	}
	
	public List<BpmnEdge> getEdges() {
		return edges;
	}
	
	public List<BpmnShape> getShapes() {
		return shapes;
	}
	
}
