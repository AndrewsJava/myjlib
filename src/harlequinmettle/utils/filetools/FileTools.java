package harlequinmettle.utils.filetools;

import harlequinmettle.utils.stringtools.StringTool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

	public static Collection<String> tryToReadFileToCollection(File file) {
		Collection<String> contents = new ArrayList<String>();
		String fileContents = tryToReadFileToString(file, null);
		if (fileContents == null)
			return contents;
		String[] lines = StringTool.getLines(fileContents);
		contents.addAll(Arrays.asList(lines));
		return contents;
	}

	// Aug 24, 2015 10:55:31 AM
	public static void tryToWriteCollectionToFileLines(File file, Collection collection) {
		if (collection == null || collection.isEmpty())
			return;
		String fileContents = "";
		for (Object line : collection) {
			fileContents += line.toString() + "\n";

		}
		tryToWriteStringToFile(file, fileContents.trim());

	}
}
