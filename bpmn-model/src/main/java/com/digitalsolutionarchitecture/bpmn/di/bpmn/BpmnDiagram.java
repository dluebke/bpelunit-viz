package com.digitalsolutionarchitecture.bpmn.di.bpmn;

import java.util.ArrayList;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.di.Diagram;

public class BpmnDiagram extends Diagram {

	private List<BpmnPlane> planes = new ArrayList<>();
	
	public List<BpmnPlane> getPlanes() {
		return planes;
	}
	
}
