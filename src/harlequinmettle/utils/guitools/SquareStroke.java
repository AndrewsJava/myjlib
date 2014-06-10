package harlequinmettle.utils.guitools;

import java.awt.BasicStroke;

public class SquareStroke extends BasicStroke {
	public SquareStroke(int thickness){
		super(thickness,
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	}
	public SquareStroke(){
		super(3,
				BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	}
}
