/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HeatChromosom;

import ChromosomEditor.HistoneSetter;
import static HeatChromosom.HeatProject.HeatNukleosomWidth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ChromosomEditor.AttributeLabel;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Jakob
 */
public class HeatIcicle extends GridPane{
    
    HeatProject project;
    ArrayList<SectionPane> categorized;
    private String critArray[]; 
    public HeatIcicle(HeatProject project, String[] critArray, String timeStep) {
        
        this.project = project;
        this.critArray = critArray;
        categorized = new ArrayList<>();
        
        if(project.getHeatReader().getTimeMap().containsKey(timeStep) && project.getHeatReader().getTimeMap().get(timeStep) == null) {
            project.getHeatReader().readLogFile(timeStep);
        }
        
        ArrayList<ArrayList<Double>> list = project.getHeatReader().getTimeMap().get(timeStep);
        ArrayList<Integer> newList = new ArrayList<>();
        
        for(int channel = 0; channel < list.size(); channel++) {
            newList.add(channel);
        }
        
        SectionPane sect = new SectionPane(0, newList, null, this);
        
        add(sect, 0, 0);
        
        project.setCategorized(categorized);
    }

    /**
     * @return the critArray
     */
    public String[] getCritArray() {
        return critArray;
    }

    /**
     * @param critArray the critArray to set
     */
    public void setCritArray(String[] critArray) {
        this.critArray = critArray;
    }
}

class IcicleOptions extends BorderPane{

    GridPane leftPane;
    HeatProject project;
    
    public IcicleOptions(HeatProject project) {
        this.project = project;
        String critArray[] = project.getCritReservoir();
        String emptyArray[] = project.getCritOrder();

        leftPane = HistoneSetter.addAttributeLabels(emptyArray, 3);
//        leftPane.setStyle("-fx-border-width: 2px; -fx-border-color: transparent; -fx-spacing: 4px;");
        leftPane.setMinWidth(200);

        GridPane rightPane = HistoneSetter.addAttributeLabels(critArray, critArray.length);
//        rightPane.setStyle("-fx-border-width: 2px; -fx-border-color: transparent; -fx-spacing: 4px;");
        
        HBox hbox = new HBox();
        hbox.setSpacing(40);
        
        VBox leftBox = new VBox();
        leftBox.setSpacing(5);
        VBox rightBox = new VBox();
        rightBox.setSpacing(5);
        
        leftBox.getChildren().addAll(new Label("Icicle Plot:"), leftPane);
        rightBox.getChildren().addAll(new Label("Criterion Reservoir:"), rightPane);
        
        hbox.getChildren().addAll(leftBox, rightBox);

        setCenter(hbox);
    }
    
    public String[] getCritArray() {
        String[] critArray = new String[3];
        
        for(int x = 0; x < critArray.length; x++) {
            for(Node node : leftPane.getChildren()) {
                if(GridPane.getColumnIndex(node) == x) {
                    if(node instanceof ChromosomEditor.AttributeLabel) {
                        AttributeLabel lab = (AttributeLabel)node;
                        critArray[x] = lab.getText();
                    }
                }
            }
        }
        
        return critArray;
    }

}

class SectionPane extends BorderPane {
    
    int inOrder;
    ArrayList<Integer> memberList;
    HeatIcicle icicle;
    private boolean selected = false;
    private ArrayList<CritPane> critList;
    private ArrayList<SectionPane> sectionList;
    
