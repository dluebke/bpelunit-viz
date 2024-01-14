package com.digitalsolutionarchitecture.bpmn.io;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.omg.spec.bpmn._20100524.di.BPMNDiagram;
import org.omg.spec.bpmn._20100524.di.BPMNEdge;
import org.omg.spec.bpmn._20100524.di.BPMNLabel;
import org.omg.spec.bpmn._20100524.di.BPMNPlane;
import org.omg.spec.bpmn._20100524.di.BPMNShape;
import org.omg.spec.bpmn._20100524.model.ObjectFactory;
import org.omg.spec.bpmn._20100524.model.TBaseElement;
import org.omg.spec.bpmn._20100524.model.TCollaboration;
import org.omg.spec.bpmn._20100524.model.TDefinitions;
import org.omg.spec.bpmn._20100524.model.TDocumentation;
import org.omg.spec.bpmn._20100524.model.TEventDefinition;
import org.omg.spec.bpmn._20100524.model.TIntermediateCatchEvent;
import org.omg.spec.bpmn._20100524.model.TIntermediateThrowEvent;
import org.omg.spec.bpmn._20100524.model.TMessageEventDefinition;
import org.omg.spec.bpmn._20100524.model.TMessageFlow;
import org.omg.spec.bpmn._20100524.model.TParticipant;
import org.omg.spec.bpmn._20100524.model.TProcess;
import org.omg.spec.bpmn._20100524.model.TSequenceFlow;
import org.omg.spec.bpmn._20100524.model.TServiceTask;
import org.omg.spec.bpmn._20100524.model.TTimerEventDefinition;
import org.omg.spec.dd._20100524.dc.Bounds;

import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnDiagram;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnEdge;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnLabel;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnPlane;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnShape;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.Color;
import com.digitalsolutionarchitecture.bpmn.model.activity.ServiceTask;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.MessageFlow;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.common.Point;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.events.EventDefinition;
import com.digitalsolutionarchitecture.bpmn.model.events.IntermediateCatchEvent;
import com.digitalsolutionarchitecture.bpmn.model.events.IntermediateThrowEvent;
import com.digitalsolutionarchitecture.bpmn.model.events.MessageEventDefinition;
import com.digitalsolutionarchitecture.bpmn.model.events.TimerEventDefinition;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BpmnModel;
import com.digitalsolutionarchitecture.bpmn.model.foundation.Documentation;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.process.Process;

public class BpmnXsdModelWriter implements BpmnModelWriter {

	private static final String NSURI_CAMUNDA = "http://bpmn.io/schema/bpmn/biocolor/1.0";
	private String exporterVersion = "1.0";
	private String exporter = getClass().getCanonicalName();
	private String targetNamespace = "urn:" + getClass().getCanonicalName().replaceAll("\\.", ":");
	
	@Override
	public void write(BpmnModel model, OutputStream out) throws IOException {
		ObjectFactory bpmnObjectFactory = new ObjectFactory();
		org.omg.spec.bpmn._20100524.di.ObjectFactory diObjectFactory = new org.omg.spec.bpmn._20100524.di.ObjectFactory();
		
		TDefinitions definitions = mapDefinitions(model);
		
		TCollaboration c = mapCollaboration(model.getCollaboration());
		definitions.getRootElement().add(bpmnObjectFactory.createCollaboration(c));
		
		for(Participant p : model.getCollaboration().getParticipants()) {
			if(p.getProcess() != null) {
				definitions.getRootElement().add(bpmnObjectFactory.createProcess(mapProcess(p.getProcess(), bpmnObjectFactory)));
			}
		}
		
		for(BpmnDiagram d : model.getDiagrams()) {
			definitions.getBPMNDiagram().add(mapBpmnDiagramm(d, diObjectFactory));
		}
		
		try {
			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			context.createMarshaller().marshal(bpmnObjectFactory.createDefinitions(definitions), out);
		} catch (JAXBException e) {
			throw new RuntimeException("Could not serialize JAXB objects", e);
		}
	}

