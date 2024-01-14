package com.digitalsolutionarchitecture.bpmn.layout.grid;

import static org.junit.Assert.*;

import org.junit.Test;

public class GridTest {

	@Test
	public void testCreation() {
		Grid<String> g = new Grid<String>();
		
		assertEquals(1, g.getHeight());
		assertEquals(1, g.getWidth());
		
		assertNull(g.getValue(0, 0));
	}
	
	@Test
	public void testInsertExistingEmptyCell() throws Exception {
		Grid<String> g = new Grid<String>();

		g.setValue("A", 0, 0);
		assertEquals("A", g.getValue(0, 0));
	}
	
	@Test
	public void testInsertNewCell() throws Exception {
		Grid<String> g = new Grid<String>();
		
		g.setValue("A", 2, 1);
		assertEquals("A", g.getValue(2, 1));
	}

	@Test(expected=IllegalStateException.class)
	public void testInsertExistingNonEmptyCell() throws Exception {
		Grid<String> g = new Grid<String>();

		g.setValue("A", 0, 0);
		g.setValue("B", 0, 0);
	}
	
}
