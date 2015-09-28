/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.io.BufferedReader;
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
    
    public HashMap<String, ArrayList<ArrayList<Double>>> timeMap;
    public HashMap<String, int[]> hitMap;
    public ArrayList<Integer> channelList;
    
    double max;
    double min;
    
    String timeStep;
    
    public HeatReader(String fileName) {
        
        timeMap = new HashMap<>();
        hitMap = new HashMap<>();
        channelList = new ArrayList<>();
        
        readChannelInfo("channelinfo.log");
        
        readLogFile(fileName);
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
                        
                        channelList.add(channel, enzyme);
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
    
    public void readLogFile(String fileName) {
        
        timeStep = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.indexOf("."));
        
        if(timeMap.containsKey(timeStep)) {
            return;
        }
        
        FileInputStream fin = null;
        BufferedReader br = null;
        
        try {
            fin = new FileInputStream(fileName);
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
                    
                    hitMap.put(timeStep, hitArray);
                }
            }
            
            timeMap.put(timeStep, enzymeList);
            
//            for(ArrayList<ArrayList<Double>> entry : timeMap.values()) {
//                for(int enzyme = 0; enzyme < entry.size(); enzyme++) {
//                    for(int nukleosom = 0; nukleosom < entry.get(enzyme).size(); nukleosom++) {
//                        System.err.print(entry.get(enzyme).get(nukleosom) + ";");
//                    }
//                    System.err.println();
//                }
//            }
            
            scaleValues();
            
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
    
    public void scaleValues() {
            ArrayList<ArrayList<Double>> entry = timeMap.get(timeStep); 
            
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
            
            timeMap.remove(timeStep);
            timeMap.put(timeStep, newEnzymeList);
    }
    
}
