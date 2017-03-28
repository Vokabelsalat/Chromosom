/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jakob
 */
public class EditorNukleosom extends Pane{
    
    private HashMap<String, HashMap<String, StateComboBox>> histoneMap;
    private ArrayList<StateBorderPane> stateBorderPaneList = new ArrayList<>();
    
    public EditorNukleosom(EditorRule rule, boolean left, int nukleosomNr) {
        super();
        
        histoneMap = new HashMap<>();
        
        int x = 0;
        int y = 0;
        int i = 0;
        
        GridPane grid = new GridPane();
        
        for(String histone : rule.getEditor().getChromosomProject().getHistoneMap().keySet()) {
            
            if(histone != null && rule.getEditor().getChromosomProject().getHistoneMap().get(histone)!=null) {
                StateBorderPane state = new StateBorderPane(histone, rule.getEditor().getChromosomProject().getHistoneMap().get(histone), rule, left, nukleosomNr);
                stateBorderPaneList.add(state);
                histoneMap.put(histone, state.getComboMap());
                grid.add(state, x, y);
            }
            
            if ((i+1)%2 == 0) {
                y++;
                x = 0;
            }
            else {
                x++;
            }
            i++;
        } 
        
        
        grid.setHgap(5);
        grid.setVgap(5);
        getChildren().add(grid); 
        grid.setStyle("-fx-border-width: 1px; -fx-border-color: black;");
    }
    

    /**
     * @return the histoneMap
     */
    public HashMap<String, HashMap<String, StateComboBox>> getHistoneMap() {
        return histoneMap;
    }

    /**
     * @param histoneMap the histoneMap to set
     */
    public void setHistoneMap(HashMap<String, HashMap<String, StateComboBox>> histoneMap) {
        this.histoneMap = histoneMap;
    }

    /**
     * @return the stateBorderPaneList
     */
    public ArrayList<StateBorderPane> getStateBorderPaneList() {
        return stateBorderPaneList;
    }

    /**
     * @param stateBorderPaneList the stateBorderPaneList to set
     */
    public void setStateBorderPaneList(ArrayList<StateBorderPane> stateBorderPaneList) {
        this.stateBorderPaneList = stateBorderPaneList;
    }

    void setEditable(boolean b) {
        for(StateBorderPane state : stateBorderPaneList) {
            state.setEditable(b);
        }
    }
}

class EditorTagField extends HBox {
    
    private TextField tagName = new TextField();
    private TextField valueTF = new TextField();
    private Button minus = new Button("-");
    
    public EditorTagField(String tag) {
        
        tagName.setMaxWidth(90);
        valueTF.setMaxWidth(120);
        
        setSpacing(1);
        tagName.setText(tag);
        
        setAlignment(Pos.CENTER);
        getChildren().addAll(tagName, new Label(":") , valueTF, minus);
    }

    /**
     * @return the tagName
     */
    public TextField getTagName() {
        return tagName;
    }

    /**
     * @param tagName the tagName to set
     */
    public void setTagName(TextField tagName) {
        this.tagName = tagName;
    }

    /**
     * @return the valueTF
     */
    public TextField getValueTF() {
        return valueTF;
    }

    /**
     * @param valueTF the valueTF to set
     */
    public void setValueTF(TextField valueTF) {
        this.valueTF = valueTF;
    }

    /**
     * @return the minus
     */
    public Button getMinus() {
        return minus;
    }

    /**
     * @param minus the minus to set
     */
    public void setMinus(Button minus) {
        this.minus = minus;
    }
    
}