	private BPMNDiagram mapBpmnDiagramm(BpmnDiagram d, org.omg.spec.bpmn._20100524.di.ObjectFactory diObjectFactory) {
		BPMNDiagram result = new BPMNDiagram();
		
		for(BpmnPlane p : d.getPlanes()) {
			result.setBPMNPlane(mapToBpmnPlane(p, diObjectFactory));
		}
		
		return result;
	}
	private BPMNPlane mapToBpmnPlane(BpmnPlane p, org.omg.spec.bpmn._20100524.di.ObjectFactory diObjectFactory) {
		BPMNPlane bpmnPlane = new BPMNPlane();
		bpmnPlane.setId(p.getId());
//		TODO bpmnPlane.setExtension()
//		TODO bpmnPlane.getOtherAttributes()
		bpmnPlane.setBpmnElement(new QName(p.getBpmnElement().getId()));
		for(BpmnShape s : p.getShapes()) {
			bpmnPlane.getDiagramElement().add(mapToBpmnShape(s, diObjectFactory));
		}
		for(BpmnEdge e : p.getEdges()) {
			bpmnPlane.getDiagramElement().add(mapToBpmnEdge(e, diObjectFactory));
		}
		
		return bpmnPlane;
	}


	private JAXBElement<BPMNEdge> mapToBpmnEdge(BpmnEdge e, org.omg.spec.bpmn._20100524.di.ObjectFactory diObjectFactory) {
		if(e.getSourceElement() == null || e.getTargetElement() == null) {
			System.out.println(e.getId() + " edge is missing source or target: " + e.getSourceElement() + "->" + e.getTargetElement());
			return null;
		}
		BPMNEdge edge = new BPMNEdge();
		edge.setBpmnElement(new QName(e.getBpmnElement().getId()));
		edge.setBPMNLabel(mapLabel(e.getLabel()));
		edge.setId(e.getId());
//		TODO edge.setMessageVisibleKind(e.get)
		edge.setSourceElement(new QName(e.getSourceElement().getId()));
		edge.setTargetElement(new QName(e.getTargetElement().getId()));
//		TODO edge.getOtherAttributes()
		for(Point p : e.getWaypoints()) {
			org.omg.spec.dd._20100524.dc.Point point = new org.omg.spec.dd._20100524.dc.Point();
			point.setX(p.getX());
			point.setY(p.getY());
			edge.getWaypoint().add(point);			
		}
		return diObjectFactory.createBPMNEdge(edge);
	}

	private JAXBElement<BPMNShape> mapToBpmnShape(BpmnShape s, org.omg.spec.bpmn._20100524.di.ObjectFactory diObjectFactory) {
		BPMNShape shape = new BPMNShape();
		shape.setBounds(mapBounds(s.getBounds()));
		shape.setBpmnElement(new QName(s.getBpmnElement().getId()));
		shape.setBPMNLabel(mapLabel(s.getLabel()));
		shape.setId(s.getId());
		shape.setIsExpanded(s.getIsExpanded());
		shape.setIsHorizontal(s.getIsHorizontal());
		shape.setIsMarkerVisible(s.getIsMarkerVisible());
		shape.setIsMessageVisible(s.getIsMessageVisible());
//		TODO shape.setChoreographyActivityShape()
//		TODO shape.setExtension(value)
//		TODO shape.setParticipantBandKind(value)
//		TODO shape.getOtherAttributes()
		
		Color borderColor = s.getBorderColor();
		if(borderColor != null) {
			shape.getOtherAttributes().put(
				new QName(NSURI_CAMUNDA, "stroke"), 
				"rgb(" + borderColor.getRed() + ", " + borderColor.getGreen() + ", " +borderColor.getBlue() + ")"
			);
		}
		
		Color backgroundColor = s.getBackgroundColor();
		if(backgroundColor != null) {
			shape.getOtherAttributes().put(
				new QName(NSURI_CAMUNDA, "fill"), 
				"rgb(" + backgroundColor.getRed() + ", " + backgroundColor.getGreen() + ", " + backgroundColor.getBlue() + ")"
			);
		}
		return diObjectFactory.createBPMNShape(shape);
	}

