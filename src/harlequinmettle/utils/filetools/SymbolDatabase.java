package harlequinmettle.utils.filetools;

import java.io.Serializable;
import java.util.TreeMap;

public class SymbolDatabase implements Serializable {
public SymbolDatabase(SerializationMechanismInterface source){
	
}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4918073118972210082L;
	// ticker symbol hashcode to readable string map
	static TreeMap<Integer, String> tickers = new TreeMap<Integer, String>();
	// sector hashcode to readable string map
	static TreeMap<Integer, String> sectors = new TreeMap<Integer, String>();
	// industry hashcode to readable string map
	static TreeMap<Integer, String> industries = new TreeMap<Integer, String>();
	//
	static TreeMap<Integer, TickerSymbol> tickerSymbolDatabase = new TreeMap<Integer, TickerSymbol>();

	class TickerSymbol implements Serializable {
		 
		private static final long serialVersionUID = 5241697877905853722L;
		// int tickerHashCode;
		int sectorHashCode;
		int industryHashCode;

		float marketCap;
		float lastPrice;
		short ipoYear;

		TickerSymbol(String csvLine) {
			String[] values = csvLine.split(",");
			// SymbolName0 LastSale1 MarketCap2 ADR_TSO3 IPOyear4 Sector5
			// industry6
			// Summary_Quote7
			String ticker = values[0].trim();
			int tickerHashCode = ticker.hashCode();
			tickers.put(tickerHashCode, ticker);
			lastPrice = readFloatNumber(values[1], Float.NaN);
			marketCap = readFloatNumber(values[2], Float.NaN);
			ipoYear = (short) readFloatNumber(values[4], -1);

			String sector = values[5].trim();
			sectorHashCode = sector.hashCode();

			String industry = values[6].trim();
			industryHashCode = industry.hashCode();
			

			tickers.put(tickerHashCode, ticker);
			sectors.put(sectorHashCode,sector);
			industries.put(industryHashCode, industry);
			tickerSymbolDatabase.put(tickerHashCode, this);
		}

		private float readFloatNumber(String number, float defaultValue) {
			float val = defaultValue;
			try {
				val = Float.parseFloat(number.trim());
			} catch (Exception e) {
			}
			return val;
		}

	}
}
