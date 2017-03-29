/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakob
 */
public class EditorRuleDesigner extends EditorPane{
    
    ChromosomEditor editor;
    private ScrollPane scroll;
    VBox vbox;
    Button plus;
    LinkedHashMap<String, EditorEnzyme> enzymeMap;
    
    
    public EditorRuleDesigner(ChromosomEditor editor) {
        this.paneName = "rf";
        this.paneFullName = "Ruleset";
        this.editor = editor;
        
        AnchorPane topPane = new AnchorPane();
        Label top = new Label(paneFullName);
        top.setFont(new Font(top.getFont().getName(), 16));
        topPane.getChildren().add(top);
        AnchorPane.setTopAnchor(top, 7.0);
        AnchorPane.setBottomAnchor(top, 10.0);
        AnchorPane.setLeftAnchor(top, 10.0);
        setTop(topPane);
        
        scroll = new ScrollPane();
        
        vbox = new VBox();
        vbox.setSpacing(30);
        vbox.setPadding(new Insets(20,20,20,20));
        vbox.setAlignment(Pos.CENTER);
        
        plus = new Button("+");
        plus.getStyleClass().add("buttonPlus");
        plus.setOnAction(new EventHandler<ActionEvent>() {     
                @Override public void handle(ActionEvent e) { 
                    EditorEnzyme enzyme = new EditorEnzyme(editor);
                    enzyme.setCache(true);
                    enzyme.setCacheShape(true);
                    enzyme.setCacheHint(CacheHint.SPEED);
                    addEnzyme(enzyme);
                }
        });
        

//        EditorEnzyme enzyme = new EditorEnzyme(editor);     
//        enzyme.getMinusButton().setOnAction(new EventHandler<ActionEvent>() {     
//                @Override public void handle(ActionEvent e) { 
//                    vbox.getChildren().remove(enzyme);
//                } 
//            });
//        
//        vbox.getChildren().add(0, enzyme);
  
        vbox.getChildren().add(0,plus);
        
        scroll.setContent(vbox);
        
        this.setCenter(scroll);
        
        if(editor.getChromosom().getEnzymeMap() != null) {
            for(EditorEnzyme enzyme : editor.getChromosom().getEnzymeMap().values()) {
                addEnzyme(enzyme);
                for(EditorRule rul : enzyme.getRuleMap().values()) {
                    rul.getSecondRow().checkStateColors();
                }
            }
        }
        else {
            EditorEnzyme enzyme = new EditorEnzyme(editor);
            addEnzyme(enzyme);
            enzyme.addRule(new EditorRule(editor));
        }
    } 
                
    public void addEnzyme(EditorEnzyme enzyme) {
        vbox.getChildren().remove(plus);
        enzyme.getMinusButton().setOnAction(new EventHandler<ActionEvent>() {     
            @Override public void handle(ActionEvent e) { 
                vbox.getChildren().remove(enzyme);
            } 
        });
        vbox.getChildren().add(enzyme);
        vbox.getChildren().add(plus);
    }
        
