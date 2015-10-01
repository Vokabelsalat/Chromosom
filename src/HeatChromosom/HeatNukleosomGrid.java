/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jakob
 */
public class HeatNukleosomGrid extends GridPane{
    
    private HashMap<String,ArrayList<ArrayList<Double>>> timeMap;
    private HashMap<String, int[]> hitMap;
    private int width = 8, height = 8;
    private BorderPane parent;
    private HeatNukleosom leftNode = null;
    private HeatNukleosom rightNode = null;
    private HeatReader hr;
    private ArrayList<HeatNukleosom> highlightedList;
    private boolean both;
    private HeatProject project;
     
    public HeatNukleosomGrid(HeatProject project, String timeStep, boolean both) {
        this.project = project;
        this.both = both;
        this.hr = project.getHeatReader();
        this.timeMap = hr.getTimeMap();
        this.hitMap = hr.getHitMap();
        this.parent = project.getBorderPane();
       
//        ArrayList<Node> nodeList = new ArrayList<>();
        
        if(timeMap.containsKey(timeStep) && timeMap.get(timeStep) == null) {
            hr.readLogFile(timeStep);
        }
        
        ArrayList<ArrayList<Double>> enzymeList = timeMap.get(timeStep);
        
        
        for(int enzyme = 0; enzyme < enzymeList.size(); enzyme++) {
            ArrayList<Double> nukleosomList = enzymeList.get(enzyme);
            for(int nukleosom = 0; nukleosom < nukleosomList.size(); nukleosom++) {
                HeatNukleosom heatNukl;
                
                if(enzyme == hitMap.get(timeStep)[1] && nukleosom == hitMap.get(timeStep)[0]) {
                    heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, height, false, false, "BOTH");
                    project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(heatNukl, 1, 1);
                }
                else if(enzyme == hitMap.get(timeStep)[1]) {
                    heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, height, false, false, "HORIZONTAL");
                }
                else if(nukleosom == hitMap.get(timeStep)[0]) {
                    heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, height, false, false, "VERTICAL");
                }
                else {
                    heatNukl = new HeatNukleosom(nukleosomList.get(nukleosom), nukleosom, enzyme, width, height, false, false, "");
                }
                
                heatNukl.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//                            project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(heatNukl, 1, project.getHeatOptionsPanel().getStartRow());
                            resetAndStrokeNukleosom(heatNukl, leftNode, true, HeatProject.RED, true);
                        }
                        if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
//                            project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(heatNukl, 2, project.getHeatOptionsPanel().getStartRow());
                            resetAndStrokeNukleosom(heatNukl, rightNode, false, HeatProject.GREEN, true);
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
                    
                    nukl.highlight();
                    
                    getHighlightedList().add(nukl);
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
    
    private void resetAndStrokeNukleosom(HeatNukleosom newNukl, HeatNukleosom oldNukl, boolean left, Color color, boolean first) {
        
        if(both == true && first == true) {
            for(HeatProject pro :project.getChromosom().projectArray) {
                if(pro != project) {
                    
                    HeatNukleosom nop = pro.getHeatGrid().findNukleosom(newNukl.x, newNukl.y);
                    
                    if(left == true) {
                        pro.getHeatGrid().resetAndStrokeNukleosom(nop, pro.getHeatGrid().leftNode, left, color, false);
                    }
                    else {
                        pro.getHeatGrid().resetAndStrokeNukleosom(nop, pro.getHeatGrid().rightNode, left, color, false);
                    }
                }
            }
        }
         if(oldNukl != null) {
             oldNukl.setStrokeColor(Color.GRAY);
             oldNukl.setStrokeWidth(oldNukl.oldStrokeWidth);
             oldNukl.highlightRect.setStrokeWidth(0.0);
         }

         newNukl.setStrokeColor(color);
         newNukl.setStrokeWidth(2.0);
         
         if(left == true) {
//            setLeftNode(newNukl);
            project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(newNukl, 1, project.getHeatOptionsPanel().getStartRow());
            leftNode = newNukl;
         }
         else {
//             setRightNode(newNukl);
            project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(newNukl, 2, project.getHeatOptionsPanel().getStartRow());
            rightNode = newNukl;
         }
        
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

//    void addHeatNukleosomToOptionPanel(HeatNukleosom nukl, int col, int row) {
//        
//        if(parent.getRight() instanceof javafx.scene.layout.VBox) {
//            VBox box = (VBox)parent.getRight();
//            
//            if(box.getChildren().get(row) instanceof javafx.scene.layout.GridPane) {
//                GridPane table = (GridPane)box.getChildren().get(row);
//                
//                ArrayList<Node> nodeList = new ArrayList<>();
//                
//                for(Node nod : table.getChildren()) {
//                    if(table.getColumnIndex(nod) == col) {
//                        nodeList.add(nod);
//                    }
//                }
//                
//                for(Node nod : nodeList) {
//                    table.getChildren().remove(nod);
//                }
//                
//                StackPane pane = new StackPane();
//                
//                pane.setPrefSize(28, 28);
//                pane.setAlignment(Pos.CENTER_LEFT);
//                
//                Color color = Color.GRAY;
//
//                if(col == 1 && row >= startRow) {
//                    color = HeatProject.RED;
//                } 
//                else if(col > 1) {
//                    color = HeatProject.GREEN;
//                }
//
//                Rectangle bg = new Rectangle(28,28,color);
//                bg.setTranslateX(0);
//                bg.setTranslateY(0);
//
//
//                pane.getChildren().add(bg);
//
//                Rectangle fg = new Rectangle(24, 24, Color.WHITE);
//                fg.setTranslateX(2);
////                fg.setTranslateY(1);
//
//                pane.getChildren().add(fg); 
//                
//                HeatNukleosom newNukl = new HeatNukleosom(nukl.value, nukl.x, nukl.y, 18, 18, false, false, "");
//                newNukl.setTranslateX(5);
//                newNukl.setTranslateY(5);
//                
//                pane.getChildren().add(newNukl);
//                
//                table.add(pane, col, 0);
//                table.add(new Label(String.valueOf(newNukl.value).replace(".", ",")), col, 1);
//               
//                //Nukleosom
//                table.add(new Label(String.valueOf(nukl.x)), col, 2);
//                
//                //Für die Action
////                int y = (nukl.y/2);
//                
//                //Enzyme
//                table.add(new Label(String.valueOf(hr.getChannelList().get(nukl.y))), col, 3);
//                
//                //Channel
//                table.add(new Label(String.valueOf(nukl.y)), col, 4);
//                
////                //Action
////                String action;
////                if(nukl.y%2 == 0) {
////                    action = "activation";
////                }
////                else {
////                    action = "deactivation";
////                }
////                
////                table.add(new Label(action), col, 5);
//            }
//        }
//    }
    
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
        return highlightedList;
    }

    /**
     * @param highlightedList the highlightedList to set
     */
    public void setHighlightedList(ArrayList<HeatNukleosom> highlightedList) {
        this.highlightedList = highlightedList;
    }

}
