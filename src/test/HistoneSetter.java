/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/** 
 *
 * @author Jakob
 */
public class HistoneSetter extends Application{

    public static String zwischen = "";
    int maxX = 5;
    int maxY = 5;
    GridPane grid1 = new GridPane();
    GridPane grid2 = new GridPane();
            
    @Override
    public void start(Stage primaryStage) {
            SplitPane root = new SplitPane();
            
            BorderPane leftPane = new BorderPane();
            
            int x = 0;
            int y = 0;

            addEmptyAttributes(grid1, maxX, maxY);
            
            String attributeArray[] = {"H2A", "H2B", "H3", "H4"};
            x=0;
            y=0;
            
            addEmptyAttributes(grid2, 2, 2);
            
            //attriibuteArray zum Grid2 hinzufügen
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
            
            spinX.setPrefSize(50, 10);
            spinY.setPrefSize(50, 10);
            
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
                            for(int u = 1; u < grid1.getChildren().size(); u++) {
                                if(grid1.getChildren().get(u) instanceof AttributeLabel) {
                                    AttributeLabel lab = (AttributeLabel)grid1.getChildren().get(u);
//                                    if(!lab.getText().equals("")) {
//                                        for(int t = 1; t < grid2.getChildren().size(); t++) {
//                                            AttributeLabel label = (AttributeLabel)grid2.getChildren().get(t);
//                                            if(label.getText().equals("")) {
//                                                label.setText(lab.getText());
//                                                break;
//                                            }
//                                        }
//                                    }
//                                    grid1.getChildren().
                                    
//                                    System.err.println("TEXT. " + lab.getText() + " " + u);
                                }
//                                System.err.println(grid1.getChildren().size());
//                                grid1.getChildren().remove(grid1.getChildren().get(u));
//                                System.err.println(u);
                            } 
                        }
                        maxX = value;
                }
             });            
            
            svfY.valueProperty().addListener(new ChangeListener<Integer>() {
                public void changed(ObservableValue<? extends Integer> ov,
                    Integer old_val, Integer new_val) {
                        int value = Integer.parseInt(svfY.valueProperty().getValue().toString());
                        
                        for(int u = 1; u < (maxX * maxY); u++) {
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
                        } 
//                        grid1.getChildren().removeAll(grid1.getChildren());
                        
                        addEmptyAttributes(grid1, maxX, value);

                        maxY = value;
                }
             });
            
            leftPane.setLeft(spinY);
            leftPane.setTop(spinX);
            leftPane.setCenter(grid1);
            
            Button submit = new Button();
            submit.setText("Bestätigen");
            submit.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) {         
                 
                    addEmptyAttributes(grid1,3 ,3);
                    leftPane.setCenter(grid1);
                    
                } 
            });
            Pane buttonPane = new Pane();
            buttonPane.getChildren().add(submit);
            leftPane.setBottom(buttonPane);
            
            
            root.getItems().addAll(leftPane, grid2);
            
            Scene scene = new Scene(root, 500, 500);
            primaryStage.setTitle("HistoneSetter");
            primaryStage.setScene(scene);
            primaryStage.show();

    }

    private void addEmptyAttributes(GridPane grid, int x, int y) {
        
         for(int i = 1; i < grid.getChildren().size();i++) {
             grid.getChildren().remove(i);
         }
        
//         grid = new GridPane();
         
        int countX = 0, countY = 0;
        
        for(int i = 0; i < x*y; i++) {
            grid.add(new AttributeLabel(""), countX, countY);
            
            if(countX % (x-1) == 0 && countX > 0) {
                countY++;
                countX = 0;
            }
            else {
                countX++;
            }
        }
        
        grid.setGridLinesVisible(true);
    }

    public static void main(String[] args) {
            launch(args);
    }
    
}
 