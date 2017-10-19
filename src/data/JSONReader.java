package data;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;

public class JSONReader {

    String filname;

    public JSONReader(String filename)
    {
        this.filname = filename;
    }
    
    public List<InputDataFormat> readData()
    {
        JSONParser parser = new JSONParser();
        JSONArray corpus = new JSONArray();
        List<InputDataFormat> dataList = new ArrayList<>();
        try {
                Object object = parser.parse(new FileReader(this.filname));

                //convert Object to JSONObject
                JSONObject jsonObject = (JSONObject)object;

                //Reading the corpus
                corpus = (JSONArray) jsonObject.get("corpus");
                
                int corpusSize = corpus.size();
                for(int index = 0;index < corpusSize ;index++){
                		JSONObject current_play = (JSONObject) corpus.get(index);
                		String playId = (String)current_play.get("playId");
                		String sceneId = (String)current_play.get("sceneId");
                		Number sceneNum = (Number) current_play.get("sceneNum");
                		String text = (String)current_play.get("text");
                		List<String> wordList = new ArrayList<String>(Arrays.asList(text.split("\\s+")));
                		InputDataFormat df = new InputDataFormat();
                		df.setPlayId(playId);
                		df.setSceneId(sceneId);
                		df.setSceneNum(sceneNum.intValue());
                		df.setText(text);
                		df.setWordList(wordList);
                		dataList.add(df);		
                }

        }  catch(FileNotFoundException fe) {
            fe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
