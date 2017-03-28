/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Jakob
 */
public class AttributeLabel extends BorderPane{
    
    static String zwischen;
    Text label;
    
    public AttributeLabel(String text) {
        this.label = new Text(text);
        
        label.setFill(Color.BLACK);
        
        this.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = label.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString(label.getText());
                db.setContent(content);

                event.consume();
            }
        });
        
        this.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != text &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });
        
        this.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != text &&
                        event.getDragboard().hasString()) {
                    label.setFill(Color.GREEN);
                }
                
                event.consume();
            }
        });

        this.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                label.setFill(Color.BLACK);
                
                event.consume();
            }
        });
        
        this.setOnDragDropped(new EventHandler <DragEvent>() {
            
            
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    
                    
                    zwischen = label.getText();
                    label.setText(db.getString());
                    
                    success = true;
                }
                event.setDropCompleted(success);
                
                event.consume();
            }
        });

        this.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    label.setText(zwischen);
                }
                
                event.consume();
            }
        });
     this.setPrefSize(30,30);
     this.setCenter(label);
     
        setStyle("-fx-border-width: 2px; -fx-border-color: black;");
        
    }
    
    public String getText() {
        return label.getText();
    }
    
    public void setText(String labelText) {
        label.setText(labelText);
    }
}
