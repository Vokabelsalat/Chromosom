/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 *
 * @author Jakob
 */
public class HeatPairButton extends Button{
    
    private boolean selected = false;
//    private HeatOptionsPanel parent;
    ImageView pair, depair;
    Background backgroundPair;
    Background background;
    
    public HeatPairButton() {
        super();
//        this.parent = parent;
        
        Image image = new Image("file:depair.png");
        depair = new ImageView(image);
        
        Image imagePair = new Image("file:pair.png");
        pair = new ImageView(imagePair);
        
        setMaxSize(15.0, 15.0);
        this.setWidth(15.0);
        this.setHeight(15.0);
        
//        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
//        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
//        // new BackgroundImage(image, repeatX, repeatY, position, size)
//        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
//        // new Background(images...)
//        background = new Background(backgroundImage);
//        
//        this.setBackground(background);
//        
//        BackgroundImage backgroundImagePair = new BackgroundImage(imagePair, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
//        // new Background(images...)
//        backgroundPair = new Background(backgroundImagePair);
//        
//        this.setBackground(background);
        
//        depair.setFitHeight(15.0);
//        depair.setFitWidth(15.0);
        
        setGraphic(depair);
//        
//        depair.fitWidthProperty().bind(widthProperty()); 
//        pair.fitWidthProperty().bind(widthProperty()); 
//        pair.fitHeightProperty().bind(heightProperty());
//        depair.fitHeightProperty().bind(heightProperty());
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
        if(selected == true) {
//            this.setBackground(backgroundPair);
            setGraphic(pair);
        }
        else {
//            this.setBackground(background);
            setGraphic(depair);
        }
    }
    
    public void click() {
        if(isSelected() == false) {
            setSelected(true);
        }
        else {
            setSelected(false);
        }
    }
    
}
