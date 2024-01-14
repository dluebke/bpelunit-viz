package net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel;

import java.util.ArrayList;
import java.util.List;

public class PartnerTrack implements WithPassState {

	public enum Type {
		SERVICE, HUMAN
	}

	private String name;
	private Type type = Type.SERVICE;
	private PassStatus passState;
	private String statusMessage;
	private String exception;
	private List<Activity> activities = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public PassStatus getPassStatus() {
		return passState;
	}

	@Override
	public String getStatusMessage() {
		return statusMessage;
	}

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

	@Override
	public void setPassStatus(PassStatus passState) {
		this.passState = passState;
	}

	public List<Activity> getActivities() {
		return activities;
	}
}
