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
    
    public HeatTimeStepSpinner(HeatProject project) {
        super(0.0, project.getHeatReader().getTimeMap().size()-1, 0.0);
        
        this.project = project;
        
        timeStepSpinnerValueFactory = new IntegerSpinnerValueFactory(0, project.getHeatReader().getTimeMap().size()-1, 0, 1);
        
        setValueFactory(timeStepSpinnerValueFactory);
        
        setEditable(true); 
        
        valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable,
                    Integer oldValue, Integer newValue) {
                
                System.err.println(newValue);
                
                if(newValue < timeStepSpinnerValueFactory.getMin()) {
                    timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMin());
                    showNewGrid(timeStepSpinnerValueFactory.getMin(), timeStepSpinnerValueFactory.getMin());
                }
                else if(newValue > timeStepSpinnerValueFactory.getMax()) {
                    timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMax());
                    showNewGrid(timeStepSpinnerValueFactory.getMax(), timeStepSpinnerValueFactory.getMax());
                }
                else {
                    showNewGrid(newValue, oldValue);
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
                    int i;
                    try {
                        i = Integer.parseInt(getEditor().getText());

                        if(i < timeStepSpinnerValueFactory.getMin()) {
                            timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMin());
                            getEditor().setText(String.valueOf(timeStepSpinnerValueFactory.getMin()));
                            showNewGrid(timeStepSpinnerValueFactory.getMin(), timeStepSpinnerValueFactory.getMin());
                        }
                        else if(i > timeStepSpinnerValueFactory.getMax()) {
                            timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMax());
                            getEditor().setText(String.valueOf(timeStepSpinnerValueFactory.getMax()));
                            showNewGrid(timeStepSpinnerValueFactory.getMax(), timeStepSpinnerValueFactory.getMax());
                        }
                        else {
                            showNewGrid(i, i);
                        }
                    }
                    catch (Exception e) {
                        timeStepSpinnerValueFactory.setValue(timeStepSpinnerValueFactory.getMin());
                        getEditor().setText(String.valueOf(timeStepSpinnerValueFactory.getMin()));
                        showNewGrid(0, 0);
                    } 
                    break;
            }
        });
    }

    /**
     * @return the timeStepSpinnerValueFactory
     */
    public SpinnerValueFactory.IntegerSpinnerValueFactory getTimeStepSpinnerValueFactory() {
        return timeStepSpinnerValueFactory;
    }
    
    private void showNewGrid(Integer newValue, Integer oldValue) {
        String newText = String.valueOf(newValue);
        int add = 0;

        if(newValue < oldValue) {
            add = -1;
        }
        if(newValue > oldValue) {
            add = 1;
        }

        if(!project.getHeatReader().getTimeMap().containsKey(newText)) {
            File file = new File(newText + ".txt");

            while(!file.exists() && newValue < 20000) {
                newValue = newValue + add;
                newText = String.valueOf(newValue);
                file = new File(newText);
            }
            if(file.exists()) {
                project.getHeatReader().readLogFile(newText);
                project.showNewHeatGrid(newText);
                getValueFactory().setValue(newValue);
            }
        }
        else {
            project.showNewHeatGrid(String.valueOf(newValue));
        }
    }
    
}
