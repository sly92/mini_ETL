package com.esgi;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataColumn extends ArrayList<Value> {
	private static final long serialVersionUID = -7550978464554567998L;

	public DataColumn(DataSet dataset, Variable variable) {
		super(dataset.stream().filter(row -> row.containsKey(variable)).map(row -> row.get(variable))
				.collect(Collectors.toList()));
	}

	public Double[] toDoubleArray() {
		return stream().map(Value::getDoubleValue).toArray(Double[]::new);
	}

	public String[] toStringArray() {
		return stream().map(Value::getStringValue).toArray(String[]::new);
	}

	@Override
	public String toString() {
		return String.join("\n", stream().map(Value::toString).toArray(String[]::new));
	}
}
