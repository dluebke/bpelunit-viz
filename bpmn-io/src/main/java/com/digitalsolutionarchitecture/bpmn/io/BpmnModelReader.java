package com.digitalsolutionarchitecture.bpmn.io;

import java.io.InputStream;

import com.digitalsolutionarchitecture.bpmn.model.foundation.BpmnModel;

public interface BpmnModelReader {
	BpmnModel readModel(InputStream in);
}
