/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.A;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import test.AttributeLabel;

/**
 *
 * @author Jakob
 */
public class HistoneSetter extends BorderPane{

    public static String zwischen = "";
    int maxX = 2;
    int maxY = 2;
    GridPane rightInnerGrid;
    
    GridPane leftInnerGrid;
    
    Button back;
    
    int anzahl = 1;
    
    ChromosomEditor chromosomEditor;
    
    public HistoneSetter(ChromosomEditor chromosomEditor) {
        this.chromosomEditor = chromosomEditor;
        
        setTop(new Label("Histones:"));
        
            GridPane outerGrid = new GridPane();
            outerGrid.setHgap(40);
            outerGrid.setVgap(40);
            
            String[] histoneArray = {"H2A", "H2B", "H3", "H4"};
            String[] emptyText = {"","","",""};
            BorderPane leftPane = new BorderPane();    
            VBox rightPane = new VBox();
            
//            histoneList = new ArrayList<>();

            leftInnerGrid = addAttributeLabels(emptyText);
            leftInnerGrid.setHgap(5);
            leftInnerGrid.setVgap(5);
            
            rightInnerGrid = addAttributeLabels(histoneArray);
            
            rightInnerGrid.setHgap(5);
            rightInnerGrid.setVgap(5);

            HBox bottomPane = new HBox();
            back = new Button("<<");
            back.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
     
                } 
            });
            
            Button next = new Button();
            next.setText(">>");
            next.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
                    
                    int y = 0;
                    int x = 0;
                    
                    for(int i = 0; i < leftInnerGrid.getChildren().size(); i++) {
                        
                        String text = "";
                        
                        for(Node nod : leftInnerGrid.getChildren()) {
                            if(nod instanceof test.AttributeLabel && leftInnerGrid.getColumnIndex(nod) == x && 
                                     leftInnerGrid.getRowIndex(nod) == y) {
                                AttributeLabel lab = (AttributeLabel)nod;
                                text = lab.getText();
                            }
                        }       
                        
                        histoneArray[i] = text;
                        
                        if((i+1)%2 == 0) {
                            y++;
                            x = 0;
                        }
                        else {
                            x++;
                        }
                    }
                    
                    chromosomEditor.setHistoneArray(histoneArray);
                    chromosomEditor.showEditorPane(1);
                } 
            });
            back.setVisible(false);
            bottomPane.setSpacing(10);
            bottomPane.getChildren().add(back);
            bottomPane.getChildren().add(next);
            
            leftPane.setCenter(leftInnerGrid);
            
            rightPane.getChildren().add(rightInnerGrid);
            
//            splitPane.getItems().addAll(leftPane, rightPane);
            outerGrid.add(leftPane, 0, 0);
            outerGrid.add(rightPane, 1, 0);
            
            setBottom(bottomPane);
            setCenter(outerGrid);
            
    }
    
    public GridPane addAttributeLabels(String[] array) {
            
        GridPane pan = new GridPane();

        int countX = 0, countY = 0;

        for(String text : array) {
            pan.add(new AttributeLabel(text), countX, countY);

            if(countX % (maxX-1) == 0 && countX > 0) {
                countY++;
                countX = 0;
            }
            else {
                countX++;
            }
        }
        pan.setPadding(new Insets(1,1,1,1));
        pan.setStyle("-fx-border: 3px solid; -fx-border-color: black;");
        
        return pan;
    }

}
