package com.esgi;

import java.util.ArrayList;
import java.util.Arrays;

public class NominalVariable extends Variable {
	private ArrayList<String> possibleValues;
	private boolean open;

	public NominalVariable(String name) {
		super(name);
		this.possibleValues = new ArrayList<>();
		open = true;
	}

	public NominalVariable(String name, String[] possibleValues) {
		this(name);
		open = false;
		this.possibleValues.addAll(Arrays.asList(possibleValues));
	}

	public String[] possibleValues() {
		return possibleValues.toArray(new String[0]);
	}

	public Integer indexOf(String s) {
		return possibleValues.indexOf(s);
	}
	
	@Override
	public Double distance(Value value1, Value value2) {
		return value1.getStringValue().equals(value2.getStringValue()) ? 0.0 : 1.0;
	}

	@Override
	public void declare(Value value) {
		if (value.getVariable() == this && !possibleValues.contains(value.getStringValue()))
			possibleValues.add(value.getStringValue());
	}

	@Override
	public Value parse(String rawValue) throws ParseValueException {
		rawValue = rawValue.trim();
		if (possibleValues.contains(rawValue))
			return new Value(this, rawValue, null);
		else if(open){
			possibleValues.add(rawValue);
			return new Value(this, rawValue, null);
		}
		else
			throw new ParseValueException(
					String.format("Value %s is not allowed for variable %s", rawValue, getName()));
	}

	@Override
	public int compare(Value value1, Value value2) {
		return value1.getStringValue().compareTo(value2.getStringValue());
	}
}