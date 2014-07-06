package harlequinmettle.utils.nettools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

public class NetPuller {
	// http://finance.yahoo.com/q/hp?s=ALCO
	// dividend history
	// http://finance.yahoo.com/q/hp?s=GE&g=v
	private static int counter = 0;
	// public static long inputBytes = DatastoreAccess.getPreviousByteCount();
	public static final String yahoobase = "http://finance.yahoo.com/q";
	public static String longString = "123456789012345678901234567890123456789012345678901234567890-12345678901234567890-12345678901234567890";
	 
	//public static AtomicInteger fetchFails = new AtomicInteger(0);
	public static final TreeMap<String, Integer> errorCount = new TreeMap<String, Integer>();

	/**
	 * This method returns a String of http data f
	 * 
	 * @param suf
	 *            The http address to get string from.
	 */
	public static String getHtml2(String suf) {
		takeABreak(50);
		URL url;
		InputStream is;
		InputStreamReader isr;
		BufferedReader r;
		String str = "";
		String nl = "";

		try {
			url = new URL(suf);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setInstanceFollowRedirects(true);
			int timeoutLimit = 20000;
			conn.setConnectTimeout(timeoutLimit);
			conn.setReadTimeout(timeoutLimit);
			is = url.openStream();
			isr = new InputStreamReader(is);
			r = new BufferedReader(isr);
			do {
				nl = r.readLine();
				if (nl != null) {
					nl = nl.trim() + " ";
					str += nl;
				}
			} while (nl != null);
			r.close();
		} catch (Exception e) {
			//dont report all these sockettimeoutexpetions
			//about 5% of all url calls
		//	if(!(e instanceof SocketTimeoutException))
	 
			// logError(e);
			return null;
		}
		return str;
	}

	private static void logError(Exception ex) {
		try {
			String key = ex.toString().split(":")[0];
			if (errorCount.containsKey(key)) {
				int errors = errorCount.get(key) + 1;
				errorCount.put(key, errors); 
			} else {
				errorCount.put(key, 1); 
			}
		} catch (Exception e) {

		}
	}

	// ///////////replace @,^,shorten to end of table,
	public static String reformat(String input) {
		String output = input.replaceAll("@", "_").replaceAll("^", "_")
				.replaceAll("\\*", "_");
		if (input.contains("</table>"))
			output = output.substring(0, output.indexOf("</table>"));
		output = output.replaceAll("d><t", "d> <t").replaceAll("h><t", "h> <t")
				.replaceAll("d></t", "d>@</t").replaceAll("&nbsp;", "-")
				.replaceAll("--", "");
		return output;
	}

	/**
	 * This method removes html tags such as <a href = ...> and
	 * <table>
	 * 
	 * @param withhtml
	 *            The string text with tags included
	 */
	public static String removeHtml(String withhtml, boolean colon) {
		String stripped = withhtml;//
		String save = "";
		if (colon)
			if (stripped.indexOf(":") > 0) {// jump ahead just past the colons
				stripped = withhtml.substring(withhtml.indexOf(":") + 1);
			}
		// skip all sections of html code between the <htmlcode>
		while (stripped.indexOf("<") >= 0 && stripped.indexOf(">") > 0) {
			stripped = stripped.substring(stripped.indexOf(">") + 1);
			if (stripped.indexOf("<sup") < 2 && stripped.indexOf("<sup") > -1)
				stripped = stripped.substring(stripped.indexOf("</sup>") + 6);
			if (stripped.indexOf("<") > 0)// keep any text inbetween
											// <code>keeptext<code>
				save += stripped.substring(0, stripped.indexOf("<"));
		}
		// save = save.replaceAll("-","_");//VITAL TO PRESERVE NEGATIVES
		save = save.replaceAll(" ", "_");
		save = save.replaceAll("___", "_");
		save = save.replaceAll("__", "_");
		save = save.replaceAll("_-_", "_");
		return save;

	}

