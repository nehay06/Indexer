package main;

import java.util.Map.Entry;
import java.util.Set;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import FileLookUp.FileLookUpCompressed;
import FileLookUp.FileLookUpTable;
import data.JSONReader;
import data.Posting;
import fileio.FileIO;
import indexercomponents.FileLookUpComponents;
import indexercomponents.IndexingComponents;
import parser.Tokenizer;
import retrieval.LoadAncillaryStructures;
import retrieval.QueryGeneration;
import retrieval.RetrievalAPI;
import retrieval.TopQueryResult;
import retrieval.DicesCofficient;

public class main {
	
	public static void getCompressedQueryResult(Tokenizer t,boolean generateQuery,boolean validate ) throws IOException {
		IndexingComponents idxcomp = t.indexerComponents(true);
		Map<String,List<Posting>> invertedDeltaIndices = idxcomp.getInvertedDeltaIndices();
        FileLookUpCompressed flcompress = new FileLookUpCompressed(idxcomp);
        FileIO fileio = new FileIO();
        
        //fileio. writeCompressedAncillaryIndexStructureToDisk(idxcomp,flcompress);
        fileio.writeAuxStructures(idxcomp);
        fileio.writeCompressedLookUp(flcompress);
        fileio.writeInvertedBinaryFile(flcompress.getInvertedIndicesList(), "invertedCompressedIndicies.dat");
       
      
        RetrievalAPI retapi = new RetrievalAPI();
        LoadAncillaryStructures rapi = new LoadAncillaryStructures();
        rapi.loadCompressedLookUpTable("CompressedLookUpFile.json");
        rapi.loadDocIdSceneIdMap("docIdSceneId.json");
        Map<Integer,String>docIdSceneIdMap= rapi.getDocIdSceneIdMap();
        Map<String,List<Integer>> compressedLookuplist = rapi.getCompressedLookuplist();
        
        
      
        if(generateQuery) {
	        QueryGeneration qg = new QueryGeneration();
			qg.createQueryTerms(compressedLookuplist,7,100,"queryTerms.txt");
        }
	    List<String> queryList = fileio.readQueryFile("queryTerms.txt");
	           
	    StringBuilder sb =new StringBuilder("");  
		String prefix = "";
		final long startTime = System.currentTimeMillis();

        for (String query:queryList)
        {
            	List<String> queryTerms = Arrays.asList(query.split("\\s+"));
			//List<String> queryTerms = new ArrayList<String>(Arrays.asList("unguarded", "mount", "blameful", "coystrill", "unproportioned", "stickler", "savoury"));

    	 		List<TopQueryResult>topCompressedResults = retapi.retrieveDocAtATime(compressedLookuplist, docIdSceneIdMap, queryTerms, "invertedCompressedIndicies.dat",true); 
    	 		sb.append(prefix);
			prefix = "\n";
			sb.append(query.toString());
			sb.append(" : ");
			StringBuilder tt=new StringBuilder("");
			String prefix2 = "";
    	 		for(TopQueryResult res:topCompressedResults) {
    	 			tt.append(prefix2);
    	 			prefix2 = " ";
	    	  		tt.append(res.toString());
    		      }
    	 		sb.append(tt.toString());

        }
        final long endTime = System.currentTimeMillis();

        System.out.println("Total execution time for compressed query: " + (endTime - startTime) );
  		fileio.writeTextToFile("CompressedQueryTermsResult",sb.toString());
  		DicesCofficient dc = new DicesCofficient();
  		String doubleQuery = dc.generateCofficient(compressedLookuplist,"queryTerms.txt","invertedCompressedIndicies.dat",true) ;
  		fileio.writeTextToFile("doubleQueryTerms.txt", doubleQuery);
  		
  		
  		List<String> queryListd = fileio.readQueryFile("doubleQueryTerms.txt");
  		StringBuilder sbd =new StringBuilder("");  
 		String prefixd = "";
 		final long startTime1 = System.currentTimeMillis();

         for (String query:queryListd)
         {
             	List<String> queryTerms = Arrays.asList(query.split("\\s+"));
 			//List<String> queryTerms = new ArrayList<String>(Arrays.asList("unguarded", "mount", "blameful", "coystrill", "unproportioned", "stickler", "savoury"));

     	 		List<TopQueryResult>topCompressedResults = retapi.retrieveDocAtATime(compressedLookuplist, docIdSceneIdMap, queryTerms, "invertedCompressedIndicies.dat",true); 
     	 	sbd.append(prefixd);
 			prefixd = "\n";
 			sbd.append(query.toString());
 			sbd.append(" : ");
 			StringBuilder tt=new StringBuilder("");
 			String prefix2 = "";
     	 		for(TopQueryResult res:topCompressedResults) {
     	 			tt.append(prefix2);
     	 			prefix2 = " ";
 	    	  		tt.append(res.toString());
     		      }
     	 		sbd.append(tt.toString());

         }
         //System.out.println(sbd.toString());
         final long endTime1 = System.currentTimeMillis();

         System.out.println("Total execution time for Dices compressed query: " + (endTime1 - startTime1) );
   		fileio.writeTextToFile("DicesCompressedQueryTermsResult",sbd.toString());
   		
   	 System.out.println("Compressed Query Evaluation");
   	  if(validate)
      {
      	    FileLookUpTable flc = new FileLookUpTable(idxcomp);
      		fileio.writeUnCompressedLoopUpFile(flc);
  			rapi.loadLookUpTable("LookUpFile.json");
  			Map<String,List<Integer>> lookuplist = rapi.getLookuplist();
  			Set<String> compressedLookupTerms = compressedLookuplist.keySet();
  			Set<String> LookupTerms = lookuplist.keySet();
  			Integer LookupTermsSize = lookuplist.size();
  			LookupTerms.retainAll(compressedLookupTerms);
  			if(LookupTerms.size() == LookupTermsSize)
  			{
  				System.out.println("Validation successfull for compressed and uncompressed list");
  			}
  			
      }
  		
	}

