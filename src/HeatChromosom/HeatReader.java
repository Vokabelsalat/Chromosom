/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jakob
 */
public class HeatReader {
    
    HeatProject heatProject;
    private HashMap<String, ArrayList<ArrayList<Double>>> timeMap;
    private HashMap<String, ArrayList<ArrayList<Double>>> originalTimeMap;
    private HashMap<String, ArrayList<ArrayList<Double>>> probabilityTimeMap;
    private final LinkedHashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> allSectionMap;
    private HashMap<String, int[]> hitMap;
    private String path;
    boolean first = true;
    double max;
    double min;
    
    double sum = 0;
    
    public HeatReader(HeatProject heatProject) {
        this.heatProject = heatProject;
        timeMap = new HashMap<>();
        originalTimeMap = new HashMap<>();
        probabilityTimeMap = new HashMap<>();
        hitMap = new HashMap<>();
        allSectionMap = new LinkedHashMap<>();
        
//        readChannelInfo("channelinfo.log");
    }
    
    public LinkedHashMap<String, ArrayList<Integer>> categorizeAssociation(ArrayList<Integer> parentList) {
        LinkedHashMap<String, ArrayList<Integer>> sectionMap = new LinkedHashMap<>();
        
        ArrayList<Integer> firstSection = new ArrayList<>();
        ArrayList<Integer> secondSection = new ArrayList<>();
        
        for(int channel : parentList) {
            if(channel%2 == 0) {
                firstSection.add(channel);
            }
            else {
                secondSection.add(channel);
            }
        }
        
        sectionMap.put("Association", firstSection);
        sectionMap.put("Dissociation", secondSection);        
//        addEnzymeSection("Association", firstSection);
//        addEnzymeSection("Dissociation", secondSection);
        
//        getAllSectionMap().put("Association", sectionMap);
        return sectionMap;
    }
    
    public LinkedHashMap<String, ArrayList<Integer>> categorizeEnzyme(ArrayList<Integer> parentList) {
        
        LinkedHashMap<String, ArrayList<Integer>> sectionList = new LinkedHashMap<>();
        
        ArrayList<Integer> section;
        for(int channel : parentList) {
            String enzyme = heatProject.getChromosom().getEditorRuleList().get(channel/2).getEnzymeName();
            if(sectionList.containsKey(enzyme)) {
                section = sectionList.get(enzyme);
            }
            else {
                section = new ArrayList<>();
            }
            
            section.add(channel);
            sectionList.put(enzyme, section);
        }
        
        return sectionList;
    }
    
    public LinkedHashMap<String, ArrayList<Integer>> categorizeByTagName(ArrayList<Integer> parentList, String tagName) {
        
        LinkedHashMap<String, ArrayList<Integer>> sectionList = new LinkedHashMap<>();
        
        ArrayList<Integer> section;
        for(int channel : parentList) {
            String tagValue = heatProject.getChromosom().getEditorRuleList().get(channel/2).getTagValue(tagName);
            
            if(sectionList.containsKey(tagValue)) {
                section = sectionList.get(tagValue);
            }
            else {
                section = new ArrayList<>();
            }
            
            section.add(channel);
            sectionList.put(tagValue, section);
        }
        
        return sectionList;
    }
    
    public LinkedHashMap<String, ArrayList<Integer>> categorizeProcess(ArrayList<Integer> parentList) {
        
        LinkedHashMap<String, ArrayList<Integer>> sectionList = new LinkedHashMap<>();
        
        ArrayList<Integer> section;
        for(int channel : parentList) {
            String process = heatProject.getChromosom().getEditorRuleList().get(channel).getTagValue("process");
            
            if(sectionList.containsKey(process)) {
                section = sectionList.get(process);
            }
            else {
                section = new ArrayList<>();
            }
            
            section.add(channel);
            sectionList.put(process, section);
        }
        
        return sectionList;
    }
    
