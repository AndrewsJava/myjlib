package harlequinmettle.utils.datastructure;

import java.util.concurrent.ConcurrentSkipListMap;

//Mar 24, 2015  9:32:02 PM 
public class ConcurrentHackMaxMap {
	public ConcurrentSkipListMap<String, Double> factorStringMap = new ConcurrentSkipListMap<String, Double>();

	public void putIfMax(String key, double value) {
		if (factorStringMap.containsKey(key)) {
			double existingValue = factorStringMap.get(key);
			if (value > existingValue)
				factorStringMap.put(key, value);
		} else {
			factorStringMap.put(key, value);
		}
	}

}
