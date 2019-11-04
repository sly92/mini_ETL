package com.esgi;

import java.util.TreeMap;

public class DataRow extends TreeMap<Variable, Value> {
	private static final long serialVersionUID = 5337423511452561764L;

	public double distance(DataRow other) {
		double sum = 0;
		int count = 0;
		for (Variable var : this.keySet()) {
			if (other.containsKey(var)) {
				count++;
				sum += var.distance(this.get(var), other.get(var));
			}
		}

		return sum / count;
	}

	public final void parse(String text, Variable[] variables, ParsingOptions options) {
		String[] fields = text.split(options.getFieldSeparator());
		Variable var;
		Value value;
		int firstFieldIndex = options.skipId() ? 1 : 0;

		for (int i = firstFieldIndex; i < fields.length; i++) {
			try {
				var = variables[i - firstFieldIndex];
				value = variables[i - firstFieldIndex].parse(fields[i]);

				put(var, value);
			} catch (ParseValueException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public Value put(Variable variable, Value value) {
		variable.declare(value);
		return super.put(variable, value);
	}

	@Override
	public String toString() {
		String[] strings = new String[size()];
		int i = 0;
		for (Value v : this.values())
			strings[i++] = v.toString();

		return String.join(", ", strings);
	}

	@Override
	public boolean equals(Object o) {
		DataRow other;

		if (o instanceof DataRow) {
			other = (DataRow) o;
			if (size() == other.size()) {
				for (Variable var : keySet()) {
					if (!get(var).equals(other.get(var)))
						return false;
				}

				return true;
			}
		}
		return false;
	}
}