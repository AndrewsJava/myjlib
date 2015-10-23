package harlequinmettle.investorengine.util;

public class UrlBuilderTool {

	public static final String YAHOO_ROOT = "http://finance.yahoo.com/q";

	public final static String SEC_ROOT = "http://www.sec.gov";

	public static final String NEW_TAB_TARGET = " target=\"_blank\"";
	public static final String SAME_TAB_TARGET = "";

	// http://[instance]-dot-[backend_name]-dot-[your_app_id].appspot.com
	// http://[backend_name]-dot-[your_app_id].appspot.com,

	public static String getHtmlHeadWithJQueryReference() {
		String jQRef = "<head><script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js\"></script> <script src=\"/javascript/jquery.tablesorter.js\"></script> </head>";
		return jQRef;
	}

	public static String getUrlForDailyData(String ticker) {

		return "http://real-chart.finance.yahoo.com/table.csv?s=" + ticker + "&g=d";

	}

	public static String getUrlForMinuteData(String ticker) {

		return "http://chartapi.finance.yahoo.com/instrument/1.0/" + ticker + "/chartdata;type=quote;range=1d/csv";

	}

	public static String buildTickerLookupUrl(String tickerLookup) {
		// http://www.sec.gov/cgi-bin/browse-edgar?CIK=ABCDE&Find=Search&owner=exclude&action=getcompany
		return SEC_ROOT + "/cgi-bin/browse-edgar?CIK=" + tickerLookup.trim() + "&Find=Search&owner=exclude&action=getcompany";
	}

	public static String generateHref(String link, String text, String target) {
		return "<a href=\"" + link + "\" " + target + ">" + text + "</a>";
	}

	public static String constructEarningsTableUrl(String ticker, String period) {
		return YAHOO_ROOT + "/is?s=" + ticker + "&" + period;
	}

	public static String generateGoogleFinanceLink(String ticker) {

		String index = "N";
		// if
		// (DatabaseCore.tickerSymbolDatabase.get(ticker.hashCode()).isNasdaq)
		// index = DatabaseCore.NASDAQ;
		return "https://www.google.com/finance?q=" + index + "%3A" + ticker;
	}

	public static String getSummaryLink(String ticker) {
		return YAHOO_ROOT + "?s=" + ticker;
	}

	public static String getProfileLink(String ticker) {
		return YAHOO_ROOT + "/pr?s=" + ticker;
	}

	public static String getAnnualIncomeTableLink(String ticker) {
		return YAHOO_ROOT + "/is?s=" + ticker + "+Income+Statement&" + "annual";
	}

	public static String getQuarterlyIncomeTableLink(String ticker) {
		return YAHOO_ROOT + "/is?s=" + ticker + "+Income+Statement&" + "quarterly";
	}
}
