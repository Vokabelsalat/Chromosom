/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

/**
 *
 * @author Jakob
 */
public class StateComboBox extends BorderPane{
    
//    ComboBox box;
    TextField box;
    String title;
    
    public StateComboBox(String title) {
        super();
        
        this.title = title;
        
        ObservableList<String> options = 
        FXCollections.observableArrayList(
            "0",
            "1",
            "2",
            "3",
            "4",
            "*"
        );
        
//        box = new ComboBox(options);
        box = new TextField();
    
        Label lab = new Label(title);
        lab.setFont(new Font(10));
        setTop(lab);
        
        if(title.equals("")) {
            box.setVisible(false);
        }

//        box.setValue("0");
//        box.setEditable(true);
        
//        box.setMinWidth(32);
//        box.setPrefWidth(32);
        
        box.setPrefWidth(32);
        box.setMinHeight(20);
        box.setPrefHeight(20);
        
        setCenter(box);
        setPadding(new Insets(1,1,1,1));
        setStyle("-fx-border: 0.5px solid; -fx-border-color: black;");
    }
    
    public String getValue() {
        return box.getText();
    }
    
}
