package net.bpelunit.bpmn_bptslogfile_visualization;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnShape;
import com.digitalsolutionarchitecture.bpmn.io.BpmnModelWriter;
import com.digitalsolutionarchitecture.bpmn.io.BpmnXsdModelWriter;
import com.digitalsolutionarchitecture.bpmn.layout.IBpmnLayouter;
import com.digitalsolutionarchitecture.bpmn.layout.simple.SimpleHorizontalLayouter;
import com.digitalsolutionarchitecture.bpmn.model.activity.ServiceTask;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.InteractionNode;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.MessageFlow;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.events.IntermediateCatchEvent;
import com.digitalsolutionarchitecture.bpmn.model.events.IntermediateThrowEvent;
import com.digitalsolutionarchitecture.bpmn.model.events.MessageEventDefinition;
import com.digitalsolutionarchitecture.bpmn.model.events.TimerEventDefinition;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BpmnModel;
import com.digitalsolutionarchitecture.bpmn.model.foundation.Documentation;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowNode;
import com.digitalsolutionarchitecture.bpmn.model.humaninteraction.UserTask;
import com.digitalsolutionarchitecture.bpmn.model.process.Process;
import com.digitalsolutionarchitecture.bpmn.poolordering.minimumdistancemessageflows.IncomingEdgesHeuristicMessageFlowOrderingStrategy;

import net.bpelunit.bpmn_bptslogfile_visualization.bptsbpmncolors.DefaultColorScheme;
import net.bpelunit.bpmn_bptslogfile_visualization.bptsbpmncolors.ITestColorScheme;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.Activity;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.Assertion;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.PartnerTrack;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.TestCase;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.TestSuite;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.WithPassState.PassStatus;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogreader.BptsLogReader;
import net.bpelunit.bpmn_bptslogfile_visualization.bptslogreader.BptsLogXmlReader;

public class BptsLogFileVisualizationApp {
	
