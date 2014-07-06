package harlequinmettle.utils.stringtools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringTools {

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

}
