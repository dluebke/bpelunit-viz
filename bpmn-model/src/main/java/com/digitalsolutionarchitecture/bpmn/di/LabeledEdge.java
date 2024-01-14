package com.digitalsolutionarchitecture.bpmn.di;

import java.util.ArrayList;
import java.util.List;

import com.digitalsolutionarchitecture.bpmn.model.common.Point;

public class LabeledEdge extends DiagramElement {

	public LabeledEdge(String id) {
		super(id);
	}
	

	public void addWayPoint(double x, double y) {
		waypoints.add(new Point(x, y));
	}
	
	public List<Point> getWaypoints() {
		return waypoints;
	}
	
	private List<Point> waypoints = new ArrayList<>();
}
