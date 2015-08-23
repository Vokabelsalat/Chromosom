/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import application.ChromosomProject;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jakob
 */
public class PlusMinusLabel extends GridPane{
    
    public PlusMinusLabel(String valueString, ChromosomProject project) {
        Label plus = new Label("+");
        Label minus = new Label("-");
                        
//        plus.setStyle("-fx-border-color: black;");
//        minus.setStyle("-fx-border-color: black;");

        plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(project.stepSize.peek() != 1) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                       project.zoomIn(Integer.parseInt(valueString));
                    }
                }
            }
        });

        minus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                   project.zoomOut();
                }
            }
        });

        add(plus,0,0);
        add(new Label(valueString),1,0);
        add(minus,2,0);
    }
}
