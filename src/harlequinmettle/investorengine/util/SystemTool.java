package harlequinmettle.investorengine.util;

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

}
