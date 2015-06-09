package NukleosomVase;

import Nukleosom.AttributeRectangle;

import application.ChromosomProject;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

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
				
		double angle = 180;
		
		double maxX = getPrefWidth();
		double maxY = getPrefHeight();
		
		Paint color = null;
			
		if(horizontal) {
			axisLine = new Line(0, maxY, maxX+5, maxY);
		}
		else {
			axisLine = new Line(0, 0, 0, maxY+5);
		}
			
			axisLine.setStrokeWidth(0.5);
			
			AttributeRectangle rect = null; 
			
			if(value == 0) {
//				color = Color.web("#FF9933");
				color = ChromosomProject.color0;
			}
			else if(value == 1) {
//				color = Color.web("#3333FF");
				color = ChromosomProject.color1;
			}
			else if(value == 2) {
//				color = Color.web("#3333FF");
				color = ChromosomProject.color2;
			}
			else if(value == 3) {
//				color = Color.web("#FF9933");
				color = ChromosomProject.color3;
			}
			else if(value == 4) {
//				color = Color.web("#E84C3C");
				color = ChromosomProject.color4;
			}
			
//			if(value > 0.5) {
//				color = Color.web("#3333FF");
//				
//			}
//			else if(value <= 0.5) {
//				color = Color.web("#FF9933");//#FF9933
//			}
			
			double doub = 0.0;
			
			if(value > 0)
				doub = (value/5.0);
			else
				doub = (0.15);
			
			if(horizontal) {
				rect = new AttributeRectangle(0,0,maxX-1, maxY * doub);
			}
			else {
				rect = new AttributeRectangle(0,0,maxX * doub, maxY-1);
			}
				rect.setFill(color);
		
			if(horizontal) {
//				axisLine.getTransforms().add(new Rotate(angle, maxX/2, maxY/2));
				rect.getTransforms().add(new Rotate(angle, maxX/2, maxY/2));
			}
			
			rectGroup.getChildren().add(rect);
			axisGroup.getChildren().add(axisLine);
		
		getChildren().add(rectGroup);
		
		getChildren().add(axisGroup);	
	}
	
}