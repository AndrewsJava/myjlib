// Oct 30, 2015 8:42:02 AM
package harlequinmettle.utils.guitools;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class ErrorLine {
	private ArrayList<Float> errorPoints = new ArrayList<Float>();

	GeneralPath errorGraph;

	int height = 500;

	public void addErrorPoint(float error) {

		if (errorPoints.size() > 100) {
			float initialerror = errorPoints.get(0);
			float lasterror = errorPoints.get(errorPoints.size() - 1);
			errorPoints.clear();
			errorPoints.add(initialerror);
			errorPoints.add(lasterror);
		}
		errorPoints.add(error);
	}

	// Oct 23, 2015 12:09:16 PM
	void rescaleErrorLine() {
		errorGraph = new GeneralPath();
		int x = 50;
		int y = 80;
		errorGraph.moveTo(x, y);
		if (errorPoints.isEmpty())
			return;
		float initialError = errorPoints.get(0);
		float errorGraphScalingFactor = (float) (height * 1 / initialError);
		float interval = 5;
		for (float error : errorPoints) {
			x += interval;
			y = (int) (height * 0.7 + 80 - error * errorGraphScalingFactor / 20);
			errorGraph.lineTo(x, y);
		}
	}
}
