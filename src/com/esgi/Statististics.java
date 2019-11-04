package com.esgi;

public class Statististics {
	private double avg, variance;

	public Statististics(Double[] values) {
		double sum = 0;
		for (Double value : values)
			sum += value;
		avg = sum / values.length;

		sum = 0;
		for (Double value : values)
			sum += Math.pow(value - avg, 2);
		variance = Math.sqrt(sum / values.length);
	}

	public static double correlation(Double[] values1, Double[] values2) {
		Statististics stats1, stats2;
		double corr;
		stats1 = new Statististics(values1);
		stats2 = new Statististics(values2);

		corr = 0;
		for (int i = 0; i < values1.length; i++) {
			try {
				corr += (values1[i] - stats1.avg) * (values2[i] - stats2.avg);
			}
			catch (IndexOutOfBoundsException ignored){
			}
		}
		corr /= values1.length * stats1.variance * stats2.variance;
		return corr;
	}

	public double getAverage() {
		return avg;
	}
	
	public double getVariance() {
		return variance;
	}
}