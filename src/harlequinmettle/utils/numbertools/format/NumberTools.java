package harlequinmettle.utils.numbertools.format;

public class NumberTools {

	public static String stringToBMKTrunkated(String parsableNumber, String defaultValue) {
		float val = tryToParse(parsableNumber, Float.NaN);
		if (val != val)
			return defaultValue;
		return floatToBMKTrunkated(val);
	}

	public static float tryToParse(String parsableNumber, float defaultValue) {
		try {
			return Float.parseFloat(parsableNumber.replaceAll(",", ""));
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
}
