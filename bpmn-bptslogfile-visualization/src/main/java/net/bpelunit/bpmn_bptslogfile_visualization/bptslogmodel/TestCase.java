package net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel;

import java.util.ArrayList;
import java.util.List;

public class TestCase implements WithPassState {

	private String name;
	private List<PartnerTrack> partnerTracks = new ArrayList<>();
	private PassStatus passState;
	private String statusMessage;
	private String exception;

	@Override
	public PassStatus getPassStatus() {
		return passState;
	}

	@Override
	public void setPassStatus(PassStatus passState) {
		this.passState = passState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PartnerTrack> getPartnerTracks() {
		return partnerTracks;
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
}
