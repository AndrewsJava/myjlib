package harlequinmettle.investorengine.util;

import java.util.ArrayList;

public class PearsonsCoorelationCooeficient {
	// sum(x-xmean * y-ymean) / sqrt( sum( (x-xmean)* (x-xmean))) * sqrt( sum(
	// (y-ymean)*(y-ymean)))
	ArrayList<Float> datay;
	ArrayList<Float> datax;
	double meany = 0;
	double meanx = 0;
	float n = 0;

	public PearsonsCoorelationCooeficient(ArrayList<Float> data) {

		datay = data;

		n = data.size();

		datax = genXData((int) n);

		meany = getMean(datay);
		meanx = getMean(datax);

	}

	public float getPearsonsCoorelationCooeficient() {

		float numerator = sumOffofMeanXY();
		double denominator = getSquareRootsMultipliedOfSumErrorsSquare();
		return (float) (numerator / denominator);

	}

	private ArrayList<Float> genXData(int length) {

		ArrayList<Float> x = new ArrayList<Float>();
		for (int i = 0; i < length; i++) {
			x.add((float) i);
		}
		return x;
	}

	private double getSquareRootsMultipliedOfSumErrorsSquare() {

		double sqrtSumSqrx = Math.sqrt(getSumErrorsSquare(datay, meany));
		double sqrtSumSqry = Math.sqrt(getSumErrorsSquare(datax, meanx));

		return sqrtSumSqrx * sqrtSumSqry;

	}

	private double getSumErrorsSquare(ArrayList<Float> data, double mean) {
		double sumSqr = 0;
		for (float f : data) {
			sumSqr += (f - mean) * (f - mean);
		}
		return sumSqr;
	}

	private float sumOffofMeanXY() {
		float sumErrors = 0;
		for (int i = 0; i < n; i++) {
			sumErrors += (datax.get(i) - meanx) * (datay.get(i) - meany);
		}
		return sumErrors;
	}

	private double getMean(ArrayList<Float> data) {
		double sum = 0;
		for (int i = 0; i < data.size(); i++) {
			sum += data.get(i);
		}
		return sum / n;
	}

}
