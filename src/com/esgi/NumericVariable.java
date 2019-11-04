package com.esgi;

public class NumericVariable extends Variable {
	private String unit;
	private Double min, max;

	public NumericVariable(String name, String unit) {
		super(name);
		this.unit = unit;
		min = null;
		max = null;
	}
	
	public NumericVariable(String name) {
		this(name, null);
	}

	public String getUnit() {
		return unit;
	}

	public Double getMin() {
		return min;
	}
	
	public Double getMax() {
		return max;
	}
	
	@Override
	public Double distance(Value value1, Value value2){
		return (value1.getDoubleValue() - value2.getDoubleValue()) / max;
	}
	
	@Override
	public void declare(Value value) {
		if (value.getVariable() == this) {
			if (min == null || value.getDoubleValue() < min)
				min = value.getDoubleValue();
			if (max == null || value.getDoubleValue() > max)
				max = value.getDoubleValue();
		}
	}

	@Override
	public Value parse(String rawValue) throws ParseValueException {
		double doubleValue;
		rawValue = rawValue.trim();
		try {
			doubleValue = Double.parseDouble(rawValue.replace(',', '.'));
		} catch (NumberFormatException e) {
			throw new ParseValueException(String.format("Empty value for %s", getName()));
		}
		return new Value(this, rawValue, doubleValue);
	}

	@Override
	public int compare(Value value1, Value value2) {
		return value1.getDoubleValue().compareTo(value2.getDoubleValue());
	}
}