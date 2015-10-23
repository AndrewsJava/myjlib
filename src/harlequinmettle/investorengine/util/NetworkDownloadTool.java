package harlequinmettle.investorengine.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class NetworkDownloadTool {

	int maxMemoryUsed = 0;
	boolean alreadyOverTime;
	int memIncreases = 0;
	long startTime = System.currentTimeMillis();

	/**
	 * This method returns a String of http data f
	 * 
	 * @param address
	 *            The http address to get string from.
	 */
	public String getData(StorageMechanismInterface cache, String address) {
		takeABreak(20);
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
			// dont report all these sockettimeoutexpetions
			// about 5% of all url calls
			if (!(e instanceof SocketTimeoutException))
				// DSLog.ifDebuggingLog(e.toString());
				return null;
		}

		String str = sb.toString();
		str = str.replaceAll("\\s", " ");
		while (str.contains("  "))
			str = str.replaceAll("  ", " ");
		// 1mb limit object storage
		if ((cache != null) && str != null && str.length() > 100 && str.length() < 999e3)
			cache.store(address, str);
		return str;
	}

	public void takeABreak(long milliseconds) {
		try {
			checkMemoryUse();
			checkElapsedTime(startTime);
			Thread.sleep(milliseconds);
		} catch (Exception e) {
		}
	}

	private void checkElapsedTime(long startTimeMillis) {
		if (alreadyOverTime)
			return;
		long elapsed = System.currentTimeMillis() - startTimeMillis;
		elapsed /= 1000;
		elapsed /= 60;

		if (elapsed > 150) {
			alreadyOverTime = true;

		}
	}

	private void checkMemoryUse() {

		int currentTotalMemory = SystemTool.getTotalMemoryUsed();

		//
		if (currentTotalMemory > maxMemoryUsed) {
			maxMemoryUsed = currentTotalMemory;
			if (maxMemoryUsed > 48)
				logMemoryUseage();

		}
	}

	private void logMemoryUseage() {
		memIncreases++;
		float totalMemory = SystemTool.getTotalMemoryUsed();

		float maxMemory = (float) SystemTool.getMaxMemoryAvailable();

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
		}

	}
}
