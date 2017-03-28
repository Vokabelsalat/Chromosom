package NukleosomVase;

import Nukleosom.AttributeRectangle;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 * NukleosomVase
 * @author Jakob
 */
public class NukleosomVase extends Pane{
    
        String exportString = "";
               
	
	public NukleosomVase(int value, boolean horizontal, int width, int height) {
		
                exportString = "<g transform=\"translate(%X,%Y) rotate(%W " + width/2.0 + " " + height/2.0 + ")\">\n";
                String axisString = "";
                String rectString = "";
            
		if(horizontal) {
			setPrefSize(width,height);
		}
		else {
			setPrefSize(height,width);
		}
		
		Line axisLine = null;
		
		Group axisGroup = new Group(), 
				rectGroup = new Group();
				
                int gap = 2;
                
		double maxX = getPrefWidth();
		double maxY = getPrefHeight();
		
		Paint color = null;
			
		if(horizontal) {
			axisLine = new Line(0, maxY, maxX, maxY);
		}
		else {
			axisLine = new Line(0, 0, 0, maxY);
		}
			
			axisLine.setStrokeWidth(0.5);
			
			AttributeRectangle rect = null; 
			
			if(value == 0) {
//				color = ChromosomProject.color0;
			}
			else if(value == 1) {
//				color = ChromosomProject.color1;
			}
			else if(value == 2) {
//				color = ChromosomProject.color2;
			}
			else if(value == 3) {
//				color = ChromosomProject.color3;
			}
			else if(value == 4) {
//				color = ChromosomProject.color4;
			}
			
			double doub = 0.0;
			
			if(value > 0)
				doub = (value/5.0);
			else
				doub = (0.10);
                        
//                        System.err.println(value + " -> " + doub);
			
			if(horizontal) {
//				rect = new AttributeRectangle(gap,0,maxX-gap, maxY * doub);
                                rect = new AttributeRectangle();
                                rect.setX(Math.ceil(gap));
                                rect.setY((int)Math.ceil(maxY - (maxY * doub)));
                                rect.setWidth(Math.ceil(maxX-gap));
                                rect.setHeight((int)Math.ceil(maxY * doub));
			}
			else {
				rect = new AttributeRectangle();
                                rect.setX(0);
                                rect.setY(Math.ceil(gap));
                                rect.setWidth((int)Math.ceil(maxX * doub));
                                rect.setHeight(Math.ceil(maxY-gap));
			}
				rect.setFill(color);
		
//			if(horizontal) {
////				axisLine.getTransforms().add(new Rotate(angle, maxX/2, maxY/2));
//				rect.getTransforms().add(new Rotate(angle, maxX/2, maxY/2));
//			}
			
		rectGroup.getChildren().add(rect);
                        
                        Color col  = Color.BLACK;
                        Color strokeCol = Color.BLACK;

                        if(rect.getFill()!=null) {
                            col = Color.web(rect.getFill().toString());
                        }

                        String colString = (int)(col.getRed() * 255) + "," + (int)(col.getGreen() * 255) + "," + (int)(col.getBlue() * 255);

                        if(rect.getStroke()!=null) {
                            col = Color.web(rect.getStroke().toString());
                        }

                        String strokeColString = (int)(col.getRed() * 255) + "," + (int)(col.getGreen() * 255) + "," + (int)(col.getBlue() * 255);

                        rectString += "<rect x=\"" + rect.getX() + "\" y=\"" + rect.getY() + "\" width=\"" + rect.getWidth() + "\" height=\"" + rect.getHeight() + "\" style=\"fill:rgb(" + colString + ");stroke:rgb(" + strokeColString + ");stroke-width:" + rect.getStrokeWidth() + ";\" />";
                        rectString += "\n";
                        
            axisGroup.getChildren().add(axisLine);
                        
                        strokeCol = Color.web(axisLine.getStroke().toString());

                         if(axisLine.getStroke()!=null) {
                            strokeCol = Color.web(axisLine.getStroke().toString());
                         }

                        strokeColString = (int)(strokeCol.getRed() * 255) + "," + (int)(strokeCol.getGreen() * 255) + "," + (int)(strokeCol.getBlue() * 255);

                        axisString += "<line x1=\"" + axisLine.getStartX() + "\" y1=\"" + axisLine.getStartY() + "\" x2=\"";

                        axisString += axisLine.getEndX() + "\" y2=\""+ axisLine.getEndY() + "\" style=\"stroke:rgb(" + strokeColString + ");stroke-width:" + axisLine.getStrokeWidth() + "\" />";
                        axisString += "\n";
		
		getChildren().add(rectGroup);
		exportString += rectString;
                
		getChildren().add(axisGroup);	
                exportString += axisString;
                
                exportString += "</g>";

	}

    public String getSVGExportString() {
        return exportString;
    }
	
}