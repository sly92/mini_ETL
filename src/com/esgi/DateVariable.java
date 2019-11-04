package com.esgi;

import java.util.ArrayList;


public class DateVariable extends Variable {

    private ArrayList<String> possibleValues;

    public DateVariable(String name, ArrayList<String> possibleValues) {
        super(name);
        this.possibleValues = possibleValues;
    }

    @Override
    public void declare(Value value) { }

    @Override
    public Value parse(String rawValue) throws ParseValueException {
        rawValue = rawValue.trim();
        if (possibleValues.contains(rawValue))
            return new Value(this, rawValue, null);
        else
            throw new ParseValueException(
                    String.format("Value %s is not allowed for variable %s", rawValue, getName()));
    }


    @Override
    public int compare(Value value1, Value value2) {
        return value1.getStringValue().compareTo(value2.getStringValue());
    }

    @Override
    public Double distance(Value value1, Value value2) {
        return null;
    }
}