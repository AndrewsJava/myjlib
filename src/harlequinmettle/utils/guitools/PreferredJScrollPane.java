package harlequinmettle.utils.guitools;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class PreferredJScrollPane extends JScrollPane {
	public PreferredJScrollPane() {
		this.setPreferredSize(new Dimension(600, 300));
		this.getVerticalScrollBar().setUnitIncrement(32);
	}
public int getHorizontalOffset(){
	return  getHorizontalScrollBar().getValue();
}
	public PreferredJScrollPane(JComponent jComp) {

		/////////////////
		 super(jComp);
	        final JScrollBar horizontalScrollBar = getHorizontalScrollBar();
	        final JScrollBar verticalScrollBar = getVerticalScrollBar();
	        setWheelScrollingEnabled(false);
	        addMouseWheelListener(new MouseAdapter()
	        {
	            public void mouseWheelMoved(MouseWheelEvent evt)
	            {
	                if (evt.getWheelRotation() == 1)//mouse wheel was rotated down/ towards the user
	                {
	                    int iScrollAmount = 12;//evt.getScrollAmount();
	                    int iNewValue = horizontalScrollBar.getValue() + horizontalScrollBar.getBlockIncrement() * iScrollAmount;
	                    if (iNewValue <= horizontalScrollBar.getMaximum())
	                    {
	                        horizontalScrollBar.setValue(iNewValue);
	                    }
	                }
	                else if (evt.getWheelRotation() == -1)//mouse wheel was rotated up/away from the user
	                {
	                    int iScrollAmount = 12;//evt.getScrollAmount();
	                    int iNewValue = horizontalScrollBar.getValue() - horizontalScrollBar.getBlockIncrement() * iScrollAmount;
	                    if (iNewValue >= 0)
	                    {
	                        horizontalScrollBar.setValue(iNewValue);
	                    }
	                }
	            }
	        });
		////////////////
		this.setViewportView(jComp);
		//SETPREFERREDSIZE REQUIRED FOR SCROLLING TO WORK
		this.setPreferredSize(new Dimension(600, 300));
//		this.getHorizontalScrollBar().setUnitIncrement(32);
//		this.getVerticalScrollBar().setUnitIncrement(32);
		
		
	}

}
