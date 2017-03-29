/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import application.StreamGobbler;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
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
public class EditorSimulation  extends EditorPane{
    
    ChromosomEditor chromosomEditor;
    File histSimFolder;
    File histSimFile;
    TextArea textarea;
    private CheckBox propMatCheck;
    private File destFolder;
    private VBox vbox;
    private ComboBox timesComboBox;
    private TextField timesField;
    private AnchorPane anchorPane;
    private TextArea emptyNucleosomeField;

    String line = "";
    private final CheckBox emptySitesCheck;
    private final CheckBox cicleCheck;
    private String checkOutput;
    
    public EditorSimulation(ChromosomEditor chromosomEditor, boolean justSimulation) {
        this.chromosomEditor = chromosomEditor;
        this.paneName = "pf";
        this.paneFullName = "Simulation Parameters";
        
        AnchorPane topPane = new AnchorPane();
        Label top = new Label(paneFullName);
        top.setFont(new Font(top.getFont().getName(), 16));
        topPane.getChildren().add(top);
        AnchorPane.setBottomAnchor(top, 10.0);
        AnchorPane.setTopAnchor(top, 10.0);
        setTop(topPane);
        
        anchorPane = new AnchorPane();
        anchorPane.setPadding(new Insets(0,10,0,10));
        
        AnchorPane hbox = new AnchorPane();
        
        TextField histSimPathField = new TextField("Path to HistSim ...");
        histSimPathField.setMinWidth(200);
        
        if(chromosomEditor.getChromosomProject().getChromosom().getHistSimFile() != null) {
           histSimPathField.setText(chromosomEditor.getChromosomProject().getChromosom().getHistSimFile().getAbsolutePath());
           histSimFile = chromosomEditor.getChromosomProject().getChromosom().getHistSimFile();
        }
        
        Button setPathButton = new Button("Set Path");
        
        setPathButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
        
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select HistSim");
                histSimFile = fileChooser.showOpenDialog(new Stage());
                
                if(histSimFile != null) {
                    histSimPathField.setText(histSimFile.getAbsolutePath());
                    histSimFolder = new File(histSimFile.getParent());
                    chromosomEditor.getChromosom().setHistSimFile(histSimFile);
                    histSimPathField.positionCaret(histSimPathField.getText().length());
                }
            }
        });
        
        Label histSimLabel = new Label("HistSim Binary: ");
        histSimLabel.setMinHeight(22);
        
        AnchorPane.setLeftAnchor(histSimLabel, 0.0);
        AnchorPane.setTopAnchor(histSimLabel, 0.0);
        
        AnchorPane.setLeftAnchor(histSimPathField, 80.0);
        AnchorPane.setTopAnchor(histSimPathField, 0.0);
        
        AnchorPane.setLeftAnchor(setPathButton, 290.0);
        AnchorPane.setTopAnchor(setPathButton, 0.0);
        
        hbox.getChildren().addAll(histSimLabel, histSimPathField, setPathButton);
        
        vbox = new VBox();
        
        if(checkHistSimInstallation(chromosomEditor) == false) {
            vbox.getChildren().add(hbox);
        }
        
        AnchorPane destBox = new AnchorPane();
        TextField destField = new TextField("Output Directory ...");
        destField.setMinWidth(200);
        
        destField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.contains("actual")) {
                setDestFolder(new File(newValue));
            }
            destField.positionCaret(destField.getText().length());
        });
        
        Button destButton = new Button("Set Path");
        destButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                DirectoryChooser dirchooser = new DirectoryChooser();
                dirchooser.setTitle("Select Output Directory");
                
                File folder = dirchooser.showDialog(new Stage());
                if(folder != null) {
                    setDestFolder(folder);
                    destField.setText(folder.getAbsolutePath());
                    destField.positionCaret(destField.getText().length());
                }
            }
        });
        
        Label outputLabel = new Label("Output Folder: ");
        outputLabel.setMinHeight(22);
        
        AnchorPane.setLeftAnchor(outputLabel, 0.0);
        AnchorPane.setTopAnchor(outputLabel, 0.0);
        
        AnchorPane.setLeftAnchor(destField, 80.0);
        AnchorPane.setTopAnchor(destField, 0.0);
        
        AnchorPane.setLeftAnchor(destButton, 290.0);
        AnchorPane.setTopAnchor(destButton, 0.0);
        
        destBox.getChildren().addAll(outputLabel, destField, destButton);
        
        if(chromosomEditor.getChromosomProject().getOutputDirectory() != null) {
            destField.setText(chromosomEditor.getChromosomProject().getOutputDirectory().getAbsolutePath());
            setDestFolder(chromosomEditor.getChromosomProject().getOutputDirectory());
        }
        
        vbox.getChildren().add(destBox);
        
        textarea = new TextArea();
        
        AnchorPane histoneMapBox = new AnchorPane();
        TextField histoneMapField = new TextField("HistoneMap File ...");
        histoneMapField.setMinWidth(200);
        histoneMapField.setOnDragOver(this::handleEnter);
        histoneMapField.setOnDragDropped(e -> handleDrop(e, histoneMapField));
        histoneMapField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.contains("actual")) {
                chromosomEditor.getChromosomProject().setHistoneMapFile(new File(newValue));
            }
            histoneMapField.positionCaret(histoneMapField.getText().length());
        });
        Button histoneMapButton = new Button("Set Path");
        histoneMapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser filechooser = new FileChooser();
                filechooser.setTitle("Select HistoneMap File");
                FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("CONFIG files (*.cfg)", "*.cfg");
                filechooser.getExtensionFilters().add(extFilter);

                File histoneMapFile = filechooser.showOpenDialog(new Stage());

                if(histoneMapFile != null) {
                    histoneMapField.setText(histoneMapFile.getAbsolutePath());
                    chromosomEditor.getChromosomProject().setHistoneMapFile(histoneMapFile);
                    histoneMapField.positionCaret(histoneMapField.getText().length());
                    //TODO Vielleicht Datei richtig einlesen und verarbeiten, damit am Edne noch was angepasst werden kann?
                }
            }
        });

        if(chromosomEditor.getChromosomProject().getHistoneMapFile() != null) {
            histoneMapField.setText("actual HistoneMap");
        }
            
        Label histoneMapLabel = new Label("HistoneMap: ");
        histoneMapLabel.setMinHeight(22);
        
        AnchorPane.setLeftAnchor(histoneMapLabel, 0.0);
        AnchorPane.setTopAnchor(histoneMapLabel, 0.0);
        
        AnchorPane.setLeftAnchor(histoneMapField, 80.0);
        AnchorPane.setTopAnchor(histoneMapField, 0.0);
        
        AnchorPane.setLeftAnchor(histoneMapButton, 290.0);
        AnchorPane.setTopAnchor(histoneMapButton, 0.0);
        
        histoneMapBox.getChildren().addAll(histoneMapLabel, histoneMapField, histoneMapButton);

        AnchorPane rulesetBox = new AnchorPane();
        TextField rulesetField = new TextField("Ruleset File ...");
        rulesetField.setMinWidth(200);
        rulesetField.setOnDragOver(this::handleEnter);
        rulesetField.setOnDragDropped(e -> handleDrop(e, rulesetField));
        rulesetField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.contains("actual")) {
                chromosomEditor.getChromosomProject().setRulesetFile(new File(newValue));
            }
            rulesetField.positionCaret(rulesetField.getText().length());
        });
        Button rulesetButton = new Button("Set Path");
        rulesetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser filechooser = new FileChooser();
                filechooser.setTitle("Select Ruleset File");
                FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("CONFIG files (*.cfg)", "*.cfg");
                filechooser.getExtensionFilters().add(extFilter);

                File rulesetFile = filechooser.showOpenDialog(new Stage());
                if(rulesetFile != null) {
                    rulesetField.setText(rulesetFile.getAbsolutePath());
                    chromosomEditor.getChromosomProject().setRulesetFile(rulesetFile);
                    rulesetField.positionCaret(rulesetField.getText().length());
                    //TODO Vielleicht Datei richtig einlesen und verarbeiten, damit am Edne noch was angepasst werden kann?
                }
            }
        });

        if(chromosomEditor.getChromosomProject().getRulesetFile()!= null) {
            rulesetField.setText("actual Ruleset");
        }
        
        Label ruleSetLabel = new Label("Ruleset: ");
        ruleSetLabel.setMinHeight(22);
        
        AnchorPane.setLeftAnchor(ruleSetLabel, 0.0);
        AnchorPane.setTopAnchor(ruleSetLabel, 0.0);
        
        AnchorPane.setLeftAnchor(rulesetField, 80.0);
        AnchorPane.setTopAnchor(rulesetField, 0.0);
        
        AnchorPane.setLeftAnchor(rulesetButton, 290.0);
        AnchorPane.setTopAnchor(rulesetButton, 0.0);

        rulesetBox.getChildren().addAll(ruleSetLabel, rulesetField, rulesetButton);

        AnchorPane initialStateBox = new AnchorPane();
        TextField initialStateField = new TextField("Initial State File ...");
        initialStateField.setMinWidth(200);
        initialStateField.setOnDragOver(this::handleEnter);
        initialStateField.setOnDragDropped(e -> handleDrop(e, initialStateField));
        initialStateField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.contains("actual")) {
                chromosomEditor.getChromosomProject().setInitialStateFile(new File(newValue));
            }
            initialStateField.positionCaret(initialStateField.getText().length());
        });
        Button initialStateButton = new Button("Set Path");
        initialStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                FileChooser filechooser = new FileChooser();
                filechooser.setTitle("Select Initial State File");
                FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("CONCFIG files (*.cfg)", "*.cfg");
                filechooser.getExtensionFilters().add(extFilter);

                File initialStateFile = filechooser.showOpenDialog(new Stage());
                if(initialStateFile != null) {
                    initialStateField.setText(initialStateFile.getAbsolutePath());
                    chromosomEditor.getChromosomProject().setInitialStateFile(initialStateFile);
                    initialStateField.positionCaret(initialStateField.getText().length());
                    //TODO Vielleicht Datei richtig einlesen und verarbeiten, damit am Edne noch was angepasst werden kann?
                }
            }
        });

        if(chromosomEditor.getChromosomProject().getInitialStateFile()!= null) {
            initialStateField.setText("actual Initial State");
        }

        Label initStateLabel = new Label("Initial State: ");
        initStateLabel.setMinHeight(22);
        
        AnchorPane.setLeftAnchor(initStateLabel, 0.0);
        AnchorPane.setTopAnchor(initStateLabel, 0.0);
        
        AnchorPane.setLeftAnchor(initialStateField, 80.0);
        AnchorPane.setTopAnchor(initialStateField, 0.0);
        
        AnchorPane.setLeftAnchor(initialStateButton, 290.0);
        AnchorPane.setTopAnchor(initialStateButton, 0.0);

        initialStateBox.getChildren().addAll(initStateLabel, initialStateField, initialStateButton);

        AnchorPane emptyNucleosomes = new AnchorPane();
        Label emptyNuclesomesLabel = new Label("Empty nucleosome:");
        emptyNucleosomeField = new TextArea();
        emptyNucleosomeField.setMinHeight(50);
        emptyNucleosomeField.setPrefHeight(50);
        emptyNucleosomeField.setWrapText(true);
        emptyNucleosomeField.setText(chromosomEditor.getChromosomProject().getEmpty_nucleosome());

        AnchorPane.setLeftAnchor(emptyNucleosomeField, 0.0);
        AnchorPane.setTopAnchor(emptyNucleosomeField, 20.0);
        AnchorPane.setRightAnchor(emptyNucleosomeField, 40.0);

        AnchorPane.setLeftAnchor(emptyNuclesomesLabel, 0.0);
        AnchorPane.setTopAnchor(emptyNuclesomesLabel, 0.0);

        emptyNucleosomes.getChildren().addAll(emptyNuclesomesLabel, emptyNucleosomeField);

        HBox timesBox = new HBox();
        ObservableList<String> options = FXCollections.observableArrayList(
            "Number Of Iterations",
            "Simulation Timesteps"
        );
        timesComboBox = new ComboBox(options);
        timesComboBox.setMinHeight(20);
        timesComboBox.setMinWidth(150);
        timesComboBox.setPromptText("Choose Time");

        timesField = new TextField();
        timesField.setMaxWidth(100);

        timesBox.getChildren().addAll(timesComboBox, timesField);
        timesBox.setSpacing(10);
        
        if(chromosomEditor.getChromosomProject().getSimulationTimeType() != null) {
            if(chromosomEditor.getChromosomProject().getSimulationTimeType().equals("i")) {
                timesComboBox.getSelectionModel().select(0);
            }
            else {
                timesComboBox.getSelectionModel().select(1);
            }
            
            if(chromosomEditor.getChromosomProject().getSimulationTime() > 0) {
                timesField.setText(String.valueOf(chromosomEditor.getChromosomProject().getSimulationTime()));
            }
        }  

