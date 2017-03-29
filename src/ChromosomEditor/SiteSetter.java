/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
public class SiteSetter extends EditorPane{

    int maxX = 2;
    int maxY = 2;
    GridPane rightInnerGrid;
    
    String[] histoneArray = {"H2A", "H2B", "H3", "H4"};
    
    GridPane outerGrid;
    int anzahl = 1;
    
    ChromosomEditor chromosomEditor;

    public static SiteBorderPane clipboard;
    public static SiteBorderPane zwischen;
    public static GridPane zwischenGrid;
    public static GridPane clipBoardGrid;
    public static int zwischenX;
    public static int zwischenY;
    public static String zwischenText;
    public static int clipX;
    public static int clipY;
    private boolean sameHistoneMap = true;
    private boolean atLeast;
    
    private ArrayList<GridPane> gridList;
    
    public SiteSetter(ChromosomEditor chromosomEditor) {
        this.paneName = "hm";
        this.paneFullName = "Histone Map";
        this.chromosomEditor = chromosomEditor;
    }
    
    public void initGUI() {
        gridList = new ArrayList<>();
        
        AnchorPane topPane = new AnchorPane();
        Label top = new Label(paneFullName);
        top.setFont(new Font(top.getFont().getName(), 16));
        topPane.getChildren().add(top);
        AnchorPane.setBottomAnchor(top, 10.0);
        setTop(topPane);
        
        outerGrid = new GridPane();

        int x = 0;
        int y = 0;
        
        if(chromosomEditor.getChromosomProject().getHistoneMap() != null) {
            int newMaxX = 0;
            int newMaxY = 0;
            
            for(int i = 0; i < histoneArray.length; i++) {
                String title = histoneArray[i];
                
                if(chromosomEditor.getChromosomProject().getHistoneMap().containsKey(title)) {
                    String[][] entries = chromosomEditor.getChromosomProject().getHistoneMap().get(title);
                    
                    if(entries != null) {
                        if(entries.length > newMaxX) {
                            newMaxX = entries.length;
                        }

                        if(entries[0].length > newMaxY) {
                            newMaxY = entries[0].length;
                        }

                        SiteBorderPane innerPane = new SiteBorderPane(title, this, outerGrid, entries);
                        addDnD(innerPane);
                        gridList.add(innerPane.getInnerGrid());

                        outerGrid.add(innerPane, x, y);

                        innerPane.setX(x);
                        innerPane.setY(y);
                    }
                    else {
                        SiteBorderPane innerPane = new SiteBorderPane(title, this, chromosomEditor.getOptions().getStorage());
                        addDnD(innerPane);
                        chromosomEditor.getOptions().getStorage().add(innerPane, x, y);
                        innerPane.showGrid(false);
                        gridList.add(innerPane.getInnerGrid());

                        innerPane.setX(x);
                        innerPane.setY(y);

                        SiteBorderPane empty = new SiteBorderPane("", this, outerGrid);
                        empty.showGrid(false);
                        outerGrid.add(empty, x, y);
                        addDnD(empty);

                        empty.setX(x);
                        empty.setY(y);
                    }
                }
                else {
                    SiteBorderPane innerPane = new SiteBorderPane(title, this, chromosomEditor.getOptions().getStorage());
                    addDnD(innerPane);
                    chromosomEditor.getOptions().getStorage().add(innerPane, x, y);
                    innerPane.showGrid(false);
                    gridList.add(innerPane.getInnerGrid());
                    
                    innerPane.setX(x);
                    innerPane.setY(y);
                    
                    SiteBorderPane empty = new SiteBorderPane("", this, outerGrid);
                    empty.showGrid(false);
                    outerGrid.add(empty, x, y);
                    addDnD(empty);
                    
                    empty.setX(x);
                    empty.setY(y);
                }
                
                if((i+1)%2 == 0) {
                    y++;
                    x = 0;
                }
                else {
                    x++;
                }
            }
            
            chromosomEditor.getOptions().getRowListener().changed(chromosomEditor.getOptions().getRowSpinner().valueProperty(), chromosomEditor.getOptions().getRowSpinner().valueProperty().getValue(), newMaxY);
            chromosomEditor.getOptions().getRowSpinner().getValueFactory().setValue(newMaxY);
            chromosomEditor.getOptions().getColListener().changed(chromosomEditor.getOptions().getColSpinner().valueProperty(), chromosomEditor.getOptions().getColSpinner().valueProperty().getValue(), newMaxX);
            chromosomEditor.getOptions().getColSpinner().getValueFactory().setValue(newMaxX);
            
            chromosomEditor.getChromosomProject().setHistoneMap(fillHistoneMap());
        }
        else {
            for(int i = 0; i < histoneArray.length; i++) {
                String title = histoneArray[i];

                SiteBorderPane innerPane = new SiteBorderPane(title, this, outerGrid);

                addDnD(innerPane);

                gridList.add(innerPane.getInnerGrid());

                outerGrid.add(innerPane, x, y);
                innerPane.setX(x);
                innerPane.setY(y);

                if((i+1)%2 == 0) {
                    y++;
                    x = 0;
                }
                else {
                    x++;
                }
            }
        }



        setCenter(outerGrid);

        VBox bottomBox = new VBox();

        bottomBox.setPadding(new Insets(50,10,10,10));
        bottomBox.setSpacing(10);

//            bottomBox.setAlignment(Pos.TOP_CENTER);

//            Label modificationTabelLab = new Label("ModificationTable");
//            modificationTabelLab.setOnMouseClicked(e -> {
//                chromosomEditor.getChromosom().openModificationDialog();
//            });

        Hyperlink modificationLink = new Hyperlink();
        modificationLink.setText("Modification Table file");
        modificationLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                chromosomEditor.getChromosom().openModificationDialog();
            }
        });

        Hyperlink colorLink = new Hyperlink();
        colorLink.setText("Color Theme");
        colorLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                chromosomEditor.getChromosom().openColorDialog();
            }
        });

        GridPane colorGrid = new GridPane();
        GridPane modGrid = new GridPane();

        modGrid.setHgap(1);
        modGrid.add(new Label("To set the Modification Table choose a"), 0,0);
        modGrid.add(new Label("(Default Modification Table is Mammal)"), 0,1);
        modGrid.add(modificationLink, 1,0);

        colorGrid.setHgap(1);
        colorGrid.add(new Label("To set the Modification Colors choose a"), 0,0);
        colorGrid.add(new Label("(Default Theme is: Theme 1)"), 0,1);
        colorGrid.add(colorLink, 1,0);

        bottomBox.getChildren().addAll(modGrid, colorGrid);

        setBottom(bottomBox);
    }
    
    @Override
    public boolean next() {
        chromosomEditor.getChromosomProject().setHistoneMap(fillHistoneMap());
        
        if(atLeast == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("At least one histone with one site is needed.");
            alert.setContentText("Please correct it. \n");
            alert.showAndWait();

            return false;
        }
        
        try {
            File tempFile = File.createTempFile("temp-histonemap", ".cfg");
            tempFile.deleteOnExit();
            return writeToFile(tempFile);
            
        } catch (IOException ex) {
            Logger.getLogger(SiteSetter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean save() {
        if(next()) {
            return saveHistoneMap();
        }
        return false;
    }
    
    @Override
    public void back() {
        this.chromosomEditor.reset();
        this.chromosomEditor.getChromosom().backToMenu();
    }
    
    @Override
    public void reset() {
        this.chromosomEditor.getChromosom().getProject().setHistoneMap(null);
    }
    
    private boolean saveHistoneMap() {
        File saveFile = null;
        
        if(saveFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save HistoneMap to File");
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
    
    public Element createHistoneOrderElement(Document doc) {
        Element histoneElement = chromosomEditor.addXMLElement(doc.getDocumentElement(), "histoneOrder");
        
        for(String key : chromosomEditor.getChromosomProject().getHistoneMap().keySet()) {
            if(chromosomEditor.getChromosomProject().getHistoneMap().get(key) != null) {
                String[][] array = chromosomEditor.getChromosomProject().getHistoneMap().get(key);
                Element histone = chromosomEditor.addXMLElement(histoneElement, "histone");
                histone.setAttribute("maxX", String.valueOf(array.length));
                histone.setAttribute("name", key);
                for(int y = 0; y < array[0].length; y++) {
                    for(int x = 0; x < array.length; x++) {
                        Element site = chromosomEditor.addXMLElement(histone, "site");
                        site.setAttribute("name", array[x][y]);
                        site.setAttribute("pos", x + "|" + y);
                    }
                }
            }
            else {
                Element histone = chromosomEditor.addXMLElement(histoneElement, "histone");
                histone.setAttribute("name", key);
            }
        }
        
        return histoneElement;
    }
    
    @Override
    public boolean writeToFile(File saveFile) {
        try {
            Document doc = ChromosomEditor.createNewXMLDocument("histoneMap");
                   
            createHistoneOrderElement(doc);

            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            //Format, Zeilenumbrüche, Einzüge festlegen
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(saveFile);
            
            transformer.transform(source, result);
            
            System.out.println("File saved!");
            chromosomEditor.getChromosomProject().setHistoneMapFile(saveFile);
            return true;
            
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(EditorRuleDesigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(EditorRuleDesigner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    

    
    public LinkedHashMap<String, String[][]> fillHistoneMap() {
        LinkedHashMap<String, String[][]> histoneMap = new LinkedHashMap<>();
        atLeast = false;
        
        SiteBorderPane sitePane;
        
        Stack<String> sideBorderPaneTitleList = new Stack<>();
        for(Node nod : chromosomEditor.getOptions().getStorage().getChildren()) {
            if(nod instanceof SiteBorderPane) {
                SiteBorderPane siteTmp = (SiteBorderPane)nod;
                if(!siteTmp.getTitle().equals("")) {
                    sideBorderPaneTitleList.push(siteTmp.getTitle());
                }
            }
        }
        
        int x = 0, y = 0;
        
        for(int i = 0; i < 4; i++) {
            for(Node nod : outerGrid.getChildren()) {
                if(nod instanceof SiteBorderPane) {
                    sitePane = (SiteBorderPane)nod;
                    
                    if(outerGrid.getColumnIndex(sitePane) == x &&
                        outerGrid.getRowIndex(sitePane) == y) {
                        
                        if(!sitePane.getTitle().equals("")) {
                            String[][] entries = sitePane.getEntries();
                            if(entries != null) {
                                atLeast = true;
                            }
                            histoneMap.put(sitePane.getTitle(), entries);
                        }
                        else {
                            histoneMap.put(sideBorderPaneTitleList.pop(), null);
                        }
                    }
                }
            }
            
            if((i+1) % 2 == 0) {
                y++;
                x = 0;
            }
            else {
                x++;
            }
        }
        
        sameHistoneMap = true; // test if the histonemap has changed
        LinkedHashMap<String, String[][]> oldHistoneMap = chromosomEditor.getChromosomProject().getHistoneMap();
        
        if(oldHistoneMap != null) {
            for(int index = 0; index < histoneMap.size(); index++) {

                String[][] entries = (String[][]) histoneMap.values().toArray()[index];
                
                String[][] oldEntries = null;
                if(oldHistoneMap.values().toArray().length >= histoneMap.values().toArray().length) {
                    oldEntries = (String[][]) oldHistoneMap.values().toArray()[index];
                }
                
                if(entries != null) {
                    if(oldEntries != null && sameHistoneMap){ // check if the entries in histone map are the same
                        for(int it = 0; it < entries[0].length; it++){
                            for(int ot = 0; ot < entries.length; ot++) {
                                if(!entries[ot][it].equals(oldEntries[ot][it])) { 
                                    sameHistoneMap = false;
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        sameHistoneMap = false;
                    }
                }
            }
        }
        
        return histoneMap;
    }

    /**
     * @return the gridList
     */
    public ArrayList<GridPane> getGridList() {
        return gridList;
    }

    /**
     * @param gridList the gridList to set
     */
    public void setGridList(ArrayList<GridPane> gridList) {
        this.gridList = gridList;
    }

    public void addDnD(SiteBorderPane innerPane) {
        innerPane.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/

                /* allow any transfer mode */
                Dragboard db = startDragAndDrop(TransferMode.ANY);
    //
    //                        /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(innerPane.getClass().getName());
                db.setContent(content);

                clipboard = innerPane;

//                        if(((SiteBorderPane)event.getSource()).getParent() instanceof javafx.scene.layout.GridPane && outerGrid != ((SiteBorderPane)event.getSource()).getParent()) {
//                            setOuterGrid((GridPane)((SiteBorderPane)event.getSource()).getParent());
//                        }

                clipBoardGrid = innerPane.getOuterGrid();
                clipX = innerPane.getX();
                clipY = innerPane.getY();

                event.consume();
            }
        });

        innerPane.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
    //                        System.out.println("onDragOver");
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != null &&
                        event.getDragboard().hasString() &&
                            event.getDragboard().getString().equals("ChromosomEditor.SiteBorderPane") &&
                                innerPane != clipboard) {

    //                            /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY);
                }

                event.consume();
            }
        });

        innerPane.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */

    //                        System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
    //                        if (event.getGestureSource() != text)
    //                                event.getDragboard().hasString()) {
    //        //                    textField.seColor.GREEN);
    //                        }

                event.consume();
            }
        });

        innerPane.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
    //                textField.setFill(Color.BLACK);
    //                        System.out.println("onDragExited");
                event.consume();
            }
        });

        innerPane.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
    //                        System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {

                    zwischen = innerPane;

    //                        System.err.println((SiteBorderPane)event.getSource().getParent().getClass().getName());
    //                        System.err.println(outerGrid.getClass().getName());

    //                            if(((SiteBorderPane)event.getSource()).getParent() instanceof javafx.scene.layout.GridPane && outerGrid != ((SiteBorderPane)event.getSource()).getParent()) {
    //                                setOuterGrid((GridPane)((SiteBorderPane)event.getSource()).getParent());
    //                            }

                    zwischenGrid = innerPane.getOuterGrid();
                    zwischenX = innerPane.getX();
                    zwischenY = innerPane.getY();

                    zwischenGrid.getChildren().remove(zwischen);
                    clipBoardGrid.getChildren().remove(clipboard);

                    if(zwischenGrid != clipBoardGrid) {
                        clipboard.showGrid(!clipboard.isShowGrid());
                    }
                    zwischenGrid.add(clipboard, zwischenX, zwischenY);
                    clipboard.setX(zwischenX);
                    clipboard.setY(zwischenY);

                    success = true;

                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.COPY) {
                    if(zwischenGrid != clipBoardGrid) {
                        zwischen.showGrid(!zwischen.isShowGrid());
                    }
                    clipBoardGrid.add(zwischen, clipX, clipY);
                    zwischen.setX(clipX);
                    zwischen.setY(clipY);
    //                        boolean bool = parent.getZwischen().showGrid;
    //                        
    //                        parent.getZwischen().showGrid(parent.getClipboard().showGrid);
    //                        parent.getClipboard().showGrid(bool);

                    GridPane pane = zwischenGrid;
                    zwischen.setOuterGrid(clipBoardGrid);
                    clipboard.setOuterGrid(pane);
                }

                event.consume();
            }
        });
    }

    /**
     * @return the sameHistoneMap
     */
    public boolean isSameHistoneMap() {
        return sameHistoneMap;
    }

    /**
     * @param sameHistoneMap the sameHistoneMap to set
     */
    public void setSameHistoneMap(boolean sameHistoneMap) {
        this.sameHistoneMap = sameHistoneMap;
    }

}