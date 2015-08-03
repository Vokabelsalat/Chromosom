/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Vector;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/** 
 *
 * @author Jakob
 */
public class HistoneSetter extends Application{

    public static String zwischen = "";
    int maxX = 3;
    int maxY = 3;
    
    @Override
    public void start(Stage primaryStage) {
            SplitPane root = new SplitPane();
            
            BorderPane leftPane = new BorderPane();
            
//            AttributeLabel lab1 = new AttributeLabel("H2A");
//            AttributeLabel lab2 = new AttributeLabel("H2B");
//            AttributeLabel lab3 = new AttributeLabel("H3");
//            AttributeLabel lab4 = new AttributeLabel("H4");
//            
//            lab1.setPrefSize(30, 30);
//            lab2.setPrefSize(30, 30);
//            lab3.setPrefSize(30, 30);
//            lab4.setPrefSize(30, 30);
//            
//            root.add(lab1,0,0);
//            root.add(lab2,1,0);
//            root.add(lab3,0,1);
//            root.add(lab4,1,1);
            
            System.out.println("javafx.runtime.version: " + System.getProperties().get("javafx.runtime.version")); 

            
            int x = 0;
            int y = 0;
            
            GridPane grid1 = new GridPane();
            GridPane grid2 = new GridPane();
            
            grid1.setGridLinesVisible(true);
            grid2.setGridLinesVisible(true);
            
            for(int i = 0; i < 9; i++) {
                grid1.add(new AttributeLabel(""), x, y);
                
                if(x % (maxX-1) == 0 && x > 0) {
                    y++;
                    x = 0;
                }
                else {
                    x++;
                }
            }
            
            String attributeArray[] = {"H2A", "H2B", "H3", "H4"};
            x=0;
            y=0;
            
            for(int i = 0; i < attributeArray.length; i++) {
                grid2.add(new AttributeLabel(attributeArray[i]), x, y);
                
                if(x % (maxX-1) == 0 && x > 0) {
                    y++;
                    x = 0;
                }
                else {
                    x++;
                }
            }
            
            Spinner spinX = new Spinner(0,0,3);
            Spinner spinY = new Spinner(0,0,3);
            
            SpinnerValueFactory svfX = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100,3,1);
            spinX.setValueFactory(svfX);
            spinX.setEditable(true);
            
            SpinnerValueFactory svfY = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100,3,1);
            spinY.setValueFactory(svfY);
            spinY.setEditable(true);

            
            svfX.valueProperty().addListener(new ChangeListener<Integer>() {
                public void changed(ObservableValue<? extends Integer> ov,
                    Integer old_val, Integer new_val) {
                        int value = Integer.parseInt(svfX.valueProperty().getValue().toString());
                        if(maxX < value) {
//                            for(int u = 0; u < maxY; u++) {
//                                grid1.add(new AttributeLabel(""), value-1, u);
//                            } 
                            
                            Node nodes[] = new Node[maxY];
                            for(int z = 0; z < maxY; z++) {
                                nodes[z] = new AttributeLabel("");
                            }
                            
                            grid1.addColumn(value, nodes);
                        }
                        else {
                            for(int u = maxX; u < grid1.getChildren().size(); u = u + maxX) {
//                                if(grid1.getChildren().get(u) instanceof AttributeLabel) {
//                                    AttributeLabel lab = (AttributeLabel)grid1.getChildren().get(u);
//                                    if(!lab.getText().equals("")) {
//                                        for(int t = 1; t < grid2.getChildren().size(); t++) {
//                                            AttributeLabel label = (AttributeLabel)grid2.getChildren().get(t);
//                                            if(label.getText().equals("")) {
//                                                label.setText(lab.getText());
//                                                break;
//                                            }
//                                        }
//                                    }
//                                }
//                                System.err.println(grid1.getChildren().size());
                                grid1.getChildren().remove(grid1.getChildren().get(u));
                                System.err.println(u);
                            } 
                        }
                        maxX = value;
                }
             });            
            
            svfY.valueProperty().addListener(new ChangeListener<Integer>() {
                public void changed(ObservableValue<? extends Integer> ov,
                    Integer old_val, Integer new_val) {
                        int value = Integer.parseInt(svfY.valueProperty().getValue().toString());
                        if(maxY < value) {
                            for(int u = 0; u < maxX; u++) {
                                grid1.add(new AttributeLabel(""), u, value-1);
                            } 
                        }
                        else {
                            for(int u = maxX * maxY; u > (maxX * maxY) - maxX; u--) {
                                if(grid1.getChildren().get(u) instanceof AttributeLabel) {
                                    AttributeLabel lab = (AttributeLabel)grid1.getChildren().get(u);
                                    if(!lab.getText().equals("")) {
                                        for(int t = 1; t < grid2.getChildren().size(); t++) {
                                            AttributeLabel label = (AttributeLabel)grid2.getChildren().get(t);
                                            if(label.getText().equals("")) {
                                                label.setText(lab.getText());
                                                break;
                                            }
                                        }
                                    }
                                }
                                
                                grid1.getChildren().remove(grid1.getChildren().get(u));
                            } 
                        }
                        maxY = value;
                }
             });
            
            leftPane.setLeft(spinY);
            leftPane.setTop(spinX);
            leftPane.setCenter(grid1);
            
            root.getItems().addAll(leftPane, grid2);
            
            Scene scene = new Scene(root, 500, 500);
            primaryStage.setTitle("HistoneSetter");
            primaryStage.setScene(scene);
            primaryStage.show();

    }

    public static void main(String[] args) {
            launch(args);
    }
    
}
 