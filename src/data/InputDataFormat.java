package data;

import java.util.*;

public class InputDataFormat {
	
	private String sceneId;
	private String playId;
	private int sceneNum;
	private String text;
	private List <String> wordList = new ArrayList<>();
	
	public InputDataFormat() {
		
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public int getSceneNum() {
		return sceneNum;
	}

	public void setSceneNum(int sceneNum) {
		this.sceneNum = sceneNum;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getWordList() {
		return wordList;
	}

	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}

	
}
