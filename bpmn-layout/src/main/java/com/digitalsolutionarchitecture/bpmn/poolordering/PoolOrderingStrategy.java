package com.digitalsolutionarchitecture.bpmn.poolordering;

import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.collaboration.Collaboration;
import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;

public interface PoolOrderingStrategy {
	List<Participant> reorder(Collaboration c);
}
