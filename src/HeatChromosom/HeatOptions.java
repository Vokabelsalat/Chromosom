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
public class HeatOptions extends GridPane{
    
    public HeatOptions() {
        
        this.setHgap(7);
        this.setVgap(7);
        
        this.add(new Label("Value:"), 0, 1);
        this.add(new Label("X:"), 0, 2);
        this.add(new Label("Y:"), 0, 3);
        
        Pane emptyPane = new Pane();
        emptyPane.setMinSize(28,28);
        
        this.add(emptyPane, 0, 0);
        
        this.setMinSize(150, 0);
    }
    
}
