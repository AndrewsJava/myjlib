package harlequinmettle.investorengine.util;

public class TimeToCorrelationCoefficient {
	/** sum of x values */
	private float sumX = 0f;

	/** total variation in x (sum of squared deviations from xbar) */
	private float sumXX = 0f;

	/** sum of y values */
	private float sumY = 0f;

	/** total variation in y (sum of squared deviations from ybar) */
	private float sumYY = 0f;

	/** sum of products */
	private float sumXY = 0f;

	/** number of observations */
	private long n = 0;

	public void addData(final float x, final float y) {

		sumXX += x * x;
		sumYY += y * y;
		sumXY += x * y;

		sumX += x;
		sumY += y;
		n++;
	}

	public float correlation(final float[] yArray) throws IllegalArgumentException {

		if (yArray.length > 1) {
			for (int i = 0; i < yArray.length; i++) {
				addData(i + 10, yArray[i]);
			}
			return getR();
		}
		return Float.NaN;
	}

	/**
	 * Returns <a
	 * href="http://mathworld.wolfram.com/CorrelationCoefficient.html">
	 * Pearson's product moment correlation coefficient</a>, usually denoted r.
	 * <p>
	 * <strong>Preconditions</strong>:
	 * <ul>
	 * <li>At least two observations (with at least two different x values) must
	 * have been added before invoking this method. If this method is invoked
	 * before a model can be estimated, <code>Double,NaN</code> is returned.</li>
	 * </ul>
	 * </p>
	 *
	 * @return Pearson's r
	 */
	public float getR() {
		float b1 = getSlope();
		float result = (float) Math.sqrt(getRSquare());
		if (b1 < 0) {
			result = -result;
		}
		return result;
	}

	public float mean(final float[] values) {
		float sum = sum(values);
		if (values.length <= 0)
			return Float.NaN;
		return sum / values.length;

	}

	public float sum(final float[] values) {
		float sum = 0f;
		for (float val : values) {
			if (val == val && !Float.isInfinite(val))
				sum += val;
		}
		return sum;
	}

	/**
	 * Returns the <a href="http://www.xycoon.com/SumOfSquares.htm"> sum of
	 * squared errors</a> (SSE) associated with the regression model.
	 * <p>
	 * The sum is computed using the computational formula
	 * </p>
	 * <p>
	 * <code>SSE = SYY - (SXY * SXY / SXX)</code>
	 * </p>
	 * <p>
	 * where <code>SYY</code> is the sum of the squared deviations of the y
	 * values about their mean, <code>SXX</code> is similarly defined and
	 * <code>SXY</code> is the sum of the products of x and y mean deviations.
	 * </p>
	 * <p>
	 * The sums are accumulated using the updating algorithm referenced in
	 * {@link #addData}.
	 * </p>
	 * <p>
	 * The return value is constrained to be non-negative - i.e., if due to
	 * rounding errors the computational formula returns a negative result, 0 is
	 * returned.
	 * </p>
	 * <p>
	 * <strong>Preconditions</strong>:
	 * <ul>
	 * <li>At least two observations (with at least two different x values) must
	 * have been added before invoking this method. If this method is invoked
	 * before a model can be estimated, <code>Double,NaN</code> is returned.</li>
	 * </ul>
	 * </p>
	 *
	 * @return sum of squared errors associated with the regression model
	 */
	public double getSumSquaredErrors() {
		return Math.max(0d, sumYY - sumXY * sumXY / sumXX);
	}

	/**
	 * Returns the sum of squared deviations of the y values about their mean.
	 * <p>
	 * This is defined as SSTO <a
	 * href="http://www.xycoon.com/SumOfSquares.htm">here</a>.
	 * </p>
	 * <p>
	 * If <code>n < 2</code>, this returns <code>Double.NaN</code>.
	 * </p>
	 *
	 * @return sum of squared deviations of y values
	 */
	public double getTotalSumSquares() {
		if (n < 2) {
			return Double.NaN;
		}
		return sumYY;
	}

	/**
	 * Returns the <a href="http://www.xycoon.com/coefficient1.htm"> coefficient
	 * of determination</a>, usually denoted r-square.
	 * <p>
	 * <strong>Preconditions</strong>:
	 * <ul>
	 * <li>At least two observations (with at least two different x values) must
	 * have been added before invoking this method. If this method is invoked
	 * before a model can be estimated, <code>Double,NaN</code> is returned.</li>
	 * </ul>
	 * </p>
	 *
	 * @return r-square
	 */
	public double getRSquare() {
		double ssto = getTotalSumSquares();
		return (ssto - getSumSquaredErrors()) / ssto;
	}

	/**
	 * Returns the slope of the estimated regression line.
	 * <p>
	 * The least squares estimate of the slope is computed using the <a
	 * href="http://www.xycoon.com/estimation4.htm">normal equations</a>. The
	 * slope is sometimes denoted b1.
	 * </p>
	 * <p>
	 * <strong>Preconditions</strong>:
	 * <ul>
	 * <li>At least two observations (with at least two different x values) must
	 * have been added before invoking this method. If this method is invoked
	 * before a model can be estimated, <code>Double.NaN</code> is returned.</li>
	 * </ul>
	 * </p>
	 *
	 * @return the slope of the regression line
	 */
	public float getSlope() {
		if (n < 2) {
			return Float.NaN; // not enough data
		}
		// if (Math.abs(sumXX) < 10 * Double.MIN_VALUE) {
		// return Float.NaN; // not enough variation in x
		// }
		return sumXY / sumXX;
	}

	/**
	 * Clears all data from the model.
	 */
	public void clear() {
		sumX = 0f;
		sumXX = 0;
		sumY = 0;
		sumYY = 0;
		sumXY = 0;
		n = 0;
	}

}
