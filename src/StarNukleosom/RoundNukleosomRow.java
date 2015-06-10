///*
//package application;
//
//import java.util.ArrayList;
//import java.util.List;
//import javafx.application.Application;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//
//public class RoundNukleosomRow extends Application {
//
//	@Override
//	public void start(Stage primaryStage) {
//		GridPane grid = new GridPane();
//		
//		grid.setAlignment(Pos.CENTER);
//		grid.setHgap(5);
//		grid.setVgap(5);
//		
//		int 	number = 50,
//				maxX = 10,
//				y = 0,  x = 0;
//		
//		List<String> returnList = NukleosomReader.readNukleosoms(0,60);
//		List<double[]> doubleList = new ArrayList<double[]>();
//		for(int i = 0; i < number; i++) {
//			
//			double valueArray[] = NukleosomReader.getValueArray(returnList.get(i+2));
//			
//			if((x) % maxX == 0) {
//				y++;
//				x = 0;
//			}
//			x++;	
//			
//			doubleList.add(valueArray);
//			
//			grid.add(new RoundNukleosom(valueArray), x,y);
//		}
//
//		NukleosomReader.mergeValueArrays(doubleList);
//		
//		Scene scene = new Scene(grid,1000,1000);
////		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//}
//*/
//
/////*
//package StarNukleosom;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import NukleosomReader.NukleosomReader;
//import javafx.application.Application;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//
//public class RoundNukleosomRow extends Application {
//
//	@Override
//	public void start(Stage primaryStage) {
//	
////		/*
//		GridPane grid = new GridPane();
//		
//		grid.setAlignment(Pos.CENTER);
//		grid.setHgap(0);
//		grid.setVgap(0);
//		
//		int 	maxX = 25,	
//				number = maxX * 28,
//				y = 0,  x = 0,
//				anzahl = 2, off = 0;
//	
//		int u;
//		
//		List<String> returnList = NukleosomReader.readNukleosoms(0,number + 10);
//		
//		for(int i = 0; i < number; i = i) {
//			List<double[]> doubleList = new ArrayList<double[]>();
//			for( u = 0; u < anzahl;u++) {
//				double valueArray[] = NukleosomReader.getValueArray(returnList.get(i + u + 2));
//			
//				doubleList.add(valueArray);
//			}
//			
//			i = i + u;
//			
//			if((x) % maxX == 0) {
//				y++;
//				x = 0;
//			}
//			
//			x++;	
//			
//			grid.add(new RoundNukleosom(NukleosomReader.mergeRoundValues(doubleList)), x,y);
//		}
////		*/
//
//		/*
//		GridPane grid = new GridPane();
//		
//		grid.setAlignment(Pos.CENTER);
//		grid.setHgap(5);
//		grid.setVgap(5);
//		
//		List<String> returnList = NukleosomReader.readNukleosoms(0,60);
//		
//		
//		int number = 6;
//		
//		int divide = ggt(number);
//		
//		for(int i = 0; i < number; i++) {
//			
//			double valueArray[] = NukleosomReader.getValueArray(returnList.get(i+2));
//								
//			grid.add(new RoundNukleosom(valueArray), i,0);
//		}
//
//		int anzahl = number;
//		int off = 0;
//		
//		for(int y = 1; y < 2; y++) {
//			divide = ggt(anzahl);
//			System.err.println(divide);
//			for(int x = 0; x < (anzahl/divide); x++) {
//				List<double[]> valueList = new ArrayList<double[]>();
//				for(int nuk = 0; nuk < divide; nuk++) {
//					try {
//						valueList.add(((RoundNukleosom)grid.getChildren().get(off)).getArray());
//						off++;
//					}
//					catch (ClassCastException e) {
//					}	
//				}
//				grid.add(new RoundNukleosom(NukleosomReader.mergeRoundValues(valueList)), x, y); 
//			}
//			anzahl = anzahl/divide;
//		}
//		*/
//		
//		Scene scene = new Scene(grid,1680,1080);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	
//	}
//	
//    public int ggt(int a) {
//    	
//    	int test = a-1;
//    	
//    	if(a >= 6)
//    		test = (a-1)/2;
//    	
//        for (int i = test; i > 1; i--) {
//            if ((a % i) == 0) {
//                return i;
//            }
//        }      
//        return 1;
//    }
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//}
////*/