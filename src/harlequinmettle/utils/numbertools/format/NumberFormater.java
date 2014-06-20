package harlequinmettle.utils.numbertools.format;

public class NumberFormater {

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
}
