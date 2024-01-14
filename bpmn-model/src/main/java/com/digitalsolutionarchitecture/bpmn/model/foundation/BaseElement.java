package com.digitalsolutionarchitecture.bpmn.model.foundation;

import java.util.ArrayList;
import java.util.List;

public class BaseElement {

	private String id;
	private List<Documentation> documentation;
	private List<Object> extensions;
	
	public BaseElement(String id) {
		setId(id);
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Documentation> getDocumentation() {
		synchronized(this) {
			if(documentation == null) {
				documentation = new ArrayList<>();
			}
		}
		return documentation;
	}

	public boolean hasDocumentation() {
		return documentation != null;
	}
	
	public List<Object> getExtensions() {
		synchronized(this) {
			if(extensions == null) {
				extensions = new ArrayList<>();
			}
		}
		return extensions;
	}
	
	public boolean hasExtensions() {
		return extensions != null;
	}
}
