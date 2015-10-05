/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Jakob
 */
public class HeatLegend extends HBox{
    
    HeatProject project;
    
    public HeatLegend(HeatProject project) {
        
        this.project = project;
        
        HeatTimeStepSpinner spin = new HeatTimeStepSpinner(project);
        
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);

        if(project.getID() == 0) {

            ImageView imageView = new ImageView(createColorScaleImage(120, 20, Orientation.HORIZONTAL));
        
            getChildren().addAll(spin, sep, new Label("0.0"), imageView, new Label("1.0"));
        }
        else {
            getChildren().add(spin); 
        }
        
        setSpacing(5.0);
        
//        setStyle("-fx-border: 3px solid; -fx-border-color: black;");
    }
    
    public Image createColorScaleImage(int width, int height, Orientation orientation) {
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        
        double step = 0.05;
        
//        double oldValue = step;
        
        if (orientation == Orientation.HORIZONTAL) {
            for (int x=0; x<width; x++) {
                
                double value = 0.0 + (1.0 - 0.0) * x / width;
                
//                if(value < oldValue) {
//                    value = oldValue - step;
//                }
//                else {
//                    oldValue = oldValue + step;
//                }
                
                Color color = HeatNukleosom.generateColorForValue(value);
                
                if(x==0 || x == width-1) {
                    color = Color.BLACK; 
                }
                
                for (int y=0; y<height; y++) {
                    pixelWriter.setColor(x, y, color);
                }
            }
        } else {
            for (int y=0; y <height; y++) {
                double value = 1.0 - (1.0 - 0.0) * y / height ;
                
//                if(value < oldValue) {
//                    value = oldValue - step;
//                }
//                else {
//                    oldValue = oldValue + step;
//                }
                
                Color color = HeatNukleosom.generateColorForValue(value);
                
                if(y==0 || y == height-1) {
                    color = Color.BLACK;
                }
                for (int x=0; x<width; x++) {
                    pixelWriter.setColor(x, y, color);
                }
            }
        }
        return image ;
    }
    
}
