/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Jakob
 */
public class HeatNukleosomGrid extends GridPane{
    
    private HashMap<String,ArrayList<ArrayList<Double>>> timeMap;
    private HashMap<String, int[]> hitMap;
    private BorderPane parent;
    private HeatNukleosom leftNode = null;
    private HeatNukleosom rightNode = null;
    private HeatReader hr;
    private ArrayList<HeatNukleosom> highlightedList;
    private boolean both;
    private HeatProject project;
    public String timeStep;
     
    public HeatNukleosomGrid(HeatProject project, String timeStep, boolean both) {
        this.project = project;
        this.both = both;
        this.hr = project.getHeatReader();
        this.timeMap = hr.getTimeMap();
        this.hitMap = hr.getHitMap();
        this.parent = project.getBorderPane();
        this.timeStep = timeStep;
        
        int width = project.HeatNukleosomWidth;
       
        if(timeMap.containsKey(timeStep) && timeMap.get(timeStep) == null) {
            hr.readLogFile(timeStep);
        }
        
        ArrayList<ArrayList<Double>> enzymeList = timeMap.get(timeStep);
        
        for(int enzyme = 0; enzyme < enzymeList.size(); enzyme++) {
            ArrayList<Double> nukleosomList = enzymeList.get(enzyme);
            for(int nukleosom = 0; nukleosom < nukleosomList.size(); nukleosom++) {
                HeatNukleosom heatNukl;
                
                if(enzyme == hitMap.get(timeStep)[1] && nukleosom == hitMap.get(timeStep)[0]) {
                    heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, width, "BOTH");
                    project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(heatNukl, 1, 1);
                }
                else if(enzyme == hitMap.get(timeStep)[1]) {
                    heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, width, "HORIZONTAL");
                }
                else if(nukleosom == hitMap.get(timeStep)[0]) {
                    heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, width, "VERTICAL");
                }
                else {
                    heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, width, "");
                }
                
                heatNukl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                            for(HeatProject pro :project.getChromosom().projectList) {
                                HeatNukleosom nop = heatNukl;
                                if(pro != project && both == true) {
                                    nop = pro.getHeatGrid().findNukleosom(heatNukl.x, heatNukl.y);
                                }
                                pro.getHeatGrid().resetAndStrokeNukleosom(nop, pro.getHeatGrid().leftNode, true, HeatProject.RED);
                            }
                        }
                        if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                            for(HeatProject pro :project.getChromosom().projectList) {
                                HeatNukleosom nop = heatNukl;
                                if(pro != project  && both == true) {
                                    nop = pro.getHeatGrid().findNukleosom(heatNukl.x, heatNukl.y);
                                }
                                pro.getHeatGrid().resetAndStrokeNukleosom(nop, pro.getHeatGrid().rightNode, false, HeatProject.GREEN);
                            }
                        }
                    }
                });
                
                add(heatNukl, nukleosom, enzyme);
            }
        }
        
//        setStyle("-fx-border: 3px solid; -fx-border-color: black;");
        
    }
    
    public void highlightNear(double value, double range) {
        
        resetHighlightedNukl();
        
        for(Node nod : this.getChildren()) {
            if(nod instanceof HeatChromosom.HeatNukleosom ) {
                HeatNukleosom nukl = (HeatNukleosom)nod;
                if(nukl.value >= value - range && nukl.value <= value + range) {
//                    nukl.setStrokeColor(Color.rgb(255, 0, 255));
//                    nukl.setStrokeWidth(2.3);
                    
                    if(nukl.value != 0.0) {
                        nukl.highlight();
                        getHighlightedList().add(nukl);
                    }
                }
            }
        }
        
        for(Node nod : this.getChildren()) {
            if(nod instanceof HeatChromosom.HeatNukleosom ) {
                HeatNukleosom nukl = (HeatNukleosom)nod;
                if(!highlightedList.contains(nukl) && !highlightedList.isEmpty()) {
                    nukl.setOpacity(0.35);
                }
            }
        }        
    }
    
    public void resetHighlightedNukl() {
        
        for(Node nod : this.getChildren()) {
            if(nod instanceof HeatChromosom.HeatNukleosom ) {
                HeatNukleosom nukl = (HeatNukleosom)nod;
                nukl.setOpacity(1.0); 
                nukl.getChildren().remove(nukl.highlightRect);
            }
        }        
        
        if(getHighlightedList() != null) {
            for(HeatNukleosom nukl : getHighlightedList()) {
                nukl.deHighlight();
            }
        }
        
        setHighlightedList(new ArrayList<>());
    }
    
    private void resetAndStrokeNukleosom(HeatNukleosom newNukl, HeatNukleosom oldNukl, boolean left, Color color) {
        
        if(left == true) {
            if(leftNode == newNukl) {
                newNukl.deselect();
                leftNode = null;
            }
            else {
                newNukl.select(project.RED);
                if(oldNukl != null) {
                    oldNukl.deselect();
                }
                leftNode = newNukl;
            }
            if(rightNode != null && leftNode != rightNode) {
                rightNode.select(project.GREEN);
            }
        }
        else {
            if(rightNode == newNukl) {
                newNukl.deselect();
                rightNode = null;
            }
            else {
                newNukl.select(HeatProject.GREEN);
                if(oldNukl != null) {
                    oldNukl.deselect();
                }
                rightNode = newNukl;
            }
            if(leftNode != null  && leftNode != rightNode) {
                leftNode.select(HeatProject.RED);
            }
        }
        
        project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(leftNode, 1, project.getHeatOptionsPanel().getStartRow());
        project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(rightNode, 2, project.getHeatOptionsPanel().getStartRow());
     }
    
    public HeatNukleosom findNukleosom(int x, int y) {
        HeatNukleosom returnNukl = null;
        for(Node node : getChildren()) {
            if(getRowIndex(node) == y && getColumnIndex(node) == x) {
                if(node instanceof HeatChromosom.HeatNukleosom) {
                    returnNukl = (HeatNukleosom)node;
                    break;
                }
            }
        }
        return returnNukl;
    }
    
    private void setLeftNode(HeatNukleosom heatNukl) {
        leftNode = heatNukl;
   }
     
    private void setRightNode(HeatNukleosom heatNukl) {
        rightNode = heatNukl;
   }    

    /**
     * @return the highlightedList
     */
    public ArrayList<HeatNukleosom> getHighlightedList() {
        if(highlightedList == null) {
            highlightedList = new ArrayList<HeatNukleosom>();
        }
        return highlightedList;
    }

    /**
     * @param highlightedList the highlightedList to set
     */
    public void setHighlightedList(ArrayList<HeatNukleosom> highlightedList) {
        this.highlightedList = highlightedList;
    }
    
    public String getTimeStep() {
        return timeStep;
    }

}