    public LinkedHashMap<String, ArrayList<Integer>> categorizeSite(ArrayList<Integer> parentList) {
        
        LinkedHashMap<String, ArrayList<Integer>> sectionList = new LinkedHashMap<>();
        
        ArrayList<Integer> section;
        for(int channel : parentList) {
            
            String site = "";
            
            String target = heatProject.getChromosom().getEditorRuleList().get(channel/2).getTarget();
            String rule = heatProject.getChromosom().getEditorRuleList().get(channel/2).getRule();
            
            target = target.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\(", "").replaceAll("\\)", "");
            
            rule = rule.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\(", "").replaceAll("\\)", "");
            
            String targetSplit[] = target.split("H");
            String ruleSplit[] = rule.split("H");
            
            ArrayList<String> tar = new ArrayList(Arrays.asList(ruleSplit));
            
            String longArray[] = null;
            String shortArray[] = null;
            
            if(targetSplit.length > ruleSplit.length) {
                longArray = targetSplit;
                shortArray = ruleSplit;
            }
            else {
                longArray = ruleSplit;
                shortArray = targetSplit;
            }
            
            ArrayList<String> shortList = new ArrayList(Arrays.asList(shortArray));
            
            for(String longString : longArray) {
                if(!shortList.contains(longString)) {
                    if(longString.contains(".")) {
                        String targetStringSplit[] = longString.split("\\.");
                        site = "H" + targetStringSplit[0];
                        site = site.replaceAll("\\[", " ");
                    }
                }
                else {
                    shortList.remove(longString);
                }
            }
            
            if(sectionList.containsKey(site)) {
                section = sectionList.get(site);
            }
            else {
                section = new ArrayList<>();
            }
            
            section.add(channel);
            sectionList.put(site, section);
        }
        
        return sectionList;
    }
    
    public LinkedHashMap<String, ArrayList<Integer>> categorizeHistone(ArrayList<Integer> parentList) {
        
        LinkedHashMap<String, ArrayList<Integer>> sectionList = new LinkedHashMap<>();
        
        ArrayList<Integer> section;
        for(int channel : parentList) {
            
            String histone = "";
            
            String target = heatProject.getChromosom().getEditorRuleList().get(channel/2).getTarget();
            String rule = heatProject.getChromosom().getEditorRuleList().get(channel/2).getRule();
            
            target = target.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\(", "").replaceAll("\\)", "");
            
            rule = rule.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\(", "").replaceAll("\\)", "");
            
            String targetSplit[] = target.split("H");
            String ruleSplit[] = rule.split("H");
            
            String longArray[] = null;
            String shortArray[] = null;
            
            if(targetSplit.length > ruleSplit.length) {
                longArray = targetSplit;
                shortArray = ruleSplit;
            }
            else {
                longArray = ruleSplit;
                shortArray = targetSplit;
            }
            
            ArrayList<String> shortList = new ArrayList(Arrays.asList(shortArray));
            
            for(String longString : longArray) {
                if(!shortList.contains(longString)) {
                    histone = "H" + longString.substring(0, longString.indexOf("["));
                }
                else {
                    shortList.remove(longString);
                }
            }
            
            if(sectionList.containsKey(histone)) {
                section = sectionList.get(histone);
            }
            else {
                section = new ArrayList<>();
            }
            
            section.add(channel);
            sectionList.put(histone, section);
        }
        
        return sectionList;
    }
    
