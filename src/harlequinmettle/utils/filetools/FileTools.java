package harlequinmettle.utils.filetools;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class FileTools {

	public static String tryToReadFileToString(File f, String defaultValue) {
		try {
			return FileUtils.readFileToString(f);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static boolean tryToWriteStringToFile(File file, String textToSave) {
		try {
			file.getParentFile().mkdirs();	
			} catch (Exception e) {
				e.printStackTrace();}
		try {
			FileUtils.writeStringToFile(file, textToSave);		
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return true;
	}
}
