package com.esgi;

public class ParsingOptions {
	private String lineSeparator, fieldSeparator;
	private boolean skipHeader, skipId;

	public ParsingOptions(String lineSeparator, String fieldSeparator, boolean skipHeader, boolean skipId) {
		super();
		this.lineSeparator = lineSeparator;
		this.fieldSeparator = fieldSeparator;
		this.skipHeader = skipHeader;
		this.skipId = skipId;
	}

	public String getLineSeparator() {
		return lineSeparator;
	}

	public String getFieldSeparator() {
		return fieldSeparator;
	}

	public boolean skipHeader() {
		return skipHeader;
	}

	public boolean skipId() {
		return skipId;
	}
}