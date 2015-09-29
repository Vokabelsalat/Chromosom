/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import application.Chromosom;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jakob
 */
public class HeatOptionsPanel extends VBox{
    
    Chromosom chromosom;
    double oldProb = 2.0;
    
    public HeatOptionsPanel(Chromosom chromosom) {
        
        this.chromosom = chromosom;
        this.setSpacing(3);
        this.setMinSize(220, 0);
        
//        setStyle("-fx-border: 3px solid; -fx-border-color: black;");
        
        getChildren().add(new Label("Result:"));
        
        HeatOptionsGrid selectedNukleosom = new HeatOptionsGrid();
        getChildren().add(selectedNukleosom);
        
        Separator sep = new Separator();
        getChildren().add(sep);
        
        getChildren().add(new Label("Selected Items:"));
        
        HeatOptionsGrid hOptions = new HeatOptionsGrid();
        getChildren().add(hOptions);
        
        Separator sep2 = new Separator();
        getChildren().add(sep2); 
        
        getChildren().add(new Label("Probability Highlight:"));
        
        HBox hbox = new HBox();
        
        CheckBox checky = new CheckBox();

        Spinner nearSpin = new Spinner(0.0, 1.0, 0.0, 0.01);
        Spinner rangeSpinner = new Spinner(0.0, 1.0, 0.0, 0.01);
        
        DoubleSpinnerValueFactory nearSpinValueFactory = new DoubleSpinnerValueFactory(0.0, 1.0, 0.0, 0.01);
        nearSpin.setValueFactory(nearSpinValueFactory);
        
        DoubleSpinnerValueFactory rangeSpinValueFactory = new DoubleSpinnerValueFactory(0.0, 1.0, 0.0, 0.01);
        rangeSpinner.setValueFactory(rangeSpinValueFactory);
        
        checky.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            
            if(new_val == true) {
                String text = nearSpin.getEditor().getText().replaceAll(",", ".");
                double doub = Double.parseDouble(text);
                chromosom.heatGrid.highlightNear(doub, rangeSpinValueFactory.getValue());
                oldProb = doub;
            }
            else {
                if(!chromosom.heatGrid.highlightedList.isEmpty()) {
                    chromosom.heatGrid.highlightedList.removeAll(chromosom.heatGrid.highlightedList);
                }
                chromosom.heatGrid.resetHighlightedNukl();
                oldProb = oldProb + 2.0;
            }
        });
        
        nearSpin.setEditable(true); 
        
        nearSpin.setMaxWidth(80.0);
        

        
        nearSpin.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable,
                    Double oldValue, Double newValue) {
                try {
                    oldProb = newValue;//.doubleValue();
                    chromosom.heatGrid.highlightNear(newValue, rangeSpinValueFactory.getValue());//.doubleValue());
                    checky.setSelected(true);  
                }
                catch(Exception e) {
                    
                }
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
                case ENTER:
                    if(!nearSpin.getEditor().getText().equals("")) {
                        String text = nearSpin.getEditor().getText().replaceAll(",", ".");
                        double doub = Double.parseDouble(text);
                        if(oldProb == doub) {
                            if(!chromosom.heatGrid.highlightedList.isEmpty()) {
                                chromosom.heatGrid.highlightedList.removeAll(chromosom.heatGrid.highlightedList);
                            }
                            chromosom.heatGrid.resetHighlightedNukl();
                            oldProb = oldProb + 2.0;
                            checky.setSelected(false);
                        }
                        else {
                            chromosom.heatGrid.highlightNear(doub, rangeSpinValueFactory.getValue());
                            oldProb = doub;
                            checky.setSelected(true);
                        }
                    }
                    else {
                        nearSpin.getEditor().setText("0.0");
                    }
                    break;
            }
        });
        
        rangeSpinner.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable,
                    Double oldValue, Double newValue) {
                nearSpinValueFactory.setAmountToStepBy(newValue);
                if(checky.isSelected() == true) {
                    chromosom.heatGrid.highlightNear(nearSpinValueFactory.getValue(), newValue);
                }
            }
        });
        
        rangeSpinner.setEditable(true);
        rangeSpinner.setMaxWidth(70);
        hbox.setSpacing(3);
        hbox.getChildren().addAll(checky, nearSpin);
        
        HBox rangeHBox = new HBox();
        rangeHBox.getChildren().addAll(new Label("Range:"), rangeSpinner);
        rangeHBox.setSpacing(3);
        
        getChildren().addAll(hbox, rangeHBox);
    }
    
}
