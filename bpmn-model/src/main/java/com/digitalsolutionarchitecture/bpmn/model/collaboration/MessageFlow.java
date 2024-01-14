package com.digitalsolutionarchitecture.bpmn.model.collaboration;

import com.digitalsolutionarchitecture.bpmn.model.common.Message;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BaseElement;
import com.digitalsolutionarchitecture.bpmn.model.layout.BpmnEdgeLayouts;

public class MessageFlow extends BaseElement {

	private String name;
	private Message message;
	private InteractionNode source;
	private InteractionNode target;
	private BpmnEdgeLayouts layouts = new BpmnEdgeLayouts(this);
	
	public MessageFlow(String id) {
		super(id);
	}

	public MessageFlow(String id, InteractionNode source,
			InteractionNode target) {
		this(id);
		setSource(source);
		setTarget(target);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public InteractionNode getSource() {
		return source;
	}

	public void setSource(InteractionNode source) {
		this.source = source;
	}

	public InteractionNode getTarget() {
		return target;
	}

	public void setTarget(InteractionNode target) {
		this.target = target;
	}

	public BpmnEdgeLayouts getLayouts() {
		return layouts;
	}
}