    @Override
    public boolean next() {
        if(createEnzymeMap() == false) {
            return false;
        }
        
        try {
            File tempFile = File.createTempFile("tmp-rulefile", ".cfg");
            tempFile.deleteOnExit();
            return writeToFile(tempFile);
        } catch (IOException ex) {
            Logger.getLogger(EditorRuleDesigner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean save() {
        
        if(createEnzymeMap() == false) {
            return false;
        }
        
        File saveFile = null;
        
        if(saveFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Configuration File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CONFIGURATION files (*.cfg)", "*.cfg");
            fileChooser.getExtensionFilters().add(extFilter);
            saveFile = fileChooser.showSaveDialog(new Stage());
            if (saveFile != null && !saveFile.getAbsolutePath().endsWith(".cfg")) {
                saveFile = new File(saveFile.getAbsolutePath() + ".cfg");
            }
        }
        if(saveFile != null) {
            return writeToFile(saveFile);
        }
        return false;
    }
    
    @Override
    public void reset() {
        editor.setConfigFile(null);
        editor.getChromosom().setEnzymeMap(null);
        editor.getChromosom().setEditorRuleList(null);
    }
    
    /**
     * Geht alle enthaltenen Enzmye durch und sortiert Sie in eine gemeinsame HashMap ein.
     */
    public boolean createEnzymeMap() {
        
        enzymeMap = new LinkedHashMap<>();
        
        for(Node node : vbox.getChildren()) {
            if(node instanceof EditorEnzyme) {
                EditorEnzyme enzyme = (EditorEnzyme)node;
                
                if(enzyme.checkFields() == false) {
                    return false;
                }
                
//                if(enzymeMap.containsKey(enzyme.getNameTF().getText())) {
//                    
//                    //Alle bisherigen Regeln zum Enzyme hinzufügen
//                    for(Entry<String, EditorRule> entry : enzymeMap.get(enzyme.getNameTF().getText()).getRuleMap().entrySet()) {
//                        EditorRule value = entry.getValue();
//                        
//                        if(value.checkFields() == false) {
//                            return false;
//                        }
//                        
//                        enzyme.getRuleMap().put(entry.getKey(), value);
//                    }
//                    enzymeMap.remove(enzyme.getNameTF().getText());
//                }
                
                enzymeMap.put(enzyme.getNameTF().getText(), enzyme);
                
                for(Entry<String, EditorRule> entry : enzyme.getRuleMap().entrySet()) { // for every rule of the enzyme ...
                    EditorRule value = entry.getValue();

                    if(value.checkFields() == false) { // ... check all input fields
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    
    public Element createHashKeyElement() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String dateString = dateFormat.format(date);
        
        String hash = dateString + Integer.toString(dateString.hashCode());
        
        Element hashElement = editor.addXMLElement(editor.getSimulationElement(), "configurationHashKey");
        hashElement.setAttribute("value", hash);
        return hashElement;
    }
    
    public Element createInitialStateElement(Document doc) {
        String text;
        if((text = editor.getEditorInitialState().getInitialStateText()) != null) {
            editor.getChromosomProject().setInitialState(text);
            
            Element initialStateElement = editor.addXMLElement(doc.getDocumentElement(), "initialState");

//            {H3[K4.me3]H3[K27.me3]}{H3[K4.me3]H3[K27.me3]}
//            
//            H3[K4.me3]H3[K27.me3]
//            H3[K4.me3]H3[K27.me3]
//            H3[K4.me3]H3[K27.me3]
//            H3[K4.me3]H3[K27.me3]
                    
            text = "{" + text;
            text = text.replaceAll("\n", "}{");
            text += "}";
            
//            String textArray[] = text.split("\n");
//            for(int i = 0; i < textArray.length; i++) {
//                String str = textArray[i];
//            }
            
            initialStateElement.setAttribute("value", text);
            initialStateElement.setAttribute("type", "custom");
            
            return initialStateElement;
        }
        else {
            return null;
        }
        
//        <simulationTime type="fixedTime" value="30"/>
//        <cyclicChromosomes value="0"/>
    }
    
    /**
     * Erzeugt eine XML-Struktur von allen Enzymen mit ihren Regeln und schreibt sie in eine Konfigurationsdatei
     */
    @Override
    public boolean writeToFile(File outfile) {
        
        try {
            Document doc = ChromosomEditor.createNewXMLDocument("enzymeSet");
                   
            for(Entry<String, EditorEnzyme> entry : enzymeMap.entrySet()) {
                
                Element enzymeElement = entry.getValue().createEnzymeElement(doc);
                    for(EditorRule rule : entry.getValue().getRuleMap().values()) {
                        Element ruleElement = rule.createRuleElement(enzymeElement);
                }
            }
            
//            createCiclingElement(doc);
//            createTimeElement(doc);
//            createInitialStateElement(doc);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            //Format, Zeilenumbrüche, Einzüge festlegen
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outfile);
            
            transformer.transform(source, result);
            
            System.out.println("File saved!");
            editor.getChromosomProject().setRulesetFile(outfile);
            return true;
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(EditorRuleDesigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(EditorRuleDesigner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * @return the scroll
     */
    public ScrollPane getScroll() {
        return scroll;
    }

    /**
     * @param scroll the scroll to set
     */
    public void setScroll(ScrollPane scroll) {
        this.scroll = scroll;
    }
    
    public void checkStateColors() {
        System.err.println("CHECK");
        if(editor.getChromosom().getEnzymeMap() != null) {
            for(EditorEnzyme enzyme : editor.getChromosom().getEnzymeMap().values()) {
                for(EditorRule rul : enzyme.getRuleMap().values()) {
                    rul.getFirstRow().checkStateColors();
                    rul.getSecondRow().checkStateColors();
                }
            }
        }
    }
}
