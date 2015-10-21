/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 *
 * @author Jakob
 */
public class HeatNukleosom extends Pane {
	
    int width; 
    int height;
    double value;
    Rectangle rect;
    Rectangle highlightRect;
    double oldStrokeWidth;
    int x, y;
    private boolean selected;
    private double probability;
    private double originalValue;
    
    private double BLUE_HUE = Color.rgb(200,180,0).getHue();;
    private double RED_HUE = Color.rgb(215,170,0).getHue();
    
    public HeatNukleosom(double value, int x, int y, int width, int height, String strokeType, double probability, double originalValue) {

        highlightRect = new Rectangle(width, height , Color.rgb(255,150,0)); 
        this.value = value;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.probability = probability;
        this.originalValue = originalValue;
        
        setPrefSize(width, height);

        Paint color;

        color = generateColorForValue(value);

        rect = new Rectangle(width, height , color); 

        rect.setX(0);
        rect.setY(0);
        
        double rectStrokeWidth = (HeatProject.GridLineStrokeWidth / HeatProject.HeatNukleosomWidth) * width;
        
        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(rectStrokeWidth);
        
        oldStrokeWidth = rect.getStrokeWidth();
        
        rect.setStrokeType(StrokeType.INSIDE);
        highlightRect.setStrokeType(StrokeType.INSIDE);
        
        getChildren().add(rect);
        
        Line line;
        double strokeWidth = (0.8 / HeatProject.HeatNukleosomWidth) * width;
        double add = strokeWidth + rect.strokeWidthProperty().doubleValue();
        
        if(strokeType.equals("HORIZONTAL")){
            line = new Line(0,0,width,0);
            line.setStroke(Color.GRAY);
            line.setStrokeWidth(strokeWidth);
            getChildren().add(line);
            line = new Line(0,height-add,width,height-add);
            line.setStroke(Color.GRAY);
            line.setStrokeWidth(strokeWidth);
            getChildren().add(line);
        }
        
        if(strokeType.equals("VERTICAL")){
            line = new Line(0,0,0,height);
            line.setStrokeWidth(strokeWidth);
            line.setStroke(Color.GRAY);
            getChildren().add(line);
            line = new Line(width-add,0,width-add,height);
            line.setStrokeWidth(strokeWidth);
            line.setStroke(Color.GRAY);
            getChildren().add(line);
        }
    }
    
    public void highlight() {
        
        highlightRect.setOpacity(0.9);
        highlightRect.setStrokeWidth(rect.getStrokeWidth());
        highlightRect.setStroke(rect.getStroke());

        highlightRect.setFill(getColorForValue(value)); 
        getChildren().add(highlightRect);
    }
    
//    public void highlight() {
//        highlightRect.setOpacity(value + 0.25);
//        highlightRect.setStrokeWidth(rect.getStrokeWidth());
//        highlightRect.setStroke(rect.getStroke());
//
//        getChildren().add(highlightRect);
//    }
    
    private Color getColorForValue(double value) {
        if (value < 0.0 || value > 1.0) {
            return Color.BLACK ;
        }
        double hue = BLUE_HUE + (RED_HUE - BLUE_HUE) * (value - 0.0) / (1.0 - 0.0) ;
        return Color.hsb(hue, 1.0, 1.0);
    }

    
    public void deHighlight() {
       if(highlightRect != null) {
            getChildren().remove(highlightRect);
       }
    }
    
    public void setStrokeColor(Color col) {
        rect.setStroke(col);
        highlightRect.setStroke(col);
    } 
    
    public void setStrokeWidth(double doub) {
        rect.setStrokeWidth(doub);
        highlightRect.setStrokeWidth(doub);
    } 
    
    public static Color generateColorForValue(double orgValue) {
        Color cool = Color.hsb(60,1.0,categorizeValue(orgValue),1.0).invert();
//        if(orgValue != 0.0) {
//            cool = cool.deriveColor(0.0, 1.0, 0.95, 1.0);
//        }
        return cool;
    }
    
    public static Color generateHighlightColorForValue(double orgValue) {
        Color cool = Color.hsb(180,1.0,categorizeValue(orgValue),1.0).invert();
//        if(orgValue != 0.0) {
//            cool = cool.deriveColor(0.0, 1.0, 0.95, 1.0);
//        }
        
        return cool;
    }
    
    public static double categorizeValue(double value) {
        double face = Math.floor(value/0.05);
        return face * 0.05;
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
    }
    
    public void select(Color color) {
        setStrokeColor(color);
        setStrokeWidth(15.0);
    }
    
    public void deselect() {
        setStrokeColor(Color.GRAY);
        setStrokeWidth(oldStrokeWidth);
        highlightRect.setStrokeWidth(0.0);
    }

    /**
     * @return the probability
     */
    public double getProbability() {
        return probability;
    }

    /**
     * @param probability the probability to set
     */
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     * @return the originalValue
     */
    public double getOriginalValue() {
        return originalValue;
    }

    /**
     * @param originalValue the originalValue to set
     */
    public void setOriginalValue(double originalValue) {
        this.originalValue = originalValue;
    }
    
}
