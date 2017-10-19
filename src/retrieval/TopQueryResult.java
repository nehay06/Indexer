package retrieval;

import java.util.Comparator;
import java.util.List;

public class TopQueryResult implements Comparator<TopQueryResult> {

	Integer docID;
	String sceneID;
	Integer score;
	
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Integer getDocID() {
		return docID;
	}
	public void setDocID(Integer docID) {
		this.docID = docID;
	}
	public String getSceneID() {
		return sceneID;
	}
	public void setSceneID(String sceneID) {
		this.sceneID = sceneID;
	}
	@Override
	public int compare(TopQueryResult o1, TopQueryResult o2) {
		// TODO Auto-generated method stub
		return o2.getScore().compareTo(o1.getScore());
	}
	
	@Override
	public String toString() { 
	    return "docID: '" + this.docID + "', sceneID: '" + this.sceneID + "', score: '" + this.score + "'";
	} 
	
	
}
