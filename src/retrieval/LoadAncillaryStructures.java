package retrieval;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONObject;

import data.Posting;
import fileio.FileIO;

public class LoadAncillaryStructures {
	
	private Map<String,List<Integer>> lookuplist = new TreeMap<String,List<Integer>>();
	private Map<String,String> sceneIdPlayIdMap = new TreeMap<String,String>();
	private Map<Integer,String> docIdSceneIdMap = new TreeMap<Integer,String>();
    private Map<String,List<String>> playIdSceneIdMap = new TreeMap<String,List<String>>();
    private Map<Integer,Integer>docIdSceneNumMap = new TreeMap<Integer,Integer>();
	private Map<String,List<Integer>> compressedLookuplist = new TreeMap<String,List<Integer>>();
    private Map<Integer,List<String>>docIdTextMap = new TreeMap<Integer,List<String>>();

    
    public Map<String, List<Integer>> getCompressedLookuplist() {
		return compressedLookuplist;
	}

	public void setCompressedLookuplist(Map<String, List<Integer>> compressedLookuplist) {
		this.compressedLookuplist = compressedLookuplist;
	}

	public Map<String, String> getSceneIdPlayIdMap() {
		return sceneIdPlayIdMap;
	}

	public void setSceneIdPlayIdMap(Map<String, String> sceneIdPlayIdMap) {
		this.sceneIdPlayIdMap = sceneIdPlayIdMap;
	}

	public Map<Integer, String> getDocIdSceneIdMap() {
		return docIdSceneIdMap;
	}

	public void setDocIdSceneIdMap(Map<Integer, String> docIdSceneIdMap) {
		this.docIdSceneIdMap = docIdSceneIdMap;
	}

	public Map<String, List<String>> getPlayIdSceneIdMap() {
		return playIdSceneIdMap;
	}

	public void setPlayIdSceneIdMap(Map<String, List<String>> playIdSceneIdMap) {
		this.playIdSceneIdMap = playIdSceneIdMap;
	}

	public Map<Integer, Integer> getDocIdSceneNumMap() {
		return docIdSceneNumMap;
	}

	public void setDocIdSceneNumMap(Map<Integer, Integer> docIdSceneNumMap) {
		this.docIdSceneNumMap = docIdSceneNumMap;
	}

	
	
	public Map<String, List<Integer>> getLookuplist() {
		return lookuplist;
	}

	public void setLookuplist(Map<String, List<Integer>> lookuplist) {
		this.lookuplist = lookuplist;
	}
	
	public void loadLookUpTable(String fileName) {
        FileIO fileio = new FileIO();
		JSONObject jsonObject = fileio.readJSONFile(fileName);
		
		for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
		String key = (String) iterator.next();
		lookuplist.put(key, (List<Integer>) jsonObject.get(key));
		}
	  this.setLookuplist(lookuplist);
	}
	
	public void loadCompressedLookUpTable(String fileName) {
        FileIO fileio = new FileIO();
		JSONObject jsonObject = fileio.readJSONFile(fileName);
		
		for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
		String key = (String) iterator.next();
		lookuplist.put(key, (List<Integer>) jsonObject.get(key));
		}
	  this.setCompressedLookuplist(lookuplist);
	}
	
	public void loadSceneIdPlayIdMap(String fileName) {
		FileIO fileio = new FileIO();
		JSONObject jsonObject = fileio.readJSONFile(fileName);
		
		for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
		String key = (String) iterator.next();
		sceneIdPlayIdMap.put(key, (String) jsonObject.get(key));
		}
	  this.setSceneIdPlayIdMap(sceneIdPlayIdMap);
	}
	
	public void loadDocIdSceneIdMap(String fileName) {
		FileIO fileio = new FileIO();
		JSONObject jsonObject = fileio.readJSONFile(fileName);
		
		for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
		String key =  (String) iterator.next();
		docIdSceneIdMap.put( Integer.valueOf(key), (String) jsonObject.get(key));
		}
	  this.setDocIdSceneIdMap(docIdSceneIdMap);
	}
	
	public void loadPlayIdSceneIdMap(String fileName) {
		FileIO fileio = new FileIO();
		JSONObject jsonObject = fileio.readJSONFile(fileName);
		
		for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
		String key = (String) iterator.next();
		playIdSceneIdMap.put(key, (List<String>) jsonObject.get(key));
		}
	  this.setPlayIdSceneIdMap(playIdSceneIdMap);
	}

	public void loadDocIdSceneNumMap(String fileName) {
		FileIO fileio = new FileIO();
		JSONObject jsonObject = fileio.readJSONFile(fileName);
		
		for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
		docIdSceneNumMap.put(Integer.valueOf(key), (Integer) jsonObject.get(key));
		}
	  this.setDocIdSceneNumMap(docIdSceneNumMap);
	}
	
	public void loadDocIdTextMap(String fileName) {
		FileIO fileio = new FileIO();
		JSONObject jsonObject = fileio.readJSONFile(fileName);
		
		for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			docIdTextMap.put(Integer.valueOf(key),  (List<String>) jsonObject.get(key));
		}
	  this.setDocIdTextMap(docIdTextMap);
	}

	public Map<Integer,List<String>> getDocIdTextMap() {
		return docIdTextMap;
	}

	public void setDocIdTextMap(Map<Integer,List<String>> docIdTextMap) {
		this.docIdTextMap = docIdTextMap;
	}
}
