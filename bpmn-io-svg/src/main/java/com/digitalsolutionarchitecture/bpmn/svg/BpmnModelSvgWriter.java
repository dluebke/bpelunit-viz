package com.digitalsolutionarchitecture.bpmn.svg;

import java.io.OutputStream;

import com.digitalsolutionarchitecture.bpmn.io.BpmnModelWriter;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BpmnModel;

public class BpmnModelSvgWriter implements BpmnModelWriter {
	
	@Override
	public void write(BpmnModel model, OutputStream out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFileSuffix() {
		return ".svg";
	}
	
	@Override
	public String getMimeType() {
		return "image/svg+xml";
	}
}
