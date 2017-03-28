/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Jakob
 */
public class HeatLegend extends HBox{
    
    HeatProject project;
    private HeatTimeStepSpinner spin;
    private HeatPairButton timePairButton;
    
    public HeatLegend(HeatProject project) {
        
        this.project = project;
        
        spin = new HeatTimeStepSpinner(project);
        
        timePairButton = new HeatPairButton();
        
        timePairButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                timePairButton.click();
//                if(project.getHeatLegend().getTimePairButton().isSelected()) {
                    for(HeatProject pro : project.getChromosom().projectList) {
                        if(pro != project) {
                            pro.getHeatLegend().getTimePairButton().setSelected(timePairButton.isSelected());
                            project.showNewHeatGrid(spin.getEditor().getText());
                            if(timePairButton.isSelected()) {
                                if(pro.getHeatReader().getTimeMap().containsKey(spin.getEditor().getText())) {
                                    pro.showNewHeatGrid(spin.getEditor().getText());
                                    pro.getHeatLegend().getHeatTimeSpinner().getEditor().setText(spin.getEditor().getText());
                                }
                            }
                        } 
                    }
//                }
            }
        });
        
        Dialog<ButtonType> dialog = new Dialog<>();;
        dialog.setResizable(true);
 
        dialog.setTitle("Icicle Options");

        IcicleOptions icicleOptions = new IcicleOptions(project);

        dialog.getDialogPane().setContent(icicleOptions);
        
//        dialog.getDialogPane().setMaxSize(500, 100);
        dialog.getDialogPane().setMinSize(500, 180);
        dialog.getDialogPane().setPrefSize(500, 180);
        dialog.setWidth(500);
        dialog.setHeight(500);

        ButtonType buttonTypeClose = new ButtonType("Close");
        
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeClose);

        Button icicleButton = new Button();
        
        Image image = new Image("file:IciclePlotIcon3.png");
        ImageView view = new ImageView(image);
        icicleButton.setGraphic(view);
        icicleButton.setMinWidth(20);
        icicleButton.setMinHeight(20);
        
        icicleButton.setOnAction(e -> {
            if (dialog.showAndWait().get() == buttonTypeClose){
                for(HeatProject pro : project.getChromosom().projectList) {
                    pro.setCritOrder(icicleOptions.getCritArray());
                    pro.showNewHeatGrid(project.getStep());
                }
           }
        });
        
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);

        if(project.getID() == 0) {

//            ImageView imageView = new ImageView(createColorScaleImage(120, 20, Orientation.HORIZONTAL));
//        
//            if(project.getChromosom().projectList.size() < 2) {
//                timePairButton.setVisible(false);
//            }
            
//            getChildren().addAll(spin, timePairButton, sep, new Label("0.0"), imageView, new Label("1.0"), icicleButton);
        }
        else {
            getChildren().addAll(spin, timePairButton); 
        }
        
        setSpacing(10.0);
        
        HBox legendBox = new HBox();
        ImageView imageView = new ImageView(createColorScaleImage(120, 20, Orientation.HORIZONTAL));
        legendBox.setSpacing(4);
        legendBox.getChildren().addAll( new Label("0.0"), imageView, new Label("1.0"));
        
        project.getHeatOptionsPanel().getChildren().addAll(new Label("Time Step:"), spin, new Separator(), new Label("Scale Legend:"), legendBox, new Separator(), new Label("Icicle Options:"), icicleButton, new Separator());
        
//        setStyle("-fx-border: 3px solid; -fx-border-color: black;");
    }
    
    public Image createColorScaleImage(int width, int height, Orientation orientation) {
        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();
        
        double step = 0.05;
        
//        double oldValue = step;
        
        if (orientation == Orientation.HORIZONTAL) {
            for (int x=0; x<width; x++) {
                
                double value = 0.0 + (1.0 - 0.0) * x / width;
                
//                if(value < oldValue) {
//                    value = oldValue - step;
//                }
//                else {
//                    oldValue = oldValue + step;
//                }
                
                Color color = HeatNukleosom.generateColorForValue(value);
                
                if(x==0 || x == width-1) {
                    color = Color.BLACK; 
                }
                
                for (int y=0; y<height; y++) {
                    pixelWriter.setColor(x, y, color);
                }
            }
        } else {
            for (int y=0; y <height; y++) {
                double value = 1.0 - (1.0 - 0.0) * y / height ;
                
//                if(value < oldValue) {
//                    value = oldValue - step;
//                }
//                else {
//                    oldValue = oldValue + step;
//                }
                
                Color color = HeatNukleosom.generateHighlightColorForValue(value);
                
                if(y==0 || y == height-1) {
                    color = Color.BLACK;
                }
                
                for (int x=0; x<width; x++) {
                    pixelWriter.setColor(x, y, color);
                }
            }
        }
        return image ;
    }

    /**
     * @return the timePairButton
     */
    public HeatPairButton getTimePairButton() {
        return timePairButton;
    }
    
    public HeatTimeStepSpinner getHeatTimeSpinner() {
        return spin;
    }
    
}
