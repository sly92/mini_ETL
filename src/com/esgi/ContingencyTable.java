package com.esgi;

public class ContingencyTable {
	private Integer[][] table;
	private String[] values1, values2;

	public ContingencyTable(DataSet ds, NominalVariable var1, NominalVariable var2) {
		int i1, i2;
		values1 = var1.possibleValues();
		values2 = var2.possibleValues();
		table = new Integer[values1.length][values2.length];
		for (i1 = 0; i1 < values1.length; i1++)
			for (i2 = 0; i2 < values2.length; i2++)
				table[i1][i2] = 0;

		for (DataRow row : ds) {
			i1 = var1.indexOf(row.get(var1).getStringValue());
			i2 = var2.indexOf(row.get(var2).getStringValue());
			table[i1][i2]++;
		}
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();

		for (int j = 0; j < table[0].length; j++)
			res.append(String.format("%s\t", values2[j]));
		res = new StringBuilder(res.toString().trim() + "\n");

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if (j == 0)
					res.append(String.format("%s\t", values1[i]));
				res.append(String.format("%d\t", table[i][j]));
			}
			res = new StringBuilder(res.toString().trim() + "\n");
		}
		return "\t" + res.toString().trim();
	}
}