//class RuleDesignerRow extends BorderPane {
//    
//    private EditorNukleosomRow firstRow;
//    private EditorNukleosomRow secondRow;
//    private String rule = "";
//    private Button minusButton;
//    private ChromosomEditor editor;
//    private TextField nameTF;
//    private TextField conzentration;
//    private TextField associationRate;
//    private TextField dissociationRate;
//            
//    public RuleDesignerRow(ChromosomEditor editor) {
//        this.editor = editor;
//        
//        Image image = new Image("file:Pfeil.png", 50, 50, false, false);
//        
//        StackPane stackPane = new StackPane();
//        stackPane.setPrefSize(60, 60);
//        stackPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
//        
//        minusButton = new Button("-");
//        GridPane minusPane = new GridPane();
//        minusPane.setAlignment(Pos.CENTER);
//        minusPane.getChildren().add(minusButton);
//
//        firstRow = new EditorNukleosomRow(this, true);
//        secondRow = new EditorNukleosomRow(this, false);
//        
//        HBox hbox = new HBox();
//        hbox.getChildren().addAll(firstRow, stackPane, secondRow);
//        hbox.setPadding(new Insets(5,5,5,5));
//        hbox.setSpacing(8); 
//        
//        BorderPane innerBorder = new BorderPane();
//        innerBorder.setCenter(hbox);
//       
//        nameTF = new TextField("");
//        nameTF.setPrefWidth(150);
//                
//        associationRate = new TextField("");
//        associationRate.setPrefWidth(70);
//        dissociationRate = new TextField("");
//        dissociationRate.setPrefWidth(70);
//        
//        conzentration = new TextField("");
//        conzentration.setPrefWidth(70);
//        
//        CheckBox check = new CheckBox();
//        check.setSelected(true);
//        
//        HBox innerBox = new HBox();
//        innerBox.getChildren().addAll(check, new Label("Name:"), nameTF);
//        innerBox.setSpacing(5);
//        innerBox.setAlignment(Pos.CENTER_LEFT);
//        
//        GridPane rateGrid = new GridPane();
////        rateBox.setSpacing(5);
//        rateGrid.setAlignment(Pos.CENTER);
//        rateGrid.setHgap(5);
//        rateGrid.setVgap(5);
//        
//        rateGrid.add(new Label("Associationrate:"), 0, 0);
//        rateGrid.add(associationRate, 1, 0);
//        rateGrid.add(new Label("Dissociationrate:"), 0, 1);
//        rateGrid.add(dissociationRate, 1, 1);
//        
//        VBox tagBox = new VBox();
//        tagBox.setSpacing(5);
//        tagBox.getChildren().add(new EditorTagField("Process"));
//        
//        Button plus = new Button("+");
//        plus.setOnAction(e -> {
//            tagBox.getChildren().remove(plus);
//            EditorTagField tagField = new EditorTagField("");
//            tagField.getMinus().setOnAction(minus -> {
//                tagBox.getChildren().remove(tagField);
//            });
//            tagBox.getChildren().add(tagField);
//            tagBox.getChildren().add(plus);
//        });
//        
//        tagBox.setAlignment(Pos.CENTER);
//        tagBox.getChildren().add(plus);
//        
//        HBox fieldBox = new HBox();
//        fieldBox.setSpacing(20);
//        
//        AnchorPane anPane = new AnchorPane();
//        AnchorPane.setTopAnchor(rateGrid, 5.0);
//        anPane.getChildren().add(rateGrid);
//        
//        AnchorPane anPane2 = new AnchorPane();
//        AnchorPane.setTopAnchor(tagBox, 5.0);
//        anPane2.getChildren().add(tagBox);
//        
//        fieldBox.getChildren().addAll(anPane, anPane2);
//        
//        AnchorPane anchor = new AnchorPane();
//        AnchorPane.setTopAnchor(innerBox, 5.0);
//        AnchorPane.setTopAnchor(fieldBox, 5.0);
//        AnchorPane.setTopAnchor(innerBox, 5.0);
//        AnchorPane.setTopAnchor(fieldBox, 5.0);
//        AnchorPane.setBottomAnchor(innerBox, 5.0);
//        AnchorPane.setBottomAnchor(fieldBox, 5.0);
//        AnchorPane.setLeftAnchor(innerBox, 0.0);
//        AnchorPane.setRightAnchor(fieldBox, 5.0);
//        anchor.getChildren().addAll(innerBox, fieldBox);
//        
//        setCenter(innerBorder);
//        innerBorder.setBottom(anchor);
//        setLeft(minusPane);
//        setMinWidth(700);
//        setStyle("-fx-border: 1px solid; -fx-border-color: black; -fx-border-width: 2px;");
//        
//    }
//
//    /**
//     * @return the firstRow
//     */
//    public EditorNukleosomRow getFirstRow() {
//        return firstRow;
//    }
//
//    /**
//     * @param firstRow the firstRow to set
//     */
//    public void setFirstRow(EditorNukleosomRow firstRow) {
//        this.firstRow = firstRow;
//    }
//
//    /**
//     * @return the secondRow
//     */
//    public EditorNukleosomRow getSecondRow() {
//        return secondRow;
//    }
//
//    /**
//     * @param secondRow the secondRow to set
//     */
//    public void setSecondRow(EditorNukleosomRow secondRow) {
//        this.secondRow = secondRow;
//    }
//
//    /**
//     * @return the rule
//     */
//    public String getRule() {
//        return rule;
//    }
//    
//    public void setRule() {
//        
//        try {
//            EditorRule editorRule = new EditorRule(this);
//            
//            
//
//            
//              
//            
//            
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
////            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
////            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File("file.cfg"));
//            
//            // Output to console for testing
//            // StreamResult result = new StreamResult(System.out);
//            
//            transformer.transform(source, result);
//            
//            System.out.println("File saved!");
//            
//            rule += getRuleNukleosomString(firstRow);
//            
//            rule += "->";
//            
//            rule += getRuleNukleosomString(secondRow);
//            
//            System.err.println(rule);
//        } catch (TransformerConfigurationException ex) {
//            Logger.getLogger(RuleDesignerRow.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (TransformerException ex) {
//            Logger.getLogger(RuleDesignerRow.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public static String getRuleNukleosomString(EditorNukleosomRow nukleosomRow) {
//        String returnStr = "";
//        for(int nuklCount = 0; nuklCount < 3; nuklCount++) {
//            EditorNukleosom nukl = nukleosomRow.getNukleosomMap().get(String.valueOf(nuklCount));
//            String nukleosomString = "";
//            for(String histone : nukl.getHistoneMap().keySet()) {
//                boolean empty = true;
//                for(StateComboBox box : nukl.getHistoneMap().get(histone).values()) {
//                    if(!box.getValue().equals("")) {
//                        empty = false;
//                        break;
//                    }
//                }
//                if(empty == false) {
//                    
//                    for(StateComboBox box : nukl.getHistoneMap().get(histone).values()) {
//                        if(!box.getValue().equals("")) {
//                            nukleosomString += histone + "[" + box.title + "." + box.getValue() + "]";
//                        }
//                    }
//                }
//            }
//            
//          
//            
//            if(!nukleosomString.equals("")) {
//
//                nukleosomString = "{" +  nukleosomString + "}";
//            }
//            
//            if(nuklCount == 1) {
//                nukleosomString = "(" + nukleosomString + ")";
//            }
//                
//            returnStr += nukleosomString;
//        }
//        return returnStr;
//    }
//
//    /**
//     * @return the minusButton
//     */
//    public Button getMinusButton() {
//        return minusButton;
//    }
//
//    /**
//     * @return the editor
//     */
//    public ChromosomEditor getEditor() {
//        return editor;
//    }
//
//    /**
//     * @param editor the editor to set
//     */
//    public void setEditor(ChromosomEditor editor) {
//        this.editor = editor;
//    }
//
//    /**
//     * @return the nameTF
//     */
//    public TextField getNameTF() {
//        return nameTF;
//    }
//
//    /**
//     * @param nameTF the nameTF to set
//     */
//    public void setNameTF(TextField nameTF) {
//        this.nameTF = nameTF;
//    }
//
//    /**
//     * @return the conzentration
//     */
//    public TextField getConzentration() {
//        return conzentration;
//    }
//
//    /**
//     * @param conzentration the conzentration to set
//     */
//    public void setConzentration(TextField conzentration) {
//        this.conzentration = conzentration;
//    }
//
//    /**
//     * @return the associationRate
//     */
//    public TextField getAssociationRate() {
//        return associationRate;
//    }
//
//    /**
//     * @param associationRate the associationRate to set
//     */
//    public void setAssociationRate(TextField associationRate) {
//        this.associationRate = associationRate;
//    }
//
//    /**
//     * @return the dissociationRate
//     */
//    public TextField getDissociationRate() {
//        return dissociationRate;
//    }
//
//    /**
//     * @param dissociationRate the dissociationRate to set
//     */
//    public void setDissociationRate(TextField dissociationRate) {
//        this.dissociationRate = dissociationRate;
//    }
//}
