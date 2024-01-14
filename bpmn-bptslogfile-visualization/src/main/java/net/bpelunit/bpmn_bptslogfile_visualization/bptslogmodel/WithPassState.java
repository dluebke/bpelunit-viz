package net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel;

public interface WithPassState {

	public enum PassStatus {
		PASSED,
		ERROR,
		FAILED,
		ABORTED,
		NOTYETSPECIFIED
	}
	
	public PassStatus getPassStatus();
	public String getStatusMessage();
	public String getException();
	public void setPassStatus(PassStatus valueOf);
	public void setStatusMessage(String textContent);
	public void setException(String textContent);
	
	
}
