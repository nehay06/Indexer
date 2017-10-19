package fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import FileLookUp.FileLookUpCompressed;
import FileLookUp.FileLookUpTable;
import constant.ProjectConstants;
import data.Posting;
import indexercomponents.IndexingComponents;

public class FileIO {

	public void writeBinaryFile(List<Integer> lookupList, String fileName) throws IOException {
		FileOutputStream fstream = new FileOutputStream(fileName);
		DataOutputStream dos = new DataOutputStream(fstream);
		System.out.println("Writing inverted indexes to binary file");
		int counter = 0;
		for (Integer item : lookupList) {
			dos.writeInt(item);
		}
		dos.close();
		//System.out.println("Finished Writing inverted indexes to binary file");
	}

	public void writeInvertedBinaryFile(List<Byte> lookupList, String fileName) throws IOException {
		FileOutputStream fstream = new FileOutputStream(fileName);
		DataOutputStream dos = new DataOutputStream(fstream);
		System.out.println("Writing inverted indexes to binary file");
		int counter = 0;
		for (Byte item : lookupList) {
			// if (counter < 10) {
			// System.out.println(item);
			// counter++;
			// }
			dos.writeByte(item);
		}
		dos.close();
		//System.out.println("Finished Writing inverted indexes to binary file");
	}

	public void writeInvertedIndexesToBinaryFile(Map<String, List<Posting>> invertedIndices, String fileName)
			throws IOException {
		FileOutputStream fstream = new FileOutputStream(fileName);
		DataOutputStream dos = new DataOutputStream(fstream);
		System.out.println("Writing inverted indexes to binary file");
		for (Entry<String, List<Posting>> te : invertedIndices.entrySet()) {
			String term = te.getKey();
			List<Posting> termPostings = te.getValue();
			for (Posting p : termPostings) {
				dos.writeInt(p.getID());
				dos.writeInt(p.getCount());
				List<Integer> termPostions = p.getPositions();
				for (Integer postion : termPostions) {
					dos.writeInt(postion);
				}
			}
		}
		dos.close();
	}

