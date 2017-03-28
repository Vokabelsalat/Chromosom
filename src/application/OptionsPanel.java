/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import ChromosomEditor.EditorEnzyme;
import ChromosomEditor.EditorRule;
import Nukleosom.BigNukleosomNew;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author jakob
 */
public class OptionsPanel extends VBox{
    
    BigNukleosomNew nuklPane;
    int row = 5;
    ChromosomProject project;
    GridPane selectedGrid;
    Label usedRuleLabel;
    Label probabilityLabel;
    
    public OptionsPanel(ChromosomProject project) {
        this.project = project;
        
        this.setMinWidth(200);
        
        selectedGrid = new GridPane();
        
        selectedGrid.setHgap(5);
        selectedGrid.setVgap(5);
        selectedGrid.setPadding(new Insets(5));
        
        this.getChildren().add(new Label("Selected Nucleosomes:"));
        selectedGrid.add(new Label("Nucleosome:"), 0, 3);
        selectedGrid.add(new Label("Time Step:"), 0, 2);
        selectedGrid.add(new Label("Time:"), 0, 4);
        usedRuleLabel = new Label("Used Rule:");
        probabilityLabel = new Label("Probability");
        
        HBox hbox = new HBox();
        
        Button zoomIn = new Button("+");
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                project.getChromosom().zoomInNukleosoms(1);
            }
        });
        
        Button zoomOut = new Button("-");
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                project.getChromosom().zoomOutNukleosoms(1);
            }
        });
        
        hbox.getChildren().add(zoomIn);
        hbox.getChildren().add(zoomOut);
        
        GridPane zoomGrid = new GridPane();
        zoomGrid.setPadding(new Insets(5));
        zoomGrid.setHgap(5);
        zoomGrid.setVgap(5);
        zoomGrid.add(new Label("Zoom:"),0,0);
        zoomGrid.add(hbox, 0,1);
        
        Button showEnzymeOverlayButton = new Button("Enzyme Overlay");
        
        showEnzymeOverlayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                project.getChromosom().getRow().highlightOverlay();
            }
        });
        
        getChildren().addAll(selectedGrid, new Separator(), zoomGrid, new Separator(), showEnzymeOverlayButton);

    }
    
    public void addNukleosom(BigNukleosomNew nukleosom, int col) {
        
        StackPane stack = new StackPane();
        stack.setAlignment(Pos.CENTER);
        
        String color = "RED";
        
        if(col == 1) {
            color = "RED";
        }
        else {
            color = "GREEN";
        }
        
        stack.setStyle("-fx-border-color: " + color + "; -fx-border-width: 2px; -fx-padding: 6; -fx-spacing: 4;");
        
        ArrayList<Node> removeList = new ArrayList<>();
        for(Node nod : selectedGrid.getChildren()) {
            if(selectedGrid.getColumnIndex(nod) == col && selectedGrid.getRowIndex(nod) < row) {
                removeList.add(nod);
            }
        }
        
        selectedGrid.getChildren().removeAll(removeList);
        
//        nuklPane = nukleosom;
        nuklPane = new BigNukleosomNew(project, nukleosom.getHistoneMap(), nukleosom.getX(), nukleosom.getY(), project.nukleosomMinWidth * 5, project.nukleosomMinHeight * 5, true, true);
        stack.getChildren().add(nuklPane);
//        stack.getChildren().addAll(bgrect, whiterect, nuklPane);
        
        selectedGrid.add(stack, col, 1);
        
        //Nukleosom
        int nukl = nukleosom.getX();
        
        selectedGrid.add(new Label(String.valueOf(nukl)), col, 3);
        
        //Time Step
        int timeStep = nukleosom.getY();
        selectedGrid.add(new Label(String.valueOf(timeStep)), col, 2);
        
        //Time
        String time = project.getMetaInformations().get(String.valueOf(nukleosom.getY()))[0];
        selectedGrid.add(new Label(time), col, 4);
        
            ArrayList<Node> nodList = new ArrayList<>();
            for(Node nod : selectedGrid.getChildren()) {
                if(GridPane.getRowIndex(nod) == 6 || GridPane.getRowIndex(nod) == 5) {
                    if(GridPane.getColumnIndex(nod) == col) {
                        nodList.add(nod);
                    }
                }
            }
            selectedGrid.getChildren().removeAll(nodList);
        
        if(nukleosom.isChanged()) {
            
            //Row Rule
            String usedRule = project.getMetaInformations().get(String.valueOf(timeStep))[2];

            EditorRule myrule = project.getChromosom().getEditorRuleList().get(Integer.parseInt(usedRule)/2);
           
            EditorEnzyme myenzyme = project.getChromosom().getEnzymeMap().get(myrule.getEnzymeName());
                    
            EditorEnzyme enzyme = myenzyme.cloneEnzyme();
                    
            EditorRule rule = enzyme.getEnzymeMap().get(myrule.getNameTF().getText());
            
            for(EditorRule rul : enzyme.getEnzymeMap().values()) {
               enzyme.getRuleBox().getChildren().remove(rul);
            }
            
            Dialog<ButtonType> dialog = new Dialog<>();;
            dialog.setResizable(true);

            dialog.setTitle("Used Rule");

            rule.getSecondRow().checkStateColors();
            rule.setEditable(false);
            rule.setShowMode();
            
            if(!enzyme.getRuleBox().getChildren().contains(rule))
                enzyme.getRuleBox().getChildren().add(rule);
            
            enzyme.setShowMode();
            dialog.getDialogPane().setContent(enzyme);

            ButtonType buttonTypeClose = new ButtonType("Close");

            dialog.getDialogPane().getButtonTypes().addAll(buttonTypeClose);

            Hyperlink usedRuleValueLink = new Hyperlink();
            
            usedRuleValueLink.setOnAction(e -> {
                Optional<ButtonType> result = dialog.showAndWait();
                    if (result.get() == buttonTypeClose){
                    }
            });

            usedRuleValueLink.setText(rule.getNameTF().getText());

            if(!selectedGrid.getChildren().contains(usedRuleLabel)) {
                selectedGrid.add(usedRuleLabel, 0, 5);
            }
            selectedGrid.add(usedRuleValueLink, col, 5);
            
            //Wahrscheinlichkeit
            String probability = project.getMetaInformations().get(String.valueOf(nukleosom.getY()))[3];
            if(!selectedGrid.getChildren().contains(probabilityLabel)) {
                selectedGrid.add(probabilityLabel, 0, 6);
            }
            selectedGrid.add(new Label(probability), col, 6);
        }
        else {
            
            nodList = new ArrayList<>();
            for(Node nod : selectedGrid.getChildren()) {
                if(GridPane.getRowIndex(nod) == 6 || GridPane.getRowIndex(nod) == 5) {
                    if(GridPane.getColumnIndex(nod) == 1 || GridPane.getColumnIndex(nod) == 2) {
                        nodList.add(nod);
                    }
                }
            }
            if(nodList.isEmpty()) {
                for(Node nod : selectedGrid.getChildren()) {
                    if(GridPane.getRowIndex(nod) == 6 || GridPane.getRowIndex(nod) == 5) {
                        if(GridPane.getColumnIndex(nod) == 0) {
                            nodList.add(nod);
                        }
                    }
                }
                selectedGrid.getChildren().removeAll(nodList);
            }
        }
    }
}
