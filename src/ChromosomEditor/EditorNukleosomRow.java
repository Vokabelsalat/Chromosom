/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.HashMap;
import javafx.scene.layout.HBox;

public class EditorNukleosomRow extends HBox {
    
    private HashMap<String, EditorNukleosom> nukleosomMap = new HashMap<>();;
    
    public EditorNukleosomRow(EditorRule rule, boolean left) {
        super();
        setSpacing(15);
        for(int i = 0; i < 3; i++) {
            EditorNukleosom nukl = new EditorNukleosom(rule, left, i);
            getChildren().add(nukl);
            nukleosomMap.put(String.valueOf(i), nukl);
            if(left == false && i != 1) {
                nukl.setDisable(true);
            }
        } 
    }
    
    public void checkStateColors() {
        
        for(EditorNukleosom nukl : nukleosomMap.values()) {
            for(StateBorderPane pan : nukl.getStateBorderPaneList()) {
                pan.checkStateColors();
            }
        }
        
    }
    
    /**
     * @return the nukleosomMap
     */
    public HashMap<String, EditorNukleosom> getNukleosomMap() {
        return nukleosomMap;
    }

    /**
     * @param nukleosomMap the nukleosomMap to set
     */
    public void setNukleosomMap(HashMap<String, EditorNukleosom> nukleosomMap) {
        this.nukleosomMap = nukleosomMap;
    }
    
    public static String getRuleNukleosomString(EditorNukleosomRow nukleosomRow) {
        String returnStr = "";
        for(int nuklCount = 0; nuklCount < 3; nuklCount++) {
            EditorNukleosom nukl = nukleosomRow.getNukleosomMap().get(String.valueOf(nuklCount));
            String nukleosomString = "";
            for(String histone : nukl.getHistoneMap().keySet()) {
                boolean empty = true;
                for(StateComboBox box : nukl.getHistoneMap().get(histone).values()) {
                    if(!box.getValue().equals("")) {
                        empty = false;
                        break;
                    }
                }
                if(empty == false) {
                    
                    for(StateComboBox box : nukl.getHistoneMap().get(histone).values()) {
                        if(!box.getValue().equals("") && !box.title.isEmpty()) {
                            nukleosomString += histone + "[" + box.title + "." + box.getValue() + "]";
                        }
                    }
                }
            }
            
            if(!nukleosomString.equals("")) {

                nukleosomString = "{" +  nukleosomString + "}";
            }
            
            if(nuklCount == 1) {
                nukleosomString = "(" + nukleosomString + ")";
            }
                
            returnStr += nukleosomString;
        }
        return returnStr;
    }

    void setEditable(boolean b) {
        for(EditorNukleosom nukl : nukleosomMap.values()) {
            nukl.setDisable(b);
            nukl.setEditable(b);
        }
    }
}