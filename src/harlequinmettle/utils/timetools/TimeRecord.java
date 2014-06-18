package harlequinmettle.utils.timetools;

import java.util.Date;

public class TimeRecord {


	public static String fileTitle(String nasOrNy) {
		double time = System.currentTimeMillis();
		// convert to seconds
		time /= 1000.0;
		// convert to hours
		time /= 3600.0;
		// to days
		time /= 24.0;
		// limit to one decimal place
		time = (double) ((int) (time * 10) / 10.0);

		return nasOrNy + "_" + time + ".txt";
		//
	}
	public static String fileTitleHtml(String nasOrNy) {
		double time = System.currentTimeMillis();
		// convert to seconds
		time /= 1000.0;
		// convert to hours
		time /= 3600.0;
		// to days
		time /= 24.0;
		// limit to one decimal place
		time = (double) ((int) (time * 10) / 10.0);

		return nasOrNy + "_" + time + ".html";
		//
	}

	public static float secondsSince() {
		Date d = new Date();
		float hours = d.getHours() * 1000000;
		float time = d.getMinutes() * 60;
		time += d.getSeconds();
		time += hours;
		return time;
		//
	}

	public static float daysSince() {
		float time = System.currentTimeMillis();
		// convert to seconds
		time /= 1000.0;
		// convert to hours
		time /= 3600.0;
		// to days
		time /= 24.0;
		// limit to one decimal place
		time = (float) ((int) (time * 10) / 10.0);
		return time;
		//
	}

	

	public static float dayNumber(long time) {

		// convert to seconds
		time /= 1000.0;
		// convert to hours
		time /= 3600.0;
		// to days
		time /= 24.0;
		// limit to one decimal place
		return (float) ((int) (time * 10) / 10.0);

	}

}
