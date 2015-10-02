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
    private int ID = 0;
    
    public HeatProject(Chromosom chromosom, int ID) {
        this.chromosom = chromosom;
        this.ID = ID;

        prepareReader();
    }
    
    public void prepareReader() {
        heatReader = new HeatReader();
        
        File testFile = new File("test.txt");
        
        String pazText = testFile.getAbsolutePath().replaceAll(testFile.getName(), "logs");
    
        heatReader.searchForLogFiles(pazText);
        
    }
    
    public BorderPane createHeatMainPanel() {
        borderPane = new BorderPane();
        
        if(getChromosom().sameColumns && getChromosom().sameRow) {
            this.twoHeatMaps = true;
        }
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
        
        
        
        heatLegend = new HeatLegend(this);
        borderPane.setBottom(heatLegend);
        
        showNewHeatGrid(heatReader.getFirstItemInTimeMap());
        
        return getBorderPane();
    }
    
    public void showNewHeatGrid(String newValue) {
        heatGrid = new HeatNukleosomGrid(this, newValue, twoHeatMaps);

        heatOptionsPanel.resetOptionPanel();
        
        ScrollPane sp = new ScrollPane();
        BorderPane.setMargin(sp, new Insets(7,7,7,7));
        sp.setContent(getHeatGrid());
        borderPane.setCenter(sp);
        
        if(getHeatOptionsPanel().getRangeBox().isSelected()) {
            if(getHeatGrid() != null) {
                getHeatGrid().setHighlightedList(new ArrayList<>());
                getHeatGrid().highlightNear(getHeatOptionsPanel().nearSpinValueFactory.getValue(), getHeatOptionsPanel().rangeSpinValueFactory.getValue());
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

    
}
