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
    HeatNukleosomGrid parent;
    Rectangle rect;
    double oldStrokeWidth;
    int x, y;
    
    public HeatNukleosom(double value, int x, int y, int width, int height, boolean showLabels, boolean showStrokes, String strokeType) {

        this.value = value;
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        setPrefSize(width, height);

        String histoneNumberString = "";

        Paint color;

        color = Color.hsb(50,1.0,value,1.0).invert();

        rect = new Rectangle(width, height , color); 
//        rect.setOpacity(value);   

        rect.setX(0);
        rect.setY(0);
        
//        if(strokeType)
        
        if(showStrokes) {
            rect.setStroke(Color.RED);
            rect.setStrokeWidth(0.8);
        }
        else {
            rect.setStroke(Color.GRAY);
            rect.setStrokeWidth(0.2);
        }
        
        oldStrokeWidth = rect.getStrokeWidth();
        
        rect.setStrokeType(StrokeType.INSIDE);
        
        getChildren().add(rect);
        
        Line line;
        double strokeWidth = 1.0;
        double add = strokeWidth + rect.strokeWidthProperty().doubleValue();
        
        if(strokeType.equals("HORIZONTAL") || strokeType.equals("BOTH")) {
            line = new Line(0,0,width,0);
            line.setStroke(Color.GRAY);
            line.setStrokeWidth(strokeWidth);
            getChildren().add(line);
            line = new Line(0,height-add,width,height-add);
            line.setStroke(Color.GRAY);
            line.setStrokeWidth(strokeWidth);
            getChildren().add(line);
        }
        
        if(strokeType.equals("VERTICAL") || strokeType.equals("BOTH")) {
            line = new Line(0,0,0,height);
            line.setStrokeWidth(strokeWidth);
            line.setStroke(Color.GRAY);
            getChildren().add(line);
            line = new Line(width-add,0,width-add,height);
            line.setStrokeWidth(strokeWidth);
            line.setStroke(Color.GRAY);
            getChildren().add(line);
        }

        if(showLabels == true) {
            Label lab = new Label(String.valueOf(value));
            getChildren().add(lab);
        }
    }
    
    public void setStrokeColor(Color col) {
        rect.setStroke(col);
    } 
    
    public void setStrokeWidth(double doub) {
        rect.setStrokeWidth(doub);
    } 
    
}
