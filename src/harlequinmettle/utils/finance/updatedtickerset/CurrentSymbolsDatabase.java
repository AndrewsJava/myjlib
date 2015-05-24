package harlequinmettle.utils.finance.updatedtickerset;

import harlequinmettle.utils.filetools.SerializationMechanismInterface;
import harlequinmettle.utils.nettools.NetPuller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class CurrentSymbolsDatabase implements Serializable {

	ArrayList<Integer> discontinued;
	public ArrayList<Integer> tickersAddedHash;
	public ArrayList<String> tickersAdded = new ArrayList<String>();
	ArrayList<Integer> currentTickerSet = new ArrayList<Integer>();

	private static final String TICKERS_KEY = "tickers_serialization_key";
	private static final String NAMES_KEY = "names_serialization_key";
	private static final String SECTORS_KEY = "sectors_serialization_key";
	private static final String INDUSTRIES_KEY = "industries_serialization_key";
	private static final String TICKERS_DATABASE_KEY = "tickers_database_serialization_key";

	private String nyse = "nyse";
	private String nasdaq = "nasdaq";
	private String[] indicies = { nyse, nasdaq };
	private String urlstart = "http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=";
	private String urlend = "&render=download";

	private static final long serialVersionUID = -4918073118972210082L;
	// ticker symbol hashcode to readable string map
	public static TreeMap<Integer, String> tickers = new TreeMap<Integer, String>();
	// full name hashcode to readable string map
	public static TreeMap<Integer, String> names = new TreeMap<Integer, String>();
	// sector hashcode to readable string map
	public static TreeMap<Integer, String> sectors = new TreeMap<Integer, String>();
	// industry hashcode to readable string map
	public static TreeMap<Integer, String> industries = new TreeMap<Integer, String>();
	//
	public static TreeMap<Integer, TickerSymbol> tickerSymbolDatabase = new TreeMap<Integer, TickerSymbol>();
	private boolean initialScan = false;

	public CurrentSymbolsDatabase(SerializationMechanismInterface source) {
		tickers = source.deserialize(tickers.getClass(), TICKERS_KEY);
		names = source.deserialize(names.getClass(), NAMES_KEY);
		sectors = source.deserialize(sectors.getClass(), SECTORS_KEY);
		industries = source.deserialize(industries.getClass(), INDUSTRIES_KEY);
		tickerSymbolDatabase = source.deserialize(tickerSymbolDatabase.getClass(), TICKERS_DATABASE_KEY);
		ensureNoneAreNull(source);
	}

	private void updateDatabase(SerializationMechanismInterface source) {
		ArrayList<Integer> existingTickers = new ArrayList<Integer>(tickerSymbolDatabase.keySet());
		// source.serialize(existingTickers,
		// "memory_size_test_integer_for_string");
		for (String index : indicies) {
			String csvData = NetPuller.getHtml2(urlstart + index + urlend).replaceAll(",", "").replaceAll("\" \"", "\"\"");

			String[] lines = csvData.split("\"\"");
			for (int i = 9; i < lines.length - 9; i += 9) {
				String symbol = lines[i];// Symbol0
				if (symbol.matches(".*[^A-Z0-9]+.*"))
					continue;
				String name = lines[i + 1];// Name1
				String price = lines[i + 2];// LastSale2
				String mcap = lines[i + 3];// MarketCap3 (ADR_TSO4)
				String ipo = lines[i + 5];// IPOyear5
				String sctr = lines[i + 6];// Sector6
				String ind = lines[i + 7];// industry7 (Summary_Quote8)
				TickerSymbol read = new TickerSymbol(symbol, name, price, mcap, ipo, sctr, ind);

				currentTickerSet.add(read.tickerHashCode);
				tickerSymbolDatabase.put(read.tickerHashCode, read);
			}

		}
		if (currentTickerSet.size() < 4000)
			currentTickerSet = existingTickers;

		System.out.println("\n-----00))- total symbols: " + tickers.size() + "\n");
		serializeAllData(source);
		if (initialScan)
			return;
		// compare old and new
		discontinued = new ArrayList<Integer>(existingTickers);
		discontinued.removeAll(currentTickerSet);

		tickersAddedHash = new ArrayList<Integer>(currentTickerSet);
		tickersAddedHash.removeAll(existingTickers);

		tickersAdded = new ArrayList<String>();
		for (int hash : tickersAddedHash)
			tickersAdded.add(tickers.get(hash));

		// System.out.println("\ndiscontinued ticker symbols: " +
		// discontinued.size() + "\n");
		// for (int hash : discontinued)
		// System.out.print(tickers.get(hash) + "   ");
		// System.out.println("\nticker symbols just added       : " +
		// tickersAdded.size() + "\n");
		// for (int hash : tickersAdded)
		// System.out.print(tickers.get(hash) + "   ");
		// System.out.println("\n\n\nfinal list to use       : " +
		// currentTickerSet.size() + "\n");
		// for (int hash : currentTickerSet)
		// System.out.print(tickers.get(hash) + "   ");
	}

	private void serializeAllData(SerializationMechanismInterface source) {
		source.serialize(tickers, TICKERS_KEY);
		source.serialize(names, NAMES_KEY);
		source.serialize(sectors, SECTORS_KEY);
		source.serialize(industries, INDUSTRIES_KEY);
		source.serialize(tickerSymbolDatabase, TICKERS_DATABASE_KEY);
	}

	private void ensureNoneAreNull(SerializationMechanismInterface source) {
		if (tickers == null || (names == null) || (sectors == null) || (industries == null) || tickerSymbolDatabase == null)
			reinitialize(source);
	}

	private void reinitialize(SerializationMechanismInterface source) {
		initialScan = true;
		tickers = new TreeMap<Integer, String>();
		names = new TreeMap<Integer, String>();
		sectors = new TreeMap<Integer, String>();
		industries = new TreeMap<Integer, String>();
		tickerSymbolDatabase = new TreeMap<Integer, TickerSymbol>();
		updateDatabase(source);

	}

	public class TickerSymbol implements Serializable {

		private static final long serialVersionUID = 5241697877905853722L;
		public int tickerHashCode;
		public int fullNameHashCode;
		public int sectorHashCode;
		public int industryHashCode;

		public float marketCap;
		public float lastPrice;

		public short ipoYear;
		// TOD0: MANAGE DISCONTINUED TICKERS IN DATABASE
		public boolean discontinued = false;

		TickerSymbol(String symbol, String name, String price, String mcap, String ipo, String sctr, String ind) {

			String ticker = symbol.trim();
			tickerHashCode = ticker.hashCode();

			String fullName = name.trim();
			fullNameHashCode = fullName.hashCode();

			lastPrice = readFloatNumber(price, Float.NaN);
			marketCap = readFloatNumber(mcap, Float.NaN);
			ipoYear = (short) readFloatNumber(ipo, -1);

			String sector = sctr.trim();
			sectorHashCode = sector.hashCode();

			String industry = ind.trim();
			industryHashCode = industry.hashCode();

			tickers.put(tickerHashCode, ticker);
			names.put(fullNameHashCode, fullName);
			sectors.put(sectorHashCode, sector);
			industries.put(industryHashCode, industry);
			// tickerSymbolDatabase.put(tickerHashCode, this);
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
