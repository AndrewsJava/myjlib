package harlequinmettle.investorengine.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringTool {

	public static String clip(String htmlData, String start, String end) {
		int startIndex = htmlData.indexOf(start);
		if (startIndex < 0)
			return "INVALID START OR END";

		htmlData = htmlData.substring(htmlData.indexOf(start));
		int endIndex = htmlData.indexOf(end);
		if (endIndex < 0)
			return "INVALID START OR END";
		htmlData = htmlData.substring(0, htmlData.indexOf(end));
		return htmlData;
	}

	public static String clip(String htmlData, String start) {
		int startIndex = htmlData.indexOf(start);
		if (startIndex < 0)
			return "INVALID START";
		return htmlData.substring(htmlData.indexOf(start));
	}

	public static float dayNumberOfLastReport(String interestingData) {
		SimpleDateFormat typical = new SimpleDateFormat("MMM dd, yyyy");
		if (interestingData.length() < 250)
			return 0;
		String date = interestingData.split("@@@")[1].trim();
		Date lastReport = parseDate(typical, date);
		return TimeRecord.dayNumber(lastReport.getTime());
	}

	private static Date parseDate(SimpleDateFormat typical, String date) {
		Date d = new Date(0);
		// for reports with no numbers; avoids exception reporting
		if (date.equals("Total Revenue"))
			return d;
		try {
			d = typical.parse(date);
		} catch (Exception e) {
		}
		return d;
	}

	public static Date parseDate(SimpleDateFormat typical, String date, Date defaultDate) {
		Date d = defaultDate;
		// for reports with no numbers; avoids exception reporting
		if (date.equals("Total Revenue"))
			return d;
		try {
			d = typical.parse(date);
		} catch (Exception e) {
		}
		return d;
	}

	static int exceptionCounter = 0;

	public static Date parseDate(SimpleDateFormat typical, String date, Date defaultDate, boolean logCountExceptions) {
		Date d = defaultDate;
		// for reports with no numbers; avoids exception reporting
		if (date.equals("Total Revenue"))
			return d;
		try {
			d = typical.parse(date);
		} catch (Exception e) {
			if (logCountExceptions) {
				exceptionCounter++;
			}
		}
		return d;
	}

	public static String clip(String htmlData, String start, String end, String defaultValue) {
		int startIndex = htmlData.indexOf(start);
		if (startIndex < 0)
			return defaultValue;

		htmlData = htmlData.substring(htmlData.indexOf(start));
		int endIndex = htmlData.indexOf(end);
		if (endIndex < 0)
			return defaultValue;
		htmlData = htmlData.substring(0, htmlData.indexOf(end));
		return htmlData;
	}

	public static String limitLength(String string, int maxLength) {
		if (string.length() > maxLength)
			return string.substring(0, maxLength);
		return string;
	}

}
