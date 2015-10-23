package harlequinmettle.investmentadviserengine.util;

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentIncrementingHackMap {
	public static final double COUNT_FACTOR = 1000000;
	public static final double VALUE_FACTOR = 1;
	public static final double incrementer = 1 / COUNT_FACTOR;
	public ConcurrentSkipListMap<Integer, Double> countingMap = new ConcurrentSkipListMap<Integer, Double>();
	public ConcurrentSkipListMap<String, Integer> countingStringMap = new ConcurrentSkipListMap<String, Integer>();

	public void putAdd(int key, int value) {

		if (countingMap.containsKey(key)) {
			double existingValue = countingMap.get(key);
			existingValue += incrementer;
			existingValue += (int) (value / VALUE_FACTOR);
			countingMap.put(key, existingValue);
		} else {
			countingMap.put(key, value + incrementer);
		}
	}

	public void countString(String key) {

		if (countingStringMap.containsKey(key)) {
			int existingValue = countingStringMap.get(key);
			existingValue++;
			countingStringMap.put(key, existingValue);
		} else {
			countingStringMap.put(key, 1);
		}
	}

	public void countString(String key, int value) {

		if (countingStringMap.containsKey(key)) {
			int existingValue = countingStringMap.get(key);
			existingValue += value;
			countingStringMap.put(key, existingValue);
		} else {
			countingStringMap.put(key, value);
		}
	}
}
