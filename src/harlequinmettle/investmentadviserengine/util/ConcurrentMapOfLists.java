package harlequinmettle.investmentadviserengine.util;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

//Apr 26, 2015  9:23:22 AM 
public class ConcurrentMapOfLists {
	public ConcurrentSkipListMap<String, CopyOnWriteArrayList<Float>> addingListStringKeyMap = new ConcurrentSkipListMap<String, CopyOnWriteArrayList<Float>>();

	public void putAdd(String key, float value) {

		if (addingListStringKeyMap.containsKey(key)) {
			CopyOnWriteArrayList<Float> existing = addingListStringKeyMap.get(key);
			existing.add(value);
		} else {
			CopyOnWriteArrayList<Float> newList = new CopyOnWriteArrayList<Float>();
			newList.add(value);
			addingListStringKeyMap.put(key, newList);
		}
	}

	public float getAverage(String key) {
		if (addingListStringKeyMap.containsKey(key))
			return MathTool.avgFloat(addingListStringKeyMap.get(key));
		return Float.NaN;
	}

	// Jun 10, 2015 1:00:17 PM
	public float getN(String key) {
		if (addingListStringKeyMap.containsKey(key))
			return (addingListStringKeyMap.get(key).size());
		return Float.NaN;

	}

}
