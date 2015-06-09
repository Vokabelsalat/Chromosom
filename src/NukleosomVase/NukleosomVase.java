package NukleosomVase;

import Nukleosom.AttributeRectangle;

import application.ChromosomProject;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class NukleosomVase extends Pane{
	
	public NukleosomVase(int value, boolean horizontal, int width, int height) {
		
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
				color = ChromosomProject.color0;
			}
			else if(value == 1) {
				color = ChromosomProject.color1;
			}
			else if(value == 2) {
				color = ChromosomProject.color2;
			}
			else if(value == 3) {
				color = ChromosomProject.color3;
			}
			else if(value == 4) {
				color = ChromosomProject.color4;
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
			axisGroup.getChildren().add(axisLine);
		
		getChildren().add(rectGroup);
		
		getChildren().add(axisGroup);	
	}
	
}