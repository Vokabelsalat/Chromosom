package NukleosomReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NukleosomReader {
	
	public static List<String> readNukleosoms(String fileName, int offset, int number) {
		String line = "";
//		String fileName = "ES_segmentation_wholedata.data";
		
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
				}
				lineNr++;
			}
			
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return returnList;
	}
	
	public static double[] getValueArray(String str) {
		String retString = "";
		String[] splitArray = str.split(";");

		
		for(int i = 2; i < 11; i++) {
			
			if(i != 2)
				retString += ";";
			retString += splitArray[i];
		}
		splitArray = retString.split(";");
		double returnArray[] = new double[splitArray.length];
		
		for(int i = 0; i < splitArray.length; i++) {
			returnArray[i] =  Double.parseDouble(splitArray[i]);
		}
		
		return returnArray;
	}
	
	public static double[] mergeRoundValues(List<double[]> arrayList) {
		double[] returnArray = new double[arrayList.get(0).length];
		
		for(int dim = 0; dim < returnArray.length; dim++) {
			
			String test = "";
			double newValue = 0.0;
						double sum = 0.0;
						
			for(int counter = 0; counter < arrayList.size(); counter++) {
				
				double value = arrayList.get(counter)[dim];
				test += value + ";";
				sum += value;
			}
			
			double n = arrayList.size();
			
			newValue = sum/n;
			
//			System.err.println(test + " -> " + newValue);
			
			returnArray[dim] = newValue;
			
		}
		
		return returnArray;
	}
	
	public static double[] mergeValueArrays(List<double[]> arrayList) {
		
		double[] returnArray = new double[arrayList.get(0).length];
		
		for(int dim = 0; dim < returnArray.length; dim++) {
			
			double newValue = 0.5;
			double[] copyArray = new double[arrayList.size()];		
			
			String test = "";
			
			for(int counter = 0; counter < arrayList.size(); counter++) {
				
				double value = arrayList.get(counter)[dim];
				
				test += value +";";
				
				if(value < 0.5) {
					copyArray[counter] = 0.0;
				}
				else if(value > 0.5) {
					copyArray[counter] = 1.0;
				}
				else if(value == 0.5) {
					copyArray[counter] = 0.5;
				}
	
			}
			
			double sum = 0;
			double n = copyArray.length;
			
			for(double val : copyArray) {
				if(val == 0.5) {
					continue;
				}
				else {
					sum += val; 
				}
			}
			
			if(sum == 0.0) {
				newValue = 0.0;
			}
			else if(sum == n) {
				newValue = 1.0;
			}
			else if(sum > n/2.0) {
				newValue = 0.75;
			}
			else if(sum < n/2.0) {
				newValue = 0.25;
			}
			else if(sum == n/2.0) {
				newValue = 0.5;
			}
			
//			System.err.println(test + " -> " + newValue);
			
			returnArray[dim] = newValue;
			
			
		}	
		
//		
//		String test = "00001111";
//		String splitArray[] = test.split("");
//		
//		for(int i = 0; i < splitArray.length; i++) {
//			System.err.println(splitArray[i]);
//		}
		
		return returnArray;
		
	}
	
}	
