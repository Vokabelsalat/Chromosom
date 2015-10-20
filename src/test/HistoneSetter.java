/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import ChromosomEditor.AttributeTextfield;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/** 
 *
 * @author Jakob
 */
public class HistoneSetter extends Application{

    GridPane root;
    
    BorderPane zwischen;
    BorderPane clip;
    int zwischenX, zwischenY, clipX, clipY;
            
    @Override
    public void start(Stage primaryStage) {
            root = new GridPane();
            
            int x = 0;
            int y = 0;
            
            for(int i = 0; i < 4; i++) {
                
                BorderPane borderPane = new BorderPane();
                
                borderPane.setCenter(new AttributeTextfield(""));
                borderPane.setPadding(new Insets(20,20,20,20));
                
                borderPane.setOnDragDetected(new EventHandler <MouseEvent>() {
                    public void handle(MouseEvent event) {
                        /* drag was detected, start drag-and-drop gesture*/

                        /* allow any transfer mode */
                        Dragboard db = borderPane.startDragAndDrop(TransferMode.ANY);
//
//                        /* put a string on dragboard */
                        ClipboardContent content = new ClipboardContent();
                        content.putString(borderPane.getClass().getName());
                        db.setContent(content);
                        
                        clip = borderPane;
                        clipX = root.getColumnIndex(clip);
                        clipY = root.getRowIndex(clip);

                        event.consume();
                    }
                });
                
                borderPane.setOnDragOver(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data is dragged over the target */
//                        System.out.println("onDragOver");
                        /* accept it only if it is  not dragged from the same node 
                         * and if it has a string data */
//                        if (event.getGestureSource() != text &&
//                                event.getDragboard().hasString()) {
//                            /* allow for both copying and moving, whatever user chooses */
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                        }

                        event.consume();
                    }
                });
                
                borderPane.setOnDragEntered(new EventHandler <DragEvent>() {
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
                
                borderPane.setOnDragExited(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* mouse moved away, remove the graphical cues */
        //                textField.setFill(Color.BLACK);
//                        System.out.println("onDragExited");
                        event.consume();
                    }
                });
                
                borderPane.setOnDragDropped(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* data dropped */
//                        System.out.println("onDragDropped");
                        /* if there is a string data on dragboard, read it and use it */
                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        if (db.hasString()) {

                            zwischen = borderPane;
                            zwischenX = root.getColumnIndex(zwischen);
                            zwischenY = root.getRowIndex(zwischen);
                            
                            root.getChildren().remove(zwischen);
                            root.getChildren().remove(clip);
                            root.add(clip, zwischenX, zwischenY);
                            
                            success = true;
                        }
                        /* let the source know whether the string was successfully 
                         * transferred and used */
                        event.setDropCompleted(success);

                        event.consume();
                    }
                });
                
                borderPane.setOnDragDone(new EventHandler <DragEvent>() {
                    public void handle(DragEvent event) {
                        /* the drag-and-drop gesture ended */
                        System.out.println("onDragDone");
                        /* if the data was successfully moved, clear it */
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            root.add(zwischen, clipX, clipY);
                        }

                        event.consume();
                    }
                });
                
                root.add(borderPane, x, y);
            
                if((i+1)%2 == 0) {
                    y++;
                    x = 0;
                }
                else {
                    x++;
                }
            }
            
            root.setGridLinesVisible(true);
            
            Scene scene = new Scene(root, 500, 500);
            primaryStage.setTitle("HistoneSetter");
            primaryStage.setScene(scene);
            primaryStage.show();

    }


    public static void main(String[] args) {
            launch(args);
    }
    
}
 