/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author jakob
 */
public class OptionsPanel extends VBox{
    
    public OptionsPanel() {
        setPadding(new Insets(10));
        
        Text title = new Text("Options");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        getChildren().add(title);
        
        setStyle("-fx-border: 3px solid; -fx-border-color: black;");

    }
}
