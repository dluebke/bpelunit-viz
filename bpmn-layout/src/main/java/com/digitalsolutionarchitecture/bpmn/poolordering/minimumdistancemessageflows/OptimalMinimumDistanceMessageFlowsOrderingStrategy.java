package com.digitalsolutionarchitecture.bpmn.poolordering.minimumdistancemessageflows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.InteractionNode;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.MessageFlow;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.process.Process;
import com.digitalsolutionarchitecture.bpmn.poolordering.PoolOrderingStrategy;
import com.digitalsolutionarchitecture.bpmn.util.permut.HeapsPermutator;
import com.digitalsolutionarchitecture.bpmn.util.permut.PermutatorCallback;

public class OptimalMinimumDistanceMessageFlowsOrderingStrategy implements PoolOrderingStrategy {

	@Override
	public List<Participant> reorder(Collaboration c) {
		
		List<Participant> participants = c.getParticipants();
		
		final int[][] messageFlowsBetweenParticipants = countMessageFlows(c);
		final int[] bestDistance = new int[] { Integer.MAX_VALUE };
		final List<Integer> bestPositions = new ArrayList<>();
		
		Integer[] indexPositions = new Integer[participants.size()];
		for(int i = 0; i < indexPositions.length; i++) {
			indexPositions[i] = i;
		}
		new HeapsPermutator<Integer>().generate(indexPositions, new PermutatorCallback<Integer>() {
			
			@Override
			public void newPermutation(Integer[] result) {
				int x = calculateTotalMessageFlowDistance(messageFlowsBetweenParticipants, result);
				if(x < bestDistance[0]) {
					bestDistance[0] = x;
					bestPositions.clear();
					bestPositions.addAll(Arrays.asList(result));
				}
			}
		});
		
		List<Participant> result = new ArrayList<>(participants.size());
		for(Integer i : bestPositions) {
			result.add(participants.get(i));
		}
		return result;
	}

	int calculateTotalMessageFlowDistance(int[][] messageFlowsBetweenParticipants, Integer[] participantPositions) {
		int result = 0;
		
		int[] indexOf = new int[participantPositions.length];
		for(int i = 0; i < participantPositions.length; i++) {
			indexOf[i] = indexOf(participantPositions, i);
		}
		for(int x = 0; x < messageFlowsBetweenParticipants.length; x++) {
			for(int y = x + 1; y < messageFlowsBetweenParticipants.length; y++) {
				int distance = Math.abs((indexOf[y] - indexOf[x]));
				result += distance * messageFlowsBetweenParticipants[x][y];
			}
		}
		
		return result;
	}
	
	int indexOf(Integer[] participantPositions, int x) {
		for(int i = 0; i < participantPositions.length; i++) {
			if(participantPositions[i] == x) {
				return i;
			}
		}
		return -1;
	}

	private int[][] countMessageFlows(Collaboration c) {
		List<Participant> participants = c.getParticipants();
		int[][] edges = new int[participants.size()][participants.size()];
		
		for(MessageFlow mf : c.getMessageFlows()) {
			Participant sourceParticipant = getContainingParticipant(c, mf.getSource());
			Participant targetParticipant = getContainingParticipant(c, mf.getTarget());
			
			int sourceIndex = participants.indexOf(sourceParticipant);
			int targetIndex = participants.indexOf(targetParticipant);
			edges[sourceIndex][targetIndex]++;
			edges[targetIndex][sourceIndex]++;
		}
		return edges;
	}

	private Participant getContainingParticipant(Collaboration c, InteractionNode source) {
		if(source instanceof Participant) {
			return (Participant)source;
		} else if(source instanceof FlowElement){
			Process process = (Process)((FlowElement)source).getParent();
			for(Participant p : c.getParticipants()) {
				if(p.getProcess() == process) {
					return p;
				}
			}
		}
		return null;
	}
}
