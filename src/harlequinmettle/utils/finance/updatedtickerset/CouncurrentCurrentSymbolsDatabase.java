package harlequinmettle.utils.finance.updatedtickerset;

import harlequinmettle.utils.filetools.SerializationMechanismInterface;
import harlequinmettle.utils.filetools.SerializationTool;
import harlequinmettle.utils.nettools.NetPuller;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class CouncurrentCurrentSymbolsDatabase implements Serializable {

	public static final String INSTANCE_ID = "" + (int) (Math.random() * 100000) * 1000;
	public ConcurrentLinkedQueue<Integer> discontinued;
	private ConcurrentLinkedQueue<Integer> tickersAddedHash;

	private static final ConcurrentSkipListSet<String> tickersAdded = new ConcurrentSkipListSet<String>();
	private static ConcurrentSkipListSet<Integer> currentTickerSet = new ConcurrentSkipListSet<Integer>();

	private static final String TICKERS_KEY = "tickers_serialization_key";
	private static final String NAMES_KEY = "names_serialization_key";
	private static final String SECTORS_KEY = "sectors_serialization_key";
	private static final String INDUSTRIES_KEY = "industries_serialization_key";
	private static final String TICKERS_DATABASE_KEY = "tickers_database_serialization_key";

	public static final String NYSE = "nyse";
	public static final String NASDAQ = "nasdaq";
	public static final String[] INDICIES = { NYSE, NASDAQ };

	public static final String ANNUAL = "annual";
	public static final String QUARTERLY = "quarterly";
	public static final String[] REPORT_PERIODS = { ANNUAL, QUARTERLY };

	private static final String URLSTART = "http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=";
	private static final String URLEND = "&render=download";

	private static final long serialVersionUID = -4918073118972210082L;
	public static final SerializationTool SERIALIZER;
	static {
		SERIALIZER = new SerializationTool();
	}
	// ticker symbol hashcode to readable string map
	public static ConcurrentSkipListMap<Integer, String> tickers = new ConcurrentSkipListMap<Integer, String>();
	// full name hashcode to readable string map
	public static ConcurrentSkipListMap<Integer, String> names = new ConcurrentSkipListMap<Integer, String>();
	// sector hashcode to readable string map
	public static ConcurrentSkipListMap<Integer, String> sectors = new ConcurrentSkipListMap<Integer, String>();
	// industry hashcode to readable string map
	public static ConcurrentSkipListMap<Integer, String> industries = new ConcurrentSkipListMap<Integer, String>();
	//
	public static ConcurrentSkipListMap<Integer, TickerSymbol> tickerSymbolDatabase = new ConcurrentSkipListMap<Integer, TickerSymbol>();

	private boolean initialScan = false;

	public static ConcurrentLinkedQueue<Integer> getaCurrentTickerSet() {
		return new ConcurrentLinkedQueue<Integer>(currentTickerSet);
	}

	public CouncurrentCurrentSymbolsDatabase() {

		restoreData(SERIALIZER);
		if (initialScan)
			updateDatabase();
		else
			currentTickerSet = new ConcurrentSkipListSet<Integer>(tickerSymbolDatabase.keySet());
	}

	public void updateDatabase() {
		ConcurrentSkipListSet<Integer> existingTickers = new ConcurrentSkipListSet<Integer>(tickerSymbolDatabase.keySet());

		for (String index : INDICIES) {
			String csvData = NetPuller.getHtml2(URLSTART + index + URLEND).replaceAll(",", "").replaceAll("\" \"", "\"\"");

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
		if (currentTickerSet.size() < 4000) {
			currentTickerSet = existingTickers;

		}// compare old and new
		discontinued = new ConcurrentLinkedQueue<Integer>(existingTickers);
		discontinued.removeAll(currentTickerSet);

		serializeAllData(SERIALIZER);

		if (initialScan)
			return;

		tickersAddedHash = new ConcurrentLinkedQueue<Integer>(currentTickerSet);
		tickersAddedHash.removeAll(existingTickers);

		for (int hash : tickersAddedHash)
			tickersAdded.add(tickers.get(hash));
		checkForNewTickers_Email();

	}

	private void checkForNewTickers_Email() {

		int newTickersAdded = tickersAddedSize();
		if (newTickersAdded > 0) {
			ConcurrentSkipListSet<String> tickersAdded = getaTickersAdded();
			collectInfoAndSendEmail("NEW_TICKERS_ADDED_(" + newTickersAdded + ")", tickersAdded);
		}
	}

	protected void collectInfoAndSendEmail(String title, ConcurrentSkipListSet<String> tickersSet) {

		// CommonNotifierBackend.collectAdditionalInformation(tickersAdded);
		// Emailer.sendDataByEmail(title, tickersSet);
	}

	private static ConcurrentSkipListSet<String> getaTickersAdded() {
		return new ConcurrentSkipListSet<String>(tickersAdded);
	}

	private void restoreData(SerializationMechanismInterface source) {
		tickers = source.deserialize(tickers.getClass(), TICKERS_KEY);
		names = source.deserialize(names.getClass(), NAMES_KEY);
		sectors = source.deserialize(sectors.getClass(), SECTORS_KEY);
		industries = source.deserialize(industries.getClass(), INDUSTRIES_KEY);
		tickerSymbolDatabase = source.deserialize(tickerSymbolDatabase.getClass(), TICKERS_DATABASE_KEY);
		ensureNoneAreNull();
	}

	private void ensureNoneAreNull() {
		if (tickers == null || (names == null) || (sectors == null) || (industries == null) || tickerSymbolDatabase == null)
			reinitialize();
	}

	private void reinitialize() {
		initialScan = true;
		tickers = new ConcurrentSkipListMap<Integer, String>();
		names = new ConcurrentSkipListMap<Integer, String>();
		sectors = new ConcurrentSkipListMap<Integer, String>();
		industries = new ConcurrentSkipListMap<Integer, String>();
		tickerSymbolDatabase = new ConcurrentSkipListMap<Integer, TickerSymbol>();

	}

	private void serializeAllData(SerializationMechanismInterface source) {
		boolean allSuccess = source.serialize(tickers, TICKERS_KEY) && source.serialize(names, NAMES_KEY)
				&& source.serialize(sectors, SECTORS_KEY) && source.serialize(industries, INDUSTRIES_KEY)
				&& source.serialize(tickerSymbolDatabase, TICKERS_DATABASE_KEY);
		if (!allSuccess) {
			removeUntrackedTickers();
			serializeAllData(source);
		}
	}

	private void removeUntrackedTickers() {
		for (Integer older : discontinued) {
			tickerSymbolDatabase.remove(older);
			names.remove(older);
			tickers.remove(older);

		}
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
			names.put(tickerHashCode, fullName);
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

	public CharSequence queryAllTickerDataRecords() {
		StringBuilder htmlResults = new StringBuilder();

		for (Entry<Integer, TickerSymbol> reportDate : tickerSymbolDatabase.entrySet()) {

			int hash = reportDate.getKey();
			String ticker = tickers.get(hash);
			addDataInfoTable(htmlResults, reportDate.getValue(), ticker);
		}
		return htmlResults.toString();
	}

	private void addDataInfoTable(StringBuilder htmlResults, TickerSymbol tickerData, String ticker) {

		float marketCap = tickerData.marketCap;
		float lastPrice = tickerData.lastPrice;
		float ipoYear = tickerData.ipoYear;

		String fullName = CouncurrentCurrentSymbolsDatabase.names.get(tickerData.tickerHashCode);
		String sector = CouncurrentCurrentSymbolsDatabase.sectors.get(tickerData.sectorHashCode);
		String industry = CouncurrentCurrentSymbolsDatabase.industries.get(tickerData.industryHashCode);

		htmlResults.append("<table border=1 style=\"float: left\"><tr><td colspan=\"2\" >");
		htmlResults.append(ticker + "    :  " + fullName);
		htmlResults.append(" </td></tr><tr><td colspan=\"2\" >");
		htmlResults.append(sector);
		htmlResults.append(" </td></tr><tr><td colspan=\"2\" >");
		htmlResults.append(industry);
		htmlResults.append(" </td></tr><tr><td >");
		htmlResults.append("market cap </td><td>" + marketCap);
		htmlResults.append(" </td></tr><tr><td   >");
		htmlResults.append("last price  </td><td>" + lastPrice);
		htmlResults.append(" </td></tr><tr><td   >");
		htmlResults.append("ipo year  </td><td>" + ipoYear);
		htmlResults.append(" </td></tr></table>");

	}

	public int currentTickerSetSize() {
		return currentTickerSet.size();
	}

	public int tickersAddedSize() {
		return tickersAdded.size();
	}
}
