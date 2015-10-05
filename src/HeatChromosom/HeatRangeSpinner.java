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
                
                if(project.getHeatOptionsPanel().getPairButton().isSelected()) {
                    for(HeatProject pro : project.getChromosom().projectList) {
                        if(pro != project) {
                            pro.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().setValue(newValue);
                        }
                        pro.getHeatOptionsPanel().getRangeBox().setHighlighted();
                    }
                }
                else {
                    project.getHeatOptionsPanel().getRangeBox().setHighlighted();
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
                case ENTER:
                    if(!getEditor().getText().equals("")) {
                        String text = getEditor().getText().replace(".", ",");
                        double doub = Double.parseDouble(text.replace(",", "."));

                        if(rangeSpinValueFactory.getValue() == doub) {
                            project.getHeatOptionsPanel().getRangeBox().triggerRangeBox();
                        }

                        getEditor().setText(text);
                    }
                    else {
                        getEditor().setText("0,0");
                    }
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
