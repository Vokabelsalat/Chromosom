///*
package Nukleosom;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import NukleosomReader.NukleosomGenerator;
import application.ChromosomProject;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;

public class BigNukleosomRow extends GridPane {

	ChromosomProject project;
	int X, Y, height, width;
	BigNukleosomRow scaledRow;
	
	public BigNukleosomRow(ChromosomProject project, int X, int Y, int width, int height) {
		
		this.project = project;
		this.X = X;
		this.Y = Y;
		this.height = height;
		this.width = width;
		
		setAlignment(Pos.CENTER);
		setHgap(width / (7./4.));
		setVgap(height / (7./6.));
		setPadding(new Insets(0,getHgap(),getVgap(),0));
//                setStyle("-fx-border: 2px solid; -fx-border-color: red;");

		
		int 	maxX = X,
				number = maxX * Y,
				y = 0,  x = 0;		
		
//		List<String> returnList = project.getReadedNukleosoms();
		Vector<ArrayList<ArrayList<int[]>>> timeVector = NukleosomGenerator.getGeneratedData();
		
                for(int i = 0; i < number; i++) {
			
//			List<int[]> valueList = new ArrayList<int[]>();
//			
//			for(int u = 0; u < project.getHistoneNumber(); u++) {
//				valueList.add(timeVector.get(y).get(x).get(u));
//			}
			
                    
                      
			if((x) % X == 0) {
				y++;
				x = 0;
			}
                        x++;
                    
                        BigNukleosomNew nukl = new BigNukleosomNew(project,timeVector.get(y).get(x).get(0), width, height);
			add(nukl, x,y);
                    
		}
                
//		for(int i = 0; i < number; i++) {
//			
////			setPrefColumns(X);
////			setPrefRows(Y);
//			
//			List<int[]> valueList = new ArrayList<int[]>();
//			
//			for(int u = 0; u < project.getHistoneNumber(); u++) {
//				
//				valueList.add(timeVector.get(y).get(x).get(u));
//				offset++;
//			}
//			
//			if((x) % X == 0) {
//				y++;
//				x = 0;
//			}
//			x++;	
//			
//			BigNukleosom nukl = new BigNukleosom(valueList, width, height);
//                        System.err.println("NUKLWIDTH:" + nukl.getPrefWidth());
//                        nukl.setLayoutX((nukl.getPrefWidth()) * (x) + this.getHgap() * (x-1));
//                        nukl.setLayoutY((nukl.getPrefHeight() ) * (y) + this.getVgap() * (y-1));
//			add(nukl,x,y);
//		}
		
		
		
//		this.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
//		this.setCenter(grid);
//		this.setGridLinesVisible(true);
	}
        
        public double getExportWidth() {

            Bounds bounds = this.getBoundsInLocal();
              
            return bounds.getMaxX() - bounds.getMinX();
        }
        
        public double getExportHeight() {
            
            Bounds bounds = this.getBoundsInLocal();
            
            return bounds.getMaxY() - bounds.getMinY();
        }
	
}
//	public class BigNukleosomRow extends JPanel {
//	
//	public BigNukleosomRow(ChromosomProject project, int X, int Y, int height, int width) {
//		
//		this.setLayout(new GridLayout(X,Y));
//		
//		int 	maxX = X,
//				number = maxX * Y,
//				y = 0,  x = 0,
//				offset = 2;
//		
//		List<String> returnList = project.getReadedNukleosoms();
//		
//		for(int i = 0; i < number; i++) {
//			
//			List<double[]> valueList = new ArrayList<double[]>();
//			
//			for(int u = 0; u < project.getHistoneNumber(); u++) {
//				
//				valueList.add(NukleosomReader.getValueArray(returnList.get(offset)));
//				offset++;
//			}
//			
//			this.add(new BigNukleosom(valueList, height, width));
//		}
//		
//		export();
//		
//	}
//	
////	public static void main(String args[]) {
////		JFrame frame = new JFrame("AWTTEST");
////		
////		JPanel pan = new JPanel();
////		pan.setLayout(new BorderLayout());
////		
////		ChromosomProject project = new ChromosomProject();
////		
////		double array[] = {0.0,0.5,0.3,0.7,1.0,0.9,0.0,0.5,0.6};
////		
////		List<double[]> valueList = new ArrayList<double[]>();
////		valueList.add(array);
////		
////		pan.add(new BigNukleosomRow(project,2,2,project.getNukleosomWidth(), project.getNukleosomHeight()));
////		
////		frame.add(pan);
////		frame.setVisible(true);
////		frame.setSize(new Dimension(1280,800));
////		
////		ExportDialog export = new ExportDialog();
////	    export.showExportDialog( frame, "Export view as ...", frame, "export" );
////	}
//
//	public void export() {
//		ExportDialog export = new ExportDialog();
//	    export.showExportDialog( this, "Export view as ...", this, "export" );
//	}
//	
//}