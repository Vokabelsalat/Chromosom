/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Jakob
 */

    /**
     * Stellt eine Spalte, also genau ein Histon der Uebersicht dar.
     * Enthaelt Sites und die moeglichen Modifiaktionen an diesen.
     */
    public class HistoneTable extends BorderPane {
        
        GridPane table;
        int rowCounter = 0;
        private final ArrayList <ModificationList> modList = new ArrayList<>();
        private final ArrayList <TextField> siteList = new ArrayList<>();
        String title;
        
        public HistoneTable(String title) {
            this.title = title;
            
            //Die Überschrift der Spalte bzw. des Histons
            setTop(new Label(title));
        
            ScrollPane sp = new ScrollPane();
            
            table = new GridPane();

            table.add(new Label("Site"), 1, rowCounter);
            table.add(new Label("Modification"), 2, rowCounter);
            
            table.setPadding(new Insets(2,20,20,2));
            sp.setContent(table);
            setCenter(sp);
            
            Button plusButton = new Button("+");
            
            //Beim Klick auf den Plus-Button soll eine neue Site hinzugefügt werden.
            //Eine leere Modifikation wird ebenso hinzugeüfgt
            plusButton.setOnAction(e ->  {
                ModificationList moo = new ModificationList();
                moo.addRow("");
                addRow("", moo);
            });
            plusButton.setAlignment(Pos.CENTER);
            
            //Der PlusButton wird unter der jeweiligen Tabelle hinzugefügt
            StackPane stack = new StackPane();
            stack.getChildren().add(plusButton);
            StackPane.setAlignment(plusButton, Pos.CENTER);
            
            setBottom(stack);
        }
        
        /** Gibt einen String zurück der alle Sites mit den enthaltenen Modifikationen auflistet
         * 
         * @return String mit allen Sites und den dazugehörigen Modifikationen in der Form: "SiteName{Modification1,Modification2,...}"
         */
        public String getHistoneString() {
            String returnString = "";
            for(int i = 0; i < getSiteList().size(); i++) {
                returnString += getSiteList().get(i).getText() + "{" + getModList().get(i).getModificatonString() + "}\n";
            }
            return returnString;
        }
        
        /**
         * Fügt eine neue Site hinzuh. Standardmäßig mit einer leeren Modification
         * @param site Der Name der Site
         * @param mod Die Liste der möglichen Modifikationen der Site
         */
        public void addRow(String site, ModificationList mod) {
            rowCounter++;
            HBox pane = new HBox();
            
            //Das Site-Textfeld wird zusammen mit einem Minus-Button zu der linken Spalte hinzugefügt
            TextField siteTF = new TextField(site); 
            siteTF.setMaxWidth(100);
            getSiteList().add(siteTF);
            
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    siteTF.requestFocus();
                }
            });
            
            Button minusButton = new Button("-");
            minusButton.setOnAction(e -> {
                table.getChildren().removeAll(pane,mod);
                getModList().remove(mod);
                getSiteList().remove(siteTF);
            });
            
            pane.getChildren().addAll(minusButton, siteTF);
            pane.setPadding(new Insets(0,2,5,2));
            table.add(pane, 1, rowCounter);
            
            //Die ModListe wird der rechten Spalte hinzugefügt
            getModList().add(mod);
            table.add(mod, 2, rowCounter);
            mod.setPadding(new Insets(0,0,5,0));
        }

        /**
         * @return the siteList
         */
        public ArrayList <TextField> getSiteList() {
            return siteList;
        }

        /**
         * @return the modList
         */
        public ArrayList <ModificationList> getModList() {
            return modList;
        }
    }