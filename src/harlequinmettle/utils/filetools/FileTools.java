package harlequinmettle.utils.filetools;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class FileTools {

	public static String tryToReadFileToString(File f, String defaultValue){
		try{
		 return FileUtils.readFileToString(f);
		}catch(Exception e){ 
		}
		return defaultValue;
	}
}
