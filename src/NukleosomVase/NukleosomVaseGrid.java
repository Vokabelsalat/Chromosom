
package NukleosomVase;

import java.util.ArrayList;
import java.util.Vector;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import NukleosomReader.NukleosomGenerator;
import application.ChromosomProject;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;

public class NukleosomVaseGrid extends GridPane {

	ChromosomProject project;
	int X, Y, width, height;
	String auswahl;
        boolean horizontal;
        int innerX = 0;
        int innerY = 0;
        double nuklWidth = 0;
        double nuklHeight = 0;
	
	public NukleosomVaseGrid(ChromosomProject project, String auswahl, int X, int Y, int width, int height) {
		
		this.project = project;
		this.X = X;
		this.Y = Y;
		this.width = width;
		this.height = height;
		this.auswahl = auswahl;
//		setStyle("-fx-border: 2px solid; -fx-border-color: red;");
                this.setPadding(new Insets(0,10,10,0));
                
		Vector<ArrayList<ArrayList<int[]>>> timeVector = NukleosomGenerator.getGeneratedData();
		int number = 8;
		int zeitSchritt = 0;

		
		switch(auswahl) {
		
		case "BLOCK":
			horizontal = false;
		
		if(horizontal) {
			setHgap(0);
			setVgap(10);	
		}
		else {
			setHgap(10);
			setVgap(0);
		}
		
		number = 8;
		innerX = 0;
		innerY = 0;
		
		setAlignment(Pos.CENTER);
//		grid.setGridLinesVisible(true);
		
		
		for(zeitSchritt = 0; zeitSchritt < X; zeitSchritt++) {
		
//			List<String> returnList = project.readNukleosoms(project.getDefaultFileName(), zeitSchritt * number + 2, number);
			
			for(int spalte = 0; spalte < Y; spalte++) {
				
				GridPane innerGrid = new GridPane();
				
//				int valueArray[] = Double.va(NukleosomReader.getValueArray(returnList.get(spalte)));
				int valueArray[] = timeVector.get(zeitSchritt).get(spalte).get(0);
				
				for(int mod = 0; mod < valueArray.length; mod++) {
					if(horizontal) {
						innerX = 0;
						innerY = mod;
					}
					else {
						innerX = mod;
						innerY = 0;
					}
					innerGrid.add(new NukleosomVase(valueArray[mod], horizontal, width, height), innerX, innerY);
				}
				
				if(horizontal) {
					innerX = zeitSchritt;
					innerY = spalte;
				}
				else {
					innerX = spalte;
					innerY = zeitSchritt;
				}
				add(innerGrid, innerX, innerY);
			
			}    
		}
		break;
		
		case "ZUSTAND":
			horizontal = true;
		
		if(horizontal) {
			setHgap(0);
			setVgap(10);	
		}
		else {
			setHgap(20);
			setVgap(0);
		}
		
		zeitSchritt = 0;
		
		innerX = 0;
		innerY = 0;
		
		setAlignment(Pos.CENTER);
		
		for(zeitSchritt = 0; zeitSchritt < Y; zeitSchritt++) {
		
			for(int mod = 0; mod < timeVector.get(zeitSchritt).get(0).get(0).length; mod++) {
				
				GridPane innerGrid = new GridPane();
				
				for(int nukleosom = 0; nukleosom < X; nukleosom++) {
					
					int valueArray[] = timeVector.get(zeitSchritt).get(nukleosom).get(0);
					
					if(horizontal) {
						innerX = 0;
						innerY = nukleosom;
					}
					else {
						innerX = nukleosom;
						innerY = 0;
					}
					innerGrid.add(new NukleosomVase(valueArray[mod], horizontal, 5, 9), innerX, innerY);
				}
				
				if(horizontal) {
					innerX = zeitSchritt;
					innerY = mod;
				}
				else {
					innerX = mod;
					innerY = zeitSchritt;
				}
				add(innerGrid, innerX, innerY);
			}       
		}
		break;
		
		
		
		case "ZEIT":	
			horizontal = false;
		
		if(horizontal) {
			setHgap(0);
			setVgap(10);	
		}
		else {
			setHgap(20);
			setVgap(0);
		}
		
		number = 10;
		zeitSchritt = 0;
		
		innerX = 0;
		innerY = 0;
		
		int savX = 0, savY = 0;
		
		setAlignment(Pos.CENTER);
//		grid.setGridLinesVisible(true);
		
		for(zeitSchritt = 0; zeitSchritt < Y; zeitSchritt++) {
		
			for(int nukleosom = 0; nukleosom < X; nukleosom++) {
				
				int valueArray[] = timeVector.get(zeitSchritt).get(nukleosom).get(0);
				
				GridPane innerGrid = new GridPane();
				
				for(int mod = 0; mod < valueArray.length; mod++) {
					
					if(horizontal) {
						innerX = 0;
						innerY = mod;
					}
					else {
						innerX = mod;
						innerY = 0;
					}
					innerGrid.add(new NukleosomVase(valueArray[mod], !horizontal, 5, 9), innerX, innerY);
				}
				
				if(horizontal) {
					innerX = zeitSchritt;
					innerY = nukleosom;
				}
				else {
					innerX = nukleosom;
					innerY = zeitSchritt;
				}
				add(innerGrid, innerX, innerY);
				savX = innerX;
				savY = innerY;
			}       
		}
		break;
		
/*Zeit2	
		boolean horizontal = false;
		
		if(horizontal) {
			grid.setHgap(0);
			grid.setVgap(10);	
		}
		else {
			grid.setHgap(0);
			grid.setVgap(0);
		}
		
		int number = 10;
		int zeitSchrittMax = 6;
		
		int innerX = 0, innerY = 0;
		
		int savX = 0, savY = 0;
		
		grid.setAlignment(Pos.CENTER);
//		grid.setGridLinesVisible(true);
		
		for(int zeitSchritt = 0; zeitSchritt < zeitSchrittMax; zeitSchritt++) {
		
			List<String> returnList = NukleosomReader.readNukleosoms(zeitSchritt * number + 2, number);	
			
			for(int nukleosom = 0; nukleosom < number; nukleosom++) {
				
				double valueArray[] = NukleosomReader.getValueArray(returnList.get(nukleosom));
				
				GridPane innerGrid = new GridPane();
				
				for(int mod = 0; mod < valueArray.length; mod++) {
					
					if(horizontal) {
						innerX = 0;
						innerY = mod;
					}
					else {
						innerX = mod;
						innerY = 0;
					}
					System.err.println(mod);
					innerGrid.add(new NukleosomVase(valueArray[mod], !horizontal, 5, 9), innerX, innerY);
				}
				
				if(horizontal) {
					innerX = zeitSchritt;
					innerY = nukleosom;
				}
				else {
					innerX = nukleosom;
					innerY = zeitSchritt;
				}
				grid.add(innerGrid, innerX, innerY);
//				savX = innerX;
//				savY = innerY;
			}       
		}
		
		int anzahl = 2;
		
//		for(int zeitSchritt = 0; zeitSchritt < zeitSchrittMax; zeitSchritt++) {
//			
//			List<String> returnList = NukleosomReader.readNukleosoms(zeitSchritt * number + 2, number);	
//			int off = 0;
//			
//			for(int nukleosom = 0; nukleosom < number/anzahl; nukleosom++) {
//				
//				List<double[]> arrayList = new ArrayList<double[]>();
//				
//				for(int zahl = 0; zahl < anzahl; zahl++) {
//					arrayList.add(NukleosomReader.getValueArray(returnList.get(off)));
//					off++;
//				}
//				
//				double valueArray[] = NukleosomReader.mergeRoundValues(arrayList);
//				
//				GridPane innerGrid = new GridPane();
//				
//				for(int mod = 0; mod < valueArray.length; mod++) {
//					
//					if(horizontal) {
//						innerX = 0;
//						innerY = mod;
//					}
//					else {
//						innerX = mod;
//						innerY = 0;
//					}
//					innerGrid.add(new NukleosomVase(valueArray[mod], !horizontal, 5, 9), innerX, innerY);
//				}
//				
//				if(horizontal) {
//					innerX = zeitSchritt + savX +1;
//					innerY = nukleosom;
//				}
//				else {
//					innerX = nukleosom;
//					innerY = zeitSchritt + savY + 1;
//				}
//				grid.add(innerGrid, innerX, innerY);
//			}       
//		}
Zeit2*/
		
		case ("ROW"):
		horizontal = false;
		
		if(horizontal) {
			setHgap(0);
			setVgap(10);	
		}
		else {
			setHgap(10);
			setVgap(0);  
		}
		
		number = 250;
		zeitSchritt = 0;
		
		innerX = 0;
		innerY = 0;
		
		setAlignment(Pos.CENTER);
//		grid.setGridLinesVisible(true);
		
		for(zeitSchritt = 0; zeitSchritt < X; zeitSchritt++) {
		
			for(int nukleosom = 0; nukleosom < Y; nukleosom++) {
				
				int valueArray[] = timeVector.get(zeitSchritt).get(nukleosom).get(0);
				
				GridPane innerGrid = new GridPane();
				
				for(int mod = 0; mod < valueArray.length; mod++) {
					
					if(horizontal) {
						innerX = 0;
						innerY = mod;
					}
					else {
						innerX = mod;
						innerY = 0;
					}
                                        NukleosomVase nuklVase = new NukleosomVase(valueArray[mod], horizontal, 5, 9);
					innerGrid.add(nuklVase, innerX, innerY);
                                        nuklWidth = (nuklVase.getPrefWidth() * innerX);
                                        nuklHeight = (nuklVase.getPrefHeight());
				}
				
				if(horizontal) {
					innerX = nukleosom;
					innerY = zeitSchritt;
				}
				else {
					innerX = zeitSchritt;
					innerY = nukleosom;
				}
                                
                                
				add(innerGrid, innerX, innerY);
			}       
		}
		break;
	}
		
		

	}
        
        public double getExportWidth() {
            
            NukleosomVase nukl = new NukleosomVase(1, horizontal, width, height);
           
            System.err.println("JOOOO: " + nukl.getPrefWidth() + " " + nukl.getPrefHeight());
            
            Bounds bounds = this.getBoundsInLocal();
            
            
//            return (nuklWidth + this.getHgap()) * X + this.getPadding().getLeft() + this.getPadding().getRight();
            return bounds.getMaxX() - bounds.getMinX();
//            return 50000;
        }
        
        public double getExportHeight() {
            
            NukleosomVase nukl = new NukleosomVase(1, horizontal, width, height);

            Bounds bounds = this.getBoundsInLocal();
            
            return bounds.getMaxY() - bounds.getMinY();
//                return 50000;
//            return (nuklHeight + this.getVgap()) * Y + this.getPadding().getBottom() + this.getPadding().getTop();
        }

	public NukleosomVaseGrid getScaledPic(int scale) {
		return new NukleosomVaseGrid(project, auswahl, X, Y, width * 4, height * 4);
	}
}

