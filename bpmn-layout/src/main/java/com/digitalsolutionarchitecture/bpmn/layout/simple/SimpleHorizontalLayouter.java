package com.digitalsolutionarchitecture.bpmn.layout.simple;

import java.util.List;

import com.digitalsolutionarchitecture.bpmn.di.Bounds;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnEdge;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnShape;
import com.digitalsolutionarchitecture.bpmn.layout.IBpmnLayouter;
import com.digitalsolutionarchitecture.bpmn.model.activity.Task;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.MessageFlow;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.events.Event;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.poolordering.PoolOrderingStrategy;
import com.digitalsolutionarchitecture.bpmn.poolordering.SimplePoolOrderingStrategy;

public class SimpleHorizontalLayouter implements IBpmnLayouter {

	private double diagramPaddingTop = 5;
	private double diagramPaddingLeft = 5;
	private double spacingBetweenPools = 50;
	private double collapsedPoolHeight = 60;
	private double poolHeight = 195;
	private double poolPaddingLeft = 80;
	private double poolPaddingRight = 45;
	private double flowElementSpacing = 45;
	private double taskHeight = 80;
	private double taskWidth = 105;
	private double eventSize = 36;
	
	private PoolOrderingStrategy poolOrderingStrategy;
	
	public SimpleHorizontalLayouter() {
		this(SimplePoolOrderingStrategy.NO_REORDER);
	}
	
	public SimpleHorizontalLayouter(PoolOrderingStrategy poolOrderingStrategy) {
		setPoolOrderingStrategy(poolOrderingStrategy);
	}

	@Override
	public void layout(Collaboration model) {
		double maxWidth = 0;
		double currentPoolY = diagramPaddingTop;
		List<Participant> participants = poolOrderingStrategy.reorder(model);
		for(Participant p : participants) {
			BpmnShape pool = p.getLayouts().getDiagramElement();
			pool.setIsHorizontal(true);
			if(p.getProcess() == null) {
				pool.setBounds(new Bounds(diagramPaddingLeft, currentPoolY, 0, collapsedPoolHeight));
			} else {
				pool.setBounds(new Bounds(diagramPaddingLeft, currentPoolY, 0, poolHeight));
				
				double currentX = diagramPaddingLeft + poolPaddingLeft;
				for(FlowElement fe : p.getProcess().getFlowElements()) {
					if(fe instanceof Event) {
						BpmnShape eventShape = ((Event) fe).getLayouts().getDiagramElement();
						eventShape.setBounds(new Bounds(currentX, currentPoolY + (poolHeight - eventSize) / 2, eventSize, eventSize));
						currentX = eventShape.getBounds().getRight(); 
						maxWidth = Math.max(maxWidth, currentX);
						currentX += flowElementSpacing;
					} else if(fe instanceof Task) {
						BpmnShape taskShape = ((Task) fe).getLayouts().getDiagramElement();
						taskShape.setBpmnElement(fe);
						taskShape.setBounds(new Bounds(currentX, currentPoolY + (poolHeight - taskHeight) / 2, taskWidth, taskHeight));
						currentX = taskShape.getBounds().getRight(); 
						maxWidth = Math.max(maxWidth, currentX);
						currentX += flowElementSpacing;
					} else if(fe instanceof SequenceFlow) {
						BpmnEdge sequenceFlowEdge = ((SequenceFlow) fe).getLayouts().getDiagramElement();
						sequenceFlowEdge.setBpmnElement(fe);
					} else {
						throw new RuntimeException("Unknown BPMN Flow Element: " + fe.getClass().getCanonicalName());
					}
				}
			}
			
			currentPoolY = pool.getBounds().getBottom() + spacingBetweenPools;
		}
		
		setPoolWidth(model, maxWidth + poolPaddingRight);
		
		for(MessageFlow mf : model.getMessageFlows()) {
			BpmnEdge messageFlowEdge = new BpmnEdge(mf.getId() + "-EDGE");
			messageFlowEdge.setSourceElement(mf.getSource().getLayouts().getDiagramElement());
			messageFlowEdge.setTargetElement(mf.getTarget().getLayouts().getDiagramElement());
			messageFlowEdge.setBpmnElement(mf);
//			plane.getEdges().add(messageFlowEdge);
		}
		
		setEdgeSourcesAndTargets(model);
	}


	public PoolOrderingStrategy getPoolOrderingStrategy() {
		return poolOrderingStrategy;
	}
	
	public void setPoolOrderingStrategy(
			PoolOrderingStrategy poolOrderingStrategy) {
		this.poolOrderingStrategy = poolOrderingStrategy;
	}
	
	private void setPoolWidth(Collaboration model, double endX) {
		for(Participant p : model.getParticipants()) {
			Bounds b = p.getLayouts().getDiagramElement().getBounds();
			b.setWidth(endX - b.getX());
		}
	}

	private void setEdgeSourcesAndTargets(Collaboration model) {
		for(MessageFlow mf : model.getMessageFlows()) {
			BpmnEdge e = mf.getLayouts().getDiagramElement();
			Bounds sourceBounds = mf.getSource().getLayouts().getDiagramElement().getBounds();
			Bounds targetBounds = mf.getTarget().getLayouts().getDiagramElement().getBounds();
			
			Bounds smallerBounds = Bounds.withSmallerWidth(sourceBounds, targetBounds);
			double x = smallerBounds.getX() + (smallerBounds.getWidth() / 2);
			
			if(sourceBounds.isAbove(targetBounds)) {
				e.addWayPoint(x, sourceBounds.getBottom());
				e.addWayPoint(x, targetBounds.getY());
			} else{
				e.addWayPoint(x, sourceBounds.getY());
				e.addWayPoint(x, targetBounds.getBottom());
			}
			
			e.setSourceElement(mf.getSource().getLayouts().getDiagramElement());
			e.setTargetElement(mf.getTarget().getLayouts().getDiagramElement());
		}
		
		for(Participant p : model.getParticipants()) {
			if(p.getProcess() != null) {
				for(FlowElement fn : p.getProcess().getFlowElements()) {
					if(fn instanceof SequenceFlow) {
						SequenceFlow sf = (SequenceFlow)fn;
						BpmnEdge e = sf.getLayouts().getDiagramElement();
						BpmnShape sourceShape = sf.getSource().getLayouts().getDiagramElement();
						e.setSourceElement(sourceShape);
						BpmnShape targetShape = sf.getTarget().getLayouts().getDiagramElement();
						e.setTargetElement(targetShape);
					}
				}
			}
		}
	}

//	private void connectLeftToRight(BpmnEdge e, BpmnShape sourceShape,
//			BpmnShape targetShape) {
//		e.addWayPoint(sourceShape.getBounds().getRight(), sourceShape.getBounds().getMiddle());
//		e.addWayPoint(targetShape.getBounds().getX(), sourceShape.getBounds().getMiddle());
//	}

}
