package harlequinmettle.utils.filetools;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class ChooseFilePrompter {
	
	public static String directoryPathChooser( ) {
		final JFileChooser fc = new JFileChooser( );
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			return file.getAbsolutePath();

		}
		return null;
	}
	
	public static String filePathChooser() {
		final JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			return file.getAbsolutePath();

		}
		return null;
	}
}
