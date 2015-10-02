/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Jakob
 */
public class HeatPairButton extends Button{
    
    private boolean selected = false;
//    private HeatOptionsPanel parent;
    ImageView pair, depair;
    
    public HeatPairButton() {
        super();
//        this.parent = parent;
        
        Image image = new Image("file:depair.png");
        depair = new ImageView(image);
        
        Image imagePair = new Image("file:pair.png");
        pair = new ImageView(imagePair);
        
        setMaxSize(15.0, 15.0);
        setGraphic(depair);
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected == false) {
            selected = true;
            setGraphic(pair);
        }
        else {
            selected = false;
            setGraphic(depair);
        }
    }
    
    public void click() {
        if(selected == false) {
            selected = true;
            setGraphic(pair);
        }
        else {
            selected = false;
            setGraphic(depair);
        }
    }
    
}
