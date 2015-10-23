package harlequinmettle.investmentadviserengine.util;

import java.math.BigDecimal;

public class NumberTool {
	public static double roundToSignificantFigures(double num, int n) {
		if (num == 0) {
			return 0;
		}

		final double d = Math.ceil(Math.log10(num < 0 ? -num : num));
		final int power = n - (int) d;

		final double magnitude = Math.pow(10, power);
		final long shifted = Math.round(num * magnitude);
		return shifted / magnitude;
	}

	public static double roundToSignificantFigures2(double num) {

		final double d = Math.ceil(Math.log10(num < 0 ? -num : num));
		final int power = 2 - (int) d;

		final double magnitude = Math.pow(10, power);
		final long shifted = Math.round(num * magnitude);
		return shifted / magnitude;
	}

	public static double roundToSignificantFigures2Optimized(double num, double defaultValue, double minMagnitude) {

		final double d = Math.ceil(Math.log10(num < 0 ? -num : num));
		final int power = 2 - (int) d;

		final double magnitude = Math.pow(10, power);
		final long shifted = Math.round(num * magnitude);

		double returnValue = shifted / magnitude;
		if (returnValue == returnValue && Math.abs(returnValue) > minMagnitude)
			return returnValue;
		return defaultValue;
	}

	public static double roundToOneSignificantFigureOptimized(double num, double defaultValue, double minMagnitude) {

		final double d = Math.ceil(Math.log10(num < 0 ? -num : num));
		final int power = 1 - (int) d;

		final double magnitude = Math.pow(10, power);
		final long shifted = Math.round(num * magnitude);

		double returnValue = shifted / magnitude;
		if (returnValue == returnValue && Math.abs(returnValue) > minMagnitude)
			return returnValue;
		return defaultValue;
	}

	/**
	 * Round to certain number of decimals
	 * 
	 * @param d
	 * @param decimalPlace
	 * @return
	 */
	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public static float round2(float d) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public static String stringToBMKTrunkated(String parsableNumber, String defaultValue) {
		float val = tryToParseFloat(parsableNumber, Float.NaN);
		if (val != val)
			return defaultValue;
		return floatToBMKTrunkated(val);
	}

	public static float tryToParseFloat(String parsableNumber, float defaultValue) {
		try {

			return Float.parseFloat(parsableNumber.replaceAll(",", "").trim());
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static double tryToParseDouble(String parsableNumber, double defaultValue) {
		try {
			return Double.parseDouble(parsableNumber.replaceAll(",", ""));
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static String floatToBMKTrunkated(float number) {
		String suffix = "";
		if (Math.abs(number) > 1e9) {
			suffix += " B";
			number /= 1e9;
		}
		if (Math.abs(number) > 1e6) {
			suffix += " M";
			number /= 1e6;

		}
		if (Math.abs(number) > 1e3) {
			suffix += " K";
			number /= 1e3;
		}
		if (Math.abs(number) < 10)
			return ((int) (10 * number)) / 10f + suffix;
		else
			return (int) number + suffix;
	}

	public static float stringNumberWithMBKtoFloat(String numberString) {

		float factor = 1;
		if (numberString.contains("B")) {
			factor *= 1e9f;
		}
		if (numberString.contains("M")) {
			factor *= 1e6f;

		}
		if (numberString.contains("K")) {
			factor *= 1e3f;
		}
		numberString = numberString.replaceAll("[^\\.0-9-]", "");
		return factor * NumberTool.tryToParseFloat(numberString, Float.NaN);

	}

	public static String floatToBMKTrunkated(float number, int sigfigs) {
		String suffix = "";
		if (Math.abs(number) > 1e9) {
			suffix += " B";
			number /= 1e9;
		}
		if (Math.abs(number) > 1e6) {
			suffix += " M";
			number /= 1e6;

		}
		if (Math.abs(number) > 1e3) {
			suffix += " K";
			number /= 1e3;
		}
		return ((int) (Math.pow(10, sigfigs) * number)) / Math.pow(10, sigfigs) + suffix;

	}

	public static String formatCalculatePercentChange(float f, float g) {
		float percent = calculatePercentChange(f, g);

		return floatToBMKTrunkated(percent) + "%";
	}

	public static float calculatePercentChange(float start, float end) {
		if (start != start || end != end)
			return Float.NaN;
		// if(Float.isInfinite(start))return
		if (start == 0) {
			if (end > 0)
				return Float.POSITIVE_INFINITY;
		}
		return 100f * (end - start) / Math.abs(start);

	}

	public static long tryToParseLong(String parsableNumber, long defaultValue) {
		// Apr 21, 2015 9:27:24 AM
		try {
			return Long.parseLong(parsableNumber.replaceAll(",", ""));
		} catch (Exception e) {
		}
		return defaultValue;

	}

}
