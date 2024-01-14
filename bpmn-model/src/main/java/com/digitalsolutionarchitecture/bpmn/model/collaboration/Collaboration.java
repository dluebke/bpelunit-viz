package com.digitalsolutionarchitecture.bpmn.model.collaboration;

import java.util.ArrayList;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.foundation.RootElement;


public class Collaboration extends RootElement {

	private List<Participant> participants = new ArrayList<>();
	private String name;
	private Boolean isClosed;
	private List<MessageFlow> messageFlows = new ArrayList<>();
	
	public Collaboration(String id) {
		super(id);
	}
	
	public List<Participant> getParticipants() {
		return participants;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean isClosed() {
		return isClosed;
	}
	
	public void setClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	public List<MessageFlow> getMessageFlows() {
		return messageFlows;
	}
}
