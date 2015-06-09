package StarNukleosom;

import java.util.List;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class BigRoundNukleosom extends GridPane {
	
	public BigRoundNukleosom(List<double[]> valueList) {
		
		setAlignment(Pos.CENTER);
		setHgap(2);
		setVgap(2);
		
		int 	maxX = 2, y = 0,  x = 0;
		
		int xArray[] = {2,2,1,1};
		int yArray[] = {1,2,2,1};
			
			for(int u = 0; u < valueList.size(); u++) {
				double array[] = valueList.get(u);
				add(new BRNukle(array, u * 90.0), x + xArray[u], y + yArray[u]);
			}
			
			if((x) % maxX == 0) {
				y++;
				x = 0;
			}
			x++;	
	}	
	
	private class BRNukle extends Pane{

		public BRNukle(double[] array, double angleOffset) {
			
			setPrefSize(40, 40);
			int dimension = array.length, nextDim = 0, diff = 2;
			
			double angle = 90.0/dimension, nextDimAngle = 0.0;
			
			double midX = this.getPrefWidth();
			double midY = this.getPrefHeight();
			
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
			
				axisLine = new Line(midX, midY, midX, 0);
				
				axisLine.setStrokeWidth(0.7);
				
				axisLine.getTransforms().add(new Rotate(angleOffset + angle * nextDim, midX, midY));
				
				nextDim = dimCounter + 1;
				
				nextDimAngle = angle * nextDim;
				
				if(nextDim==dimension) {
					nextDim = 0;
					nextDimAngle = 90.0;
				}
										
				double y1 = midY - midY/2.5;
				double y2 = y1 - diff;
				
				Rectangle rect = new Rectangle(midX,(y1 - y2 * array[dimCounter]),0,0); 
				rect.getTransforms().add(new Rotate(angleOffset + angle * dimCounter, midX, midY));
				
				Rectangle helperRect = new Rectangle(midX,(y1 - y2 * array[nextDim]),0,0);
				helperRect.getTransforms().add(new Rotate(angleOffset + nextDimAngle, midX, midY));
				
				bounds = rect.localToScene(rect.getBoundsInLocal());
				nextBounds = helperRect.localToScene(helperRect.getBoundsInLocal());
				
				if(array[dimCounter]<=0.5 && array[nextDim]<=0.5) 
					color = Color.BLUE;
				else if(array[dimCounter]<=0.5 && array[nextDim]>0.5) 
					color = Color.ORANGE;
				else if(array[dimCounter]>0.5 && array[nextDim]>0.5) 
					color = Color.RED;
				else if(array[dimCounter]>0.5 && array[nextDim]<=0.5) 
					color = Color.GREEN;
				
				polygon = new Polygon();
				polygon.getPoints().addAll(new Double[]{
					midX, midY,
				    bounds.getMinX() ,bounds.getMinY(),
				    nextBounds.getMinX(), nextBounds.getMinY() });
				polygon.setFill(color);
				polygon.setSmooth(true);
				
//				polygon.setOpacity(0.65);
				
				outLine = new Line(bounds.getMinX(),bounds.getMinY(), nextBounds.getMinX(), nextBounds.getMinY());
				outLine.setStroke(color);
				outLine.setStrokeWidth(1.4);
				
//					for(double netValue : netArray) {
//						Rectangle netRect = new Rectangle(midX,(y1 - y2 * netValue),0,0); 
//						netRect.getTransforms().add(new Rotate(angle * dimCounter, midX, midY));
//						
//						Rectangle helperNetRect = new Rectangle(midX,(y1 - y2 * netValue),0,0);
//						helperNetRect.getTransforms().add(new Rotate(angle * nextDim, midX, midY));
//						
//						bounds = netRect.localToScene(netRect.getBoundsInLocal());
//						nextBounds = helperNetRect.localToScene(helperNetRect.getBoundsInLocal());
//					
//						netLine = new Line(nextBounds.getMinX(), nextBounds.getMinY(), bounds.getMinX(),bounds.getMinY());
//						netLine.setStrokeWidth(0.4);
//						
//						netGroup.getChildren().add(netLine);
//						netLine.setStroke(Color.BLACK);
//					}
				
				rectGroup.getChildren().add(rect);
				polygonGroup.getChildren().add(polygon);
				outLineGroup.getChildren().add(outLine);
				axisGroup.getChildren().add(axisLine);
				
			}	
			
			Translate trans = new Translate(transX, transY);
			
			polygonGroup.getTransforms().add(trans);
			axisGroup.getTransforms().add(trans);
			outLineGroup.getTransforms().add(trans);
			netGroup.getTransforms().add(trans);
			rectGroup.getTransforms().add(trans);
			
//			getChildren().add(netGroup);
			
			getChildren().add(polygonGroup);
			
//			getChildren().add(outLineGroup);
			
			getChildren().add(rectGroup);
			
//			getChildren().add(axisGroup);	
		}
	}

	
}

