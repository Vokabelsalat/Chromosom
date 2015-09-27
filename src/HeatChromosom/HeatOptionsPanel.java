/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import application.Chromosom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jakob
 */
public class HeatOptionsPanel extends VBox{
    
    Chromosom chromosom;
    
    public HeatOptionsPanel(Chromosom chromosom) {
        
        this.chromosom = chromosom;
        
        setStyle("-fx-border: 3px solid; -fx-border-color: black;");
        
        getChildren().add(new Label("Result:"));
        
        HeatOptionsGrid selectedNukleosom = new HeatOptionsGrid();
        getChildren().add(selectedNukleosom);
        
        Separator sep = new Separator();
        getChildren().add(sep);
        
        getChildren().add(new Label("Selected Items:"));
        
        HeatOptionsGrid hOptions = new HeatOptionsGrid();
        getChildren().add(hOptions);
        
        Spinner nearSpin = new Spinner(0.0, 1.0 , 0.0, 0.01);
        nearSpin.setEditable(true); 
        
        nearSpin.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                System.err.println(newValue);
                chromosom.heatGrid.highlightNear(newValue.doubleValue());
            }
        });      
        
        nearSpin.getEditor().setOnKeyPressed(event -> {
           switch (event.getCode()) {
                case UP:
                    nearSpin.increment();
                    break;
                case DOWN:
                    nearSpin.decrement();
                    break;
            }
        });
        
        getChildren().add(nearSpin);
    }
    
}
