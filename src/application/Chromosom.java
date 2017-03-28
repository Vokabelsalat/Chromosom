package application;

import ChromosomEditor.ChromosomEditor;
import ChromosomEditor.EditorEnzyme;
import ChromosomEditor.EditorRule;
import ChromosomEditor.ModificationTable;
import HeatChromosom.HeatProject;
import Nukleosom.BigNukleosomNew;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import Nukleosom.BigNukleosomRow;
import NukleosomReader.NukleosomReader;
import NukleosomVase.NukleosomVaseGrid;
import SunburstNukleosom.SunburstNukleosomRow;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javax.imageio.ImageIO;
import Nukleosom.ChromosomTree;
import Nukleosom.ZoomableScrollPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

/**
 * Startklasse des Programms
 * Enthält die wichtigesten Elemente und Einstellungen des Programms
 * @author Jakob
 */

public class Chromosom extends Application {
    private static int maxTimeSteps;
    private static int offset;
    private static String fileName;
    private BorderPane rootLayout;
    private ChromosomProject project;
    public double screenWidth;
    public double screenHeight;
    private BigNukleosomRow row;
    private SunburstNukleosomRow sun;
    private NukleosomVaseGrid vase;
    private TabPane tabPane;
    private Stage newStage;
    private Tab nukleosomeTab;
    private Tab heatTab;
    private OptionsPanel options;
    public ScrollPane sb;
    public ChromosomTree tree;
    private BorderPane nukleosomBorderPane;
    private ScrollPane sp;
    private Button heatPlus;
    int heatCounter = 0;
    private SplitPane splitPane;
    private ChromosomMenu chromosomMenu;
    private Stage primaryStage;
    HashMap<String, Color> colorMap;
    private BigNukleosomRow secondRow;
    private MenuItem saveAsPNG;
    
    //Unbedingt auslagern in ein "Project"
    private File modificationTableFile = new File("MammalModificationTable.txt");
    private File histSimFile;
    private File confFile;
    
    public ArrayList<HeatProject> projectList;
    
    public boolean sameRow, sameColumns;
    private HeatProject heatProject;
    
    private ArrayList<EditorRule> editorRuleList;
    private LinkedHashMap<String, EditorEnzyme> enzymeMap;
    private ZoomableScrollPane plusMinusScroll;
    private ZoomableScrollPane scroll;
    public ArrayList<Integer> testList;
    private MenuItem openModTable;
    private MenuItem setModificationColors;
    
    private Label stepwidthLabel = new Label("Maximum steps to show:");
    private TextField stepwidthText = new TextField();
    private Button saveButton = new Button("Apply");
    private Button cancelButton = new Button("Cancel");
    
    private int stepsToShowValue = 50;
            
    /**
     * Startet die Applikation
     * @param primaryStage Die Haupt-Stage des Programms
     */
    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        
        initGUI(primaryStage);
        
        projectList = new ArrayList<>();
        
        MenuBar menuBar = createMenu(primaryStage);
        rootLayout.setTop(menuBar);

//        primaryStage.setMaximized(true);

//        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
////                primaryStage.sizeToScene();
//                    if((double)newSceneWidth < primaryStage.getScene().getWidth()) {
//                        primaryStage.sizeToScene();
//                    }
//            }
//            
//        });
//        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//                    if((double)newSceneHeight < primaryStage.getScene().getHeight()) {
//                        primaryStage.sizeToScene();
//                    }
//            }
//        });

        chromosomMenu = startChromosomsMenu();
        chromosomMenu.setPadding(new Insets(100,100,100,100));
        project = chromosomMenu.getChromosomProject();
        
        Scene scene = new Scene(getRootLayout());
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMaxHeight(screenHeight);
        primaryStage.setMaxWidth(screenWidth);
        primaryStage.sizeToScene();
        show(primaryStage);
        
