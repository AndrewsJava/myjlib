package harlequinmettle.utils.numbertools.math.statistics;

public class BasicCalculations {

	public static float calculatePercentChange(float start, float end) {
		if (start != start || end != end)
			return Float.NaN;
		// if(Float.isInfinite(start))return
		if (start == 0) {
			if (end > 0)
				return Float.POSITIVE_INFINITY;
		}
		return 100f * (end - start) / start;

	}

}