//            HBox propMatBox = new HBox();
        propMatCheck = new CheckBox("Create Propensity Matrices");
        propMatCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                chromosomEditor.getChromosomProject().setPropMatrices(new_val);
            }
        });
        
        if(chromosomEditor.getChromosomProject().isPropMatrices()) {
            propMatCheck.setSelected(true);
        }

        emptySitesCheck = new CheckBox("Show empty sites");
        emptySitesCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                chromosomEditor.getChromosomProject().setShowEmptySites(new_val);
            }
        });
        
        if(chromosomEditor.getChromosomProject().isShowEmptySites()) {
            emptySitesCheck.setSelected(true);
        }        

        cicleCheck = new CheckBox("Cyclic");
        cicleCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean old_val, Boolean new_val) {
                chromosomEditor.getChromosomProject().setCyclic(new_val);
            }
        });
        
        if(chromosomEditor.getChromosomProject().isCyclic()) {
            cicleCheck.setSelected(true);
        }  
        
        vbox.getChildren().addAll(histoneMapBox, rulesetBox, initialStateBox, timesBox, propMatCheck, cicleCheck, emptySitesCheck);//, emptyNucleosomes);//, parameterBox);
            
        vbox.setSpacing(20);
        
        AnchorPane.setLeftAnchor(vbox, 20.0);
        AnchorPane.setTopAnchor(vbox, 20.0);
        AnchorPane.setRightAnchor(vbox, 20.0);
        AnchorPane.setBottomAnchor(vbox, 20.0);
        anchorPane.getChildren().add(vbox);
        anchorPane.setMaxWidth(500);
        setCenter(anchorPane);
    }
    
    public void handleEnter(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);
        event.consume();
    }

    public void handleDrop(DragEvent event, TextField field) {

        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            File f = db.getFiles().get(0);
            field.setText(f.getAbsolutePath());
            success = true;
        }
        /* let the source know whether the string was successfully 
         * transferred and used */
        event.setDropCompleted(success);
        event.consume();
    }
    
    @Override
    public boolean writeToFile(File outfile) {
        
        try {
            Document doc = ChromosomEditor.createNewXMLDocument("simulationParameters");
            
            createCiclingElement(doc);
            createSpecialColorsElement(doc);
            createShowEmptySites(doc, cicleCheck.isSelected());
            createModificationColorElement(doc);
//            createEmptyNucleosome(doc);
            createPropensityMatricesElement(doc);
            createOutputDirectoryElement(doc);
            createSimulationTimeElement(doc);
            
            
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
            chromosomEditor.getChromosomProject().setParameterFile(outfile);
            return true;
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(EditorRuleDesigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(EditorRuleDesigner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean save() {
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
    
    public boolean startSimulation() {
        try{
            if(!vbox.getChildren().contains(textarea)) {
                vbox.getChildren().add(textarea);
            }
            else {
               textarea.setText("");
            }

            ArrayList<String> argList = new ArrayList<>();
            
            if(histSimFile != null) {
                argList.addAll(Arrays.asList(new String[]{histSimFile.getAbsolutePath()}));
            }
            else {
                argList.addAll(Arrays.asList(new String[]{"HistSim"}));     //Use the installed version of HistSim
            }

//            if(chromosomEditor.getChromosomProject().isPropMatrices()) {
//                argList.add("-pm");
//            }

            if(destFolder != null) {
                argList.add("-d");
                argList.add(destFolder.getAbsolutePath());
            }
            
            if(chromosomEditor.getChromosomProject().getParameterFile()!= null) {
                argList.add("-pf");
                argList.add(chromosomEditor.getChromosomProject().getParameterFile().getAbsolutePath());
            }
            
            if(chromosomEditor.getChromosomProject().getRulesetFile()!= null) {
                argList.add("-rf");
                argList.add(chromosomEditor.getChromosomProject().getRulesetFile().getAbsolutePath());
            }
                        
            if(chromosomEditor.getChromosomProject().getHistoneMapFile() != null) {
                argList.add("-hm");
                argList.add(chromosomEditor.getChromosomProject().getHistoneMapFile().getAbsolutePath());
            }
                                    
            if(chromosomEditor.getChromosomProject().getInitialStateFile()!= null) {
                argList.add("-sf");
                argList.add(chromosomEditor.getChromosomProject().getInitialStateFile().getAbsolutePath());
            }
            
            //Check additional time settings
//            int selectedTimeOption;
//            if((selectedTimeOption = timesComboBox.getSelectionModel().getSelectedIndex()) != -1) {
//                try {
//                    int timeNumber = Integer.parseInt(timesField.getText());
//                    switch(selectedTimeOption) {
//                        case 0:
//                            argList.add("-i");
//                            argList.add(String.valueOf(timeNumber));
//                            break;
//                        case 1:
//                            argList.add("-t");
//                            argList.add(String.valueOf(timeNumber));
//                            break;
//                    }
//                }
//                catch(NumberFormatException e) {
//                }
//            }

//            if(chromosomEditor.getChromosomProject().getInitialState() != null) {
//                argList.add("-s");
//                String text  = "{" + chromosomEditor.getChromosomProject().getInitialState();
//                text = text.replaceAll("\n", "}{");
//                text += "}";
//
//                argList.add(text);
//            }

            String[] args = argList.stream().toArray(String[]::new);
            

            String commandText = "";
            for(String str : args) {
                commandText += str + " ";
            }

            textarea.appendText("running command:\n");
            System.err.println("running command:\n");
             
            System.err.println(commandText);
            textarea.appendText(commandText);

            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = processBuilder.start();
            
            new StreamGobbler(process.getInputStream(), textarea);
            new StreamGobbler(process.getErrorStream(), textarea);
            
            this.getScene().getWindow().sizeToScene();
            
            return true;
            
        } catch(Exception e){
            System.err.println("Doesn't work.");
            textarea.appendText("\nDoesn't work.");
            e.printStackTrace();
       }
        return false;
    }
    
    @Override
    public boolean next() {
        try {
            File tempFile = File.createTempFile("temp-parameters", ".cfg");
            tempFile.deleteOnExit();
            writeToFile(tempFile);
            if(check()) {
                return startSimulation();
            }
            else {
                Alert dialog = new Alert(Alert.AlertType.ERROR);
                dialog.setTitle("Error");
                dialog.setHeaderText("Check Failed");
                dialog.setContentText(checkOutput);

                dialog.setResizable(true);
                dialog.getDialogPane().setPrefSize(480, 320);

                dialog.showAndWait();
            }
        } catch (IOException ex) {
            Logger.getLogger(EditorSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean checkHistSimInstallation(ChromosomEditor chromosomEditor) {
        
        try {
            String path = EditorSimulation.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");
            decodedPath = decodedPath.substring(0, decodedPath.lastIndexOf(File.separator));
            
            List<File> files = new ArrayList<>();
            Path pathTest = Paths.get(decodedPath);
            files = getFiles(files, pathTest);
            
            for(File file : files) {
                if(file.getName().equals("HistSim")) {
                    chromosomEditor.getChromosom().setHistSimFile(file);
                    return true;
                }
            }
            
        }
        catch(UnsupportedEncodingException ex) {
            Logger.getLogger(EditorSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }    
       
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("HistSim");
            Process process = processBuilder.start();
            process.waitFor();
            return true;
        }
        catch(IOException ioe) {
            if(ioe.getMessage().contains("error=2")) {
                System.err.println("HistSim is not installed.");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(EditorSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean check() {
        try{
            ArrayList<String> argList = new ArrayList<>();
            
            if(chromosomEditor.getChromosomProject().getChromosom().getHistSimFile() != null) {
                argList.addAll(Arrays.asList(new String[]{chromosomEditor.getChromosomProject().getChromosom().getHistSimFile().getAbsolutePath()}));
            }
            else if(EditorSimulation.checkHistSimInstallation(chromosomEditor) == true){
                argList.addAll(Arrays.asList(new String[]{"HistSim"}));     //Use the installed version of HistSim
            }
                        
            if(chromosomEditor.getChromosomProject().getHistoneMapFile() != null) {
                argList.add("-hm");
                argList.add(chromosomEditor.getChromosomProject().getHistoneMapFile().getAbsolutePath());
            }
            
            if(chromosomEditor.getChromosomProject().getInitialStateFile() != null) {
                argList.add("-sf");
                argList.add(chromosomEditor.getChromosomProject().getInitialStateFile().getAbsolutePath());
            }
                                    
            argList.add("-check");
            
            String[] args = argList.stream().toArray(String[]::new);
            
            String commandText = "";
            for(String str : args) {
                commandText += str + " ";
            }
            
            System.err.println(commandText);

            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = processBuilder.start();

            this.getScene().getWindow().sizeToScene();
            
//            process.waitFor();
            
            InputStream stdout = process.getInputStream();
            BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));
            String line="";
            checkOutput = "";
            while ((line = reader.readLine()) != null) {
                checkOutput += line + System.lineSeparator();
            }
            
            process.waitFor();
            int exitValue = process.exitValue();
            
            if(exitValue == 0){
                checkOutput  = "";
                argList.remove(argList.indexOf("-sf"));
                argList.remove(argList.indexOf(chromosomEditor.getChromosomProject().getInitialStateFile().getAbsolutePath()));
                
                if(chromosomEditor.getChromosomProject().getRulesetFile()!= null) {
                    argList.add("-rf");
                    argList.add(chromosomEditor.getChromosomProject().getRulesetFile().getAbsolutePath());
                }
                
                args = argList.stream().toArray(String[]::new);
            
                commandText = "";
                for(String str : args) {
                    commandText += str + " ";
                }

                System.err.println(commandText);

                processBuilder = new ProcessBuilder(args);
                process = processBuilder.start();

                this.getScene().getWindow().sizeToScene();

    //            process.waitFor();

                stdout = process.getInputStream();
                reader = new BufferedReader (new InputStreamReader(stdout));
                line="";
                checkOutput = "";
                while ((line = reader.readLine()) != null) {
                    checkOutput += line + System.lineSeparator();
                }

                process.waitFor();
                exitValue = process.exitValue();
                
                if(exitValue == 0) {
                    return true;
                }
                else {
                    return false;
                }
                
            }
            else {
                return false;
            }
            
        } catch(Exception e){
            System.err.println("Doesn't work.");
            e.printStackTrace();
       }
        return false;
    }

    /**
     * @return the propMatCheck
     */
    public CheckBox getPropMatCheck() {
        return propMatCheck;
    }
    
    public static boolean isAlive(Process p) {
        try {
          p.exitValue();
          return false;
        }
        catch (IllegalThreadStateException e) {
          return true;
        }
    }
    
    private static List<File> getFiles(List<File> fileNames, Path dir) {
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if(path.toFile().isDirectory()) {
                    getFiles(fileNames, path);
                } else {
                    fileNames.add(path.toFile());
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    } 
    
    /**
     * @param propMatCheck the propMatCheck to set
     */
    public void setPropMatCheck(CheckBox propMatCheck) {
        this.propMatCheck = propMatCheck;
    }

    /**
     * @return the destFolder
     */
    public File getDestFolder() {
        return destFolder;
    }

    /**
     * @param destFolder the destFolder to set
     */
    public void setDestFolder(File destFolder) {
        this.destFolder = destFolder;
    }
    
    public Element createCiclingElement(Document doc) {
        String text = "0";
        
        if(chromosomEditor.getChromosomProject().isCyclic() == true) {
            text = "1";
        }
        
        Element cicleElement = chromosomEditor.addXMLElement(doc.getDocumentElement(), "cyclicChromosomes");
        
        cicleElement.setAttribute("value", text);

        return cicleElement;
    }
    
    public Element createSpecialColorsElement(Document doc) {
       Element specialColors = null;
        
       HashMap<String, HashMap<String, HashMap<String, String>>> specialColorsMap = chromosomEditor.getChromosomProject().getSpecialColorMap();
       
       if (specialColorsMap != null && specialColorsMap.size() > 0) {
           // Init the XML-Element
           specialColors = chromosomEditor.addXMLElement(doc.getDocumentElement(), "specialColors");
           for(String histone : specialColorsMap.keySet()) {
               for(String site : specialColorsMap.get(histone).keySet()) {
                   for(String modification : specialColorsMap.get(histone).get(site).keySet()) {
                      Element color = chromosomEditor.addXMLElement(specialColors, "color");
                      color.setAttribute("histone", histone);
                      color.setAttribute("site", site);
                      color.setAttribute("modification", modification);
                      color.setAttribute("value", specialColorsMap.get(histone).get(site).get(modification));
                   }
               }
           }
       }
        
       return specialColors;
    }
    
    public Element createEmptyNucleosome(Document doc) {
        Element emptyNucleosome = null;
        
        String emptyNucleosomeStr = emptyNucleosomeField.getText();
        
        if(emptyNucleosomeStr != null && !emptyNucleosomeStr.equals("")) {
            emptyNucleosome = chromosomEditor.addXMLElement(doc.getDocumentElement(), "emptyNucleosome");
            emptyNucleosome.setAttribute("value", emptyNucleosomeStr);
        }
        return emptyNucleosome;
    }
    
    /**
     * Creates and append a new XML element for the attribute 'showEmptySites'.
     * ShowEmptySites is the boolean valueto show or hide complete empty (unnamed) sites.
     * @param doc Given XML document to append on
     * @param show value of attribute 'showEmptySite'
     * @return 
     */
    public Element createShowEmptySites(Document doc, boolean show) {
        Element showEmptySites = chromosomEditor.addXMLElement(doc.getDocumentElement(), "showEmptySites");
        if (chromosomEditor.getChromosomProject().isShowEmptySites() == true) {
            showEmptySites.setAttribute("value", "1");
        }
        else {
            showEmptySites.setAttribute("value", "0");
        }
        
        return showEmptySites;
    }
    
    public Element createModificationColorElement(Document doc) {
        Element colorElement = chromosomEditor.addXMLElement(doc.getDocumentElement(), "colorTheme");
        colorElement.setAttribute("value", chromosomEditor.getChromosom().getProject().getModificationColors().getTheme());
        return colorElement;
    }
    
    public Element createPropensityMatricesElement(Document doc) {
        Element propMatrices = chromosomEditor.addXMLElement(doc.getDocumentElement(), "propMatrices");
        String value = "0";
        
        if(chromosomEditor.getChromosomProject().isPropMatrices()) {
            value = "1";
        }
        propMatrices.setAttribute("value", value);
        return propMatrices;
    }
    
//    public Element createHistSimElement(Document doc) {
//        Element element = null;
//        
//        if(chromosomEditor.getHistSimFile() != null) {
//            element = chromosomEditor.addXMLElement(doc.getDocumentElement(), "histSim");
//            element.setAttribute("value", chromosomEditor.getHistSimFile().getAbsolutePath());
//        }
//        
//        return element;
//    }
    
    public Element createOutputDirectoryElement(Document doc) {
        Element element = null;
        
        if(destFolder != null) {
            element = chromosomEditor.addXMLElement(doc.getDocumentElement(), "outputDirectory");
            element.setAttribute("value", destFolder.getAbsolutePath());
        }
        
        return element;
    }
   
    public Element createSimulationTimeElement(Document doc) {
        Element element = null;
        
        if(timesField.getText() != null && !timesField.getText().isEmpty()) {
            element = chromosomEditor.addXMLElement(doc.getDocumentElement(), "simulationTime");
            String typeStr = timesComboBox.getValue().toString();
            
            if(typeStr.contains("Iteration")) {
                typeStr = "i";
            }
            else {
                typeStr = "t";
            }
            
            element.setAttribute("type", typeStr);
            element.setAttribute("value", timesField.getText());
        }
        
        return element;
    }
    
    
}
