package harlequinmettle.utils.guitools;

import javax.swing.JFrame;

public class DisplayJFrame {
public DisplayJFrame(String title){
	JFrame display = new JFrame(title);
	display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	display.setSize(800, 200);
	display.setVisible(true);
}
}
