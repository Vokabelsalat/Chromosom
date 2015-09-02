/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import application.ChromosomProject;
import java.util.HashMap;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

public class BigNukleosomNew extends GridPane {
	
	HashMap<String,Integer> attributeMap;
//	int width, height;
	double angleWidth;
//        private String svgExportString = "<g transform=\"translate(%X,%Y)\">\n";
	
        HashMap<String,HashMap<String,Integer>> histoneMap;
        ChromosomProject project;
        int width; 
        int height;
        
	public BigNukleosomNew(ChromosomProject project, HashMap<String,HashMap<String,Integer>> histoneMap, int width, int height, boolean showLabels) {
            
            this.histoneMap = histoneMap;
            this.project = project;
            this.width = width;
            this.height = height;
            
                Map<String,HashMap<String,int[]>> histoneProperties = project.getHistoneProperties();
               
                int maxX = project.getMaxX();
                int maxY = project.getMaxY();
                
//                //TODO ind die Höhe und Breite noch die Lücken zwischen den Histonen einberechnen (gaps)
//		this.width = maxX * width;
//		this.height = maxY * height;
                
                int gridX = 0;
                int gridY = 0;
                
                setPrefSize(width * maxX, height * maxY);
                
                int x = 0;
                int y = 0;
                String histoneNumberString = "";
                
                Pane pan = new Pane();                
                this.setHgap(5);
                this.setVgap(2);
                
                int value;
                Paint color;
                
                for(String histone : histoneProperties.keySet()) {
                    pan = new Pane(); 
                    x = 0;
                    y = 0;
                    histoneNumberString = histone;
                    
                    for(Map.Entry<String, int[]> attribute : histoneProperties.get(histone).entrySet()) {
                            
                        if(histoneMap.containsKey(histone)) {

                            if(histoneMap.get(histone).containsKey(attribute.getKey())) {
                            
                                value = histoneMap.get(histone).get(attribute.getKey());
                                
                                color = Color.BLACK;

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

                                Rectangle rect = new Rectangle(width, height , color); 
                                rect.setOpacity(1.0);   
                                
                                x = histoneProperties.get(histoneNumberString).get(attribute.getKey())[0];
                                y = histoneProperties.get(histoneNumberString).get(attribute.getKey())[1];

                                rect.setX(x * width);
                                rect.setY(y * height);
                                
                                rect.setStroke(Color.BLACK);
                                rect.setStrokeWidth(0.3);
//                                rect.setAttributeValue(value);
                                pan.getChildren().add(rect);
                                
                                if(showLabels == true) {
                                    Label lab = new Label(String.valueOf(value));
//                                    lab.setTranslateX(x * width);
                                    lab.getTransforms().add(new Translate(x*width,0.0));
                                    pan.getChildren().add(lab);
                                }
                            }
                            else {
                               Rectangle rect = new Rectangle(width, height , Color.WHITE); 
                               rect.setOpacity(1.0);   
                                
                                x = histoneProperties.get(histoneNumberString).get(attribute.getKey())[0];
                                y = histoneProperties.get(histoneNumberString).get(attribute.getKey())[1];

                                rect.setX(x * width);
                                rect.setY(y * height);
                                rect.setStroke(Color.BLACK);
                                rect.setStrokeWidth(0.3);
//                                rect.setAttributeValue(value);
                                pan.getChildren().add(rect);                                
                            }
                            
                        }
//                        else {
//                               Rectangle rect = new Rectangle(width, height , Color.WHITE); 
//                               rect.setOpacity(1.0);   
//                                
//                                x = histoneProperties.get(histoneNumberString).get(attribute.getKey())[0];
//                                y = histoneProperties.get(histoneNumberString).get(attribute.getKey())[1];
//
//                                rect.setX(x * width);
//                                rect.setY(y * height);
//                                rect.setStroke(Color.BLACK);
//                                rect.setStrokeWidth(0.3);
////                                rect.setAttributeValue(value);
//                            
//                                pan.getChildren().add(rect);                                
//                        }
                    }
                        this.add(pan, gridX, gridY); 
                        gridY++;
                }
                
//                for(Map.Entry<String,HashMap<String,Integer>> histone : histoneMap.entrySet()) {
//                    pan = new Pane(); 
//                    x = 0;
//                    y = 0;
//                    histoneNumberString = histone.getKey();
////                    /* 
//                    
//                    for(Map.Entry<String,Integer> attribute : histone.getValue().entrySet()) {
//                            value = attribute.getValue();
//
//                            color = Color.BLACK;
//
//                            if(value == 0) {
//        //					color = Color.web("#FF9933");
//                                    color = ChromosomProject.color0;
//                            }
//                            else if(value == 1) {
//        //					color = Color.web("#3333FF");
//                                    color = ChromosomProject.color1;
//                            }
//                            else if(value == 2) {
//        //					color = Color.web("#3333FF");
//                                    color = ChromosomProject.color2;
//                            }
//                            else if(value == 3) {
//        //					color = Color.web("#FF9933");
//                                    color = ChromosomProject.color3;
//                            }
//                            else if(value == 4) {
//        //					color = Color.web("#E84C3C");
//                                    color = ChromosomProject.color4;
//                            }
//
//                            Rectangle rect = new Rectangle(width, height , color); rect.setOpacity(1.0);
//                            rect = new AttributeRectangle(width, height, color);
//                            
//                            x = histoneProperties.get(histoneNumberString).get(attribute.getKey())[0];
//                            y = histoneProperties.get(histoneNumberString).get(attribute.getKey())[1];
//                            
//                            rect.setX(x * width);
//                            rect.setY(y * height);
//                            rect.setStroke(Color.BLACK);
//                            rect.setStrokeWidth(0.3);
////                            rect.setAttributeValue(value);
//                            
//
////                            Tooltip t = new Tooltip(String.valueOf(value));
////                            Tooltip.install(rect, t);
//                            
////                            Color col  = Color.BLACK;
////                            Color strokeCol = Color.BLACK;
////
////                            if(rect.getFill()!=null) {
////                                col = Color.web(rect.getFill().toString());
////                            }
////
////                            String colString = (int)(col.getRed() * 255) + "," + (int)(col.getGreen() * 255) + "," + (int)(col.getBlue() * 255);
////
////                            if(rect.getStroke()!=null) {
////                                col = Color.web(rect.getStroke().toString());
////                            }
////
////                            String strokeColString = (int)(col.getRed() * 255) + "," + (int)(col.getGreen() * 255) + "," + (int)(col.getBlue() * 255);
////
////                            String pointString = "";
////
////                            svgExportString += "<rect x=\"" + rect.getX() + "\" y=\"" + rect.getY() + "\" width=\"" + width + "\" height=\"" + height + "\" style=\"fill:rgb(" + colString + ");stroke:rgb(" + strokeColString + ");stroke-width:" + rect.getStrokeWidth() + ";\" />";
////                            svgExportString += "\n";
//
//                            pan.getChildren().add(rect);
//
//
////                            if(x % maxX == 0 && x > 0) {
////                                    y++;
////                                    x = 0;
////                            }
////                            else
////                                x++;
//                            
//
//                            
////                            System.err.println(attribute.getKey() + " : " + x + " ; " + y);
//                            
//                        }
//                       
//                        this.add(pan, gridX, gridY); 
//                        gridY++;
//                    }
                        
//                    this.setGridLinesVisible(true);
                    
//                    svgExportString += "</g>";
                    
                    
                    x = 0;
                    y = 0;
//                    */
                    
                    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                           project.addNukleosomToOptions(new BigNukleosomNew(project, histoneMap, project.nukleosomMinWidth * 4, project.nukleosomMinHeight * 4, true));
                        }
                    });
                    
        }
        
        public String getSVGExportString() {
            return ""; //svgExportString;
        }
}

