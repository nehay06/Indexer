package indexercomponents;

import java.util.*;

import org.json.simple.JSONObject;

public class FileLookUpComponents {
	
	private Map<String,Integer>termFrequency;
	private Map<String,Integer>docFrequency;
	private Map<String,Integer>length;
	private Map<String,Long>byteOffset;	
	public Map<String, Integer> getTermFrequency() {
		return termFrequency;
	}
	public void setTermFrequency(Map<String, Integer> termFrequency) {
		this.termFrequency = termFrequency;
	}
	public Map<String, Integer> getDocFrequency() {
		return docFrequency;
	}
	public void setDocFrequency(Map<String, Integer> docFrequency) {
		this.docFrequency = docFrequency;
	}
	public Map<String, Integer> getLength() {
		return length;
	}
	public void setLength(Map<String, Integer> length) {
		this.length = length;
	}
	public Map<String, Long> getByteOffset() {
		return byteOffset;
	}
	public void setByteOffset(Map<String, Long> byteOffset) {
		this.byteOffset = byteOffset;
	}
}
