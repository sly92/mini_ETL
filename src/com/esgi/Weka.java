package com.esgi;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Weka {
	private DataSet ds;

	public Weka(DataSet ds) {
		this.ds = ds;
	}

	public void saveAs(Path path) throws IOException {
		Files.write(path, toString().getBytes());
	}

	@Override
	public String toString() {
		ArrayList<Variable> variables = ds.getVariables();
		StringWriter sw = new StringWriter();
		ArrayList<String> values = new ArrayList<>();
		sw.append(String.format("@RELATION \"%s\"\n", ds.getName()));

		for (Variable var : variables) {
			sw.append(convert(var) + "\n");
		}

		sw.append("@DATA\n");
		for (DataRow row : ds) {

			values.clear();
			for (Variable variable : variables) {
				if (row.containsKey(variable))
					if (variable instanceof NominalVariable)
						values.add(String.format("\"%s\"", row.get(variable).getStringValue()));
					else
						values.add(row.get(variable).getStringValue());
				else
					values.add("?");
			}
			sw.append(String.join(",", values) + "\n");
		}

		return sw.toString();
	}

	public String convert(Variable var) {
		if (var instanceof NumericVariable)
			return String.format("@ATTRIBUTE\t\"%s\"\tNUMERIC", var.getName());
		else if (var instanceof NominalVariable) {
			ArrayList<String> values = new ArrayList<>();
			for (String s : ((NominalVariable) var).possibleValues())
				values.add(String.format("\"%s\"", s));
			return String.format("@ATTRIBUTE\t\"%s\"\t{%s}", var.getName(), String.join(",", values));
		}
		return null;
	}
}