package harlequinmettle.investmentadviserengine.util;

import java.util.ArrayList;

//May 27, 2015  7:56:11 AM 
public class PercentageChangeListBuilder {

	// May 27, 2015 7:56:29 AM
	public ArrayList<Float> buildPercentChangeList(ArrayList<Float> limitedTimeValues) {
		ArrayList<Float> percents = new ArrayList<Float>();
		float lastPrice = Float.NaN;
		boolean first = true;
		for (float price : limitedTimeValues) {
			if (first) {
				first = false;
				lastPrice = price;
				continue;
			}
			if (price != price)
				continue;
			float change = NumberTool.calculatePercentChange(lastPrice, price);
			percents.add(change);
		}
		return percents;

	}
	// System.out.println("asdfsd"+"asdfasdf");
}
