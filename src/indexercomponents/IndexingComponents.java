package indexercomponents;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import data.Posting;

public class IndexingComponents {
	
	private Map<String,List<Posting>> invertedIndices;
	private Map<String,List<Posting>> invertedDeltaIndices;
    
	private Map<String,String> sceneIdPlayIdMap;
    private Map<Integer,String> docIdSceneIdMap;
    private Map<String,List<String>> playIdSceneIdMap;
    private Map<Integer,Integer>docIdSceneNumMap;
    private Map<Integer,List<String>>docIdTextMap;
    
    private JSONObject sceneIdPlayIdJson;
    private JSONObject docIdSceneIdJson ;
    private JSONObject playIdSceneIdJson;
    private JSONObject docIdSceneNumJson;
    private JSONObject invertedIndicesJson;
    private JSONObject invertedDeltaIndicesJson;
    private JSONObject docIdTextJson;
    
    
    public Map<String, List<Posting>> getInvertedDeltaIndices() {
		return invertedDeltaIndices;
	}
	public void setInvertedDeltaIndices(Map<String, List<Posting>> invertedDeltaIndices) {
		this.invertedDeltaIndices = invertedDeltaIndices;
	}
	
    public JSONObject getInvertedDeltaIndicesJson() {
		return invertedDeltaIndicesJson;
	}
	public void setInvertedDeltaIndicesJson(JSONObject invertedDeltaIndicesJson) {
		this.invertedDeltaIndicesJson = invertedDeltaIndicesJson;
	}
	public JSONObject getInvertedIndicesJson() {
		return invertedIndicesJson;
	}
	public void setInvertedIndicesJson(JSONObject invertedIndicesJson) {
		this.invertedIndicesJson = invertedIndicesJson;
	}
	public JSONObject getSceneIdPlayIdJson() {
		return sceneIdPlayIdJson;
	}
	public void setSceneIdPlayIdJson(JSONObject sceneIdPlayIdJson) {
		this.sceneIdPlayIdJson = sceneIdPlayIdJson;
	}
	public JSONObject getDocIdSceneIdJson() {
		return docIdSceneIdJson;
	}
	public void setDocIdSceneIdJson(JSONObject docIdSceneIdJson) {
		this.docIdSceneIdJson = docIdSceneIdJson;
	}
	public JSONObject getPlayIdSceneIdJson() {
		return playIdSceneIdJson;
	}
	public void setPlayIdSceneIdJson(JSONObject playIdSceneIdJson) {
		this.playIdSceneIdJson = playIdSceneIdJson;
	}
	public JSONObject getDocIdSceneNumJson() {
		return docIdSceneNumJson;
	}
	public void setDocIdSceneNumJson(JSONObject docIdSceneNumJson) {
		this.docIdSceneNumJson = docIdSceneNumJson;
	}
    
	public Map<String, List<Posting>> getInvertedIndices() {
		return invertedIndices;
	}
	public void setInvertedIndices(Map<String, List<Posting>> invertedIndices) {
		this.invertedIndices = invertedIndices;
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
	
	public JSONObject getDocIdTextJson() {
		return docIdTextJson;
	}
	public void setDocIdTextJson(JSONObject docIdTextJson) {
		this.docIdTextJson = docIdTextJson;
	}
	public Map<Integer,List<String>> getDocIdTextMap() {
		return docIdTextMap;
	}
	public void setDocIdTextMap(Map<Integer,List<String>> docIdTextMap) {
		this.docIdTextMap = docIdTextMap;
	}
}
