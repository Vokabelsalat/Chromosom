/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jakob
 */
public class StateBorderPane extends BorderPane{

    private String title;
    String[][] data;
    ChromosomEditor editor;
    GridPane innerGrid;
    private HashMap<String, StateComboBox> comboMap;
    EditorRule rule;
    boolean left;
    
    //Anstelle des Editor vielleicht den Parent-Pane mit übergeben
    public StateBorderPane(String title, String[][] data, EditorRule rule, boolean left, int nukleosomNr) {
        super();
        
        this.rule = rule;
        this.title = title;
        this.data = data;
        this.editor = rule.getEditor();
        this.left = left;
        
        comboMap = new HashMap<>();
        
        String modString = "";
        
        if(left) {
            modString = rule.getTargetStrings()[nukleosomNr];
        }
        else {
            modString = rule.getRuleStrings()[nukleosomNr];
        }
        
        innerGrid = addStateComboBoxes(data, title, rule, modString);
        
        setStyle("-fx-border-width: 1px; -fx-border-color: black;");

        GridPane pan = new GridPane();
        pan.getChildren().add(new Label(title));
        pan.setAlignment(Pos.CENTER);
        
        setTop(pan);    
        setCenter(innerGrid);
        
    }

    private GridPane addStateComboBoxes(String[][] data, String histone, EditorRule rule, String target) {
        GridPane pan = new GridPane();

        for(int y = 0; y < data[0].length; y++) {
            for(int x = 0; x < data.length; x++) {
                String content = data[x][y];
                StateComboBox box = new StateComboBox(content);

                String mod = "";

                String search = histone + "[" + content;

                if(target != null && target.contains("]")) {
                String split[] = target.split("]");
                    for(String str : split) {
                        if(str.contains(search)) {
                            mod = str.replace(search + ".", "");
                        }
                    }
                }

                box.getBox().textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        if(left) { 
                            for(String nukleosom : rule.getFirstRow().getNukleosomMap().keySet()) {
                                if(rule.getFirstRow().getNukleosomMap().get(nukleosom).getHistoneMap().get(title).containsValue(box)) {
                                    rule.getSecondRow().getNukleosomMap().get(nukleosom).getHistoneMap().get(title).get(content).getBox().setText(newValue);
                                    break;
                                }
                            }
                        }
                        checkStateColors();
                    }
                });
                pan.add(box, x, y);
                box.setValue(mod);

//                if(target!=null && !mod.equals("")) {
//                    checkStateColors();
//                }

                getComboMap().put(data[x][y], box);
            }
            
        }
        return pan;
    }
    
    public void checkStateColors() {
        int changeCounter = 0;
        Pattern r;
        Matcher m;

        for(String nukleosom : rule.getSecondRow().getNukleosomMap().keySet()) {
            for(String histone : rule.getSecondRow().getNukleosomMap().get(nukleosom).getHistoneMap().keySet()) {
                for(String valueTitle : rule.getSecondRow().getNukleosomMap().get(nukleosom).getHistoneMap().get(histone).keySet()) {
                    StateComboBox secondValue = rule.getSecondRow().getNukleosomMap().get(nukleosom).getHistoneMap().get(histone).get(valueTitle);
                    StateComboBox firstValue = rule.getFirstRow().getNukleosomMap().get(nukleosom).getHistoneMap().get(histone).get(valueTitle);
                    
                    if(!secondValue.title.isEmpty() &&
                            !firstValue.getValue().equals(
                                secondValue.getValue())) {
                        changeCounter++;
                    }
                    
                    if(rule.getEditor().getChromosomProject().getHistoneModificationMap() != null) {
                        HistoneTable histTable = rule.getEditor().getChromosomProject().getHistoneModificationMap().get(histone);
                        String firstColor = "rgba(255,255,255,1.0)"; //white
                        boolean found = false;
                        
                        for(int siteNumber = 0; siteNumber < histTable.getSiteList().size(); siteNumber++) {
                            TextField tf = histTable.getSiteList().get(siteNumber);
                            if(tf.getText().equals(secondValue.title)) {
                                for(TextField modTF : histTable.getModList().get(siteNumber).getModList()) {
                                    String pattern = modTF.getText() + "[123]?";
                                    r = Pattern.compile(pattern);
                                    m = r.matcher(firstValue.getValue());
                                    if(m.matches()) {
                                        found = true;
                                        break;
                                    }
                                } 
                            }
                        }
                        
                        if(!firstValue.getValue().equals("") && !firstValue.getValue().equals("un") && found == false) {
                            firstColor = "rgba(20,20,181,0.25)"; //blue
                        }
                        
                        firstValue.getBox().setStyle("-fx-background-color: " + firstColor + ";");
                    }
                }
            }
        }

        String color;
        
        for(String nukleosom : rule.getSecondRow().getNukleosomMap().keySet()) {
            for(String histone : rule.getSecondRow().getNukleosomMap().get(nukleosom).getHistoneMap().keySet()) {
                for(StateComboBox value : rule.getSecondRow().getNukleosomMap().get(nukleosom).getHistoneMap().get(histone).values()) {
                    if(!rule.getFirstRow().getNukleosomMap().get(nukleosom).getHistoneMap().get(histone).get(value.title).getValue().equals(
                        rule.getSecondRow().getNukleosomMap().get(nukleosom).getHistoneMap().get(histone).get(value.title).getValue())) {
                        if(changeCounter == 1) {
                            color = "rgba(20,163,60,0.4)"; // green
                        }
                        else {
                            color = "rgba(240,30,30,0.35)"; // red
                        }
                    }
                    else {
                        color = "white";
                    }
                    
                    if(rule.getEditor().getChromosomProject().getHistoneModificationMap() != null) {
                        HistoneTable histTable = rule.getEditor().getChromosomProject().getHistoneModificationMap().get(histone);
                        boolean found = false; 
                        for(int siteNumber = 0; siteNumber < histTable.getSiteList().size(); siteNumber++) {
                            TextField tf = histTable.getSiteList().get(siteNumber);
                            if(tf.getText().equals(value.title)) {
                               for(TextField modTF : histTable.getModList().get(siteNumber).getModList()) {
                                    String pattern = modTF.getText() + "[123]?";
                                    r = Pattern.compile(pattern);
                                    m = r.matcher(value.getValue());
                                    if(m.matches()) {
                                        found = true;
                                        break;
                                    }
                               }
                            }
                        }
                        if(found == false && !color.equals("rgba(240,30,30,0.35)") && !value.getValue().equals("") && !value.getValue().equals("un")) {
                            color = "rgba(20,20,181,0.25)";
                        }
                    }

                    value.getBox().setStyle("-fx-background-color: " + color + ";");
                }
            }
        }
    }
    
    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        
        for(StateComboBox box : getComboMap().values()) {
            values.add(box.getValue());
        }
        return values;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the comboMap
     */
    public HashMap<String, StateComboBox> getComboMap() {
        return comboMap;
    }

    /**
     * @param comboMap the comboMap to set
     */
    public void setComboMap(HashMap<String, StateComboBox> comboMap) {
        this.comboMap = comboMap;
    }

    void setEditable(boolean b) {
        for(StateComboBox combo : comboMap.values()) {
            combo.getBox().setEditable(b);
        }
    }
    
}