	public static String pastDividends(String ticker) {

		String httpdata = getHtml2(yahoobase + "/hp?s=" + ticker + "&g=v");
		if (httpdata == null) {
			return null;
		} 

		if ((!httpdata.contains("Adj Close"))
				|| httpdata
						.contains("Dividend data is unavailable for the specified date range"))
			return "NO_DIVIDEND_HISTORY";
		httpdata = removeHtml(
				reformat(httpdata.substring(httpdata.indexOf("Adj Close"))),
				false);
		System.out.println("reformated----------:     " + httpdata);
		httpdata = httpdata.substring(0, httpdata.lastIndexOf("@"));
		if (httpdata.indexOf("@") < 2)
			httpdata = httpdata.substring(httpdata.indexOf("@") + 1);
		if (httpdata.lastIndexOf("@") > 1)
			httpdata = (httpdata.substring(0, httpdata.lastIndexOf("@"))
					.replaceAll(",", ""));
		return httpdata;
	}

	public static void downloadProfile(String ticker,
			TreeMap<String, String> companyDetails,
			TreeMap<String, String> companyDescription) {
		String httpdata = "";
		try {
			// http://finance.yahoo.com/q/pr?s=UCBA
			httpdata = getHtml2(yahoobase + "/pr?s=" + ticker);
//			if (httpdata.equals(ERROR_MESSAGE)) {
//				companyDetails.put(ticker, ERROR_MESSAGE);
//				companyDescription.put(ticker, ERROR_MESSAGE);
//			} 
			// System.out.println(httpdata);
			if (!httpdata.contains(">Details<")) {
				companyDetails.put(ticker, "<td>NO_DETAILS</td>");
				companyDescription.put(ticker, "<td>NO_DETAILS</td>");
				return;
			}
			httpdata = httpdata.substring(httpdata.indexOf(">Details<"));
			httpdata = httpdata.substring(httpdata.indexOf("<tr>"));
			String detailsTable = httpdata.substring(0,
			// httpdata.indexOf("</table>"));
					httpdata.indexOf("</p>") + 4);
			// detailsTable+="</table></td> </tr>";
			String profileDescription = httpdata.substring(httpdata
					.indexOf("<p>"));
			profileDescription = httpdata.substring(0,
					httpdata.indexOf("</p>") + 4);

			companyDetails.put(ticker, "<table>" + detailsTable);
			companyDescription.put(ticker, profileDescription);
			// DSLog.log(ticker+"DETAILS", detailsTable);
			// DSLog.log(ticker+"DESCRIPTION", profileDescription);
		} catch (Exception e) {
			companyDetails.put(ticker, "NO DETAILS AT: " + yahoobase + "/pr?s="
					+ ticker);
			companyDescription.put(ticker, "NO DESCRIPTION AT: " + yahoobase
					+ "/pr?s=" + ticker);
 

		}
	}

	public static void downloadSummary(String ticker,
			TreeMap<String, String> companySummary) {
		String httpdata = "";
		try {
			// http://finance.yahoo.com/q?s=UCBA
			httpdata = getHtml2(yahoobase + "?s=" + ticker);
//			if (httpdata.equals(ERROR_MESSAGE)) {
//				companySummary.put(ticker, ERROR_MESSAGE);
//			} 
			// System.out.println(httpdata);
			if (!httpdata.contains("<div class=\"title\">")) {
				companySummary.put(ticker, "<td>NO_DETAILS</td>");
				return;
			}
			httpdata = httpdata.substring(httpdata
					.indexOf("<div class=\"title\">"));
			String ender = "Currency in USD.</div>";
			httpdata = httpdata.substring(0,
					httpdata.indexOf(ender) + ender.length());
			httpdata = httpdata
					.replace(
							"Quotes delayed, except where indicated otherwise. Currency in USD.",
							"");

			// httpdata = httpdata.replaceAll("\\s", " ");
			// httpdata = httpdata.replaceAll("<div.*>", "");
			// httpdata = httpdata.replaceAll("</div>", "");
			httpdata = httpdata.replaceAll("<h2>", "");
			httpdata = httpdata.replaceAll("</h2>", "");
			// httpdata = httpdata.replaceAll("src=\".*\"", "");
			httpdata = httpdata.replaceAll(
					"Get the big picture on all your investments.", " ");
			httpdata = httpdata
					.replaceAll("Sync your Yahoo portfolio now", " ");
			// DSLog.log(ticker+"SUMMARY", httpdata);
			companySummary.put(ticker, httpdata);
		} catch (Exception e) {
			companySummary.put(ticker, "NO SUMMARY AT: " + yahoobase + "?s="
					+ ticker);
 

		}
	}

	public static void takeABreak(long milliseconds) {
		try { 
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		//	DSLog.log(e);
		}
	}
}
