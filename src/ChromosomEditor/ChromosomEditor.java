/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import application.Chromosom;
import application.ChromosomMenu;
import application.ChromosomProject;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakob
 */
public class ChromosomEditor extends GridPane{
    
    private Chromosom chromosom;
    private ChromosomProject chromosomProject;
    private EditorPane[] paneArray;
    private HistoneSetter histoneSetter;
    private SiteSetter siteSetter;
    private EditorOptions options;
    private String[] histoneArray;
    private int paneIndex = 0;
    private EditorBottomPane bottom;
    private EditorRuleDesigner ruleDesigner;
//    public static Document XMLdoc;
    private Element rootElement;
    private Element simulationElement;
    private EditorInitialState editorInitialState;
    private EditorSimulation editorSimulation;
    private File configFile;
    private File histSimFile;
    
//    private String oldHistoneArray[];
    
    public ChromosomEditor(Chromosom chromosom, ChromosomProject project) 
    {
            this.chromosom = chromosom;
            this.chromosomProject = project;
            this.paneArray = new EditorPane[4];
            
            this.setMinSize(500,400);
            this.setAlignment(Pos.CENTER);
            
            this.bottom = new EditorBottomPane(this);
            
            setStyle("-fx-border-width: 1px; -fx-border-color: lightgray;");
//            createNewXMLDocument();
            
    }
    
//    public void createNewXMLDocument() {
//        try {
//            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//
//            // root elements
//            XMLdoc = docBuilder.newDocument();
//            Element simulation = XMLdoc.createElement("simulation");
//            setSimulationElement(simulation);
//            XMLdoc.appendChild(simulation);
//            Element enzymeSet = XMLdoc.createElement("enzymeSet");
//            setRootElement(enzymeSet);
//            XMLdoc.getFirstChild().appendChild(enzymeSet);
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(ChromosomEditor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static Document createNewXMLDocument(String rootname) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement(rootname);
            doc.appendChild(root);
            return doc;
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ChromosomEditor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void showEditorPane(int index, int add) {
        
        index = index + add;
        paneIndex = index;
        
        switch(index) {
            
            case 0:     // init the site setter screen
                if(paneArray[index] == null) {
                    siteSetter = new SiteSetter(this);
                    paneArray[index] = siteSetter;
                }
                getChromosom().getRootLayout().setRight(options);
                break;
                
            case  1:    // next after siteSetter -> ruleDesigner
                if(paneArray[index] == null || siteSetter.isSameHistoneMap() == false) {
                    setRuleDesigner(new EditorRuleDesigner(this));
                    paneArray[index] = getRuleDesigner();
                }
                break;
                
            case 2:     // Startstring
                System.err.println(siteSetter.isSameHistoneMap());
                if(paneArray[index] == null || siteSetter.isSameHistoneMap() == false) {
                    setEditorInitialState(new EditorInitialState(this, false));
                    paneArray[index] = getEditorInitialState();
                } 
                break;
                
            case 3:    // Simulation Settings
                this.setEditorSimulation(new EditorSimulation(this, true));
                paneArray[index] = getEditorSimulation();
                
                if(add == 0) {
                    bottom.getBack().setVisible(false);
                }
                
                break;
               
        }
        
        bottom.getSave().setText("Save " + paneArray[paneIndex].paneFullName);
        bottom.getSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) { 
                if(paneArray[paneIndex].save())     //Check saving dialog
                    showEditorPane(paneIndex, 1);
            }
        });
        
        bottom.getBack().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                paneArray[paneIndex].back();
                if(paneIndex > 0) {
                    showEditorPane(paneIndex, -1);
                }
            }
        });

        if(paneIndex < 3) {
            bottom.getNext().setText("Next >>");
        }
        else {
            if(add > 0) {
                bottom.getSaveAll().setVisible(true);
                bottom.getSaveAll().setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) { 
                        File saveFile = saveAll();
                        if(saveFile != null) {
                            String fileName = saveFile.getName();
                            for(int i = 0; i <= paneIndex; i++ ) {
                                EditorPane pane = paneArray[i];
                                String newFile = saveFile + File.separator + fileName + "-" + pane.toString() + ".cfg";
                                File file = new File(newFile);
                                pane.writeToFile(file);
                            }
                        }
                    }
                });
            }
            
            bottom.getNext().setText("Start");
        }
        
        bottom.getNext().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) { 
                if(paneArray[paneIndex].next() == true && (paneIndex + 1) < paneArray.length)
                    showEditorPane(paneIndex, 1);
            }
        });
        
        
        if(index!= 0) {
            getChromosom().getRootLayout().getChildren().remove(options);
        }
        
        if(index < 4) {
            this.getChildren().removeAll(this.getChildren());
            this.getChildren().add(paneArray[index]);
            ChromosomMenu.packInCenter(chromosom);
        }
       
    }
    
    public void reset() {
        for(EditorPane pane : paneArray) {
            if(pane != null) {
                pane.reset();
            }
        }
    }

    public File saveAll() {
        File file = null;
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select or create a directory for your project.");
        file = directoryChooser.showDialog(new Stage());

        if(file == null){
            System.err.println("No Directory selected");
        }
        
        return file;
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


//    /**
//     * @return the XMLdoc
//     */
//    public Document getXMLdoc() {
//        return XMLdoc;
//    }
//
//    /**
//     * @param XMLdoc the XMLdoc to set
//     */
//    public void setXMLdoc(Document XMLdoc) {
//        this.XMLdoc = XMLdoc;
//    }

    /**
     * @return the rootElement
     */
    public Element getRootElement() {
        return rootElement;
    }

    /**
     * @param rootElement the rootElement to set
     */
    public void setRootElement(Element rootElement) {
        this.rootElement = rootElement;
    }
    
    public Element addXMLElement(Element root, String elementName) {
        Element element = root.getOwnerDocument().createElement(elementName);
        if(root == null) {
            System.err.println("XML-ROOT is null.");
            return null;
        }
        else {
            root.appendChild(element);
        }
        return element;
    }
    
    public Attr addXMLAttribute(Element root, String attrName, String attrValue) {
        Attr attr = root.getOwnerDocument().createAttribute(attrName);
        attr.setValue(attrValue);
        root.setAttributeNode(attr);
        return attr;
    }

    /**
     * @return the chromosom
     */
    public Chromosom getChromosom() {
        return chromosom;
    }

    /**
     * @param chromosom the chromosom to set
     */
    public void setChromosom(Chromosom chromosom) {
        this.chromosom = chromosom;
    }

    /**
     * @return the project
     */
    public ChromosomProject getChromosomProject() {
        return chromosomProject;
    }

    /**
     * @param project the project to set
     */
    public void setChromosomProject(ChromosomProject project) {
        this.chromosomProject = project;
    }

    /**
     * @return the ruleDesigner
     */
    public EditorRuleDesigner getRuleDesigner() {
        return ruleDesigner;
    }

    /**
     * @param ruleDesigner the ruleDesigner to set
     */
    public void setRuleDesigner(EditorRuleDesigner ruleDesigner) {
        this.ruleDesigner = ruleDesigner;
    }

    /**
     * @return the simulationElement
     */
    public Element getSimulationElement() {
        return simulationElement;
    }

    /**
     * @param simulationElement the simulationElement to set
     */
    public void setSimulationElement(Element simulationElement) {
        this.simulationElement = simulationElement;
    }

    /**
     * @return the editorInitialState
     */
    public EditorInitialState getEditorInitialState() {
        return editorInitialState;
    }

    /**
     * @param editorInitialState the editorInitialState to set
     */
    public void setEditorInitialState(EditorInitialState editorInitialState) {
        this.editorInitialState = editorInitialState;
    }

    /**
     * @return the editorSimulation
     */
    public EditorSimulation getEditorSimulation() {
        return editorSimulation;
    }

    /**
     * @param editorSimulation the editorSimulation to set
     */
    public void setEditorSimulation(EditorSimulation editorSimulation) {
        this.editorSimulation = editorSimulation;
    }

    /**
     * @return the configFile
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * @param configFile the configFile to set
     */
    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

}
