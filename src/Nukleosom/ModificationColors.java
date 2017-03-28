/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Oberfläche für die Zuordnung der Modifikationszustände zu den entsprechenden Farben
 * @author Jakob
 */
public class ModificationColors extends BorderPane{
    
    private ArrayList<String> superStrings;
    private LinkedHashMap<String, Color> colorMap;
    private final ComboBox combo;
    
    
    public ModificationColors() {
        
        makeFirstTheme();

        ObservableList<String> themeList = 
        FXCollections.observableArrayList(
            "Theme 1",
            "Theme 2"
            //    ,"Theme 3"
        );
        
        combo = new ComboBox(themeList);
        
        combo.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String oldString, String newString) {     
                switch(newString) {
                    case "Theme 1": 
                        makeFirstTheme();
                        break;
                    case "Theme 2":
                        makeSecondTheme();
                        break;
//                    case "Theme 3":
//                        makeThirdTheme();
//                        break;
                }
                makeGrid();
            }    
        });
                
        combo.setValue("Theme 1");
        
        setRight(combo);
    }
    
    public void makeGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        int rowCounter = 0;
        int firstColRowCounter = 0;
        
        for(Entry<String, Color> entry : getColorMap().entrySet()) {
            
            int index = new ArrayList<String>(getColorMap().keySet()).indexOf(entry.getKey());
            if(index < superStrings.size()) {
                if(index > 0 && superStrings.get(index).equals(superStrings.get(index-1))) {
                }
                else {
                    grid.add(new Label(superStrings.get(index)), 0, firstColRowCounter);
                }
            }
               
            rowCounter = firstColRowCounter + 1; 
            grid.add(new Label(entry.getKey()), 1, rowCounter);
            Rectangle rect = new Rectangle(30,30, entry.getValue());
            grid.add(rect, 2, rowCounter);
            firstColRowCounter += 2;
        }
        
        getColorMap().put("un", Color.WHITE);
        
        setCenter(grid);
        setPrefWidth(350);
    }
    
    public void makeSpecialTheme() {
        
    }
    
    public void makeFirstTheme() {
        
        String[] array = {"me","me","me","ac","ph","ub"};
        superStrings = new ArrayList<>(Arrays.asList(array));
        
        setColorMap(new LinkedHashMap<>());

        getColorMap().put("me", Color.LIGHTSALMON);
        getColorMap().put("me2", Color.RED);
        getColorMap().put("me3", Color.RED.darker());
        
        getColorMap().put("ac", Color.rgb(77, 175, 74)); //Grün
        getColorMap().put("ph", Color.rgb(200, 0, 255)); //Lila
        getColorMap().put("ub", Color.rgb(55,126,190)); //Blau
        

    }
    
    public void makeSecondTheme() {
        setColorMap(new LinkedHashMap<>());
        String[] array = {"me", "me", "ac","ph","ub"};
        superStrings = new ArrayList<>(Arrays.asList(array));

        colorMap.put("R:me", Color.rgb(0,255,255));
        colorMap.put("K:me", Color.RED);
        getColorMap().put("ac", Color.rgb(77, 175, 74)); //Grün
        getColorMap().put("ph", Color.rgb(200, 0, 255)); //Lila
        getColorMap().put("ub", Color.rgb(55,126,190)); //Blau
    }
    
//    public void makeThirdTheme() {
//        setColorMap(new LinkedHashMap<>());
//        String[] array = {"me", "me"};
//        superStrings = new ArrayList<>(Arrays.asList(array));
//
//        colorMap.put("H3K4:me", Color.BLUE);
//        colorMap.put("H3K27:me", Color.RED);
//        colorMap.put("H4K4:me", Color.ORANGE);
//        
//    }

    /**
     * @return the colorMap
     */
    public LinkedHashMap<String, Color> getColorMap() {
        return colorMap;
    }

    /**
     * @param colorMap the colorMap to set
     */
    public void setColorMap(LinkedHashMap<String, Color> colorMap) {
        this.colorMap = colorMap;
    }
    
    private Color getColorForValue(double value) {
        
        double MIN = 1;
        double MAX = 3;
        double BLUE_HUE = Color.BEIGE.getHue() ;
        double RED_HUE = Color.RED.getHue() ;
        
        if (value < MIN || value > MAX) {
            return Color.BLACK ;
        }
        double hue = BLUE_HUE + (RED_HUE - BLUE_HUE) * (value - MIN) / (MAX - MIN) ;
        return Color.hsb(hue, 1.0, 1.0).invert();
    }
    
    public void setTheme(String colorTheme) {
        combo.setValue(colorTheme);
    }
    
    public String getTheme() {
        return (String)combo.getValue();
    }
}
