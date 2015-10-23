package harlequinmettle.investmentadviserengine.util;

public class SystemTool {

	public static int getTotalMemoryUsed() {

		return (int) (Runtime.getRuntime().totalMemory() / 1000000) - (int) (Runtime.getRuntime().freeMemory() / 1000000);
	}

	public static int getMaxMemoryAvailable() {

		return (int) ((Runtime.getRuntime().maxMemory()) / 1000000);
	}

	public static float getMemoryUseAsPercentOfMax() {

		return getTotalMemoryUsed() / (float) getMaxMemoryAvailable();
	}

	static int memIncreases = 0;
	static int upTimeMinutes = 0;
	static int maxMemoryUsed = 0;
	static int lastRecordedMemory = 0;
	static boolean alreadyOverTime;
	static long startTime = System.currentTimeMillis();

	private static void checkElapsedTime(long startTimeMillis) {
		// if (alreadyOverTime)
		// return;

		long elapsed = System.currentTimeMillis() - startTimeMillis;
		elapsed /= 1000;
		elapsed /= 60;
		if (elapsed > upTimeMinutes + 5 || upTimeMinutes <= 5) {
			upTimeMinutes = (int) elapsed;

		}
		// if (elapsed > 150) {
		// alreadyOverTime = true;
		// //new DSLog().log("TIME: " + elapsed + "  minutes");
		//
		// }
	}

	private static int round(int number) {

		return 20 * ((int) (number / 20.0));
	}

	public static void checkMemoryUse() {

		int currentTotalMemory = SystemTool.getTotalMemoryUsed();
		int roundedMemory = round(currentTotalMemory);
		if (roundedMemory != lastRecordedMemory) {
			lastRecordedMemory = roundedMemory;
		}
		//
		if (currentTotalMemory > maxMemoryUsed) {
			maxMemoryUsed = currentTotalMemory;
			if (maxMemoryUsed > 48 || memIncreases == 0)
				logMemoryUseage();

		}
	}

	private static void logMemoryUseage() {
		memIncreases++;
		float totalMemory = SystemTool.getTotalMemoryUsed();

		float maxMemory = (float) SystemTool.getMaxMemoryAvailable();

		new DSLog().logByName("00 Memory (" + ")", "" + totalMemory + " / " + maxMemory + "                  (" + memIncreases + ")");
	}

	public static void takeABreak(long milliseconds) {
		try {
			checkMemoryUse();
			checkElapsedTime(startTime);
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			ErrorLog.log(e);
		}
	}

	public static String getCurrentStackTrace(boolean asHtml) {

		StackTraceElement[] names = Thread.currentThread().getStackTrace();
		String logMessage = "";

		// 0 = runtime
		// 1 = this method: addProgramExecutionDetails
		// 2 = method calling this addProgramExecutionDetails (DSLog.log and
		// object_asBlob_toDatastore...)
		// 3 =
		for (int i = 2; i < names.length; i++) {
			StackTraceElement st = names[i];
			String nextLog = "[" + st.getFileName().replaceAll("java", st.getMethodName()) + "]" + "{" + st.getLineNumber() + "}               ";
			if (asHtml)
				nextLog += "<br>";
			logMessage += nextLog + System.lineSeparator();

			if (i > 22)
				break;
		}
		return logMessage;
	}

	public static String getStackTraceForException(Exception e, boolean asHtml) {
		StackTraceElement[] names = e.getStackTrace();
		String logMessage = "";

		try {
			// 0 = runtime
			// 1 = this method: addProgramExecutionDetails
			// 2 = method calling this addProgramExecutionDetails (DSLog.log and
			// object_asBlob_toDatastore...)
			// 3 =
			for (int i = 2; i < names.length; i++) {
				StackTraceElement st = names[i];
				String nextLog = "[" + st.getFileName().replaceAll("java", st.getMethodName()) + "]" + "{" + st.getLineNumber() + "}               ";
				if (asHtml)
					nextLog += "<br>";
				logMessage += nextLog + System.lineSeparator();

				if (i > 22)
					break;
			}
		} catch (Exception ex) {

		}
		return logMessage;
	}

	public static long getHoursAgoFromNowFromMilliseconds(long millisecondTimeStamp) {
		// Oct 7, 2015 9:40:58 AM
		long currentTimeHours = System.currentTimeMillis() / (60 * 60 * 1000);
		long timeStampHours = millisecondTimeStamp / (60 * 60 * 1000);
		return (currentTimeHours - timeStampHours);
	}
}
