package com.digitalsolutionarchitecture.bpmn.model.foundation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnDiagram;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnPlane;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.MessageFlow;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.layout.BpmnEdgeLayouts;
import com.digitalsolutionarchitecture.bpmn.model.layout.BpmnShapeLayouts;

public class BpmnModel {

	private Collaboration collaboration;
//	private List<BpmnDiagram> diagrams = new ArrayList<>();
	private String name;
	private String id;
	
	public Collaboration getCollaboration() {
		return collaboration;
	}
	
	public void setCollaboration(Collaboration collaboration) {
		this.collaboration = collaboration;
	}

	public List<BpmnDiagram> getDiagrams() {
		Map<String, BpmnDiagram> diagrams = new HashMap<>();
		
		for(Participant p : collaboration.getParticipants()) {
			addShapes(diagrams, p.getLayouts());
			if(p.getProcess() != null) {
				for(FlowElement fe : p.getProcess().getFlowElements()) {
					if(fe instanceof FlowNode) {
						addShapes(diagrams, ((FlowNode) fe).getLayouts());
					} else if (fe instanceof SequenceFlow) {
						addEdges(diagrams, ((SequenceFlow) fe).getLayouts());
					}
				}
			}
		}
		for(MessageFlow mf : collaboration.getMessageFlows()) {
			addEdges(diagrams, mf.getLayouts());
		}
		
		return new ArrayList<>(diagrams.values());
	}
	
	private void addEdges(Map<String, BpmnDiagram> diagrams,
			BpmnEdgeLayouts layouts) {
		for(String diagramId : layouts.getDiagramIds()) {
			BpmnDiagram diagram = diagrams.get(diagramId);
			if(diagram == null) {
				diagram = new BpmnDiagram();
				BpmnPlane plane = new BpmnPlane(diagramId);
				plane.setBpmnElement(collaboration);
				diagram.getPlanes().add(plane);
				diagrams.put(diagramId, diagram);
			}
			diagram.getPlanes().get(0).getEdges().add(layouts.getDiagramElement(diagramId));
		}
	}

	private void addShapes(Map<String, BpmnDiagram> diagrams, BpmnShapeLayouts layouts) {
		for(String diagramId : layouts.getDiagramIds()) {
			BpmnDiagram diagram = diagrams.get(diagramId);
			if(diagram == null) {
				diagram = new BpmnDiagram();
				BpmnPlane plane = new BpmnPlane(diagramId);
				plane.setBpmnElement(collaboration);
				diagram.getPlanes().add(plane);
				diagrams.put(diagramId, diagram);
			}
			diagram.getPlanes().get(0).getShapes().add(layouts.getDiagramElement(diagramId));
		}
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
}
