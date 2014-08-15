package harlequinmettle.utils.nettools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetPuller {
	// http://finance.yahoo.com/q/hp?s=ALCO
	// dividend history
	// http://finance.yahoo.com/q/hp?s=GE&g=v
	private static int counter = 0;
	// public static long inputBytes = DatastoreAccess.getPreviousByteCount();
	public static final String yahoobase = "http://finance.yahoo.com/q";
	public static String longString = "123456789012345678901234567890123456789012345678901234567890-12345678901234567890-12345678901234567890";

	// public static AtomicInteger fetchFails = new AtomicInteger(0);
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

		StringBuilder sb = new StringBuilder();
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
			String line;
			while ((line = r.readLine()) != null) {
				sb.append(line + " ");
				// 32 megabyte limit I think
				if (sb.length() > 3e7)
					break;
			}
			r.close();
		} catch (Exception e) {

		}

		String str = sb.toString();
		str = str.replaceAll("\\s", " ");
		while (str.contains("  "))
			str = str.replaceAll("  ", " ");
		return str;
	}

	public static void getHtmlTables(String suf, String fileName) {
		takeABreak(50);
		URL url;
		InputStream is;
		String str = "";
		String nl = "";
		File destination = new File(fileName);
		destination.getParentFile().mkdirs();
		try {
			url = new URL(suf);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setInstanceFollowRedirects(true);
			int timeoutLimit = 60000;
			conn.setConnectTimeout(timeoutLimit);
			conn.setReadTimeout(timeoutLimit);
			is = conn.getInputStream();
			// ///////////////////////////////////////////////////////////
			OutputStream os = new FileOutputStream(destination);
			try {
				byte[] bytes = new byte[1024];
				for (;;) {
					int count = is.read(bytes, 0, 1024);
					if (count == -1)
						break;
					os.write(bytes, 0, count);

				}
			} catch (Exception ex) {
			}
			os.close();
			// //////////////////////////////////////////////////////////////////

		} catch (Exception e) {
			// dont report all these sockettimeoutexpetions
			// about 5% of all url calls
			// if(!(e instanceof SocketTimeoutException))

			// logError(e);

		}
	}

	private static String extractTables(String str) {
		String tables = "";
		Pattern patternTableRow = Pattern.compile("<table.*?>.*?</table>");
		Matcher matcher = patternTableRow.matcher(str);
		// check all occurance
		while (matcher.find()) {
			String tableRow = matcher.group();

			tables += tableRow;
		}
		return tables;
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

	public static void takeABreak(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
			// DSLog.log(e);
		}
	}
}
