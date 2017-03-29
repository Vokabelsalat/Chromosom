/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import ChromosomEditor.ChromosomEditor;
import ChromosomEditor.EditorBottomPane;
import ChromosomEditor.EditorEnzyme;
import ChromosomEditor.EditorOptions;
import ChromosomEditor.EditorRule;
import ChromosomEditor.ModificationTable;
import HeatChromosom.HeatProject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jakob
 */
public class ChromosomMenu extends VBox{
    
    Chromosom chromosom;
    private ChromosomEditor chromosomEditor;
    File chromosomFile;
    private ChromosomProject chromosomProject;
    HeatProject heatProject;
    private String missingFileName = "";
    
    public ChromosomMenu(Chromosom chromosom) {
        chromosomProject = null;
        this.chromosom = chromosom;
        
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        
        Button loadSimulation = new Button("Load Simulation");
        Button loadRuleset = new Button("Load Existing Ruleset");
        Button createNewSimulation = new Button("Create New Simulation");
        Button runSimulation = new Button("Run New Simulation");
        
        loadSimulation.setPrefWidth(200);
        loadRuleset.setPrefWidth(200);
        createNewSimulation.setPrefWidth(200);
        runSimulation.setPrefWidth(200);
        
        chromosomProject = new ChromosomProject(chromosom);
        chromosom.setProject(chromosomProject);
        heatProject = new HeatProject(chromosom, 0);
        
        loadSimulation.setOnAction(e -> {
            
            boolean loaded = false;
            
//            FileChooser configChooser = new FileChooser();
//            configChooser.setTitle("Select Config File");
//            
//            FileChooser.ExtensionFilter extFilter = 
//                        new FileChooser.ExtensionFilter("CONFIG files (*.cfg)", "*.cfg");
//                configChooser.getExtensionFilters().add(extFilter);
//                    
//            File selectedConfig = configChooser.showOpenDialog(chromosom.getPrimaryStage());
//            
//            if(selectedConfig != null) {
//                
//                String configHashKey = readHistoneMapFile(selectedConfig);

                File selectedDirectory;

                do{
                    DirectoryChooser chooser = new DirectoryChooser();
                    chooser.setTitle("Choose Project Folder");

                    selectedDirectory = chooser.showDialog(chromosom.getPrimaryStage());
                    
                    if(selectedDirectory != null) {

                        chromosomProject.setChromosomProjectDirectory(selectedDirectory);

                        try {
                            readProjectFolder(selectedDirectory, true);
                            loaded = true;
                        }
                        catch(NullPointerException ee) {
                            
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Wrong Folder");
                            alert.setHeaderText("Wrong Folder");
                            alert.setContentText("The selected folder doesn't contain all needed files.\n" + missingFileName + " is/are missed. \nPlease try it again!");

                            alert.setResizable(true);
                            alert.getDialogPane().setPrefSize(480, 320);
                            
                            alert.showAndWait();
                            
                        }
                    }
                }
                while(loaded == false && selectedDirectory != null);
                
                if(loaded) {
                    chromosom.startChromosom(chromosomProject);

                    String heatNukleosomeString = chromosomProject.getChromosomProjectDirectory() + File.separator + "prop_matrices";

                    chromosom.getOpenModTable().setDisable(true);

                    heatProject.prepareReader(new File(heatNukleosomeString));

                    chromosom.startHeatNukleosomeTab(heatProject);

                    chromosom.getSaveAsPNG().setDisable(false);
                    chromosom.getSetModificationColors().setDisable(false);

                    packInCenter(chromosom);
                }
        });
        
        loadRuleset.setOnAction(e3 -> {
            
            boolean loaded = false;

            File selectedDirectory;

            do{
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Choose Project Folder");

                selectedDirectory = chooser.showDialog(chromosom.getPrimaryStage());

                if(selectedDirectory != null) {

                    chromosomProject.setChromosomProjectDirectory(selectedDirectory);

                    try {
                        readProjectFolder(selectedDirectory, false);
                        loaded = true;
                    }
                    catch(NullPointerException ee) {

                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Wrong Folder");
                        alert.setHeaderText("Wrong Folder");
                        alert.setContentText("The selected folder doesn't contain all needed files.\n" + missingFileName + " is/are missed. \nPlease try it again!");

                        alert.setResizable(true);
                        alert.getDialogPane().setPrefSize(480, 320);
                        
                        alert.showAndWait();

                    }
                }
            }
            while(loaded == false && selectedDirectory != null);

            if(loaded) {
                while(chromosomProject.getHistoneMap() == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ChromosomMenu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                startChromosomEditor(chromosomProject, 0);
                packInCenter(chromosom);
            }
            
                        
//            FileChooser chooser = new FileChooser();
//            chooser.setTitle("Select Config File");
//            FileChooser.ExtensionFilter extFilter = 
//                        new FileChooser.ExtensionFilter("CONFIG files (*.cfg)", "*.cfg");
//                chooser.getExtensionFilters().add(extFilter);
////            File defaultDirectory = new File("C:\\Users\\Jakob\\Documents\\NetBeansProjects\\Chromosom");
////            chooser.setInitialDirectory(defaultDirectory);
//            File selectedConfig = chooser.showOpenDialog(chromosom.getPrimaryStage());
//
//            if(selectedConfig != null) {
//                readHistoneMapFile(selectedConfig);
//
//                if (findAndReadModificationTable(chromosom.getModificationTableFile())) {
//                    startChromosomEditor(chromosom.getProject());
//                    chromosom.getSetModificationColors().setDisable(false);
//                    chromosom.getOpenModTable().setDisable(false);
//
//                    packInCenter(chromosom);
//                }
//            }
        });
        

        createNewSimulation.setOnAction(e2 -> {
            findAndReadModificationTable(chromosom.getModificationTableFile());
            startChromosomEditor(chromosomProject);
            chromosom.getOpenModTable().setDisable(false);
            chromosom.getSetModificationColors().setDisable(false);
            
            packInCenter(chromosom);
        });
        
        runSimulation.setOnAction(e4 -> {
            startChromosomEditor(chromosomProject, 3);
        });
        
        this.getChildren().add(createNewSimulation);
        this.getChildren().add(loadRuleset);
        this.getChildren().add(loadSimulation);
        this.getChildren().add(runSimulation);
    }
    
    public boolean setChromosomFile(File file, boolean first) {
        boolean bool = false;
        File[] fileArray = file.listFiles(new FilenameFilter() { 
            public boolean accept(File dir, String filename) { 
                return filename.equals("outfile.txt"); 
            }
        });
        
        if(fileArray != null && fileArray.length == 1) {
            bool = true;
            chromosomFile = file.getParentFile();
        }
        else if(first == true){
            return setChromosomFile(new File(file + File.separator + "simout"), false);
        }
        
        return bool;
    }
    
    public void startChromosomEditor(ChromosomProject chromosomProject) {
        
            if(getChromosomEditor() == null){
                setChromosomEditor(new ChromosomEditor(chromosom, chromosomProject));
                getChromosomEditor().showEditorPane(0, 0);
            }
            
            chromosom.setProject(chromosomProject);
            BorderPane.setMargin(getChromosomEditor(), new Insets(7,7,7,7));
            chromosom.getRootLayout().setCenter(getChromosomEditor());

            EditorBottomPane editorBottomPane = getChromosomEditor().getBottom();
            getChromosomEditor().setBottom(editorBottomPane);
            chromosom.getRootLayout().setBottom(editorBottomPane);

            EditorOptions options = new EditorOptions(getChromosomEditor());
            getChromosomEditor().setOptions(options);
            chromosom.getRootLayout().setRight(options);
            
            getChromosomEditor().getSiteSetter().initGUI();
            
            packInCenter(chromosom);

//            if(chromosomProject.getHistoneMap() != null || chromosom.getEditorRuleList().size() > 0) {
//                getChromosomEditor().showEditorPane(1, 0);
//            }
    }
    
    public void startChromosomEditor(ChromosomProject chromosomProject, int initialEditorPane) {
        startChromosomEditor(chromosomProject);
        getChromosomEditor().showEditorPane(initialEditorPane, 0);
    }
    
    public String getOutputHashKey(File outputFile) {
        BufferedReader br = null;
        String hashKey = "";
        try {
            br = new BufferedReader(new FileReader(outputFile));
            String firstLine = br.readLine().trim();
            if(firstLine.startsWith("#") && firstLine.endsWith("#")) {
                hashKey = firstLine.replaceAll("#", "");
            }
//            System.err.println(hashKey);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChromosomMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChromosomMenu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ChromosomMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return hashKey;
    }
    
    public void readHistoneMapFile(File ruleFile) {
               
        if(getChromosomEditor() == null) {
            setChromosomEditor(new ChromosomEditor(chromosom, getChromosomProject()));
        }
        
        try {
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(ruleFile);

            doc.getDocumentElement().normalize();
            
            NodeList histoneDef = doc.getElementsByTagName("histoneOrder");
            
            ArrayList<String> critList = new ArrayList<>();
            
            for (int temp = 0; temp < histoneDef.getLength(); temp++) {
                Node nNode = histoneDef.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    
                    NodeList histoneList = eElement.getElementsByTagName("histone");
                    LinkedHashMap<String, String[][]> histoneMap = new LinkedHashMap<>();
                    for (int histoneNr = 0; histoneNr < histoneList.getLength(); histoneNr++) {
                        Node histoneNode = histoneList.item(histoneNr);
                        if (histoneNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element histoneElement = (Element) histoneNode;
                            String histoneName = histoneElement.getAttribute("name");
                            
                            String histoneMaxXString = histoneElement.getAttribute("maxX");
                            
                            if(!histoneMaxXString.equals("")) {
                                int histoneMaxX = Integer.parseInt(histoneElement.getAttribute("maxX"));
                                int histoneMaxY = 0;

                                NodeList siteList = histoneElement.getElementsByTagName("site");

                                histoneMaxY = siteList.getLength() / histoneMaxX;
                                String sitesOnHistone[][] = new String[histoneMaxX][histoneMaxY];

                                for (int siteNr = 0; siteNr < siteList.getLength(); siteNr++) {
                                    Node siteNode = siteList.item(siteNr);
                                    if (siteNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element siteElement = (Element) siteNode;
                                        String siteName = siteElement.getAttribute("name");
                                        String sitePos = siteElement.getAttribute("pos");
                                        if(sitePos.contains("|")) {
                                            String splitArray[] = sitePos.split("\\|");
                                            sitesOnHistone[Integer.parseInt(splitArray[0])][Integer.parseInt(splitArray[1])] = siteName;
                                        }
                                    }
                                }
                                
                                histoneMap.put(histoneName, sitesOnHistone);
                            }
                            else {
                                histoneMap.put(histoneName, null);
                            }
                        }
                    }
                    getChromosomProject().setHistoneMap(histoneMap);
                    getChromosomProject().setHistoneMapFile(ruleFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void findAndReadXML(File file) {
        
        File[] files = file.listFiles();
        File ruleFile = null;
        for (File fileInDir : files) {
            if (fileInDir.isFile()) {
                if(fileInDir.getName().endsWith(".cfg")) {
                    ruleFile = fileInDir;
                    break;
                }
            }
        }
        
        
        if(getChromosomEditor() == null) {
            setChromosomEditor(new ChromosomEditor(chromosom, getChromosomProject()));
        }
        getChromosomEditor().showEditorPane(0, 0);
        
        try {
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(ruleFile);

            doc.getDocumentElement().normalize();

            String colorTheme = "Theme 1";
            if(doc.getDocumentElement().getElementsByTagName("colorTheme").getLength()>0) {
                colorTheme = getXMLValueByTag(doc.getDocumentElement(), "colorTheme", "value");
            }
            
            String empty_nucleosome = "";
            if(doc.getDocumentElement().getElementsByTagName("empty_nucleosome").getLength()>0) {
                empty_nucleosome = getXMLValueByTag(doc.getDocumentElement(), "empty_nucleosome", "value");
            }
            
            getChromosomProject().setEmpty_nucleosome(empty_nucleosome);
            
            getChromosomProject().getModificationColors().setTheme(colorTheme);
            
            NodeList histoneDef = doc.getElementsByTagName("histoneOrder");
            
            for (int temp = 0; temp < histoneDef.getLength(); temp++) {
                Node nNode = histoneDef.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    
                    NodeList histoneList = eElement.getElementsByTagName("histone");
                    LinkedHashMap<String, String[][]> histoneMap = new LinkedHashMap<>();
                    for (int histoneNr = 0; histoneNr < histoneList.getLength(); histoneNr++) {
                        Node histoneNode = histoneList.item(histoneNr);
                        if (histoneNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element histoneElement = (Element) histoneNode;
                            String histoneName = histoneElement.getAttribute("name");
                            
                            String histoneMaxXString = histoneElement.getAttribute("maxX");
                            
                            if(!histoneMaxXString.equals("")) {
                                int histoneMaxX = Integer.parseInt(histoneElement.getAttribute("maxX"));
                                int histoneMaxY = 0;

                                NodeList siteList = histoneElement.getElementsByTagName("site");

                                histoneMaxY = siteList.getLength() / histoneMaxX;
                                String sitesOnHistone[][] = new String[histoneMaxX][histoneMaxY];

                                for (int siteNr = 0; siteNr < siteList.getLength(); siteNr++) {
                                    Node siteNode = siteList.item(siteNr);
                                    if (siteNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element siteElement = (Element) siteNode;
                                        String siteName = siteElement.getAttribute("name");
                                        String sitePos = siteElement.getAttribute("pos");
                                        if(sitePos.contains("|")) {
                                            String splitArray[] = sitePos.split("\\|");
                                            sitesOnHistone[Integer.parseInt(splitArray[0])][Integer.parseInt(splitArray[1])] = siteName;
                                        }
                                    }
                                }
                                
                                histoneMap.put(histoneName, sitesOnHistone);
                            }
                            else {
                                histoneMap.put(histoneName, null);
                            }
                        }
                    }
                    getChromosomProject().setHistoneMap(histoneMap);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void readParameterFile(File parameterFile) {
                   
        if(getChromosomEditor() == null) {
            setChromosomEditor(new ChromosomEditor(chromosom, getChromosomProject()));
        }
        
        try {
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(parameterFile);

            doc.getDocumentElement().normalize();

            //Color Theme
            String colorTheme = "Theme 1";
            if(doc.getDocumentElement().getElementsByTagName("colorTheme").getLength()>0) {
                colorTheme = getXMLValueByTag(doc.getDocumentElement(), "colorTheme", "value");
            }
            
            getChromosomProject().getModificationColors().setTheme(colorTheme);
            
            //Cyclic
            if(doc.getDocumentElement().getElementsByTagName("cyclicChromosomes").getLength()>0) {
                String cyclicValue = getXMLValueByTag(doc.getDocumentElement(), "cyclicChromosomes", "value");
                if(cyclicValue.equals("1"))
                    getChromosomProject().setCyclic(true);
            }
            
            //Output Directory
//            if(doc.getDocumentElement().getElementsByTagName("outputDirectory").getLength()>0) {
//                String outputPath = getXMLValueByTag(doc.getDocumentElement(), "outputDirectory", "value");
//                getChromosomProject().setOutputDirectory(new File(outputPath));
//            }
            
            //Prop Matrices
            if(doc.getDocumentElement().getElementsByTagName("propMatrices").getLength()>0) {
                String value = getXMLValueByTag(doc.getDocumentElement(), "propMatrices", "value");
                
                if(value.equals("1")) {
                    getChromosomProject().setPropMatrices(true);
                }
                
            }
            
            //Simulation Time
            if(doc.getDocumentElement().getElementsByTagName("simulationTime").getLength()>0) {
                String type = getXMLValueByTag(doc.getDocumentElement(), "simulationTime", "type");
                getChromosomProject().setSimulationTimeType(type);
                
                String value = getXMLValueByTag(doc.getDocumentElement(), "simulationTime", "value");
                getChromosomProject().setSimulationTime(Integer.parseInt(value));
            }
            
//            String empty_nucleosome = "";
//            if(doc.getDocumentElement().getElementsByTagName("emptyNucleosome").getLength()>0) {
//                empty_nucleosome = getXMLValueByTag(doc.getDocumentElement(), "emptyNucleosome", "value");
//            }
//            getChromosomProject().setEmpty_nucleosome(empty_nucleosome);
            
            String showEmptySites = "";
            
            if(doc.getDocumentElement().getElementsByTagName("showEmptySites").getLength()>0) {
                showEmptySites = getXMLValueByTag(doc.getDocumentElement(), "showEmptySites", "value");
            }
            if (showEmptySites.equals("1")) {
                getChromosomProject().setShowEmptySites(true);
            }
            
            NodeList colorDef = doc.getElementsByTagName("color");
            
            if (colorDef.getLength() > 0) {
                HashMap<String, HashMap<String, HashMap<String, String>>> specialColorMap = new HashMap();
            
                for (int temp = 0; temp < colorDef.getLength(); temp++) {
                    Node nNode = colorDef.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element colorElement = (Element) nNode;

                        // read out all values
                        String histone = colorElement.getAttribute("histone");
                        if (histone.equals("")) {
                            histone = "undefined";
                        }
                        
                        String site = colorElement.getAttribute("site");
                        if (site.equals("")) {
                            site = "undefined";
                        }
                        
                        String modification = colorElement.getAttribute("modification");
                        if (modification.equals("")) {
                            modification = "undefined";
                        }
                        
                        String color = colorElement.getAttribute("value");
                        
                        //create or get the hashmap for the special
                        HashMap<String, HashMap<String, String>> sites;
                        if(specialColorMap.containsKey(histone)) {
                            sites = specialColorMap.get(histone);
                        }
                        else {
                            sites = new HashMap();
                        }

                        HashMap<String, String> modifications;
                        if(sites.containsKey(site)) {
                            modifications = sites.get(site);
                        }
                        else {
                            modifications = new HashMap();
                        }

                        modifications.put(modification, color);

                        sites.put(site, modifications);
                        specialColorMap.put(histone, sites);
                    }
                }
                getChromosomProject().setSpecialColorMap(specialColorMap);
            }
            
            getChromosomProject().setParameterFile(parameterFile);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    public void readInitialStateFile(File initalStateFile) {
        if(getChromosomEditor() == null) {
            setChromosomEditor(new ChromosomEditor(chromosom, getChromosomProject()));
        }
        
        try {
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(initalStateFile);

            doc.getDocumentElement().normalize();
            NodeList initStateList = doc.getElementsByTagName("initialState");
            
            for(int stateKey = 0; stateKey < initStateList.getLength(); stateKey++) {
                Node node = initStateList.item(stateKey);
                
                Element initStateElement = (Element)node;
                
                String initStateText = initStateElement.getTextContent();
                
                getChromosomProject().setInitialState(initStateText);
                getChromosomProject().setInitialStateFile(initalStateFile);
                break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void readProjectFolder(File folderPath, boolean checkOutfile) {
        missingFileName = "";
        
        File[] files = folderPath.listFiles();
        
        File histoneMapFile = null;
        File ruleSetFile = null;
        File initialStateFile = null;
        File parameterFile = null;
        File outputFile = null;
        
        for (File fileInDir : files) {
            if (fileInDir.isFile()) {
                String fileName = fileInDir.getName();
                if(fileName.endsWith(".cfg")) {
                    
                    try {
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(fileInDir);

                        doc.getDocumentElement().normalize();

                        NodeList histoneMapList = doc.getElementsByTagName("histoneMap");
                        NodeList parametersList = doc.getElementsByTagName("simulationParameters");
                        NodeList initialStateList = doc.getElementsByTagName("initialState");
                        NodeList enzymeSetList = doc.getElementsByTagName("enzymeSet");

                        if(histoneMapList.getLength() > 0 && histoneMapFile == null) {
                            histoneMapFile = fileInDir;
                            continue;
                        }                            
                        if(enzymeSetList.getLength() > 0 && ruleSetFile == null) {
                            ruleSetFile = fileInDir;
                            continue;
                        }   
                        if(initialStateList.getLength() > 0 && initialStateFile == null) {
                            initialStateFile = fileInDir;
                            continue;
                        }
                        if(parametersList.getLength() > 0 && parameterFile == null) {
                            parameterFile = fileInDir;
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            
                if(fileName.equals("outfile.txt")) {
                    outputFile = fileInDir;
                    continue;
                }
            }
        }
        
        if(histoneMapFile != null) {
            readHistoneMapFile(histoneMapFile);
        }
        else {
            if(missingFileName.equals("")) {
                missingFileName = "HistoneMap File";
            }
            else {
                missingFileName += ",\nHistoneMap File";
            }
        }
        
        if(ruleSetFile != null) {
            readRulesetFile(ruleSetFile);
        }
        else {
            if(missingFileName.equals("")) {
                missingFileName = "Ruleset File";
            }
            else {
                missingFileName += ",\nRuleset File";
            }
        }
        
        if(initialStateFile != null) {
            readInitialStateFile(initialStateFile);
        }
        else {
            if(missingFileName.equals("")) {
                missingFileName = "Initial State File";
            }
            else {
                missingFileName += ",\nInitial State File";
            }
        }
        
        if(parameterFile != null) {
            readParameterFile(parameterFile);
        }    
        else {
            if(missingFileName.equals("")) {
                missingFileName = "Parameters File";
            }
            else {
                missingFileName += ",\nParameters File";
            }
        }
        
        if(checkOutfile) {
            if(outputFile != null) {
                chromosomProject.setOutputFile(outputFile);
            }    
            else {
                if(missingFileName.equals("")) {
                    missingFileName = "outfile.txt";
                }
                else {
                    missingFileName += ",\noutfile.txt";
                }
            }
        }
        
        if(histoneMapFile != null && ruleSetFile != null && parameterFile != null && checkOutfile == false) {
            getChromosomEditor().showEditorPane(0, 0);
        }
        else if(histoneMapFile != null && ruleSetFile != null && parameterFile != null && checkOutfile == true && outputFile != null) {
            
        }
        else {
            throw new NullPointerException();
        }
        
    }
    
    public void readRulesetFile(File ruleFile) {
               
        if(getChromosomEditor() == null) {
            setChromosomEditor(new ChromosomEditor(chromosom, getChromosomProject()));
        }
        
        try {
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(ruleFile);

            doc.getDocumentElement().normalize();
            
            NodeList enzymeList = doc.getElementsByTagName("enzymeDef");

            ArrayList<String> critList = new ArrayList<>();
            
            LinkedHashMap<String, EditorEnzyme> enzymeMap = new LinkedHashMap<>();
            
            for (int temp = 0; temp < enzymeList.getLength(); temp++) {

                    EditorEnzyme enzyme = new EditorEnzyme(getChromosomEditor());
                
                    Node nNode = enzymeList.item(temp);
                    String enzymeName = "";
                    String concentration = "";
                    String size = "";

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        enzymeName = getXMLValueByTag(eElement, "name", "value");
//                        System.err.println("\nCurrent Element: " + enzymeName);
                        enzyme.getNameTF().setText(enzymeName);
                        
                        concentration = getXMLValueByTag(eElement, "concentration", "value");
//                        System.err.println("Concentration: " + concentrationType + ": " + concentration + "\n");
                        enzyme.getConcentrationTF().setText(concentration);
                        
                        size = getXMLValueByTag(eElement, "size", "value");
                        enzyme.getSizeTF().setText(size);
                        
                        NodeList ruleList = eElement.getElementsByTagName("bindingRatesRuleSet");
                        
                        for (int ruleNumber = 0; ruleNumber < ruleList.getLength(); ruleNumber++) {
                            Node ruleNode = ruleList.item(ruleNumber);

                            EditorRule editorRule = new EditorRule(getChromosomEditor());
                            
                            String ruleName = "";
                            String ruleType = "";
                            String target = "";
                            String rule = "";
                            String rate = "";
                            String dissociationRateType = "";
                            String dissociationRate = "";
                            String process = "";
                            String ruleEnabled = "";
                            
                            if (ruleNode.getNodeType() == Node.ELEMENT_NODE) {
                               Element ruleElement = (Element) ruleNode;

                               ruleName = getXMLValueByTag(ruleElement, "name", "value");
                               editorRule.getNameTF().setText(ruleName);
                               
                               ruleEnabled = getXMLValueByTag(ruleElement, "enabled", "value");
                               
                               ruleType = getXMLValueByTag(ruleElement, "type", "value");
                               editorRule.setType(ruleType);
                               
                               target = getXMLValueByTag(ruleElement, "target", "value");
                               editorRule.setTarget(target);
                               
                               rule = getXMLValueByTag(ruleElement, "rule", "value");
                               editorRule.setRule(rule);
                               
                               rate = getXMLValueByTag(ruleElement, "rate", "value");
                               editorRule.getAssociationRateTF().setText(rate);
                               
                               dissociationRateType = getXMLValueByTag(ruleElement, "dissociationRate", "type");
                               editorRule.setDissociationRateType(dissociationRateType);
                               
                               dissociationRate = getXMLValueByTag(ruleElement, "dissociationRate", "value");
                               editorRule.getDissociationRateTF().setText(dissociationRate);
                               
                               NodeList tagList = ruleElement.getElementsByTagName("tag");
                               
                               String tagName = "";
                               String tagValue = "";
                               
                               for(int tagNumber = 0; tagNumber < tagList.getLength(); tagNumber++) {
                                    Node tagNode = tagList.item(tagNumber);

                                    if (tagNode.getNodeType() == Node.ELEMENT_NODE) {
                                        Element tagElement = (Element) tagNode;
                                        tagName = tagElement.getAttribute("name");
                                        tagValue = tagElement.getAttribute("value");
                                        editorRule.addTagField(tagName, tagValue);
                                        if(!critList.contains(tagName)) {
                                            critList.add(tagName);
                                        }
                                    }
                               }
                               
                               enzyme.addRule(editorRule);
                               editorRule.setEnzymeName(enzymeName);
                               if(Integer.parseInt(ruleEnabled) == 0) {
                                    editorRule.getSelCheck().setSelected(false);
                               }
                               else {
                                    chromosom.getEditorRuleList().add(editorRule);
                               }
                            }
                        }
                        
                        enzymeMap.put(enzymeName, enzyme);
                    }
            }
            
            heatProject.setCritList(critList);
            chromosom.setEnzymeMap(enzymeMap);  
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String getXMLValueByTag(Element eElement, String tagName, String valueName) {
        return ((Element)(eElement.getElementsByTagName(tagName).item(0))).getAttribute(valueName);
    }
 

    public boolean findAndReadModificationTable(File chromosomFile) {
        
        boolean ret = false;
        
        try {
        
        ModificationTable modTable = new ModificationTable(chromosom);

        modTable.load(chromosomFile);
        
        getChromosomProject().setHistoneModificationMap(modTable.getHistoneMap());
        
        ret = true;
        
        }
        catch(Exception e) {
            System.err.println("Not a valid modification file. Continued without modification table.");
            return true;
        }
        
        return ret;
    }

    /**
     * @return the chromosomProject
     */
    public ChromosomProject getChromosomProject() {
        return chromosomProject;
    }

    /**
     * @param chromosomProject the chromosomProject to set
     */
    public void setChromosomProject(ChromosomProject chromosomProject) {
        this.chromosomProject = chromosomProject;
    }
    
    public static void packInCenter(Chromosom chromosom) {
        boolean height = false, width = false;
        if(!chromosom.getPrimaryStage().isMaximized()){
            chromosom.getPrimaryStage().sizeToScene();
            
            chromosom.getPrimaryStage().setHeight(chromosom.getPrimaryStage().getHeight());
            chromosom.getPrimaryStage().setWidth(chromosom.getPrimaryStage().getWidth());
            
            if(chromosom.getPrimaryStage().getHeight() > chromosom.screenHeight) {
                height = true;
                chromosom.getPrimaryStage().setHeight(chromosom.screenHeight);
            }
            if(chromosom.getPrimaryStage().getWidth() > chromosom.screenWidth) {
                width = true;
                chromosom.getPrimaryStage().setWidth(chromosom.screenWidth);
            }
            if(width && height) {
                chromosom.getPrimaryStage().setMaximized(true);
            }
            else {
                chromosom.getPrimaryStage().centerOnScreen();
            }
        }
    }

    /**
     * @return the chromosomEditor
     */
    public ChromosomEditor getChromosomEditor() {
        return chromosomEditor;
    }

    /**
     * @param chromosomEditor the chromosomEditor to set
     */
    public void setChromosomEditor(ChromosomEditor chromosomEditor) {
        this.chromosomEditor = chromosomEditor;
    }
    
}
