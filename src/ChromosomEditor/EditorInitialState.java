/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import static ChromosomEditor.EditorSimulation.checkHistSimInstallation;
import NukleosomReader.NukleosomReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 *
 * @author Jakob
 */
public class EditorInitialState extends EditorPane{
    
    private final ChromosomEditor chromosomEditor;
    private String initialStateText;
    private TextArea textArea;
    private CheckBox cicleCheck;
    private TextField timeInput;
    private HashMap<String, HashMap<String, HashMap<String,String>>> nucleosomeMap;
    private File histSimFile;
    private Label checkLabel;
    private String checkOutput;
    
    public EditorInitialState(ChromosomEditor chromosomEditor, boolean justSimulation) 
    {
        this.paneName = "sf";
        this.paneFullName = "Initital State";
        nucleosomeMap = new HashMap();
        this.chromosomEditor = chromosomEditor;
                
        AnchorPane topPane = new AnchorPane();
        Label top = new Label(paneFullName);
        top.setFont(new Font(top.getFont().getName(), 16));
        topPane.getChildren().add(top);
        AnchorPane.setBottomAnchor(top, 10.0);
        AnchorPane.setLeftAnchor(top, 10.0);
        setTop(topPane);
        
        AnchorPane anchor = new AnchorPane();
        
        textArea = new TextArea();
        
        if(chromosomEditor.getChromosomProject().getInitialState() != null) {
            textArea.appendText(chromosomEditor.getChromosomProject().getInitialState());
        }
        
        AnchorPane.setLeftAnchor(textArea, 0.0);
        AnchorPane.setTopAnchor(textArea, 30.0);
        AnchorPane.setBottomAnchor(textArea, 60.0);
        
        textArea.setPrefWidth(chromosomEditor.getWidth());
        textArea.setId("text-area");
        
        Label initLabel = new Label("Initial state of the nucleosome string:");
        
        AnchorPane.setLeftAnchor(initLabel, 0.0);
        AnchorPane.setTopAnchor(initLabel, 0.0);
        
        anchor.getChildren().addAll(initLabel, textArea);
        
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
        
        if(checkHistSimInstallation(chromosomEditor) == false) {
            anchor.getChildren().add(hbox);
        }
        
        Button checkButton = new Button("Check");
        
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if(check() == true) {
                    checkLabel.setText("All Right!");
                }
                else {
                    Alert dialog = new Alert(Alert.AlertType.ERROR);
                    dialog.setTitle("Error");
                    dialog.setHeaderText("Check Failed");
                    dialog.setContentText(checkOutput);

                    dialog.setResizable(true);
                    dialog.getDialogPane().setPrefSize(480, 320);

                    dialog.showAndWait();
                    
                    checkLabel.setText("Check Failed!");
                }
                
//                textArea.setText(getInitialStateText().replaceAll(" <---", ""));
//                
//                if(checkInitalState() == true) {
//                    ScrollPane scroll = new ScrollPane();
//                    BigNukleosomRow initState = new BigNukleosomRow(chromosomEditor.getChromosomProject(), nucleosomeMap, 7, 7, 1, 1, false);
//                    scroll.setContent(initState);
//                    scroll.setPrefSize(USE_PREF_SIZE, 80);
//                    scroll.setStyle("-fx-background: #FFFFFF;-fx-border-color: #FFFFFF;");
//                    
//                    AnchorPane bottomAnchor = new AnchorPane();
//                    
//                    AnchorPane.setTopAnchor(scroll, 10.0);
//                    AnchorPane.setLeftAnchor(scroll, 0.0);
//                    AnchorPane.setRightAnchor(scroll, 0.0);
//                    
//                    bottomAnchor.getChildren().add(scroll);
//                    
//                    setBottom(bottomAnchor);
//                }
//                else {
//                    setBottom(null);
//                }
            }
        });
        
        AnchorPane.setLeftAnchor(checkButton, 0.0);
        AnchorPane.setBottomAnchor(checkButton, 0.0);
        
        AnchorPane.setLeftAnchor(hbox, 0.0);
        AnchorPane.setBottomAnchor(hbox, 30.0);
        
        checkLabel = new Label();
        checkLabel.setPrefHeight(checkButton.getPrefHeight());
        
        AnchorPane.setLeftAnchor(checkLabel, 70.0);
        AnchorPane.setBottomAnchor(checkLabel, 0.0);
        
        anchor.setPadding(new Insets(0,10,0,10));
        
        anchor.getChildren().addAll(checkButton, checkLabel);
        setCenter(anchor);
        
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
                                    
            if(getInitialStateText() != null) {
                argList.add("-sf");
                File temp = File.createTempFile("temp-statefile", ".cfg");
                temp.deleteOnExit();
                saveInitialState(temp);
                argList.add(temp.getAbsolutePath());
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
                return true;
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
    
    public boolean checkInitalState() {
        String stateText = getInitialStateText();
        
        String lineArray[] = stateText.split("\n");
        
        HashMap<String, HashMap<String,String>> nucleosome = null;
        
        LinkedHashMap<String, String[][]> histoneMap = chromosomEditor.getChromosomProject().getHistoneMap();
        
        boolean allRight = true;
        boolean nucleosomeRight;
        String errorKey = null;
        String line;
        for(int i = 0; i < lineArray.length; i++) {
            line = lineArray[i];
            
            if(line.trim().equals("")) {
                line = chromosomEditor.getChromosomProject().getEmpty_nucleosome();
            }
            
            nucleosomeRight = true;
            nucleosome = NukleosomReader.parseLine(line);
            
            if(nucleosome == null) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("There is an error in line " + (i+1));
                alert.setContentText("Please correct it. \n" + line);
                colorErrorLines(i);
                alert.showAndWait();
                
                return false;
            }
            else {
                for(String histoneKey : nucleosome.keySet()) {
                    if(histoneMap.get(histoneKey) == null) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("The histone \"" + histoneKey + "\" is not part of the histone map.");
                        alert.setContentText("Please correct this, by deleting this histone from the inititial nucleosome string or from the histone settings.");
                        colorErrorLines(i);
                        alert.showAndWait();
                        return false;
                    }
                    else {
                        for(String siteKey : nucleosome.get(histoneKey).keySet()) {
                            boolean hit = false;
                            for(int y = 0; y < histoneMap.get(histoneKey)[0].length; y++) {
                                for(int x = 0; x < histoneMap.get(histoneKey).length; x++) {
                                    String siteName = histoneMap.get(histoneKey)[x][y];
                                        if(siteName.equals(siteKey)) {
                                            hit = true;
                                        }
                                }
                            }
                            if(hit == false) {
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("The histone \"" + siteKey + "\" is not part of the site map.");
                                alert.setContentText("Please correct this, by deleting this site from the inititial nucleosome string or from the site settings.");
                                colorErrorLines(i);
                                alert.showAndWait();
                                return false;
                            }
                        }
                    }
                }
            }
            
            if(nucleosomeRight == true) {
                nucleosomeMap.put(String.valueOf(i), nucleosome);
            }
        }
        
        return allRight;
    }
    
    public String getInitialStateText() {
        initialStateText = textArea.getText().trim();
        if(initialStateText.equals(""))
        {
            initialStateText = null;
        }
        
//        initialStateText = initialStateText.replaceAll("&#10;", "\n");
        
        return initialStateText;
    }
    
    public void colorErrorLines(int errorLine) {
        String initialStateText = getInitialStateText();
        
        String textLines[] = initialStateText.split("\n");
        String newText = "";
        
        for(int i = 0;i < textLines.length; i++) {
            if(i != errorLine) {
                newText += textLines[i] + "\n";
            }
            else {
                newText += textLines[i] + " <---\n";
            }
        }
        
        textArea.setText(newText);
    }
    
    public boolean saveInitialState(File saveFile) {
        
        if(saveFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Initial State to File");
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
    
    public boolean writeToFile(File file) {
        try {
            Document doc = ChromosomEditor.createNewXMLDocument("initialState");
            
            String initialStateText = getInitialStateText();

            if(initialStateText != null && !initialStateText.equals("")) {
                doc.getDocumentElement().setTextContent(initialStateText);
            
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                //Format, Zeilenumbrüche, Einzüge festlegen
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);

                transformer.transform(source, result);

                System.out.println("File saved!");
                chromosomEditor.getChromosomProject().setInitialStateFile(file);
                return true;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean save() {
        return saveInitialState(null);
    }
    
    @Override
    public void back() {
        getInitialStateText(); //save the actual text
    }
    
    @Override
    public boolean next() {
        
        String startString = getInitialStateText();
        
        if(startString == null || startString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Startstring can't be emtpy");
            alert.setContentText("Please correct it. \n");
            alert.showAndWait();

            return false;
        }
        
        try {
            File tempFile = File.createTempFile("tmp-startstring", ".cfg");
            tempFile.deleteOnExit();
            return writeToFile(tempFile);
        } catch (IOException ex) {
            Logger.getLogger(EditorInitialState.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