        setConfFile(new File("conf.txt"));
        if(getConfFile().exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(getConfFile()));
                String line = "";
                while((line = br.readLine()) != null) {
                  if(line.startsWith("histSimFile") && line.contains(":")) {
                      String filePath = line.split(":")[1];
                        if(filePath.endsWith("HistSim")) {
                            histSimFile = new File(filePath);
                        }
                  }  
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            try {
                setConfFile(new File("conf.txt"));
                getConfFile().createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    /**
     * Return back to the main menu from the beginning and undo all the settings
     */
    public void backToMenu() {
        rootLayout.getChildren().removeAll(rootLayout.getChildren());
        primaryStage.close();
        nukleosomeTab = null;
        primaryStage = null;
        System.gc();
        primaryStage = new Stage();
        
        project = null;
        
        editorRuleList = new ArrayList<>();
        
        this.start(primaryStage);
        
//      rootLayout.setCenter();
    }
    
    /**
     * Starte die Visualisierung der Nucleosomes
     * @param project Das ChromosomProject, welches alle wichtigen Einstellungen für den Anwendungsfall enthält
     */
    public void startChromosom(ChromosomProject project) {
        
        this.setProject(project);

        project.maxTimeSteps.push(maxTimeSteps);
        project.offset.push(offset);
         
        NukleosomReader nukleosomReader = new NukleosomReader(project);
        nukleosomReader.openFile(project.getOutputFile());
        sb = new ScrollPane();
        nukleosomeTab = createNuclosomeTab();
        tabPane.getTabs().add(nukleosomeTab);
        rootLayout.setCenter(tabPane);
        
    }

        /**
         * Startet die Ansicht um zwei HeatNucleosomes zu vergleichen
         */
        private void startTwoHeatChromosom() {
            VBox vbox = new VBox();

            splitPane = new SplitPane();

            splitPane.setOrientation(Orientation.VERTICAL);

            Button plus = new Button("+");

            heatPlus = new Button("+");

            heatPlus.setOnAction((ActionEvent actionEvent) -> {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(primaryStage);

                if(file != null) {
                    startHeatChromosom(file);
                    heatCounter++;
                    rootLayout.getChildren().remove(vbox);
                    primaryStage.centerOnScreen();
                    primaryStage.sizeToScene();
                }
            });

            vbox.setSpacing(5);
            vbox.getChildren().addAll(new Label("Chromosom:"), plus, new Separator(), new Label("HeatChromosom:"), heatPlus);

            rootLayout.setLeft(vbox);
    }
    
    /**
     * Startet die Ansicht um eine HeatNucleosomesmap anzuzeigen
     * @param file Datei welche als Datengrundlage genutzt werden soll
     */
    private void startHeatChromosom(File file) {
        
        HeatProject heatProject = new HeatProject(this, heatCounter);
        
        projectList.add(heatProject);
        
        sameRow = false;
        sameColumns = false;
        
        GridPane pane = new GridPane();
        
        splitPane.getItems().add(heatProject.createHeatMainPanel());
        
        if(heatCounter < 1) {
            splitPane.getItems().add(heatPlus);
        }
        else {
            splitPane.getItems().remove(heatPlus);
            splitPane.setDividerPosition(0, 0.5);
            
            for(HeatProject proj : projectList) {
                proj.setTwoHeatMaps(true);
                proj.getHeatLegend().getTimePairButton().setVisible(true);
                proj.getHeatOptionsPanel().getPairButton().setVisible(true);
            }
        }
        
        pane.add(splitPane, 1, 0);

        pane.setGridLinesVisible(true);
        
        getRootLayout().setCenter(pane);
    }
    
    /**
     * Erstellt den Tab für die HeatNucleosomes
     * @param heatProject Das entsprechende HeatProject, welches die Einstellungen enthält
     */
    public void startHeatNukleosomeTab(HeatProject heatProject) {
        
        this.setHeatProject(heatProject);
        
        if(heatProject.getHeatReader().getTimeMap() != null && heatProject.getHeatReader().getTimeMap().size() > 0) {
            projectList.add(heatProject);

            sameRow = false;
            sameColumns = false;

            heatTab = new Tab();
            heatTab.setText("HeatNucleosomes");

            heatTab.setContent(heatProject.createHeatMainPanel());
            heatTab.setClosable(false);

            tabPane.getTabs().add(heatTab);
        }
    }
    
    private void show(Stage primaryStage) {
        primaryStage.show();
    }
    
    
    int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null){
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }

    /**Erstellt den Nucleosomes-Tab
     * 
     * @return Den erstellten Tab
     */
    private Tab createNuclosomeTab() {
        BorderPane plusMinusBorderPane = new BorderPane();
        BorderPane nuklBorder = new BorderPane();
        Tab nukleosomeTab = new Tab();
        nukleosomeTab.setText("Nucleosomes");
        setOptions(new OptionsPanel(getProject()));
       
        setRow(new BigNukleosomRow(getProject(), getProject().getNukleosomWidth(), getProject().getNukleosomHeight(), getProject().maxTimeSteps.peek(), getProject().stepSize.peek(), false));
        
        for(int i = 0; i < getRowCount(getRow()); i++) {
            RowConstraints row1 = new RowConstraints();
            row1.setVgrow(Priority.NEVER);
            getRow().getRowConstraints().add(row1); 
        }
        
        for(Node nod : getRow().getChildren()) {
            if(GridPane.getColumnIndex(nod) == getProject().getTimeVector().get(String.valueOf(getProject().offset.peek())).size()) {
                nod.setOpacity(0.0);
                nod.setVisible(false);
                nod.maxWidth(4.0);
                nod.setScaleX(0.3);
            }
        }
        
        secondRow = null;
        secondRow = new BigNukleosomRow(getProject(), getProject().getNukleosomWidth(), getProject().getNukleosomHeight(), getProject().maxTimeSteps.peek(), getProject().stepSize.peek(), true);

        secondRow.getUp().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                secondRow.goUp();
                getRow().goUp();
                if(secondRow.isFoundFirst() == false) {
                    plusMinusBorderPane.setTop(secondRow.getUp());
                    BorderPane.setAlignment(secondRow.getUp(), Pos.CENTER);
                    nuklBorder.setTop(getRow().getUp());
                    getRow().getUp().setVisible(false);
                }
                else {
                    plusMinusBorderPane.setTop(null);
                    nuklBorder.setTop(null);
                }
                
                if(getProject().stepSize.peek() == 1) {
                    for(BigNukleosomNew nukleo : getRow().getNuklList()) {
                        if(Integer.parseInt(getProject().getMetaInformations().get(String.valueOf(nukleo.getY()))[1]) == nukleo.getX() && nukleo.isShowLabels() == false) {
                            nukleo.impl_processCSS(true);
                            nukleo.highlight(0, nukleo.getWidth(), nukleo.getHeight());
                        }
                    }
                }
            }
        });
        
        secondRow.getDown().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                secondRow.goDown();
                getRow().goDown();
                
                if(secondRow.isFoundLast() == false) {
                    plusMinusBorderPane.setBottom(secondRow.getDown());
                    BorderPane.setAlignment(secondRow.getDown(), Pos.CENTER);
                    nuklBorder.setBottom(getRow().getDown());
                    getRow().getDown().setVisible(false);
                }
                else {
                    plusMinusBorderPane.setBottom(null);
                    nuklBorder.setBottom(null);
                }
                if(getProject().stepSize.peek() == 1) {
                    for(BigNukleosomNew nukleo : getRow().getNuklList()) {
                        if(Integer.parseInt(getProject().getMetaInformations().get(String.valueOf(nukleo.getY()))[1]) == nukleo.getX() && nukleo.isShowLabels() == false) {
                            nukleo.impl_processCSS(true);
                            nukleo.highlight(0, nukleo.getWidth(), nukleo.getHeight());
                        }
                    }
                }
            }
        });
        
        double high = 0;

        if(getProject().stepSize.peek() == 1) {
            for(BigNukleosomNew nukleo : getRow().getNuklList()) {
                if(Integer.parseInt(getProject().getMetaInformations().get(String.valueOf(nukleo.getY()))[1]) == nukleo.getX() && nukleo.isShowLabels() == false) {
                    nukleo.impl_processCSS(true);
                    nukleo.highlight(0, nukleo.getWidth(), nukleo.getHeight());
                    if(nukleo.prefHeight(-1)>high) {
                        high = nukleo.prefHeight(-1);
                    }
                }
            }
        }
        
        for(int i = 0; i < getRowCount(secondRow); i++) {
            RowConstraints row1 = new RowConstraints();
            row1.setVgrow(Priority.NEVER);
            row1.setMinHeight(high);
            secondRow.getRowConstraints().add(row1); 
        }

        ArrayList nodeList = new ArrayList<>();
        for(Node nod : getSecondRow().getChildren()) {
            if(GridPane.getColumnIndex(nod) != 0) {
                nodeList.add(nod);
                nod.setManaged(false);
            }
        }
        getSecondRow().getChildren().removeAll(nodeList);
        
        BorderPane border = new BorderPane();
        nukleosomBorderPane = new BorderPane();
        
        StackPane stack = new StackPane();
        stack.getChildren().add(getRow());
        stack.getChildren().add(getRow().getEnzymeGroup());
        
        border.setCenter(stack);
        border.setStyle("-fx-background-color: white;");
        
        scroll = new ZoomableScrollPane(border);
        
        getRow().setPadding(new Insets(10,0,10,0));

        BorderPane plusMinusBorder = new BorderPane();
        plusMinusBorder.setCenter(secondRow);
        setPlusMinusScroll(new ZoomableScrollPane(plusMinusBorder));
        
        getPlusMinusScroll().setPadding(new Insets(10,5,10,10));
        
        plusMinusScroll.setId("address");

        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        plusMinusScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        plusMinusScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scroll.vvalueProperty().addListener(new ChangeListener<Number>() {
          public void changed(ObservableValue<? extends Number> ov,
              Number old_val, Number new_val) {
                  plusMinusScroll.setVvalue(new_val.doubleValue());
          }
        });
        plusMinusScroll.vvalueProperty().addListener(new ChangeListener<Number>() {
          public void changed(ObservableValue<? extends Number> ov,
              Number old_val, Number new_val) {
                scroll.setVvalue(new_val.doubleValue());
          }
        });
        
        if(secondRow.isFoundFirst() == false) {
            secondRow.getUp().setAlignment(Pos.CENTER);
            BorderPane.setAlignment(secondRow.getUp(), Pos.CENTER);
            plusMinusBorderPane.setTop(secondRow.getUp());
            nuklBorder.setTop(getRow().getUp());
            getRow().getUp().setVisible(false);
        }
        if(secondRow.isFoundLast() == false) {
            secondRow.getDown().setAlignment(Pos.CENTER);
            BorderPane.setAlignment(secondRow.getDown(), Pos.CENTER);
            plusMinusBorderPane.setBottom(secondRow.getDown());
            nuklBorder.setBottom(getRow().getDown());
            getRow().getDown().setVisible(false);
        }
        
        plusMinusBorderPane.setCenter(getPlusMinusScroll());
        plusMinusBorderPane.setPadding(new Insets(10,0,10,0));
        nuklBorder.setCenter(scroll);
        nuklBorder.setPadding(new Insets(10,0,10,0));
        
        nukleosomBorderPane.setLeft(plusMinusBorderPane);
        nukleosomBorderPane.setCenter(nuklBorder);
        
        nukleosomBorderPane.setRight(getOptions());
        
        nukleosomBorderPane.setPadding(new Insets(10,10,10,10));
        nukleosomeTab.setContent(nukleosomBorderPane);
        nukleosomBorderPane.setMaxWidth(screenWidth);
        nukleosomBorderPane.setMaxHeight(screenHeight);
        nukleosomeTab.setClosable(false);
        
        /* Hier werde die vershciedenen Tabs der NucleosomeVases erstellt
        
        Tab sunburstTab = new Tab();
        sunburstTab.setText("SunburstNukleosom");
        
        findNukleosomResulution(sunburstTab.getText());
        
        //		sun = new SunburstNukleosomRow(project, 45, 30, project.getSunburstWidth(), project.getSunburstHeight());
        sun = new SunburstNukleosomRow(project, 10, 10, project.getSunburstWidth(), project.getSunburstHeight());
        
        ScrollPane sb2 = new ScrollPane();
        sb2.setContent(sun);
        
        sunburstTab.setContent(sb2);
        sunburstTab.setClosable(false);
        tabPane.getTabs().add(sunburstTab);
        
        Tab vaseTab = new Tab();
        vaseTab.setText("Nukleosom Vase ROW");
        ScrollPane sb3 = new ScrollPane();
        vase = new NukleosomVaseGrid(project,"ROW",10,100,6,10);
        //                vase = new NukleosomVaseGrid(project,"ROW",1,1,6,10);
        sb3.setContent(vase);
        vaseTab.setContent(sb3);
        vaseTab.setClosable(false);
        tabPane.getTabs().add(vaseTab);
        
        Tab vaseTab1 = new Tab();
        vaseTab1.setText("Nukleosom Vase BLOCK");
        ScrollPane sb4 = new ScrollPane();
        //		vase2 = new NukleosomVaseGrid(project,"BLOCK",100,20,6,10);
        vase2 = new NukleosomVaseGrid(project,"BLOCK",1,1,6,10);
        sb4.setContent(vase2);
        vaseTab1.setContent(sb4);
        vaseTab1.setClosable(false);
        tabPane.getTabs().add(vaseTab1);
        
        Tab vaseTab2 = new Tab();
        vaseTab2.setText("Nukleosom Vase Zeit");
        ScrollPane sb5 = new ScrollPane();
        //		vase3 = new NukleosomVaseGrid(project,"ZEIT",15,80,6,10);
        vase3 = new NukleosomVaseGrid(project,"ZEIT",1,1,6,10);
        sb5.setContent(vase3);
        vaseTab2.setContent(sb5);
        vaseTab2.setClosable(false);
        tabPane.getTabs().add(vaseTab2);
        
        Tab vaseTab3 = new Tab();
        vaseTab3.setText("Nukleosom Vase ZUSTAND");
        ScrollPane sb6 = new ScrollPane();
        //		vase4 = new NukleosomVaseGrid(project,"ZUSTAND",15,80,6,10);
        vase4 = new NukleosomVaseGrid(project,"ZUSTAND",1,1,6,10);
        sb6.setContent(vase4);
        vaseTab3.setContent(sb6);
        vaseTab3.setClosable(false);
        tabPane.getTabs().add(vaseTab3);*/
        
        if(getProject().stepSize.peek() == 1) {
            for(BigNukleosomNew nukleo : getRow().getNuklList()) {
                if(Integer.parseInt(getProject().getMetaInformations().get(String.valueOf(nukleo.getY()))[1]) == nukleo.getX() && nukleo.isShowLabels() == false) {
                    nukleo.impl_processCSS(true);
                    nukleo.highlight(0, nukleo.getWidth(), nukleo.getHeight());
                }
            }
        }
        
        this.primaryStage.sizeToScene();
        
        return nukleosomeTab;
    }
    
    private void initGUI(Stage primaryStage) {
        this.rootLayout = new BorderPane();
        primaryStage.setTitle("In Viso");
        screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        tabPane = new TabPane();
    }

    private MenuBar createMenu(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem openFile = new MenuItem("Open File");
        openFile.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.showOpenDialog(primaryStage);

        });
        
        
        MenuItem exportToSVG = new MenuItem("Export to SVG");
        exportToSVG.setOnAction(actionEvent -> {
            BorderPane pane = new BorderPane();
            Label label = new Label("Die Darstellung wird exportiert ...");
            Button but = new Button("Wollen Sie die Darstellung exportieren?");
            but.setOnAction(actionEvent2 -> {
                pane.getChildren().remove(but);
                pane.setCenter(new Label("Bitte warten!"));
                this.export("SVG");
                
            });


            pane.setCenter(but);
            Scene scene2 = new Scene(pane, 400, 200);
            newStage = new Stage();
            newStage.setScene(scene2);
            //tell stage it is meannt to pop-up (Modal)
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Export");

            newStage.show();

        });
        
        MenuItem exportToPNG = new MenuItem("Export to PNG");
        exportToPNG.setOnAction(actionEvent -> {

            Label label = new Label("Die Darstellung wird exportiert ...");
            Button but = new Button("Wollen Sie die Darstellung exportieren?");
            but.setOnAction(actionEvent2 -> {
                this.export("PNG");
            });

            BorderPane pane = new BorderPane();
            pane.setCenter(but);
            Scene scene2 = new Scene(pane, 200, 200);
            newStage = new Stage();
            newStage.setScene(scene2);
            //tell stage it is meannt to pop-up (Modal)
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Export");
            newStage.show();

        });
        
