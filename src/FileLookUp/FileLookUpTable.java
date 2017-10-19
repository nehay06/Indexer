package FileLookUp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.json.simple.JSONObject;



import data.Posting;
import fileio.FileIO;
import indexercomponents.FileLookUpComponents;
import indexercomponents.IndexingComponents;

public class FileLookUpTable {
	
	private TreeMap<String,FileLookUpComponents>fileTable = new TreeMap<>();
	private JSONObject fileTableJson = new JSONObject();
	private List <Integer> invertedIndicesList = new ArrayList<>();

	public JSONObject getFileTableJson() {
		return fileTableJson;
	}
	public void setFileTableJson(JSONObject fileTableJson) {
		this.fileTableJson = fileTableJson;
	}
	public List<Integer> getInvertedIndicesList() {
		return invertedIndicesList;
	}
	public void setInvertedIndicesList(List<Integer> invertedIndicesList) {
		this.invertedIndicesList = invertedIndicesList;
	}
	public FileLookUpTable(IndexingComponents idxcomp){
		this.createFileTable(idxcomp);
	}
	public TreeMap<String, FileLookUpComponents> getFileTable() {
		return fileTable;
	}

	public void setFileTable(TreeMap<String, FileLookUpComponents> fileTable) {
		this.fileTable = fileTable;
	}
	
	public void createFileTable(IndexingComponents idxcomp){
		System.out.println("Creating look uo table");
		Map<String,List<Posting>> invertedIndices  = idxcomp.getInvertedIndices();
		long prevOffset = 0;
		int counter = 0;
		int prevPostingLength = 0;
		int c = 10;
		for (Entry<String,List<Posting>> te:invertedIndices.entrySet() ) {
			List<Posting> termPostings = te.getValue();
			String term = te.getKey();
			
			Map<String,Integer> docFrequency = new HashMap<String,Integer>();
			Map<String,Integer> length = new HashMap<String,Integer>();
			Map<String,Long> byteOffset = new HashMap<String,Long>();
			Map<String,Integer> termFrequency = new HashMap<String,Integer>();
			List lookUpValues =  new ArrayList<>();
			int ctf = 0;
			long termByteOffset = 0;
			List <Integer> termlookupList = new ArrayList<>();

			for(Posting posting: termPostings) {
				termlookupList.add(posting.getID());
				termlookupList.add(posting.getCount());
				List<Integer> termPositions = posting.getPositions();
				for(Integer position: termPositions) {
					termlookupList.add(position);
				}
				ctf+= posting.getCount();
			}

			int documentFreq = termPostings.size() ;
				
			int postingLength = (2*documentFreq+ ctf)*Integer.SIZE/8;
			if (counter == 0)
			{
				termByteOffset = 0;
				counter++;
			}
			else {
				termByteOffset = prevOffset+prevPostingLength;
				counter++;
			}

			prevOffset = termByteOffset;
			prevPostingLength = postingLength;
			
				
			docFrequency.put("docFrequency",termPostings.size() );
			length.put("length",postingLength );
			byteOffset.put("byteOffset", termByteOffset);
			termFrequency.put("termFrequency", ctf);
			
			lookUpValues.add(termByteOffset);
			lookUpValues.add(postingLength);
			lookUpValues.add(documentFreq);
			lookUpValues.add(ctf);
			
			
			FileLookUpComponents flc = new FileLookUpComponents();
			flc.setLength(length);
			flc.setDocFrequency(docFrequency);
			flc.setTermFrequency(termFrequency);
			flc.setByteOffset(byteOffset);	
			fileTable.put(term, flc);
			fileTableJson.put(term, lookUpValues);
			invertedIndicesList.addAll(termlookupList);
		}
		System.out.println("Total items in the interverted list "+counter);
		this.setFileTable(fileTable);
		this.setInvertedIndicesList(invertedIndicesList);
		this.setFileTableJson(fileTableJson);
	}
}
