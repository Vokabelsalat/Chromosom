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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jakob
 */
public class HeatReader {
    
    private HashMap<String, ArrayList<ArrayList<Double>>> timeMap;
    private HashMap<String, int[]> hitMap;
    private ArrayList<Integer> channelList;
    
    double max;
    double min;
    
    public HeatReader() {
        
        timeMap = new HashMap<>();
        hitMap = new HashMap<>();
        channelList = new ArrayList<>();
        
        readChannelInfo("channelinfo.log");
        
//        readLogFile(fileName);
    }
    
    public void readChannelInfo(String fileName) {
        FileInputStream fin = null;
        BufferedReader br = null;
        
        int enzyme = 0;
        int channel = 0;
//        int rowNumber = 0;
        
        try {
            fin = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(fin));
            
            String line = "";
            
            while((line = br.readLine()) != null) {
                
                if(line.equals("")) {
                    
                }
                else if(line.contains("Enzyme")) {
                    if(line.contains(" ")) {
                        
                        String splitArray[] = line.split(" ");
                        enzyme = Integer.parseInt(splitArray[1]);
                    }
                }
                else if(line.contains("Channel")) {
                    if(line.contains(" ")) {
                        
                        String splitArray[] = line.split(" ");
                        
                        channel = Integer.parseInt(splitArray[1]);
                        
                        getChannelList().add(channel, enzyme);
//                        channel++;
                    }
                }
            }
            
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
    }
    
    public void searchForLogFiles(String path) {
        File[] files = new File(path).listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null. 

        for (File file : files) {
            if (file.isFile()) {
                addLogFile(file.getPath());
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
        String timeStep = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.indexOf("."));
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
            fin = new FileInputStream("logs/" + timeStep + ".csv");
            br = new BufferedReader(new InputStreamReader(fin));
            
            ArrayList<ArrayList<Double>> enzymeList = new ArrayList<>();
            ArrayList<Double> nukleosomList;
            
            String line = "";
            max = 0.0;
            min = 0.0;
            
            while ((line = br.readLine()) != null) {
                if(line.equals("")) {
                    
                }
                else if(line.contains(";")) {
                    
                    String strArray[] = line.split(";");
                    nukleosomList = new ArrayList<>();
                    double value;
                    for(int i = 0; i < strArray.length - 1; i++) {
                        value = Double.parseDouble(strArray[i]);
                        nukleosomList.add(i, value);
                        
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
            
//            for(ArrayList<ArrayList<Double>> entry : timeMap.values()) {
//                for(int enzyme = 0; enzyme < entry.size(); enzyme++) {
//                    for(int nukleosom = 0; nukleosom < entry.get(enzyme).size(); nukleosom++) {
//                        System.err.print(entry.get(enzyme).get(nukleosom) + ";");
//                    }
//                    System.err.println();
//                }
//            }
            
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

    /**
     * @return the channelList
     */
    public ArrayList<Integer> getChannelList() {
        return channelList;
    }

    /**
     * @param channelList the channelList to set
     */
    public void setChannelList(ArrayList<Integer> channelList) {
        this.channelList = channelList;
    }
    
}
