/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import static ChromosomEditor.EditorEnzyme.showTooltip;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Element;

/**
 *
 * @author Jakob
 */
public class EditorRule extends BorderPane {
    
    private String sizeType = "symmetric";
    private String concentrationType = "absolute";
    private String type = "explicitRate";
    private String dissociationRateType = "";
//    private String name = "";
//    private String rate = "";
//    private String dissociationRate = "";
    private ChromosomEditor editor;
    
    private EditorNukleosomRow firstRow;
    private EditorNukleosomRow secondRow;
    private String rule = "";
    private String target = "";
    private Button minusButton;
    private TextField nameTF;
    private TextField associationRateTF;
    private TextField dissociationRateTF;
    private ArrayList<EditorTagField> tagList;
    private CheckBox selCheck;
    private String[] targetStrings = new String[3];
    private String[] ruleStrings = new String[3];
    HBox hbox;
    StackPane stackPane;
    String enzymeName = "";
    Button plus;
    VBox tagBox;
    HBox innerBox;
    
    public EditorRule(ChromosomEditor editor) {
        this.editor = editor;

        tagList = new ArrayList<>();
        
        Image image = new Image("Pfeil.png" ,50, 50, false, false);
        
        stackPane = new StackPane();
        stackPane.setPrefSize(60, 60);
        stackPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        
        minusButton = new Button("-");
        minusButton.getStyleClass().add("buttonRemove");
        GridPane minusPane = new GridPane();
        minusPane.setAlignment(Pos.CENTER);
        minusPane.getChildren().add(minusButton);

        if(!editor.getChromosomProject().getHistoneMap().isEmpty()) {
            firstRow = new EditorNukleosomRow(this, true);
            firstRow.setCache(true);
            firstRow.setCacheShape(true);
            firstRow.setCacheHint(CacheHint.SPEED);
            
            secondRow = new EditorNukleosomRow(this, false);
            secondRow.setCache(true);
            secondRow.setCacheShape(true);
            secondRow.setCacheHint(CacheHint.SPEED);
        }
        
        hbox = new HBox();

        hbox.getChildren().addAll(firstRow, stackPane, secondRow);
        hbox.setPadding(new Insets(5,5,5,5));
        hbox.setSpacing(8);
        
        BorderPane innerBorder = new BorderPane();
        innerBorder.setCenter(hbox);
       
        nameTF = new TextField();
        nameTF.setPrefWidth(150);
                
        associationRateTF = new TextField();
        associationRateTF.setPrefWidth(70);
        
        EditorEnzyme.setNumericFilterTextfield(associationRateTF);
        
        dissociationRateTF = new TextField();
        dissociationRateTF.setPrefWidth(70);     
        
        EditorEnzyme.setNumericFilterTextfield(dissociationRateTF);
    
        selCheck = new CheckBox();
        selCheck.setSelected(true);
        
        GridPane nameEnableGrid = new GridPane();
        nameEnableGrid.add(new Label("Name:"), 0, 0);
        nameEnableGrid.add(new Label("Enabled:"), 0, 1);
        nameEnableGrid.add(nameTF, 1,0);
        nameEnableGrid.add(selCheck, 1,1);
        nameEnableGrid.setHgap(5);
        nameEnableGrid.setVgap(5);
        nameEnableGrid.setAlignment(Pos.CENTER);
                
        innerBox = new HBox();
        innerBox.getChildren().addAll(nameEnableGrid);
        innerBox.setSpacing(5);
        innerBox.setAlignment(Pos.CENTER_LEFT);
        
        GridPane rateGrid = new GridPane();
        rateGrid.setAlignment(Pos.CENTER);
        rateGrid.setHgap(5);
        rateGrid.setVgap(5);
        
        rateGrid.add(new Label("Associationrate:"), 0, 0);
        rateGrid.add(associationRateTF, 1, 0);
        rateGrid.add(new Label("Dissociationrate:"), 0, 1);
        rateGrid.add(dissociationRateTF, 1, 1);
        rateGrid.add(new Label(" Tags:"), 2, 0);
        
        tagBox = new VBox();
        tagBox.setSpacing(5);
        
        plus = new Button("+");
        plus.setOnAction(e -> {
           addTagField("","");
        });
        
        tagBox.setAlignment(Pos.CENTER);
        tagBox.getChildren().add(plus);
        
        HBox fieldBox = new HBox();
        fieldBox.setSpacing(20);
        
        AnchorPane anPane = new AnchorPane();
        AnchorPane.setTopAnchor(rateGrid, 5.0);
        anPane.getChildren().add(rateGrid);
        
        AnchorPane anPane2 = new AnchorPane();
        AnchorPane.setTopAnchor(tagBox, 5.0);
        anPane2.getChildren().add(tagBox);
        
        fieldBox.getChildren().addAll(anPane, anPane2);
        
        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(innerBox, 5.0);
        AnchorPane.setTopAnchor(fieldBox, 5.0);
        AnchorPane.setTopAnchor(innerBox, 5.0);
        AnchorPane.setTopAnchor(fieldBox, 5.0);
        AnchorPane.setBottomAnchor(innerBox, 5.0);
        AnchorPane.setBottomAnchor(fieldBox, 5.0);
        AnchorPane.setLeftAnchor(innerBox, 0.0);
        AnchorPane.setRightAnchor(fieldBox, 5.0);
        anchor.getChildren().addAll(innerBox, fieldBox);
        
        setCenter(innerBorder);
        innerBorder.setTop(anchor);
        setLeft(minusPane);
        setMinWidth(700);
        setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        
        this.setCache(true);
        this.setCacheShape(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    public EditorRule getNewFromOld() {
        EditorRule newRule = this.cloneRule();
        newRule.getNameTF().setText((newRule.getNameTF().getText() + "-1"));
        
        return newRule;
    }

    public void addTagField(String tagName, String tagValue) {
        tagBox.getChildren().remove(plus);
        EditorTagField tagField = new EditorTagField(tagName);
        tagField.getValueTF().setText(tagValue);
        tagField.getMinus().setOnAction(minus -> {
            tagBox.getChildren().remove(tagField);
        });
        tagList.add(tagField);
        tagBox.getChildren().add(tagField);
        tagBox.getChildren().add(plus);
    }
    
    public String getTagValue(String tagName) {
        String returnStr = "";
        for(EditorTagField tagField : tagList) {
            if(tagField.getTagName().getText().equals(tagName)) {
                returnStr = tagField.getValueTF().getText();
            }
        }
        return returnStr;
    }
    
    /**
     * Check all input fields and show tooltips
     * @return Returns true, if all fields are filled correctly  and false, if at least one fails.
     */
    public boolean checkFields() {
        boolean allRight = true;
        
        try{
            Integer.parseInt(associationRateTF.getText());
        }
        catch(Exception e) {
            showTooltip((Stage)associationRateTF.getScene().getWindow(), associationRateTF, "Ownly numeric values are allowed.");
            allRight = false;
        }
        
        try{
            Integer.parseInt(dissociationRateTF.getText());
        }
        catch(Exception e) {
            showTooltip((Stage)dissociationRateTF.getScene().getWindow(), dissociationRateTF, "Ownly numeric values are allowed.");
            allRight = false;
        }
                
        if(nameTF.getText().isEmpty() || nameTF.getText().equals("")) {
            showTooltip((Stage)nameTF.getScene().getWindow(), nameTF, "The rule needs a name.");
            allRight = false;
        }
        
        return allRight;
    }  
    
    /**
     * @return the editor
     */
    public ChromosomEditor getEditor() {
        return editor;
    }

    /**
     * @param editor the editor to set
     */
    public void setEditor(ChromosomEditor editor) {
        this.editor = editor;
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
     * @return the minusButton
     */
    public Button getMinusButton() {
        return minusButton;
    }

    /**
     * @param minusButton the minusButton to set
     */
    public void setMinusButton(Button minusButton) {
        this.minusButton = minusButton;
    }

    /**
     * @return the nameTF
     */
    public TextField getNameTF() {
        return nameTF;
    }

    /**
     * @param nameTF the nameTF to set
     */
    public void setNameTF(TextField nameTF) {
        this.nameTF = nameTF;
    }

    /**
     * @return the associationRate
     */
    public TextField getAssociationRate() {
        return getAssociationRateTF();
    }

    /**
     * @param associationRate the associationRate to set
     */
    public void setAssociationRate(TextField associationRate) {
        this.setAssociationRateTF(associationRate);
    }

    /**
     * @return the dissociationRate
     */
    public TextField getDissociationRate() {
        return getDissociationRateTF();
    }

    /**
     * @param dissociationRate the dissociationRate to set
     */
    public void setDissociationRate(TextField dissociationRate) {
        this.setDissociationRateTF(dissociationRate);
    }

    /**
     * @return the tagList
     */
    public ArrayList<EditorTagField> getTagList() {
        return tagList;
    }

    /**
     * @param tagList the tagList to set
     */
    public void setTagList(ArrayList<EditorTagField> tagList) {
        this.tagList = tagList;
    }
    
    public Element createRuleElement(Element enzymeElement) {
        Element ruleDef = editor.addXMLElement(enzymeElement, "bindingRatesRuleSet");
        
        Element ruleName = editor.addXMLElement(ruleDef, "name");
        ruleName.setAttribute("value", nameTF.getText());    

        Element type = editor.addXMLElement(ruleDef, "type");
        type.setAttribute("value", getType());  

        Element enabled = editor.addXMLElement(ruleDef, "enabled");
        
        String enabledStr = "0";
        if(selCheck.isSelected() == true) {
            enabledStr = "1";
        }
        
        enabled.setAttribute("value", enabledStr); 

        Element target = editor.addXMLElement(ruleDef, "target");
        target.setAttribute("value", EditorNukleosomRow.getRuleNukleosomString(firstRow));

        Element ruleElement = editor.addXMLElement(ruleDef, "rule");
        ruleElement.setAttribute("value", EditorNukleosomRow.getRuleNukleosomString(secondRow));

        Element rate = editor.addXMLElement(ruleDef, "rate");
        rate.setAttribute("value", getAssociationRateTF().getText());

        Element dissociationRate = editor.addXMLElement(ruleDef, "dissociationRate");
        dissociationRate.setAttribute("type", getConcentrationType());
        dissociationRate.setAttribute("value", getDissociationRateTF().getText());

        for(EditorTagField tagField : tagList) {
            Element tag = editor.addXMLElement(ruleDef, "tag");
            tag.setAttribute("name", tagField.getTagName().getText());
            tag.setAttribute("value", tagField.getValueTF().getText());  
        }
        
        return ruleDef;
        
    }

    /**
     * @return the selCheck
     */
    public CheckBox getSelCheck() {
        return selCheck;
    }

    /**
     * @param selCheck the selCheck to set
     */
    public void setSelCheck(CheckBox selCheck) {
        this.selCheck = selCheck;
    }

    /**
     * @return the dissociationRateType
     */
    public String getDissociationRateType() {
        return dissociationRateType;
    }

    /**
     * @param dissociationRateType the dissociationRateType to set
     */
    public void setDissociationRateType(String dissociationRateType) {
        this.dissociationRateType = dissociationRateType;
    }

    /**
     * @return the concentrationType
     */
    public String getConcentrationType() {
        return concentrationType;
    }

    /**
     * @param concentrationType the concentrationType to set
     */
    public void setConcentrationType(String concentrationType) {
        this.concentrationType = concentrationType;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        hbox.getChildren().removeAll(hbox.getChildren());
        this.firstRow = parseAndSetRuleString(target, true);
        if(firstRow == null) {
            System.err.println("Can't read target string of rule " + this.getNameTF().getText() + " : " + target);
            return;
        }
        hbox.getChildren().addAll(firstRow, stackPane, secondRow);
        this.target = target;
    }

    /**
     * @return the rule
     */
    public String getRule() {
        return rule;
    }

    /**
     * @param rule the rule to set
     */
    public void setRule(String rule) {
        hbox.getChildren().removeAll(hbox.getChildren());
        this.secondRow = parseAndSetRuleString(rule, false);
        if(secondRow == null) {
            System.err.println("Can't read rule string of rule " + this.getNameTF().getText() + " : " + rule);
            return;
        }        
        hbox.getChildren().addAll(firstRow, stackPane, secondRow);
        this.rule = rule;
    }

    /**
     * @return the associationRateTF
     */
    public TextField getAssociationRateTF() {
        return associationRateTF;
    }

    /**
     * @param associationRateTF the associationRateTF to set
     */
    public void setAssociationRateTF(TextField associationRateTF) {
        this.associationRateTF = associationRateTF;
    }

    /**
     * @return the dissociationRateTF
     */
    public TextField getDissociationRateTF() {
        return dissociationRateTF;
    }

    /**
     * @param dissociationRateTF the dissociationRateTF to set
     */
    public void setDissociationRateTF(TextField dissociationRateTF) {
        this.dissociationRateTF = dissociationRateTF;
    }

    private EditorNukleosomRow parseAndSetRuleString(String inputString, boolean target) {
        
        String stringArray[] = new String[3];
        
        
        if(target) {
            stringArray = targetStrings;
        }
        else {
            stringArray = getRuleStrings();
        }
        
        if(inputString.contains("(") && inputString.contains(")")) {
            
            stringArray[0] = inputString.substring(0, inputString.indexOf("("));
            stringArray[1] = inputString.substring(inputString.indexOf("(")+1, inputString.lastIndexOf(")"));
            stringArray[2] = inputString.substring(inputString.lastIndexOf(")")+1);
        }
        else {
            return null;
        }
        
        for(int i = 0; i < stringArray.length; i++) {
            stringArray[i] = stringArray[i].replaceAll("\\{", "");
            stringArray[i] = stringArray[i].replaceAll("\\}", "");
        }

        EditorNukleosomRow row = new EditorNukleosomRow(this, target);
        
        return row;
    }

    public String[] getTargetStrings() {
        return targetStrings;
    }

    /**
     * @param targetStrings the targetStrings to set
     */
    public void setTargetStrings(String[] targetStrings) {
        this.targetStrings = targetStrings;
    }

    /**
     * @return the ruleStrings
     */
    public String[] getRuleStrings() {
        return ruleStrings;
    }

    /**
     * @param ruleStrings the ruleStrings to set
     */
    public void setRuleStrings(String[] ruleStrings) {
        this.ruleStrings = ruleStrings;
    }

    public void setEnzymeName(String enzymeName) {
        this.enzymeName = enzymeName;
    }
    
    public String getEnzymeName() {
        return enzymeName;
    }

    public void setEditable(boolean b) {
        nameTF.setEditable(b);
        associationRateTF.setEditable(b);
        dissociationRateTF.setEditable(b);
        
        for(EditorTagField field : tagList) {
            field.getValueTF().setEditable(b);
            field.getTagName().setEditable(b);
        }
        firstRow.setEditable(b);
        secondRow.setEditable(b);
    }
    
    public void setShowMode() {
        
        nameTF.setEditable(false);
        associationRateTF.setEditable(false);
        dissociationRateTF.setEditable(false);
        
        
        for(EditorTagField field : tagList) {
            field.getValueTF().setEditable(false);
            field.getTagName().setEditable(false);
            field.getChildren().remove(field.getMinus());
        }
        firstRow.setEditable(false);
        secondRow.setEditable(false);
        
        tagBox.getChildren().remove(plus);
        selCheck.setDisable(true);
//        innerBox.getChildren().remove(selCheck);
        this.setLeft(new Pane());
    }
    
    public EditorRule cloneRule() {
        EditorRule returnRule = new EditorRule(editor);
        returnRule.getNameTF().setText(nameTF.getText());
        returnRule.getAssociationRateTF().setText(this.associationRateTF.getText());
        returnRule.getDissociationRateTF().setText(this.getDissociationRateTF().getText());
        returnRule.setType(type);
        returnRule.setDissociationRateType(dissociationRateType);
        if(target.equals("")) {
            target = EditorNukleosomRow.getRuleNukleosomString(firstRow);
        }
        returnRule.setTarget(target);
        
        if(rule.equals("")) {
            rule = EditorNukleosomRow.getRuleNukleosomString(secondRow);
        }
        returnRule.setRule(rule);
        
        for(EditorTagField tag : tagList) {
            returnRule.addTagField(tag.getTagName().getText(), tag.getValueTF().getText());
        }
        
        return returnRule;
    }
    
}
