/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import static java.time.OffsetTime.MAX;
import static java.time.OffsetTime.MIN;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Jakob
 */
public class HeatNukleosomGrid extends GridPane{
    
    HeatReader hr; 
    HashMap<String,ArrayList<ArrayList<Double>>> timeMap;
    
    public HeatNukleosomGrid(HeatReader hr, String timeStep) {
        this.hr = hr;
        this.timeMap = hr.timeMap;
        
        ArrayList<ArrayList<Double>> enzymeList = timeMap.get(timeStep);
        
        for(int enzyme = 0; enzyme < enzymeList.size(); enzyme++) {
            ArrayList<Double> nukleosomList = enzymeList.get(enzyme);
            for(int nukleosom = 0; nukleosom < nukleosomList.size(); nukleosom++) {
                add(new HeatNukleosom(nukleosomList.get(nukleosom), 9, 9, false, false), nukleosom, enzyme);
            }
        }
    }
    
    public Image createColorScaleImage(int width, int height, Orientation orientation) {
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        if (orientation == Orientation.HORIZONTAL) {
            for (int x=0; x<width; x++) {
                

                
                double value = 0.0 + (1.0 - 0.0) * x / width;
                Color color = Color.hsb(50,1.0,value,1.0).invert();
                
                if(x==0 || x == width-1) {
                    color = Color.BLACK;
                }
                
                for (int y=0; y<height; y++) {
                    pixelWriter.setColor(x, y, color);
                }
            }
        } else {
            for (int y=0; y<height; y++) {
                double value = 1.0 - (1.0 - 0.0) * y / height ;
                Color color = Color.hsb(50,1.0,value,1.0).invert();
                
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
