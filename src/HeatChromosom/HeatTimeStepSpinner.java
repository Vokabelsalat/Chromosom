/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

/**
 *
 * @author Jakob
 */
public class HeatTimeStepSpinner extends Spinner{
    
    private final HeatProject project;
    private final IntegerSpinnerValueFactory timeStepSpinnerValueFactory;
    int max, min;
    
    public HeatTimeStepSpinner(HeatProject project) {
        super(Integer.parseInt(project.getHeatReader().getFirstItemInTimeMap()),Integer.parseInt(project.getHeatReader().getLastItemInTimeMap()),Integer.parseInt(project.getStep()));
        
        this.project = project;
        
        timeStepSpinnerValueFactory = new IntegerSpinnerValueFactory(0, project.getHeatReader().getTimeMap().size()-1, Integer.parseInt(project.getHeatGrid().getTimeStep()), 1);
//        
//        setValueFactory(timeStepSpinnerValueFactory);
//        
        setEditable(true); 
//        
//        timeStepSpinnerValueFactory.setValue(Integer.parseInt(project.getHeatGrid().getTimeStep()));
//        getEditor().setText(String.valueOf(timeStepSpinnerValueFactory.getValue()));
        
        valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable,
                    Integer oldValue, Integer newValue) {
                
//                oldValue = timeStepSpinnerValueFactory.getValue();
                
                
//                if(newValue < timeStepSpinnerValueFactory.getMin()) {
//                    timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMin());
//                    showNewGrid(timeStepSpinnerValueFactory.getMin(), timeStepSpinnerValueFactory.getMin());
//                }
//                else if(newValue > timeStepSpinnerValueFactory.getMax()) {
//                    timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMax());
//                    showNewGrid(timeStepSpinnerValueFactory.getMax(), timeStepSpinnerValueFactory.getMax());
//                }
//                else {
                
//                if()
//                    showNewGrid(newValue, oldValue);
//                }
                try {
                    if(project.getHeatLegend().getTimePairButton().isSelected()) {
                        for(HeatProject pro : project.getChromosom().projectList) {
                            if(pro.getHeatReader().getTimeMap().containsKey(String.valueOf(newValue))) {
                                    pro.showNewHeatGrid(String.valueOf(newValue));
                                    if(pro != project) {
                                        pro.getHeatLegend().getHeatTimeSpinner().getEditor().setText(String.valueOf(newValue));
                                    }
                            }
                        }
                    }
                    else {
                        project.showNewHeatGrid(String.valueOf(newValue));
                    }
                    
                }
                catch(Exception e) {
                }
            }
        });
//        
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
//                case ENTER:
//                    int i;
//                    try {
//                        i = Integer.parseInt(getEditor().getText());
//
//                        if(i < timeStepSpinnerValueFactory.getMin()) {
//                            timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMin());
//                            getEditor().setText(String.valueOf(timeStepSpinnerValueFactory.getMin()));
//                            showNewGrid(timeStepSpinnerValueFactory.getMin(), timeStepSpinnerValueFactory.getMin());
//                        }
//                        else if(i > timeStepSpinnerValueFactory.getMax()) {
//                            timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMax());
//                            getEditor().setText(String.valueOf(timeStepSpinnerValueFactory.getMax()));
//                            showNewGrid(timeStepSpinnerValueFactory.getMax(), timeStepSpinnerValueFactory.getMax());
//                        }
//                        else {
//                            showNewGrid(i, i);
//                        }
//                    }
//                    catch (Exception e) {
//                        timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMin());
//                        getEditor().setText(String.valueOf(timeStepSpinnerValueFactory.getMin()));
//                        showNewGrid(0, 0);
//                    } 
//                    break;
//            }
//        });
    }

    /**
     * @return the timeStepSpinnerValueFactory
     */
    public SpinnerValueFactory.IntegerSpinnerValueFactory getTimeStepSpinnerValueFactory() {
        return timeStepSpinnerValueFactory;
    }
    
    private void showNewGrid(Integer newValue, Integer oldValue) {

        int add = 0;

        if(newValue < oldValue) {
            add = -1;
        }
        if(newValue > oldValue) {
            add = 1;
        }
        
        String newText = String.valueOf(newValue);
        
        while(!project.getHeatReader().getTimeMap().containsKey(newText)) {

            newValue = newValue + add;
            newText = String.valueOf(newValue);
            

        }
            project.getHeatReader().readLogFile(newText);
            project.showNewHeatGrid(newText);
            getValueFactory().setValue(newValue);
            getEditor().setText(newText);
    }
    
}
