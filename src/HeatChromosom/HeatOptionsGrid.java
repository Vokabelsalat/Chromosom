/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jakob
 */
public class HeatOptionsGrid extends GridPane{
    
    public HeatOptionsGrid() {

        this.setHgap(5);
        this.setVgap(2);
        
        this.add(new Label("Scalevalue:"), 0, 1);
        this.add(new Label("Probability:"), 0, 2);
        this.add(new Label("Value:"), 0, 3);
        this.add(new Label("Nukleosom:"), 0, 4);
        this.add(new Label("Enzyme:"), 0, 5);
        this.add(new Label("Rule:"), 0, 6);
        this.add(new Label("Action:"), 0, 7);
        
        Pane emptyPane = new Pane();
        emptyPane.setMinSize(28,28);
        
        this.add(emptyPane, 0, 0);
        
        this.setMinSize(270, 0);
    }
    
}
