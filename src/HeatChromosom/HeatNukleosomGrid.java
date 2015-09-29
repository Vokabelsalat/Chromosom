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
    
    HeatReader hr; 
    HashMap<String,ArrayList<ArrayList<Double>>> timeMap;
    HashMap<String, int[]> hitMap;
    int width = 9, height = 9;
    BorderPane parent;
    HeatNukleosom leftNode = null;
    HeatNukleosom rightNode = null;
    int startRow = 4;
    ArrayList<HeatNukleosom> highlightedList;
     
    public HeatNukleosomGrid(BorderPane parent, HeatProject project, HeatReader hr, String timeStep) {
        this.hr = hr;
        this.timeMap = hr.timeMap;
        this.hitMap = hr.hitMap;
        this.parent = parent;
       
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
                     addHeatNukleosomToOptionPanel(heatNukl, 1, 1);
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
                            addHeatNukleosomToOptionPanel(heatNukl, 1, startRow);
                            resetAndHighlightNukleosom(heatNukl, leftNode, true, Color.rgb(255, 0, 0));
                        }
                        if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                            addHeatNukleosomToOptionPanel(heatNukl, 2, startRow);
                            resetAndHighlightNukleosom(heatNukl, rightNode, false, Color.rgb(0, 255, 0));
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
                    
                    highlightedList.add(nukl);
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
        
        if(highlightedList != null) {
            for(HeatNukleosom nukl : highlightedList) {
                nukl.deHighlight();
            }
        }
        
        highlightedList = new ArrayList<>();
    }
    
    private void resetAndHighlightNukleosom(HeatNukleosom newNukl, HeatNukleosom oldNukl, boolean left, Color color) {
        
         if(oldNukl != null) {
             oldNukl.setStrokeColor(Color.GRAY);
             oldNukl.setStrokeWidth(oldNukl.oldStrokeWidth);
         }

         newNukl.setStrokeColor(color);
         newNukl.setStrokeWidth(2.0);
         
         if(left == true) {
//            setLeftNode(newNukl);
            leftNode = newNukl;
         }
         else {
//             setRightNode(newNukl);
             rightNode = newNukl;
         }
        
     }

    void addHeatNukleosomToOptionPanel(HeatNukleosom nukl, int col, int row) {
        
        if(parent.getRight() instanceof javafx.scene.layout.VBox) {
            VBox box = (VBox)parent.getRight();
            
            if(box.getChildren().get(row) instanceof javafx.scene.layout.GridPane) {
                GridPane table = (GridPane)box.getChildren().get(row);
                
                ArrayList<Node> nodeList = new ArrayList<>();
                
                for(Node nod : table.getChildren()) {
                    if(table.getColumnIndex(nod) == col) {
                        nodeList.add(nod);
                    }
                }
                
                for(Node nod : nodeList) {
                    table.getChildren().remove(nod);
                }
                
                StackPane pane = new StackPane();
                
                pane.setPrefSize(28, 28);
                pane.setAlignment(Pos.CENTER_LEFT);
                
                Color color = Color.GRAY;

                if(col == 1 && row >= startRow) {
                    color = Color.rgb(255, 0, 0);
                } 
                else if(col > 1) {
                    color = Color.rgb(0, 255, 0);
                }

                Rectangle bg = new Rectangle(28,28,color);
                bg.setTranslateX(0);
                bg.setTranslateY(0);


                pane.getChildren().add(bg);

                Rectangle fg = new Rectangle(24, 24, Color.WHITE);
                fg.setTranslateX(2);
//                fg.setTranslateY(1);

                pane.getChildren().add(fg); 
                
                HeatNukleosom newNukl = new HeatNukleosom(nukl.value, nukl.x, nukl.y, 18, 18, false, false, "");
                newNukl.setTranslateX(5);
                newNukl.setTranslateY(5);
                
                pane.getChildren().add(newNukl);
                
                table.add(pane, col, 0);
                table.add(new Label(String.valueOf(newNukl.value).replace(".", ",")), col, 1);
               
                //Nukleosom
                table.add(new Label(String.valueOf(nukl.x)), col, 2);
                
                //Für die Action
//                int y = (nukl.y/2);
                
                //Enzyme
                table.add(new Label(String.valueOf(hr.channelList.get(nukl.y))), col, 3);
                
                //Channel
                table.add(new Label(String.valueOf(nukl.y)), col, 4);
                
//                //Action
//                String action;
//                if(nukl.y%2 == 0) {
//                    action = "activation";
//                }
//                else {
//                    action = "deactivation";
//                }
//                
//                table.add(new Label(action), col, 5);
            }
        }
    }
    
    private void setLeftNode(HeatNukleosom heatNukl) {
        leftNode = heatNukl;
   }
     
    private void setRightNode(HeatNukleosom heatNukl) {
        rightNode = heatNukl;
   }    
}
