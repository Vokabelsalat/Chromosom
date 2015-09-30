/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import application.Chromosom;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    
    Chromosom chromosom;
    
    public HeatLegend(Chromosom chromosom) {
        
        this.chromosom = chromosom;
        
        Spinner spin = new Spinner(0.0, 20000.0 , 0.0);
        spin.setEditable(true); 
        
        spin.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                int i = newValue.intValue();
                String newText = String.valueOf(i);
                int add;
                
                if(i > oldValue.intValue()) {
                    add = 1;
                }
                else {
                    add = -1;
                }
                
                if(!chromosom.hr.timeMap.containsKey(newText)) {
                    File file = new File(newText + ".txt");
                    
                    while(!file.exists() && i < 20000) {
                       i = i + add;
                       newText = String.valueOf(i);
                       file = new File(newText);
                    }
                    if(file.exists()) {
                        chromosom.hr.readLogFile(newText);
                        chromosom.showNewHeatGrid(newText);
                        spin.getValueFactory().setValue((double)i);
                    }
                }
                else {
                   chromosom.showNewHeatGrid(String.valueOf(i)); 
                }
            }
        });
        
        spin.getEditor().setOnKeyPressed(event -> {
           switch (event.getCode()) {
                case UP:
                    spin.increment();
                    break;
                case DOWN:
                    spin.decrement();
                    break;
            }
        });
        
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);


        ImageView imageView = new ImageView(createColorScaleImage(100, 20, Orientation.HORIZONTAL));
        
        getChildren().addAll(spin, sep, new Label("0.0"), imageView, new Label("1.0"));
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
