/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
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
        
        this.add(new Label("Probability:"), 0, 1);
        this.add(new Label("Nukleosom:"), 0, 2);
        this.add(new Label("Enzyme:"), 0, 3);
        this.add(new Label("Channel:"), 0, 4);
//        this.add(new Label("Action:"), 0, 5);
        
        Pane emptyPane = new Pane();
        emptyPane.setMinSize(28,28);
        
        this.add(emptyPane, 0, 0);
        
        this.setMinSize(150, 0);
    }
    
}
