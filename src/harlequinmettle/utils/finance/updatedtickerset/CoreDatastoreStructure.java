package harlequinmettle.utils.finance.updatedtickerset;

import harlequinmettle.investmentadviserengine.util.RandomTools;

import java.io.Serializable;

public class CoreDatastoreStructure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7039901530290477492L;
	// note in case of changeing singleton helps differentiate instances in
	// datastoreviewer when debugging
	static CoreDatastoreStructure singleton;

	private CoreDatastoreStructure() {

	}

	public static CoreDatastoreStructure getDatastoreCoreSingleton() {
		if (singleton == null)
			singleton = new CoreDatastoreStructure();
		return singleton;
	}

	public DatastoreTool getDatastoreTool(String entityName) {
		return new DatastoreTool(entityName);
	}

	public final String ENTITY_APP_LOG = "ApplicationLog";

	private final String ENTITY_BLOB_FINANCIAL_DATABASE = "BlobFinancialDatabase";
	private final String ENTITY_BLOB_TECHNICAL_DATABASE = "BlobTechnicalDatabase";
	// ------------------------------------------------------------------------------------------------------------
	private final String ENTITY_TICKER_QUEUES = "TickerQueues";
	private final String ENTITY_IP_ADDRESS_TRACKER = "IPTracks";
	// ------------------------------------------------------------------------------------------------------------
	private final String ENTITY_NEW_EDGAR_FILINGS_FOR_EMAIL = "NewEdgarFilingsForEmail";
	private final String ENTITY_NEW_BUY_SIMULATION_UPDATE_FOR_EMAIL = "BuySimulationEmailConfirmationUpdate";
	private final String ENTITY_RECENT_EDGAR_FILINGS = "CurrentEdgarFilingEdgarArchivesURLs";
	private final String ENTITY_GLOBAL_APP_SETTINGS = "GlobalAppSettings";
	private final String ENTITY_GLOBAL_APP_STATS = "GlobalAppStats";
	private final String ENTITY_LAST_DAY_UPDATE_TRACKER = "LastDayUpdateTracker";
	private final String ENTITY_HTML_PAGE_ARCHIVE = "HtmlResultPagesArchive";
	private final String ENTITY_NEW_EDGAR_FILINGS = "NewEdgarFilings";
	private final String ENTITY_NEW_EDGAR_FILINGS_FOR_AJAX = "NewEdgarFilingsForAjax";
	private final String ENTITY_VOLUME_SPIKES_FOR_AJAX = "VolumeSpikesForAjax";
	private final String ENTITY_HIGH_VOLUME_SPIKE_KEY = "HighVolumeSpikes";
	private final String ENTITY_DAILY_IPO_TICKERS_ADDED_FILER = "DailyRecordIPOTickersAdded";
	private final String ENTITY_DAILY_VOLUME_SPIKE_FILER = "DailyRecordHighVolumeSpikes";
	private final String ENTITY_DAILY_NEW_EDGAR_FILER = "DailyRecordNewEdgarFilings";
	// ------------------------------------------------------------------------------------------------------------
	public final String ENTITY_BUY_LOG_AVAILABLE_STATE_INFO = "BuyStockSimulationStatInfoAvailable";
	public final String ENTITY_BUY_LOG = "BuyStockSimulationTracker";
	private final String ENTITY_BACKUP_DATA = "DataBackup";
	private final String ENTITY_NASDAQ_NYSE_CORE_DATA_ARCHIVES = "CoreDataTickersDailyArchives";
	// ------------------------------------------------------------------------------------------------------------
	private final String ENTITY_EDGAR_JAVASCRIPT = ("PerTickerJavascriptJQueryCanvasDrawCode");
	private final String ENTITY_RUNDOWN_TABLE_OUTLINES = ("PerTickerTableOutlines");
	private final String ENTITY_ANNUAL_INCOME = ("PerTickerIncomeTablesColoredAnnual");
	private final String ENTITY_QUARTERLY_INCOME = ("PerTickerIncomeTablesColoredQuarterly");
	private final String ENTITY_SUMMARIES = ("PerTickerGeneralInfoTableSummaries");
	private final String ENTITY_PROFILES = ("PerTickerCompanyProfileDescriptions");
	private final String ENTITY_EDGAR_TABLE_WITH_CANVAS = ("PerTickerBuildAsYouGoCustomExtractedEdgarTables");

	// ------------------------------------------------------------------------------------------------------------
	public DatastoreTool getBundleTheoryTesterForMonthAndYear(String monthAndYear) {
		return new DatastoreTool(ENTITY_BUNDLE_METRIC_THEORY_TESTS + "(" + monthAndYear + ")");
	}

	private final String ENTITY_BUNDLE_METRIC_THEORY_TESTS = "ZBundleMetricsBuyTests_";
	// ------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------
	public final String PROPERTY_BLOB_OB = "object bytes";
	public final String PROPERTY_INFO = "log info";
	public final String PROPERTY_STACK_TRACE = "stack trace";
	public final String PROPERTY_POST_TIME = "posted time";
	public final String PROPERTY = "Value";
	// ------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------
	public final String OBJ_DB_TICKERS_NEW_TICKERS_FOR_BUY_TASK = "obj_db_tickers_new_tickers_for_buy_task";
	public final String OBJ_DB_LAST_KNOWN_PRICE = "obj_db_last_known_prices";
	public final String OBJ_DB_TRADE_HISTORY = "obj_db_buy_trade_history";
	public final String OBJ_DB_TICKERS_MARKET_CAPTIALS_KEY = "obj_db_tickers_market_capitals";
	public final String OBJ_DB_TICKERS_RECENT_PRICES_KEY = "obj_db_tickers_recent_prices";
	public final String OBJ_DB_TICKERS_IPO_YEARS_KEY = "obj_db_tickers_ipo_years";
	public final String OBJ_DB_TICKERS_INDEX_IN_KEY = "obj_db_tickers_index_in";
	public final String OBJ_DB_TICKERS_INDUSTRIES_KEY = "obj_db_tickers_industries";
	public final String OBJ_DB_TICKERS_NAMES_KEY = "obj_db_tickers_names";
	public final String OBJ_DB_TICKERS_SECTORS_KEY = "obj_db_tickers_sectors";
	public final String OBJ_DB_TICKERS_TICKERS_KEY = "obj_db_tickers_tickers";
	public final String OBJ_DB_DIVIDENDS_KEY = "obj_db_dividends";
	public final String OBJ_DB_EARNINGS_KEY = "obj_db_earnings";
	public final String OBJ_DB_EDGAR_FILINGS_KEY = "obj_db_edgar_filings";
	public final String OBJ_DB_EDGAR_FILING_TYPES_KEY = "obj_db_edgar_filing_types";
	public final String OBJ_DB_EDGAR_EXPECTED = "obj_db_edgar_expected";
	public final String OBJ_DB_EXPECTED_DIVIDEND_PRIORITIES_STATEMENTS = "obj_db_expected_dividend_priorities_statement";
	public final String OBJ_DB_NEW_DIVIDENDS = "obj_db_new_dividends";
	// ------------------------------------------------------------------------------------------------------------
	public final String OBJ_VOL_DB_INTRADAY_MAX_VOLUME = "obj_vol_db_intraday_todays_maximum_volume";
	public final String OBJ_VOL_DB_INTRADAY_RELATIVE_VOLUME = "obj_vol_db_intraday_relative_volume";
	public final String OBJ_VOL_DB_INTRADAY_PRICE_TIME_COORELATION = "obj_vol_db_intraday_momentary_price_time_coorelation";
	public final String OBJ_VOL_DB_INTRADAY_PRICE_TRENDS = "obj_vol_db_intraday_price_velocity_trend";
	// public final String OBJ_VOL_DB_INTRADAY_PERCENT_CHANGES =
	// "obj_vol_db_intraday_todays_percent_change";
	public final String OBJ_VOL_DB_INTRADAY_PRICE_CHANGES_SINCE_YESTERDAY = "obj_vol_db_intraday_price_change_since_yesterday";
	public final String OBJ_VOL_DB_INTRADAY_PRICE_CHANGES_SINCE_OPEN = "obj_vol_db_intraday_price_change_since_open";

	public final String OBJ_VOL_DB_MULTIDAY_YESTERDAYS_PRICES = "obj_vol_db_multiday_yesterdays_prices";
	public final String OBJ_VOL_DB_MULTIDAY_SOME_DAY_AVGS = "obj_vol_db_multiday_some_day_averages";
	public final String OBJ_VOL_DB_MULTIDAY_R_PRICES_DAYS_3 = "obj_vol_db_multiday_r_prices_days_short";
	public final String OBJ_VOL_DB_MULTIDAY_R_PRICES_DAYS_10 = "obj_vol_db_multiday_r_prices_days_2_weeks";
	public final String OBJ_VOL_DB_MULTIDAY_R_VOL_DAYS_10 = "obj_vol_db_multiday_r_vol_days_10";
	public final String OBJ_VOL_DB_MULTIDAY_R_VOL_ACCUMULATED = "obj_vol_db_multiday_r_vol_accumlated";
	public final String OBJ_VOL_DB_INTRADAY_TIME_OF_ACCESS = "obj_vol_db_intraday_time_of_access";
	// ------------------------------------------------------------------------------------------------------------
	public final String TICKER_QUEUE_INTRADAY_TECHNICAL_DATA_UPDATE = "ticker_queue_intraday_technical_data_update";
	public final String TICKER_QUEUE_MULTIDAY_TECHNICAL_DATA_UPDATE = "ticker_queue_multiday_technical_data_update";
	public final String TICKER_QUEUE_VOLUME_SCAN = "ticker_queue_volume_scan";
	public final String TICKER_QUEUE_TABLES_STORE = "ticker_queue_table_store";
	public final String TICKER_QUEUE_EDGAR_SCAN = "ticker_queue_edgar_scan";
	// ------------------------------------------------------------------------------------------------------------

	public final String BACKUP_TICKER_RAW_CSV = "backup_raw_csv_ticker_download";
	// ------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------
	// ------------------------------------------------------------------------------------------------------------
	public final DatastoreTool DATASTORE_IP_ADDRESS_TRACKER = new DatastoreTool(ENTITY_IP_ADDRESS_TRACKER);
	public final DatastoreTool DATASTORE_TICKER_QUEUES = new DatastoreTool(ENTITY_TICKER_QUEUES);
	public final DatastoreTool DATASTORE_LAST_DAY_UPDATE_TRACKER = new DatastoreTool(ENTITY_LAST_DAY_UPDATE_TRACKER);
	public final DatastoreTool DATASTORE_GLOBAL_APP_STATS = new DatastoreTool(ENTITY_GLOBAL_APP_STATS);
	public final DatastoreTool DATASTORE_GLOBAL_APP_SETTINGS = new DatastoreTool(ENTITY_GLOBAL_APP_SETTINGS);
	public final DatastoreTool DATASTORE_MOST_RECENT_EDGAR_FILINGS = new DatastoreTool(ENTITY_RECENT_EDGAR_FILINGS);
	// ------------------------------------------------------------------------------------------------------------
	public final DatastoreTool DATASTORE_BLOB_DB_FINANCIAL_DATABASE = new DatastoreTool(ENTITY_BLOB_FINANCIAL_DATABASE);
	public final DatastoreTool DATASTORE_BLOB_DB_TECHNICAL = new DatastoreTool(ENTITY_BLOB_TECHNICAL_DATABASE);
	// ------------------------------------------------------------------------------------------------------------
	public final DatastoreTool DATASTORE_BACKUP_DATA = new DatastoreTool(ENTITY_BACKUP_DATA);
	public final DatastoreTool DATASTORE_HTML_PAGES = new DatastoreTool(ENTITY_HTML_PAGE_ARCHIVE);

	public final DatastoreTool DATASTORE_NEW_EDGAR_FILINGS = new DatastoreTool(ENTITY_NEW_EDGAR_FILINGS);
	public final DatastoreTool DATASTORE_VOLUME_SPIKE = new DatastoreTool(ENTITY_HIGH_VOLUME_SPIKE_KEY);
	public final DatastoreTool DATASTORE_BUY_LOG = new DatastoreTool(ENTITY_BUY_LOG);
	public final DatastoreTool DATASTORE_BUY_LOG_AVAILABLE_STATE_INFO = new DatastoreTool(ENTITY_BUY_LOG_AVAILABLE_STATE_INFO);

	public final DatastoreTool DATASTORE_EDGAR_JAVASCRIPT = new DatastoreTool(ENTITY_EDGAR_JAVASCRIPT);
	public final DatastoreTool DATASTORE_EDGAR_TABLE_WITH_CANVAS = new DatastoreTool(ENTITY_EDGAR_TABLE_WITH_CANVAS);

	public final DatastoreTool DATASTORE_DAILY_VOLUME_SPIKE_FILER = new DatastoreTool(ENTITY_DAILY_VOLUME_SPIKE_FILER);
	public final DatastoreTool DATASTORE_DAILY_NEW_EDGAR_FILER = new DatastoreTool(ENTITY_DAILY_NEW_EDGAR_FILER);
	public final DatastoreTool DATASTORE_DAILY_IPO_TICKERS_ADDED_FILER = new DatastoreTool(ENTITY_DAILY_IPO_TICKERS_ADDED_FILER);

	public final DatastoreTool DATASTORE_NEW_BUY_SIMULATION_UPDATE_FOR_EMAIL = new DatastoreTool(ENTITY_NEW_BUY_SIMULATION_UPDATE_FOR_EMAIL);
	public final DatastoreTool DATASTORE_NEW_EDGAR_FILINGS_FOR_EMAIL = new DatastoreTool(ENTITY_NEW_EDGAR_FILINGS_FOR_EMAIL);
	public final DatastoreTool DATASTORE_NEW_EDGAR_FILINGS_FOR_AJAX = new DatastoreTool(ENTITY_NEW_EDGAR_FILINGS_FOR_AJAX);
	public final DatastoreTool DATASTORE_VOLUME_SPIKES_FOR_AJAX = new DatastoreTool(ENTITY_VOLUME_SPIKES_FOR_AJAX);

	public final DatastoreTool DATASTORE_CORE_DATA_TICKERS_ARCHIVES = new DatastoreTool(ENTITY_NASDAQ_NYSE_CORE_DATA_ARCHIVES);

	public final DatastoreTool DATASTORE_RUNDOWN_TABLE_OUTLINES = new DatastoreTool(ENTITY_RUNDOWN_TABLE_OUTLINES);
	public final DatastoreTool DATASTORE_ANNUAL_INCOME = new DatastoreTool(ENTITY_ANNUAL_INCOME);
	public final DatastoreTool DATASTORE_QUARTERLY_INCOME = new DatastoreTool(ENTITY_QUARTERLY_INCOME);
	public final DatastoreTool DATASTORE_SUMMARIES = new DatastoreTool(ENTITY_SUMMARIES);
	public final DatastoreTool DATASTORE_PROFILES = new DatastoreTool(ENTITY_PROFILES);

	public final String APP_STATS_INSTANCES_KEY = "appinstances";

	public final String APP_SETTINGS_OBJECT_KEY = "appsettingskey";
	public final String APP_STATE_LOGGING_PREFERENCES_OBJECT_KEY = "appstateloggingpreferenceskey";
	public final String APP_STATE_EDGAR_LOG_TIME_KEY = "edgarmemcachelogtimekey";
	public final String APP_STATE_INITAIL_EDGAR_SCAN_KEY = "initialedgarscankey";
	public final String APP_STATE_LAST_EDGAR_EXPECTED_DAY = "lastdayedgarexpectedran";
	public final String APP_STATE_LAST_RESULTS_FILED_DAY = "lastdayresultswerefiled";
	public final String APP_STATE_LAST_TECHNICALS_DATA_UPDATE_DAY = "lastdaytechnicalswereupdated";
	public final String APP_STATE_LAST_TICKER_BASE_UPDATE_DAY = "lastdaytickerbasewasupdated";
	public final String APP_STATE_LAST_DIVIDEND_UPDATE_DAY = "lastdaydividendsdatawereupdated";
	public final String APP_STATE_LAST_EARNINGS_UPDATE_DAY = "lastdayearningsdatawereupdated";
	public final String APP_STATE_LAST_LOGS_CLEARED_DAY = "lastdaylogswerecleared";
	public final String APP_STATE_LAST_STATS_RESET_DAY = "lastdayapplicationstatisticswerereset";

	public final String APP_STATE_EDGAR_EMAIL_UPDATE_TIME_KEY = "lastedgaremailupdatetimemssinceorigin";

	public final int EDGAR_EMAIL_UPDATE_TIME_INTERVAL_MINUTES = 120;

	public final String APP_STATE_APPLICATION_BEHAVIOUR_STATUS = "lastknownstatusofapplicationbehaviour";
	public final String APP_STATE_APPLICATION_BEHAVIOUR_STATUS_STARTED = "applicationbehaviourstarted";
	public final String APP_STATE_APPLICATION_BEHAVIOUR_STATUS_FINISHED = "applicationbehaviourfinished";

	public static final String ID_ID = "CRDS-";

	// public final String APP_ID =
	// ModulesServiceFactory.getModulesService().getCurrentModule();
	// public final String APP_VERSION =
	// ModulesServiceFactory.getModulesService().getDefaultVersion(APP_ID);

	public static final String INSTANCE_ID = "" + (int) (Math.random() * 100000) * 100;
	public static final String CORE_CLASS_RANDOM_WORD = new RandomTools().getAnUnusualRandomWord();
	public static final String CORE_CLASS_ID = ID_ID + CORE_CLASS_RANDOM_WORD
	// WikipediaWords.getWikipediaWordsSingleton().getInstanceNameFromWikipediaWords()
			+ "-" + (System.currentTimeMillis());

	static {
		try {
		} catch (Exception e) {
		}
	}

}
