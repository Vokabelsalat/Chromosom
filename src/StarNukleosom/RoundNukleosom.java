package StarNukleosom;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class RoundNukleosom extends Pane{
	
	double array[];
	
	public RoundNukleosom(double[] array) {//, int height, int width) {
		
		this.array = array;
	
		setPrefSize(50, 50);
		
		int dimension = array.length, nextDim = 0, diff = 3;
		
		double angle = (360.0/dimension);
		
		double midX = this.getPrefWidth()/2;
		double midY = this.getPrefHeight()/2;
		
		Line axisLine = null, outLine = null, netLine = null;
		
		Bounds bounds = null, nextBounds = null;
		
		Group axisGroup = new Group(), outLineGroup = new Group(), polygonGroup = new Group(), netGroup = new Group();
		
		Polygon polygon = null;
		
		Paint color = null;
		
		double netArray[] = {0.0,0.5,1.0};
			
		for(int dimCounter = 0; dimCounter < dimension; dimCounter++) {
			
			axisLine = new Line(midX, midY, midX, 0);
			
			axisLine.setStrokeWidth(0.7);
			
			axisLine.getTransforms().add(new Rotate(angle * nextDim, midX, midY));
			
			nextDim = dimCounter + 1;
			
			if(nextDim==dimension) {
				nextDim = 0;
			}
									
			double y1 = midY - midY/4;
			double y2 = y1 - diff;
			
			Rectangle rect = new Rectangle(midX,(y1 - y2 * array[dimCounter]),0,0); 
			rect.getTransforms().add(new Rotate(angle * dimCounter, midX, midY));
			
			Rectangle helperRect = new Rectangle(midX,(y1 - y2 * array[nextDim]),0,0);
			helperRect.getTransforms().add(new Rotate(angle * nextDim, midX, midY));
			
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
			
//			polygon.setOpacity(0.65);
			
			outLine = new Line(nextBounds.getMinX(), nextBounds.getMinY(), bounds.getMinX(),bounds.getMinY());
			outLine.setStroke(color);
			outLine.setStrokeWidth(1.4);
			
			for(double netValue : netArray) {
				Rectangle netRect = new Rectangle(midX,(y1 - y2 * netValue),0,0); 
				netRect.getTransforms().add(new Rotate(angle * dimCounter, midX, midY));
				
				Rectangle helperNetRect = new Rectangle(midX,(y1 - y2 * netValue),0,0);
				helperNetRect.getTransforms().add(new Rotate(angle * nextDim, midX, midY));
				
				bounds = netRect.localToScene(netRect.getBoundsInLocal());
				nextBounds = helperNetRect.localToScene(helperNetRect.getBoundsInLocal());
			
				netLine = new Line(nextBounds.getMinX(), nextBounds.getMinY(), bounds.getMinX(),bounds.getMinY());
				netLine.setStrokeWidth(0.4);
				
				netGroup.getChildren().add(netLine);
				netLine.setStroke(Color.BLACK);
			}
			
			polygonGroup.getChildren().add(polygon);
			outLineGroup.getChildren().add(outLine);
			axisGroup.getChildren().add(axisLine);
			
		}	
		
		getChildren().add(netGroup);
		
		getChildren().add(polygonGroup);
		
		getChildren().add(axisGroup);	
		

	}

	public double[] getArray() {
		// TODO Auto-generated method stub
		return array;
	}
	
}
