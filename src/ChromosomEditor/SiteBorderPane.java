/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jakob
 */
public class SiteBorderPane extends BorderPane {
    
    private GridPane outerGrid;
    private GridPane innerGrid;
    private SiteSetter parent;
    
    private boolean showGrid = true;
    
    private String title;
    
    private int x;
    private int y;
    
    String[][] emptyText = {{"",""},{"",""}};
    
    public SiteBorderPane(String title, SiteSetter parent, GridPane outerGrid) {
        super();

        this.title = title;
        this.parent = parent;
        this.outerGrid = outerGrid;
        
        setTop(new Label(title));
        innerGrid = addAttributeTextfields(emptyText);

        setPadding(new Insets(10,10,10,10));
        setStyle("-fx-border-width: 1px; -fx-border-color: black;");
        setCenter(innerGrid);
    }
    
    public SiteBorderPane(String title, SiteSetter parent, GridPane outerGrid, String[][] emptyText) {
        super();

        this.title = title;
        this.parent = parent;
        this.outerGrid = outerGrid;
        this.emptyText = emptyText;
        
        setTop(new Label(title));
        innerGrid = addAttributeTextfields(emptyText);

        setPadding(new Insets(10,10,10,10));
        setStyle("-fx-border-width: 1px; -fx-border-color: black;");
        setCenter(innerGrid);
    }
        
    public GridPane addAttributeTextfields(String[][] array) {
            
        GridPane pan = new GridPane();

        int countX = 0, countY = 0;

        for(countY = 0; countY < array[0].length; countY++) {
            for(countX = 0; countX < array.length; countX++) {
                pan.add(new AttributeTextfield(array[countX][countY]), countX, countY);
            }
        }
        
        return pan;
    }

    /**
     * @return the innerGrid
     */
    public GridPane getInnerGrid() {
        return innerGrid;
    }

    /**
     * @param innerGrid the innerGrid to set
     */
    public void setInnerGrid(GridPane innerGrid) {
        this.innerGrid = innerGrid;
    }
    
    public void showGrid(boolean bool) {
        if(bool == true && !title.equals("")) {
            setCenter(innerGrid);
            setShowGrid(true);
        }
        else {
            setCenter(null);
            setShowGrid(false);
        }
    }
    
    /**
     * @return the outerGrid
     */
    public GridPane getOuterGrid() {
        return outerGrid;
    }

    /**
     * @param outerGrid the outerGrid to set
     */
    public void setOuterGrid(GridPane outerGrid) {
        this.outerGrid = outerGrid;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
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

    public String[][] getEntries() {
        
        int x = 0, y = 0;
        
        String[][] returnArray = new String[parent.maxX][parent.maxY];
        
        boolean hit = false;
        
        for(int i = 0; i < innerGrid.getChildren().size(); i++) {
            for(Node nod : innerGrid.getChildren()) {
                AttributeTextfield attributeTextfield;
                if(nod instanceof AttributeTextfield) {
                    attributeTextfield = (AttributeTextfield)nod;
                    
                    if(outerGrid.getColumnIndex(attributeTextfield) == x &&
                            outerGrid.getRowIndex(attributeTextfield) == y) {
                        returnArray[x][y] = attributeTextfield.textField.getText();
                        if(!returnArray[x][y].equals("")) {
                            hit = true;
                        }
                    }
                }
            }
            
            if((i+1)%parent.maxX == 0) {
                y++;
                x = 0;
            }
            else {
                x++;
            }
        } 
        
        if(hit == false) {
            return null;
        }
        
        return returnArray;
    }

    /**
     * @return the showGrid
     */
    public boolean isShowGrid() {
        return showGrid;
    }

    /**
     * @param showGrid the showGrid to set
     */
    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }
    
}
