package com.digitalsolutionarchitecture.bpmn.util.permut;

public class HeapsPermutator<T> {

	public void generate(T[] elements, PermutatorCallback<T> callback) {
		int n = elements.length;
		
		int c[] = new int[n]; // zeroed out new stack state
		callback.newPermutation(elements);

		int i = 0;
		while(i < n) {
			if(c[i] < i) {
				if(i % 2 == 0) {
					swap(elements, 0, i);
				} else {
					swap(elements, c[i], i);
				}
				callback.newPermutation(elements);
				c[i]++;
				i = 0;
			} else {
				c[i] = 0;
				i++;
			}
		}
		
	}

	private void swap(T[] elements, int i, int j) {
		T temp = elements[i];
		elements[i] = elements[j];
		elements[j] = temp;
	}
	
}