	public static void getQueryResults(Tokenizer t,boolean generateQuery,boolean validate ) throws IOException {
		IndexingComponents idxcomp = t.indexerComponents(false);
        Map<String,List<Posting>> invertedIndices = idxcomp.getInvertedIndices();
        FileLookUpTable flc = new FileLookUpTable(idxcomp);
      
			FileIO fileio = new FileIO();
			
			//fileio.writeAncillaryIndexStructureToDisk(idxcomp,flc);
			fileio.writeAuxStructures(idxcomp);
	        fileio.writeUnCompressedLoopUpFile(flc);
			fileio.writeBinaryFile(flc.getInvertedIndicesList(), "invertedIndicies.dat");
			
			LoadAncillaryStructures rapi = new LoadAncillaryStructures();
			rapi.loadLookUpTable("LookUpFile.json");
			rapi.loadDocIdSceneIdMap("docIdSceneId.json");
			Map<String,List<Integer>> lookuplist = rapi.getLookuplist();
			Map<Integer,String>docIdSceneIdMap= rapi.getDocIdSceneIdMap();
			
			
			if (generateQuery) {
				QueryGeneration qg = new QueryGeneration();
				qg.createQueryTerms(lookuplist,7,100,"queryTerms.txt");
			}
			
		    List<String> queryList = fileio.readQueryFile("queryTerms.txt");
		    
		    
		    RetrievalAPI retapi = new RetrievalAPI();
		    
		    StringBuilder sb =new StringBuilder("");  
			String prefix = "";
			
			final long startTime = System.currentTimeMillis();
	        
			for (String query:queryList)
	        {
	            	List<String> queryTerms = Arrays.asList(query.split("\\s+"));
				//List<String> queryTerms = new ArrayList<String>(Arrays.asList("unguarded", "mount", "blameful", "coystrill", "unproportioned", "stickler", "savoury"));

	
	    	 		List<TopQueryResult>TopResults = retapi.retrieveDocAtATime(lookuplist, docIdSceneIdMap, queryTerms, "invertedIndicies.dat",false); 
	    	 		sb.append(prefix);
				prefix = "\n";
				sb.append(query.toString());
				sb.append(" : ");
				StringBuilder tt=new StringBuilder("");
				String prefix2 = "";
	    	 		for(TopQueryResult res:TopResults) {
	    	 			tt.append(prefix2);
	    	 			prefix2 = " ";
		    	  		tt.append(res.toString());

	    		      }
	    	 		sb.append(tt.toString());
	
	        }
	        final long endTime = System.currentTimeMillis();

	        System.out.println("Total execution time for un-compressed query: " + (endTime - startTime) );
	  		
	        fileio.writeTextToFile("QueryTermsResult",sb.toString());
	  		DicesCofficient dc = new DicesCofficient();
	  		String doubleQuery = dc.generateCofficient(lookuplist,"queryTerms.txt","invertedIndicies.dat",false) ;
	  		fileio.writeTextToFile("doubleQueryTerms.txt", doubleQuery);
	  		List<String> queryListd = fileio.readQueryFile("doubleQueryTerms.txt");

	  		StringBuilder sbd =new StringBuilder("");  
			String prefixd = "";
	  		final long startTime1 = System.currentTimeMillis();
	        
	  		for (String query:queryListd)
	        {
	            	List<String> queryTerms = Arrays.asList(query.split("\\s+"));
				//List<String> queryTerms = new ArrayList<String>(Arrays.asList("unguarded", "mount", "blameful", "coystrill", "unproportioned", "stickler", "savoury"));

	
	    	 		List<TopQueryResult>TopResults = retapi.retrieveDocAtATime(lookuplist, docIdSceneIdMap, queryTerms, "invertedIndicies.dat",false); 
	    	 		sbd.append(prefixd);
				prefixd = "\n";
				sbd.append(query.toString());
				sbd.append(" : ");
				StringBuilder tt=new StringBuilder("");
				String prefix2 = "";
	    	 		for(TopQueryResult res:TopResults) {
	    	 			tt.append(prefix2);
	    	 			prefix2 = " ";
		    	  		tt.append(res.toString());

	    		      }
	    	 		sbd.append(tt.toString());
	
	        }
	        final long endTime1 = System.currentTimeMillis();
	        System.out.println("Total execution time for un-compressed dice's cofficient query: " + (endTime1 - startTime1) );
	        fileio.writeTextToFile("DicesQueryTermsResult",sbd.toString());
	        
	        System.out.println("Uncompressed Query Evaluation");
	        if(validate)
	        {
	        		IndexingComponents idxcomp1 = t.indexerComponents(true);
	        		FileLookUpCompressed flcompress = new FileLookUpCompressed(idxcomp1);
	        		fileio.writeCompressedLookUp(flcompress);
	        		rapi.loadCompressedLookUpTable("CompressedLookUpFile.json");
	                
	            Map<String,List<Integer>> compressedLookuplist = rapi.getCompressedLookuplist();
	                
	    			rapi.loadLookUpTable("LookUpFile.json");
	    			Set<String> compressedLookupTerms = compressedLookuplist.keySet();
	    			Set<String> LookupTerms = lookuplist.keySet();
	    			Integer LookupTermsSize = lookuplist.size();
	    			LookupTerms.retainAll(compressedLookupTerms);
	    			if(LookupTerms.size() == LookupTermsSize)
	    			{
	    				
	    				System.out.println("Validation successfull for compressed and uncompressed list");
	    			}
	    			
	        }

	  		
	}
    public static void main(String args[]) throws IOException
    {
    	
    		boolean performWithCompression = false;
    		boolean generateQuery = false;
    		boolean validate = false;
    		for(int i = 0; i<args.length;i++) {
    			
        			if(args[i].equals("-c")) {
        				performWithCompression = true;
        			}
        	
        		
       
        		if(args[i].equals("-q"))
        		{
        			generateQuery = true;
        		}
     
        		
        		if(args[i].equals("-d"))
        		{
        			validate = true;
        		}
    		}
    		
    	
    		Tokenizer t = new Tokenizer();
        JSONReader jsonReader = new JSONReader("shakespeare-scenes.json");
        t.setJsonReader(jsonReader);
        if (performWithCompression)
        {
        		System.out.println("Performing Compressed query evaluation");
    	    		getCompressedQueryResult(t,generateQuery,validate);
    	    		
        }
        else {
        	System.out.println("Performing query evaluation");
        	getQueryResults(t,generateQuery,validate);
      
        	
        }
        
       

    

    }
}
