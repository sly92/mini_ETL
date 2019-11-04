package com.esgi;

public abstract class Variable implements Comparable<Variable> {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void declare(Value value);

    public abstract Value parse(String rawValue) throws ParseValueException;

    public abstract int compare(Value value1, Value value2);

    public abstract Double distance(Value value1, Value value2);

    @Override
    public int compareTo(Variable o) {
        return name.compareToIgnoreCase(o.name);
    }

    @Override
    public String toString() {
		return String.format("%s (%s)", getName(), this.getClass().getName());
	}
}
