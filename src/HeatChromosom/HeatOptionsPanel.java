/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import org.controlsfx.control.RangeSlider;

/**
 *
 * @author Jakob
 */
public class HeatOptionsPanel extends VBox{
    
    private HeatProject project;
    private double oldProb = 0.0;
    private HeatRangeBox rangeBox;
    private HeatNearSpinner nearSpin;
    private HeatRangeSpinner rangeSpin;
    private int startRow = 4;
    private HeatOptionsGrid selectedNukleosoms;
    private HeatOptionsGrid resultNukleosom;
    private HeatPairButton pairButton;
    private HBox rangeHBox;
    private HBox hbox;
    private RangeSlider rangeSlider;
    
    public HeatOptionsPanel(HeatProject project) {
        
        this.project = project;
        this.setSpacing(2);
        this.setMinSize(220, 0);
        
//        setStyle("-fx-border: 3px solid; -fx-border-color: black;");
        
        getChildren().add(new Label("Result:"));
        
        resultNukleosom = new HeatOptionsGrid();
        getChildren().add(resultNukleosom);
        
        Separator sep = new Separator();
        getChildren().add(sep);
        
        getChildren().add(new Label("Selected Items:"));
        
        selectedNukleosoms = new HeatOptionsGrid();
        getChildren().add(selectedNukleosoms);
        
        Separator sep2 = new Separator();
        getChildren().add(sep2); 
        
        getChildren().add(new Label("Probability Highlight:"));
        
        hbox = new HBox();
        
        rangeSpin = new HeatRangeSpinner(project);
        nearSpin = new HeatNearSpinner(project);
        
        rangeBox = new HeatRangeBox(project);

        hbox.setSpacing(18);
        
        pairButton = new HeatPairButton();
        
        pairButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                for(HeatProject pro : project.getChromosom().projectList) {
                    pro.getHeatOptionsPanel().getPairButton().click();
                    if(pro != project) {
                        pro.getHeatOptionsPanel().getRangeBox().setSelected(getRangeBox().isSelected());
                        pro.getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().setValue(nearSpin.getNearSpinValueFactory().getValue());
                        pro.getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().setValue(rangeSpin.getRangeSpinValueFactory().getValue());
                    } 
                }
                
            }
        });
        
        Button play = new Button(">|");
        
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                playArray();
            }
        });
                
        if(project.isTwoHeatMaps() == false) {
            pairButton.setVisible(false);
        }
        
        hbox.getChildren().addAll(rangeBox, nearSpin, pairButton);

        rangeHBox = new HBox();
        rangeHBox.getChildren().addAll(new Label("Range:"), rangeSpin);
        rangeHBox.setSpacing(3);
        
        getChildren().addAll(hbox, rangeHBox);
    }
    
    void addHeatNukleosomToOptionPanel(HeatNukleosom nukl, int col, int row) {
         
            if(getChildren().get(row) instanceof javafx.scene.layout.GridPane) {
                GridPane table = (GridPane)getChildren().get(row);
                
                ArrayList<Node> nodeList = new ArrayList<>();
                
                for(Node nod : table.getChildren()) {
                    if(table.getColumnIndex(nod) == col) {
                        nodeList.add(nod);
                    }
                }
                
                for(Node nod : nodeList) {
                    table.getChildren().remove(nod);
                }
                
                if(nukl != null) {
                    table.add(createSelectedPane(nukl, col, row), col, 0);
                    DecimalFormat df = new DecimalFormat("#.####");
                    table.add(new Label(df.format(nukl.getProbability()).replace(".", ",")), col, 1);

                    //Value
                    table.add(new Label(String.valueOf(nukl.getOriginalValue())), col, 2);
                    
                    //Nukleosom
                    table.add(new Label(String.valueOf(nukl.x)), col, 3);

//                    Für die Action
//                    int y = (nukl.y/2);

                    //Enzyme
                    table.add(new Label(String.valueOf(project.getHeatReader().getChannelList().get(nukl.y))), col, 4);

                    //Channel
                    table.add(new Label(String.valueOf(nukl.y)), col, 5);

                    //Action
                    String action;
                    if(nukl.y%2 == 0) {
                        action = "association";
                    }
                    else {
                        action = "dissociation";
                    }
                    table.add(new Label(action), col, 6);
                }
        }
        
    }
    
    public void resetOptionPanel() {
        int index = getChildren().indexOf(selectedNukleosoms);
        selectedNukleosoms = new HeatOptionsGrid();
        getChildren().remove(index);
        getChildren().add(index, selectedNukleosoms);
    }

    /**
     * @return the rangeBox
     */
    public HeatRangeBox getRangeBox() {
        return rangeBox;
    }

    /**
     * @return the startRow
     */
    public int getStartRow() {
        return startRow;
    }

    public HeatRangeSpinner getRangeSpin() {
        return rangeSpin;
    }

    public HeatNearSpinner getNearSpin() {
       return nearSpin;
    }
    
    public void playArray() {
        
            ArrayList<Double> doubList = new ArrayList<>();
            
            ArrayList<ArrayList<Double>> enzymeList = project.getHeatReader().getTimeMap().get(project.getHeatGrid().timeStep);
        
        
            for(int enzyme = 0; enzyme < enzymeList.size(); enzyme++) {
                ArrayList<Double> nukleosomList = enzymeList.get(enzyme);
                for(int nukleosom = 0; nukleosom < nukleosomList.size(); nukleosom++) {
                    if(nukleosomList.get(nukleosom) > 0.0) {
                        doubList.add(nukleosomList.get(nukleosom));
                    }
                }
            }
            
            int value = 44100;
            try {
                for(double doub : doubList) {
                    byte[] buf = new byte[ 1 ];;
                    AudioFormat af = new AudioFormat((float )(15000.0 + (40000*doub)), 8, 1, true, false);
                    SourceDataLine sdl = AudioSystem.getSourceDataLine( af );
                    sdl.open();
                    sdl.start();
                    for( int i = 0; i < 1000 * (float )(15000.0 + (40000*doub)) / 10000; i++ ) {
                        double angle = i / ( (float )value / 440 ) * 2.0 * Math.PI;
                        buf[ 0 ] = (byte )( Math.sin( angle ) * 100 );
                        sdl.write( buf, 0, 1 );
                    }
                    sdl.drain();
                    sdl.stop();
                }               
            } catch (LineUnavailableException ex) {
                    Logger.getLogger(HeatOptionsPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public StackPane createSelectedPane(HeatNukleosom nukl, int col, int row) {
        StackPane pane = new StackPane();

        pane.setPrefSize(28, 28);
        pane.setAlignment(Pos.CENTER_LEFT);
        
        Color color = Color.GRAY;

        if(col == 1 && row >= getStartRow()) {
            color = HeatProject.RED;
        } 
        else if(col > 1) {
            color = HeatProject.GREEN;
        }

        Rectangle bg = new Rectangle(28,28,color);
        bg.setTranslateX(0);
        bg.setTranslateY(0);

        Rectangle fg = new Rectangle(24, 24, Color.WHITE);
        fg.setTranslateX(2);

        HeatNukleosom newNukl = new HeatNukleosom(nukl.value, nukl.x, nukl.y, 18, 18, "", nukl.getProbability(), nukl.getOriginalValue());
        newNukl.setTranslateX(5);
        newNukl.setTranslateY(5);

        pane.getChildren().addAll(bg, fg, newNukl);
        
        return pane;
    }

    /**
     * @return the pairButton
     */
    public HeatPairButton getPairButton() {
        return pairButton;
    }

    /**
     * @return the oldProb
     */
    public double getOldProb() {
        return oldProb;
    }

    /**
     * @param oldProb the oldProb to set
     */
    public void setOldProb(double oldProb) {
        this.oldProb = oldProb;
    }

    /**
     * @param rangeSpin the rangeSpinner to set
     */
    public void setRangeSpin(HeatRangeSpinner rangeSpin) {
        this.rangeSpin = rangeSpin;
    }

    /**
     * @param nearSpin the nearSpin to set
     */
    public void setNearSpin(HeatNearSpinner nearSpin) {
        this.nearSpin = nearSpin;
    }
    
}
