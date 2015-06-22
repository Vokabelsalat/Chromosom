package NukleosomReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BigNukleosomReader {
	
	public static List<String> readNukleosoms(int offset, int number) {
		String line = "";
		String fileName = "run2_state.txt";
		int lineNr = 0;
		int lineCounter = 0;
		
		//Die ArrayList die zurückgegeben werden soll
		List<String> returnList = new ArrayList<String>();
	
		try {
			//Filereader um aus der Datei lesen zu können
			BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
			
			//Solange noch Eintraege vorhanden sind, sollen diese in die Liste aufgenommen werden
			while((line = fileReader.readLine()) != null && lineCounter < number){
				if(lineNr < offset) {
				}
				else {
					returnList.add(line);
					lineCounter++;
//					System.err.println(line);
				}
				lineNr++;
			}
			
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return returnList;
	}	
	
	public static List<String> extractNukleosomStrings(List<String> inputList) {
		List<String> returnList = new ArrayList<String>();
		
		for(String str : inputList) {
			
			String splitArray[] = str.split("\\|");
			
			for(int i = 0; i < splitArray.length; i++) {
//				System.err.println(splitArray[i]);
			}
			
//			for(String nukleosomString : splitArray) {
//				returnList.add(nukleosomString);
//				System.err.println(nukleosomString);
//			}
		}
		
		return returnList;
	}
	
	public static List<String> extractModifikations(String nukleosomString) {
		List<String> modiList = new ArrayList<String>();
		
		String splitArray[] = nukleosomString.split("\\)\\(");
		
		for(String str : splitArray) {
//			str.replaceAll("\\(", "");
//			str.replaceAll("\\)", "");
			modiList.add((str.replaceAll("\\)", "").replaceAll("\\(", "")));
		}
 		
		return modiList;
	}
	
	public static void main(String args[]) {
		
		List<String> 	h2A = new ArrayList<String>(),
						h2B = new ArrayList<String>(),
						h3 = new ArrayList<String>(),
						h4 = new ArrayList<String>();
		
		Map<String, List<String>> proteinMap = new HashMap<String, List<String>>();
		
		List<String> returnList = readNukleosoms(0, 100);
		System.err.println(returnList.get(0));
		String splitArray[] = returnList.get(0).split("\\|");
		String splitArray2[] = new String[splitArray.length];
		
		
		
		for(int i = 1; i < splitArray.length; i++) {
			splitArray2[i] = splitArray[i].replace(splitArray[i-1], "");
//			extractModifikations(splitArray2[i]);
			
//			h2A = new ArrayList<String>();
//			h2B = new ArrayList<String>();
//			h3 = new ArrayList<String>();
//			h4 = new ArrayList<String>();
			
//			proteinMap = new HashMap<String, List<String>>();
			
			proteinMap.put("H2A", h2A);
			proteinMap.put("H2B", h2B);
			proteinMap.put("H3", h3);
			proteinMap.put("H4", h4);
			
			for(String str : extractModifikations(splitArray2[i])) {
				if(str.contains("[")) {
					String key = str.substring(0,str.indexOf("["));
					
					String rest = str.replace(key, "");
					
					proteinMap.get(key).add(rest.substring(1,rest.indexOf(".")));
				}
				System.out.print(str);
			}
			
			System.out.println("");
			
//			for(Entry<String, List<String>> prot : proteinMap.entrySet()) {
//				System.err.print("[ " + prot.getKey() + " ");
//				
//				for(String mod : prot.getValue()) {
//					System.err.print(mod + " ");
//				}
//				System.err.print(" ]\n");
//				
//			}
		}
		
		for(Entry<String, List<String>> prot : proteinMap.entrySet()) {
			
			List<String> duplicatList = prot.getValue();
			Set<String> uniqueList = new HashSet<String>(duplicatList);
			duplicatList = new ArrayList<String>(uniqueList);
			Collections.sort(duplicatList);
			System.out.println("Removed Duplicate : "+ duplicatList + " " + duplicatList.size());
			
//			System.err.print(prot.getKey() + " ");
//			
//			System.err.println(prot.getValue());
		}
		
//		List<String> duplicatList = new ArrayList<String>();

			
		
		
		

		
		
//		extractNukleosomStrings(returnList);
	}
}
