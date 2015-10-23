package harlequinmettle.investmentadviserengine.util;

import java.text.SimpleDateFormat;
import java.util.TreeMap;

//Feb 28, 2015  3:39:14 PM 
public class CSVTool {

	public static TreeMap<Long, Float> getMultidayNumbersFromCSVString(String csv, int csvColumn) {
		TreeMap<Long, Float> multiDayTimeDataMap = new TreeMap<Long, Float>();
		int n = 1;
		String[] data = csv.split(" ");
		// Date,Open,High,Low,Close,Volume,Adj Close
		// 2014-10-08,10.57,10.89,10.50,10.83,103800,10.83
		for (String d : data) {
			if (d.startsWith("Date"))
				continue;
			String[] dayData = d.split(",");
			if (dayData.length < 6)
				continue;

			String date = dayData[0];
			float timeMillis = TimeDateTool.tryToParseDate(date, new SimpleDateFormat(TimeDateTool.MULTIDAY_CSV_DATE_FORMAT), Float.NaN);
			if (timeMillis != timeMillis)
				continue;
			float csvValue = NumberTool.tryToParseFloat(dayData[csvColumn], Float.NaN);
			if (csvValue != csvValue)
				continue;
			multiDayTimeDataMap.put((long) timeMillis, csvValue);

			n++;

		}
		return multiDayTimeDataMap;
	}

	public static TreeMap<Long, Float> getIntradayNumbersFromCSVString_milliseconds(String csv, int csvColumn) {

		TreeMap<Long, Float> intradayTechnicalTimeDataMap = new TreeMap<Long, Float>();// values:Timestamp,close,high,low,open,volume
		String[] data = csv.split(" ");// 1408109645,576.1398,576.2000,575.7000,575.7490,4400

		for (String d : data) {

			if (d.contains("labels"))
				continue;

			String[] tick = d.split(",");

			if (tick.length < 6)
				continue;

			float t = NumberTool.tryToParseFloat(tick[0], Float.NaN);
			float p = NumberTool.tryToParseFloat(tick[csvColumn], Float.NaN);

			if (p == p && t == t) {
				intradayTechnicalTimeDataMap.put((long) t * 1000, p);

			}
		}
		return intradayTechnicalTimeDataMap;
	}

}
