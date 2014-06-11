package harlequinmettle.utils.filetools;

import java.io.File;

public class ChooseFilePrompterPathSaved {
	SavedSettings savedsettings;
	String pathToObject;

	public String getSetting(String key){
		if(savedsettings.settings.containsKey(key)){
			return savedsettings.settings.get(key);
		}
		else{

			String savedPath = ChooseFilePrompter.filePathChooser();
			savedsettings.settings.put(key, savedPath);
			SerializationTool.serialize(savedsettings, pathToObject);
			return savedPath;
		}
	}
	public ChooseFilePrompterPathSaved(String pathToObject) {
		this.pathToObject = pathToObject;
		File objectFile = new File(pathToObject);
		if (objectFile.exists()) {
			savedsettings = SerializationTool.deserialize(SavedSettings.class,
					pathToObject); 
		} else {
			savedsettings = new SavedSettings();
		}
	}
}
