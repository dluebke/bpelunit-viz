package net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel;

import java.util.ArrayList;
import java.util.List;

public class TestSuite implements WithPassState {

	private List<TestCase> testCases = new ArrayList<>();
	private PassStatus passState;
	private String name;
	private String statusMessage;
	private String exception;

	@Override
	public PassStatus getPassStatus() {
		return passState;
	}

	public void setPassStatus(PassStatus passState) {
		this.passState = passState;
	}

	public List<TestCase> getTestCases() {
		return testCases;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setException(String exception) {
		this.exception = exception;
	}

}
