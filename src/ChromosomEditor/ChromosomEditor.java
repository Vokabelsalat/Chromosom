/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import application.Chromosom;
import java.util.HashMap;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jakob
 */
public class ChromosomEditor extends GridPane{
    
    private Chromosom chromosom;
    private Pane[] paneArray;
    private HistoneSetter histoneSetter;
    private SiteSetter siteSetter;
    private EditorOptions options;
    private String[] histoneArray;
    private int paneIndex = 0;
    private EditorBottomPane bottom;
    private HashMap<String, String[][]> histoneMap;
    private EditorRuleDesigner ruleDesigner;
//    private String oldHistoneArray[];
    
    public ChromosomEditor(Chromosom chromosom) {
        this.chromosom = chromosom;
        this.paneArray = new Pane[3];
        
        histoneMap = new HashMap<>();
        
        this.setMinSize(500,400);
//        this.setAlignment(Pos.CENTER);
        
        showSiteSetter();
    }
    
    public void showSiteSetter() {
        siteSetter = new SiteSetter(this);
        paneArray[0] = siteSetter;
        this.getChildren().add(siteSetter);
    }
    
    public void showEditorPane(int index) {
        
        paneIndex = index;
        
        if(index != 0) {
            bottom.getBack().setVisible(true);
        }
        else {
            bottom.getBack().setVisible(false);
        }
        
        switch(index) {
            
            case  1: 
                siteSetter.next();
                break;
            case 2:
                ruleDesigner.next();
                break;
        }
        
//        if(paneArray[index] != null) {

//        }
        switch(index) {
            case 0:
                chromosom.getRootLayout().setRight(options);
                break;
            case 1:
                ruleDesigner = new EditorRuleDesigner(this);
                this.getChildren().removeAll(this.getChildren());
                paneArray[index] = ruleDesigner;
                chromosom.getRootLayout().getChildren().remove(options);
                break;
        }
        
        this.getChildren().removeAll(this.getChildren());
        this.getChildren().add(paneArray[index]);

    }

    /**
     * @return the histoneArray
     */
    public String[] getHistoneArray() {
        return histoneArray;
    }

    /**
     * @param histoneArray the histoneArray to set
     */
    public void setHistoneArray(String[] histoneArray) {
        this.histoneArray = histoneArray;
    }

    /**
     * @return the paneIndex
     */
    public int getPaneIndex() {
        return paneIndex;
    }

    /**
     * @param paneIndex the paneIndex to set
     */
    public void setPaneIndex(int paneIndex) {
        this.paneIndex = paneIndex;
    }

    /**
     * @return the bottom
     */
    public EditorBottomPane getBottom() {
        return bottom;
    }

    /**
     * @param bottom the bottom to set
     */
    public void setBottom(EditorBottomPane bottom) {
        this.bottom = bottom;
    }

    /**
     * @return the siteSetter
     */
    public SiteSetter getSiteSetter() {
        return siteSetter;
    }

    /**
     * @param siteSetter the siteSetter to set
     */
    public void setSiteSetter(SiteSetter siteSetter) {
        this.siteSetter = siteSetter;
    }

    /**
     * @return the options
     */
    public EditorOptions getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(EditorOptions options) {
        this.options = options;
    }

    /**
     * @return the histoneSetter
     */
    public HistoneSetter getHistoneSetter() {
        return histoneSetter;
    }

    /**
     * @param histoneSetter the histoneSetter to set
     */
    public void setHistoneSetter(HistoneSetter histoneSetter) {
        this.histoneSetter = histoneSetter;
    }

    /**
     * @return the histoneMap
     */
    public HashMap<String, String[][]> getHistoneMap() {
        return histoneMap;
    }

    /**
     * @param histoneMap the histoneMap to set
     */
    public void setHistoneMap(HashMap<String, String[][]> histoneMap) {
        this.histoneMap = null;
        this.histoneMap = histoneMap;
    }
    
}
