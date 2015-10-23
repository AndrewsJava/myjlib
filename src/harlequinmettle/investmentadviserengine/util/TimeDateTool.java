package harlequinmettle.investmentadviserengine.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeDateTool {

	public static final String ALPHABETICAL_DATE_FORMAT = new String("yyMMdd");
	public static final String FORMAT_DAY_ONLY = new String("EEE");
	public static final String MULTIDAY_CSV_DATE_FORMAT = new String("yyyy-MM-dd");
	public static final String HTML_ARCHIVES_DATE_FORMAT = new String("dd_MMM_HH_mm");
	// Mar 31, 2013
	public static final String EARNINGS_REPORTS_DATE_FORMAT = new String("MMM dd, yyyy");
	// 2013-06-02
	public static final String EDGAR_FILINGS_DATE_FORMAT = new String("yyyy-MM-dd");
	public static final String DAY_DATE_FULL = new String("EEE MMM dd yyyy");
	public static final String DAY_DATE_TIME = new String("EEE MMM dd yyyy HH:mm:ss");
	private static final String NET_FONDS_URL_DATE_FORMAT = new String("yyyyMMdd");

	public static final String BUNDLE_TESTER_YEAR_AND_MONTH = new String("yyyy-MM");
	private static final String BUNDLE_TESTER_DAY_OF_MONTH_AND_TIME_OF_DAY = new String("dd_kk");

	public static long getReverseOrderTimeMillis() {
		return 9000000000000L - System.currentTimeMillis();
	}

	public static long getOriginalMillisecondsFromReverseOrder(String numericalString) {
		return -(NumberTool.tryToParseLong(numericalString, 0) - 9000000000000L);
	}

	public static long getOriginalMillisecondsFromReverseOrder(long tryToParseFloat) {
		return -(tryToParseFloat - 9000000000000L);
	}

	public static int getMinute() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
		int minute = c.get(Calendar.MINUTE);
		return (minute);
	}

	public static int getHourEST() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return (hour);
	}

	public static boolean itsWeekend() {
		// if (true)
		// return false;
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));

		int day = c.get(Calendar.DAY_OF_WEEK);
		return (day == Calendar.SATURDAY || day == Calendar.SUNDAY);
	}

	public static boolean isBetweenHours(int early, int late) {
		int hour = TimeDateTool.getHourEST();
		return hour >= early && hour <= late;
	}

	public static Date getESTDate() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		return c.getTime();
	}

	public static String getESTDateAndTime() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		int dAY = c.get(Calendar.DAY_OF_MONTH);
		int mONTH = c.get(Calendar.MONTH) + 1;
		return "[" + mONTH + "-" + dAY + "][" + h + ":" + m + ":" + s + " est]";
	}

	public static String getDefaultDateAndTimeWithSimpleDateFormat(String formatForDate) {
		long estOffsetTime = System.currentTimeMillis();
		return new SimpleDateFormat(formatForDate).format(new Date(estOffsetTime));
	}

	public static String getDefaultDateAndTimeWithSimpleDateFormat(String formatForDate, long time) {

		return new SimpleDateFormat(formatForDate).format(new Date(time));
	}

	public static String getESTDateAndTimeWithSimpleDateFormat(String formatForDate) {
		TimeZone zone = TimeZone.getTimeZone("America/New_York");
		boolean daylightSavings = zone.inDaylightTime(new Date());
		int hoursOffsetGMT = -5;
		if (daylightSavings)
			hoursOffsetGMT++;
		int millisecondsOffset = (hoursOffsetGMT * 60) * (60 * 1000);
		long estOffsetTime = System.currentTimeMillis() + millisecondsOffset;
		return new SimpleDateFormat(formatForDate).format(new Date(estOffsetTime));
	}

	public static String getESTDateAndTimeWithSimpleDateFormat(String formatForDate, long time) {
		TimeZone zone = TimeZone.getTimeZone("America/New_York");
		boolean daylightSavings = zone.inDaylightTime(new Date(time));
		int hoursOffsetGMT = -5;
		if (daylightSavings)
			hoursOffsetGMT++;
		int millisecondsOffset = (hoursOffsetGMT * 60) * (60 * 1000);
		long estOffsetTime = time + millisecondsOffset;
		return new SimpleDateFormat(formatForDate).format(new Date(estOffsetTime));
	}

	public static String getESTTime() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		return "[" + h + ":" + m + ":" + s + " est]";
	}

	public static String timeSince(long start) {
		long elapsedTime = System.currentTimeMillis() - start;
		long sixHours = 60 * 60 * 6 * 1000;
		if (elapsedTime > sixHours)
			return " [" + hoursSince(start) + " hours] ";
		long fiveMinutes = 60 * 5 * 1000;
		if (elapsedTime > fiveMinutes)
			return " [" + minutesSince(start) + " minutes] ";
		long fiveSeconds = 5000;
		if (elapsedTime > fiveSeconds)
			return " [" + secondsSince(start) + " seconds] ";

		return " [" + elapsedTime + " milliseconds] ";
	}

	public static long secondsSince(long start) {
		long elapsedTime = System.currentTimeMillis() - start;
		elapsedTime /= 1000;// milliseconds to seconds
		return elapsedTime;
	}

	public static long minutesSince(long start) {
		long elapsedTime = System.currentTimeMillis() - start;
		elapsedTime /= 1000;// milliseconds to seconds
		elapsedTime /= 60;// seconds to minutes
		return elapsedTime;
	}

	public static long hoursSince(long start) {
		long elapsedTime = System.currentTimeMillis() - start;
		elapsedTime /= 1000;// milliseconds to seconds
		elapsedTime /= 60;// seconds to minutes
		elapsedTime /= 60;//
		return elapsedTime;
	}

	public static String getShortDate() {
		return new SimpleDateFormat(DAY_DATE_FULL).format(new Date());
	}

	public static String getShortDateAndTime() {
		return new SimpleDateFormat(DAY_DATE_TIME).format(new Date());
	}

	public static String getFullDateAndTime(long time) {
		return new SimpleDateFormat(DAY_DATE_TIME).format(new Date(time));
	}

	public static String getDay() {
		return new SimpleDateFormat(FORMAT_DAY_ONLY).format(new Date());
	}

	public static String getTodayFormated() {

		return new SimpleDateFormat(NET_FONDS_URL_DATE_FORMAT).format(new Date());
	}

	public static boolean itsSunday() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
		int day = c.get(Calendar.DAY_OF_WEEK);
		return (day == Calendar.SUNDAY);
	}

	// Mar 1, 2015 3:45:41 PM
	public static float tryToParseDate(String date, SimpleDateFormat multidayCsvDateFormat, float defaultTimeMillis) {
		try {
			return multidayCsvDateFormat.parse(date).getTime();

		} catch (Exception e) {
			ErrorLog.log(e);
		}
		return defaultTimeMillis;
	}

	// Mar 8, 2015 8:31:55 AM
	public static String getMonthAndYearForBundleTester() {
		return new SimpleDateFormat(BUNDLE_TESTER_YEAR_AND_MONTH).format(new Date());

	}

	public static String getDayOfMonthAndHourOfDayForBundleTester() {
		// Apr 8, 2015 2:25:14 PM
		return getESTDateAndTimeWithSimpleDateFormat(BUNDLE_TESTER_DAY_OF_MONTH_AND_TIME_OF_DAY);

	}

	// Apr 11, 2015 12:22:21 PM
	public static String getAlphabeticalESTFromReverseOrderTime(String reverseOrderTime) {
		return getESTDateAndTimeWithSimpleDateFormat(ALPHABETICAL_DATE_FORMAT,
				TimeDateTool.getOriginalMillisecondsFromReverseOrder((long) NumberTool.tryToParseFloat(reverseOrderTime, 0)));

	}

	public static int getESTHourFromReverseOrderMilliseconds(String reverseOrderTime) {
		long time = TimeDateTool.getOriginalMillisecondsFromReverseOrder((long) NumberTool.tryToParseFloat(reverseOrderTime, 0));
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		c.setTimeInMillis(time);
		int h = c.get(Calendar.HOUR_OF_DAY);
		return h;

	}

	public static int getESTDayNumberFromReverseOrderMilliseconds(String reverseOrderTime) {
		long time = TimeDateTool.getOriginalMillisecondsFromReverseOrder((long) NumberTool.tryToParseFloat(reverseOrderTime, 0));
		TimeZone zone = TimeZone.getTimeZone("America/New_York");
		boolean daylightSavings = zone.inDaylightTime(new Date(time));
		int hoursOffsetGMT = -5;
		if (daylightSavings)
			hoursOffsetGMT++;
		int millisecondsOffset = (hoursOffsetGMT * 60) * (60 * 1000);
		long estOffsetTime = time + millisecondsOffset;
		return (int) TimeRecord.dayNumber(estOffsetTime);

	}

}
