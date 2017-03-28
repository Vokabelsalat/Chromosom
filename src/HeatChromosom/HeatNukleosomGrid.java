/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jakob
 */
public class HeatNukleosomGrid extends GridPane{
    
//    private HashMap<String,ArrayList<ArrayList<Double>>> timeMap;
    private HashMap<String, int[]> hitMap;
    private BorderPane parent;
    private HeatNukleosom leftNode = null;
    private HeatNukleosom rightNode = null;
    private HeatReader hr;
    private ArrayList<HeatNukleosom> highlightedList;
    private HeatProject project;
    public String timeStep;
    private double paneHeight = 0;
    private ArrayList<HeatNukleosom> critHighlightedList;
     
    public HeatNukleosomGrid(HeatProject project, String timeStep) {
        this.project = project;
        this.hr = project.getHeatReader();
//        this.timeMap = hr.getTimeMap();
        this.hitMap = hr.getHitMap();
        this.parent = project.getBorderPane();
        this.timeStep = timeStep;
        
        int width = project.HeatNukleosomWidth;
       
        if(hr.getTimeMap().containsKey(timeStep) && hr.getTimeMap().get(timeStep) == null) {
            hr.readLogFile(timeStep);
        }
        
        ArrayList<ArrayList<Double>> enzymeList = hr.getTimeMap().get(timeStep);
        
        ArrayList<Integer> enzymmm = new ArrayList<>();
        
        ArrayList<SectionPane> catMap = project.getCategorized();
        
        int y = 0;

        for(SectionPane sect : catMap) {
            for(SectionPane innerSec : sect.getSectionList()) { 
                for(SectionPane innerstSect : innerSec.getSectionList()) {
                    if(!innerstSect.getSectionList().isEmpty()) {
                        continue;
                    }
                    for(CritPane critPane : innerstSect.getCritList()) {
                        for(int  enzyme : critPane.getChannelList()) {
                            if(!enzymmm.contains(enzyme)) {
                                ArrayList<Double> nukleosomList = enzymeList.get(enzyme);
                                for(int nukleosom = 0; nukleosom < nukleosomList.size(); nukleosom++) {
                                    HeatNukleosom heatNukl;

                                    double prob =  hr.getProbabilityTimeMap().get(timeStep).get(enzyme).get(nukleosom);
                                    double org = hr.getOriginalTimeMap().get(timeStep).get(enzyme).get(nukleosom);

                                    if(enzyme == hitMap.get(timeStep)[1] && nukleosom == hitMap.get(timeStep)[0]) {
                                        heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, width, "BOTH", prob, org);
                                        project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(heatNukl, 1, project.getHeatOptionsPanel().getStartRow());
                                    }
                                    else if(enzyme == hitMap.get(timeStep)[1]) {
                                        heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, width, "HORIZONTAL", prob, org);
                                    }
                                    else if(nukleosom == hitMap.get(timeStep)[0]) {
                                        heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, width, "VERTICAL", prob, org);
                                    }
                                    else {
                                        heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, width, "", prob, org);
                                    }

                                    heatNukl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                                                for(HeatProject pro :project.getChromosom().projectList) {
                                                    HeatNukleosom nop = heatNukl;

                                                    if(pro != project) {
                                                        if(project.isSameHeatMaps() == true) { 
                                                            nop = pro.getHeatGrid().findNukleosom(heatNukl.x, heatNukl.y);
                                                            pro.getHeatGrid().resetAndStrokeNukleosom(nop, pro.getHeatGrid().leftNode, true, HeatProject.RED);
                                                        }
                                                    }
                                                    else {
                                                        pro.getHeatGrid().resetAndStrokeNukleosom(nop, pro.getHeatGrid().leftNode, true, HeatProject.RED);
                                                    }
                                                }
                                            }
                                            if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                                                for(HeatProject pro :project.getChromosom().projectList) {
                                                    HeatNukleosom nop = heatNukl;
                                                    if(pro != project) {
                                                        if(project.isSameHeatMaps() == true) {
                                                            nop = pro.getHeatGrid().findNukleosom(heatNukl.x, heatNukl.y);
                                                            pro.getHeatGrid().resetAndStrokeNukleosom(nop, pro.getHeatGrid().rightNode, false, HeatProject.GREEN);
                                                        }
                                                    }
                                                    else {
                                                        pro.getHeatGrid().resetAndStrokeNukleosom(nop, pro.getHeatGrid().rightNode, false, HeatProject.GREEN);
                                                    }
                                                }
                                            }
                                        }
                                    });
                                    add(heatNukl, nukleosom, y);
                                    enzymmm.add(enzyme);
                                }
                                paneHeight += width;
                                y++;
                            }
                        }
                    }
                }
            }
        }           
        
        project.setPaneHeight(paneHeight);
        
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
        
        project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(leftNode, 2, project.getHeatOptionsPanel().getStartRow());
        project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(rightNode, 3, project.getHeatOptionsPanel().getStartRow());
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

    /**
     * @return the paneHeight
     */
    public double getPaneHeight() {
        return paneHeight;
    }

    void highlightCrit(
            ArrayList<Integer> channelList, boolean high) {
        resetCritHighlightedNukl();

            for(Node nod : this.getChildren()) {
                if(nod instanceof HeatChromosom.HeatNukleosom ) {
                    HeatNukleosom nukl = (HeatNukleosom)nod;
                    if(channelList.contains(nukl.y)) {
                        if(high == true) {
                            nukl.critHighlight();
                        }
                        else {
                            nukl.critDehighlight();
                        }
                    }
                }
            }

//        for(Node nod : this.getChildren()) {
//            if(nod instanceof HeatChromosom.HeatNukleosom ) {
//                HeatNukleosom nukl = (HeatNukleosom)nod;
//                if(!critHighlightedList.contains(nukl) && !critHighlightedList.isEmpty()) {
//                    nukl.setOpacity(0.35);
//                }
//            }
//        } 
    }

    private void resetCritHighlightedNukl() {
//        for(Node nod : this.getChildren()) {
//            if(nod instanceof HeatChromosom.HeatNukleosom ) {
//                HeatNukleosom nukl = (HeatNukleosom)nod;
//                nukl.setOpacity(1.0); 
//                nukl.getChildren().remove(nukl.highlightRect);
//            }
//        }        
//        
//        if(getHighlightedList() != null) {
//            for(HeatNukleosom nukl : getHighlightedList()) {
//                nukl.deHighlight();
//            }
//        }
//        
//        setHighlightedList(new ArrayList<>());
    }

    /**
     * @return the highlightedList
     */
    public ArrayList<HeatNukleosom> getCritHighlightedList() {
        if(critHighlightedList == null) {
            critHighlightedList = new ArrayList<HeatNukleosom>();
        }
        return critHighlightedList;
    }
    
    public void saveAsPNG() {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Modification File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        
        WritableImage image = this.snapshot(new SnapshotParameters(), null);
        
        // TODO: probably use a file chooser here
//        File file = new File("PNGExport.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }
}
