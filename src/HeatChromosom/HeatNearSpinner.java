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
        
        valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable,
                    Double oldValue, Double newValue) {
                try {
                    if(project.getHeatOptionsPanel().getPairButton().isSelected()) {
                        for(HeatProject pro : project.getChromosom().projectList) {
                            pro.getHeatOptionsPanel().setOldProb(newValue);//.doubleValue();
                            if(pro != project) {
                                pro.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().setValue(newValue);
                            }
                            pro.getHeatGrid().highlightNear(newValue, project.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());//.doubleValue());
                            pro.getHeatOptionsPanel().getRangeBox().setSelected(true);  
                        }
                    }
                    else {
                            project.getHeatOptionsPanel().setOldProb(newValue);///.doubleValue();
                            project.getHeatGrid().highlightNear(newValue, project.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());//.doubleValue());
                    }
                }
                catch(Exception e) {
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
                        String text = getEditor().getText().replaceAll(",", ".");
                        double doub = Double.parseDouble(text);
                        if(project.getHeatOptionsPanel().getOldProb() == doub) {
                            if(project.getHeatGrid().getHighlightedList() != null && !project.getHeatGrid().getHighlightedList().isEmpty()) {
                                project.getHeatGrid().getHighlightedList().removeAll(project.getHeatGrid().getHighlightedList());
                            }
                            project.getHeatGrid().resetHighlightedNukl();
                            project.getHeatOptionsPanel().setOldProb(project.getHeatOptionsPanel().getOldProb() + 2.0);
                            project.getHeatOptionsPanel().getRangeBox().setSelected(false);
                        }
                        else {
                            project.getHeatGrid().highlightNear(doub, project.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());
                            project.getHeatOptionsPanel().setOldProb(doub);
                            project.getHeatOptionsPanel().getRangeBox().setSelected(true);
                        }
                    }
                    else {
                        getEditor().setText("0.0");
                    }
                    break;
            }
        });
        
        getEditor().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(newValue) {
                
            }
            else {
                if(!getEditor().getText().equals("")) {
                    String text = getEditor().getText().replaceAll(",", ".");
                    double doub = Double.parseDouble(text);
                    if(project.getHeatOptionsPanel().getOldProb() == doub) {
                        if(project.getHeatGrid().getHighlightedList() != null && !project.getHeatGrid().getHighlightedList().isEmpty()) {
                            project.getHeatGrid().getHighlightedList().removeAll(project.getHeatGrid().getHighlightedList());
                        }
                        project.getHeatGrid().resetHighlightedNukl();
                        project.getHeatOptionsPanel().setOldProb(project.getHeatOptionsPanel().getOldProb() + 2.0);
                    }
                    else {
                        project.getHeatGrid().highlightNear(doub, project.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());
                        project.getHeatOptionsPanel().setOldProb(doub);
                    }
                }
                else {
                    getEditor().setText("0.0");
                }
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
    
}
