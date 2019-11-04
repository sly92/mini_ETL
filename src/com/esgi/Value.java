package com.esgi;

public class Value implements Comparable<Value> {
	private Variable variable;
	private Double doubleValue;
	private String textValue;

	public Value(Variable variable, String textValue, Double doubleValue) {
		super();

		this.variable = variable;
		this.textValue = textValue;
		this.doubleValue = doubleValue;
	}

	public Variable getVariable() {
		return variable;
	}

	public String getStringValue() {
		return textValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double d) {
		doubleValue = d;
	}

	@Override
	public int compareTo(Value other) {
		return variable.compare(this, other);
	}

	@Override
	public boolean equals(Object o) {
		Value value;
		if (o instanceof Value) {
			value = (Value) o;
			return variable.equals(value.variable) && textValue.equals(value.textValue);
		} else
			return false;
	}

	@Override
	public String toString() {
		return String.format("%s = %s", variable.getName(), textValue);
	}
	/*public String toString() {
		return String.format("%s = %s", variable.getName(), textValue);
	}*/
}