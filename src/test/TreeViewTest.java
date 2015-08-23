/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jakob
 */
public class TreeViewTest  extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
     TreeItem<String> rootItem = new TreeItem<>("Root");
    rootItem.setExpanded(true);

    TreeItem<String> item = new TreeItem<>("A");
    rootItem.getChildren().add(item);
    
    TreeItem<String> item2 = new TreeItem<>("C");
    item.getChildren().add(item2);
    
    item = new TreeItem<>("B");
    rootItem.getChildren().add(item);

    TreeView<String> tree = new TreeView<>(rootItem);
    StackPane root = new StackPane();
    root.getChildren().add(tree);
    primaryStage.setScene(new Scene(root, 300, 250));
    primaryStage.show();
  }
}

