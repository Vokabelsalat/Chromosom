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
            
//            System.err.println("triggerRange");
            
            if(newValue == true) {
//                    for(HeatProject pro : project.getChromosom().projectList) {
//                        pro.getHeatGrid().highlightNear(pro.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().getValue(), pro.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());
//                        if(pro != project) {
//                            pro.getHeatOptionsPanel().getRangeBox().setSelected(true);
//                        }
//                    }
                project.getHeatGrid().highlightNear(project.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().getValue(), project.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());
            }
            else {
                if(!project.getHeatGrid().getHighlightedList().isEmpty()) {
                    project.getHeatGrid().getHighlightedList().removeAll(project.getHeatGrid().getHighlightedList());
                }
                project.getHeatGrid().resetHighlightedNukl();
            }
//                
//                if(pairButton.isSelected()) {
//                    for(HeatProject pro : project.getChromosom().projectList) {
//                        pro.getHeatGrid().highlightNear(doub, rangeSpin.getRangeSpinValueFactory().getValue());
//                        pro.getHeatOptionsPanel().rangeBox.setSelected(true);
//                        pro.getHeatOptionsPanel().oldProb = doub;
//                    }
//                }
//                else {
//                    project.getHeatGrid().highlightNear(doub, rangeSpin.getRangeSpinValueFactory().getValue());
//                    rangeBox.setSelected(true);
//                    oldProb = doub; 
//                }
//            }
//            else {
//                
//                if(pairButton.isSelected()) {
//                    for(HeatProject pro : project.getChromosom().projectList) {
//                        if(!pro.getHeatGrid().getHighlightedList().isEmpty()) {
//                            pro.getHeatGrid().getHighlightedList().removeAll(pro.getHeatGrid().getHighlightedList());
//                        }
//                        pro.getHeatGrid().resetHighlightedNukl();
//                        pro.getHeatOptionsPanel().oldProb = pro.getHeatOptionsPanel().oldProb + 2.0;
//                        pro.getHeatOptionsPanel().rangeBox.setSelected(false);
//                    }
//                }
//                else {
//                    if(!project.getHeatGrid().getHighlightedList().isEmpty()) {
//                        project.getHeatGrid().getHighlightedList().removeAll(project.getHeatGrid().getHighlightedList());
//                    }
//                    project.getHeatGrid().resetHighlightedNukl();
//                    oldProb = oldProb + 2.0;
//                }
//            }
        });
        
        
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
