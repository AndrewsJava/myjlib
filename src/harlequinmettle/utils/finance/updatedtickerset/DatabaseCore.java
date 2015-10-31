package harlequinmettle.utils.finance.updatedtickerset;

import harlequinmettle.investmentadviserengine.util.NetworkDownloadTool;
import harlequinmettle.investmentadviserengine.util.NumberTool;
import harlequinmettle.investmentadviserengine.util.StringTool;
import harlequinmettle.investmentadviserengine.util.TimeRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class DatabaseCore implements Serializable {
	private static final String SYMBOL_KEY = "symbol";
	private static final String NAME_KEY = "name";
	private static final String LASTSALE_KEY = "lastsale";
	private static final String MARKETCAP_KEY = "marketcap";
	private static final String IPOYEAR_KEY = "ipoyear";
	private static final String SECTOR_KEY = "sector";
	private static final String INDUSTRY_KEY = "industry";

	private static final String[] TICKERDATABASEKEYS = {//
	SYMBOL_KEY,//
			NAME_KEY,//
			LASTSALE_KEY,//
			MARKETCAP_KEY,//
			IPOYEAR_KEY,//
			SECTOR_KEY,//
			INDUSTRY_KEY,//
	};
	public static final TreeMap<String, TreeMap<Integer, Integer>> BEST_FIT = new TreeMap<String, TreeMap<Integer, Integer>>();

	public ConcurrentLinkedQueue<Integer> discontinued;
	private ConcurrentLinkedQueue<Integer> tickersAddedHash;

	private static final ConcurrentSkipListSet<String> tickersAdded = new ConcurrentSkipListSet<String>();
	private static ConcurrentSkipListSet<Integer> currentTickerSet = new ConcurrentSkipListSet<Integer>();

	public static final String NYSE = "nyse";
	public static final String NASDAQ = "nasdaq";
	public static final String[] INDICIES = { NYSE, NASDAQ };

	private static final String URLSTART = "http://www.nasdaq.com/screening/companies-by-name.aspx?letter=0&exchange=";
	private static final String URLEND = "&render=download";

	private static final long serialVersionUID = -4918073118972210082L;

	public static final int TODAY = (int) TimeRecord.dayNumber();

	public static int tickerSetSize = -1;

	// ticker index map
	public ConcurrentSkipListMap<String, Boolean> existsInNasdaq = new ConcurrentSkipListMap<String, Boolean>();
	// ticker symbol hashcode to readable string map
	public ConcurrentSkipListMap<Integer, String> tickers = new ConcurrentSkipListMap<Integer, String>();
	// full name hashcode to readable string map
	public ConcurrentSkipListMap<Integer, String> names = new ConcurrentSkipListMap<Integer, String>();
	// sector hashcode to readable string map
	public ConcurrentSkipListMap<Integer, String> sectors = new ConcurrentSkipListMap<Integer, String>();
	// industry hashcode to readable string map
	public ConcurrentSkipListMap<Integer, String> industries = new ConcurrentSkipListMap<Integer, String>();

	// ticker hashcode to market captial map
	public ConcurrentSkipListMap<Integer, Float> marketCapitals = new ConcurrentSkipListMap<Integer, Float>();
	// ticker hashcode to last price map
	public ConcurrentSkipListMap<Integer, Float> recentPrices = new ConcurrentSkipListMap<Integer, Float>();
	// ticker hashcode to ipo year map
	public ConcurrentSkipListMap<Integer, Short> ipoYears = new ConcurrentSkipListMap<Integer, Short>();

	private static DatabaseCore databaseCoreSingletonReference;

	private boolean initialScan = false;

	public ConcurrentLinkedQueue<Integer> getaCurrentTickerSet() {
		if (currentTickerSet.isEmpty())
			DatabaseCore.getDataCoreSingleton();
		return new ConcurrentLinkedQueue<Integer>(currentTickerSet);
	}

	private static final ArrayList<String> TICKER_SKIP = new ArrayList<String>(Arrays.asList(new String[] {//
			"GNI",//
					"UNS",//
					"HSH"//

			}));

	public static DatabaseCore getDataCoreSingleton() {
		if (databaseCoreSingletonReference == null)
			databaseCoreSingletonReference = new DatabaseCore();
		return databaseCoreSingletonReference;
	}

	private DatabaseCore() {
		init();

	}

	public void init() {
		restoreData();
		if (initialScan)
			updateDatabase();
		else
			currentTickerSet = new ConcurrentSkipListSet<Integer>(tickers.keySet());
		tickerSetSize = currentTickerSet.size();
	}

	public void updateDatabase() {
		ConcurrentSkipListSet<Integer> existingTickers = new ConcurrentSkipListSet<Integer>(tickers.keySet());

		for (String index : INDICIES) {

			ArrayList<String> downloadedLines = NetworkDownloadTool.getNetworkDownloadToolSingletonReference().getLines(URLSTART + index + URLEND);

			if (downloadedLines == null) {

				// databaseCoreSingletonReference = null;
				return;
			}

			if (downloadedLines.size() < 2)
				continue;

			String[] headings = downloadedLines.get(0).toLowerCase().replaceAll("[^A-Za-z,]", "").split(",");
			calculateBestMatch(headings);
			boolean first = true;

			for (String line : downloadedLines) {
				if (first) {
					first = false;

					continue;
				}

				String[] data = line.split("\",\"");

				String symbol = data[BEST_FIT.get(SYMBOL_KEY).firstEntry().getValue()].replaceAll("\"", "");// Symbol0
				if (TICKER_SKIP.contains(symbol))
					continue;
				if (symbol.matches(".*[^A-Z0-9]+.*"))
					continue;

				String name = data[BEST_FIT.get(NAME_KEY).firstEntry().getValue()];// Name1
				String price = data[BEST_FIT.get(LASTSALE_KEY).firstEntry().getValue()];// LastSale2
				String mcap = data[BEST_FIT.get(MARKETCAP_KEY).firstEntry().getValue()];// MarketCap3
																						// (ADR_TSO4)
				String ipo = data[BEST_FIT.get(IPOYEAR_KEY).firstEntry().getValue()];// IPOyear5
				String sctr = data[BEST_FIT.get(SECTOR_KEY).firstEntry().getValue()];// Sector6
				String ind = data[BEST_FIT.get(INDUSTRY_KEY).firstEntry().getValue()];// industry7
																						// (Summary_Quote8)

				float marketCap = NumberTool.stringNumberWithMBKtoFloat(mcap);
				float lastPrice = readFloatNumber(price, Float.NaN);
				short ipoyear = (short) readFloatNumber(ipo, -1);

				int tickerHashCode = symbol.hashCode();

				tickers.put(tickerHashCode, symbol);
				marketCapitals.put(tickerHashCode, marketCap);
				recentPrices.put(tickerHashCode, lastPrice);
				ipoYears.put(tickerHashCode, ipoyear);
				names.put(tickerHashCode, name);

				String industry = ind.trim();
				industries.put(tickerHashCode, industry);

				String sector = sctr.trim();
				sectors.put(tickerHashCode, sector);

				currentTickerSet.add(symbol.hashCode());

				existsInNasdaq.put(symbol, index.equals(NASDAQ));
			}

		}
		if (currentTickerSet.size() < 4000) {
			currentTickerSet = existingTickers;

		}// compare old and new
		discontinued = new ConcurrentLinkedQueue<Integer>(existingTickers);
		discontinued.removeAll(currentTickerSet);

		serializeCoreTickerDatabaseObjects();

		if (initialScan)
			return;

		tickersAddedHash = new ConcurrentLinkedQueue<Integer>(currentTickerSet);
		tickersAddedHash.removeAll(existingTickers);

		for (int hash : tickersAddedHash)
			tickersAdded.add(tickers.get(hash));
	}

	private void calculateBestMatch(String[] headings) {
		for (int j = 0; j < TICKERDATABASEKEYS.length; j++) {
			TreeMap<Integer, Integer> bestfit = new TreeMap<Integer, Integer>();
			BEST_FIT.put(TICKERDATABASEKEYS[j], bestfit);
			for (int i = 0; i < headings.length; i++) {
				String key = TICKERDATABASEKEYS[j];
				String csvHeading = headings[i];
				double similarityFactor = calculateSimilarity(key, csvHeading);
				bestfit.put((int) (-100 * similarityFactor), i);
			}
		}
	}

	private double calculateSimilarity(String key, String csvHeading) {
		double similarity = 1;
		key = key.toLowerCase().replaceAll("[^a-z]", "");
		csvHeading = csvHeading.toLowerCase().replaceAll("[^a-z]", "");

		if (key.equals(csvHeading))
			return similarity;

		float shortest = key.length() < csvHeading.length() ? key.length() : csvHeading.length();
		if (shortest >= 3)
			if (key.contains(csvHeading) || csvHeading.contains(key))
				return similarity;

		similarity = StringTool.similarityOfStrings(csvHeading, key);
		return similarity;
	}

	public static ConcurrentSkipListSet<String> getaTickersAdded() {
		return new ConcurrentSkipListSet<String>(tickersAdded);
	}

	private void restoreData() {
		CoreDatastoreStructure CORE = CoreDatastoreStructure.getDatastoreCoreSingleton();
		DatastoreTool source = CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE;
		existsInNasdaq = source.deserialize(existsInNasdaq.getClass(), CORE.OBJ_DB_TICKERS_INDEX_IN_KEY);

		tickers = source.deserialize(tickers.getClass(), CORE.OBJ_DB_TICKERS_TICKERS_KEY);
		names = source.deserialize(names.getClass(), CORE.OBJ_DB_TICKERS_NAMES_KEY);
		sectors = source.deserialize(sectors.getClass(), CORE.OBJ_DB_TICKERS_SECTORS_KEY);
		industries = source.deserialize(industries.getClass(), CORE.OBJ_DB_TICKERS_INDUSTRIES_KEY);

		marketCapitals = source.deserialize(marketCapitals.getClass(), CORE.OBJ_DB_TICKERS_MARKET_CAPTIALS_KEY);
		recentPrices = source.deserialize(recentPrices.getClass(), CORE.OBJ_DB_TICKERS_RECENT_PRICES_KEY);
		ipoYears = source.deserialize(ipoYears.getClass(), CORE.OBJ_DB_TICKERS_IPO_YEARS_KEY);

		if (tickers != null)
			for (String t : TICKER_SKIP)
				if (tickers.containsKey(t.hashCode()))
					tickers.remove(t.hashCode());
		ensureNoneAreNull();
	}

	private void ensureNoneAreNull() {
		if (marketCapitals == null || recentPrices == null || ipoYears == null || tickers == null || (names == null) || (sectors == null)
				|| (industries == null) || (existsInNasdaq == null))
			reinitialize();

	}

	private void reinitialize() {

		initialScan = true;
		tickers = new ConcurrentSkipListMap<Integer, String>();
		names = new ConcurrentSkipListMap<Integer, String>();
		sectors = new ConcurrentSkipListMap<Integer, String>();
		industries = new ConcurrentSkipListMap<Integer, String>();
		existsInNasdaq = new ConcurrentSkipListMap<String, Boolean>();

		marketCapitals = new ConcurrentSkipListMap<Integer, Float>();
		recentPrices = new ConcurrentSkipListMap<Integer, Float>();
		ipoYears = new ConcurrentSkipListMap<Integer, Short>();

	}

	public void removeTickerFromCoreTickerDatabaseObjects(int tickerHash) {
		currentTickerSet.remove(tickerHash);
		tickers.remove(tickerHash);
		names.remove(tickerHash);
		sectors.remove(tickerHash);
		industries.remove(tickerHash);
		marketCapitals.remove(tickerHash);
		recentPrices.remove(tickerHash);
		ipoYears.remove(tickerHash);
		serializeCoreTickerDatabaseObjects();
	}

	private void serializeCoreTickerDatabaseObjects() {

		CoreDatastoreStructure CORE = CoreDatastoreStructure.getDatastoreCoreSingleton();
		boolean allSuccess = CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE.serialize(tickers, CORE.OBJ_DB_TICKERS_TICKERS_KEY)
				&& CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE.serialize(names, CORE.OBJ_DB_TICKERS_NAMES_KEY)
				&& CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE.serialize(sectors, CORE.OBJ_DB_TICKERS_SECTORS_KEY)
				&& CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE.serialize(industries, CORE.OBJ_DB_TICKERS_INDUSTRIES_KEY)
				&& CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE.serialize(existsInNasdaq, CORE.OBJ_DB_TICKERS_INDEX_IN_KEY)
				&& CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE.serialize(marketCapitals, CORE.OBJ_DB_TICKERS_MARKET_CAPTIALS_KEY)
				&& CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE.serialize(recentPrices, CORE.OBJ_DB_TICKERS_RECENT_PRICES_KEY)
				&& CORE.DATASTORE_BLOB_DB_FINANCIAL_DATABASE.serialize(ipoYears, CORE.OBJ_DB_TICKERS_IPO_YEARS_KEY);

		if (!allSuccess) {
			tickers.clear();
			names.clear();
			sectors.clear();
			industries.clear();
			existsInNasdaq.clear();
			marketCapitals.clear();
			recentPrices.clear();
			ipoYears.clear();

			initialScan = true;
			updateDatabase();
		}
	}

	private float readFloatNumber(String number, float defaultValue) {
		float val = defaultValue;
		try {
			val = Float.parseFloat(number.trim());
		} catch (Exception e) {
		}
		return val;
	}

	public int currentTickerSetSize() {
		return currentTickerSet.size();
	}

	public int tickersAddedSize() {
		return tickersAdded.size();
	}

}