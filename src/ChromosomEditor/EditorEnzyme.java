/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.LinkedHashMap;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakob
 */
public class EditorEnzyme extends BorderPane {

    final ChromosomEditor editor;
    private LinkedHashMap<String, EditorRule> ruleMap;
    private EditorRule lastRule;
    private TextField nameTF;
    private TextField concentrationTF;
    private TextField sizeTF;
    private VBox ruleBox;
    private Button minusButton;
    private String concentrationType = "absolute";
    private Button plus;
    double scrollValue = 0.0;

    public EditorEnzyme(ChromosomEditor editor) {
        this.editor = editor;
        
        ruleMap = new LinkedHashMap<>();
        
        minusButton = new Button("-");
        minusButton.getStyleClass().add("buttonRemove"); 
        nameTF = new TextField();
        concentrationTF = new TextField();
        concentrationTF.setMaxWidth(70);
        sizeTF = new TextField();
        sizeTF.setMaxWidth(70);
        
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(minusButton, new Label("  Enzyme Name: "), nameTF);
        nameBox.setAlignment(Pos.CENTER);

        HBox concentrationBox = new HBox();
        concentrationBox.getChildren().addAll(new Label("Size: "), sizeTF, new Label("  Concentration: "), concentrationTF);
        concentrationBox.setAlignment(Pos.CENTER);
        
        setNumericFilterTextfield(concentrationTF);
        setNumericFilterTextfield(sizeTF);
        
        ruleBox = new VBox();
        ruleBox.setSpacing(10);
        
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setTopAnchor(nameBox, 5.0);
        AnchorPane.setTopAnchor(concentrationBox, 5.0);
        AnchorPane.setLeftAnchor(nameBox, 5.0);
        AnchorPane.setRightAnchor(concentrationBox, 5.0);
        anchorPane.getChildren().addAll(nameBox, concentrationBox);
        
        ruleBox.setSpacing(15);
        ruleBox.setPadding(new Insets(15,10,5,10));
        ruleBox.setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: lightgray; -fx-border-color: black; -fx-border-width: 2px;");
        
        plus = new Button("+");
        plus.getStyleClass().add("buttonPlus");
        plus.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
                    EditorRule newRule = null;
                    if(lastRule != null) {
                        newRule = lastRule.cloneRule();
                        
                        //scroll down, damit die neue Regel nicht aus dem Bild rutscht
                        scrollValue = editor.getRuleDesigner().getScroll().getVvalue();
                        double height = editor.getRuleDesigner().getScroll().getContent().getBoundsInLocal().getHeight();
                        double ruleHeight = lastRule.getHeight() + 20;
                        double newHeight = ruleHeight / height;
                        editor.getRuleDesigner().getScroll().setVvalue(scrollValue + newHeight);
                        
                        //Leere Regeln abfangen
                        if(newRule.getRule().equals("()") && newRule.getTarget().equals("()")) {
                            newRule = new EditorRule(editor);
                        }
                        else {
                            newRule.getNameTF().setText(lastRule.getNameTF().getText() + "-copy"); 
                        }
//                        double ruleHeight = lastRule.getHeight();
//                        System.err.println(ruleHeight);
//                        
//                        double scrollHeight = editor.getRuleDesigner().getScroll().getVmax();
//                        System.err.println(scrollHeight);
//                        
//                        double newHeight = ruleHeight / scrollHeight;
//                        System.err.println(newHeight);
                        
//                        double scrollValue = editor.getRuleDesigner().getScroll().getVvalue();
//                        
//                        editor.getRuleDesigner().getScroll().setVvalue(scrollValue + newHeight);
                    }
                    else{
                        newRule = new EditorRule(editor);
                    }
                    
                    addRule(newRule);
                    
                }
                
                
        });
        
        ruleBox.getChildren().add(plus);
        
        setTop(anchorPane);
        setCenter(ruleBox);
        setMinWidth(600);
        

        
    }
    
    public static final void setNumericFilterTextfield(TextField textfield) {
        textfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                    Integer.parseInt(newValue);
                    if(textfield.getTooltip() != null)
                        textfield.getTooltip().hide();
            } catch(Exception e) {
                showTooltip((Stage)textfield.getScene().getWindow(), textfield, "Ownly numeric values are allowed.");
            }
        });
    }
    
    public static void showTooltip(Stage owner, Control control, String tooltipText)
    {
        
        boolean sameText = false;
        
        if(control.getTooltip() != null) {
            String oldText = control.getTooltip().getText();
            if(oldText.equals(tooltipText)) {
                sameText = true;
            }
        }
        
        if(sameText == true) {
            control.getTooltip().hide();
            control.setTooltip(null);
        }

        Point2D p = control.localToScene(control.getWidth(), 0.0);

        final Tooltip customTooltip = new Tooltip();
        customTooltip.setText(tooltipText);

        control.setTooltip(customTooltip);
        customTooltip.setAutoHide(true);

        customTooltip.show(owner, p.getX()
            + control.getScene().getX() + control.getScene().getWindow().getX(), p.getY()
            + control.getScene().getY() + control.getScene().getWindow().getY());
    }
    
    public void setShowMode() {
        if(ruleBox.getChildren().contains(plus)) {
            ruleBox.getChildren().remove(plus);
        }
        nameTF.setEditable(false);
        concentrationTF.setEditable(false);
        sizeTF.setEditable(false);
        minusButton.setVisible(false);
    }
    
    public Element createEnzymeElement(Document doc) {
            
            Element enzyme = editor.addXMLElement(doc.getDocumentElement(), "enzymeDef");
            
            Element name = editor.addXMLElement(enzyme, "name");
            name.setAttribute("enabled", "1");
            name.setAttribute("value", nameTF.getText());

            Element size = editor.addXMLElement(enzyme, "size");
            size.setAttribute("type", "symmetric");
            size.setAttribute("value", getSizeTF().getText());

            Element concentration = editor.addXMLElement(enzyme, "concentration");
            concentration.setAttribute("type", "absolute");
            concentration.setAttribute("value", String.valueOf(concentrationTF.getText()));

            return enzyme;
    }

    /**
     * @return the enzymeMap
     */
    public LinkedHashMap<String, EditorRule> getEnzymeMap() {
        return getRuleMap();
    }

    /**
     * @param enzymeMap the enzymeMap to set
     */
    public void setEnzymeMap(LinkedHashMap<String, EditorRule> enzymeMap) {
        this.setRuleMap(enzymeMap);
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

    public void addRule(EditorRule rule) {
        
        ruleBox.getChildren().remove(plus);
        ruleBox.getChildren().add(rule);
        rule.getMinusButton().setOnAction(new EventHandler<ActionEvent>() {     
            @Override public void handle(ActionEvent e) { 
                ruleBox.getChildren().remove(rule);
                getRuleMap().remove(rule.getNameTF().getText());
            } 
        });
        ruleBox.getChildren().add(plus);
        String ruleName = "";
        if(rule.getNameTF().getText().equals("")) {
            ruleName = "Rule "+ getRuleMap().size();
        }
        else {
            ruleName = rule.getNameTF().getText();
        }
        lastRule = rule;
        getRuleMap().put(ruleName, rule);
        rule.getFirstRow().checkStateColors();
        rule.getSecondRow().checkStateColors();
        
    }
  
    /**
     * Check all input fields and show tooltips
     * @return Returns true, if all fields are filled correctly  and false, if at least one fails.
     */
    public boolean checkFields() {
        boolean allRight = true;
        
        try{
            Integer.parseInt(sizeTF.getText());
        }
        catch(Exception e) {
            showTooltip((Stage)sizeTF.getScene().getWindow(), sizeTF, "Ownly numeric values are allowed.");
            allRight = false;
        }
        
        try{
            Integer.parseInt(concentrationTF.getText());
        }
        catch(Exception e) {
            showTooltip((Stage)concentrationTF.getScene().getWindow(), concentrationTF, "Ownly numeric values are allowed.");
            allRight = false;
        }
        
        if(nameTF.getText().isEmpty() || nameTF.getText().equals("")) {
            showTooltip((Stage)nameTF.getScene().getWindow(), nameTF, "The enzyme needs a name.");
            allRight = false;
        }
        
        return allRight;
    }        

    

         
    public EditorRule getRule(String ruleName) {
        return getRuleMap().get(ruleName);
    }

    /**
     * @return the concentrationTF
     */
    public TextField getConcentrationTF() {
        return concentrationTF;
    }

    /**
     * @param concentrationTF the concentrationTF to set
     */
    public void setConcentrationTF(TextField concentrationTF) {
        this.concentrationTF = concentrationTF;
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
     * @return the ruleBox
     */
    public VBox getRuleBox() {
        return ruleBox;
    }

    /**
     * @param ruleBox the ruleBox to set
     */
    public void setRuleBox(VBox ruleBox) {
        this.ruleBox = ruleBox;
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
     * @return the ruleMap
     */
    public LinkedHashMap<String, EditorRule> getRuleMap() {
        return ruleMap;
    }

    /**
     * @param ruleMap the ruleMap to set
     */
    public void setRuleMap(LinkedHashMap<String, EditorRule> ruleMap) {
        this.ruleMap = ruleMap;
    }

    /**
     * @return the sizeTF
     */
    public TextField getSizeTF() {
        return sizeTF;
    }

    /**
     * @param sizeTF the sizeTF to set
     */
    public void setSizeTF(TextField sizeTF) {
        this.sizeTF = sizeTF;
    }
    
    public EditorEnzyme cloneEnzyme() {
        EditorEnzyme returnEnzyme = new EditorEnzyme(editor);
        returnEnzyme.getNameTF().setText(nameTF.getText());
        returnEnzyme.getConcentrationTF().setText(concentrationTF.getText());
        returnEnzyme.getSizeTF().setText(sizeTF.getText());
        
        for(EditorRule rul : ruleMap.values()) {
            EditorRule newRul = rul.cloneRule();
            newRul.setEnzymeName(nameTF.getText());
            returnEnzyme.addRule(newRul);
        }
        
        return returnEnzyme;
    }

 }
