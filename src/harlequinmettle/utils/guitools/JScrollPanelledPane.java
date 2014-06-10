package harlequinmettle.utils.guitools;
 

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JScrollPanelledPane extends JScrollPane {
	public ArrayList<JComponent> parts = new ArrayList<JComponent>();
	public JPanel entireView = new VerticalJPanel();

	public JScrollPanelledPane() {
 
		setViewportView(entireView); 
		// setPreferredSize(new Dimension(300, 300));
		getVerticalScrollBar().setUnitIncrement(32);
	}

 
	public void addComp(JComponent... comp ) {
		JPanel shell = new HorizontalJPanel();
		for(JComponent a : comp)
		shell.add(a); 
		parts.add(shell);
		updateParts();
	}

	private void updateParts() {
		entireView.removeAll();
		for (JComponent jp : parts) {
			entireView.add(jp);
		}
	}
}
