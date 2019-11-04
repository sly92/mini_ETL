package com.esgi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DataSet extends ArrayList<DataRow> {
	private static final long serialVersionUID = -6027859151748740890L;
	private ArrayList<Variable> variables;
	private String name;

	public DataSet(String name, Collection<Variable> variables) {
		this.name = name;
		this.variables = new ArrayList<>();
		this.variables.addAll(variables);
	}

	public DataSet(String name, Variable[] variables) {
		this(name, Arrays.asList(variables));
	}

	public String getName() {
		return name;
	}

	public ArrayList<Variable> getVariables() {
		return variables;
	}

	public void parse(String text, ParsingOptions options) {
        String[] lines = text.split(options.getLineSeparator());
        Variable[] variableArray = variables.toArray(new Variable[0]);
        DataRow datarow;
        int firstLineIndex;
        clear();

        firstLineIndex = options.skipHeader() ? 1 : 0;

        for (int i = firstLineIndex; i < lines.length; i++) {
            datarow = new DataRow();
            datarow.parse(lines[i], variableArray, options);
            add(datarow);
        }
    }

    public void filterInstance(int maxBlank) {
        int i;
        ArrayList<Integer> toRemove = new ArrayList<>();
        int[] blanks = getAllBlanks(this);
        for (DataRow row : this) {
            for (i = 0; i < variables.size(); i++) {
                if (blanks[i] < maxBlank) {
                    if (!row.containsKey(variables.get(i))) {
                        if (!toRemove.contains(i))
                            toRemove.add(i);
                    }
                }
            }
        }
        for(i=0;i<toRemove.size();i++) {
            //System.out.println(toRemove.get(i));
            this.remove((toRemove.get(i)));
        }
    }

    public int[] getAllBlanks(DataSet ds) {
        int[] blanks = new int[variables.size()];
        for (int i = 0; i < variables.size(); i++) {
            for (DataRow row : ds) {
                if (!row.containsKey(variables.get(i)))
                    blanks[i]++;
            }
        }
        return blanks;
    }

	public void removeDuplicates() {
		int i, j;
		i = 0;
		while (i < size()) {
			j = i + 1;
			while (j < size()) {
				if (get(i).equals(get(j)))
					remove(j);
				else
					j++;
			}
			i++;
		}
	}

	@Override
	public String toString() {
		String[] strings = new String[size()];
		int i = 0;
		for (DataRow row : this)
			strings[i++] = row.toString();

		return String.join("\n", strings);
	}
}