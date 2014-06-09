package harlequinmettle.utils.guitools;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class PreferredJScrollPane extends JScrollPane {
	public PreferredJScrollPane() {
		this.setPreferredSize(new Dimension(600, 300));
		this.getVerticalScrollBar().setUnitIncrement(32);
	}

	public PreferredJScrollPane(JComponent jComp) {
		this.setViewportView(jComp);
		this.setPreferredSize(new Dimension(600, 300));
		this.getVerticalScrollBar().setUnitIncrement(32);
	}

}
