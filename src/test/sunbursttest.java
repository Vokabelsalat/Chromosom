package test;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import NukleosomVase.NukleosomVaseGrid;
import application.ChromosomProject;

public class sunbursttest extends Application {

	@Override
	public void start(Stage primaryStage) {
		GridPane pan = new GridPane();
		pan.setAlignment(Pos.CENTER);
		ChromosomProject project = new ChromosomProject();
		
		List<int[]> list = new ArrayList<int[]>();
		
		int[] array = {1,0,3,0,1,2,3,4,1,4,1,0,3,0,1,2,3,4,1,4};
		
		list.add(array);
		
		pan.add(new NukleosomVaseGrid(project, 10, 75, 75,75),1,1);
//		pan.setGridLinesVisible(true);
		
		Scene scene = new Scene(pan,1920,1680);
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
