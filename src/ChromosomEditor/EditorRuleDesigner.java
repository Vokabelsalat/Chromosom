/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jakob
 */
public class EditorRuleDesigner extends BorderPane{
    
    ChromosomEditor editor;
    ScrollPane scroll;
    RuleDesignerRow row;
    
    public EditorRuleDesigner(ChromosomEditor editor) {
        this.editor = editor;
        
        scroll = new ScrollPane();

        StateBorderPane statePane;
        
        
//        EditorNukleosom nukl = new EditorNukleosom(editor);
//        EditorNukleosomRow nuklRow = new EditorNukleosomRow(editor);
        row = new RuleDesignerRow(editor);
        
        scroll.setContent(row);
        
        this.setCenter(scroll);
    } 

    void next() {
//        System.err.println(row.getFirstRow().getNukleosomList().get(0).getHistoneList());//..get(0));
        row.setRule();
    }
    
    
    
}
