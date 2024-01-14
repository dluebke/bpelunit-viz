package com.digitalsolutionarchitecture.bpmn.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.digitalsolutionarchitecture.bpmn.model.collaboration.Participant;
import com.digitalsolutionarchitecture.bpmn.model.common.SequenceFlow;
import com.digitalsolutionarchitecture.bpmn.model.foundation.BpmnModel;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowElement;
import com.digitalsolutionarchitecture.bpmn.model.foundation.FlowNode;

public class TextBpmnModelWriter implements BpmnModelWriter {

	private String charset = "UTF-8";

	public String getCharset() {
		return charset;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	@Override
	public void write(BpmnModel model, OutputStream o) throws IOException {
		Writer out = new OutputStreamWriter(o, charset);
		for(Participant p : model.getCollaboration().getParticipants()) {
			out.write(p.getName() + ":\n");
			
			if(p.getProcess() != null) {
				for(FlowElement e : p.getProcess().getFlowElements()) {
					out.write("  ");
					out.write(e.getName() != null ? e.getName() : "<null>");
					out.write(" [id=");
					out.write(e.getId());
					if(e instanceof FlowNode) {
						FlowNode n = (FlowNode)e;
						if(!n.getIncoming().isEmpty()) {
							out.write(", incoming:");
							for(SequenceFlow s : n.getIncoming()) {
								out.write(" ");
								out.write(s.getSource().getId());
							}
						}
						if(!n.getOutgoing().isEmpty()) {
							out.write(", outgoing:");
							for(SequenceFlow s : n.getOutgoing()) {
								out.write(" ");
								out.write(s.getTarget().getId());
							}
						}
					}
					out.write("]\n");
				}
			}
			out.write("\n");
		}
		out.flush();
	}

	@Override
	public String getFileSuffix() {
		return ".txt";
	}
	
	@Override
	public String getMimeType() {
		return "text/plain";
	}
}
