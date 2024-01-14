package com.digitalsolutionarchitecture.bpmn.poolordering;

import java.util.ArrayList;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;

public enum SimplePoolOrderingStrategy implements PoolOrderingStrategy {

	NO_REORDER {
		@Override
		public List<Participant> reorder(Collaboration c) {
			return c.getParticipants();
		}
	},
	
	COLLAPSED_AROUND {
		@Override
		public List<Participant> reorder(Collaboration c) {
			List<Participant> result = new ArrayList<>(c.getParticipants().size());
			result.addAll(filterExpandedParticipants(c.getParticipants()));
			
			boolean onTop = true;
			for(Participant p : filterCollapsedParticipants(c.getParticipants())) {
				if(onTop) {
					result.add(0, p);
				} else {
					result.add(p);
				}
				onTop = !onTop;
			}
			
			return result;
		}
	},
	
	EXPANDED_AROUND {
		@Override
		public List<Participant> reorder(Collaboration c) {
			List<Participant> result = new ArrayList<>(c.getParticipants().size());
			result.addAll(filterCollapsedParticipants(c.getParticipants()));
			
			boolean onTop = true;
			for(Participant p : filterExpandedParticipants(c.getParticipants())) {
				if(onTop) {
					result.add(0, p);
				} else {
					result.add(p);
				}
				onTop = !onTop;
			}
			
			return result;
		}
	}
	;
	
	private static List<Participant> filterCollapsedParticipants(List<Participant> participants) {
		List<Participant> result = new ArrayList<>();
		
		for(Participant p : participants) {
			if(p.getProcess() == null) {
				result.add(p);
			}
		}
		
		return result;
	}
	
	private static List<Participant> filterExpandedParticipants(List<Participant> participants) {
		List<Participant> result = new ArrayList<>();
		
		for(Participant p : participants) {
			if(p.getProcess() != null) {
				result.add(p);
			}
		}
		
		return result;
	}

	
}
