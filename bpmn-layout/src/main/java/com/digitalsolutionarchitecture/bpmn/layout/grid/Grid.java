package com.digitalsolutionarchitecture.bpmn.layout.grid;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> {

	List<List<T>> cells = new ArrayList<>();
	
	public Grid() {
		cells.add(new ArrayList<>());
		cells.get(0).add(null);
	}
	
	public int getWidth() {
		return cells.size();
	}
	
	public int getHeight() {
		return cells.get(0).size();
	}
	
	public T getValue(int x, int y) {
		return cells.get(x).get(y);
	}
	
	public void setValue(T value, int x, int y) {
		while(x >= getWidth()) {
			addColumn();
		}
		
		while(y >= getHeight()) {
			addRow();
		}
		
		T cellValue = cells.get(x).get(y);
		if(cellValue == null) {
			cells.get(x).remove(y);
			cells.get(x).add(y, value);
		} else {
			throw new IllegalStateException("Cell " + x + "/" + y + " already has a value! Old value: " + cellValue + "; new value: " + value);
		}
	}

	private void addColumn() {
		ArrayList<T> newColumn = new ArrayList<>();
		cells.add(newColumn);
		for(int i = 0; i < getHeight(); i++) {
			newColumn.add(null);
		}
	}
	
	private void addRow() {
		for(List<T> column : cells) {
			column.add(null);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int y = 0; y < getHeight(); y++) {
			for(int x = 0; x < getWidth(); x++) {
				sb.append(getValue(x, y)).append("\t");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

	public void optimize() {
		for(int y = 0; y < getHeight(); y++) {
			boolean isEmpty = true;
			for(int x = 0; x < getWidth(); x++) {
				if(getValue(x, y) != null) {
					isEmpty = false;
					break;
				}
			}
			if(isEmpty) {
				removeRow(y);
				y--;
			}
		}
		
		for(int x = 0; x < getHeight(); x++) {
			boolean isEmpty = true;
			for(int y = 0; y < getHeight(); y++) {
				if(getValue(x, y) != null) {
					isEmpty = false;
					break;
				}
			}
			if(isEmpty) {
				removeCol(x);
				x--;
			}
		}
	}

	private void removeCol(int x) {
		cells.remove(x);
	}

	private void removeRow(int row) {
		for(List<T> r : cells) {
			r.remove(row);
		}
	}
}
