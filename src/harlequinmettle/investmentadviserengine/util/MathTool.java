package harlequinmettle.investmentadviserengine.util;

import java.util.Collection;

//Mar 1, 2015  1:01:45 PM 
public class MathTool {

	public static float avgFloat(Collection<Float> values) {
		float sum = 0;
		int n = 0;

		for (float f : values) {
			if (f != f || Float.isInfinite(f))
				continue;
			sum += f;
			n++;

		}
		return (sum / n);
	}

	public static double avgDouble(Collection<Double> values) {
		double sum = 0;
		int n = 0;

		for (double d : values) {
			if (d != d)
				continue;
			sum += d;
			n++;

		}
		return (sum / n);
	}

}
