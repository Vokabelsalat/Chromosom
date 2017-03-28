/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
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
public class AttributeTextfield extends BorderPane{
    
    TextField textField;
    
    String clip;
    public static String zwischen;
    GridPane parent;
    
    public AttributeTextfield(String text) {
        this.textField = new TextField(text);
        textField.setEditable(true);
        textField.setMinSize(70, 20);
        textField.setMaxSize(70, 20);
        
        textField.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
//                System.out.println("onDragDetected");

                /* allow any transfer mode */
                Dragboard db = textField.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(textField.getText());
                db.setContent(content);

                event.consume();
            }
        });
        
        textField.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
//                System.out.println("onDragOver");
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != text &&
                        event.getDragboard().hasString() &&
                             !event.getDragboard().getString().equals("ChromosomEditor.SiteBorderPane")) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });
        
        textField.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                
//                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != text &&
                        event.getDragboard().hasString()) {
//                    textField.seColor.GREEN);
                }
                
                event.consume();
            }
        });

        textField.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
//                textField.setFill(Color.BLACK);
                
                event.consume();
            }
        });
        
        textField.setOnDragDropped(new EventHandler <DragEvent>() {
            
            public void handle(DragEvent event) {
                /* data dropped */
//                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    
                    zwischen = textField.getText();
                    textField.setText(db.getString());
                    textField.requestFocus();
                    textField.positionCaret(textField.getText().length());
                    
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
            }
        });

        textField.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    textField.setText(zwischen);
                }
                
                event.consume();
            }
        });
        
        this.setPrefSize(30,30);
        this.setCenter(textField);
     
        setStyle("-fx-border-width: 0.5px; -fx-border-color: black;");
        
    }
    
    public String getText() {
        return textField.getText();
    }
    
    public void setText(String labelText) {
        textField.setText(labelText);
    }
    
}
