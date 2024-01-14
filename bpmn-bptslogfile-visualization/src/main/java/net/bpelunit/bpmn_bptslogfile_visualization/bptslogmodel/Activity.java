package net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

public class Activity implements WithPassState {

	public enum ActivityType {
		SEND_RECEIVE_SYNC,
		RECEIVE_SEND_SYNC,
		SEND_ASYNC,
		RECEIVE_ASYNC,
		SEND_RECEIVE_ASYNC,
		RECEIVE_SEND_ASYNC,
		WAIT,
		COMPLETE_HUMAN_TASK
		;
		
		public boolean isInitiallySending() {
			return this == COMPLETE_HUMAN_TASK || this == SEND_ASYNC || this == SEND_RECEIVE_ASYNC || this == SEND_RECEIVE_SYNC;
		}
		
		public boolean isInitiallyReceiving() {
			return this == RECEIVE_ASYNC || this == RECEIVE_SEND_ASYNC || this == RECEIVE_SEND_SYNC;
		}
		
	}

	private String name;
	private Element request;
	private Element response;
	private List<Assertion> assertions = new ArrayList<>();
	private PassStatus passState;
	private String statusMessage;
	private String exception;
	private ActivityType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Element getRequest() {
		return request;
	}

	public void setRequest(Element request) {
		this.request = request;
	}

	public Element getResponse() {
		return response;
	}

	public void setResponse(Element response) {
		this.response = response;
	}

	public List<Assertion> getAssertions() {
		return assertions;
	}
	
	@Override
	public PassStatus getPassStatus() {
		return passState;
	}

	@Override
	public void setPassStatus(PassStatus passState) {
		this.passState = passState;
	}

	@Override
	public String getStatusMessage() {
		return statusMessage;
	}

	@Override
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String getException() {
		return exception;
	}

	@Override
	public void setException(String exception) {
		this.exception = exception;
	}

	public ActivityType getType() {
		return type;
	}
	
	public void setType(ActivityType type) {
		this.type = type;
	}
	
	public boolean isCalling() {
		return
			this.type == ActivityType.SEND_ASYNC ||
			this.type == ActivityType.SEND_RECEIVE_ASYNC ||
			this.type == ActivityType.SEND_RECEIVE_SYNC
		;
	}
}
