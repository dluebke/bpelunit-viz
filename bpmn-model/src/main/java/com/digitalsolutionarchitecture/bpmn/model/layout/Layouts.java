package com.digitalsolutionarchitecture.bpmn.model.layout;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.digitalsolutionarchitecture.bpmn.di.DiagramElement;

public abstract class Layouts<T extends DiagramElement> {

	public static final String DEFAULT_DIAGRAM_ID = "_DEFAULT_";
	private Map<String, T> diagramElements = new HashMap<>();

	public T getDiagramElement() {
		return getDiagramElement(DEFAULT_DIAGRAM_ID);
	}

	public T getDiagramElement(String diagramId) {
		T result = diagramElements.get(diagramId);
		if(result == null) {
			result = createNewDiagramElement();
			diagramElements.put(diagramId, result);
		}
		return result;
	}

	public Set<String> getDiagramIds() {
		return diagramElements.keySet();
	}
	
	protected abstract T createNewDiagramElement();
}
