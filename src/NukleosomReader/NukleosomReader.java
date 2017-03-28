package NukleosomReader;

import application.ChromosomProject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NukleosomReader {

    public static int zaehler = 0;
    private HashMap<String, int[]> overlayMap;

    ChromosomProject project;

    public NukleosomReader(ChromosomProject project) {
        this.project = project;
        project.setNukleosomReader(this);
    }

    public void openFile(File file) {
        
        String fileName = file.getName();
        
//            Die weiche nach dme Format in diese Methode. Der eigentlich Export in andere einzelne Methoden. Dabei die Streams alle in der Try öffnen, damit sie auch wieder geschlossen werden. 
//                    StringBuffer benutzen anstatt von Strings.
//            SPLIT FÜR DAS EINLESEN VERWENDEN; DA DIEs besonders schnell ist.
        String format = fileName.substring(fileName.lastIndexOf(".") + 1);

//            if(format.equals("gz")) {
//                readGZip(new File(fileName));
//            }
//            else if(format.equals("zip")) {
//                readZip(new File(fileName));
//            }
//            else if(format.equals("bz2")) {
//                readBZip2(new File(fileName));
//            }
//            else if ...
        if (format.equals("txt")) {
            readTxt(file);
        }
    }

//        public void readZip(File inFile) {
//            try(ZipInputStream zipStream = new ZipInputStream(new FileInputStream(inFile));
//                BufferedReader fileReader = new BufferedReader(new InputStreamReader(zipStream))   ){
//                
//                        ZipEntry zipEntry = null;
//                        
//                        if(null == (zipEntry = zipStream.getNextEntry())) {
//                            
//                        }
//                
//                        fillDataVectors(fileReader);
//                        
//            } catch (IOException ex) {
//                System.err.println("Datei " + inFile.getName() + " konnte nicht gelesen werden.");
//            }
//        }
//        
//        public void readGZip(File inFile) {
//            try( GZIPInputStream in = new GZIPInputStream(new FileInputStream(new File("hallo")));
//                 BufferedReader fileReader = new BufferedReader(new InputStreamReader(in))){
//                
//                fillDataVectors(fileReader);
//                
//            } catch (IOException ex) {
//                System.err.println("Datei " + inFile.getName() + " konnte nicht gelesen werden.");
//            }
//        }
//        
//        public void readBZip2(File inFile) {
//            try( 
//                BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));
//                BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
//                BufferedReader fileReader = new BufferedReader(new InputStreamReader(bzIn))){
//                
//                fillDataVectors(fileReader);
//                
//            } catch (IOException ex) {
//                System.err.println("Datei " + inFile.getName() + " konnte nicht gelesen werden.");
//            }           
//        }
    public void readTxt(File inFile) {
        try (
                FileInputStream fin = new FileInputStream(inFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fin));) {

            fillDataVectors(br);

        } catch (IOException ex) {
//            ex.printStackTrace(System.err);
            System.err.println("Can't read " + inFile.getName() + "."); 
        }
    }
