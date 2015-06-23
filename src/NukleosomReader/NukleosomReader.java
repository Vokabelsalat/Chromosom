package NukleosomReader;

import application.ChromosomProject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class NukleosomReader {
    
    public static int zaehler = 0;
    
        ChromosomProject project;
    
        public NukleosomReader(ChromosomProject project) {
            this.project = project;
        }
    
        public void openFile(String fileName) {
            
//            Die weiche nach dme Format in diese Methode. Der eigentlich Export in andere einzelne Methoden. Dabei die Streams alle in der Try öffnen, damit sie auch wieder geschlossen werden. 
//                    StringBuffer benutzen anstatt von Strings.
//            SPLIT FÜR DAS EINLESEN VERWENDEN; DA DIEs besonders schnell ist.
                    
            String format = fileName.substring(fileName.lastIndexOf(".")+1);

            if(format.equals("gz")) {
                readGZip(new File(fileName));
            }
            else if(format.equals("zip")) {
                readZip(new File(fileName));
            }
            else if(format.equals("bz2")) {
                readBZip2(new File(fileName));
            }
            else if(format.equals("txt")) {
                readTxt(new File(fileName));
            }
        }
        
        public void readZip(File inFile) {
            try(ZipInputStream zipStream = new ZipInputStream(new FileInputStream(inFile));
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(zipStream))   ){
                
                        ZipEntry zipEntry = null;
                        
                        if(null == (zipEntry = zipStream.getNextEntry())) {
                            
                        }
                
                        fillDataVectors(fileReader);
                        
            } catch (IOException ex) {
                System.err.println("Datei " + inFile.getName() + " konnte nicht gelesen werden.");
            }
        }
        
        public void readGZip(File inFile) {
            try( GZIPInputStream in = new GZIPInputStream(new FileInputStream(new File("hallo")));
                 BufferedReader fileReader = new BufferedReader(new InputStreamReader(in))){
                
                fillDataVectors(fileReader);
                
            } catch (IOException ex) {
                System.err.println("Datei " + inFile.getName() + " konnte nicht gelesen werden.");
            }
        }
        
        public void readBZip2(File inFile) {
            try( 
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));
                BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(bzIn))){
                
                fillDataVectors(fileReader);
                
            } catch (IOException ex) {
                System.err.println("Datei " + inFile.getName() + " konnte nicht gelesen werden.");
            }           
        }
        
        public void readTxt(File inFile) {
            try(    
                FileInputStream fin = new FileInputStream(inFile);
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(fin))){  
                
                fillDataVectors(fileReader);
                
            } catch (IOException ex) {  
                System.err.println("Datei " + inFile.getName() + " konnte nicht gelesen werden.");
            }  
        }
        
        public void fillDataVectors(BufferedReader br) {
                
        try {
            //Hier muss die vordefinierte Attributmenge eingelesen werden
            HashMap<String,Integer> attributeMap;// = new HashMap<String, Integer>();
            HashMap<String,HashMap<String,Integer>> histonMap;// = new HashMap<String, HashMap<String,Integer>>();
            ArrayList <HashMap<String,HashMap<String,Integer>>> nukleosomList = new ArrayList<HashMap<String,HashMap<String,Integer>>>();
            Vector<ArrayList< HashMap<String,HashMap<String,Integer>>>> timeVector = new Vector<ArrayList< HashMap<String,HashMap<String,Integer>>>>();
            
            String line = "";
            
            while((line = br.readLine()) != null){
                
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

                histonMap = new HashMap<String, HashMap<String,Integer>>();
                
                if(line.contains("H")) {
                    String splitArray[] = line.split("H");

                    for(int i = 1; i < splitArray.length; i++) {

                        String histoneNumber = "3";
                        String attributeString = "0";
                        int value = 0;

                        if(splitArray[i].contains("\\[")); {
                            String split[] = splitArray[i].split("\\[");
                            histoneNumber = split[0];
                            String splitAttribute[] = split[1].split("\\.");
                            attributeString = splitAttribute[0];
                            String splitValue[] = splitAttribute[1].split("\\]");
                            value = Integer.parseInt(splitValue[0]);
                        }

                        attributeMap = new HashMap<String, Integer>();

                        attributeMap.put(attributeString, value);

                        histonMap.put(histoneNumber, attributeMap);

                        nukleosomList.add(histonMap);                    

                    }
                }
                else {
                        attributeMap = new HashMap<String, Integer>();

                        attributeMap.put("0", 0);

                        histonMap.put("3",attributeMap);

                        nukleosomList.add(histonMap);
                }
            }
            
            project.setTimeVector(timeVector);
            
        } catch (IOException ex) {
            Logger.getLogger(NukleosomReader.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        }
        
        public static void main(String args[]) {
//            ChromosomProject project = new ChromosomProject();
//            NukleosomReader.fillDataVectors(project);
////            System.err.println("LOL: " + project.getTimeVector().get(1).get(1).get("3"));
//           
        }
        
        
	
}	
