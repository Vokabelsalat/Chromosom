package StarNukleosom;

import java.util.ArrayList;
import java.util.List;

import NukleosomReader.NukleosomReader;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BigRoundNukleosomRow extends Application {

	@Override
	public void start(Stage primaryStage) {
		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(0);
		grid.setVgap(0);
		
		int 	maxX = 15,
				number = maxX * 8,
				y = 0,  x = 0;
		
		List<String> returnList = NukleosomReader.readNukleosoms(0,number * 4 + 10);
		
		int offset = 2;
		
		for(int i = 0; i < number; i++) {
			
			List<double[]> valueList = new ArrayList<double[]>();
			
			for(int u = 0; u < 4; u++) {
				
				valueList.add(NukleosomReader.getValueArray(returnList.get(offset)));
				offset++;
			}
			
			if((x) % maxX == 0) {
				y++;
				x = 0;
			}
			x++;	
			
			grid.add(new BigRoundNukleosom(valueList), x,y);
		}

		Scene scene = new Scene(grid,1680,1024);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
