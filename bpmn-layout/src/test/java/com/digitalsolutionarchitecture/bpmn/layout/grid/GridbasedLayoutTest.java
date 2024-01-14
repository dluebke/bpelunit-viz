package com.digitalsolutionarchitecture.bpmn.layout.grid;

import static org.junit.Assert.*;

import org.junit.Test;

import com.digitalsolutionarchitecture.bpmn.model.activity.ServiceTask;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowNode;

public class GridbasedLayoutTest {

	@Test
	public void testSimpleSequence() {
		GridbasedLayout l = new GridbasedLayout();
		
		Collaboration model = new Collaboration("Collaboration1");
		Participant participant = new Participant("Participant1", "Name1");
		model.getParticipants().add(participant);
		com.digitalsolutionarchitecture.bpmn.model.process.Process process = new com.digitalsolutionarchitecture.bpmn.model.process.Process("Process1");
		participant.setProcess(process);
		ServiceTask task1 = new ServiceTask("Task1", process);
		process.getFlowElements().add(task1);
		ServiceTask task2 = new ServiceTask("Task2", process);
		process.getFlowElements().add(task2);
		ServiceTask task3 = new ServiceTask("Task3", process);
		process.getFlowElements().add(task3);
		
		process.getFlowElements().add(new SequenceFlow("SequenceFlow1", task1, task2, process));
		process.getFlowElements().add(new SequenceFlow("SequenceFlow2", task2, task3, process));
		
		Grid<FlowNode> grid = l.layoutGrid(process);
		System.out.println(grid);
		assertSame(task1, grid.getValue(0, 0));
		assertSame(task2, grid.getValue(1, 0));
		assertSame(task3, grid.getValue(2, 0));
	}
	
	@Test
	public void testSimpleLoop() {
		GridbasedLayout l = new GridbasedLayout();
		
		Collaboration model = new Collaboration("Collaboration1");
		Participant participant = new Participant("Participant1", "Name1");
		model.getParticipants().add(participant);
		com.digitalsolutionarchitecture.bpmn.model.process.Process process = new com.digitalsolutionarchitecture.bpmn.model.process.Process("Process1");
		participant.setProcess(process);
		ServiceTask task1 = new ServiceTask("Task1", process);
		process.getFlowElements().add(task1);
		ServiceTask task2 = new ServiceTask("Task2", process);
		process.getFlowElements().add(task2);
		ServiceTask task3 = new ServiceTask("Task3", process);
		process.getFlowElements().add(task3);
		
		process.getFlowElements().add(new SequenceFlow("SequenceFlow1", task1, task3, process));
		process.getFlowElements().add(new SequenceFlow("SequenceFlow3", task2, task3, process));
		process.getFlowElements().add(new SequenceFlow("SequenceFlow2", task3, task2, process));
		
		Grid<FlowNode> grid = l.layoutGrid(process);
		System.out.println(grid);
		assertSame(task1, grid.getValue(0, 0));
		assertSame(task3, grid.getValue(1, 0));
		assertSame(task2, grid.getValue(2, 0));
	}
	
	@Test
	public void testSimpleParallelFlow() {
		GridbasedLayout l = new GridbasedLayout();
		
		Collaboration model = new Collaboration("Collaboration1");
		Participant participant = new Participant("Participant1", "Name1");
		model.getParticipants().add(participant);
		com.digitalsolutionarchitecture.bpmn.model.process.Process process = new com.digitalsolutionarchitecture.bpmn.model.process.Process("Process1");
		participant.setProcess(process);
		ServiceTask task1 = new ServiceTask("Task1", process);
		process.getFlowElements().add(task1);
		ServiceTask task2 = new ServiceTask("Task2", process);
		process.getFlowElements().add(task2);
		ServiceTask task3 = new ServiceTask("Task3", process);
		process.getFlowElements().add(task3);
		ServiceTask task4 = new ServiceTask("Task4", process);
		process.getFlowElements().add(task4);
		
		process.getFlowElements().add(new SequenceFlow("SequenceFlow1", task1, task2, process));
		process.getFlowElements().add(new SequenceFlow("SequenceFlow2", task1, task3, process));
		process.getFlowElements().add(new SequenceFlow("SequenceFlow3", task2, task4, process));
		process.getFlowElements().add(new SequenceFlow("SequenceFlow4", task3, task4, process));
		
		Grid<FlowNode> grid = l.layoutGrid(process);
		System.out.println(grid);
		assertSame(task1, grid.getValue(0, 1));
		assertSame(task2, grid.getValue(1, 0));
		assertSame(task3, grid.getValue(1, 2));
		assertSame(task4, grid.getValue(2, 1));
	}

}
