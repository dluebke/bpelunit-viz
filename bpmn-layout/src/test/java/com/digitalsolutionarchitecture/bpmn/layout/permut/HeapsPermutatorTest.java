package com.digitalsolutionarchitecture.bpmn.layout.permut;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.digitalsolutionarchitecture.bpmn.util.permut.HeapsPermutator;
import com.digitalsolutionarchitecture.bpmn.util.permut.PermutatorCallback;

public class HeapsPermutatorTest {

	private HeapsPermutator<Integer> sut = new HeapsPermutator<>();
	
	@Test
	public void test_Permut() {
		permutationInternalTest(2);
		permutationInternalTest(3);
		permutationInternalTest(4);
	}

	private void permutationInternalTest(int size) {
		int expectedSize = frac(size);
		
		final Set<Integer[]> permutationsSet = new HashSet<>();
		final int[] counter = new int[1];
		
		Integer[] s = new Integer[size];
		for(int i = 0; i < size; i++) { 
			s[i] = i + 1;
		}
		sut.generate(s, new PermutatorCallback<Integer>() {
			
			@Override
			public void newPermutation(Integer[] result) {
				permutationsSet.add(result.clone());
				counter[0]++;
			}
		});
		assertEquals("no duplicate permutations", counter[0], permutationsSet.size());
		assertEquals(permutationsSet.toString(), expectedSize, permutationsSet.size());
	}

	private static int frac(int x) {
		int result = 1;
		for(int i = 1; i <= x; i++) {
			result *= i;
		}
		
		return result;
	}

}