/*
    public void fillDataVectors(FileInputStream f) {
        try {

//        FileInputStream f = new FileInputStream("run2_state.txt");
            FileChannel ch = f.getChannel();
            ByteBuffer bb = ByteBuffer.allocateDirect(1024);
            byte[] barray = new byte[1024];
            int nRead, nGet;

            String str = "";
            String strArray[] = {"", ""};
            String line = "";
            
            //Hier muss die vordefinierte Attributmenge eingelesen werden
            HashMap<String, Integer> attributeMap;// = new HashMap<String, Integer>();
            HashMap<String, HashMap<String, Integer>> histonMap;// = new HashMap<String, HashMap<String,Integer>>();
            ArrayList<HashMap<String, HashMap<String, Integer>>> nukleosomList = new ArrayList<HashMap<String, HashMap<String, Integer>>>();
            Vector<ArrayList< HashMap<String, HashMap<String, Integer>>>> timeVector = new Vector<ArrayList< HashMap<String, HashMap<String, Integer>>>>();

            while ((nRead = ch.read(bb)) != -1) {
                if (nRead == 0) {
                    continue;
                }
                bb.position(0);
                bb.limit(nRead);
                while (bb.hasRemaining()) {
                    nGet = Math.min(bb.remaining(), 1024);
                    bb.get(barray, 0, nGet);
                    str += new String(barray, 0, nGet);

                    strArray = str.split("\n");

                    for (int lineNumber = 0; lineNumber < strArray.length - 1; lineNumber++) {

                        line = strArray[lineNumber];
//                        System.err.println(line);
                        //Den Beginn einen neuen Zeitschritts einleiten
                        if (line.contains(">")) {
                            if (nukleosomList.size() != 0) {
                                timeVector.add(nukleosomList);
                            }
                            nukleosomList = new ArrayList<HashMap<String, HashMap<String, Integer>>>();
                            continue;
                        }
                        //Leere Zeilen abfangen
                        if (line.equals("")) {
                            continue;
                        }

                        histonMap = new HashMap<String, HashMap<String, Integer>>();

                        if (line.contains("H")) {
                            String splitArray[] = line.split("H");

                            attributeMap = null;

                            for (int i = 1; i < splitArray.length; i++) {

                                String histoneNumber = "3";
                                String attributeString = "0";
                                int value = 0;

                                if (splitArray[i].contains("\\["));
                                {
                                    String split[] = splitArray[i].split("\\[");
                                    histoneNumber = split[0];

                                    if (histonMap.containsKey(histoneNumber)) {
                                        attributeMap = histonMap.get(histoneNumber);
                                    } else {
                                        attributeMap = new HashMap<String, Integer>();
                                    }

                                    String splitAttribute[] = split[1].split("\\.");
                                    attributeString = splitAttribute[0];
                                    String splitValue[] = splitAttribute[1].split("\\]");
                                    value = Integer.parseInt(splitValue[0]);
                                }

                                attributeMap.put(attributeString, value);

                                histonMap.put(histoneNumber, attributeMap);
                            }
                        } else {
                            attributeMap = new HashMap<String, Integer>();

                            attributeMap.put("0", 0);

                            histonMap.put("3", attributeMap);

                        }

                        nukleosomList.add(histonMap);

                    }//FORSCHLEIFE(LINENUMBER)                        

                    //Um Zeilenbrüche die durch den split verloren gehen, wieder einzufügen
                    if (str.endsWith("\n")) {
                        str = strArray[strArray.length - 1] + "\n";
                    } else {
                        str = strArray[strArray.length - 1];
                    }

                    if (!nukleosomList.isEmpty()) {
                        timeVector.add(nukleosomList);
                    }
                }//WHILESCHLEIFE

                bb.clear();
            }

            //Um den letzten Zeitschritt noch hinzuzufügen
            if (nukleosomList.size() != 0) {
                timeVector.add(nukleosomList);
            }

            project.setTimeVector(timeVector);

            bb.clear();
            ch.close();

        } catch (IOException ex) {
            Logger.getLogger(NukleosomReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
*/

    public void fillDataVectors(BufferedReader br) {

        try {
            //Hier muss die vordefinierte Attributmenge eingelesen werden
            HashMap<String, Integer> attributeMap;// = new HashMap<String, Integer>();
            HashMap<String, HashMap<String, String>> histonMap;// = new HashMap<String, HashMap<String,Integer>>();
            HashMap<String, HashMap<String, HashMap<String, String>>> nukleosomList =  new HashMap<>();
            HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>> timeVector = new HashMap<> ();
            HashMap<String, String[]> metaInformations = new HashMap<>();
            
            overlayMap = new HashMap<>();
//            HashMap<String, HashMap<String, HashMap<String, Integer>>> nukleosomList = new HashMap<>();
//            HashMap<String, HashMap<String, HashMap<String, HashMap<String, Integer>>>> timeVector = new HashMap<>();

//            int stepsNumber = 0;
            int nukleosomNumber = 0;
            String line = "";
            String stepNumberString = "";
            
//            LinkedHashMap<String, String[][]> histoneMap = project.getHistoneMap();
//            for(String his : histoneMap.keySet()) {
//               if(histoneMap.get(his) != null) {
//                   for(int innerY = 0; innerY < histoneMap.get(his)[0].length; innerY++) {
//                       for(int innerX = 0; innerX < histoneMap.get(his).length; innerX++) {
//                           if(!histoneMap.get(his)[innerX][innerY].isEmpty()) {
//                               empty_nucleosome += his + "[" + histoneMap.get(his)[innerX][innerY] + ".un]";
//                           }
//                       }
//                   }
//               }
//           }

           String empty_nucleosome = project.getEmpty_nucleosome();
           int[] overlayArray = null;
            
            while ((line = br.readLine()) != null) {
//                if(project.maxTimeSteps.peek() == 0 || timeVector.size() < project.maxTimeSteps.peek()-1)
                    //Leere Zeilen abfangen
//                    if (line.trim().equals("")) {
////                        continue;
//                            line = empty_nucleosome;
//                    }
                   
                    //Den Beginn einen neuen Zeitschritts einleiten
                    if (line.contains(">")) {
                        
                        String splt[] = line.replaceAll(">", "").split(" ");
                        stepNumberString = String.valueOf(Integer.parseInt(splt[0])-1);
                        
                        String[] meta = new String[4];
                        for(int i = 1; i < splt.length; i++) {
                            meta[i-1] = splt[i];
                            
//                            System.err.print(splt[i] + " ");
                        } 
                        
                        metaInformations.put(String.valueOf(Integer.parseInt(splt[0])), meta);

                        if (!nukleosomList.isEmpty()) {
                            getOverlayMap().put(String.valueOf((Integer.parseInt(stepNumberString))), overlayArray);
                            timeVector.put(stepNumberString, nukleosomList); 
                        }

                        nukleosomList = new HashMap<>();
                        nukleosomNumber = 0;
                        
                        continue;
                    }

//                    System.err.println(line);

                    if(line.contains(";")) {
                        String[] splitLine = line.split(";");
                        overlayArray = new int[splitLine.length];

                        for(String nucleosomeString : splitLine) {
                            String parseLine = nucleosomeString.split(":")[0].replace("{", "").replace("}", "");

                            if (parseLine.trim().equals("")) {
    //                        continue;
                                parseLine = empty_nucleosome;
                            }

                            if(nucleosomeString.contains(":")) {
                                int enzymeValue = Integer.parseInt(nucleosomeString.split(":")[1]);
                                overlayArray[nukleosomNumber] = enzymeValue;
                            }

                            histonMap = parseLine(parseLine);
                            nukleosomList.put(String.valueOf(nukleosomNumber), histonMap);
                            nukleosomNumber++;
                        }
                    }
            }
            
            //Um den letzten Zeitschritt noch hinzuzufügen
            if (!nukleosomList.isEmpty()) {
                getOverlayMap().put(String.valueOf((Integer.parseInt(stepNumberString))), overlayArray);
                timeVector.put(String.valueOf((Integer.parseInt(stepNumberString))), nukleosomList);
            }
            project.setTimeVector(timeVector);
            
            if(project.maxTimeSteps.peek() == 0) {
                project.maxTimeSteps.pop();
                project.maxTimeSteps.push(timeVector.size());
            }
            int stepSize = timeVector.size();
            int steps = timeVector.size();
            
            ArrayList<Integer> testList = new ArrayList<>();
            
            while(stepSize > project.stepsToShow.peek()) {
               stepSize = steps / project.stepsToShow.peek();
               if(stepSize > 1) {
                   testList.add(stepSize);
               }
               steps = stepSize;
            }
            
            testList.add(1);
            
            for(int i = (testList.size()-1); i >= 0; i--) {
                project.stepSize.push(testList.get(i));
            }
            
            project.setMetaInformations(metaInformations);
            
        } catch (IOException ex) {
            Logger.getLogger(NukleosomReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
//    public static  HashMap<String, HashMap<String, HashMap<String, String>>> parseCSVLine(String line) {
//        HashMap<String, HashMap<String, HashMap<String, String>>> nukleosomList = new HashMap<>();
//
//        for(String nucleosomeString : line.split(";")) {
//            
//        }
//
//        return nukleosomList;
//    }

    public static HashMap<String, HashMap<String, String>> parseLine(String line)  {
        
        HashMap<String, HashMap<String, String>> histonMap = null;
        HashMap<String, String> attributeMap;
        if (line.contains("H")) {
            histonMap = new HashMap<>();
            String splitArray[] = line.split("H");
            
            attributeMap = null;
            
            for (int i = 1; i < splitArray.length; i++) {
                
                String histoneNumber = "3";
                String attributeString = "0";
                String value = "";
                
                    if (splitArray[i].contains("\\["));
                    {
                        int openCount = line.split("\\[",-1).length-1;
                        int closeCount = line.split("\\]",-1).length-1;

                        if(openCount == closeCount) {

                            String split[] = splitArray[i].split("\\[");
                            histoneNumber = "H" + split[0];

                            if (histonMap.containsKey(histoneNumber)) {
                                attributeMap = histonMap.get(histoneNumber);
                            } else {
                                attributeMap = new HashMap<>();
                            }

                            String splitAttribute[] = split[1].split("\\.");
                            attributeString = splitAttribute[0];
                            String splitValue[] = splitAttribute[1].split("\\]");
                            value = splitValue[0];

                            attributeMap.put(attributeString, value);

                            histonMap.put(histoneNumber, attributeMap);
                    }
                    else {
                        return null;
                    }
                }
               
            }
        }
//        else {
//            attributeMap = new HashMap<>();
//            
//            attributeMap.put("0", "");
//            
//            histonMap.put("3", attributeMap);
//        }
        
        return histonMap;
    }

    public static void main(String args[]) {
//            ChromosomProject project = new ChromosomProject();
//            NukleosomReader.fillDataVectors(project);
////            System.err.println("LOL: " + project.getTimeVector().get(1).get(1).get("3"));
//           
    }

    /**
     * @return the overlayMap
     */
    public HashMap<String, int[]> getOverlayMap() {
        return overlayMap;
    }

    /**
     * @param overlayMap the overlayMap to set
     */
    public void setOverlayMap(HashMap<String, int[]> overlayMap) {
        this.overlayMap = overlayMap;
    }

}
