/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Jakob
 */
public class EditorNukleosom extends Pane{
    
//    ChromosomEditor editor;
    
    private ArrayList<StateBorderPane> stateList = new ArrayList<>();
    
    public EditorNukleosom(ChromosomEditor editor) {
        super();
        
        ArrayList<Node> nodeList = new ArrayList<>();
        
        int x = 0;
        int y = 0;
        
        GridPane grid = new GridPane();
        
        
        for(int i = 0; i < editor.getSiteSetter().getHistoneOrder().length; i++) {
            
            String histone = editor.getSiteSetter().getHistoneOrder()[i];
            
            if(histone != null && !histone.equals("")) {            
                StateBorderPane state = new StateBorderPane(histone, editor.getHistoneMap().get(histone), editor);
                nodeList.add(state);
                stateList.add(state);
                grid.add(state, x, y);
            }
            
            if((i+1)%2 == 0) {
                y++;
                x = 0;
            }
            else {
                x++;
            }
        } 
        
        grid.setHgap(5);
        grid.setVgap(5);
        getChildren().add(grid); 
        grid.setStyle("-fx-border: 1px solid; -fx-border-color: black;");
    }
    
    public ArrayList<ArrayList<String>> getHistoneList() {
        ArrayList<ArrayList<String>> histoneList = new ArrayList<>();
        
        for(StateBorderPane state : getStateList()) {
            histoneList.add(state.getValues());
        }
        
        return histoneList;
    }

    /**
     * @return the stateList
     */
    public ArrayList<StateBorderPane> getStateList() {
        return stateList;
    }
    
}

class EditorNukleosomRow extends HBox {
    
    ArrayList<EditorNukleosom> nukleosomList = new ArrayList<>();;
    
    public EditorNukleosomRow(ChromosomEditor editor) {
        super();
        setSpacing(15);
        for(int i = 0; i < 3; i++) {
            EditorNukleosom nukl = new EditorNukleosom(editor);
            getChildren().add(nukl);
            nukleosomList.add(nukl);
        } 
    }
    
    public ArrayList<EditorNukleosom> getNukleosomList() {
        return nukleosomList;
    }
}

class RuleDesignerRow extends HBox {
    
    private EditorNukleosomRow firstRow;
    private EditorNukleosomRow secondRow;
    private String rule = "";
    private Button minusButton;
    
            
    public RuleDesignerRow(ChromosomEditor editor) {
        super();
        
        Image image = new Image("file:Pfeil.png", 50, 50, false, false);
//        arrow = new ImageView(image);
        
        StackPane stackPane = new StackPane();
        stackPane.setMinSize(60, 60);
        stackPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        
        minusButton = new Button("-");
        GridPane minusPane = new GridPane();
        minusPane.setAlignment(Pos.CENTER);
        minusPane.getChildren().add(minusButton);

        firstRow = new EditorNukleosomRow(editor);
        secondRow = new EditorNukleosomRow(editor);
        
        getChildren().addAll(minusPane, firstRow, stackPane, secondRow);
        
        setPadding(new Insets(5,5,5,5));
        setSpacing(8);
        setStyle("-fx-border: 1px solid; -fx-border-color: black;");
        
    }

    /**
     * @return the firstRow
     */
    public EditorNukleosomRow getFirstRow() {
        return firstRow;
    }

    /**
     * @param firstRow the firstRow to set
     */
    public void setFirstRow(EditorNukleosomRow firstRow) {
        this.firstRow = firstRow;
    }

    /**
     * @return the secondRow
     */
    public EditorNukleosomRow getSecondRow() {
        return secondRow;
    }

    /**
     * @param secondRow the secondRow to set
     */
    public void setSecondRow(EditorNukleosomRow secondRow) {
        this.secondRow = secondRow;
    }

    /**
     * @return the rule
     */
    public String getRule() {
        return rule;
    }
    
    public void setRule() {
        
        
        
        rule += getRuleNukleosomString(firstRow);
        
        rule += "-->";
        
        rule += getRuleNukleosomString(secondRow);
        
        System.err.println(rule);
    }
    
    public String getRuleNukleosomString(EditorNukleosomRow nukleosomRow) {
        String returnStr = "";
        
        for(EditorNukleosom nukl : nukleosomRow.getNukleosomList()) {
            returnStr += " {";
            for(StateBorderPane state : nukl.getStateList()) {
                boolean empty = true;
                for(StateComboBox box : state.comboList) {
                    if(!box.getValue().equals("")) {
                        empty = false;
                    }
                }
                if(empty == false) {
                    returnStr += state.getTitle() + ":";
                    for(StateComboBox box : state.comboList) {
                        if(!box.getValue().equals("")) {
                            returnStr += box.title + "[" + box.getValue() + "] ";
                        }
                    }
                }
            }
            returnStr += "} ";
        }
        return returnStr;
    }

    /**
     * @return the minusButton
     */
    public Button getMinusButton() {
        return minusButton;
    }
}
