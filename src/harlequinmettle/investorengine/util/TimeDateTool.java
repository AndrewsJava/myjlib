package harlequinmettle.investorengine.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeDateTool {

	public static final SimpleDateFormat CSV_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat HTML_ARCHIVES_DATE_FORMAT = new SimpleDateFormat("dd_MMM_HH_mm");
	// Mar 31, 2013
	public static final SimpleDateFormat EARNINGS_REPORTS_DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy");
	// 2013-06-02
	public static final SimpleDateFormat EDGAR_FILINGS_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DAY_DATE_FULL = new SimpleDateFormat("EEE MMM dd yyyy");
	public static final SimpleDateFormat DAY_DATE_TIME = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss");

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

	public static Date getESTDate() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		return c.getTime();
	}

	public static long minutesSince(long start) {
		long elapsedTime = System.currentTimeMillis() - start;
		elapsedTime /= 1000;// milliseconds to seconds
		elapsedTime /= 60;// seconds to minutes
		return elapsedTime;
	}

	public static String getShortDate() {
		return DAY_DATE_FULL.format(new Date());
	}

	public static String getShortDateAndTime() {
		return DAY_DATE_TIME.format(new Date());
	}

}
