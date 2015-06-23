package NukleosomReader;

import application.ChromosomProject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class NukleosomReader {
    
    public static int zaehler = 0;
    
    
        public static void openFile(ChromosomProject project, String fileName) {
//		InputStream decodeStream = null;
//		ZipInputStream zis = null;
//                File file = new File(fileName);
//
//		try {
//
//			decodeStream = new FileInputStream(file);
//			zis = new ZipInputStream(decodeStream);
//			ZipEntry zipEntry = null;
//
//			while (null != (zipEntry = zis.getNextEntry())) {
//
//				String curTableName = zipEntry.getName();
//                                        
//                                System.err.println(curTableName);
//                                
//                                project.setFileLines(readFileInLines(zis));
//			}
//
////			zis.close();
////			decodeStream.close();
//		} catch (FileNotFoundException e) {
//		} catch (Exception e) {
//		} 
//                finally {
//			try {
//				zis.close();
//			} catch (Exception e) {
//			}
//			try {
//				decodeStream.close();
//			} catch (Exception e) {
//			}
//		}
            
            
                try {
                     // Open the compressed file
//                     String source = "sourcename.gzip";
                    
                    String format = fileName.substring(fileName.lastIndexOf(".")+1);
                    
                    InputStreamReader inst = new InputStreamReader(System.in);
                    
                    if(format.equals("gz")) {
                        GZIPInputStream in = new GZIPInputStream(new FileInputStream(new File(fileName)));
                        inst = new InputStreamReader(in);
                    }
                    else if(format.equals("zip")) {
                        
			ZipInputStream zipStream = new ZipInputStream(new FileInputStream(new File(fileName)));
                        inst = new InputStreamReader(zipStream);
                        
                        ZipEntry zipEntry = null;
                        
                        if(null == (zipEntry = zipStream.getNextEntry())) {
                            
                        }
                    }
                    else if(format.equals("bz2")) {
                        FileInputStream fin = new FileInputStream(fileName);
                        BufferedInputStream in = new BufferedInputStream(fin);
                        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
                        inst = new InputStreamReader(bzIn);
                    }
                    else if(format.equals("txt")) {
                        FileInputStream fin = new FileInputStream(fileName);
                        inst = new InputStreamReader(fin);
                    }
                     
                     BufferedReader br = new BufferedReader(inst);
                     String line = "";
                     
                     ArrayList<String> linesList = new ArrayList<String>();
                     
                     while((line = br.readLine()) != null) {
                         linesList.add(line);
                     }
                     
                     project.setFileLines(linesList);

                     // Close the file and stream
                     inst.close();
//                     out.close();
                 } catch (IOException e) {
                 }
        }
	
	public static ArrayList<String> readFileInLines(InputStream in) {
		String line = "";
//		String fileName = "ES_segmentation_wholedata.data";
		
		//Die ArrayList die zurückgegeben werden soll
		ArrayList<String> returnList = new ArrayList<String>();
	
		try {
			//Filereader um aus der Datei lesen zu können
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(in));
			//Solange noch Eintraege vorhanden sind, sollen diese in die Liste aufgenommen werden
			while((line = fileReader.readLine()) != null){
                            returnList.add(line);
			}
			
			fileReader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
                
		return returnList;
        }
        
        public static void fillDataVectors(ChromosomProject project) {
                
                //Hier muss die vordefinierte Attributmenge eingelesen werden
            	HashMap<String,Integer> attributeMap;// = new HashMap<String, Integer>();
                HashMap<String,HashMap<String,Integer>> histonMap;// = new HashMap<String, HashMap<String,Integer>>();
                ArrayList <HashMap<String,HashMap<String,Integer>>> nukleosomList = new ArrayList<HashMap<String,HashMap<String,Integer>>>();
                Vector<ArrayList< HashMap<String,HashMap<String,Integer>>>> timeVector = new Vector<ArrayList< HashMap<String,HashMap<String,Integer>>>>();
                int count = 0;
                for(String line : project.getFileLines()) {
                    
//                    count++;
//                    System.err.println(count);
                    //Den Beginn einen neuen Zeitschritts einleiten
                    if(line.contains(">")) {
                        if(nukleosomList.size() != 0)
                            timeVector.add(nukleosomList);
                        nukleosomList = new ArrayList< HashMap<String,HashMap<String,Integer>>>();
                        continue;
                    }
                    //Leere Zeilen abfangen
                    if(line.equals("")) {
                        continue;
                    }
//                    System.err.println(line);
                    histonMap = new HashMap<String, HashMap<String,Integer>>();
                    
                    line = line.replaceAll("\\}", "");
                    line = line.replaceAll("\\{", "");
                    
                    String[] modArray = line.split("H");
                    
                    if(line.equals("")) {
        
                            attributeMap = new HashMap<String, Integer>();
                            
                            attributeMap.put("0", 0);
                            
                            histonMap.put("3",attributeMap);
                            
                            nukleosomList.add(histonMap);
                    } 
                    else {
                    
                        for(String nuklString : modArray) {

                            if(!nuklString.equals("")) {
                                String histoneNumber = nuklString.substring(0, 1);

                                if(histonMap.containsKey(histoneNumber)) {
                                    attributeMap = histonMap.get(histoneNumber);
                                }
                                else {
                                    attributeMap = new HashMap<String, Integer>();
                                }

                                String attributeString = nuklString.substring(nuklString.indexOf("[")+1, nuklString.lastIndexOf("]"));

                                String splittedAttribute[] = attributeString.split("\\.");

    //                            attributeArray[Integer.parseInt(splittedAttribute[0])-1] = Integer.parseInt(splittedAttribute[1]);

                                attributeMap.put(splittedAttribute[0], Integer.parseInt(splittedAttribute[1]));

    //                            histonList.add(histoneNumber, attributeArray);
                                histonMap.put(histoneNumber,attributeMap);

                                nukleosomList.add(histonMap);
                            }

                        }
                    }
                    
                }
                
                project.setTimeVector(timeVector);
                
//                for(ArrayList <HashMap<String,HashMap<String,Integer>>> nuklList : timeVector) {
//                    System.out.print("JOP:");
//                    for(HashMap<String,HashMap<String,Integer>> histMap : nuklList) {
//                        System.out.print(histMap);
//                    }
//                    System.out.println("");
//                }
                
//                System.err.print(timeVector.toString());
                
        }
        
        public static void main(String args[]) {
            ChromosomProject project = new ChromosomProject();
            NukleosomReader.fillDataVectors(project);
//            System.err.println("LOL: " + project.getTimeVector().get(1).get(1).get("3"));
           
        }
        
        
	
}	
