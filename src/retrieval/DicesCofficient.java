package retrieval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import constant.ProjectConstants;
import data.Posting;
import fileio.FileIO;

public class DicesCofficient {
	
	
	public  String generateCofficient(Map<String, List<Integer>> lookuplist,String queryFileName,String invertedFilename,boolean compressed) throws IOException {
		FileIO fileio = new FileIO();
	    List<String> queryList = fileio.readQueryFile(queryFileName);
        LoadAncillaryStructures rapi = new LoadAncillaryStructures();
        rapi.loadDocIdTextMap(ProjectConstants.DOC_ID_TEXT_FILE);
        Map<Integer,List<String>>docIdTextMap = rapi.getDocIdTextMap();
       
        List<String> doubleQueryList = new ArrayList<String>();
        StringBuilder doubleQueryTerms =new StringBuilder("");  
		String prefix = "";
		
	    for (String query:queryList)
        {
	    		//System.out.println("Query: "+query);
	    		List<String> queryTerms = Arrays.asList(query.split("\\s+"));
	    		
	    		List<List<Integer>> Querypostings = new ArrayList<List<Integer>>();
	    		Map<String,String> adjacentTerms = new HashMap<String,String>();
	    		
	    		StringBuilder dquery =new StringBuilder("");
    		    
    		    String prefixq = "";
	    		for(String queryTerm: queryTerms) {
	    			
	    			List lookupTerms = lookuplist.get(queryTerm);
	    			Long byteOffset = (Long) lookupTerms.get(0);
	    			Integer length = ((Number) lookupTerms.get(1)).intValue();
	    			Integer na = ((Number)lookupTerms.get(3)).intValue();
	    			List<Integer> posting = new ArrayList<Integer>();

	    			if(compressed)
	    			{
	    			        posting = fileio.readCompressedInvertedList(invertedFilename, byteOffset, length);
	    			} 
	    			else
	    			{
    			        posting = fileio.readInvertedList(invertedFilename, byteOffset, length);
	    			}
					int counter = 0;
					Integer currentDocId;
					Map<String,Integer>nbrtermMap = new HashMap<String,Integer>();

					while (counter < posting.size()) {
						
						currentDocId = posting.get(counter);
		    				List<String>wordList = docIdTextMap.get(currentDocId);
		    				Integer wordListLength = wordList.size();
		    				
		    				List<String>nbrWords = new ArrayList<String>();

						int count = posting.get(counter + 1);
						List<Integer>indices = new ArrayList<Integer>();
						int c = 0;
						while(c< count)
						{
							int currentIndex =((Number) posting.get(counter+2+c)).intValue();
							indices.add(currentIndex);
							if(currentIndex+1 < wordListLength)
							{
								String nbr1 = wordList.get(currentIndex+1);
								if(!nbrWords.contains(nbr1)) {
									nbrWords.add(nbr1);
								}
							}
							if (currentIndex-1 >=0) {
								String nbr2 =  wordList.get(currentIndex-1);
								if(!nbrWords.contains(nbr2)){
									nbrWords.add(nbr2);
								}
							}
							
							
							c++;
						}
				

						for(String nbr: nbrWords) {
							if (nbrtermMap.containsKey(nbr)){
								continue;
							}
							List nbrlookupTerms = lookuplist.get(nbr);
				    			Long nbrbyteOffset = (Long) nbrlookupTerms.get(0);
				    			Integer nbrlength = ((Number) nbrlookupTerms.get(1)).intValue();

								
							List<Integer> nbrposting = new ArrayList<Integer>();
	
				    			if(compressed)
				    			{
				    				nbrposting = fileio.readCompressedInvertedList(invertedFilename, nbrbyteOffset, nbrlength);
				    			} 
				    			else
				    			{
				    				nbrposting = fileio.readInvertedList(invertedFilename, nbrbyteOffset, nbrlength);
				    			}
				    			
				    				int counter1 = 0;
								while (counter1 < nbrposting.size()) {
									
									Integer nbrDocId = nbrposting.get(counter1);
					    				List<Integer>nbrIndices = new ArrayList<Integer>();
					    				int nbrcount = nbrposting.get(counter1 + 1);
					    				if (nbrDocId.equals(currentDocId)) {
					    					List<Integer>nbrindices = new ArrayList<Integer>();
											int nbrc = 0;
											while(nbrc<nbrcount)
											{
												int currentIndex = nbrposting.get(counter1+2+nbrc);
												nbrindices.add(currentIndex);
												nbrc++;
											}
											
											//System.out.println("nbrindices "+ nbrindices.toString());
											List<Integer>nbrindices1 = new ArrayList<Integer>();
											List<Integer>nbrindices2 = new ArrayList<Integer>();
											for(Integer index: nbrindices) {
												nbrindices1.add(index+1);
												nbrindices2.add(index-1);
											}
											
											nbrindices1.retainAll(indices);
											nbrindices2.retainAll(indices);
											Integer totalNbrCount = nbrindices1.size()+nbrindices2.size();
											if(nbrtermMap.containsKey(nbr)) {
												int keyCount = nbrtermMap.get(nbr);
												nbrtermMap.put(nbr, totalNbrCount+keyCount);
												
											}
											else {
												nbrtermMap.put(nbr, totalNbrCount);
											}
					    				}
					    				counter1 = counter1 + nbrcount + 2;
						}	//end of nbrPosting traversal
					
						} //end of all neighbors traversal
			counter = counter + count + 2;
	    			
	    		} //end of posting traversal for one query word
			Double MaxValue = Double.NEGATIVE_INFINITY;
			for(Entry<String,Integer> e:nbrtermMap.entrySet()) {
				String neighbr = e.getKey();
			    Integer nab = e.getValue();
			    
			    List nbrlookupTerms = lookuplist.get(neighbr);
    				
    				Integer nb = ((Number)nbrlookupTerms.get(3)).intValue();
    				Double diceCofficient = (double) (double)nab*1.0/((double)2*na+(double)2*nb);
    				Double currentMax = Math.max(MaxValue,diceCofficient);
    				if (currentMax > MaxValue) {
    					MaxValue = diceCofficient;
    					adjacentTerms.put(queryTerm, neighbr);
    				}
			    
			}
			
		  
        } //end of all query in term traversal 
		for(Entry<String,String>et:adjacentTerms.entrySet()) {
		   //System.out.println(et.getKey() +" / "+ et.getValue());
		   dquery.append(prefixq);
			prefixq = " ";
			dquery.append(et.getKey() +" "+ et.getValue());
	   }
	   //System.out.println("dquery "+ dquery.toString());
	   doubleQueryTerms.append(prefix);
	   prefix = "\n";
	  doubleQueryTerms.append(dquery.toString());
	}//end of all query traversal
	return doubleQueryTerms.toString();
 }	//end of function
       
}
