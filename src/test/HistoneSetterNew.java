/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/** 
 *
 * @author Jakob
 */
public class HistoneSetterNew extends Application{

    public static String zwischen = "";
    int maxX = 2;
    int maxY = 2;
    GridPane grid1 = new GridPane();
    GridPane grid2 = new GridPane();
    
    int countX = 0;
    int countY = 0;
    
    int anzahl = 1;
            
    @Override
    public void start(Stage primaryStage) {
            SplitPane root = new SplitPane();

            BorderPane leftPane = new BorderPane();
            
            for(int i = 0; i < 4; i++) {
                grid1.add(new AttributeLabel(""), countX, countY);

                if(countX % (maxX-1) == 0 && countX > 0) {
                    countY++;
                    countX = 0;
                }
                else {
                    countX++;
                }
            }            

            grid1.setHgap(1);
            grid1.setVgap(1);
            grid1.setGridLinesVisible(true);
            
            Label title = new Label("Histone:");
            
            leftPane.setTop(title);
            leftPane.setCenter(grid1);

            Button but = new Button();
            but.setText("Hallo");
            but.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
                    
                    leftPane.getChildren().remove(grid1);
                    
                    grid1 = new GridPane();
                    
                    countX = 0;
                    countY = 0;
                    
                    for(int i = 0; i < anzahl; i++) {
                        grid1.add(new AttributeLabel(""), countX, countY);

                        if(countX % (maxX-1) == 0 && countX > 0) {
                            countY++;
                            countX = 0;
                        }
                        else {
                            countX++;
                        }
                    }            

                    grid1.setGridLinesVisible(true);
                    anzahl++;
                    leftPane.setCenter(grid1);
                } 
            });
            
            root.getItems().addAll(leftPane, but);
            
            Scene scene = new Scene(root, 500, 500);
            primaryStage.setTitle("HistoneSetter");
            primaryStage.setScene(scene);
            primaryStage.show();

    }

    public static void main(String[] args) {
            launch(args);
    }
    
}
 