/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jakob
 */
public class SiteSetter extends BorderPane{

    int maxX = 2;
    int maxY = 2;
    GridPane rightInnerGrid;
    
    String[] histoneArray = {"H2A", "H2B", "H3", "H4"};
    
    GridPane outerGrid;
    int anzahl = 1;
    
    ChromosomEditor chromosomEditor;

    public static SiteBorderPane clipboard;
    public static SiteBorderPane zwischen;
    public static GridPane zwischenGrid;
    public static GridPane clipBoardGrid;
    public static int zwischenX;
    public static int zwischenY;
    public static String zwischenText;
    public static int clipX;
    public static int clipY;
    private String[] histoneOrder;
    
    private ArrayList<GridPane> gridList;
    
    public SiteSetter(ChromosomEditor chromosomEditor) {
        this.chromosomEditor = chromosomEditor;
        
        gridList = new ArrayList<>();
        histoneOrder = new String[4];
        
        setTop(new Label("Attribute-Sites:"));
        
            outerGrid = new GridPane();
            
            int x = 0;
            int y = 0;
            
            for(int i = 0; i < histoneArray.length; i++) {
                String title = histoneArray[i];
               
                SiteBorderPane innerPane = new SiteBorderPane(title, this, outerGrid);
                
                addDnD(innerPane);
                
                gridList.add(innerPane.getInnerGrid());
                
                outerGrid.add(innerPane, x, y);
                innerPane.setX(x);
                innerPane.setY(y);
                
                if((i+1)%2 == 0) {
                    y++;
                    x = 0;
                }
                else {
                    x++;
                }
            }
            
            setCenter(outerGrid);
    }
    
    public void next() {
        chromosomEditor.setHistoneMap(fillHistoneMap());
    }
    
    public HashMap<String, String[][]> fillHistoneMap() {
        HashMap<String, String[][]> histoneMap = new HashMap<>();
        histoneOrder = new String[4];
        
        SiteBorderPane sitePane;
        
        int x = 0, y = 0;
        
        for(int i = 0; i < 4; i++) {
            for(Node nod : outerGrid.getChildren()) {
                if(nod instanceof SiteBorderPane) {
                    sitePane = (SiteBorderPane)nod;
                    
                        if(outerGrid.getColumnIndex(sitePane) == x &&
                                outerGrid.getRowIndex(sitePane) == y) {

                            histoneOrder[i] = sitePane.getTitle();
                            if(!sitePane.getTitle().equals("")) {
                                histoneMap.put(sitePane.getTitle(), sitePane.getEntries());
                            }
//                            else {
//                                histoneMap.put(sitePane.getTitle(), null);
//                            }
                    }
                }
            }
            
            if((i+1)%2 == 0) {
                y++;
                x = 0;
            }
            else {
                x++;
            }
        }
        
        return histoneMap;
    }

    /**
     * @return the gridList
     */
    public ArrayList<GridPane> getGridList() {
        return gridList;
    }

    /**
     * @param gridList the gridList to set
     */
    public void setGridList(ArrayList<GridPane> gridList) {
        this.gridList = gridList;
    }

