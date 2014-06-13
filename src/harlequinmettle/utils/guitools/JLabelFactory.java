package harlequinmettle.utils.guitools;

import java.awt.Color;

import javax.swing.JLabel;

public class JLabelFactory {
	public static JLabel doBluishJLabel(String string) {
		JLabel general = new JLabel(string);
		general.setOpaque(true);
		general.setBackground(new Color(100, 200, 250));
		return general;
	}
}
