package com.digitalsolutionarchitecture.bpmn.model.events;

import com.digitalsolutionarchitecture.bpmn.model.common.Expression;

public class TimerEventDefinition extends EventDefinition {

	private Expression timeCycle;
	private Expression timeDate;
	private Expression timeDuration;
	
	public TimerEventDefinition(String id) {
		super(id);
	}
	
	public Expression getTimeCycle() {
		return timeCycle;
	}
	
	public Expression getTimeDate() {
		return timeDate;
	}
	
	public Expression getTimeDuration() {
		return timeDuration;
	}
	
	public void setTimeCycle(Expression timeCycle) {
		this.timeCycle = timeCycle;
		this.timeDate = null;
		this.timeDuration = null;
	}
	
	public void setTimeDate(Expression timeDate) {
		this.timeDate = timeDate;
		this.timeCycle = null;
		this.timeDuration = null;
	}
	
	public void setTimeDuration(Expression timeDuration) {
		this.timeDuration = timeDuration;
		this.timeDate = null;
		this.timeCycle = null;
	}
	
}
