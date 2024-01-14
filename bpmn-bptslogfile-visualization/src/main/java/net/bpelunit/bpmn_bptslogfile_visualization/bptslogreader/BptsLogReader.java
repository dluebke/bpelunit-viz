package net.bpelunit.bpmn_bptslogfile_visualization.bptslogreader;

import java.io.IOException;

import net.bpelunit.bpmn_bptslogfile_visualization.bptslogmodel.TestSuite;

public interface BptsLogReader {

	public TestSuite readLog(String logId) throws IOException; 
}
