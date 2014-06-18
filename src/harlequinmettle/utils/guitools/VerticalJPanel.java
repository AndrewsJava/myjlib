package harlequinmettle.utils.guitools;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class VerticalJPanel extends JPanel{
	public VerticalJPanel(){ 
		setLayout(new GridLayout(0, 1));
	}
	public VerticalJPanel(int columns){ 
		setLayout(new GridLayout(0,columns));
	}
}
