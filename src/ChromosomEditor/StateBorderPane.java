/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jakob
 */
public class StateBorderPane extends BorderPane{

    private String title;
    String[][] data;
    ChromosomEditor editor;
    GridPane innerGrid;
    ArrayList<StateComboBox> comboList;
    
    //Anstelle des Editor vielleicht den Parent-Pane mit übergeben
    public StateBorderPane(String title, String[][] data, ChromosomEditor editor) {
        super();
        
        this.title = title;
        this.data = data;
        this.editor = editor;
        
        comboList = new ArrayList<>();
        innerGrid = addStateComboBoxes(data);
        
//        setPadding(new Insets(2,2,2,2));
        setStyle("-fx-border: 1px solid; -fx-border-color: black;");

        GridPane pan = new GridPane();
        pan.getChildren().add(new Label(title));
        pan.setAlignment(Pos.CENTER);
        
        setTop(pan);    
        setCenter(innerGrid);
        
    }

    private GridPane addStateComboBoxes(String[][] data) {
        GridPane pan = new GridPane();

        for(int y = 0; y < data[0].length; y++) {
            for(int x = 0; x < data.length; x++) {
                StateComboBox box = new StateComboBox(data[x][y]);
                pan.add(box, x, y);
                comboList.add(box);
            }
        }
        return pan;
    }
    
    public ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        
        for(StateComboBox box : comboList) {
            values.add(box.getValue());
        }
        return values;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
}