    public void searchForLogFiles(String path) {
        this.setPath(path);
        
        File[] files = new File(path).listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null. 
                
        if(files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    addLogFile(file.getAbsolutePath());
                }
            }
        }
    }
    
    public String getFirstItemInTimeMap() {
        String returnStr = "";
        for(int i = 0; i < 100000; i++) {
            returnStr = String.valueOf(i);
            if(getTimeMap().containsKey(returnStr) && getTimeMap() != null) {
                break;
            }
        }
        return returnStr;
    }
    
    public String getLastItemInTimeMap() {
        int max = 0;
        for(String key : timeMap.keySet()) {
            if(Integer.parseInt(key) > max) {
                max = Integer.parseInt(key);
            }
        }
        return String.valueOf(max);
    }
    
    public void addLogFile(String fileName) {
        String timeStep = fileName.substring(fileName.lastIndexOf(File.separator)+1, fileName.indexOf("."));
        String ending = fileName.substring(fileName.indexOf(".")+1);
        
        if(ending.equals("csv") || ending.equals("txt")) {
            if(getTimeMap().containsKey(timeStep)) {
                return;
            }

            getTimeMap().put(timeStep, null);
        }
    }
    
    public void readLogFile(String timeStep) {
        
        if(getTimeMap().containsKey(timeStep) && getTimeMap().get(timeStep) != null) {
            return;
        }
        
        FileInputStream fin = null;
        BufferedReader br = null;
        
        try {
            fin = new FileInputStream(getPath() + File.separator + timeStep + ".csv");
            br = new BufferedReader(new InputStreamReader(fin));
            
            ArrayList<ArrayList<Double>> enzymeList = new ArrayList<>();
            ArrayList<Double> nukleosomList;
            
            String line = "";
            max = 0.0;
            min = 0.0;
            sum = 0.0;
            
            while ((line = br.readLine()) != null) {
                if(line.equals("")) {
                    
                }
                else if(line.contains(";")) {
                    
                    String strArray[] = line.split(";");
                    nukleosomList = new ArrayList<>();
                    double value;
                    
                    for(int i = 0; i < strArray.length; i++) {
                        value = Double.parseDouble(strArray[i]);
                        nukleosomList.add(i, value);
                        sum += value;
                        
                        if(value > max) {
                            max = value;
                        }
                        if(value < min) {
                            min = value;
                        }
                    }
                    
                    if(!nukleosomList.isEmpty()) {
                        enzymeList.add(nukleosomList);
                    }
                    
                }
                else if(line.contains("|")) {
                    String strArray[] = line.split("\\|");
                    int hitArray[] = new int[strArray.length];
                    hitArray[0] = Integer.parseInt(strArray[0]);
                    hitArray[1] = Integer.parseInt(strArray[1]);
                    
                    getHitMap().put(timeStep, hitArray);
                }
            }
            
            getTimeMap().replace(timeStep, enzymeList);
            getOriginalTimeMap().put(timeStep, enzymeList);
            
            generateProbabilities(timeStep);
            scaleValues(timeStep);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HeatReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HeatReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(HeatReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println(timeStep + ".csv was added to timeMap");
    }
    
    public void scaleValues(String timeStep) {
        if( getTimeMap().get(timeStep) != null) {
            ArrayList<ArrayList<Double>> entry = getTimeMap().get(timeStep); 
            
            ArrayList<ArrayList<Double>> newEnzymeList = new ArrayList<>();
            ArrayList<Double> newNukleosomList;
            
            for(int enzyme = 0; enzyme < entry.size(); enzyme++) {
                newNukleosomList = new ArrayList<>();
                for(int nukleosom = 0; nukleosom < entry.get(enzyme).size(); nukleosom++) {
                    double newValue = (entry.get(enzyme).get(nukleosom) - min) / ( max - min);
                    
                    newNukleosomList.add(nukleosom, newValue);
                }
                
                newEnzymeList.add(enzyme, newNukleosomList);
            }
            
            getTimeMap().remove(timeStep);
            getTimeMap().put(timeStep, newEnzymeList);
        }
    }

    /**
     * @return the timeMap
     */
    public HashMap<String, ArrayList<ArrayList<Double>>> getTimeMap() {
        return timeMap;
    }

    /**
     * @param timeMap the timeMap to set
     */
    public void setTimeMap(HashMap<String, ArrayList<ArrayList<Double>>> timeMap) {
        this.timeMap = timeMap;
    }

    /**
     * @return the hitMap
     */
    public HashMap<String, int[]> getHitMap() {
        return hitMap;
    }

    /**
     * @param hitMap the hitMap to set
     */
    public void setHitMap(HashMap<String, int[]> hitMap) {
        this.hitMap = hitMap;
    }


    private void generateProbabilities(String timeStep) {
        if(getOriginalTimeMap().get(timeStep) != null) {
            ArrayList<ArrayList<Double>> entry = getOriginalTimeMap().get(timeStep); 
            
            ArrayList<ArrayList<Double>> newEnzymeList = new ArrayList<>();
            ArrayList<Double> newNukleosomList;
            
            for(int enzyme = 0; enzyme < entry.size(); enzyme++) {
                newNukleosomList = new ArrayList<>();
                for(int nukleosom = 0; nukleosom < entry.get(enzyme).size(); nukleosom++) {
                    double newValue = entry.get(enzyme).get(nukleosom);

                    newValue = newValue / sum;
                    
                    newNukleosomList.add(nukleosom, newValue);
                }
                
                newEnzymeList.add(enzyme, newNukleosomList);
            }
            
            getProbabilityTimeMap().put(timeStep, newEnzymeList);
        }
    }

    /**
     * @return the originalTimeMap
     */
    public HashMap<String, ArrayList<ArrayList<Double>>> getOriginalTimeMap() {
        return originalTimeMap;
    }

    /**
     * @param originalTimeMap the originalTimeMap to set
     */
    public void setOriginalTimeMap(HashMap<String, ArrayList<ArrayList<Double>>> originalTimeMap) {
        this.originalTimeMap = originalTimeMap;
    }

    /**
     * @return the probabilityTimeMap
     */
    public HashMap<String, ArrayList<ArrayList<Double>>> getProbabilityTimeMap() {
        return probabilityTimeMap;
    }

    /**
     * @param probabilityTimeMap the probabilityTimeMap to set
     */
    public void setProbabilityTimeMap(HashMap<String, ArrayList<ArrayList<Double>>> probabilityTimeMap) {
        this.probabilityTimeMap = probabilityTimeMap;
    }

    /**
     * @return the allSectionMap
     */
    public HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> getAllSectionMap() {
        return allSectionMap;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

}
