/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import application.Chromosom;
import java.io.File;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Jakob
 */
public class HeatProject {
    
    public static final Color RED = Color.rgb(255, 0, 0);

    public static final Color GREEN = Color.rgb(0, 255, 0);
    
    private Chromosom chromosom;
    private HeatReader heatReader;
    private HeatOptionsPanel heatOptionsPanel;
    private BorderPane borderPane;
    private HeatNukleosomGrid heatGrid;
    private HeatLegend heatLegend;
    private boolean twoHeatMaps = false;
    private boolean sameHeatMaps = false;
    private int ID = 0;
//    public static int HeatNukleosomWidth = 8;
    public static int HeatNukleosomWidth = 7;
    public static double GridLineStrokeWidth = 0.2;
    private String step;
    public File file;
    
    public HeatProject(Chromosom chromosom, int ID, File file) {
        this.chromosom = chromosom;
        this.ID = ID;
        this.file = file;

        prepareReader();
    }
    
    public void prepareReader() {
        heatReader = new HeatReader();
        
//        File testFile = new File("test.txt");
        
//        String pazText = testFile.getAbsolutePath().replaceAll(testFile.getName(), "logs");
        
        String pazText = file.getAbsolutePath().replaceAll(file.getName(), "");
    
        heatReader.searchForLogFiles(pazText);
    }
    
    public BorderPane createHeatMainPanel() {
        borderPane = new BorderPane();
        
//            heatOptionsPanel = new HeatOptionsPanel(this);
//            
//            if(chromosom.heatProject != null && chromosom.heatProject != this) {
//                heatOptionsPanel = chromosom.heatProject.getHeatOptionsPanel();
//                chromosom.getRootLayout().setRight(heatOptionsPanel);
//            }
//        }
//        else {
            heatOptionsPanel = new HeatOptionsPanel(this);
            borderPane.setRight(heatOptionsPanel);
//        }
        
        step = file.getName().replaceAll(".csv", "");
            
        showNewHeatGrid(step);
        
        heatLegend = new HeatLegend(this);
        borderPane.setBottom(heatLegend);
        
        return getBorderPane();
    }
    
    public void showNewHeatGrid(String newValue) {
        heatGrid = new HeatNukleosomGrid(this, newValue);
        step = newValue;

        heatOptionsPanel.resetOptionPanel();
        
        ScrollPane sp = new ScrollPane();
        BorderPane.setMargin(sp, new Insets(7,7,7,7));
        sp.setContent(getHeatGrid());
        borderPane.setCenter(sp);
        
        boolean sameRow = false, sameColumns = false;
        
        for(HeatProject proj : chromosom.projectList) {
            if(proj != this) {
                if(proj.getHeatReader().getTimeMap().get(proj.getStep()).size() == 
                    this.getHeatReader().getTimeMap().get(step).size()) {
                    sameRow = true;
                    if(proj.getHeatReader().getTimeMap().get(proj.getStep()).get(0).size() == 
                        this.getHeatReader().getTimeMap().get(this.getStep()).get(0).size()) {
                        sameColumns = true;
                    }
                }
                
                if(sameRow && sameColumns) {
                    proj.setSameHeatMaps(true);
                    this.setSameHeatMaps(true);
                }
                
            }
        }
        
        
        
        if(getHeatOptionsPanel().getRangeBox().isSelected()) {
            if(getHeatGrid() != null) {
                getHeatGrid().setHighlightedList(new ArrayList<>());
                getHeatGrid().highlightNear(getHeatOptionsPanel().getNearSpin().getNearSpinValueFactory().getValue(), getHeatOptionsPanel().getRangeSpin().getRangeSpinValueFactory().getValue());
            }
        }
    }

    /**
     * @return the borderPane
     */
    public BorderPane getBorderPane() {
        return borderPane;
    }

    /**
     * @return the heatReader
     */
    public HeatReader getHeatReader() {
        return heatReader;
    }

    /**
     * @return the heatGrid
     */
    public HeatNukleosomGrid getHeatGrid() {
        return heatGrid;
    }

    /**
     * @return the heatOptionsPanel
     */
    public HeatOptionsPanel getHeatOptionsPanel() {
        return heatOptionsPanel;
    }

    /**
     * @return the chromosom
     */
    public Chromosom getChromosom() {
        return chromosom;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    public HeatLegend getHeatLegend() {
        return heatLegend;
    }

    /**
     * @return the twoHeatMaps
     */
    public boolean isTwoHeatMaps() {
        return twoHeatMaps;
    }

    /**
     * @param twoHeatMaps the twoHeatMaps to set
     */
    public void setTwoHeatMaps(boolean twoHeatMaps) {
        this.twoHeatMaps = twoHeatMaps;
    }

    /**
     * @return the sameHeatMaps
     */
    public boolean isSameHeatMaps() {
        return sameHeatMaps;
    }

    /**
     * @param sameHeatMaps the sameHeatMaps to set
     */
    public void setSameHeatMaps(boolean sameHeatMaps) {
        this.sameHeatMaps = sameHeatMaps;
    }

    public String getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(String step) {
        this.step = step;
    }
}
