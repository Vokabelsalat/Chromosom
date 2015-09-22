/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jakob
 */
public class HeatNukleosomGrid extends GridPane{
    
    HeatReader hr; 
    HashMap<String,ArrayList<ArrayList<Double>>> timeMap;
    
    public HeatNukleosomGrid(HeatReader hr, String timeStep) {
        this.hr = hr;
        this.timeMap = hr.timeMap;
        
        ArrayList<ArrayList<Double>> enzymeList = timeMap.get(timeStep);
        
        for(int enzyme = 0; enzyme < enzymeList.size(); enzyme++) {
            ArrayList<Double> nukleosomList = enzymeList.get(enzyme);
            for(int nukleosom = 0; nukleosom < nukleosomList.size(); nukleosom++) {
                add(new HeatNukleosom(nukleosomList.get(nukleosom), 7, 7, false, false), nukleosom, enzyme);
            }
        }
    }
    
}
