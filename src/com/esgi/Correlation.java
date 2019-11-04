package com.esgi;

public class Correlation {


	public static void printDS(DataSet ds) {
        int i,j;
        Double[] values1, values2;
        for (i = 0; i < ds.getVariables().size(); i++) {
            if (ds.getVariables().get(i) instanceof NumericVariable) {
                values1 = getColumnValues(ds, i);
                for (j = i + 1; j < ds.getVariables().size(); j++) {
                    if (ds.getVariables().get(j) instanceof NumericVariable) {
                        values2 = getColumnValues(ds, j);
                        System.out.println(String.format("Correlation les plus fortes sont entre %s et %s = %f%%\n", getAttrName(ds, i), getAttrName(ds, j), getCorrelation(values1, values2)));
                    }
                }
            }
        }
    }


    public static String[][] getCorrelations(DataSet ds) {
	    int i,j;
        int k=0;
        Double corr;
        String var1, var2;
        Double[] values1, values2;
        String[][] correlations = new String[30][3];
            for (i = 0; i < ds.getVariables().size(); i++) {
                if (ds.getVariables().get(i) instanceof NumericVariable) {
                    values1 = getColumnValues(ds, i);
                    for (j = i + 1; j < ds.getVariables().size(); j++) {
                        if (ds.getVariables().get(j) instanceof NumericVariable) {
                            values2 = getColumnValues(ds, j);

                            var1 = getAttrName(ds, i);
                            var2 = getAttrName(ds, j);
                            corr = getCorrelation(values1, values2);


                            if (corr > 50 || corr < -50 && !var1.equals(var2)) {
                                    correlations[k][0] = var1;
                                    correlations[k][1] = var2;
                                    correlations[k][2] = corr.toString();
                                    k++;
                            }
                        }
                    }
                }
            }
        return correlations;
    }

    public static Double getCorrelation (Double[]values1, Double[]values2){
	    return 100 * Statististics.correlation(values1, values2);
	}

	public static String getAttrName (DataSet ds,int index){
	    return ds.getVariables().get(index).getName();
	}

	public static Double[] getColumnValues (DataSet ds,int index){
	    return new DataColumn(ds, ds.getVariables().get(index)).toDoubleArray();
	}



            }
