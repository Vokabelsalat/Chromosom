/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;

/**
 *
 * @author Jakob
 */
public class HeatRangeSpinner extends Spinner{
    
    private final HeatProject project;
    private DoubleSpinnerValueFactory rangeSpinValueFactory;
    
    public HeatRangeSpinner(HeatProject project) {
        super(0.0, 1.0, 0.0, 0.01);
        this.project = project;
        
        rangeSpinValueFactory = new DoubleSpinnerValueFactory(0.0, 1.0, 0.0, 0.01);
        setValueFactory(rangeSpinValueFactory);
        
        valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable,
                    Double oldValue, Double newValue) {
//                nearSpinValueFactory.setAmountToStepBy(newValue);
                if(project.getHeatOptionsPanel().getPairButton().isSelected()) {
                    for(HeatProject pro : project.getChromosom().projectList) {
                        if(pro.getHeatOptionsPanel().getRangeBox().isSelected() == true) {
                            pro.getHeatGrid().highlightNear(project.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().getValue(), newValue);
                        
                        }    
                        if(pro != project) {
                            pro.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().setValue(newValue);
                        }
                    }
                }
                else {
                    if(project.getHeatOptionsPanel().getRangeBox().isSelected() == true) {
                        project.getHeatGrid().highlightNear(project.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().getValue(), newValue);
                    }
                }
            }
        });
        
        getEditor().setOnKeyPressed(event -> {
           switch (event.getCode()) {
                case UP:
                    increment();
                    break;
                case DOWN:
                    decrement();
                    break;
           }
        });
        
        setEditable(true);
        setMaxWidth(70);
        
    }

    /**
     * @return the rangeSpinValueFactory
     */
    public SpinnerValueFactory.DoubleSpinnerValueFactory getRangeSpinValueFactory() {
        return rangeSpinValueFactory;
    }

    /**
     * @param rangeSpinValueFactory the rangeSpinValueFactory to set
     */
    public void setRangeSpinValueFactory(SpinnerValueFactory.DoubleSpinnerValueFactory rangeSpinValueFactory) {
        this.rangeSpinValueFactory = rangeSpinValueFactory;
    }
    
}
