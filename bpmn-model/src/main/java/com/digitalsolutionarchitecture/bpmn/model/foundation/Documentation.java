package com.digitalsolutionarchitecture.bpmn.model.foundation;

public class Documentation extends BaseElement {

	private String text;
	private String textFormat;
	
	public Documentation(String id) {
		this(id, null);
	}
	
	public Documentation(String id, String documentation) {
		super(id);
		this.text = documentation;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getTextFormat() {
		return textFormat;
	}
	
	public void setTextFormat(String textFormat) {
		this.textFormat = textFormat;
	}
	
}