	private BPMNLabel mapLabel(BpmnLabel label) {
		if(label != null) {
			BPMNLabel result = new BPMNLabel();
			result.setBounds(mapBounds(label.getBounds()));
			result.setId(label.getId());
//			TODO result.setLabelStyle(label.get)
			return result;
		} else {
			return null;
		}
	}

	private Bounds mapBounds(com.digitalsolutionarchitecture.bpmn.di.Bounds bounds) {
		Bounds result = new Bounds();
		result.setHeight(bounds.getHeight());
		result.setWidth(bounds.getWidth());
		result.setX(bounds.getX());
		result.setY(bounds.getY());
		return result;
	}

	private TProcess mapProcess(Process process, ObjectFactory bpmnObjectFactory) {
		if(process == null)
			return null;
		
		TProcess result = new TProcess();
		
		result.setId(process.getId());
		result.setIsClosed(process.isClosed());
		result.setIsExecutable(process.isExecutable());
		
		Map<TSequenceFlow, SequenceFlow> sequenceFlows = new HashMap<>();
		Map<FlowElement, Object> mappedFlowElements = new HashMap<>();
		
		for(FlowElement fe : process.getFlowElements()) {
			if(fe instanceof SequenceFlow) {
				SequenceFlow sf = (SequenceFlow)fe;
				TSequenceFlow sequenceFlow = new TSequenceFlow();
				mapBaseElement(sequenceFlow, sf);
				sequenceFlow.setName(sf.getName());
				sequenceFlow.setSourceRef(sf.getSource().getId());
				sequenceFlow.setTargetRef(sf.getTarget().getId());
				result.getFlowElement().add(bpmnObjectFactory.createSequenceFlow(sequenceFlow));
				sequenceFlows.put(sequenceFlow, sf);
			} else if (fe instanceof ServiceTask) {
				ServiceTask st = (ServiceTask)fe;
				TServiceTask serviceTask = new TServiceTask();
				mapBaseElement(serviceTask, st);
				serviceTask.setName(st.getName());
				if(st.getCompletionQuantity() != null) {
					serviceTask.setCompletionQuantity(BigInteger.valueOf(st.getCompletionQuantity()));
				}
				serviceTask.setIsForCompensation(st.isForCompensation());
				
				for(SequenceFlow sf : st.getIncoming()) {
					serviceTask.getIncoming().add(new QName(sf.getId()));
				}
				for(SequenceFlow sf : st.getOutgoing()) {
					serviceTask.getOutgoing().add(new QName(sf.getId()));
				}
				result.getFlowElement().add(bpmnObjectFactory.createServiceTask(serviceTask));
				mappedFlowElements.put(fe, serviceTask);
			} else if(fe instanceof IntermediateCatchEvent) {
				IntermediateCatchEvent ice =(IntermediateCatchEvent)fe;
				TIntermediateCatchEvent intermediateCatchEvent = new TIntermediateCatchEvent();
				mapBaseElement(intermediateCatchEvent, ice);
				intermediateCatchEvent.setName(ice.getName());
				intermediateCatchEvent.getEventDefinition().add(mapEventDefinition(ice.getEventDefinition(), bpmnObjectFactory));
				result.getFlowElement().add(bpmnObjectFactory.createIntermediateCatchEvent(intermediateCatchEvent));
				mappedFlowElements.put(fe, intermediateCatchEvent);
			} else if(fe instanceof IntermediateThrowEvent) {
				IntermediateThrowEvent ite =(IntermediateThrowEvent)fe;
				TIntermediateThrowEvent intermediateThrowEvent = new TIntermediateThrowEvent();
				mapBaseElement(intermediateThrowEvent, ite);
				intermediateThrowEvent.setName(ite.getName());
				result.getFlowElement().add(bpmnObjectFactory.createIntermediateThrowEvent(intermediateThrowEvent));
				intermediateThrowEvent.getEventDefinition().add(mapEventDefinition(ite.getEventDefinition(), bpmnObjectFactory));
				mappedFlowElements.put(fe, intermediateThrowEvent);
			} else {
				throw new RuntimeException("Element not suppported: " + fe.getClass());
			}
		}
		
		for(Entry<TSequenceFlow, SequenceFlow> sequenceFlow : sequenceFlows.entrySet()) {
			sequenceFlow.getKey().setSourceRef(mappedFlowElements.get(sequenceFlow.getValue().getSource()));
			sequenceFlow.getKey().setTargetRef(mappedFlowElements.get(sequenceFlow.getValue().getTarget()));
		}
		
		return result;
	}