    public SectionPane(int inOrder, ArrayList<Integer> memberList, CritPane parentCrit, HeatIcicle icicle) {
        this.inOrder = inOrder;
        this.memberList = memberList;
        this.icicle = icicle;
        
        critList = new ArrayList<>();
        sectionList = new ArrayList<>();
        
        GridPane vbox = new GridPane();
        int rowCount = 0;
        
        LinkedHashMap<String, ArrayList<Integer>> map = null;
        
        switch(icicle.getCritArray()[inOrder]) {
            case "Association":
                map = icicle.project.getHeatReader().categorizeAssociation(memberList);
                break;
            case "Enzyme":
                map = icicle.project.getHeatReader().categorizeEnzyme(memberList);
                break;
            case "Histone":
                map = icicle.project.getHeatReader().categorizeHistone(memberList);
                break;
            case "Site":
                map = icicle.project.getHeatReader().categorizeSite(memberList);
                break;
            default :
                map = icicle.project.getHeatReader().categorizeByTagName(memberList, icicle.getCritArray()[inOrder]);
                break;
        }
        
        for(String critKey : map.keySet()) {

            BorderPane row = new BorderPane();

            CritPane crit = new CritPane(critKey, map.get(critKey), icicle);
            critList.add(crit);
            crit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        crit.click();
                    }
                }
            });

            row.setCenter(crit);

            if(inOrder < 2) {
                SectionPane nextSection = new SectionPane(inOrder+1, map.get(critKey), crit, icicle);
                sectionList.add(nextSection);
                row.setRight(nextSection);
            }

            if(parentCrit != null){
                parentCrit.addCritPane(crit);
            } 
            
            if(!icicle.getCritArray()[inOrder].equals("")) {
                vbox.add(row, 0, rowCount);
                rowCount++;
                vbox.setGridLinesVisible(true);
            }
        }

        if(inOrder == 0) {
            icicle.categorized.add(this);
        }

        setCenter(vbox);
    }
    
    /**
     * @return the critList
     */
    public ArrayList<CritPane> getCritList() {
        return critList;
    }

    /**
     * @param critList the critList to set
     */
    public void setCritList(ArrayList<CritPane> critList) {
        this.critList = critList;
    }

    /**
     * @return the sectionList
     */
    public ArrayList<SectionPane> getSectionList() {
        return sectionList;
    }

    /**
     * @param sectionList the sectionList to set
     */
    public void setSectionList(ArrayList<SectionPane> sectionList) {
        this.sectionList = sectionList;
    }
}

class CritPane extends GridPane {

    private boolean selected = false;
    private final String critKey;
    private final ArrayList<CritPane> critList;
    private final ArrayList<Integer> channelList;
    private HeatIcicle icicle;

    public CritPane(String critKey, ArrayList<Integer> channelList, HeatIcicle icicle) {
        critList = new ArrayList<>();
        this.channelList = channelList;
        this.critKey = critKey;
        this.icicle = icicle;
        setAlignment(Pos.CENTER);
        setPadding(new Insets(0,5,0,5));
        
        Text text = new Text(critKey);
        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        Label lab = new Label(critKey);
        Font f = Font.font("Arial", FontWeight.THIN, FontPosture.REGULAR, 10);
        lab.setFont(f);
        float high = fontLoader.getFontMetrics(f).getLineHeight();
        HeatNukleosomWidth = (int)Math.ceil(high)+1;
        lab.setMinHeight(HeatNukleosomWidth);
        getChildren().add(lab);
        
        int height = channelList.size() * HeatNukleosomWidth;
        setMinHeight(height);
        setMaxHeight(height);
        setPrefHeight(height);
        setStyle("-fx-border-width: 1px; -fx-border-color: black;");
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean sel) {
        this.selected = sel;
        
        for(CritPane pane : critList) {
            pane.setSelected(sel);
        }
        
        if(sel == true) {
            setStyle("-fx-background-color: beige; -fx-border-width: 1px; -fx-border-color: black;");
        }
        else {
            setStyle("-fx-border-width: 1px; -fx-border-color: black;");
        }
        icicle.project.getHeatGrid().highlightCrit(channelList, sel);
    }
    
    public void addCritPane(CritPane pane) {
        critList.add(pane);
    }

    public void click() {
        
        if(selected == true) {
            setSelected(false);
            for(CritPane pane : critList) {
                pane.setSelected(false);
            }
        }
        else {
            setSelected(true);
            for(CritPane pane : critList) {
                pane.setSelected(true);
            }
        }
    }

    public String getCritKey() {
        return critKey;
    }

    /**
     * @return the channelList
     */
    public ArrayList<Integer> getChannelList() {
        return channelList;
    }
}

