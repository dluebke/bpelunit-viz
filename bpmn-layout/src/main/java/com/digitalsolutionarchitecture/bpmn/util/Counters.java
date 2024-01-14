package com.digitalsolutionarchitecture.bpmn.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Counters<T> {
	
	private HashMap<T, Integer> counters = new HashMap<>();
	
	public int inc(T key) {
		int newValue = get(key) + 1;
		counters.put(key, newValue);
		return newValue;
	}

	private int get(T key) {
		Integer result = counters.get(key);
		if(result == null) {
			return 0;
		} else {
			return result;
		}
	}

	public List<T> getKeyWithHighestCounts() {
		List<T> result = new ArrayList<>();
		int highestCount = -1;
		
		for(T key : counters.keySet()) {
			int count = counters.get(key);
			if(count > highestCount) {
				highestCount = count;
				result.clear();
				result.add(key);
			} else if(count == highestCount) {
				result.add(key);
			}
		}
		return result;
	}
	
}
