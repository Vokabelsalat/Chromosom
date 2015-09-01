///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package test;
//
//import application.ChromosomProject;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.SplitPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//
//
///** 
// *
// * @author Jakob
// */
//public class HistoneSetterNew extends BorderPane{
//
//    public static String zwischen = "";
//    int maxX = 2;
//    int maxY = 2;
//    GridPane grid2 = new GridPane();
//    
//    GridPane leftInnerGrid;
//    
//    Button back;
//    
//    boolean histone = true;
//    
//    int anzahl = 1;
//            
//   public HistoneSetterNew(ChromosomProject project) {
//        
//            SplitPane splitPane = new SplitPane();
//            String[] histoneArray = {"H2A", "H2B", "H3", "H4"};
//            String[] attributeArray = {"H3K4", "H3K27", "H4K4", "H4K27"};
//            String[] emptyText = {"","","",""};
//            BorderPane leftPane = new BorderPane();    
//            VBox rightPane = new VBox();
//
//            leftInnerGrid = addAttributeLabels(emptyText);
//            
//            grid2 = addAttributeLabels(histoneArray);
//
//            Label title = new Label("Histone:");
//            
//            HBox bottomPane = new HBox();
//            
//            back = new Button("<<");
//            back.setOnAction(new EventHandler<ActionEvent>() {     
//                @Override public void handle(ActionEvent e) { 
//                    leftInnerGrid = addAttributeLabels(emptyText);
//                    leftPane.setCenter(leftInnerGrid);
//                    
//                    rightPane.getChildren().remove(grid2);
//                    grid2 = addAttributeLabels(histoneArray);
//                    rightPane.getChildren().add(grid2);
//                } 
//            });
//            
//            Button next = new Button();
//            next.setText(">>");
//            next.setOnAction(new EventHandler<ActionEvent>() {     
//                @Override public void handle(ActionEvent e) { 
//                    if(histone == true) { 
//                        title.setText("Attribute:");
//                        leftInnerGrid = addAttributeLabels(emptyText);
//                        leftPane.setCenter(leftInnerGrid);
//                        histone = false;
//                    }
//                    else {
//                        rightPane.getChildren().remove(grid2);
//                        grid2 = addAttributeLabels(attributeArray);
//                        rightPane.getChildren().add(grid2);
//                        project.showNukleosoms = true;
//                    }
//                } 
//            });
//            
//            bottomPane.getChildren().add(back);
//            bottomPane.getChildren().add(next);
//            
//            leftPane.setTop(title);
//            leftPane.setCenter(leftInnerGrid);
//            
//            rightPane.getChildren().add(grid2);
//            
//            splitPane.getItems().addAll(leftPane, rightPane);
//            
//            setBottom(bottomPane);
//            setCenter(splitPane);
//            
//    }
//    
//    public GridPane addAttributeLabels(String[] array) {
//            
//        GridPane pan = new GridPane();
//
//        int countX = 0, countY = 0;
//
//        for(String text : array) {
//            pan.add(new AttributeLabel(text), countX, countY);
//
//            if(countX % (maxX-1) == 0 && countX > 0) {
//                countY++;
//                countX = 0;
//            }
//            else {
//                countX++;
//            }
//        }
//        
//        pan.setGridLinesVisible(true);
//
//        return pan;
//    }
//
//}
//
////                 leftPane.getChildren().remove(grid1);
////                    
////                    grid1 = new GridPane();
////                    
////                    int countX = 0;
////                    int countY = 0;
////                    
////                    for(int i = 0; i < anzahl; i++) {
////                        grid1.add(new AttributeLabel(""), countX, countY);
////
////                        if(countX % (maxX-1) == 0 && countX > 0) {
////                            countY++;
////                            countX = 0;
////                        }
////                        else {
////                            countX++;
////                        }
////                    }            
////
////                    grid1.setGridLinesVisible(true);
////                    anzahl++;
////                    leftPane.setCenter(grid1);