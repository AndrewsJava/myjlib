package harlequinmettle.utils.guitools;

import javax.swing.JFrame;

public class JFrameFactory {

	public static JFrame displayFullScreenJFrame(String title) {
		JFrame display = new JFrame(title);
		display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		display.setExtendedState(JFrame.MAXIMIZED_BOTH);
		display.setVisible(true);
		return display;
	}

	public static JFrame displayJFrame(String title) {
		JFrame display = new JFrame(title);
		display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		display.setSize(800, 200);
		display.setVisible(true);
		return display;
	}

	public static JFrame displayFullScreenPrimaryApplicationJFrame(String title) {
		JFrame display = new JFrame(title);
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setExtendedState(JFrame.MAXIMIZED_BOTH);
		display.setVisible(true);
		return display;
	}
}
