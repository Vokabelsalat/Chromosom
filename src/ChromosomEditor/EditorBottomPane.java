/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Die untere Kontrollleiste innerhalb des NucleosomeEditors
 * @author Jakob
 */
public class EditorBottomPane extends HBox{
    
    private Button back;
    private Button next;
    private Button save;
    private Button saveAll;
    
    public EditorBottomPane(ChromosomEditor editor) {
            super();
            
            this.setPadding(new Insets(5,10,15,10));
            
            this.setAlignment(Pos.CENTER);
            back = new Button("<< Previous");
            
            next = new Button();
            next.setText("Next >>");
            
            save = new Button();
            save.setText("Save");
            
            saveAll = new Button();
            saveAll.setText("Save All");
            saveAll.setVisible(false);
            
            setSpacing(10);
            getChildren().addAll(back, saveAll, save, next);
            
//            save.setVisible(false);
//            back.setVisible(false);
//            setStyle("-fx-border-width: 3px solid; -fx-border-color: black;");
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

    /**
     * @return the save
     */
    public Button getSave() {
        return save;
    }

    /**
     * @param save the save to set
     */
    public void setSave(Button save) {
        this.save = save;
    }

    /**
     * @return the saveAll
     */
    public Button getSaveAll() {
        return saveAll;
    }

    /**
     * @param saveAll the saveAll to set
     */
    public void setSaveAll(Button saveAll) {
        this.saveAll = saveAll;
    }
    
}
