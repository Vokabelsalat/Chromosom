/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Nukleosom.BigNukleosomNew;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author jakob
 */
public class OptionsPanel extends VBox{
    
    BigNukleosomNew nuklPane;
    
    public OptionsPanel(ChromosomProject project) {
        setPadding(new Insets(10));
        
        Text title = new Text("Legende");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        getChildren().add(title);
        
        GridPane legPan = new GridPane();
        
//        legPan.add(new Label("Legende:"),0,0);
        
        legPan.add(new Label("0"), 0, 1);
        legPan.add(new Label("1"), 0, 2);
        legPan.add(new Label("2"), 0, 3);
        legPan.add(new Label("3"), 0, 4);
        legPan.add(new Label("4"), 0, 5);
        
        
        
        legPan.add(new Rectangle(8, 8 , ChromosomProject.color0) , 1, 1);
        legPan.add(new Rectangle(8, 8 , ChromosomProject.color1), 1, 2);
        legPan.add(new Rectangle(8, 8 , ChromosomProject.color2), 1, 3);
        legPan.add(new Rectangle(8, 8 , ChromosomProject.color3), 1, 4);
        legPan.add(new Rectangle(8, 8 , ChromosomProject.color4), 1, 5);
        
        legPan.setHgap(10);
        
//        legPan.setGridLinesVisible(true);
        
        getChildren().add(legPan);
        
        GridPane pan = new GridPane();
        
        pan.add(new Label("H3K4"),0 ,0 );
        pan.add(new Label("H3K27"),1 ,0 );
        pan.add(new Label("H4K4"),0 ,1 );
        pan.add(new Label("H4K27"),1 ,1 );
        
        for(Node nod : pan.getChildren()) {
            GridPane.setMargin(nod, new Insets(5, 5, 5, 5));
        }
        
        pan.setGridLinesVisible(true);
        pan.setPadding(new Insets(10,10,10,10));
        
        getChildren().add(pan);
        
        setStyle("-fx-border: 3px solid; -fx-border-color: black;");
        
        HBox hbox = new HBox();
        
        Button zoomIn = new Button("+");
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                project.getChromosom().zoomInNukleosoms();
            }
        });
        
        Button zoomOut = new Button("-");
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                project.getChromosom().zoomOutNukleosoms();
            }
        });
        
        hbox.getChildren().add(zoomIn);
        hbox.getChildren().add(zoomOut);
        
        getChildren().add(hbox);

    }
    
    public void addNukleosom(BigNukleosomNew nukleosom) {
        if(nuklPane != null && getChildren().contains(nuklPane)) {
            getChildren().remove(nuklPane);
        }
        nuklPane = nukleosom;
        getChildren().add(nuklPane);
    }
    
}
