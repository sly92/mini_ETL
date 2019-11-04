package com.esgi;

import java.util.TreeMap;

public class OrdinalVariable extends NominalVariable {
	private TreeMap<String, Integer> ranks;

	public OrdinalVariable(String name, String[] possibleValues) {
		super(name, possibleValues);
		ranks = new TreeMap<>();
		for (int i = 0; i < possibleValues.length; i++)
			ranks.put(possibleValues[i], i);
	}

	@Override
	public void declare(Value value) {
		if (value.getVariable() == this && !ranks.containsKey(value.getStringValue()))
			ranks.put(value.getStringValue(), ranks.size());
	}

	public int rank(String value) {
		return ranks.get(value);
	}
	
	@Override
	public Double distance(Value value1, Value value2) {
		return Math.abs(ranks.get(value1.getStringValue()) - ranks.get(value2.getStringValue())) / (double)ranks.size();
	}

	@Override
	public Value parse(String rawValue) throws ParseValueException {
		Value value = super.parse(rawValue);
		value.setDoubleValue(ranks.get(value.getStringValue()).doubleValue());
		return value;
	}

	@Override
	public int compare(Value value1, Value value2) {
		Integer rank1 = rank(value1.getStringValue());
		Integer rank2 = rank(value2.getStringValue());
		return rank1.compareTo(rank2);
	}
}