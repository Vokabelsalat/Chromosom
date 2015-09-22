/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import Nukleosom.BigNukleosomNew;
import application.ChromosomProject;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jakob
 */
public class HeatNukleosom extends Pane {
	
    int width; 
    int height;
        
    public HeatNukleosom(double value, int width, int height, boolean showLabels, boolean showStrokes) {

        this.width = width;
        this.height = height;

        setPrefSize(width, height);

        int x = 0;
        int y = 0;
        String histoneNumberString = "";

        Paint color;

        color = Color.BLUE;

        Rectangle rect = new Rectangle(width, height , color); 
        rect.setOpacity(value);   

        rect.setX(width);
        rect.setY(height);

        rect.setStroke(Color.GRAY);
        rect.setStrokeWidth(0.3);

        getChildren().add(rect);

        if(showLabels == true) {
            Label lab = new Label(String.valueOf(value));
            getChildren().add(lab);
        }

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                   project.addNukleosomToOptions(new BigNukleosomNew(project, histoneMap, project.nukleosomMinWidth * 4, project.nukleosomMinHeight * 4, true));
            }
        });
    }
}
