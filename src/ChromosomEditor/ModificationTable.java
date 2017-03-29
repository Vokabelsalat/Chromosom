/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import application.Chromosom;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Die Modifikationstabelle enthält eine Auflistung aller Histone mit den dazugehörigen Sites und möglichen Modifikationen an diesen.
 * @author Jakob
 */
public class ModificationTable extends BorderPane{
    
    private final HashMap<String, HistoneTable> histoneModificationMap;
    private Chromosom chromosom;
    /**
     * Erzeugt einen BorderPane mit 4 Spalten fuer die 4 Histone und ihren moeglichen Sites, sowie Modificationen an diesen.
     * Diese Moeglichkeiten sind allerdings nicht verbindlich und koennen ohne Problemee erweitert werden
     */
    public ModificationTable(Chromosom chromosom) {
        this.chromosom = chromosom;
        //Fuer die Reihenfolge der Histone
        String histoneOrder[] = {"H2A", "H2B", "H3", "H4"};
        
        histoneModificationMap = new HashMap<>();
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        
        //Die Histone einzeln, der Reieh nach hinzufuegen
        for(String histone : histoneOrder) {
            HistoneTable histonePane = new HistoneTable(histone);
            hbox.getChildren().add(histonePane);
            histoneModificationMap.put(histone, histonePane);
        }
        
        setCenter(hbox);
    }

    /**
     * @return the histoneMap
     */
    public HashMap<String, HistoneTable> getHistoneMap() {
        return histoneModificationMap;
    }
    

    /**
     * Liest alle Sites und Modifikationen aus und schreibt alles als Liste in eine Textdatei, die vorher ausgewählt wird
     * @param saveFile 
     */
    public File save(File saveFile) {
        
        if(saveFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Modification File");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            saveFile = fileChooser.showSaveDialog(new Stage());
        }

        try (//Erstellen eines neuen FileWriters
           BufferedWriter fileWriter = new BufferedWriter(new FileWriter(saveFile));){

            String ausgabe = "";

            for(HistoneTable histone : getHistoneMap().values()) {
                ausgabe += histone.title + "\n";
                ausgabe += histone.getHistoneString() + "\n";
            }

            fileWriter.write(ausgabe);
            fileWriter.flush();

            fileWriter.close();
        }
        catch ( IOException e ) {
                e.printStackTrace();
        }
        
        return saveFile;
    }
    
    /**
     * Liest eine txt-Datei ein und fügt entsprechende Sites und Modifikationen hinzu. 
     * Die Datei wird vorer in einem Dialog ausgewählt
     * @param file 
     */
    public File load(File file) {
        
        if(file == null || !file.canRead()) {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            fileChooser.setTitle("Open Modification File");
            file = fileChooser.showOpenDialog(new Stage());
            
            if(file == null || !file.canRead()) {
                return null;
            }
        }
             
        String line = "";
        HistoneTable histone = null;
        
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))){
                //Filereader um aus der Datei lesen zu können
                
                //Solange noch Einträge vorhanden sind, sollen diese in die Liste aufgenommen werden
                while((line = fileReader.readLine()) != null){
                    if(line.equals("")) {
                        continue;
                    }
                    
                    if(line.startsWith("H")) {
                        for(HistoneTable table : getHistoneMap().values()) {
                            if(table.title.equals(line)) {
                               histone = table;
                               break;
                            }
                        }
                    }
                         
                    String site;
                    String modString;
                    
                    if(line.contains("{")) {
                        
                        String splitArray[] = line.split("\\{");
                        site = splitArray[0];
                        modString = splitArray[1].replaceAll("\\}", "");
                        
                        ModificationList mod = new ModificationList();
                        
                        if(modString.contains(";")) {
                        String modSplit[] = modString.split(";");
                            for(String modification : modSplit) {
                                mod.addRow(modification);
                            }
                        }
                        else {
                             mod.addRow(modString);
                        }
                        
                        if(histone != null) {
                            histone.addRow(site, mod);
                        }
                    }
                }

                fileReader.close();

        } catch (IOException e) {
                e.printStackTrace();
        }
        
        //Nur wenn die Datei tatsächlich Informationen zu den Modifikationen enthalten hat, wird mit dieser Datei fortgefahren.
        if(histone != null) {
            chromosom.setModificationTableFile(file);
            return file;
        }
        else {
            return null;
        }
    }
}
