package com.digitalsolutionarchitecture.bpmn.model.events;

import com.digitalsolutionarchitecture.bpmn.model.common.Message;

public class MessageEventDefinition extends EventDefinition {

	private Message message;
	
	public MessageEventDefinition(String id) {
		super(id);
	}

	
	public void setMessage(Message message) {
		this.message = message;
	}
	
	public Message getMessage() {
		return message;
	}
	
}
