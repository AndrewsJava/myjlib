package harlequinmettle.investorengine.util;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EdgarFilingsListBuilderUtil {

	private static ConcurrentSkipListMap<Integer, String> filingTypesHashCodeMap = new ConcurrentSkipListMap<Integer, String>();

	// ///////////////////////////////////////////////////////////////////

	public static synchronized ConcurrentLinkedQueue<EdgarFiling> buildList(String htmlWithFilingsList, int sizeLimit) {
		ConcurrentLinkedQueue<EdgarFiling> filings = new ConcurrentLinkedQueue<EdgarFiling>();

		if (htmlWithFilingsList == null)
			return filings;
		Pattern patternTableRow = Pattern.compile("<tr.*?>.*?</tr>");
		Matcher matcher = patternTableRow.matcher(htmlWithFilingsList);
		// check all occurance
		int size = 0;
		while (matcher.find()) {
			String tableRow = matcher.group();

			if (tableRow.contains("/Archives/edgar/data")) {
				filings.add(processTableRow(tableRow, size));

			}
			// LIMMIT ALL FILINGS CHECK TO 10
			if (size++ > sizeLimit)
				break;
		}

		return filings;
	}

	private static EdgarFiling processTableRow(String tableRow, int rowIndex) {

		String archiveLink = extractPattern(tableRow, "/Archives/edgar/data/.*?htm");
		String date = extractPattern(tableRow, "<td>20\\d{2}-\\d{2}-\\d{2}</td>");
		// first table data is filing type atm
		String filingType = extractPattern(tableRow, "<td.*?>.*?</td>");
		int filingCode = filingType.hashCode();
		short dayNumber = (short) TimeRecord.dayNumber(TimeDateTool.EDGAR_FILINGS_DATE_FORMAT, date, Float.NaN, false);

		if (filingTypesHashCodeMap != null)
			filingTypesHashCodeMap.put(filingCode, filingType);
		return new EdgarFiling(dayNumber, filingCode);
	}

	protected static String extractPattern(String tableRow, String regex) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(tableRow);

		if (matcher.find()) {
			return matcher.group().replaceAll("<.*?>", "");
		}
		return "";
	}

}
