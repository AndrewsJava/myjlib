package harlequinmettle.investorengine.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;

public class HtmlTool extends UrlBuilderTool {

	public static String addColorToOutlineAndIncomeTables(ConcurrentSkipListMap<String, String> tableMap, String ticker, float marketCap,
			int daysAgoLimit, float tablePercent, float factor) {

		String annualIncomeTableHtml = tableMap.get(ticker);
		// ModelHtmlTables.ANNUAL_INCOME_TABLES.get(ticker);
		float dateTest = tryToExtractDateFromTable(annualIncomeTableHtml);
		if (!tableMap.containsKey(ticker) || dateTest < TimeRecord.dayNumber(System.currentTimeMillis()) - daysAgoLimit)
			return "<table align = center width = " + 79 + "% height = " + 80 + "%  ><td align = center> ";

		String incomeNonRecurring = "non recurring";
		String incomeRowLabel = "net income applicable";
		float last = Float.NaN;

		String tableBuilder = "";
		for (int i = 4; i >= 0; i--) {
			float thisCurrent = tryToExtractFromTable(annualIncomeTableHtml, incomeRowLabel, i);
			if (thisCurrent != thisCurrent)
				continue;
			float thisYearNonRecurring = tryToExtractFromTable(annualIncomeTableHtml, incomeNonRecurring, i);
			if (thisYearNonRecurring == thisYearNonRecurring)
				thisCurrent += thisYearNonRecurring;

			String color = generateHtmlColorFromEarningsToCapitalRatio(thisCurrent * 1000, marketCap, factor, 110);
			annualIncomeTableHtml = annualIncomeTableHtml.replace("ddddd" + (2 + i), color);

			if (last != last) {
				tableBuilder += "<table width = " + tablePercent + "%    bgcolor=\"#";
				tableBuilder += generateHtmlColorFromEarningsToCapitalRatio(thisCurrent * 1000, marketCap, factor, 50) + "\"";
				tableBuilder += "><td align = center>";
			} else {
				tableBuilder += "<table width = " + tablePercent + "% height = " + tablePercent + "%  bgcolor=\"#"
						+ generateHtmlColorFromChange(last, thisCurrent, 75) + "\"";
				tableBuilder += "><td align = center>";
			}

			// tablePercent-=( .25*i);
			last = thisCurrent;
		}

		tablePercent -= 1;
		tableMap.put(ticker, annualIncomeTableHtml);

		tableBuilder += "<table width = " + tablePercent + "% ";
		tableBuilder += "height = " + tablePercent + "%  ";
		tableBuilder += " bgcolor=\"#bbbbdd\"";
		tableBuilder += "><td align = center>";
		return tableBuilder;
	}

	public static float tryToExtractDateFromTable(String annualIncomeTableHtml) {
		float val = Float.NaN;
		try {
			String row = StringTool.clip(annualIncomeTableHtml, "period ending", "</th>", null);
			if (row == null)
				return val;
			row = row.replaceAll("<.*?>", "");
			row = row.replaceAll("period ending", "").trim();

			SimpleDateFormat tableDate = new SimpleDateFormat("MMM dd, yyyy");

			Date recent = tableDate.parse(row);

			val = TimeRecord.dayNumber(recent.getTime());

		} catch (Exception e) {
			return val;
		}
		return val;
	}

	// <font color="color_name|hex_number|rgb_number">
	// rgb(255,0,0)
	public static String generateHtmlColorFromChange(float start, float end, int lightenBy) {
		if (start == 0 && end == 0)
			return "5555aa";
		if (start == 0)
			return "55aa55";

		float change = (end - start) / Math.abs(start);
		if (change > 1)
			change = 1;
		if (change < -1)
			change = -1;
		int red = lightenBy;
		int green = lightenBy;
		int blue = lightenBy;
		if (change < 0)
			red += -(int) (255 * change);
		if (change > 0)
			green += (int) (255 * change);
		if (change == 0)
			blue = 200;
		if (red > 255)
			red = 255;
		if (green > 255)
			green = 255;
		if (blue > 255)
			blue = 255;
		return "" + Integer.toHexString(red) + "," + Integer.toHexString(green) + "," + Integer.toHexString(blue);

	}

	public static String generateHtmlColorFromEarningsToCapitalRatio(float twoYearsAgo, float marketCap, float FACTOR, int lightenBy) {

		if (marketCap != marketCap || marketCap == 0)
			return "0000ff";
		// float FACTOR = 5;// annual earnings times FACTOR / cap
		// if earnings is one X of marketcap ratio is 1
		float ratio = FACTOR * (twoYearsAgo) / (marketCap + 0.1f);
		if (ratio > 1)
			ratio = 1;
		if (ratio < -1)
			ratio = -1;
		int red = lightenBy;
		int green = lightenBy;
		int blue = lightenBy;
		if (ratio < 0)
			red += -(int) (255 * ratio);
		if (ratio > 0)
			green += (int) (255 * ratio);
		if (ratio == 0)
			blue = 200;
		if (red > 255)
			red = 255;
		if (green > 255)
			green = 255;
		if (blue > 255)
			blue = 255;

		return "" + Integer.toHexString(red) + "," + Integer.toHexString(green) + "," + Integer.toHexString(blue);
	}

	public static float tryToExtractFromTable(String annualIncomeTableHtml, String incomeRowLabel, int column) {
		float val = Float.NaN;
		try {
			String row = StringTool.clip(annualIncomeTableHtml, incomeRowLabel, "</tr>", null);
			if (row == null)
				return val;
			row = row.replaceAll("\\s", "");
			row = row.replaceAll("\\(", "-");
			row = row.replaceAll("</td>", " ");
			String regex = "[^\\d\\s-]";
			row = row.replaceAll(regex, "").trim();

			String[] values = ((row.split(" ")));
			if (column < values.length)
				val = NumberTool.tryToParseFloat(values[column], Float.NaN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	public static String removeHtmlTags(String withHtml) {

		return withHtml.replaceAll("<.*?>", "");
	}

	public static String generateHtmlColorFromChange(float change, int lightenBy) {
		if (change != change || Float.isInfinite(change))
			return "5555aa";

		if (change > 1)
			change = 1;
		if (change < -1)
			change = -1;
		int red = lightenBy;
		int green = lightenBy;
		int blue = lightenBy;
		if (change < 0)
			red += -(int) (255 * change);
		if (change > 0)
			green += (int) (255 * change);
		if (change == 0)
			blue = 200;
		if (red > 255)
			red = 255;
		if (green > 255)
			green = 255;
		if (blue > 255)
			blue = 255;
		return "" + Integer.toHexString(red) + "," + Integer.toHexString(green) + "," + Integer.toHexString(blue);
	}

}
