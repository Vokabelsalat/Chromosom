/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the chromosomEditor.
 */
package ChromosomEditor;

import java.io.File;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakob
 */
public class EditorParameters extends EditorPane{
    
    private ChromosomEditor chromosomEditor;
    private CheckBox cicleCheck;
    private CheckBox emptyCheck;
    private TextField timesField;
    private ComboBox timesComboBox;
    private TextArea emptyNucleosomeField;
    
    public EditorParameters(ChromosomEditor chromosomEditor) {
        this.chromosomEditor = chromosomEditor;
        this.paneName = "pf";
        
        AnchorPane anchor = new AnchorPane();
        VBox vbox = new VBox(); 
        
        AnchorPane ciclePane = new AnchorPane();
        cicleCheck = new CheckBox();
        Label checkLabel = new Label("Cicle: ");
        
        AnchorPane.setLeftAnchor(cicleCheck, 180.0);
        AnchorPane.setTopAnchor(cicleCheck, 0.0);
                
        AnchorPane.setLeftAnchor(checkLabel, 0.0);
        AnchorPane.setTopAnchor(checkLabel, 0.0);
        
        ciclePane.getChildren().addAll(checkLabel, cicleCheck);
        
        timesField = new TextField();
        timesField.setMinWidth(150);
        
        AnchorPane timesBox = new AnchorPane();
        ObservableList<String> options = FXCollections.observableArrayList(
            "Number Of Iterations",
            "Simulation Timesteps"
        );
        timesComboBox = new ComboBox(options);
        timesComboBox.setMinHeight(20);
        timesComboBox.setMinWidth(150);
        timesComboBox.setPromptText("Choose Time");

        AnchorPane.setLeftAnchor(timesField, 180.0);
        AnchorPane.setTopAnchor(timesField, 0.0);
                
        AnchorPane.setLeftAnchor(timesComboBox, 0.0);
        AnchorPane.setTopAnchor(timesComboBox, 0.0);
        
        timesBox.getChildren().addAll(timesComboBox, timesField);
        
        AnchorPane emptyBox = new AnchorPane();
        emptyCheck = new CheckBox();
        Label emptySitesLabel = new Label("Show empty sites:");
        
        AnchorPane.setLeftAnchor(emptyCheck, 180.0);
        AnchorPane.setTopAnchor(emptyCheck, 0.0);
                
        AnchorPane.setLeftAnchor(emptySitesLabel, 0.0);
        AnchorPane.setTopAnchor(emptySitesLabel, 0.0);
        
        emptyBox.getChildren().addAll(emptySitesLabel, emptyCheck);
        
        AnchorPane emptyNucleosomes = new AnchorPane();
        Label emptyNuclesomesLabel = new Label("Empty nucleosome:");
        emptyNucleosomeField = new TextArea();
        emptyNucleosomeField.setMaxWidth(250);
        emptyNucleosomeField.setMinHeight(80);
        emptyNucleosomeField.setPrefHeight(80);
        emptyNucleosomeField.setWrapText(true);
        emptyNucleosomeField.setText(chromosomEditor.getChromosomProject().getEmpty_nucleosome());
        
        AnchorPane.setLeftAnchor(emptyNucleosomeField, 180.0);
        AnchorPane.setTopAnchor(emptyNucleosomeField, 0.0);
                
        AnchorPane.setLeftAnchor(emptyNuclesomesLabel, 0.0);
        AnchorPane.setTopAnchor(emptyNuclesomesLabel, 0.0);
        
        emptyNucleosomes.getChildren().addAll(emptyNuclesomesLabel, emptyNucleosomeField);
        
        vbox.getChildren().addAll(ciclePane, emptyBox, timesBox, emptyNucleosomes);
        
        AnchorPane.setLeftAnchor(vbox, 20.0);
        AnchorPane.setTopAnchor(vbox, 20.0);
        AnchorPane.setRightAnchor(vbox, 20.0);
        AnchorPane.setBottomAnchor(vbox, 20.0);
        anchor.getChildren().add(vbox);
        vbox.setSpacing(20);
        vbox.setMinWidth(450);
        
        setCenter(anchor);
    }
    
    @Override
    public boolean next() {
     return true;   
    }
    
    @Override
    public boolean save() {
        return true;
    }
    
    
    @Override
    public void back() {
        
    }
    
    @Override
    public void reset() {
        
    }
    
    @Override
    public boolean writeToFile(File file) {
        
//            createEmptyNucleosome(doc);
//            createShowEmptySites(doc, true);
        
        return true;
    }
    
    

    
}
