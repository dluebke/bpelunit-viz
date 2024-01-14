package com.digitalsolutionarchitecture.bpmn.layout.grid;

import java.util.ArrayList;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.di.Bounds;
import com.digitalsolutionarchitecture.bpmn.layout.IBpmnLayouter;
import com.digitalsolutionarchitecture.bpmn.model.activity.Task;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.events.Event;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowNode;
import com.digitalsolutionarchitecture.bpmn.model.layout.Layouts;
import com.digitalsolutionarchitecture.bpmn.model.process.Process;

public class GridbasedLayout implements IBpmnLayouter {

	private String diagramId = Layouts.DEFAULT_DIAGRAM_ID;
	private Bounds taskBoundsTemplate = new Bounds(0, 0, 105, 80);
	private Bounds eventBoundsTemplate = new Bounds(0, 0, 36, 36);

	public String getDiagramId() {
		return diagramId;
	}
	
	public void setDiagramId(String diagramId) {
		this.diagramId = diagramId;
	}
	
	@Override
	public void layout(Collaboration model) {
		for(Participant p : model.getParticipants()) {
			if(p.getProcess() != null) {
				Grid<FlowNode> g = layoutGrid(p.getProcess());
				setAllFlowElementSizes(p.getProcess().getFlowElements());
			}
		}
	}

	private void setAllFlowElementSizes(List<FlowElement> flowElements) {
		for(FlowElement fe : flowElements) {
			if(fe instanceof Task) {
				((Task) fe).getLayouts().getDiagramElement(diagramId).setBounds(taskBoundsTemplate.clone());
			} else if(fe instanceof Event) {
				((Event) fe).getLayouts().getDiagramElement(diagramId).setBounds(eventBoundsTemplate.clone());
			} else if(fe instanceof SequenceFlow) {
				// ignore
			} else {
				throw new RuntimeException("Unknown flow element class to set dimensions for: " + fe.getClass());
			}
		}
	}

	protected Grid<FlowNode> layoutGrid(Process process) {
		List<FlowNode> remainingFlowNodes = new ArrayList<>(filterFlowNodes(process.getFlowElements()));
		List<FlowNode> currentFlowNodes = getStartFlowElements(remainingFlowNodes);
		List<FlowNode> layoutedFlowNodes = new ArrayList<>();
		Grid<FlowNode> grid = new Grid<>();
		
		do {
			remainingFlowNodes.removeAll(currentFlowNodes);
			layoutedFlowNodes.addAll(currentFlowNodes);
			
			int x = grid.getWidth();
			int y = 0;
			for(FlowNode fn : currentFlowNodes) {
				grid.setValue(fn, x, y);
				y++;
			}
			
			currentFlowNodes = getNextFlowElements(remainingFlowNodes, layoutedFlowNodes);
		} while(!currentFlowNodes.isEmpty());
		
		grid.optimize();
		return grid;
	}

	private List<FlowNode> filterFlowNodes(List<FlowElement> flowElements) {
		List<FlowNode> result = new ArrayList<>();
		
		for(FlowElement fe : flowElements) {
			if(fe instanceof FlowNode) {
				result.add((FlowNode)fe);
			}
		}
		
		return result;
	}

	private List<FlowNode> getNextFlowElements(
			List<FlowNode> remainingFlowNodes,
			List<FlowNode> layoutedFlowNodes) {
		List<FlowNode> result = new ArrayList<>();

		FlowNode partlyReferencedFromLayoutedNodes = null;
		
		for(FlowNode fn : remainingFlowNodes) {
			boolean noRemainingIncomingSequenceFlowsFromLayoutedSet = true;
			boolean referencedFromRemainingFlowNodes = false;
			for(SequenceFlow s : fn.getIncoming()) {
				if(!layoutedFlowNodes.contains(s.getSource())) {
					noRemainingIncomingSequenceFlowsFromLayoutedSet = false;
				}
				if(!remainingFlowNodes.contains(s.getSource())) {
					referencedFromRemainingFlowNodes = true;
				}
			}
			if(noRemainingIncomingSequenceFlowsFromLayoutedSet) {
				result.add(fn);
			}
			if(!noRemainingIncomingSequenceFlowsFromLayoutedSet && referencedFromRemainingFlowNodes) {
				partlyReferencedFromLayoutedNodes = fn;
			}
		}

		if(result.isEmpty() && partlyReferencedFromLayoutedNodes != null) {
			result.add(partlyReferencedFromLayoutedNodes);
		}

		return result;
	}

	private List<FlowNode> getStartFlowElements(List<FlowNode> remainingFlowElements) {
		List<FlowNode> result = new ArrayList<>();
		
		for(FlowNode fn : remainingFlowElements) {
			if(fn.getIncoming().size() == 0) {
				result.add(fn);
			}
		}
		return result;
	}

}