//        menu.getItems().add(exportToPNG);
        
        MenuItem exportToPDF = new MenuItem("Export to PDF");
        exportToPDF.setOnAction(actionEvent -> {

            Label label = new Label("Die Darstellung wird exportiert ...");
            Button but = new Button("Wollen Sie die Darstellung exportieren?");
            but.setOnAction(actionEvent2 -> {
                this.export("PDF");
            });

            BorderPane pane = new BorderPane();
            pane.setCenter(but);
            Scene scene2 = new Scene(pane, 200, 200);
            newStage = new Stage();
            newStage.setScene(scene2);
            //tell stage it is meannt to pop-up (Modal)
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Export");

            newStage.show();

        });
        
        setSaveAsPNG(new MenuItem("Take a Screenshot"));
        getSaveAsPNG().setOnAction(actionEvent -> {

            Label label = new Label("export ...");
            Button but = new Button("Export the visualization to PNG.");
            BorderPane pane = new BorderPane();
            but.setOnAction(actionEvent2 -> {
                pane.getChildren().remove(but);
                pane.setCenter(new Label("Please wait!"));
                if(tabPane.getSelectionModel().getSelectedIndex() == 0) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save Modification File");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
                    fileChooser.getExtensionFilters().add(extFilter);
                    File file = fileChooser.showSaveDialog(new Stage());
                    
                    SnapshotParameters snaps = new SnapshotParameters();
                    Scale scale = new Scale(3.0, 3.0, 0, 0);
                    snaps.setTransform(scale);

                    WritableImage image = getRow().snapshot(snaps, null);
                    
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                    } catch (IOException e) {
                        // TODO: handle exception here
                    }

                }
                else {
                    getHeatProject().saveAsPNG();
                }
                newStage.close();
            });

            pane.setCenter(but);
            Scene scene2 = new Scene(pane, 400, 200);
            newStage = new Stage();
            newStage.setScene(scene2);
            //tell stage it is meannt to pop-up (Modal)
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Export");
            newStage.show();

        });
        saveAsPNG.setDisable(true);
        menu.getItems().add(getSaveAsPNG());
        
        
        setOpenModTable(new MenuItem("Modification Table"));
        getOpenModTable().setOnAction(actionEvent -> {
            openModificationDialog();
        });
        
        menu.getItems().add(getOpenModTable());
        getOpenModTable().setDisable(true);
        
        setSetModificationColors(new MenuItem("Modification Colors"));
        getSetModificationColors().setOnAction(actionEvent -> {
            if(chromosomMenu.getChromosomEditor() == null) {
                chromosomMenu.setChromosomEditor(new ChromosomEditor(this, new ChromosomProject(this)));
                chromosomMenu.getChromosomEditor().showEditorPane(0, 0);
            }
            openColorDialog();
        });
        
        menu.getItems().add(getSetModificationColors());
        getSetModificationColors().setDisable(true);
        
        MenuItem options = new MenuItem("Options");
        options.setOnAction(actionEvent -> {
            openOptionsPanel();
        });
        
        menu.getItems().add(options);
        
        menu.getItems().add(new SeparatorMenuItem());
        
        MenuItem mainMenu = new MenuItem("Main Menu");
        mainMenu.setOnAction(actionEvent -> backToMenu());
        
        menu.getItems().add(mainMenu);
        
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(actionEvent -> Platform.exit());
        menu.getItems().add(exit);
        menuBar.getMenus().add(menu);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        return menuBar;
    }
    
    public void openOptionsPanel() {
//            Label label = new Label("Options");
            
            BorderPane pane = new BorderPane();
                  
            GridPane optionsGrid = new GridPane();
            optionsGrid.setAlignment(Pos.CENTER);
            optionsGrid.setHgap(10);
            optionsGrid.setVgap(10);
            
            optionsGrid.setPadding(new Insets(10,10,10,10));
            optionsGrid.add(stepwidthLabel, 0, 0);
            optionsGrid.add(stepwidthText, 1, 0);
            
            stepwidthText.setText(String.valueOf(getStepsToShowValue()));
            
            pane.setCenter(optionsGrid);
            
            saveButton.setOnAction(ae -> {
                int newSteps = Integer.parseInt(stepwidthText.getText());
                setStepsToShowValue(newSteps);
                
                if(nukleosomeTab != null) {
                    
                    newStage.close();
                    int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
                
                    if(project.maxTimeSteps.peek() == 0) {
                        project.maxTimeSteps.pop();
                        project.maxTimeSteps.push(getProject().timeVector.size());
                    }
                    int stepSize = getProject().timeVector.size();
                    int steps = getProject().timeVector.size();

                    ArrayList<Integer> testList = new ArrayList<>();
                    
                    while(stepSize > project.stepsToShow.peek()) {
                        stepSize = steps / project.stepsToShow.peek();
                        if(stepSize > 1) {
                            testList.add(stepSize);
                        }
                       steps = stepSize;
                    }

                    testList.add(1);
                    project.stepSize.clear();
                    for(int i = (testList.size()-1); i >= 0; i--) {
                        project.stepSize.push(testList.get(i));
                    }

                    nukleosomeTab = createNuclosomeTab();
                    tabPane.getTabs().remove(zahl);
                    tabPane.getTabs().add(zahl, nukleosomeTab);
                    tabPane.getSelectionModel().select(nukleosomeTab);
                    
                }
                
               newStage.close();
                
            });
            
            optionsGrid.add(saveButton, 0, 1);
            

//            pane.setCenter(label);
            Scene scene2 = new Scene(pane, 400, 200);
            newStage = new Stage();
            newStage.setScene(scene2);
            //tell stage it is meannt to pop-up (Modal)
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Options");
            newStage.show();
    }
    
    /**
     * Erzeugt eine neue Ansicht der Nuceleosomes mit einer erhöhten Nukleosomengröße und exportiert das Bild dann als PNG
     */
    public void saveAsPng() {
        
        int add = 4;
        
        zoomInNukleosoms(add);
        
        WritableImage image = getRow().snapshot(new SnapshotParameters(), null);
        
        // TODO: probably use a file chooser here
        File file = new File("PNGExport.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
        
        zoomOutNukleosoms(add);
        
        newStage.close();
    }

    public void exportToSVG(String fileName) {

        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        String selectedTabName = selectedTab.getText();
        getProject().setSelectedTabName(selectedTabName);
        if (fileName.equals("")) {
            fileName = selectedTabName + ".svg";
            newStage.close();
        }
        ChromosomExport.writeToFile(fileName);
    }

    /**
     * Übergelagerte Exportfunktion welche sich dann in verschiedene Formate unterteilt
     * @param format 
     */
    public void export(String format) {

        String selectedTabName = tabPane.getSelectionModel().getSelectedItem().getText();

        switch (selectedTabName) {
            case "Nukleosoms":
                ChromosomExport.exportBigNukleosomRow(getRow());
                break;
            case "SunburstNukleosom":
                ChromosomExport.exportSunburstNukleosomRow(sun);
                break;
            case "Nukleosom Vase ROW":
                ChromosomExport.exportNukleosomVaseGrid(vase);
                break;
        }
//                }
        String exportHelper = "exportHelper.svg";
        Process proc;
        try {
            switch (format) {
                case "PDF":
                    exportToSVG(exportHelper);
                    proc = proc = Runtime.getRuntime().exec("java -jar \"batik-1.7\\batik-rasterizer.jar\" -d " + (getProject().selectedTabName + ".pdf") + " -m application/pdf -scripts text/ecmascript -onload " + exportHelper);
                    proc.waitFor();
                    Files.delete(FileSystems.getDefault().getPath(exportHelper));
                    newStage.close();
                    break;
                case "PNG":
                    exportToSVG(exportHelper);
                    proc = Runtime.getRuntime().exec("java -jar \"batik-1.7\\batik-rasterizer.jar\" -d " + (getProject().selectedTabName + ".png") + " -m image/png -scripts text/ecmascript -onload " + exportHelper);
                    proc.waitFor();
                    Files.delete(FileSystems.getDefault().getPath(exportHelper));
                    newStage.close();
                    break;
                default:
                    exportToSVG(selectedTabName + ".svg");
                    break;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
        }

        newStage.close();

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Zoom in die Zeitschritte herein
     * @param newOffset Der neue Offset ab welchem die jeweilige Anzahl an Schritten angezeigt werden soll
     */
    void zoomIn(int newOffset) {
        int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
        getProject().stepsToShow.push(getProject().stepSize.peek());
        getProject().maxTimeSteps.push(getProject().stepSize.peek());
        getProject().reservoirStack.push(getProject().stepSize.pop());
//        getProject().stepSize.push(1);
        getProject().offset.push(newOffset);
        nukleosomeTab = null;
        System.gc();
        nukleosomeTab = createNuclosomeTab();
        tabPane.getTabs().remove(zahl);
        tabPane.getTabs().add(zahl, nukleosomeTab);
        tabPane.getSelectionModel().select(nukleosomeTab);
    }

    /**
     * Zoom aus der detaillierten Ansicht heraus in eine gröbere Auflösung der Zeitschritte
     */
    void zoomOut() {
        int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
        getProject().stepsToShow.pop();
        getProject().stepSize.push(getProject().reservoirStack.pop());
        getProject().offset.pop();
        getProject().maxTimeSteps.pop();
        nukleosomeTab = null;
        System.gc();
        nukleosomeTab = createNuclosomeTab();
        tabPane.getTabs().remove(zahl);
        tabPane.getTabs().add(zahl, nukleosomeTab);  
        tabPane.getSelectionModel().select(nukleosomeTab);
    }

    /**
     * Die Größe der Nukleosome wird erhöht
     * @param add Pixelanzahl um die die Größe einer einzelnen Site erhöht werden soll
     */
    public void zoomInNukleosoms(int add) {
        Scale scale = new Scale(1.2, 1.2, 0, 0);

        scroll.getZoomGroup().getTransforms().add(scale);
        plusMinusScroll.getZoomGroup().getTransforms().add(scale);

        plusMinusScroll.setMinWidth(secondRow.getBoundsInParent().getMaxX());
            
    }

    /**
     * Die Größe der Nukleosome wird verringert
     * @param add Pixelanzahl um die die Größe einer einzelnen Site verringert werden soll
     */
    public void zoomOutNukleosoms(int add) {
        Scale scale = new Scale(0.8, 0.8, 0, 0);

        scroll.getZoomGroup().getTransforms().add(scale);
        plusMinusScroll.getZoomGroup().getTransforms().add(scale);

        plusMinusScroll.setMinWidth(secondRow.getBoundsInParent().getMaxX());
        
    }
    
    /**
     * Erzeugt eine neue Anischt der Nukleosomes
     * @param newOffset Der neue Offset, ab welchem Zeitschritt die nächsten Schritte angezeigt werden sollen
     * @param newStepSize Die neue geänderte Schrittweite
     * @param newStepsToShow Neue Anzahl der angezeigten Schritte
     * @param newMaxTimeSteps Die Anzahl der Schritte die maximal angeziegt werden sollen
     */
    public void showChromosoms(int newOffset, int newStepSize, int newStepsToShow, int newMaxTimeSteps) {
        
        int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
        
        if(getProject().stepSize.size()>1) {
            getProject().stepSize.pop();
        }
        
        if(getProject().stepsToShow.size()>1)
            getProject().stepsToShow.pop();
                 
        if(getProject().offset.size()>1)  
            getProject().offset.pop();
        
        if(getProject().maxTimeSteps.size()>1)
            getProject().maxTimeSteps.pop();
        
        getProject().stepsToShow.push(newStepsToShow);
        getProject().maxTimeSteps.push(newMaxTimeSteps);
        getProject().stepSize.push(newStepSize);
        getProject().offset.push(newOffset);
        
        nukleosomeTab = createNuclosomeTab();
        tabPane.getTabs().remove(zahl);
        tabPane.getTabs().add(zahl, nukleosomeTab);
        
    }

    /**
     * @return the rootLayout
     */
    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public void openModificationDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setResizable(true);
        dialog.getDialogPane().setMaxSize(screenWidth-100, screenHeight-100);
        dialog.setWidth(screenWidth-100);
        dialog.setHeight(screenHeight-100);
        dialog.setTitle("Modification Table");
        
        ModificationTable table = new ModificationTable(this);
        File modificationFile;
        if((modificationFile = table.load(getModificationTableFile())) != null) {
            setModificationTableFile(modificationFile);
        }
        else {
            System.err.println("Not a vailid modification table. Continued without modification table.");
            return;
        }

        dialog.getDialogPane().setContent(table);
        dialog.setTitle("Modification Table - " + getModificationTableFile().getAbsolutePath());

        ButtonType buttonTypeSave = new ButtonType("Save");
        ButtonType buttonTypeSaveAs = new ButtonType("Save As");
        ButtonType buttonTypeLoad= new ButtonType("Load");
        ButtonType buttonTypeClose= new ButtonType("Close");

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeLoad, buttonTypeSave, buttonTypeSaveAs, buttonTypeClose);

        //Versteckten Close-Button hinzufügen, damit der x-Button funktioniert
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);
        
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == buttonTypeSave){
            setModificationTableFile(table.save(getModificationTableFile()));
            if(chromosomMenu.getChromosomEditor() != null) {
                chromosomMenu.findAndReadModificationTable(getModificationTableFile());
                if(chromosomMenu.getChromosomEditor().getRuleDesigner() != null) {
                    chromosomMenu.getChromosomEditor().getRuleDesigner().checkStateColors();
                }
            }
        }
        else if (result.get() == buttonTypeLoad){
            table = new ModificationTable(this);
            setModificationTableFile(table.load(null));
            chromosomMenu.findAndReadModificationTable(getModificationTableFile());
            openModificationDialog();
            if(chromosomMenu.getChromosomEditor().getRuleDesigner() != null) {
                chromosomMenu.getChromosomEditor().getRuleDesigner().checkStateColors();
            }
        }
        else if (result.get() == buttonTypeSaveAs){ 
            setModificationTableFile(table.save(null));
            chromosomMenu.findAndReadModificationTable(getModificationTableFile());
            if(chromosomMenu.getChromosomEditor().getRuleDesigner() != null) {
                chromosomMenu.getChromosomEditor().getRuleDesigner().checkStateColors();
            }
        }
        else if (result.get() == buttonTypeClose){ 
            dialog.close();
            if(chromosomMenu.getChromosomEditor().getRuleDesigner() != null) {
                chromosomMenu.getChromosomEditor().getRuleDesigner().checkStateColors();
            }
        }
        else if(chromosomMenu.getChromosomEditor().getRuleDesigner() != null) {
            chromosomMenu.getChromosomEditor().getRuleDesigner().checkStateColors();
        }
    }



    public void openColorDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setResizable(true);
        dialog.getDialogPane().setMaxSize(300, 500);
        dialog.getDialogPane().setPrefWidth(300);
        dialog.getDialogPane().setPrefHeight(500);
        
        dialog.getDialogPane().setContent(getProject().getModificationColors());
        dialog.setTitle("Modification Colors");
        
        ButtonType buttonTypeClose= new ButtonType("Close");

        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeClose);
        
        //Versteckten Close-Button hinzufügen, damit der x-Button funktioniert
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == buttonTypeClose){
            dialog.close();
            int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
            //Das neuzeichnen der Panels verhindern, wenn diese gar nicht existieren
            if(zahl >= 0) {
                nukleosomeTab = createNuclosomeTab();
                tabPane.getTabs().remove(zahl);
                tabPane.getTabs().add(zahl, nukleosomeTab);
                tabPane.getSelectionModel().select(nukleosomeTab);
            }
        }
    }

    /**
     * @return the options
     */
    public OptionsPanel getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(OptionsPanel options) {
        this.options = options;
    }

    private ChromosomMenu startChromosomsMenu() {
        setChromosomMenu(new ChromosomMenu(this));
        rootLayout.setCenter(getChromosomMenu());
        return getChromosomMenu();
    }

    /**
     * @return the primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @param primaryStage the primaryStage to set
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * @return the editorRuleList
     */
    public ArrayList<EditorRule> getEditorRuleList() {
        if(editorRuleList == null) {
            editorRuleList = new ArrayList<>();
        }
        return editorRuleList;
    }

    /**
     * @param editorRuleList the editorRuleList to set
     */
    public void setEditorRuleList(ArrayList<EditorRule> editorRuleList) {
        this.editorRuleList = editorRuleList;
    }

    /**
     * @return the enzymeMap
     */
    public LinkedHashMap<String, EditorEnzyme> getEnzymeMap() {
        return enzymeMap;
    }

    /**
     * @param enzymeMap the enzymeMap to set
     */
    public void setEnzymeMap(LinkedHashMap<String, EditorEnzyme> enzymeMap) {
        this.enzymeMap = enzymeMap;
    }

    /**
     * @return the plusMinusScroll
     */
    public ZoomableScrollPane getPlusMinusScroll() {
        return plusMinusScroll;
    }

    /**
     * @param plusMinusScroll the plusMinusScroll to set
     */
    public void setPlusMinusScroll(ZoomableScrollPane plusMinusScroll) {
        this.plusMinusScroll = plusMinusScroll;
    }

    /**
     * @return the secondRow
     */
    public BigNukleosomRow getSecondRow() {
        return secondRow;
    }

    /**
     * @param secondRow the secondRow to set
     */
    public void setSecondRow(BigNukleosomRow secondRow) {
        this.secondRow = secondRow;
    }

    /**
     * @return the project
     */
    public ChromosomProject getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(ChromosomProject project) {
        this.project = project;
    }

    /**
     * @return the modificationTableFile
     */
    public File getModificationTableFile() {
        return modificationTableFile;
    }

    /**
     * @param modificationTableFile the modificationTableFile to set
     */
    public void setModificationTableFile(File modificationTableFile) {
        this.modificationTableFile = modificationTableFile;
    }

    /**
     * @return the saveAsPNG
     */
    public MenuItem getSaveAsPNG() {
        return saveAsPNG;
    }

    /**
     * @param saveAsPNG the saveAsPNG to set
     */
    public void setSaveAsPNG(MenuItem saveAsPNG) {
        this.saveAsPNG = saveAsPNG;
    }

    /**
     * @return the openModTable
     */
    public MenuItem getOpenModTable() {
        return openModTable;
    }

    /**
     * @param openModTable the openModTable to set
     */
    public void setOpenModTable(MenuItem openModTable) {
        this.openModTable = openModTable;
    }

    /**
     * @return the setModificationColors
     */
    public MenuItem getSetModificationColors() {
        return setModificationColors;
    }

    /**
     * @param setModificationColors the setModificationColors to set
     */
    public void setSetModificationColors(MenuItem setModificationColors) {
        this.setModificationColors = setModificationColors;
    }

    /**
     * @return the stepsToShowValue
     */
    public int getStepsToShowValue() {
        return stepsToShowValue;
    }

    /**
     * @param stepsToShowValue the stepsToShowValue to set
     */
    public void setStepsToShowValue(int stepsToShowValue) {
        
        if(stepsToShowValue<=1) {
            stepsToShowValue = 2;
        }
        
        if(project != null) {
            project.stepsToShow.clear();
            project.stepsToShow.push(stepsToShowValue);
        }
        this.stepsToShowValue = stepsToShowValue;
    }

    /**
     * @return the chromsomMenu
     */
    public ChromosomMenu getChromosomMenu() {
        return chromosomMenu;
    }

    /**
     * @param chromsomMenu the chromsomMenu to set
     */
    public void setChromosomMenu(ChromosomMenu chromsomMenu) {
        this.chromosomMenu = chromsomMenu;
    }
    
    public void setHistSimFile(File histSimFile) {
        BufferedWriter bw = null;
        try {
            confFile.createNewFile();
            bw = new BufferedWriter(new FileWriter(confFile));
            bw.write("histSimFile:" + histSimFile.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.histSimFile = histSimFile;
    }

    public File getHistSimFile() {
        return histSimFile;
    }

    /**
     * @return the confFile
     */
    public File getConfFile() {
        return confFile;
    }

    /**
     * @param confFile the confFile to set
     */
    public void setConfFile(File confFile) {
        this.confFile = confFile;
    }

    /**
     * @return the heatProject
     */
    public HeatProject getHeatProject() {
        return heatProject;
    }

    /**
     * @param heatProject the heatProject to set
     */
    public void setHeatProject(HeatProject heatProject) {
        this.heatProject = heatProject;
    }

    /**
     * @return the row
     */
    public BigNukleosomRow getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(BigNukleosomRow row) {
        this.row = row;
    }

}
