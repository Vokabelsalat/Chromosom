/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import application.ChromosomProject;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Jakob
 */
public class PlusMinusLabel extends AnchorPane{
    
    private String valueString;
    
    public PlusMinusLabel(String valueString, ChromosomProject project) {
        this.valueString = valueString;
        Label plus = new Label("+");
        
        plus.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                project.getChromosom().getPrimaryStage().getScene().setCursor(Cursor.HAND);
            }
        });

        plus.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                project.getChromosom().getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
            }
        });
        
        Label minus = new Label("-");
        
        minus.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                project.getChromosom().getPrimaryStage().getScene().setCursor(Cursor.HAND);
            }
        });

        minus.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                project.getChromosom().getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
            }
        });
        
//        Button plus = new Button("+");
//        Button minus = new Button("-");
        
//        this.setAlignment(Pos.CENTER_LEFT);
        
        plus.setStyle("-fx-border-color: black; -fx-border-width: 0.3px");
        minus.setStyle("-fx-border-color: black; -fx-border-width: 0.3px");
        plus.setMinWidth(10.0);
        minus.setMinWidth(10.0);
        minus.setAlignment(Pos.CENTER);
        plus.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(plus, 0.0);
        AnchorPane.setRightAnchor(minus, 0.0);
        
        plus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                if(project.stepSize.peek() != 1) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                       project.zoomIn(Integer.parseInt(valueString));
                    }
//                }
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

        if(!valueString.equals(project.getLastItemInTimeMap())) {
            if(project.stepSize.peek() != 1) {
                getChildren().add(plus);
            }
            if(project.reservoirStack.size() != 0) {
                getChildren().add(minus);
            }
        } 
        Label lab = new Label(valueString);
        AnchorPane.setLeftAnchor(lab, 12.0);
        AnchorPane.setRightAnchor(lab, 12.0);
        lab.setAlignment(Pos.CENTER);
//        setStyle("-fx-border-color: black;");
        getChildren().add(lab);
        
    }

    /**
     * @return the valueString
     */
    public String getValueString() {
        return valueString;
    }

    /**
     * @param valueString the valueString to set
     */
    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}
