 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chromosomtesting;

import NukleosomVase.NukleosomVase;
import application.ChromosomExport;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import application.ChromosomProject;
import javafx.scene.control.Tooltip;
/**
 *
 * @author jakob
 */
public class ChromosomTesting extends Application {
    
    double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    
    @Override
    public void start(Stage primaryStage) {
        
//        Polygon polygon = new Polygon();
//
//        polygon.getPoints().addAll(new Double[]{
//            10., 10.,
//            50. , 10.,
//            10., 40. });
//        polygon.setFill(Color.BLUE);
//        
//        Line line = new Line(10,10,50,50);
//        
//        Rectangle rect = new Rectangle();
//        rect.setX(50);
//        rect.setY(50);
//        rect.setWidth(50);
//        rect.setHeight(50);
//
//        Color color = Color.rgb(255,50,150);
//        rect.setFill(color);
//        
//        Paint paint = rect.getFill();
        
//        Pane pan = new Pane();
//        pan.getChildren().add(line);
//        pan.getChildren().add(polygon);
//        pan.getChildren().add(rect);
//        
//        for(Node nod :pan.getChildren()) {
//            svgString += addToSVGString(nod);           
//        }
//        
//        writeToFile(svgString);
        
//        int valueArray[] = {0,1,0,4,0,0,4,0,1,0,2,0,3,0,1,0,4,0,0,0};
//        Nukleosom nukl = new Nukleosom(valueArray,8,8);
        
//        Rectangle rect = new Rectangle(20,20,10,10);
        NukleosomVase  vase = new NukleosomVase(4,true,100,100);
        
//        BigNukleosomRow row = new BigNukleosomRow(project,2,2,project.getNukleosomWidth(), project.getNukleosomHeight());
        
//        SunburstNukleosomRow sun = new SunburstNukleosomRow(project, 2, 2, project.getSunburstWidth(), project.getSunburstHeight());
//       
        BorderPane pan = new BorderPane();
//        pan.getChildren().add(sun);
        
//        List<int[]> valueList = new ArrayList<int[]>();
//
//        for(int i = 0; i < project.getHistoneNumber();i++) {
//                int valueArray[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//                valueList.add(valueArray);
//        }
//
//        SunburstNukleosom nukl = new SunburstNukleosom(project,valueList, project.getSunburstWidth(), project.getSunburstHeight());
//
//        for(Node nod : nukl.getChildren()) {
//            
//            if(nod instanceof javafx.scene.layout.Pane) {
//                System.err.println(nod.getClass().getName());
//                
//                for(Node node : ((Pane)nod).getChildren()) {
//                    
//                    if(node instanceof javafx.scene.Group) {
//                        for(Node node2 : ((Group)node).getChildren()) {
//                             System.err.println(node2.getClass().getName());
//                        }
//                    }
//                    
//                }
//                
//            }
//       
        int valueArray[] = {0,1,0,3,3,0,0,2,0,4,0,2,0,2,4,4,1,2,0,3};
        
//        BigNukleosomNew newBig = new BigNukleosomNew(project,valueArray,project.getNukleosomWidth(), project.getNukleosomHeight());
//       NukleosomVase vase = new NukleosomVase(4, true, 8 ,12);
//       ChromosomExport.setExportSize(100, 100);
//       ChromosomExport.exportNodeToSVG(vase);
       
       ChromosomExport.writeToFile("test.svg");
        
//        pan.getChildren().add(vase);
//       pan.setCenter(vase);
       pan.setCenter(vase);
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        Tooltip t = new Tooltip("HELLO!");
        Tooltip.install(btn, t); 
        
        BorderPane root = new BorderPane();
        root.setTop(btn);
        root.setCenter(pan);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
