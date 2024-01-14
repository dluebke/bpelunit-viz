package com.digitalsolutionarchitecture.bpmn.di.bpmn;

public class Font {

	private String fontName = "Arial";
	private double fontSize = 12.0;
	private boolean isBold = false;
	private boolean isItalic = false;
	private boolean isStrikeThrough = false;
	private boolean isUnderline = false;

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public double getFontSize() {
		return fontSize;
	}

	public void setFontSize(double fontSize) {
		this.fontSize = fontSize;
	}

	public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	public boolean isItalic() {
		return isItalic;
	}

	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}

	public boolean isStrikeThrough() {
		return isStrikeThrough;
	}

	public void setStrikeThrough(boolean isStrikeThrough) {
		this.isStrikeThrough = isStrikeThrough;
	}

	public boolean isUnderline() {
		return isUnderline;
	}

	public void setUnderline(boolean isUnderline) {
		this.isUnderline = isUnderline;
	}
}
