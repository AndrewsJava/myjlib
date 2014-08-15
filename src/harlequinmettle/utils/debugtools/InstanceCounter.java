package harlequinmettle.utils.debugtools;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

public class InstanceCounter {
	private static final ArrayList<String> HIGH_COUNT_WORDS = new ArrayList<String>();
	private static final TreeMap<String, Integer> STRING_COUNTER = new TreeMap<String, Integer>();
	public static final TreeMap<Float, Integer> FLOAT_COUNTER = new TreeMap<Float, Integer>();

	public void add(String s) {
		if (STRING_COUNTER.containsKey(s)) {
			STRING_COUNTER.put(s, STRING_COUNTER.get(s) + 1);
		} else {
			STRING_COUNTER.put(s, 1);
		}

	}

	public void printlnSize() {

		if (!STRING_COUNTER.isEmpty())
			System.out.println("string count size: " + STRING_COUNTER.size());
		if (!FLOAT_COUNTER.isEmpty())
			System.out.println("float count size: " + FLOAT_COUNTER.size());
	}

	public void add(float s) {
		if (FLOAT_COUNTER.containsKey(s)) {
			FLOAT_COUNTER.put(s, FLOAT_COUNTER.get(s) + 1);
		} else {
			FLOAT_COUNTER.put(s, 1);
		}

	}

	public void printlnCounts(float displayPercent) {
		int counter = 0;
		if (!FLOAT_COUNTER.isEmpty()) {
			for (Entry<Float, Integer> ent : FLOAT_COUNTER.entrySet()) {
				if (counter > 20 && counter < FLOAT_COUNTER.size() - 20 && displayPercent < Math.random())
					continue;
				System.out.println(ent.getKey() + "   occurs  " + ent.getValue());
				counter++;
			}
		}
		if (!STRING_COUNTER.isEmpty()) {
			for (Entry<String, Integer> ent : STRING_COUNTER.entrySet()) {
				System.out.println(ent.getKey() + "   occurs  " + ent.getValue());
			}
		}
	}

	public void printlnCountsCountLimit(int dontShowUnder) {
		int counter = 0;
		if (!FLOAT_COUNTER.isEmpty()) {
			for (Entry<Float, Integer> ent : FLOAT_COUNTER.entrySet()) {
				if (ent.getValue() < dontShowUnder)
					continue;
				System.out.println(ent.getKey() + "   occurs  " + ent.getValue());
				counter++;
			}
		}
		if (!STRING_COUNTER.isEmpty()) {
			for (Entry<String, Integer> ent : STRING_COUNTER.entrySet()) {
				if (ent.getValue() < dontShowUnder || ent.getValue() > 10000)
					continue;
				System.out.println(ent.getKey() + "   occurs  " + ent.getValue());
				STRING_COUNTER.put(ent.getKey(), 10001);
				HIGH_COUNT_WORDS.add(ent.getKey());
				System.out.println(HIGH_COUNT_WORDS);
			}
		}
	}
}
