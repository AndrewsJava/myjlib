package harlequinmettle.utils.debugtools;

import java.util.Map.Entry;
import java.util.TreeMap;

public class InstanceCounter {
	private static final TreeMap<String, Integer> STRING_COUNTER = new TreeMap<String, Integer>();
	private static final TreeMap<Float, Integer> FLOAT_COUNTER = new TreeMap<Float, Integer>();

	public   void add(String s) {
		if (STRING_COUNTER.containsKey(s)) {
			STRING_COUNTER.put(s, STRING_COUNTER.get(s) + 1);
		} else {
			STRING_COUNTER.put(s, 1);
		}

	}
public void printlnSize(){

	if (!STRING_COUNTER.isEmpty()) 
			System.out.println("string count size: "+STRING_COUNTER.size() );
	if (!FLOAT_COUNTER.isEmpty()) 
		System.out.println("float count size: "+FLOAT_COUNTER.size() );
}
	public   void add(float s) {
		if (FLOAT_COUNTER.containsKey(s)) {
			FLOAT_COUNTER.put(s, FLOAT_COUNTER.get(s) + 1);
		} else {
			FLOAT_COUNTER.put(s, 1);
		}

	}

	public void printlnCount() {
		if (!FLOAT_COUNTER.isEmpty()) {
			for (Entry<Float, Integer> ent : FLOAT_COUNTER.entrySet()) {
				System.out.println(ent.getKey() + "   occurs  "
						+ ent.getValue());
			}
		}
		if (!STRING_COUNTER.isEmpty()) {
			for (Entry<String, Integer> ent : STRING_COUNTER.entrySet()) {
				System.out.println(ent.getKey() + "   occurs  "
						+ ent.getValue());
			}
		}
	}
}
