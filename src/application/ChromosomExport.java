/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Nukleosom.AttributeRectangle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;



/**
 *
 * @author jakob
 */
public class ChromosomExport {
    
        static double addX = 0.0;
        static double addY = 0.0;

        static double minX = 0.0;
        static double minY = 0.0;
        static boolean first = false;
        
        static double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        static double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    
    public static String svgBeginString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"\n" +
                        "    viewBox=\"0 0 " + 110 + " " + 110 + "\" width=\"" + 110 + "\" height=\"" + 110 + "\" id=\"starter_svg\">";
    public static String svgString = "";
    public static double posX = .0;
    public static double posY = .0;
    public static Pane pane;
    
    public static void exportNodeToSVG(Pane inPane) {
        pane = inPane;
        if(first==false) {
            
            Bounds woop = pane.localToScene(pane.getBoundsInParent());
            
            addX = Math.ceil(woop.getMinX());
            addY = Math.ceil(woop.getMinY());
            first = true;
           
            Bounds bounds = pane.getBoundsInLocal();
            
            int width = (int)(bounds.getMaxX() - bounds.getMinX());
            int height = (int)(bounds.getMaxY() - bounds.getMinY());
            
            setExportSize(width,height);
    }
            
            for(Node nod : inPane.getChildren()) {
                
                if(nod instanceof javafx.scene.layout.Pane) {
                   exportNodeToSVG((Pane)nod); 
                }
                else {
                    try{
                        Shape shape = (Shape)nod;
                        svgString += addToSVGString(nod);
                        svgString += "\n";
                        first = true;
                    }
                    catch (Exception e) {
                        
                        if(nod instanceof javafx.scene.Group) {
                            for(Node node2 : ((Group)nod).getChildren()) {
                                svgString += addToSVGString(node2);
                                svgString += "\n";
                                first = true;
                            }
                        }
                    }
                }
            }
        }
    
       public static String addToSVGString(Node shape) {
           
        if(shape.getClass().getName().equals("javafx.scene.shape.Polygon")) {
            Polygon polygon = (Polygon)shape;
            
            Color col  = Color.BLACK;
            Color strokeCol = Color.BLACK;
            
            if(polygon.getFill()!=null) {
               col = Color.web(polygon.getFill().toString());
            }
            
            String colString = (int)(col.getRed() * 255) + "," + (int)(col.getGreen() * 255) + "," + (int)(col.getBlue() * 255);
            
            if(polygon.getStroke()!=null) {
               strokeCol = Color.web(polygon.getStroke().toString());
            }
            
            String strokeColString = (int)(strokeCol.getRed() * 255) + "," + (int)(strokeCol.getGreen() * 255) + "," + (int)(strokeCol.getBlue() * 255);
            
            String pointString = "<polygon points=\"";
            
            for(int i = 0; i < polygon.getPoints().size(); i++) {
                double doub = polygon.getPoints().get(i);
                
                if(i%2 == 0) {
//                    doub += addX - minX;
                }
                else {
//                    doub += addY - minY;
                
                    Point2D parent = pane.localToScene(polygon.getPoints().get(i-1), doub);
                    
                    posX = parent.getX();
                    posY = parent.getY();
                    
//                Rand zur linken oberen Ecke draufaddieren
//                doub += 5;
                
//            System.err.println("ADD: " + addX);
//            System.err.println("MIN: " + minX + " " + (addX - minX));
                    pointString += (posX - addX);
                    pointString += ",";
                    pointString += (posY - addY);
                    if(i != polygon.getPoints().size()-1) {
                        pointString += ",";
                    }
                }
            }
            
            pointString += "\" style=\"fill:rgb(" + colString + ");stroke:rgb(" + strokeColString + ");stroke-width:" + polygon.getStrokeWidth() + "\" />";
            
//            System.err.println(pointString);
            return pointString;
        }
        else if(shape.getClass().getName().equals("javafx.scene.shape.Line")){
            Line line = (Line)shape;
            Color strokeCol = Color.web(line.getStroke().toString());
            
            if(line.getStroke()!=null) {
               strokeCol = Color.web(line.getStroke().toString());
            }
            
            String strokeColString = (int)(strokeCol.getRed() * 255) + "," + (int)(strokeCol.getGreen() * 255) + "," + (int)(strokeCol.getBlue() * 255);
            
            Bounds axisBounds = line.getBoundsInParent();

            String pointString = "";
            
            Point2D parent = pane.localToScene(line.getStartX(),line.getStartY());
                    
            posX = parent.getX();
            posY = parent.getY();
            
            pointString = "<line x1=\"" + (posX - addX) + "\" y1=\"" + (posY - addY) + "\" x2=\"";
            
            parent = pane.localToScene(line.getEndX(),line.getEndY());
                    
            posX = parent.getX();
            posY = parent.getY();
            
            pointString += (posX - addX) + "\" y2=\""+ (posY - addY) + "\" style=\"stroke:rgb(" + strokeColString + ");stroke-width:" + line.getStrokeWidth() + "\" />";
            
            return pointString;
        }
         else if(shape.getClass().getName().equals("Nukleosom.AttributeRectangle")){
            AttributeRectangle rect = (AttributeRectangle)shape;
            
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
            
            posX = pane.localToScene(rect.getBoundsInLocal()).getMinX();
            posY = pane.localToScene(rect.getBoundsInLocal()).getMinY();
                
            pointString = "<rect x=\"" + (Math.ceil(posX) - addX) + "\" y=\"" + (Math.ceil(posY) - addY) + "\" width=\"" + rect.getWidth() + "\" height=\"" + rect.getHeight() + "\" style=\"fill:rgb(" + colString + ");stroke:rgb(" + strokeColString + ");stroke-width:" + rect.getStrokeWidth() + ";\" />";
            
            return pointString;
        }
      
        return "";
    }
       
    public static void setExportSize(int width, int height) {
        svgString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"\n" +
                        "    viewBox=\"0 0 " + width + " " + height + "\" width=\"" + width + "\" height=\"" + height + "\" id=\"starter_svg\">";
    }
    
    public static void writeToFile(String fileName) {

        svgString = svgString + "</svg>";
        
        try{
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(svgString);
            //Close the output stream
            out.close();
//            System.err.println(svgString);
            System.err.println("FERTIG");
            
            first = false;
      
        }catch (IOException e){//Catch exception if any
         System.err.println("Error: " + e.getMessage());
        }
    }
    
    
    
}
