package harlequinmettle.utils.stringtools;

import harlequinmettle.utils.nettools.NetPuller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class WikipediaWords {
	static WikipediaWords wikiWordsSingletonReference;

	public static WikipediaWords getWikipediaWordsSingleton() {
		if (wikiWordsSingletonReference == null)
			wikiWordsSingletonReference = new WikipediaWords();
		return wikiWordsSingletonReference;
	}

	private WikipediaWords() {

	}

	private String wikipediaRoot = "http://en.wikipedia.org/wiki/";

	private String[] someStartingWords = {//
	"Proterozoic",//
			"Amphibian",//
			"Language",//
			"Climate",//
			"Evolution",//
			"Mythology",//

	};

	public String getInstanceNameFromWikipediaWords() {
		String topic = getTopic();
		String randomTopicWord = getWikipediaWord(getAllUniqueWords(topic, 10));
		return topic + "-" + randomTopicWord;
	}

	private String getTopic() {
		// Feb 28, 2015 9:52:49 AM
		return someStartingWords[(int) (Math.random() * (someStartingWords.length))];
	}

	public ArrayList<String> getAllUniqueWords(String topic, int length) {

		System.out.println(topic);
		String url = wikipediaRoot + topic;
		String wordOfLength = "wordoflengthnotfound";
		String results = NetPuller.getHtml2(url);
		// String results =
		// NetworkDownloadTool.getNetworkDownloadToolSingletonReference().getData(null,
		// url);

		// results = limitResults(results);
		results = removeHtml(results);
		results = removeAllNonCharacter(results);

		LinkedHashSet<String> wikiwords = new LinkedHashSet<String>(Arrays.asList(results.split(" ")));

		ArrayList<String> getFromIndex = new ArrayList();
		for (String word : wikiwords)
			if (word.length() >= length)
				if (Character.isUpperCase(word.charAt(0)))
					getFromIndex.add(word);

		return getFromIndex;
	}

	private String limitResults(String results) {
		// Feb 28, 2015 9:39:44 AM
		return results.split("<.*?id=\"References\">")[0];

	}

	public String getWikipediaWord(ArrayList<String> getFromIndex) {
		return getFromIndex.get((int) (Math.random() * getFromIndex.size()));
	}

	private String removeAllNonCharacter(String results) {
		return results.replaceAll("[^A-Za-z ]", "");
	}

	private String removeHtml(String results) {
		results = results.replaceAll("<script.*?</script>", "");
		results = results.replaceAll("<style.*?</style>", "");
		return results.replaceAll("<.*?>", "");
	}
}