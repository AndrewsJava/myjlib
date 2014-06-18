package harlequinmettle.utils.guitools;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class JLabelFactory {
	public static JLabel doBluishJLabel(String string) {
		JLabel general = new JLabel(string);
		general.setOpaque(true);
		general.setBackground(new Color(100, 200, 250));
		return general;
	}
	public static JLabel doLightBluishJLabel(String string) {
		JLabel general = new JLabel(string);
		general.setOpaque(true);
		general.setBackground(new Color(150, 230, 250));
		return general;
	}
	public static JLabel doBluishJLabel(String string,Font font) {
		JLabel general = new JLabel(string);
		general.setFont(font);
		general.setOpaque(true);
		general.setBackground(new Color(100, 200, 250));
		return general;
	}
	public static JLabel doLightBluishJLabel(String string, Font font) {
		JLabel general = new JLabel(string);
		general.setFont(font);
		general.setOpaque(true);
		general.setBackground(new Color(150, 230, 250));
		return general;
	}
}
