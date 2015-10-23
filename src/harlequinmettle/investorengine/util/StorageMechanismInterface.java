package harlequinmettle.investorengine.util;

import java.util.concurrent.ConcurrentSkipListMap;

public interface StorageMechanismInterface {
	public ConcurrentSkipListMap<String, String> removeAll();

	public void store(String key, String value);

	public String get(String key, String defaultValue);

}
