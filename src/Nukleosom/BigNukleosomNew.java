/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import static application.ChromosomExport.pane;
import static application.ChromosomExport.posX;
import static application.ChromosomExport.posY;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


import application.ChromosomProject;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class BigNukleosomNew extends Pane {
	
	HashMap<String,Integer> attributeMap;
	int width, height;
	double angleWidth;
        private String svgExportString = "<g transform=\"translate(%X,%Y)\">\n";
	
	public BigNukleosomNew(ChromosomProject project, HashMap<String,Integer> attributeMap, int width, int height) {
	
		this.attributeMap = attributeMap;
		this.width = width;
		this.height = height;
		
                int maxX = 1;
                int maxY = 1;
                
                setPrefSize(width * maxX, height * maxY);
                
                int x = 0;
                int y = 0;
           
                for(int value : attributeMap.values()) {
                   
                    
                    Paint color = Color.BLACK;

                    System.err.println(value);
                    
                    if(value == 0) {
//					color = Color.web("#FF9933");
                            color = ChromosomProject.color0;
                    }
                    else if(value == 1) {
//					color = Color.web("#3333FF");
                            color = ChromosomProject.color1;
                    }
                    else if(value == 2) {
//					color = Color.web("#3333FF");
                            color = ChromosomProject.color2;
                    }
                    else if(value == 3) {
//					color = Color.web("#FF9933");
                            color = ChromosomProject.color3;
                    }
                    else if(value == 4) {
//					color = Color.web("#E84C3C");
                            color = ChromosomProject.color4;
                    }
			
//                    Rectangle rect = new Rectangle(width, height , color); rect.setOpacity(1.0);
                    AttributeRectangle rect = new AttributeRectangle(width, height , color);
                    rect.setX(x * width);
                    rect.setY(y * height);
                    rect.setStroke(Color.BLACK);
                    rect.setStrokeWidth(0.3);
                    rect.setAttributeValue(value);
                    
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

                    String pointString = "";

                    svgExportString += "<rect x=\"" + rect.getX() + "\" y=\"" + rect.getY() + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:rgb(" + colString + ");stroke:rgb(" + strokeColString + ");stroke-width:" + rect.getStrokeWidth() + ";\" />";
                    svgExportString += "\n";
                    
                    getChildren().add(rect);
                    
                    Bounds bounds = rect.localToScene(rect.getBoundsInLocal());
                    
                    x++;
                    if((x) % maxX == 0) {
                            y++;
                            x = 0;
                    }
                    
                }
                svgExportString += "</g>";
        }
        
        public String getSVGExportString() {
            return svgExportString;
        }
        
}