	public void writeJsonObjToJsonFile(JSONObject JSONObj, String fileName) {

		try (FileWriter file = new FileWriter(fileName)) {

			file.write(JSONObj.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	

	}

	public void writeAncillaryIndexStructureToDisk(IndexingComponents idxcomp, FileLookUpTable flc) {

		JSONObject sceneIdPlayIdJson = idxcomp.getSceneIdPlayIdJson();
		JSONObject docIdSceneIdJson = idxcomp.getDocIdSceneIdJson();
		JSONObject playIdSceneIdJson = idxcomp.getPlayIdSceneIdJson();
		JSONObject docIdSceneNumJson = idxcomp.getDocIdSceneNumJson();
		JSONObject docIdTextJson = idxcomp.getDocIdTextJson();
		JSONObject fileTableJson = flc.getFileTableJson();

		System.out.println("Writing ancillary structure to disk");

		this.writeJsonObjToJsonFile(sceneIdPlayIdJson,
				"sceneIdPlayId.json");
		this.writeJsonObjToJsonFile(docIdSceneIdJson,
				"docIdSceneId.json");
		this.writeJsonObjToJsonFile(playIdSceneIdJson,
				"playIdSceneIdJson.json");
		this.writeJsonObjToJsonFile(docIdSceneNumJson,
				"docIdSceneNumJson.json");
		this.writeJsonObjToJsonFile(fileTableJson, "LookUpFile.json");
		
		this.writeJsonObjToJsonFile(docIdTextJson, ProjectConstants.DOC_ID_TEXT_FILE);

		//System.out.println("Finished writing ancillary structure to disk");
	}

	public void writeUnCompressedLoopUpFile(FileLookUpTable flc) {
		System.out.println("Writing lookup table for uncompressed case");
		JSONObject fileTableJson = flc.getFileTableJson();
		this.writeJsonObjToJsonFile(fileTableJson, "LookUpFile.json");
		//System.out.println("Finished Writing lookup table for uncompressed case");
	}
	public void writeAuxStructures(IndexingComponents idxcomp) {
		JSONObject sceneIdPlayIdJson = idxcomp.getSceneIdPlayIdJson();
		JSONObject docIdSceneIdJson = idxcomp.getDocIdSceneIdJson();
		JSONObject playIdSceneIdJson = idxcomp.getPlayIdSceneIdJson();
		JSONObject docIdSceneNumJson = idxcomp.getDocIdSceneNumJson();
		JSONObject docIdTextJson = idxcomp.getDocIdTextJson();
		System.out.println("Writing ancillary structure to disk");

		this.writeJsonObjToJsonFile(sceneIdPlayIdJson,
				"sceneIdPlayId.json");
		this.writeJsonObjToJsonFile(docIdSceneIdJson,
				"docIdSceneId.json");
		this.writeJsonObjToJsonFile(playIdSceneIdJson,
				"playIdSceneId.json");
		this.writeJsonObjToJsonFile(docIdSceneNumJson,
				"docIdSceneNum.json");		
		this.writeJsonObjToJsonFile(docIdTextJson, ProjectConstants.DOC_ID_TEXT_FILE);

		//System.out.println("Finished writing ancillary structure to disk");
	}
	
	public void writeCompressedLookUp(FileLookUpCompressed flc) {
		System.out.println("Writing lookup table for compressed case");
		JSONObject fileTableJson = flc.getFileTableJson();
		this.writeJsonObjToJsonFile(fileTableJson,
				"CompressedLookUpFile.json");
		//System.out.println("Finised Writing lookup table for compressed case");
	}
	
	public void writeCompressedAncillaryIndexStructureToDisk(IndexingComponents idxcomp, FileLookUpCompressed flc) {

		JSONObject sceneIdPlayIdJson = idxcomp.getSceneIdPlayIdJson();
		JSONObject docIdSceneIdJson = idxcomp.getDocIdSceneIdJson();
		JSONObject playIdSceneIdJson = idxcomp.getPlayIdSceneIdJson();
		JSONObject docIdSceneNumJson = idxcomp.getDocIdSceneNumJson();
		JSONObject docIdTextJson = idxcomp.getDocIdTextJson();
		JSONObject fileTableJson = flc.getFileTableJson();

		System.out.println("Writing ancillary structure to disk");

		this.writeJsonObjToJsonFile(sceneIdPlayIdJson,
				"sceneIdPlayIdMap.json");
		this.writeJsonObjToJsonFile(docIdSceneIdJson,
				"docIdSceneId.json");
		this.writeJsonObjToJsonFile(playIdSceneIdJson,
				"playIdSceneIdJson.json");
		this.writeJsonObjToJsonFile(docIdSceneNumJson,
				"docIdSceneNumJson.json");
		this.writeJsonObjToJsonFile(fileTableJson,
				"CompressedLookUpFile.json");
		
		this.writeJsonObjToJsonFile(docIdTextJson, ProjectConstants.DOC_ID_TEXT_FILE);

		//System.out.println("Finished writing ancillary structure to disk");
	}

	public JSONObject readJSONFile(String fileName) {
		JSONObject jsonObject = new JSONObject();
		try {
			JSONParser parser = new JSONParser();
			FileReader fr = new FileReader(fileName);
			Object object = parser.parse(new FileReader(fileName));
			// convert Object to JSONObject
			jsonObject = (JSONObject) object;

			// for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();)
			// {
			// String key = (String) iterator.next();
			// System.out.println(key+ ":"+ jsonObject.get(key));
			// }
			fr.close();
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public void readInvertedListBinary(String filename) throws IOException {
		int number; // To hold a number
		boolean endOfFile = false; // End of file flag

		// Open Numbers.dat as a binary file.
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream inputFile = new DataInputStream(fstream);

		//System.out.println("Reading inverted list from the file:");

		// Read data from the file.
		int counter = 0;

		while (!endOfFile) {
			try {
				number = inputFile.readInt();
				if (counter < 10) {
					System.out.print(number + " ");
					counter++;
				}

			} catch (EOFException e) {
				endOfFile = true;
			}
		}

		// Close the file.
		inputFile.close();
		//System.out.println("\n Done with reading from a binary file.");
	}

	public List<Integer> readInvertedList(String filename, long byteOffset, int length) throws IOException {
		//System.out.println("Reading inverted list from binary file");
		byte[] buffer = new byte[length];
		RandomAccessFile raf = new RandomAccessFile(filename, "r");
		raf.seek(byteOffset);
		raf.read(buffer);
		int counter = 0;
		List<Integer> invertedList = new ArrayList<>();
		while (counter < buffer.length) {
			byte t1 = buffer[counter];
			byte t2 = buffer[counter + 1];
			byte t3 = buffer[counter + 2];
			byte t4 = buffer[counter + 3];
			invertedList.add(ByteBuffer.wrap(new byte[] { t1, t2, t3, t4 }).getInt());
			counter = counter + 4;
		}
		raf.close();
		//System.out.println("Finised reading inverted list from binary file");
		return invertedList;

	}

	public List<Integer> decode(byte[] input) {
		//System.out.println("Performing v byte decoding for the inverted list");
		List<Integer> output = new ArrayList<Integer>();
		for (int i = 0; i < input.length; i++) {
			int position = 0;
			int result = ((int) input[i] & 0x7F);
			while ((input[i] & 0x80) == 0) {
				i += 1;
				position += 1;
				int unsignedByte = ((int) input[i] & 0x7F);
				result |= (unsignedByte << (7 * position));
			}
			output.add(result);
		}
		//System.out.println("Finised performing v byte decoding for the inverted list");
		return output;

	}

	public List<Integer> decodeDelta(List<Integer> posting) {

		//System.out.println("Performing delta decoding for the inverted list");
		List<Integer> decodedPosting = new ArrayList<Integer>();
		int prevDocId = 0;

		int counter = 0;

		while (counter < posting.size()) {
			int currentDocId = posting.get(counter);
			int count = posting.get(counter + 1);
			int ind = 0;
			decodedPosting.add(currentDocId + prevDocId);
			decodedPosting.add(count);
			prevDocId = currentDocId + prevDocId;
			int prevPosition = 0;
			while (ind < count) {
				int index = posting.get(counter + 2 + ind);
				decodedPosting.add(index + prevPosition);
				prevPosition = index + prevPosition;
				ind++;
			}
			counter = counter + count + 2;

		}
		//System.out.println("Finished performing delta decoding for the inverted list");
		return decodedPosting;

	}

	public List<Integer> readCompressedInvertedList(String filename, long byteOffset, int length) throws IOException {

		//System.out.println("Reading compressed inverted list");
		byte[] buffer = new byte[length];
		RandomAccessFile raf = new RandomAccessFile(filename, "r");
		raf.seek(byteOffset);
		raf.read(buffer);
		int counter = 0;
		List<Integer> invertedList = new ArrayList<>();
		List<Integer> encodedInvertedList = this.decode(buffer);
		invertedList = this.decodeDelta(encodedInvertedList);
		raf.close();
		//System.out.println("Finished Reading compressed inverted list");
		return invertedList;

	}

	public void writeTextToFile(String fileName, String text) throws IOException {
		File fout = new File(fileName);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(text);
		bw.newLine();
		bw.close();
	}

	public List<String> readQueryFile(String fileName) {
		System.out.println("Ready query files containing queries");
	
		File file = new File(fileName);

		FileInputStream fis = null;
		BufferedReader reader = null;
		List<String> queryList = new ArrayList<String>();

		try {
			fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();
			while (line != null) {
				//System.out.println(line);
				line = reader.readLine();
				if (line != null) {
					queryList.add(line);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		//System.out.println("Finised Reading query files containing queries");
		return queryList;
	}
}
