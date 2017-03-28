package SunburstNukleosom;

import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import application.ChromosomProject;
import javafx.scene.control.Tooltip;

public class SunburstNukleosom extends Pane {
	
	List<int[]> valueList;
	int width, height;
	double angleWidth;
         String exportString = "<g transform=\"translate(%X,%Y)\">\n";
	
	public SunburstNukleosom(ChromosomProject project, List<int[]> valueList, int width, int height) {
		
                String axisString = "";
                String polygonString = "";
		this.valueList = valueList;
		this.width = width;
		this.height = height;
		
		int 	maxX = 2, y = 0,  x = 0;
		
		int xArray[] = {2,2,1,1};
		int yArray[] = {1,2,2,1};
		
		angleWidth = 360.0 / project.getHistoneNumber();
		
			for(int u = 0; u < project.getHistoneNumber(); u++) {
                            int array[] = valueList.get(u);
                           double angleOffset = u * angleWidth;
			
			setPrefSize(width, height);
			int dimension = array.length, nextDim = 0, diff = 0;
			
			double angle = angleWidth/dimension, nextDimAngle = 0.0;
			
			double midX = width/2;
			double midY = height/2;
			
			double transX = 0.0;
			double transY = 0.0;
			
			if(angleOffset == 0.0) {
				transX = -midX;
				transY = 0.0;
			}
			else if(angleOffset == 90.0) {
				transX = -midX;
				transY = -midY;
			}
			else if(angleOffset == 180.0) {
				transX = 0.0;
				transY = -midY;
			}
			
			Line axisLine = null, outLine = null, netLine = null;
			
			Bounds bounds = null, nextBounds = null;
			
			Group axisGroup = new Group(), outLineGroup = new Group(), polygonGroup = new Group(), netGroup = new Group(), rectGroup = new Group();
                        
                        
			
			Polygon polygon = null;
			
			Paint color = null;
			
			double netArray[] = {0.0,0.5,1.0};
			
			for(int dimCounter = 0; dimCounter < dimension; dimCounter++) {
			
                                Rectangle axisHelperRect = new Rectangle(midX, 0, 0, 0);
                            
				axisLine = new Line();
                                
				axisLine.setSmooth(true);
				axisLine.setStrokeWidth(0.5);
				
				axisHelperRect.getTransforms().add(new Rotate(angleOffset + angle * nextDim, midX, midY));
                                
                                Bounds rectBounds = axisHelperRect.getBoundsInParent();
                                
                                axisLine.setStartX(midX);
                                axisLine.setStartY(midY);
                                axisLine.setEndX(rectBounds.getMinX());
                                axisLine.setEndY(rectBounds.getMinY());

				nextDim = dimCounter + 1;
				
				nextDimAngle = angle * nextDim;
				
				if(nextDim==dimension) {
					nextDim = 0;
					nextDimAngle = 0.0;
				}
										
				double y1 = midY - midY/2.5;
				double y2 = y1 - diff;
				
				double value = 0.0;
				
				if(array[dimCounter] > 0)
					value = (array[dimCounter]/4.0);
				else
					value = (0.12);
				
				Rectangle rect = new Rectangle(midX,(y1 - y2 * value),0,0); 
				rect.getTransforms().add(new Rotate(angleOffset + angle * dimCounter, midX, midY));
				
				Rectangle helperRect = new Rectangle(midX,(y1 - y2 * value),0,0);
				helperRect.getTransforms().add(new Rotate(angleOffset + nextDimAngle, midX, midY));
				
				bounds = rect.localToScene(rect.getBoundsInLocal());
				nextBounds = helperRect.localToScene(helperRect.getBoundsInLocal());
		
				
				if(array[dimCounter] == 0) {
					color = Color.web("#FF9933");
					//color = ChromosomProject.color0;
				}
				else if(array[dimCounter] == 1) {
					color = Color.web("#3333FF");
					//color = ChromosomProject.color1;
				}
				else if(array[dimCounter] == 2) {
					color = Color.web("#3333FF");
//					color = ChromosomProject.color2;
				}
				else if(array[dimCounter] == 3) {
					color = Color.web("#FF9933");
//					color = ChromosomProject.color3;
				}
				else if(array[dimCounter] == 4) {
					color = Color.web("#E84C3C");
//					color = ChromosomProject.color4;
				}
				
				polygon = new Polygon();
				polygon.getPoints().addAll(new Double[]{
					midX, midY,
				    bounds.getMinX() ,bounds.getMinY(),
				    nextBounds.getMinX(), nextBounds.getMinY() });
				polygon.setFill(color);

				
                                polygon.setStroke(Color.BLACK);
				polygon.setStrokeWidth(0.2);
                                polygon.setSmooth(true);
                                String tipString = Integer.toString(array[dimCounter]);
                                Tooltip t = new Tooltip(tipString);
                                Tooltip.install(polygon, t);       
				
				outLine = new Line(bounds.getMinX(),bounds.getMinY(), nextBounds.getMinX(), nextBounds.getMinY());
				outLine.setStroke(color);
				outLine.setStrokeWidth(1.4);
				
				
				rectGroup.getChildren().add(rect);
				polygonGroup.getChildren().add(polygon);
                                
                                
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

                                polygonString += "<polygon points=\"";

                                polygonString += midX + "," + midY + "," + bounds.getMinX() + "," + bounds.getMinY() + "," + nextBounds.getMinX() + "," + nextBounds.getMinY();

                                polygonString += "\" style=\"fill:rgb(" + colString + ");stroke:rgb(" + strokeColString + ");stroke-width:" + polygon.getStrokeWidth() + "\" />";
                                polygonString += "\n";
                                
                                
				outLineGroup.getChildren().add(outLine);
				axisGroup.getChildren().add(axisLine);
                                
                               strokeCol = Color.web(axisLine.getStroke().toString());
            
                                if(axisLine.getStroke()!=null) {
                                   strokeCol = Color.web(axisLine.getStroke().toString());
                                }

                                strokeColString = (int)(strokeCol.getRed() * 255) + "," + (int)(strokeCol.getGreen() * 255) + "," + (int)(strokeCol.getBlue() * 255);

                                axisString += "<line x1=\"" + axisLine.getStartX() + "\" y1=\"" + axisLine.getStartY() + "\" x2=\"";

                                axisString += axisLine.getEndX() + "\" y2=\""+ axisLine.getEndY() + "\" style=\"stroke:rgb(" + strokeColString + ");stroke-width:" + axisLine.getStrokeWidth() + "\" />";
                                axisString += "\n";
			}	
			
			
			getChildren().add(polygonGroup);
                        exportString += polygonString;
			
            }
                        exportString += "</g>";
	}
        
        public String getSVGExportString() {
            return exportString;
        }
        
}

