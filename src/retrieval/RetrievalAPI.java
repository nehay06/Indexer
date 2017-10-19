package retrieval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import fileio.FileIO;

public class RetrievalAPI {

	public List<TopQueryResult> retrieveDocAtATime(Map<String, List<Integer>> lookuplist,
		Map<Integer, String> docIdSceneIdMap, List<String> queryTerms, String filname,boolean compressed) throws IOException {
		FileIO fileio = new FileIO();
		List<List<Integer>> postings = new ArrayList<List<Integer>>();
		List<TopQueryResult> topResults = new ArrayList<TopQueryResult>();

		//System.out.println("Reading postings for the Query Terms");
		for (String term : queryTerms) {
			List lookupTerms = lookuplist.get(term);
			Long byteOffset = (Long) lookupTerms.get(0);
			Integer length = ((Number) lookupTerms.get(1)).intValue();

			
			if(compressed) {
				postings.add(fileio.readCompressedInvertedList(filname, byteOffset, length));
			}
			else {
				postings.add(fileio.readInvertedList(filname,byteOffset,length));
			}
		}
		//System.out.println("Finished Reading postings for the Query Terms");

		for (Entry<Integer, String> docEntry : docIdSceneIdMap.entrySet()) {
			Integer docId = docEntry.getKey();
			String sceneID = docEntry.getValue();

			TopQueryResult queryResult = new TopQueryResult();
			queryResult.setDocID(docId);
			queryResult.setSceneID(sceneID);
			queryResult.setScore(0);

			for (List<Integer> posting : postings) {
				int currentDocId;
				int count;
				int counter = 0;
				while (counter < posting.size()) {
					currentDocId = posting.get(counter);
					count = posting.get(counter + 1);

					if (currentDocId == docId) {
						int score = queryResult.getScore();
						queryResult.setScore(score + count);

					}
					counter = counter + count + 2;
				}
			}
			if (topResults.size() < 5) {
				topResults.add(queryResult);
				Collections.sort(topResults, new TopQueryResult());
			} else {
				TopQueryResult lastElem = topResults.get(4);
				if (lastElem.getScore() < queryResult.getScore()) {
					topResults.remove(4);
					topResults.add(queryResult);
				}
				Collections.sort(topResults, new TopQueryResult());
			}

		}
		return topResults;
	}

}
