/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import javafx.scene.control.Label;
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
    
    public HeatNukleosom(double value, int x, int y, int width, int height, String strokeType) {

        //rosa Color.rgb(255,0,230)
        
        highlightRect = new Rectangle(width, height , Color.rgb(255,100,0)); 
        this.value = value;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

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
        
        if(strokeType.equals("HORIZONTAL")){// || strokeType.equals("BOTH")) {
            line = new Line(0,0,width,0);
            line.setStroke(Color.GRAY);
            line.setStrokeWidth(strokeWidth);
            getChildren().add(line);
            line = new Line(0,height-add,width,height-add);
            line.setStroke(Color.GRAY);
            line.setStrokeWidth(strokeWidth);
            getChildren().add(line);
        }
        
        if(strokeType.equals("VERTICAL")){// || strokeType.equals("BOTH")) {
            line = new Line(0,0,0,height);
            line.setStrokeWidth(strokeWidth);
            line.setStroke(Color.GRAY);
            getChildren().add(line);
            line = new Line(width-add,0,width-add,height);
            line.setStrokeWidth(strokeWidth);
            line.setStroke(Color.GRAY);
            getChildren().add(line);
        }

//        if(showLabels == true) {
//            Label lab = new Label(String.valueOf(value));
//            getChildren().add(lab);
//        }
    }
    
    public void highlight() {
        
        highlightRect.setOpacity(value + 0.15);
        highlightRect.setStrokeWidth(rect.getStrokeWidth());
        highlightRect.setStroke(rect.getStroke());
        
        getChildren().add(highlightRect);
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
    
    public static double categorizeValue(double value) {
        
//        double returnDouble;
        
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
        setStrokeWidth(2.0);
    }
    
    public void deselect() {
        setStrokeColor(Color.GRAY);
        setStrokeWidth(oldStrokeWidth);
        highlightRect.setStrokeWidth(0.0);
    }
    
}
