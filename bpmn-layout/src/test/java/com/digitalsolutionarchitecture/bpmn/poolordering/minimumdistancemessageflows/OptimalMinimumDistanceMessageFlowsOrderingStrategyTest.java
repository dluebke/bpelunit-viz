package com.digitalsolutionarchitecture.bpmn.poolordering.minimumdistancemessageflows;

import static org.junit.Assert.*;

import org.junit.Test;

import com.digitalsolutionarchitecture.bpmn.poolordering.minimumdistancemessageflows.OptimalMinimumDistanceMessageFlowsOrderingStrategy;

public class OptimalMinimumDistanceMessageFlowsOrderingStrategyTest {

	private OptimalMinimumDistanceMessageFlowsOrderingStrategy sut = new OptimalMinimumDistanceMessageFlowsOrderingStrategy();
	
	@Test
	public void test_indexOf() throws Exception {
		Integer[] x = new Integer[] { 0, 2, 4, 6, 8 };
		
		for(int i  = 0; i < x.length; i++) {
			assertEquals(Integer.toString(i), i, sut.indexOf(x, i * 2));
		}
	}
	
	@Test
	public void test_calculateTotalMessageFlowDistance_TwoPools_TwoEdges() {
		int[][] edges = new int[2][2];
		
		edges[0][1] = 2;
		edges[1][0] = 2;
		Integer[] order;
		
		order = new Integer[] { 0, 1 };
		assertEquals(2, sut.calculateTotalMessageFlowDistance(edges, order));
		
		order = new Integer[] { 1, 0 };
		assertEquals(2, sut.calculateTotalMessageFlowDistance(edges, order));
	}
	
	@Test
	public void test_calculateTotalMessageFlowDistance_ThreePools_ThreeEdges() {
		int[][] edges = new int[3][3];
		
		edges[0][1] = 2;
		edges[0][2] = 1;
		edges[1][0] = 2;
		edges[2][0] = 1;
		Integer[] order;
		
		order = new Integer[] { 0, 1, 2 };
		assertEquals(4, sut.calculateTotalMessageFlowDistance(edges, order));
		
		order = new Integer[] { 1, 0, 2 };
		assertEquals(3, sut.calculateTotalMessageFlowDistance(edges, order));
	}

}
