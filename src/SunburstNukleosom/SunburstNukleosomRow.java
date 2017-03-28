package SunburstNukleosom;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import application.ChromosomProject;
import javafx.geometry.Insets;

public class SunburstNukleosomRow extends GridPane {	
	
	ChromosomProject project;
	int width, height, X, Y;
        ArrayList<SunburstNukleosom> nuklList = new ArrayList<SunburstNukleosom>();
	
	public SunburstNukleosomRow(ChromosomProject project, int X, int Y, int width, int height) {
		
		this.project = project;
		this.width = width;
		this.height = height;
		this.X = X;
		this.Y = Y;
		
                this.setPadding(new Insets(0,3,3,0));
		
		setAlignment(Pos.CENTER);
		setHgap(3);
		setVgap(3);
		
		int 	number = X * Y,
				y = 0,  x = 0;
		
		Vector<ArrayList<ArrayList<int[]>>> timeVector = null;
		
		for(int i = 0; i < number; i++) {
			
			List<int[]> valueList = new ArrayList<int[]>();
			
			for(int u = 0; u < project.getHistoneNumber(); u++) {
				valueList.add(timeVector.get(y).get(x).get(u));
			}
			
			if((x) % X == 0) {
				y++;
				x = 0;
			}
			x++;	
			
                        
                        SunburstNukleosom nukl = new SunburstNukleosom(project, valueList, width, height);
			add(nukl, x,y);
                        nuklList.add(nukl);
		}
	}
        
        public double getExportWidth() {
            List<int[]> valueList = new ArrayList<int[]>();

            for(int i = 0; i < project.getHistoneNumber();i++) {
                    int valueArray[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                    valueList.add(valueArray);
            }

            SunburstNukleosom nukl = new SunburstNukleosom(project,valueList, width, height);
           
            return (nukl.getPrefWidth() + this.getHgap()) * X + this.getPadding().getLeft() + this.getPadding().getRight();
        }
        
        public double getExportHeight() {
            List<int[]> valueList = new ArrayList<int[]>();

            for(int i = 0; i < project.getHistoneNumber();i++) {
                    int valueArray[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                    valueList.add(valueArray);
            }

            SunburstNukleosom nukl = new SunburstNukleosom(project,valueList, width, height);
            
            return (nukl.getPrefHeight() + this.getVgap()) * Y + this.getPadding().getBottom() + this.getPadding().getTop();
        }
	
	public SunburstNukleosomRow getScaledPic(int scale) {
		return new SunburstNukleosomRow(project, X, Y, width * scale, height * scale);
	}
        
        public ArrayList<SunburstNukleosom> getNuklList() {
            return nuklList;
        }
}
