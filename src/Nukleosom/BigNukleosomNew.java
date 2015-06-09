/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


import application.ChromosomProject;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BigNukleosomNew extends Pane {
	
	int[] valueArray;
	int width, height;
	double angleWidth;
	
	public BigNukleosomNew(ChromosomProject project, int[] valueArray, int width, int height) {
	
		this.valueArray = valueArray;
		this.width = width;
		this.height = height;
		
                int maxX = 4;
                int maxY = 5;
                
                setPrefSize(width * maxX, height * maxY);
                
                int x = 0;
                int y = 0;
           
                for(int i = 0; i < valueArray.length; i++) {
                    int value = valueArray[i];
                    
                    Paint color = Color.BLACK;

                    if(valueArray[i] == 0) {
//					color = Color.web("#FF9933");
                            color = ChromosomProject.color0;
                    }
                    else if(valueArray[i] == 1) {
//					color = Color.web("#3333FF");
                            color = ChromosomProject.color1;
                    }
                    else if(valueArray[i] == 2) {
//					color = Color.web("#3333FF");
                            color = ChromosomProject.color2;
                    }
                    else if(valueArray[i] == 3) {
//					color = Color.web("#FF9933");
                            color = ChromosomProject.color3;
                    }
                    else if(valueArray[i] == 4) {
//					color = Color.web("#E84C3C");
                            color = ChromosomProject.color4;
                    }
			
//                    Rectangle rect = new Rectangle(width, height , color); rect.setOpacity(1.0);
                    AttributeRectangle rect = new AttributeRectangle(width, height , color);
                    rect.setX(x * width);
                    rect.setY(y * height);
                    rect.setStroke(Color.BLACK);
                    rect.setStrokeWidth(0.3);
                    rect.setAttributeValue(valueArray[i]);
                    
                    getChildren().add(rect);
                    
                    Bounds bounds = rect.localToScene(rect.getBoundsInLocal());
                    
                    System.err.println(Math.ceil(bounds.getMinX()) + " " + Math.ceil(bounds.getMinY()));
                    
                     x++;
                    if((x) % maxX == 0) {
                            y++;
                            x = 0;
                    }
                    
                }
        }
        
}

