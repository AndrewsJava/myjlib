package harlequinmettle.utils.guitools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class JSearchPanel extends HorizontalJPanel {

	final JLabel wordSearch = doJLabel("enter ticker");
	final JTextField wordsForSearch = doJTextField();

	final JButtonWithEnterKeyAction submitSearchButton = new JButtonWithEnterKeyAction(
			"Search");

	public JSearchPanel() {
		setPreferredSize(new Dimension(300,75));
//		int MAX_HT = 75;
//		wordSearch.setMaximumSize(new Dimension(150,MAX_HT));
//		wordsForSearch.setMaximumSize(new Dimension(150,MAX_HT));
//		submitSearchButton.setMaximumSize(new Dimension(150,MAX_HT));
		add(wordSearch);
		add(wordsForSearch);
		add(submitSearchButton);
	}

	public void addSearchAction(final ActionListener dosearch){
		submitSearchButton.addActionListener(dosearch);
		wordsForSearch.addActionListener(dosearch);
	 
	}
	public String getSearchText() {
		return wordsForSearch.getText();
	}

	public JTextField doJTextField() {
		JTextField jta = new JTextField();
 
		jta.setBackground(new Color(165, 225, 210));
		return jta;
	}

	public JLabel doJLabel(String string) {
		JLabel general = new JLabel(string);
		general.setOpaque(true);
		general.setBackground(new Color(100, 200, 250));
		return general;
	}
}
