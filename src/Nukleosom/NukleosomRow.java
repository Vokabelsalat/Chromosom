///*
package Nukleosom;

import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import NukleosomReader.NukleosomReader;
import application.ChromosomProject;

public class NukleosomRow extends BorderPane {

	public NukleosomRow(ChromosomProject project) {
		
		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(3);
		grid.setVgap(5);
		
		int 	maxX = 50,
				number = maxX * 26,
				y = 0,  x = 0;
		
		List<String> returnList = project.getReadedNukleosoms();
		
		for(int i = 0; i < number; i++) {
			
			double valueArray[] = NukleosomReader.getValueArray(returnList.get(i+2));
			
			if((x) % maxX == 0) {
				y++;
				x = 0;
			}
			x++;	
			
			grid.add(new Nukleosom(valueArray, 7, 7), x,y);
		}
		setCenter(grid);
	}
}
//*/

/*
package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NukleosomRow extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(5);
		grid.setVgap(5);
		
		List<String> returnList = NukleosomReader.readNukleosoms(0,60);
		
		int number = 20;
		
		int divide = ggt(number);
		
		for(int i = 0; i < number; i++) {
			
			double valueArray[] = NukleosomReader.getValueArray(returnList.get(i+2));
								
			grid.add(new Nukleosom(valueArray), i,0);
		}


		int anzahl = number;
		int off = 0;
		
		for(int y = 1; y < 2; y++) {
			divide = ggt(anzahl);
			System.err.println(divide);
			for(int x = 0; x < (anzahl/divide); x++) {
				List<double[]> valueList = new ArrayList<double[]>();
				for(int nuk = 0; nuk < divide; nuk++) {
					try {
						valueList.add(((Nukleosom)grid.getChildren().get(off)).getArray());
						off++;
					}
					catch (ClassCastException e) {
					}	
				}
				grid.add(new Nukleosom(NukleosomReader.mergeValueArrays(valueList)), x, y); 
			}
			anzahl = anzahl/divide;
		}
		
		double[] array = {0,0.3,0.5,0.7,1.0};
		grid.add(new Nukleosom(array), off ,1);
		
		Scene scene = new Scene(grid,1000,1000);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}
	
    public int ggt(int a) {
    	
    	int test = a-1;
    	
    	if(a >= 6)
    		test = (a-1)/2;
    	
        for (int i = test; i > 1; i--) {
            if ((a % i) == 0) {
                return i;
            }
        }      
        return 1;
    }

	public static void main(String[] args) {
		launch(args);
	}
}
*/