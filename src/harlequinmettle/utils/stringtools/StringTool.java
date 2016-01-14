package harlequinmettle.utils.stringtools;

import harlequinmettle.utils.timetools.TimeRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {

	public static ArrayList<String> extractAllPattern(String htmlData, String regexPattern) {
		ArrayList<String> patterns = new ArrayList<String>();
		Pattern p = Pattern.compile(regexPattern);
		Matcher m = p.matcher(htmlData);
		while (m.find()) {
			patterns.add(m.group());
		}
		return patterns;

	}

	public static String extractFirstPattern(String htmlData, String regexPattern) {

		Pattern p = Pattern.compile(regexPattern);
		Matcher m = p.matcher(htmlData);
		while (m.find()) {
			return m.group();
		}
		return "";

	}

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
		return htmlData.substring(startIndex);
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

	public static String removeJavascript(String htmlData) {

		return htmlData.replaceAll("<script.*?/script>", " ");

	}

	public static String replaceAllRegex(String text, String regex, String replacement) {
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		return m.replaceAll(replacement);
		// while (m.find()) {
		// m.appendReplacement(sb, replacement);
		// }
		// m.appendTail(sb);
		// return (sb.toString());
	}

	// Aug 24, 2015 10:46:36 AM
	public static String[] getLines(String fileContents) {
		if (fileContents == null)
			return null;
		return fileContents.split("\n");
	}
}