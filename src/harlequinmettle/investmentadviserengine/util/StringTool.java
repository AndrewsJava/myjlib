package harlequinmettle.investmentadviserengine.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public static Date parseDate(SimpleDateFormat typical, String date, Date defaultDate, boolean logCountExceptions) {
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

	public static String limitLength(String string, int maxLength) {
		if (string.length() > maxLength)
			return string.substring(0, maxLength);
		return string;
	}

	/**
	 * Calculates the similarity (a number within 0 and 1) between two strings.
	 */
	public static double similarityOfStrings(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater
											// length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			return 1.0; /* both strings are zero length */
		}
		/*
		 * // If you have StringUtils, you can use it to calculate the edit
		 * distance: return (longerLength -
		 * StringUtils.getLevenshteinDistance(longer, shorter)) / (double)
		 * longerLength;
		 */
		return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

	}

	// Example implementation of the Levenshtein Edit Distance
	// See http://rosettacode.org/wiki/Levenshtein_distance#Java
	public static int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	// Mar 4, 2015 10:53:18 AM
	public static String makeStringOneLineWithSingleSpacesBetweenWords(String str) {

		str = str.replaceAll("\\s", " ");

		return str;

	}

	// Mar 15, 2015 12:59:05 PM
	public static boolean isStringNonAlphabetic(String dataValue) {
		return dataValue.matches("[^a-zA-Z]*");
	}

	// Mar 19, 2015 8:50:39 AM
	public static String removeUnnecessaryHtmlTagsFromHtmlLineOfEdgarReport(String htmlText) {

		htmlText = htmlText.toLowerCase();
		// htmlText = replaceAllRegex(htmlText, "", "");
		htmlText = replaceAllRegex(htmlText, "&.*?;", " ");
		htmlText = replaceAllRegex(htmlText, "\\s+", " ");
		htmlText = replaceAllRegex(htmlText, "<font.*?>", "");
		htmlText = replaceAllRegex(htmlText, "</font>", "");
		htmlText = replaceAllRegex(htmlText, "<span.*?>", "");
		htmlText = replaceAllRegex(htmlText, "</span>", "");
		htmlText = replaceAllRegex(htmlText, "<div.*?>", "");
		htmlText = replaceAllRegex(htmlText, "</div>", "");
		htmlText = replaceAllRegex(htmlText, "<!--.*?-->", "");
		htmlText = replaceAllRegex(htmlText, "<p.*?>", "");
		htmlText = replaceAllRegex(htmlText, "</p>", "");
		htmlText = replaceAllRegex(htmlText, "<a.*?>", "");
		htmlText = replaceAllRegex(htmlText, "</a>", "");
		htmlText = replaceAllRegex(htmlText, "<b.*?>", "");
		htmlText = replaceAllRegex(htmlText, "</b>", "");

		htmlText = replaceAllRegex(htmlText, "<td.*?>", "<td>");

		htmlText = replaceAllRegex(htmlText, "<tr.*?>", "<tr>");
		htmlText = replaceAllRegex(htmlText, "\\$", "");
		return htmlText;
	}

	public static String cleanUpSomeTextAndTags(String htmlText) {

		htmlText = htmlText.toLowerCase();
		// htmlText = replaceAllRegex(htmlText, "", "");
		htmlText = replaceAllRegex(htmlText, "&.*?;", " ");
		htmlText = replaceAllRegex(htmlText, "\\s+", " ");

		htmlText = replaceAllRegex(htmlText, "<td.*?>", "<td>");

		htmlText = replaceAllRegex(htmlText, "<tr.*?>", "<tr>");
		htmlText = replaceAllRegex(htmlText, "\\$", "");
		return htmlText;
	}

	// Mar 19, 2015 10:13:10 AM
	private static String replaceAllRegex(String text, String regex, String replacement) {
		// StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		return m.replaceAll(replacement);
		// while (m.find()) {
		// m.appendReplacement(sb, replacement);
		// }
		// m.appendTail(sb);
		// return (sb.toString());
	}

}
