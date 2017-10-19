package retrieval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import fileio.FileIO;

public class QueryGeneration {
	
	public String randomSelectQueryTerms(Map<String,List<Integer>> lookuplist,int count) {
		Random random    = new Random();
		List<String> keys      = new ArrayList<String>(lookuplist.keySet());
		StringBuilder sb =new StringBuilder("");  

		Map<String,List<Integer>> randomTerms = new TreeMap<String,List<Integer>>();
		String prefix = "";
		while (count > 0)
		{
			String randomKey = keys.get( random.nextInt(keys.size()) );
			sb.append(prefix);
			prefix = " ";
			sb.append(randomKey);
			count--;
		}
		//System.out.println("Generated Query" + sb.toString());
		return sb.toString();
		
	}
	
	public void createQueryTerms(Map<String,List<Integer>> lookuplist,int count,int totalQuery,String FileName) throws IOException {
		
		System.out.println("Generating query terms");
		FileIO fileio = new FileIO();
		StringBuilder sb =new StringBuilder("");  
		String prefix = "";
		while(totalQuery>0) {
			String queryTerms = this.randomSelectQueryTerms(lookuplist,count);
			sb.append(prefix);
			prefix = "\n";
			sb.append(queryTerms);
			totalQuery--;
		}
		fileio.writeTextToFile(FileName,sb.toString());

		System.out.println("Finished generating query terms");

	}
	
}
