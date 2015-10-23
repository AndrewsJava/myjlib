package harlequinmettle.utils.datastructure;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class HashCodeDatabaseMap<K,V> extends  ConcurrentSkipListMap<Integer,String> implements Serializable{
 
	private static final long serialVersionUID = -4010165959019327230L;
	transient TreeMap<String,String> duplicateHashCodes = new TreeMap<String,String>();
	public HashCodeDatabaseMap(){
		super();
	}

	@Override
	public String put(Integer key, String value) {
	 compareExistingToNewKeys(key,value);
		return super.put(key, value);
	}

	private void compareExistingToNewKeys(Integer key, String value) {
	if(this.containsKey(key)){
		String existing = this.get(key);
		if(!existing.equals(value)){
			System.out.println("duplicate hash codes: "+existing +"{"+existing.hashCode()+"}    ,  "+value+"{"+value.hashCode()+"}");
			duplicateHashCodes.put(existing, value);
			System.out.println("all duplicates "+duplicateHashCodes);
		}
	}
		
	}
	
	 
}
