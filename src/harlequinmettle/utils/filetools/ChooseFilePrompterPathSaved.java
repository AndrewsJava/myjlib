package harlequinmettle.utils.filetools;

import harlequinmettle.utils.guitools.JFrameFactory;

import java.io.File;

public class ChooseFilePrompterPathSaved {
	SavedSettings savedsettings;
	String pathToObject;

	public String getSetting(String key) {
		if (savedsettings.settings.containsKey(key)) {
			return savedsettings.settings.get(key);
		} else {

			JFrameFactory.displayJFrame(key);
			String savedPath = ChooseFilePrompter.filePathChooser();
			savedsettings.settings.put(key, savedPath);
			SerializationTool.serializeObject(savedsettings, pathToObject);
			return savedPath;
		}
	}

	public ChooseFilePrompterPathSaved(String folderName, String pathToObject) {
		if (!new File(folderName).exists())
			new File(folderName).mkdir();
		pathToObject = folderName + File.separator + pathToObject;
		this.pathToObject = pathToObject;
		File objectFile = new File(pathToObject);
		if (objectFile.exists()) {
			savedsettings = SerializationTool.deserializeObject(SavedSettings.class, pathToObject);
		} else {
			savedsettings = new SavedSettings();
		}
	}
}