    public static void main(String[] args) throws Exception {
        BptsLogReader bptsLogReader = new BptsLogXmlReader();
        IBpmnLayouter layouter = new SimpleHorizontalLayouter(new IncomingEdgesHeuristicMessageFlowOrderingStrategy());
        
        List<?extends BpmnModelWriter> bpmnModelWriters = Arrays.asList(
//        		new BpmnModelSvgWriter(),
//        		new TextBpmnModelWriter(),
        		new BpmnXsdModelWriter()
        	);
        
        TestSuite bptsLog = bptsLogReader.readLog(args[0]);
        
        File outputDir = new File(".");
        if(args.length > 1) {
        	outputDir = new File(args[1]);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        
        for(TestCase tc : bptsLog.getTestCases()) {
        	System.out.println("Generating BPMN for Test Case " + tc.getName());
        	BpmnModel bpmnModel = new BpmnModel();
        	bpmnModel.setId(generateId((tc.getName())));
        	bpmnModel.setName(tc.getName()); 
        	Collaboration collaboration = new Collaboration(generateId(tc.getName() + "-COLLABORATION"));
        	collaboration.setName(tc.getName());
			bpmnModel.setCollaboration(collaboration);

			Participant processUnderTest = new Participant(generateId("PUT"));
			processUnderTest.setProcess(null);
			processUnderTest.setName("PUT");
			processUnderTest.getExtensions().add(tc.getPassStatus());
			collaboration.getParticipants().add(processUnderTest);
			
        	for(PartnerTrack pt : tc.getPartnerTracks()) {
        		if(!pt.getActivities().isEmpty()) {
        			Participant ptParticipant = new Participant(generateId(tc.getName() + "-" + pt.getName()), pt.getName());
        			collaboration.getParticipants().add(ptParticipant);
        			ptParticipant.setProcess(new Process(generateId(tc.getName() + "-" + pt.getName() + "-PROCESS")));
        			ptParticipant.getExtensions().add(pt.getPassStatus());
        			
        			FlowNode lastNode = null;
        			int i = 0;
        			for(Activity a : pt.getActivities()) {
        				i++;
        				FlowNode currentNode = null;
        				String requestElementName = getMessageElementName(a.getRequest());
						String id = generateId(tc.getName() + "-" + pt.getName() + "-" + Integer.toString(i));
						switch(a.getType()) {
        				case COMPLETE_HUMAN_TASK:
        					currentNode = new UserTask(id, ptParticipant.getProcess());
        					currentNode.setName(requestElementName + " (Complete Human Task)");
        					break;
        				case RECEIVE_ASYNC:
        					IntermediateCatchEvent receiveAsyncEvent = new IntermediateCatchEvent(id, ptParticipant.getProcess());
        					receiveAsyncEvent.setName(requestElementName + " (Receive Async)");
        					receiveAsyncEvent.setEventDefinition(new MessageEventDefinition(id));
        					currentNode = receiveAsyncEvent;
        					break;
        				case RECEIVE_SEND_ASYNC:
        					currentNode = new ServiceTask(id, ptParticipant.getProcess());
        					currentNode.setName(requestElementName + " (Receive/Send Async)");
        					break;
        				case RECEIVE_SEND_SYNC:
        					currentNode = new ServiceTask(id, ptParticipant.getProcess());
        					currentNode.setName(requestElementName + " (Receive/Send Sync)");
        					break;
        				case SEND_ASYNC:
        					IntermediateThrowEvent sendAsyncEvent = new IntermediateThrowEvent(id, ptParticipant.getProcess());
        					sendAsyncEvent.setName(requestElementName + " (Send Async)");
        					sendAsyncEvent.setEventDefinition(new MessageEventDefinition(generateId(tc.getName() + "-" + pt.getName() + "-" + Integer.toString(i) + "-EVENTDEFINITION")));
        					currentNode = sendAsyncEvent;
        					break;
        				case SEND_RECEIVE_ASYNC:
        					currentNode = new ServiceTask(id, ptParticipant.getProcess());
        					currentNode.setName(requestElementName + " (Send/Receive Async)");
        					break;
        				case SEND_RECEIVE_SYNC:
        					currentNode = new ServiceTask(id, ptParticipant.getProcess());
        					currentNode.setName(requestElementName + " (Send/Receive Sync)");
        					break;
        				case WAIT:
        					IntermediateCatchEvent waitEvent = new IntermediateCatchEvent(id, ptParticipant.getProcess());
        					waitEvent.setName("Wait");
        					waitEvent.setEventDefinition(new TimerEventDefinition(generateId(generateId(tc.getName() + "-" + pt.getName() + "-" + Integer.toString(i) + "-EVENTDEFINITION"))));
        					currentNode = waitEvent;
        					break;
        				default: 
        					throw new RuntimeException("Unsupported element type: " + a.getType());
        				}
						
						if(a.getType().isInitiallySending()) {
							collaboration.getMessageFlows().add(new MessageFlow(generateId(tc.getName() + "-" + pt.getName() + "-" + Integer.toString(i) + "-MESSAGEFLOWTOPUT"), (InteractionNode)currentNode, processUnderTest));
						}
						if(a.getType().isInitiallyReceiving()) {
							collaboration.getMessageFlows().add(new MessageFlow(generateId(tc.getName() + "-" + pt.getName() + "-" + Integer.toString(i) + "-MESSAGEFLOWFROMPUT"), processUnderTest, (InteractionNode)currentNode));
						}
						
						currentNode.getExtensions().add(a.getPassStatus());
						currentNode.getDocumentation().add(new Documentation(generateId(tc.getName() + "-" + pt.getName() + "-" + Integer.toString(i) + "-DOCUMENTATION"), createDocumentation(a, transformer)));
						ptParticipant.getProcess().getFlowElements().add(currentNode);
	        			if(lastNode != null) {
	        				ptParticipant.getProcess().getFlowElements().add(new SequenceFlow(generateId(tc.getName() + "-" + pt.getName() + "-SEQ" + i + "-" + (i-1)), lastNode, currentNode, ptParticipant.getProcess()));
	        			}
	        			lastNode = currentNode;
        			}
        		}
        	}
        	
        	layouter.layout(bpmnModel.getCollaboration());
        	
        	addColorsForStatus(bpmnModel.getCollaboration());
        	
        	for(BpmnModelWriter bpmnModelWriter : bpmnModelWriters) {
	        	String filebasename = generateId(tc.getName());
	        	if(filebasename.length() > 200) {
	        		filebasename = filebasename.substring(0, 200);
	        	}
				File outputFile = new File(outputDir, filebasename + bpmnModelWriter.getFileSuffix());
				try (OutputStream os = new FileOutputStream(outputFile)) {
	        		bpmnModelWriter.write(bpmnModel, os);
	        	}
        	}
        }
    }

	private static String createDocumentation(Activity a, Transformer t) throws TransformerException {
		StringBuilder result = new StringBuilder();
		result.append(a.getPassStatus());
		
		if(a.getStatusMessage() != null) {
			result.append(": ").append(a.getStatusMessage());
		}
		result.append("\n\n");
		
		if(a.getException() != null) {
			result.append("Exception : ").append(a.getException()).append("\n\n");
		}

		if(a.getAssertions().size() > 0) {
			result.append("Assertions:\n");
			for(Assertion assertion : a.getAssertions()) {
				result.append("[").append(assertion.getStatusMessage()).append("]").append(" ");
				result.append("Assertion ").append(assertion.getCondition()).append("=").append(assertion.getExpected());
				result.append("\n");
			}
			result.append("\n");
		}
		
		if(a.getRequest() != null) {
			result.append("Request: \n");
			
			StringWriter requestWriter = new StringWriter();
			Result r = new StreamResult(requestWriter);
			t.transform(new DOMSource(a.getRequest()), r);
			result.append(requestWriter.toString());
			result.append("\n\n");
		}
		
		if(a.getRequest() != null) {
			result.append("Response: \n");
			
			StringWriter responseWriter = new StringWriter();
			Result r = new StreamResult(responseWriter);
			t.transform(new DOMSource(a.getResponse()), r);
			result.append(responseWriter.toString());
			result.append("\n\n");
		}
		
		return result.toString();
	}

	private static void addColorsForStatus(Collaboration collaboration) {
		
		ITestColorScheme colorScheme = new DefaultColorScheme();
		
		for(Participant p : collaboration.getParticipants()) {
			PassStatus psParticipant = getPassStatusFromExtension(p);
			if(p.getProcess() != null) {
				if(psParticipant != null) {
					BpmnShape putShape = p.getLayouts().getDiagramElement();
					putShape.setBorderColor(colorScheme.getBorderColorForPassStatus(psParticipant));
					if(psParticipant == PassStatus.ERROR || psParticipant == PassStatus.FAILED) {
						putShape.setBackgroundColor(colorScheme.getBackgroundColorForPassStatus(psParticipant));
					}
				}
				for(FlowElement fe : p.getProcess().getFlowElements()) {
					if(fe instanceof FlowNode) {
						FlowNode fn = (FlowNode)fe;
						BpmnShape shape = fn.getLayouts().getDiagramElement();
						PassStatus psFlowNode = getPassStatusFromExtension(fe);
						shape.setBorderColor(colorScheme.getBorderColorForPassStatus(psFlowNode));
						shape.setBackgroundColor(colorScheme.getBackgroundColorForPassStatus(psFlowNode));
					}
				}
//			} else {
//				if(psParticipant != null) {
//					BpmnShape putShape = p.getLayouts().getDiagramElement();
//					putShape.setBorderColor(colorScheme.getBorderColorForPassStatus(psParticipant));
//					putShape.setBackgroundColor(colorScheme.getBackgroundColorForPassStatus(psParticipant));
//				}
			}
		}
	}

	private static PassStatus getPassStatusFromExtension(BaseElement fe) {
		for(Object extension : fe.getExtensions()) {
			if(extension instanceof PassStatus) {
				return (PassStatus)extension;
			}
		}
		return null;
	}
	
	private static String getMessageElementName(Element soapEnvelope) {
		if(soapEnvelope != null) {
			Element soapBody = null;
			NodeList envelopeChildren = soapEnvelope.getChildNodes();
			for(int i = 0; i < envelopeChildren.getLength(); i++) {
				Node n = envelopeChildren.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE && "Body".equals(n.getLocalName())) {
					soapBody = (Element)n;
					break;
				}
			}
			
			NodeList bodyChildren = soapBody.getChildNodes();
			for(int i = 0; i < bodyChildren.getLength(); i++) {
				Node n = bodyChildren.item(i);
				if(n.getNodeType() == Node.ELEMENT_NODE) {
					return n.getLocalName();
				}
			}
			
			return null;
		} else {
			return null;
		}
	}

	private static String generateId(String idCandidate) {
		return "id-" + idCandidate.replaceAll("[^A-Za-z0-9_]", "-");
	}
}
