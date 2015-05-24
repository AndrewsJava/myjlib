package harlequinmettle.utils.filetools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

public class FileTools {
	private static TreeMap<String, Boolean> fileLocks = new TreeMap<String, Boolean>();

	public static String tryToReadFileToString(File f, String defaultValue) {
		try {
			return FileUtils.readFileToString(f);
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static boolean tryToWriteStringToFile(File file, String textToSave) {
		int tries = 0;
		if (fileLocks.containsKey(file.getAbsolutePath()) && fileLocks.get(file.getAbsolutePath()))
			return false;
		fileLocks.put(file.getAbsolutePath(), true);
		try {
			file.getParentFile().mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileUtils.writeStringToFile(file, textToSave);
		} catch (Exception e2) {
			e2.printStackTrace();
			fileLocks.put(file.getAbsolutePath(), false);
			return false;
		}
		fileLocks.put(file.getAbsolutePath(), false);
		return true;
	}

	// Mar 28, 2015 9:52:16 AM
	public static BufferedImage tryToReadImageFromFile(File imageFile) {
		if (!imageFile.exists())
			return null;
		try {
			return (ImageIO.read(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
}
