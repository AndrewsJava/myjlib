package harlequinmettle.utils.guitools;

import java.awt.BasicStroke;

public class SmoothStroke extends BasicStroke {
	public SmoothStroke(int thickness){
		super(thickness,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	}
	public SmoothStroke(){
		super(3,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	}
}
