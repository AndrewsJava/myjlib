package harlequinmettle.utils.filetools;

import java.io.Serializable;
import java.util.TreeMap;

public class SavedSettings implements Serializable{
	public TreeMap<String,String> settings = new TreeMap<String,String>();

}
