package harlequinmettle.utils.guitools;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class JButtonWithEnterKeyAction extends JButton {
	public static final Font BUTTON_FONT = new Font(Font.MONOSPACED, 20, 20);
 
 
	public JButtonWithEnterKeyAction(String name) {
		super(name);

		super.registerKeyboardAction(super.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, false),
				JComponent.WHEN_FOCUSED);
		super.registerKeyboardAction(super.getActionForKeyStroke(KeyStroke
				.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke
				.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				JComponent.WHEN_FOCUSED);

		super.setFont(BUTTON_FONT);
		addDefaultHistoryRestartListener(false);
	}

	// /supplemental button actions
	private void addDefaultHistoryRestartListener(final boolean isRev) {
		addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}

		});
	}

}