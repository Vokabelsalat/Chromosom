/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Jakob
 */
public class HeatRangeBox extends CheckBox{
    
    private HeatProject project;
    
    public HeatRangeBox (HeatProject project) {
        super();
        
        this.project = project;
        
        selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) -> {
            
            if(newValue == true) {
                if(project.getHeatOptionsPanel().getPairButton().isSelected()) {
                    for(HeatProject pro : project.getChromosom().projectList) {
                        pro.getHeatOptionsPanel().getRangeBox().setHighlighted();
                    }
                }
                else {
                    project.getHeatGrid().highlightNear(project.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().getValue(), project.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());
                }
            }
            else {
                if(project.getHeatOptionsPanel().getPairButton().isSelected()) {
                    for(HeatProject pro : project.getChromosom().projectList) {
                        pro.getHeatOptionsPanel().getRangeBox().setDehighlighted();
                    }
                }
                else {
                    if(!project.getHeatGrid().getHighlightedList().isEmpty()) {
                        project.getHeatGrid().getHighlightedList().removeAll(project.getHeatGrid().getHighlightedList());
                    }
                    project.getHeatGrid().resetHighlightedNukl();
                }
            }
        });
    }
    
    public void setHighlighted() {
        setSelected(true);
        project.getHeatGrid().highlightNear(project.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().getValue(), project.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());
    }
    
    public void setDehighlighted() {
        setSelected(false);
        if(!project.getHeatGrid().getHighlightedList().isEmpty()) {
            project.getHeatGrid().getHighlightedList().removeAll(project.getHeatGrid().getHighlightedList());
        }
        project.getHeatGrid().resetHighlightedNukl();
    }
    
    public void triggerRangeBox() {
        
        if(isSelected() == true) {
            setSelected(false);
        }
        else {
            setSelected(true);
        }
    }
    
}