	private JAXBElement<? extends TEventDefinition> mapEventDefinition(
			EventDefinition eventDefinition, ObjectFactory bpmnObjectFactory) {
		JAXBElement<? extends TEventDefinition> result = null;

		
		if(eventDefinition instanceof MessageEventDefinition) {
			MessageEventDefinition med = (MessageEventDefinition)eventDefinition;
			TMessageEventDefinition tmed = new TMessageEventDefinition();
			mapBaseElement(tmed, med);
//			tmed.setMessageRef(new QName(med.getMessage().getName()));
			result = bpmnObjectFactory.createMessageEventDefinition(tmed);
		} else if (eventDefinition instanceof TimerEventDefinition) {
			TimerEventDefinition med = (TimerEventDefinition)eventDefinition;
			TTimerEventDefinition tmed = new TTimerEventDefinition();
			mapBaseElement(tmed, med);
//			tmed.setMessageRef(new QName(med.getMessage().getName()));
			result = bpmnObjectFactory.createTimerEventDefinition(tmed);
		}
		
		return result;
	}

	private TDefinitions mapDefinitions(BpmnModel model) {
		TDefinitions definitions = new TDefinitions();
		definitions.setExporter(exporter);
		definitions.setExporterVersion(exporterVersion);
		definitions.setTargetNamespace(targetNamespace);
		definitions.setName(model.getName());
		return definitions;
	}

	private TCollaboration mapCollaboration(Collaboration c) {
		TCollaboration collaboration = new TCollaboration();
		mapBaseElement(collaboration, c);
		collaboration.setIsClosed(false);
		collaboration.setName(c.getName());
		
		for(Participant p : c.getParticipants()) {
			collaboration.getParticipant().add(mapParticipant(p));
		}
		
		for(MessageFlow mf : c.getMessageFlows()) {
			collaboration.getMessageFlow().add(mapMessageFlow(mf));
		}
		
		return collaboration;
	}

	private void mapBaseElement(TBaseElement tBaseElement, BaseElement baseElement) {
		tBaseElement.setId(baseElement.getId());
		if(baseElement.hasDocumentation()) {
			for(Documentation documentation : baseElement.getDocumentation()) {
				TDocumentation tDocumentation = new TDocumentation();
				tDocumentation.setId(documentation.getId());
				tDocumentation.setTextFormat(documentation.getTextFormat());
				tDocumentation.getContent().add(documentation.getText());
				tBaseElement.getDocumentation().add(tDocumentation);
			}
		}
	}

	private TMessageFlow mapMessageFlow(MessageFlow mf) {
		TMessageFlow messageFlow = new TMessageFlow();
		mapBaseElement(messageFlow, mf);
		messageFlow.setName(mf.getName());
		messageFlow.setSourceRef(new QName(mf.getSource().getId()));
		messageFlow.setTargetRef(new QName(mf.getTarget().getId()));
		if(mf.getMessage() != null) {
			messageFlow.setMessageRef(new QName(mf.getMessage().getName()));
		}
		return messageFlow;
	}

	private TParticipant mapParticipant(Participant p) {
		TParticipant result = new TParticipant();
		mapBaseElement(result, p);
		
		result.setName(p.getName());
		if(p.getProcess() != null) {
			result.setProcessRef(new QName(p.getProcess().getId()));
		}
		
		return result;
	}

	@Override
	public String getFileSuffix() {
		return ".bpmn";
	}

	@Override
	public String getMimeType() {
		return "application/bpmn+xml";
	}

	public void setExporter(String exporter) {
		this.exporter = exporter;
	}
	
	public String getExporter() {
		return exporter;
	}
	
	public String getExporterVersion() {
		return exporterVersion;
	}
	
	public void setExporterVersion(String exporterVersion) {
		this.exporterVersion = exporterVersion;
	}
	
	public String getTargetNamespace() {
		return targetNamespace;
	}
	
	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}
}
