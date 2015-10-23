package harlequinmettle.utils.guitools;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

//Mar 28, 2015  10:18:20 AM 
public class ScrollingViewApplicationWithButtons {

	public JFrame display = new JFrame("Application");
	PreferredJScrollPane scrollable;

	public ScrollingViewApplicationWithButtons() {
		init();
	}

	public void setTitle(String title) {
		display.setTitle(title);
	}

	public void updateApplicationMainScrollingView(final JComponent mainView) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (scrollable != null)
					display.remove(scrollable);
				scrollable = new PreferredJScrollPane(mainView, false);

				scrollable.getVerticalScrollBar().setValue(0);

				display.add(scrollable, BorderLayout.CENTER);
				display.revalidate();
				display.repaint();

			}
		});

	}

	// System.out.println("asdfsd"+"asdfasdf");

	public void addTopPanelComponents(final JComponent... comps) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HorizontalJPanel hz = new HorizontalJPanel();
				for (JComponent comp : comps)
					hz.add(comp);
				display.add(hz, BorderLayout.NORTH);
			}
		});
	}

	public void addBottomPanelComponents(final JComponent... comps) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HorizontalJPanel hz = new HorizontalJPanel();
				for (JComponent comp : comps)
					hz.add(comp);
				display.add(hz, BorderLayout.SOUTH);
			}
		});
	}

	private void init() {
		// Mar 28, 2015 10:21:28 AM

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				display.setExtendedState(display.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				display.setVisible(true);
				display.setLayout(new BorderLayout());
			}
		});

	}
}
