/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jakob
 */
public class EditorOptions extends VBox{
    
    ChromosomEditor chromosomEditor;
    
    public EditorOptions(ChromosomEditor chromosomEditor) {
        this.chromosomEditor = chromosomEditor;
        
            getChildren().add(new Label("Cols:"));
            
            Spinner colSpinner = new Spinner(1,30,2);
            colSpinner.setMaxWidth(70);
            
            colSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observable,
                        Integer oldValue, Integer newValue) {
                    if(newValue > oldValue) {
                        for(GridPane innerGrid : chromosomEditor.getSiteSetter().getGridList()) {
                            for(int y = 0; y < chromosomEditor.getSiteSetter().maxY; y++) {
                                innerGrid.add(new AttributeTextfield(""), chromosomEditor.getSiteSetter().maxX, y);
                            }
                        }
                        chromosomEditor.getSiteSetter().maxX = newValue;
                    }
                    else if(chromosomEditor.getSiteSetter().maxX > 1){
                        for(GridPane innerGrid : chromosomEditor.getSiteSetter().getGridList()) {
                            ArrayList<Node> nodeList = new ArrayList<>();

                            for(Node nod : innerGrid.getChildren()) {
                                if(innerGrid.getColumnIndex(nod) == chromosomEditor.getSiteSetter().maxX-1) {
                                    nodeList.add(nod);
                                }
                            }

                            innerGrid.getChildren().removeAll(nodeList);
                        }
                        chromosomEditor.getSiteSetter().maxX = newValue;
                    }
                    
                }
            });
            
            getChildren().add(colSpinner);
            
            getChildren().add(new Label("Rows:"));
            
            Spinner rowSpinner = new Spinner(1,30,2);
            rowSpinner.setMaxWidth(70);
            
            rowSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observable,
                        Integer oldValue, Integer newValue) {
                    if(newValue > oldValue) {
                        for(GridPane innerGrid : chromosomEditor.getSiteSetter().getGridList()) {
                            for(int x = 0; x < chromosomEditor.getSiteSetter().maxX; x++) {
                                innerGrid.add(new AttributeTextfield(""), x, chromosomEditor.getSiteSetter().maxY);
                            }
                        }
                        chromosomEditor.getSiteSetter().maxY = newValue;
                    }
                    else if(chromosomEditor.getSiteSetter().maxY > 1){
                        
                        for(GridPane innerGrid : chromosomEditor.getSiteSetter().getGridList()) {
                            ArrayList<Node> nodeList = new ArrayList<>();

                            for(Node nod : innerGrid.getChildren()) {
                                if(innerGrid.getRowIndex(nod) == chromosomEditor.getSiteSetter().maxY-1) {
                                    nodeList.add(nod);
                                }
                            }
                            innerGrid.getChildren().removeAll(nodeList);
                        }
                        chromosomEditor.getSiteSetter().maxY = newValue;
                    }
                    
                }
            });
            
            getChildren().add(rowSpinner);
            
            GridPane storage = new GridPane();
//            
            int x = 0;
            int y = 0;
            
            for(int i = 0; i < 4; i++) {

                SiteBorderPane top = new SiteBorderPane("", chromosomEditor.getSiteSetter(), storage);
                top.showGrid(false);
                
                chromosomEditor.getSiteSetter().addDnD(top);
                
                storage.add(top, x, y);

                top.setX(x);
                top.setY(y);
                
                if((i+1)%2 == 0) {
                    y++;
                    x = 0;
                }
                else {
                    x++;
                }
            }

            getChildren().add(storage);
            
            setStyle("-fx-border: 3px solid; -fx-border-color: black;");
    }
    
}
