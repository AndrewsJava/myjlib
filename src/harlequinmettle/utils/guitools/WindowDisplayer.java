package harlequinmettle.utils.guitools;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;

//Mar 22, 2015  10:33:06 AM 
public class WindowDisplayer {
	JFrame display = new JFrame("window displayer");

	public void showComponentInJFrame(JComponent forShowing, boolean primary, boolean fullScreen) {
		if (primary)
			display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		else
			display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (fullScreen)
			display.setExtendedState(display.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		display.setVisible(true);

		display.setLayout(new BorderLayout());

		PreferredJScrollPane scrollable = new PreferredJScrollPane(forShowing, false);
		display.setLayout(new BorderLayout());
		display.add(scrollable, BorderLayout.CENTER);
	}

	public void showComponentInJFrame(JComponent forShowing, boolean primary) {
		if (primary)
			display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		else
			display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		display.setExtendedState(display.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		display.setVisible(true);

		display.setLayout(new BorderLayout());

		PreferredJScrollPane scrollable = new PreferredJScrollPane(forShowing, false);
		display.setLayout(new BorderLayout());
		display.add(scrollable, BorderLayout.CENTER);
	}

	// Jun 22, 2015 9:28:35 AM
	public void setSize(double width, double height) {
		display.setSize((int) width, (int) height);
	}

	// Jun 22, 2015 9:13:18 AM
	public void setTitle(String title) {
		display.setTitle(title);
	}
}
