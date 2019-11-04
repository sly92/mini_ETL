package com.esgi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static com.esgi.Correlation.getCorrelations;

public class Dataminer {
	private static String DATASET_NAME = "data";
	private static String FILENAME = "./data/factbook_v5.csv";
	private static ParsingOptions PARSING_OPTIONS = new ParsingOptions("\n", ";", true, false);
	private static ArrayList<Variable> VARIABLES;

	public static DataSet build(boolean filteredForCorr) {
        int i,k=0;
        VARIABLES = new ArrayList<>();
        String rawData;
        try {
            rawData = new String(Files.readAllBytes(Paths.get(FILENAME)), StandardCharsets.UTF_8);
            String header = rawData.substring(rawData.indexOf(""), rawData.indexOf("\n"));
            String[] champs = header.split(";");
            String unit;

            for (i = 0; i < champs.length; i++) {
                unit = "";
                if (champs[i].contains("(")) {
                    unit = champs[i].substring(champs[i].indexOf("(") + 1, champs[i].indexOf(")"));
                    champs[i] = champs[i].substring(champs[i].indexOf(""), champs[i].indexOf("("));
                }

                if (!champs[i].contains("Country")) {
                    if (!unit.equals(""))
                        VARIABLES.add(new NumericVariable(champs[i], unit));
                    else
                        VARIABLES.add(new NumericVariable(champs[i]));
                } else
                    VARIABLES.add(new NominalVariable(champs[i]));
            }
            DataSet ds = new DataSet(DATASET_NAME, VARIABLES);
            ds.parse(rawData, PARSING_OPTIONS);

            if(filteredForCorr) {
                String[][] corr = getCorrelations(ds);

                boolean let=false;
                        for(i=1;i<ds.getVariables().size();i++) {
                            while (corr[k][0] != null) {
                                let = ds.getVariables().get(i).getName().equals(corr[k][0]) || ds.getVariables().get(i).getName().equals(corr[k][1]);
                                k++;
                            }
                            if(!let)
                                ds.getVariables().remove(i);
                            i++;
                    }
            }
            return ds;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

	public static void main(String[] args) {

        try {

            DataSet ds = build(false);
            System.out.println(ds);

			Weka weka = new Weka(ds);

            assert ds != null;
            weka.saveAs(Paths.get(String.format("%s.arff", ds.getName())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}