    public void addDnD(SiteBorderPane innerPane) {
                innerPane.setOnDragDetected(new EventHandler <MouseEvent>() {
                    public void handle(MouseEvent event) {
                        /* drag was detected, start drag-and-drop gesture*/

                        /* allow any transfer mode */
                        Dragboard db = startDragAndDrop(TransferMode.ANY);
    //
    //                        /* put a string on dragboard */
                        ClipboardContent content = new ClipboardContent();
                        content.putString(innerPane.getClass().getName());
                        db.setContent(content);

                        clipboard = innerPane;

//                        if(((SiteBorderPane)event.getSource()).getParent() instanceof javafx.scene.layout.GridPane && outerGrid != ((SiteBorderPane)event.getSource()).getParent()) {
//                            setOuterGrid((GridPane)((SiteBorderPane)event.getSource()).getParent());
//                            System.err.println("jupp");
//                        }

                        clipBoardGrid = innerPane.getOuterGrid();
                        clipX = innerPane.getX();
                        clipY = innerPane.getY();

                        event.consume();
                    }
                });

                innerPane.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data is dragged over the target */
    //                        System.out.println("onDragOver");
                        /* accept it only if it is  not dragged from the same node 
                         * and if it has a string data */
                        if (event.getGestureSource() != null &&
                                event.getDragboard().hasString() &&
                                    event.getDragboard().getString().equals("ChromosomEditor.SiteBorderPane") &&
                                        innerPane != clipboard) {

    //                            /* allow for both copying and moving, whatever user chooses */
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }

                        event.consume();
                    }
                });

                innerPane.setOnDragEntered(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture entered the target */

    //                        System.out.println("onDragEntered");
                        /* show to the user that it is an actual gesture target */
    //                        if (event.getGestureSource() != text)
    //                                event.getDragboard().hasString()) {
    //        //                    textField.seColor.GREEN);
    //                        }

                        event.consume();
                    }
                });

                innerPane.setOnDragExited(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* mouse moved away, remove the graphical cues */
        //                textField.setFill(Color.BLACK);
    //                        System.out.println("onDragExited");
                        event.consume();
                    }
                });

                innerPane.setOnDragDropped(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data dropped */
    //                        System.out.println("onDragDropped");
                        /* if there is a string data on dragboard, read it and use it */
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {

                            zwischen = innerPane;

    //                        System.err.println((SiteBorderPane)event.getSource().getParent().getClass().getName());
    //                        System.err.println(outerGrid.getClass().getName());

//                            if(((SiteBorderPane)event.getSource()).getParent() instanceof javafx.scene.layout.GridPane && outerGrid != ((SiteBorderPane)event.getSource()).getParent()) {
//                                setOuterGrid((GridPane)((SiteBorderPane)event.getSource()).getParent());
//                                System.err.println("jupp");
//                            }

                            zwischenGrid = innerPane.getOuterGrid();
                            zwischenX = innerPane.getX();
                            zwischenY = innerPane.getY();

                            zwischenGrid.getChildren().remove(zwischen);
    //                        parent.getZwischenGrid().getChildren().remove(parent.getClipboard());
                            clipBoardGrid.getChildren().remove(clipboard);
    //                        parent.getClipBoardGrid().getChildren().remove(parent.getZwischen());

                            if(zwischenGrid != clipBoardGrid) {
                                clipboard.showGrid(!clipboard.showGrid);
                            }
                            zwischenGrid.add(clipboard, zwischenX, zwischenY);
                            clipboard.setX(zwischenX);
                            clipboard.setY(zwischenY);

                            success = true;
                        }
                        /* let the source know whether the string was successfully 
                         * transferred and used */
                        event.setDropCompleted(success);

                        event.consume();
                    }
                });

                setOnDragDone(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture ended */
                        /* if the data was successfully moved, clear it */
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            if(zwischenGrid != clipBoardGrid) {
                                zwischen.showGrid(!zwischen.showGrid);
                            }
                            clipBoardGrid.add(zwischen, clipX, clipY);
                            zwischen.setX(clipX);
                            zwischen.setY(clipY);
    //                        
    //                        boolean bool = parent.getZwischen().showGrid;
    //                        
    //                        parent.getZwischen().showGrid(parent.getClipboard().showGrid);
    //                        parent.getClipboard().showGrid(bool);

                            GridPane pane = zwischenGrid;

                            zwischen.setOuterGrid(clipBoardGrid);
                            clipboard.setOuterGrid(pane);
                        }

                        event.consume();
                    }
                });
    }

    /**
     * @return the histoneOrder
     */
    public String[] getHistoneOrder() {
        return histoneOrder;
    }

}