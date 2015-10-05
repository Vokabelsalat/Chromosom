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
public class HeatNearSpinner extends Spinner{
    
    private final HeatProject project;
    private DoubleSpinnerValueFactory nearSpinValueFactory;
    
    public HeatNearSpinner(HeatProject project) {
        super(0.0, 1.0, 0.0, 0.01);
        this.project = project;
        
        nearSpinValueFactory = new DoubleSpinnerValueFactory(0.0, 1.0, 0.0, 0.01);
        
        setValueFactory(nearSpinValueFactory);
        
        setEditable(true); 
        
        setMaxWidth(70.0);
        
        nearSpinValueFactory.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable,
                    Double oldValue, Double newValue) {
                triggerNear(newValue, oldValue);
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
//                        System.err.println(text);
                        double doub = Double.parseDouble(text.replace(",", "."));
                        
                        if(nearSpinValueFactory.getValue() == doub) {
                            triggerNear(doub, doub);
                        }
                        
                        getEditor().setText(text);
                        
                    }
                    else {
                        getEditor().setText("0,0");
                    }
                    break;
            }
        });
    }

    /**
     * @return the nearSpinValueFactory
     */
    public SpinnerValueFactory.DoubleSpinnerValueFactory getNearSpinValueFactory() {
        return nearSpinValueFactory;
    }

    /**
     * @param nearSpinValueFactory the nearSpinValueFactory to set
     */
    public void setNearSpinValueFactory(SpinnerValueFactory.DoubleSpinnerValueFactory nearSpinValueFactory) {
        this.nearSpinValueFactory = nearSpinValueFactory;
    }
   
    public void triggerNear(double newValue, double oldValue) {
        
//        System.err.println("triggerNear");
        
        try {
            if(project.getHeatOptionsPanel().getPairButton().isSelected()) {
                for(HeatProject pro : project.getChromosom().projectList) {
//                    if(pro != project) {
                        pro.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().setValue(newValue);
//                    }
                    if(newValue != oldValue) {
                        pro.getHeatOptionsPanel().getRangeBox().setHighlighted();
                    }
                    else if(project == pro){
                        pro.getHeatOptionsPanel().getRangeBox().triggerRangeBox();
                    }
                }
            }
            else {
                project.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().setValue(newValue);
               
                if(newValue != oldValue) {
                    project.getHeatOptionsPanel().getRangeBox().setHighlighted();
                }
                else {
                    project.getHeatOptionsPanel().getRangeBox().triggerRangeBox();
                }
            }
        }
        catch(Exception e) {
        }
    }
    
}
