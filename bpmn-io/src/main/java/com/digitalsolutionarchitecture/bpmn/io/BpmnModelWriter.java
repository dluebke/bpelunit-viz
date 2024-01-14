package com.digitalsolutionarchitecture.bpmn.io;

import java.io.IOException;
import java.io.OutputStream;

import com.digitalsolutionarchitecture.bpmn.model.foundation.BpmnModel;

public interface BpmnModelWriter {
	public void write(BpmnModel model, OutputStream out) throws IOException;
	public String getFileSuffix();
	public String getMimeType();
}
