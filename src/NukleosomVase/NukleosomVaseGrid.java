
package NukleosomVase;

import java.util.ArrayList;
import java.util.Vector;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import application.ChromosomProject;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;

/**
 * Verschiedene Arten der Grids, bestehend aus den NukleosomVases
 * @author Jakob
 */
public class NukleosomVaseGrid extends GridPane {

	ChromosomProject project;
	int X, Y, width, height;
	String auswahl;
        boolean horizontal;
        int innerX = 0;
        int innerY = 0;
        double nuklWidth = 0;
        double nuklHeight = 0;
        String exportString = "";
        ArrayList<BigNukleosomVase> bigNuklList = new ArrayList<BigNukleosomVase>();
        ArrayList<NukleosomVase> nuklList = new ArrayList<NukleosomVase>();
	
	public NukleosomVaseGrid(ChromosomProject project, String auswahl, int X, int Y, int width, int height) {
		
		this.project = project;
		this.X = X;
		this.Y = Y;
		this.width = width;
		this.height = height;
		this.auswahl = auswahl;
                this.setPadding(new Insets(10,10,10,10));
                
		Vector<ArrayList<ArrayList<int[]>>> timeVector = null;
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
		
		innerX = 0;
		innerY = 0;
		
		setAlignment(Pos.CENTER);
//		grid.setGridLinesVisible(true);
		
		
		for(zeitSchritt = 0; zeitSchritt < X; zeitSchritt++) {
		
			for(int spalte = 0; spalte < Y; spalte++) {
				
				GridPane innerGrid = new GridPane();
				
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
                                        
                                        NukleosomVase nukl = new NukleosomVase(valueArray[mod], horizontal, width, height);
					innerGrid.add(nukl, innerX, innerY);
                                        nuklList.add(nukl);
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
                                        NukleosomVase nukl = new NukleosomVase(valueArray[mod], horizontal, width, height);
					innerGrid.add(nukl, innerX, innerY);
                                        nuklList.add(nukl);
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
		
		zeitSchritt = 0;
		
		innerX = 0;
		innerY = 0;
		
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
                                        NukleosomVase nukl = new NukleosomVase(valueArray[mod], !horizontal, width, height);
					innerGrid.add(nukl, innerX, innerY);
                                        nuklList.add(nukl);
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
			}       
		}
		break;
		
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
		
		zeitSchritt = 0;
		
		innerX = 0;
		innerY = 0;
		
		setAlignment(Pos.CENTER);
//		grid.setGridLinesVisible(true);
		
		for(zeitSchritt = 0; zeitSchritt < X; zeitSchritt++) {
		
			for(int nukleosom = 0; nukleosom < Y; nukleosom++) {
				
				int valueArray[] = timeVector.get(zeitSchritt).get(nukleosom).get(0);
				
				BigNukleosomVase innerGrid = new BigNukleosomVase(valueArray, horizontal, "", width, height);
				
				if(horizontal) {
					innerX = nukleosom;
					innerY = zeitSchritt;
				}
				else {
					innerX = zeitSchritt;
					innerY = nukleosom;
				}
                                
                                
				add(innerGrid, innerX, innerY);
                                bigNuklList.add(innerGrid);
			}       
		}
		break;
            }
	}
        
        public double getExportWidth() {
            
            NukleosomVase nukl = new NukleosomVase(1, horizontal, width, height);
           
            Bounds bounds = this.getBoundsInLocal();
            
            return bounds.getMaxX() - bounds.getMinX();
        }
        
        public double getExportHeight() {
            
            NukleosomVase nukl = new NukleosomVase(1, horizontal, width, height);

            Bounds bounds = this.getBoundsInLocal();
            
            return bounds.getMaxY() - bounds.getMinY();
        }

    public ArrayList<NukleosomVase> getNuklList() {
        return nuklList;
    }
    
   public ArrayList<BigNukleosomVase> getBigNuklList() {
        return bigNuklList;
    }

}

