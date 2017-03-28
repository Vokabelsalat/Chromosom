/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jakob
 */

    /**
     * Enthält eine List aller möglicher Modifikationen an einer bestimmten Site.
     */
    public class ModificationList extends VBox {
        
        Button plusButton;
        private ArrayList<TextField> modList = new ArrayList<>();
        
        public ModificationList() {
            
            plusButton = new Button("+");
            plusButton.setOnAction(e -> addRow(""));
            getChildren().add(plusButton);
            setSpacing(2);
        }
        
        /**
         * Liefert einen String mit allen Modifikationen der Site, getrennt durch Semikolons.
         * @return 
         */
        public String getModificatonString() {
            String returnString = "";
            
            for(int i = 0; i < getModList().size(); i++) {
                returnString += getModList().get(i).getText();
                if(i < getModList().size()-1) {
                    returnString += ";";
                }
            }
            return returnString;
        }
        
        /*
        * Fügt eine neue Zeile mit einer neuen leeren Modifiktion der ModificationList hinzu.
        * Enthält ein Textfeld, sowie ein Minus-Button zum Löschen der Modifikation.
        */
        public void addRow(String mod) {
            getChildren().remove(plusButton);
            
            HBox hbox = new HBox();
            
            TextField modTF = new TextField(mod);
            getModList().add(modTF);
            modTF.setMaxWidth(70);
            
            Button minusButton = new Button("-");
            minusButton.setOnAction(e -> {
                getChildren().remove(hbox);
                getModList().remove(modTF);
            });
            
            hbox.getChildren().addAll(modTF, minusButton);
            
            getChildren().add(hbox);
            getChildren().add(plusButton);
        }

        /**
         * @return the modList
         */
        public ArrayList<TextField> getModList() {
            return modList;
        }

        /**
         * @param modList the modList to set
         */
        public void setModList(ArrayList<TextField> modList) {
            this.modList = modList;
        }
    }