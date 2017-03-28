/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    
    private final ChromosomEditor chromosomEditor;
    private GridPane storage;
    private Spinner rowSpinner;
    private Spinner colSpinner;
    private ChangeListener colListener;
    private ChangeListener rowListener;
    
    public EditorOptions(ChromosomEditor chromosomEditor) {
        this.chromosomEditor = chromosomEditor;
        this.setSpacing(8);
        this.setPadding(new Insets(0,0,0,10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMinWidth(120);
        
            getChildren().add(new Label("Site Colums:"));
            
            colSpinner = new Spinner(1,30,2);
            colSpinner.setMaxWidth(70);
            
            colListener = new ChangeListener<Integer>() {
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
                    else if(newValue < oldValue && chromosomEditor.getSiteSetter().maxX > 1){
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
                    //Resize the scene after adding oder deleting cols
                    if(chromosomEditor.getSiteSetter().getGridList() != null && chromosomEditor.getSiteSetter().getCenter() != null) {
                        if(chromosomEditor.getSiteSetter().getGridList().get(0).getWidth() * 3 > chromosomEditor.getSiteSetter().getCenter().getLayoutX()
                                 && !chromosomEditor.getChromosom().getPrimaryStage().isMaximized()) {
                           chromosomEditor.getChromosom().getPrimaryStage().sizeToScene();
                        }
                    }
                }
            };
            
            colSpinner.valueProperty().addListener(colListener);
            
            getChildren().add(colSpinner);
            
            getChildren().add(new Label("Site Rows:"));
            
            rowSpinner = new Spinner(1,30,2);
            rowSpinner.setMaxWidth(70);
            
            rowListener = new ChangeListener<Integer>() {
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
                    else if(newValue < oldValue && chromosomEditor.getSiteSetter().maxY > 1){
                        
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
                    //Resize the scene after adding oder deleting rows
                    if(chromosomEditor.getSiteSetter().getGridList() != null && chromosomEditor.getSiteSetter().getCenter() != null) {
                        if(chromosomEditor.getSiteSetter().getGridList().get(0).getHeight() * 3 > chromosomEditor.getSiteSetter().getCenter().getLayoutY()
                                && !chromosomEditor.getChromosom().getPrimaryStage().isMaximized()) {
                           chromosomEditor.getChromosom().getPrimaryStage().sizeToScene();
                        }
                    }
                }
            };
            
            rowSpinner.valueProperty().addListener(rowListener);
            
            getChildren().add(rowSpinner);
            
            getChildren().add(new Label("Histone Reservoir:"));
            
            storage = new GridPane();

            int x = 0;
            int y = 0;
            
            for(int i = 0; i < 4; i++) {

                SiteBorderPane top = new SiteBorderPane("", chromosomEditor.getSiteSetter(), getStorage());
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
            
//            setStyle("-fx-border: 3px solid; -fx-border-color: black;");
    }


    /**
     * @return the storage
     */
    public GridPane getStorage() {
        return storage;
    }

    /**
     * @param storage the storage to set
     */
    public void setStorage(GridPane storage) {
        this.storage = storage;
    }

    /**
     * @return the rowSpinner
     */
    public Spinner getRowSpinner() {
        return rowSpinner;
    }

    /**
     * @return the colSpinner
     */
    public Spinner getColSpinner() {
        return colSpinner;
    }

    /**
     * @return the colListener
     */
    public ChangeListener getColListener() {
        return colListener;
    }

    /**
     * @return the rowListener
     */
    public ChangeListener getRowListener() {
        return rowListener;
    }
    
}
