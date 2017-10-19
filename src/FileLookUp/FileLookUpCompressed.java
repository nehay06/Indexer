package FileLookUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.json.simple.JSONObject;

import data.Posting;
import indexercomponents.FileLookUpComponents;
import indexercomponents.IndexingComponents;

public class FileLookUpCompressed {
	
	private TreeMap<String,FileLookUpComponents>fileTable = new TreeMap<>();
	private JSONObject fileTableJson = new JSONObject();
	private List <Byte> invertedIndicesList = new ArrayList<>();

	
	public List<Byte> getInvertedIndicesList() {
		return invertedIndicesList;
	}
	public void setInvertedIndicesList(List<Byte> invertedIndicesList) {
		this.invertedIndicesList = invertedIndicesList;
	}
	public FileLookUpCompressed(IndexingComponents idxcomp){
		this.createFileTable(idxcomp);
	}
	public TreeMap<String, FileLookUpComponents> getFileTable() {
		return fileTable;
	}

	public void setFileTable(TreeMap<String, FileLookUpComponents> fileTable) {
		this.fileTable = fileTable;
	}

	public JSONObject getFileTableJson() {
		return fileTableJson;
	}

	public void setFileTableJson(JSONObject fileTableJson) {
		this.fileTableJson = fileTableJson;
	}

	
	
	public List<Byte> encode(List<Integer>input) {
		
		List<Byte>output = new ArrayList<Byte>();
		
		for (int i : input ) 
		{
			while ( i >= 128 ) {
			output.add ( (byte) (i & 0x7F) ) ;
			i = i >> 7 ; // logical shift, no sign bit extension
			}
			output.add( (byte) (i | 0x80) ); 
		}
		return output;
		
	}
	
		

	
	public void createFileTable(IndexingComponents idxcomp){
		System.out.println("Creating look up table for compressed list");
		Map<String,List<Posting>> invertedIndices  = idxcomp.getInvertedDeltaIndices();
		//List<Integer> flatInvertedList = this.getFlatInvertedList(invertedIndices);
		
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

			if(term.equals("unguarded"))
			{
				int j = 0;
				j += 5;
				j = j*4;
			}
			for(Posting posting: termPostings) {
				
				termlookupList.add(posting.getID());
				termlookupList.add(posting.getCount());
				List<Integer> termPositions = posting.getPositions();
				for(Integer position: termPositions) {
					termlookupList.add(position);
				}
				ctf+= posting.getCount();
			}

			List<Byte> compressedLookUp= this.encode(termlookupList);
			
			int documentFreq = termPostings.size() ;
				
			int postingLength = compressedLookUp.size();
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
			invertedIndicesList.addAll(compressedLookUp);
		
		}
		System.out.println("Total items in the interverted list "+counter);
		this.setFileTable(fileTable);
		this.setInvertedIndicesList(invertedIndicesList);
		this.setFileTableJson(fileTableJson);
	}
}
