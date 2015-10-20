/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author Jakob
 */
public class EditorBottomPane extends HBox{
    
    private Button back;
    private Button next;
    
    public EditorBottomPane(ChromosomEditor editor) {
            super();
            
            back = new Button("<<");
            back.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
                    editor.showEditorPane(editor.getPaneIndex()-1);
                } 
            });
            
            next = new Button();
            next.setText(">>");
            next.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
                    editor.showEditorPane(editor.getPaneIndex()+1);
                } 
            });
            
            setSpacing(10);
            getChildren().add(back);
            back.setVisible(false);
            getChildren().add(next);
            setStyle("-fx-border: 3px solid; -fx-border-color: black;");
    }

    /**
     * @return the back
     */
    public Button getBack() {
        return back;
    }

    /**
     * @param back the back to set
     */
    public void setBack(Button back) {
        this.back = back;
    }

    /**
     * @return the next
     */
    public Button getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Button next) {
        this.next = next;
    }
    
}
