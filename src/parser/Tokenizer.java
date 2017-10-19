package parser;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import data.InputDataFormat;
import data.JSONReader;
import data.Posting;
import indexercomponents.IndexingComponents;

public class Tokenizer {

    JSONReader jsonReader;

    public Tokenizer()
    {

    }
    

    public JSONReader getJsonReader() {
        return jsonReader;
    }

    public void setJsonReader(JSONReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    public boolean checkTerm(Map invertedIndices,String term)
    {
        if (invertedIndices.containsKey(term))
        {
            return true;
        }
        return false;

    }
    
    public IndexingComponents indexerComponents(Boolean compressed){
    	
    	    List<InputDataFormat> corpus =jsonReader.readData();
        Map<String,List<Posting>> invertedIndices = new TreeMap<String,List<Posting>>();
        //Map<String,List<Posting>> invertedDeltaIndices = new TreeMap<String,List<Posting>>();
        
        Map<String,String> sceneIdPlayIdMap = new TreeMap<String,String>();
        Map<Integer,String> docIdSceneIdMap = new TreeMap<Integer,String>();
        Map<String,List<String>> playIdSceneIdMap = new TreeMap<String,List<String>>();
        Map<Integer,Integer>docIdSceneNumMap = new TreeMap<Integer,Integer>();
        Map<Integer,List<String>>docIdTextMap = new TreeMap<Integer,List<String>>();

        
       JSONObject sceneIdPlayIdJson = new JSONObject();
       JSONObject docIdSceneIdJson = new JSONObject();
       JSONObject playIdSceneIdJson =new JSONObject();
       JSONObject docIdSceneNumJson =new JSONObject();
       JSONObject docIdTextJson = new JSONObject();
       
       
     Integer maxSceneLength = Integer.MIN_VALUE;
     Integer minSceneLength = Integer.MAX_VALUE;
     Integer longestPlayLen = Integer.MIN_VALUE;
     Integer shortestPlayLen = Integer.MAX_VALUE;
     Integer averageSceneLength = 0;
     String longestPlay = "";
   
     String shortedPlay = "";
     String longestScene= "";
     String shortedScene = "";
        
       IndexingComponents idxcomp = new IndexingComponents();

 
        int counter = 0;
        for(InputDataFormat scene: corpus) {
        				List <String> wordList = scene.getWordList();
        				Integer currentLength =  wordList.size();
                     
        				String sceneId = scene.getSceneId();
        				String playId = scene.getPlayId();
        				
        				 averageSceneLength = averageSceneLength + currentLength;
                         if (maxSceneLength < currentLength){
                             maxSceneLength = currentLength;
                             longestScene = sceneId;
                         }
                         if(minSceneLength >currentLength){
                             minSceneLength = currentLength;
                             shortedScene = sceneId;
                         }
        				Integer sceneNum = scene.getSceneNum();
	                    
        				    sceneIdPlayIdMap.put(sceneId, playId);
	                    sceneIdPlayIdJson.put(sceneId, sceneId);
	                    
	                    docIdSceneIdMap.put(counter, sceneId);
	                    docIdSceneIdJson.put(counter, sceneId);
	                    
	                    docIdSceneNumMap.put(counter, sceneNum);
	                    docIdSceneNumJson.put(counter, sceneNum);
	                    
	                    docIdTextMap.put(counter, scene.getWordList());
	                    docIdTextJson.put(counter, scene.getWordList());
	                    
	                    List <String> playScenes = playIdSceneIdMap.get(playId);
	                    if(playScenes!=null) {
	                    		playScenes.add(sceneId);
	                    }
	                    else {
	                    		List <String> newPlayScenes = new ArrayList<>();
	                    		newPlayScenes.add(sceneId);
	                    		playIdSceneIdMap.put(playId, newPlayScenes);	
	                    		playIdSceneIdJson.put(playId, newPlayScenes);
	                    		
	                    }
	                    int current_playLength = playIdSceneIdMap.get(playId).size();
	                    if(current_playLength >longestPlayLen)
	                    {
	                    		longestPlayLen = current_playLength;
	                    		longestPlay = playId;
	                    }
	                    if (current_playLength < shortestPlayLen) {
	                    	shortestPlayLen = current_playLength;
	                    	shortedPlay = playId;
	                    }
	                    
	                for (int i = 0 ;i< wordList.size() ;i++)
	                {
	
	                    String term = wordList.get(i);
	                    if (invertedIndices.containsKey(term))
	                    {
	                        List<Posting> termPosting = invertedIndices.get(term);
	                        Boolean postingExist = false;
	                        for(Posting posting: termPosting)
	                        {
	                            int docID = posting.getID();
	                            if (docID == sceneNum.intValue())
	                            {
	                                posting.getPositions().add(i);
	                                postingExist = true;
	                                break;
	                            }
	                        }
	                        if (postingExist == false)
	                        {
	                            Posting p = new Posting();
	                            p.setID(sceneNum.intValue());
	                            List<Integer> indices = new ArrayList<>();
	                            indices.add(i);
	                            p.setPositions(indices);
	                            termPosting.add(p);
	                        }
	                    }
	                    else
	                    {
	                        Posting p2 = new Posting();
	                        p2.setID(sceneNum.intValue());
	                        List<Integer> indices = new ArrayList<>();
	                        indices.add(i);
	                        p2.setPositions(indices);
	                        List <Posting> pl = new ArrayList<>();
	                        pl.add(p2);
	                        invertedIndices.put(term,pl);
	                    }
	                }
	                counter +=1;
        }
        
      System.out.println("Max length of the scene is ->>> "+maxSceneLength);
      System.out.println("Min length of scene is ->>> "+minSceneLength);
      System.out.println("Average length of the scene ->>> " + averageSceneLength/counter);
      System.out.println("Shorted Scene ->>>>>"+ shortedScene);
      System.out.println("Longest Scene ->>>>>"+ longestScene);
      //System.out.println("Shorted Play ->>>>>"+ shortedPlay);
      //System.out.println("Longest Play ->>>>>"+ longestPlay);
        for (Entry<String,List<Posting>> te:invertedIndices.entrySet() ) {
        		String term = te.getKey();
	    		List<Posting> termPostings = te.getValue();
	    		for (Posting p: termPostings) {
	    			
				int count = p.getPositions().size();
				p.setCount(count);		
	    		}
        }
        
        idxcomp.setDocIdSceneIdMap(docIdSceneIdMap);
        idxcomp.setDocIdSceneNumMap(docIdSceneNumMap);
        idxcomp.setInvertedIndices(invertedIndices);
        idxcomp.setPlayIdSceneIdMap(playIdSceneIdMap);
        idxcomp.setSceneIdPlayIdMap(sceneIdPlayIdMap);
        idxcomp.setDocIdTextJson(docIdTextJson);
        
        if(compressed) {
        	this.createInvertedDeltaIndices(idxcomp,invertedIndices);
        }
        
        idxcomp.setDocIdSceneIdJson(docIdSceneIdJson);
        idxcomp.setDocIdSceneNumJson(docIdSceneNumJson);
        idxcomp.setPlayIdSceneIdJson(playIdSceneIdJson);
        idxcomp.setSceneIdPlayIdJson(sceneIdPlayIdJson);
        idxcomp.setDocIdTextJson(docIdTextJson);
        
        return idxcomp;
    }
    
    public void createInvertedDeltaIndices(IndexingComponents idxcomp ,Map<String,List<Posting>> invertedIndices ) {
    Map<String,List<Posting>> invertedDeltaIndices = new TreeMap<String,List<Posting>>();

    	for (Entry<String,List<Posting>> te:invertedIndices.entrySet() ) {
    		String term = te.getKey();
    		List<Posting> termPostings = te.getValue();
    		List<Posting> termDeltaPostings = new ArrayList<Posting>();
    		Integer prevDocId = 0;
    		for (Posting p: termPostings) {
    			Posting delta = new Posting();
    			int currentDocId = p.getID();
    			delta.setID(currentDocId-prevDocId);
    			prevDocId = currentDocId;
    			List<Integer> indices = new ArrayList<Integer>();
    			int firstElem = 0;
    			for (Integer index:p.getPositions() ) {
    				int currentIndex = index;
    				indices.add(currentIndex-firstElem);
    				firstElem = currentIndex;
    			}
    			delta.setPositions(indices);
			//int count = p.getPositions().size();
			//p.setCount(count);	
			delta.setCount(p.getCount());
			termDeltaPostings.add(delta);
			
    		}
    		invertedDeltaIndices.put(term, termDeltaPostings);
    }
    	
    idxcomp.setInvertedDeltaIndices(invertedDeltaIndices);

    }
}
