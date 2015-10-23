package harlequinmettle.investmentadviserengine.util;

import harlequinmettle.investorengine.util.StorageMechanismInterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkDownloadTool {
	private static long successes = 0;
	private static long failures = 0;

	private static NetworkDownloadTool singleton;

	private NetworkDownloadTool() {

	}

	public static NetworkDownloadTool getNetworkDownloadToolSingletonReference() {
		if (singleton == null)
			singleton = new NetworkDownloadTool();
		return singleton;
	}

	/**
	 * This method returns a String of http data f
	 * 
	 * @param address
	 *            The http address to get string from.
	 */
	public String getDataForEdgarWithDataShortening(StorageMechanismInterface cache, String address) {
		SystemTool.takeABreak(20);
		if (cache != null) {
			String htmlPage = cache.get(address, null);
			if (htmlPage != null) {
				return htmlPage;
			}
		}
		URL url;
		InputStream is;
		InputStreamReader isr;
		BufferedReader r;

		String usefulHtml = new String();
		try {
			url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// conn.setInstanceFollowRedirects(true);
			int timeoutLimit = 30000;
			conn.setConnectTimeout(timeoutLimit);
			conn.setReadTimeout(timeoutLimit - 10);
			is = url.openStream();
			isr = new InputStreamReader(is);
			r = new BufferedReader(isr);
			int bufferSize = 4096;
			char[] buffer = new char[bufferSize];
			int len1 = 0;
			StringBuilder leftovers = new StringBuilder();
			while ((len1 = r.read(buffer)) > 0) {
				String validTagText = breakInputIntoValidTagsAndLeftovers(buffer, len1, leftovers);
				validTagText = StringTool.cleanUpSomeTextAndTags(validTagText).trim();
				usefulHtml += (validTagText + " ");
				// 32 megabyte limit I think
				if (usefulHtml.length() > 3e7)
					break;
			}
			r.close();
		} catch (Exception e) {
			failures++;
			ErrorLog.log(e);

			return null;
		}

		// 1mb limit object storage
		if ((cache != null) && usefulHtml != null && usefulHtml.length() > 100 && usefulHtml.length() < 999e3)
			cache.store(address, usefulHtml);
		successes++;
		return usefulHtml;
	}

	boolean openTag = false;

	// Aug 5, 2015 10:46:23 AM
	private String breakInputIntoValidTagsAndLeftovers(char[] buffer, int len1, StringBuilder leftovers) {

		StringBuilder maxStringWithValidHtmlTag = new StringBuilder();

		StringBuilder tag = new StringBuilder(leftovers);
		leftovers.setLength(0);
		for (int i = 0; i < len1; i++) {

			char c = Character.toLowerCase(buffer[i]);

			if (c == '<') {
				openTag = true;

			}
			if (!openTag)
				maxStringWithValidHtmlTag.append(c);
			else {
				tag.append(c);
				if (c == '>') {
					openTag = false;
					if (!shouldIgnoreTag(tag.toString())) {
						maxStringWithValidHtmlTag.append(tag);
					}
					tag.setLength(0);
				}

			}
		}
		if (openTag) {

			leftovers.append(tag);
		}
		return maxStringWithValidHtmlTag.toString();
	}

	// Aug 5, 2015 11:22:36 AM
	private boolean shouldIgnoreTag(String validsection) {
		String[] regexes = { "<font.*?>", "</font>", "<span.*?>", "</span>", "<div.*?>", "</div>", "<!--.*?-->", "<p.*?>", "</p>", "<a.*?>", "</a>",
				"<b.*?>", "</b>" };
		for (String regex : regexes)
			if (validsection.matches(regex))
				return true;
		return false;
	}

	/**
	 * This method returns a String of http data f
	 * 
	 * @param address
	 *            The http address to get string from.
	 */
	public String getDataForEdgarWithLineByLineDataShortening(StorageMechanismInterface cache, String address) {
		SystemTool.takeABreak(20);
		if (cache != null) {
			String htmlPage = cache.get(address, null);
			if (htmlPage != null) {
				return htmlPage;
			}
		}
		URL url;
		InputStream is;
		InputStreamReader isr;
		BufferedReader r;

		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// conn.setInstanceFollowRedirects(true);
			int timeoutLimit = 30000;
			conn.setConnectTimeout(timeoutLimit);
			conn.setReadTimeout(timeoutLimit - 10);
			is = url.openStream();
			isr = new InputStreamReader(is);
			r = new BufferedReader(isr);
			String line;
			while ((line = r.readLine()) != null) {
				line = StringTool.removeUnnecessaryHtmlTagsFromHtmlLineOfEdgarReport(line).trim();
				sb.append(line + " ");
				// 32 megabyte limit I think
				if (sb.length() > 3e7)
					break;
			}
			r.close();
		} catch (Exception e) {
			failures++;
			ErrorLog.log(e);

			return null;
		}

		String str = sb.toString();
		// 1mb limit object storage
		if ((cache != null) && str != null && str.length() > 100 && str.length() < 999e3)
			cache.store(address, str);
		successes++;
		return str;
	}

	/**
	 *  
	 */
	public ArrayList<String> getLines(String address) {
		SystemTool.takeABreak(20);
		URL url;
		InputStream is;
		InputStreamReader isr;
		BufferedReader r;

		ArrayList<String> downloadedLines = new ArrayList<String>();
		try {
			url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// conn.setInstanceFollowRedirects(true);
			int timeoutLimit = 30000;
			conn.setConnectTimeout(timeoutLimit);
			conn.setReadTimeout(timeoutLimit - 10);
			is = url.openStream();
			isr = new InputStreamReader(is);
			r = new BufferedReader(isr);
			String line;
			int totalSize = 0;
			while ((line = r.readLine()) != null) {
				line = condenseLineSpacing(line);
				totalSize = line.length();
				downloadedLines.add(line);
				// 32 megabyte limit I think
				if (totalSize > 3e7)
					break;
			}
			r.close();
		} catch (Exception e) {
			failures++;
			ErrorLog.log(e);
			return null;
		}
		successes++;
		return downloadedLines;
	}

	private String condenseLineSpacing(String str) {
		str = str.replaceAll("\\s+", " ");
		return str;
	}

	/**
	 * This method returns a String of http data f
	 * 
	 * @param address
	 *            The http address to get string from.
	 */
	public String getData(StorageMechanismInterface cache, String address) {
		SystemTool.takeABreak(20);
		if (cache != null) {
			String htmlPage = cache.get(address, null);
			if (htmlPage != null) {
				return htmlPage;
			}
		}
		URL url;
		InputStream is;
		InputStreamReader isr;
		BufferedReader r;

		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// conn.setInstanceFollowRedirects(true);
			int timeoutLimit = 30000;
			conn.setConnectTimeout(timeoutLimit);
			conn.setReadTimeout(timeoutLimit - 10);
			is = url.openStream();
			isr = new InputStreamReader(is);
			r = new BufferedReader(isr);
			String line;
			while ((line = r.readLine()) != null) {
				line = StringTool.makeStringOneLineWithSingleSpacesBetweenWords(line).trim();
				sb.append(line + " ");
				// 32 megabyte limit I think
				if (sb.length() > 3e7)
					break;
			}
			r.close();
		} catch (Exception e) {
			failures++;
			ErrorLog.log(e);

			return null;
		}

		String str = sb.toString();
		// 1mb limit object storage
		if ((cache != null) && str != null && str.length() > 100 && str.length() < 999e3)
			cache.store(address, str);
		successes++;
		return str;
	}

	/**
	 * This method returns a String of http data f
	 * 
	 * @param address
	 *            The http address to get string from.
	 */
	public String getDataWithoutModifications(StorageMechanismInterface cache, String address) {
		SystemTool.takeABreak(20);
		if (cache != null) {
			String htmlPage = cache.get(address, null);
			if (htmlPage != null) {
				return htmlPage;
			}
		}
		URL url;
		InputStream is;
		InputStreamReader isr;
		BufferedReader r;

		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// conn.setInstanceFollowRedirects(true);
			int timeoutLimit = 30000;
			conn.setConnectTimeout(timeoutLimit);
			conn.setReadTimeout(timeoutLimit - 10);
			is = url.openStream();
			isr = new InputStreamReader(is);
			r = new BufferedReader(isr);
			String line;
			while ((line = r.readLine()) != null) {
				sb.append(line + "\n");
				// 32 megabyte limit I think
				if (sb.length() > 3e7)
					break;
			}
			r.close();
		} catch (Exception e) {
			failures++;
			ErrorLog.log(e);

			return null;
		}

		String str = sb.toString();
		// 1mb limit object storage
		if ((cache != null) && str != null && str.length() > 100 && str.length() < 999e3)
			cache.store(address, str);
		successes++;
		return str;
	}

	public void urlCall(String url) {
		HttpURLConnection conn;

		try {
			// ONE MINUTE LIMIT
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setReadTimeout(60 * 1000);
			conn.setConnectTimeout(60 * 1000);

			InputStream response = conn.getInputStream();

		} catch (Exception e) {
			ErrorLog.log(e);
		}

	}
}
