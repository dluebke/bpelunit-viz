package com.digitalsolutionarchitecture.bpmn.layout.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnDiagram;
import com.digitalsolutionarchitecture.bpmn.di.bpmn.BpmnShape;
import com.digitalsolutionarchitecture.bpmn.io.BpmnXsdModelWriter;
import com.digitalsolutionarchitecture.bpmn.model.activity.ServiceTask;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BpmnModel;
import com.digitalsolutionarchitecture.bpmn.model.process.Process;

public class SimpleHorizontalLayouterTest {

	private SimpleHorizontalLayouter layouter = new SimpleHorizontalLayouter();
	
	@Test
	public void test1Pool1Activity() throws FileNotFoundException, IOException {
		BpmnModel model = new BpmnModel();
		Collaboration collaboration = new Collaboration("_Collaboration");
		model.setCollaboration(collaboration);
		Participant participant1 = new Participant("_Participant1");
		collaboration.getParticipants().add(participant1);
		Process process1 = new Process("_Process1");
		participant1.setProcess(process1);
		ServiceTask serviceTask1 = new ServiceTask("_ServiceTask1", process1);
		process1.getFlowElements().add(serviceTask1);
		
		layouter.layout(collaboration);
		BpmnDiagram result = model.getDiagrams().get(0);
		
		assertEquals(1, result.getPlanes().size());
		assertSame(result.getPlanes().get(0).getBpmnElement(), collaboration);
		assertEquals(2, result.getPlanes().get(0).getShapes().size());
		assertEquals(0, result.getPlanes().get(0).getEdges().size());
		assertSame(participant1, result.getPlanes().get(0).getShapes().get(0).getBpmnElement());
		
		BpmnShape serviceTaskDiagramElement = result.getPlanes().get(0).getShapes().get(1);
		assertSame(serviceTask1, serviceTaskDiagramElement.getBpmnElement());
		assertEquals(85.0, serviceTaskDiagramElement.getBounds().getX(), 0.001);
		assertEquals(62.5, serviceTaskDiagramElement.getBounds().getY(), 0.001);
		assertEquals(105.0, serviceTaskDiagramElement.getBounds().getWidth(), 0.001);
		assertEquals(80.0, serviceTaskDiagramElement.getBounds().getHeight(), 0.001);
		
		outputDiagram(model, result, "test1Pool1Activity");
	}

	
	@Test
	public void test1Pool2Activities() throws FileNotFoundException, IOException {
		BpmnModel model = new BpmnModel();
		Collaboration collaboration = new Collaboration("_Collaboration");
		model.setCollaboration(collaboration);
		Participant participant1 = new Participant("_Participant1");
		collaboration.getParticipants().add(participant1);
		Process process1 = new Process("_Process1");
		participant1.setProcess(process1);
		ServiceTask serviceTask1 = new ServiceTask("_ServiceTask1", process1);
		process1.getFlowElements().add(serviceTask1);
		ServiceTask serviceTask2 = new ServiceTask("_ServiceTask2", process1);
		process1.getFlowElements().add(serviceTask2);
		SequenceFlow sf = new SequenceFlow("_SF_1_2", process1);
		sf.setSource(serviceTask1);
		sf.setTarget(serviceTask2);
		process1.getFlowElements().add(sf);
		
		layouter.layout(collaboration);
		BpmnDiagram result = model.getDiagrams().get(0);
		
		assertEquals(1, result.getPlanes().size());
		assertSame(result.getPlanes().get(0).getBpmnElement(), collaboration);
		assertEquals(3, result.getPlanes().get(0).getShapes().size());
		assertEquals(1, result.getPlanes().get(0).getEdges().size());
		assertSame(participant1, result.getPlanes().get(0).getShapes().get(0).getBpmnElement());
		
		BpmnShape serviceTaskDiagramElement = result.getPlanes().get(0).getShapes().get(1);
		assertSame(serviceTask1, serviceTaskDiagramElement.getBpmnElement());
		assertEquals(85.0, serviceTaskDiagramElement.getBounds().getX(), 0.001);
		assertEquals(62.5, serviceTaskDiagramElement.getBounds().getY(), 0.001);
		assertEquals(105.0, serviceTaskDiagramElement.getBounds().getWidth(), 0.001);
		assertEquals(80.0, serviceTaskDiagramElement.getBounds().getHeight(), 0.001);
		
		BpmnShape serviceTask2DiagramElement = result.getPlanes().get(0).getShapes().get(2);
		assertSame(serviceTask2, serviceTask2DiagramElement.getBpmnElement());
		assertEquals(235.0, serviceTask2DiagramElement.getBounds().getX(), 0.001);
		assertEquals(62.5, serviceTask2DiagramElement.getBounds().getY(), 0.001);
		assertEquals(105.0, serviceTask2DiagramElement.getBounds().getWidth(), 0.001);
		assertEquals(80.0, serviceTask2DiagramElement.getBounds().getHeight(), 0.001);
		
		outputDiagram(model, result, "test1Pool2Activities");
	}
	
	private void outputDiagram(BpmnModel model, BpmnDiagram result,
			String testCaseName) throws IOException, FileNotFoundException {
		BpmnXsdModelWriter writer = new BpmnXsdModelWriter();
		writer.write(model, new FileOutputStream("target/" + testCaseName + ".bpmn"));
	}

}
