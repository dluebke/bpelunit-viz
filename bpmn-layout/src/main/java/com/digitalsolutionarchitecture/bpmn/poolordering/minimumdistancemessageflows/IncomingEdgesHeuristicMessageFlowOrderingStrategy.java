package com.digitalsolutionarchitecture.bpmn.poolordering.minimumdistancemessageflows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.InteractionNode;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.MessageFlow;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.process.Process;
import com.digitalsolutionarchitecture.bpmn.poolordering.PoolOrderingStrategy;
import com.digitalsolutionarchitecture.bpmn.util.Counters;

public class IncomingEdgesHeuristicMessageFlowOrderingStrategy implements PoolOrderingStrategy {

	private static final ParticipantBySizeAndNameComparator PARTICIPANT_BY_SIZE_AND_NAME_COMPARATOR = new ParticipantBySizeAndNameComparator();

	public static class ParticipantBySizeAndNameComparator implements Comparator<Participant> {

		@Override
		public int compare(Participant x, Participant y) {
			if(getFlowElementsInParticipant(x) != getFlowElementsInParticipant(y)) {
				return -Integer.compare(getFlowElementsInParticipant(x), getFlowElementsInParticipant(y));
			} else {
				return x.getName().toLowerCase().compareTo(y.getName().toLowerCase());
			}
		}

		private int getFlowElementsInParticipant(Participant x) {
			return x.getProcess() != null ? x.getProcess().getFlowElements().size() : 0;
		}

	}

	@Override
	public List<Participant> reorder(Collaboration c) {
		
		List<Participant> remainingParticipants = new ArrayList<>(c.getParticipants());
		List<Participant> result = new ArrayList<>();
		
		Participant p = getParticipantWithMostMessageFlows(c);
		result.add(p);
		remainingParticipants.remove(p);
		
		boolean addToTop = false;
		while(!remainingParticipants.isEmpty()) {
			p = getParticipantWithMostMessageFlowsWithSelectedSet(remainingParticipants, c.getMessageFlows(), result, c);
			if(addToTop) {
				result.add(0, p);
			} else {
				result.add(p);
			}
			remainingParticipants.remove(p);
			
			addToTop = !addToTop;
		}
		
		return result;
	}

	static Participant getParticipantWithMostMessageFlowsWithSelectedSet(List<Participant> participants, List<MessageFlow> messageFlows, List<Participant> selectedSet, Collaboration c) {
		Counters<Participant> counters = new Counters<>();
		
		for(MessageFlow mf : messageFlows) {
			Participant sourceParticipant = getContainingParticipant(c, mf.getSource());
			Participant targetParticipant = getContainingParticipant(c, mf.getTarget());
			boolean sourceInParticipants = participants.contains(sourceParticipant);
			boolean targetInParticipants = participants.contains(targetParticipant);
			
			if(!(sourceInParticipants && targetInParticipants)) {
				if(sourceInParticipants) {
					counters.inc(sourceParticipant);
				}
				if(targetInParticipants) {
					counters.inc(targetParticipant);
				}
			}
		}
		
		List<Participant> participantWithMostMessageFlows = counters.getKeyWithHighestCounts();
		if(participantWithMostMessageFlows.size() == 0) {
			return participants.get(0);
		}
		if(participantWithMostMessageFlows.size() > 1) {
			Collections.sort(participantWithMostMessageFlows, PARTICIPANT_BY_SIZE_AND_NAME_COMPARATOR);
		}
		return participantWithMostMessageFlows.get(0);
	}

	static Participant getParticipantWithMostMessageFlows(Collaboration c) {
		Counters<Participant> targetCounters = new Counters<>();
		for(MessageFlow mf : c.getMessageFlows()) {
			targetCounters.inc(getContainingParticipant(c, mf.getSource()));
			targetCounters.inc(getContainingParticipant(c, mf.getTarget()));
		}
		List<Participant> participantsWithHighestCount = targetCounters.getKeyWithHighestCounts();
		if(participantsWithHighestCount.size() > 1) {
			Collections.sort(participantsWithHighestCount, PARTICIPANT_BY_SIZE_AND_NAME_COMPARATOR);
		}
		return participantsWithHighestCount.get(0);
	}

	static Participant getContainingParticipant(Collaboration c, InteractionNode n) {
		if(n instanceof Participant) {
			return (Participant)n;
		} else if(n instanceof FlowElement){
			 Process process = (Process)((FlowElement)n).getParent();
			for(Participant p : c.getParticipants()) {
				if(p.getProcess() == process) {
					return p;
				}
			}
		}
		return null;
	}
}
