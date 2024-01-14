package net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel;


public class Assertion implements WithPassState {

	private String condition;
	private String expected;
	private String actual;
	private PassStatus passStatus;
	private String statusMessage;
	private String exception;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getActual() {
		return actual;
	}

	@Override
	public PassStatus getPassStatus() {
		return passStatus;
	}

	@Override
	public String getStatusMessage() {
		return statusMessage;
	}

	@Override
	public String getException() {
		return exception;
	}

	@Override
	public void setPassStatus(PassStatus passStatus) {
		this.passStatus = passStatus;
	}

	@Override
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public void setException(String exception) {
		this.exception = exception;
	}

}
