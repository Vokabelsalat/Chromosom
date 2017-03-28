/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import application.Chromosom;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

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
    public static int HeatNukleosomWidth = 10;
    public static double GridLineStrokeWidth = 0.2;
    private String step;
    public File file;
    private double paneHeight;
    private HeatIcicle icicleGrid;
    private String[] critOrder = {"", "", ""};
    private String[] critReservoir = {"Association", "Enzyme", "Histone", "Site"};
    private ArrayList<SectionPane> categorized;
    private ArrayList<String> critList;
    
    public HeatProject(Chromosom chromosom, int ID) {
        this.chromosom = chromosom;
        this.ID = ID;
    }
    
    public void prepareReader(File file) {
        heatReader = new HeatReader(this);
        
//        String pazText = file.getAbsolutePath().replaceAll(file.getName(), "");
//        System.err.println("PAZTEXT: " + file.getAbsolutePath());
        heatReader.searchForLogFiles(file.getAbsolutePath());
        
        String pathString = file.getAbsolutePath() + File.separator + heatReader.getFirstItemInTimeMap() + ".csv";
        
        this.file = new File(pathString);
    }
    
    public BorderPane createHeatMainPanel() {
        borderPane = new BorderPane();
        
        heatOptionsPanel = new HeatOptionsPanel(this);
        BorderPane.setMargin(heatOptionsPanel, new Insets(10,0,10,0));
        borderPane.setRight(heatOptionsPanel);
        
        step = file.getName().replaceAll(".csv", "");
            
        if(step.contains(File.separator)){
            step = step.substring(step.lastIndexOf(File.separator));
        }
        
        showNewHeatGrid(step);
        
        heatLegend = new HeatLegend(this);
//        borderPane.setBottom(heatLegend);
        
        return getBorderPane();
    }
    
    public void showNewHeatGrid(String newValue) {
        BorderPane innerBorder = new BorderPane();
        
        heatOptionsPanel.resetOptionPanel();
        
        icicleGrid = new HeatIcicle(this, getCritOrder(), step);
        icicleGrid.setGridLinesVisible(true);
        
        innerBorder.setLeft(icicleGrid);
        
        heatGrid = new HeatNukleosomGrid(this, newValue);
        step = newValue;
        
        ScrollPane sp = new ScrollPane();
        BorderPane.setMargin(sp, new Insets(7,7,7,7));
        
        innerBorder.setCenter(heatGrid);
                
        sp.setContent(innerBorder);
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

    /**
     * @return the paneHeight
     */
    public double getPaneHeight() {
        return paneHeight;
    }

    /**
     * @param paneHeight the paneHeight to set
     */
    public void setPaneHeight(double paneHeight) {
        this.paneHeight = paneHeight;
    }

    /**
     * @return the categorized
     */
    public ArrayList<SectionPane> getCategorized() {
        return categorized;
    }

    /**
     * @param categorized the categorized to set
     */
    public void setCategorized(ArrayList<SectionPane> categorized) {
        this.categorized = categorized;
    }

    /**
     * @return the critOrder
     */
    public String[] getCritOrder() {
        return critOrder;
    }

    /**
     * @param critOrder the critOrder to set
     */
    public void setCritOrder(String[] critOrder) {
        this.critOrder = critOrder;
    }

    /**
     * @return the critReservoir
     */
    public String[] getCritReservoir() {
        return critReservoir;
    }

    /**
     * @param critReservoir the critReservoir to set
     */
    public void setCritReservoir(String[] critReservoir) {
        this.critReservoir = critReservoir;
    }

    /**
     * @return the critList
     */
    public ArrayList<String> getCritList() {
        if(critList == null) {
            critList = new ArrayList<>();
        }
        return critList;
    }

    /**
     * @param critList the critList to set
     */
    public void setCritList(ArrayList<String> critList) {
        ArrayList<String> critArrayList = new ArrayList<>();
        
        for(String crit : critReservoir) {
            critArrayList.add(crit);
        }
        for(String crit : critList) {
            critArrayList.add(crit);
        }
        
        this.critList = critArrayList;
        
        critReservoir = critArrayList.toArray(critReservoir);
        
//        System.err.println("CRIT:" + critArrayList);
    }
    
    public void saveAsPNG() {
        heatGrid.saveAsPNG();
    }

}
