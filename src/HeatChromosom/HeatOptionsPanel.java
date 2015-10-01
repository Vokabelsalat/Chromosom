/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import application.Chromosom;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.DoubleSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jakob
 */
public class HeatOptionsPanel extends VBox{
    
    private HeatProject project;
    private double oldProb = 2.0;
    private CheckBox rangeBox;
    private Spinner nearSpin;
    private Spinner rangeSpinner;
    public DoubleSpinnerValueFactory rangeSpinValueFactory;
    public DoubleSpinnerValueFactory nearSpinValueFactory;
    private int startRow = 4;
    private HeatOptionsGrid selectedNukleosoms;
    private HeatOptionsGrid resultNukleosom;
    
    public HeatOptionsPanel(HeatProject project) {
        
        this.project = project;
        this.setSpacing(3);
        this.setMinSize(220, 0);
        
//        setStyle("-fx-border: 3px solid; -fx-border-color: black;");
        
        getChildren().add(new Label("Result:"));
        
        resultNukleosom = new HeatOptionsGrid();
        getChildren().add(resultNukleosom);
        
        Separator sep = new Separator();
        getChildren().add(sep);
        
        getChildren().add(new Label("Selected Items:"));
        
        selectedNukleosoms = new HeatOptionsGrid();
        getChildren().add(selectedNukleosoms);
        
        Separator sep2 = new Separator();
        getChildren().add(sep2); 
        
        getChildren().add(new Label("Probability Highlight:"));
        
        HBox hbox = new HBox();
        
        rangeBox = new CheckBox();

        nearSpin = new Spinner(0.0, 1.0, 0.0, 0.01);
        rangeSpinner = new Spinner(0.0, 1.0, 0.0, 0.01);
        
        nearSpinValueFactory = new DoubleSpinnerValueFactory(0.0, 1.0, 0.0, 0.01);
        nearSpin.setValueFactory(nearSpinValueFactory);
        
        rangeSpinValueFactory = new DoubleSpinnerValueFactory(0.0, 1.0, 0.0, 0.01);
        rangeSpinner.setValueFactory(rangeSpinValueFactory);
        
        rangeBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            
            if(new_val == true) {
                String text = nearSpin.getEditor().getText().replaceAll(",", ".");
                double doub = Double.parseDouble(text);
                project.getHeatGrid().highlightNear(doub, rangeSpinValueFactory.getValue());
                oldProb = doub;
            }
            else {
                if(!project.getHeatGrid().getHighlightedList().isEmpty()) {
                    project.getHeatGrid().getHighlightedList().removeAll(project.getHeatGrid().getHighlightedList());
                }
                project.getHeatGrid().resetHighlightedNukl();
                oldProb = oldProb + 2.0;
            }
        });
        
        nearSpin.setEditable(true); 
        
        nearSpin.setMaxWidth(70.0);
        
        nearSpin.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable,
                    Double oldValue, Double newValue) {
                try {
                    oldProb = newValue;//.doubleValue();
                    project.getHeatGrid().highlightNear(newValue, rangeSpinValueFactory.getValue());//.doubleValue());
                    getRangeBox().setSelected(true);  
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
                            if(project.getHeatGrid().getHighlightedList() != null && !project.getHeatGrid().getHighlightedList().isEmpty()) {
                                project.getHeatGrid().getHighlightedList().removeAll(project.getHeatGrid().getHighlightedList());
                            }
                            project.getHeatGrid().resetHighlightedNukl();
                            oldProb = oldProb + 2.0;
                            rangeBox.setSelected(false);
                        }
                        else {
                            project.getHeatGrid().highlightNear(doub, rangeSpinValueFactory.getValue());
                            oldProb = doub;
                            rangeBox.setSelected(true);
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
//                nearSpinValueFactory.setAmountToStepBy(newValue);
                if(getRangeBox().isSelected() == true) {
                    project.getHeatGrid().highlightNear(nearSpinValueFactory.getValue(), newValue);
                }
            }
        });
        
        rangeSpinner.getEditor().setOnKeyPressed(event -> {
           switch (event.getCode()) {
                case UP:
                    rangeSpinner.increment();
                    break;
                case DOWN:
                    rangeSpinner.decrement();
                    break;
           }
        });
        
        rangeSpinner.setEditable(true);
        rangeSpinner.setMaxWidth(70);
        hbox.setSpacing(18);
        hbox.getChildren().addAll(rangeBox, nearSpin);
        
        HBox rangeHBox = new HBox();
        rangeHBox.getChildren().addAll(new Label("Range:"), rangeSpinner);
        rangeHBox.setSpacing(3);
        
        getChildren().addAll(hbox, rangeHBox);
    }
    
    void addHeatNukleosomToOptionPanel(HeatNukleosom nukl, int col, int row) {
         
            if(getChildren().get(row) instanceof javafx.scene.layout.GridPane) {
                GridPane table = (GridPane)getChildren().get(row);
                
                ArrayList<Node> nodeList = new ArrayList<>();
                
                for(Node nod : table.getChildren()) {
                    if(table.getColumnIndex(nod) == col) {
                        nodeList.add(nod);
                    }
                }
                
                for(Node nod : nodeList) {
                    table.getChildren().remove(nod);
                }
                
                StackPane pane = new StackPane();
                
                pane.setPrefSize(28, 28);
                pane.setAlignment(Pos.CENTER_LEFT);
                
                Color color = Color.GRAY;

                if(col == 1 && row >= getStartRow()) {
                    color = HeatProject.RED;
                } 
                else if(col > 1) {
                    color = HeatProject.GREEN;
                }

                Rectangle bg = new Rectangle(28,28,color);
                bg.setTranslateX(0);
                bg.setTranslateY(0);


                pane.getChildren().add(bg);

                Rectangle fg = new Rectangle(24, 24, Color.WHITE);
                fg.setTranslateX(2);
//                fg.setTranslateY(1);

                pane.getChildren().add(fg); 
                
                HeatNukleosom newNukl = new HeatNukleosom(nukl.value, nukl.x, nukl.y, 18, 18, false, false, "");
                newNukl.setTranslateX(5);
                newNukl.setTranslateY(5);
                
                pane.getChildren().add(newNukl);
                
                table.add(pane, col, 0);
                table.add(new Label(String.valueOf(newNukl.value).replace(".", ",")), col, 1);
               
                //Nukleosom
                table.add(new Label(String.valueOf(nukl.x)), col, 2);
                
                //Für die Action
//                int y = (nukl.y/2);
                
                //Enzyme
                table.add(new Label(String.valueOf(project.getHeatReader().getChannelList().get(nukl.y))), col, 3);
                
                //Channel
                table.add(new Label(String.valueOf(nukl.y)), col, 4);
                
//                //Action
//                String action;
//                if(nukl.y%2 == 0) {
//                    action = "activation";
//                }
//                else {
//                    action = "deactivation";
//                }
//                
//                table.add(new Label(action), col, 5);
            }
        
    }
    
    public void resetOptionPanel() {
        int index = getChildren().indexOf(selectedNukleosoms);
        selectedNukleosoms = new HeatOptionsGrid();
        getChildren().remove(index);
        getChildren().add(index, selectedNukleosoms);
    }

    /**
     * @return the rangeBox
     */
    public CheckBox getRangeBox() {
        return rangeBox;
    }

    /**
     * @return the startRow
     */
    public int getStartRow() {
        return startRow;
    }
    
}
