package test;

import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXImagingTest extends Application {

    private ObservableList<String> items;
    private ListView list;
    private VBox root;
    private Scene scene;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Hello World");
        root = new VBox();
        scene = new Scene(root, 400, 250);
        list = new ListView();
        items = FXCollections.observableArrayList();
        Button btn = new Button();
        btn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                FXImaging imager = new FXImaging();
//                imager.nodeToImage(list,root.getChildren(),new File("st.png"));
                imager.sceneToImage(scene,new File("st.png"));
            }
        });
        for(int i = 0; i<10; i++ ) {
            items.add("Item "+i+"1");            
        }
        list.setItems(items);

        root.getChildren().addAll(list,btn);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}