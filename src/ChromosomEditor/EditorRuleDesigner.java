/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jakob
 */
public class EditorRuleDesigner extends BorderPane{
    
    ChromosomEditor editor;
    ScrollPane scroll;
    VBox vbox;
    Button plus;
    RuleDesignerRow row;
    
    public EditorRuleDesigner(ChromosomEditor editor) {
        this.editor = editor;
        
        scroll = new ScrollPane();
        
        vbox = new VBox();
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.CENTER);
        
        plus = new Button("+");
        plus.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
                    vbox.getChildren().remove(plus);
                    RuleDesignerRow rule = new RuleDesignerRow(editor);
                    rule.getMinusButton().setOnAction(new EventHandler<ActionEvent>() {     
                        @Override public void handle(ActionEvent e) { 
                            vbox.getChildren().remove(rule);
                        } 
                    });
                    vbox.getChildren().add(rule);
                    vbox.getChildren().add(plus);
                }
        });

        row = new RuleDesignerRow(editor);
        row.getMinusButton().setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
                    vbox.getChildren().remove(row);
                } 
            });
        vbox.getChildren().add(0, row);
  
        vbox.getChildren().add(1,plus);
        
        scroll.setContent(vbox);
        
        this.setCenter(scroll);
    } 

    void next() {
//        System.err.println(row.getFirstRow().getNukleosomList().get(0).getHistoneList());//..get(0));
        row.setRule();
    }
    
    
    
}
