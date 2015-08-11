package application;

import java.util.ArrayList;
import java.util.List;

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
import SunburstNukleosom.SunburstNukleosom;
import SunburstNukleosom.SunburstNukleosomRow;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

public class Chromosom extends Application {
    private static int maxTimeSteps;
    private static int offset;
    private static String fileName;

    private BorderPane rootLayout;
    private ChromosomProject project;
    private double screenWidth;
    private double screenHeight;
    private int maxX = 0;
    private int maxY = 0;
    private BigNukleosomRow row;
    private SunburstNukleosomRow sun;
    private NukleosomVaseGrid vase;
    private NukleosomVaseGrid vase2;
    private NukleosomVaseGrid vase3;
    private NukleosomVaseGrid vase4;
    private TabPane tabPane;
    private Stage newStage;

    @Override
    public void start(Stage primaryStage) {
        initGUI(primaryStage);
        MenuBar menuBar = createMenu(primaryStage);
        rootLayout.setTop(menuBar);

        OptionsPanel options = new OptionsPanel(project);
        rootLayout.setLeft(options);
        


        Tab nukleosomeTab = createNuclosomeTab(primaryStage);
        tabPane.getTabs().add(nukleosomeTab);
        rootLayout.setCenter(tabPane);

        primaryStage.setMaximized(true);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        show(primaryStage);
    }

    private void show(Stage primaryStage) {
        primaryStage.show();
    }

    private Tab  createNuclosomeTab(Stage primaryStage) {
        Tab nukleosomeTab = new Tab();
        nukleosomeTab.setText("Nukleosoms");
        findNukleosomResulution(nukleosomeTab.getText());

        project = new ChromosomProject();
//                
        NukleosomReader nukleosomReader = new NukleosomReader(project);
        
        project.defaultFileName = fileName;
        if(maxTimeSteps!=0) {
            project.setMaxTimeSteps(maxTimeSteps);
        }
        if(offset!=0) {
            project.setOffset(offset);
        }
        
        nukleosomReader.openFile(project.getDefaultFileName());
//		NukleosomReader.fillDataVectors(project);

//		row = new BigNukleosomRow(project,10,10,project.getNukleosomWidth(), project.getNukleosomHeight());
        for(Map.Entry<String, HashMap<String, HashMap<String, HashMap<String, Integer>>>> entry : project.getTimeVector().entrySet()) {
            row = new BigNukleosomRow(project, entry.getValue().size(), project.getTimeVector().size(), project.getNukleosomWidth(), project.getNukleosomHeight());
            break;
        }
        ScrollPane sb = new ScrollPane();
        sb.setContent(row);

        nukleosomeTab.setContent(sb);
        nukleosomeTab.setClosable(false);

        /*		Tab sunburstTab = new Tab();
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
        return nukleosomeTab;
    }

    private void initGUI(Stage primaryStage) {
        this.rootLayout = new BorderPane();
        primaryStage.setTitle("Chromosom");
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
        
//        menu.getItems().add(openFile);
        
        MenuItem exportToSVG = new MenuItem("Export to SVG");
        exportToSVG.setOnAction(actionEvent -> {

            Label label = new Label("Die Darstellung wird exportiert ...");
            Button but = new Button("Wollen Sie die Darstellung exportieren?");
            but.setOnAction(actionEvent2 -> {
                this.export("SVG");
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
        
//        menu.getItems().add(exportToSVG);
        
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
        
//        menu.getItems().add(exportToPDF);
        
        menu.getItems().add(new SeparatorMenuItem());
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(actionEvent -> Platform.exit());
        menu.getItems().add(exit);
        menuBar.getMenus().add(menu);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        return menuBar;
    }

    public void exportToSVG(String fileName) {

        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        String selectedTabName = tabPane.getSelectionModel().getSelectedItem().getText();
        project.setSelectedTabName(selectedTabName);
        if (fileName.equals("")) {
            fileName = selectedTabName + ".svg";
            newStage.close();
        }
        ChromosomExport.writeToFile(fileName);
    }

    public void export(String format) {

        String selectedTabName = tabPane.getSelectionModel().getSelectedItem().getText();

        switch (selectedTabName) {
            case "Nukleosoms":
                ChromosomExport.exportBigNukleosomRow(row);
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
                    proc = proc = Runtime.getRuntime().exec("java -jar \"batik-1.7\\batik-rasterizer.jar\" -d " + (project.selectedTabName + ".pdf") + " -m application/pdf -scripts text/ecmascript -onload " + exportHelper);
                    proc.waitFor();
                    Files.delete(FileSystems.getDefault().getPath(exportHelper));
                    newStage.close();
                    break;
                case "PNG":
                    exportToSVG(exportHelper);
                    proc = Runtime.getRuntime().exec("java -jar \"batik-1.7\\batik-rasterizer.jar\" -d " + (project.selectedTabName + ".png") + " -m image/png -scripts text/ecmascript -onload " + exportHelper);
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

    public void findNukleosomResulution(String visName) {

        switch (visName) {
            case "Nukleosoms": {

                HashMap<String, Integer> valueArray = new HashMap<String, Integer>();
                valueArray.put("0", 0);
                valueArray.put("1", 0);

//				BigNukleosomNew nukl = new BigNukleosomNew(project, valueArray, project.getNukleosomWidth(), project.getNukleosomHeight());
//				double actWidth = Math.ceil(nukl.getPrefWidth() + project.getNukleosomWidth() / (7./4.));
//				double actHeight = Math.ceil(nukl.getPrefHeight() + project.getNukleosomHeight() / (7./6.));
//                                maxX = (int)(1124 / actWidth);
//				maxX = (int)(screenWidth / actWidth);
//				maxY = (int)(((screenHeight) / actHeight));
//				System.err.println("Nukleosoms: " + maxX + " " + maxY);
                break;
            }

            case "Sunburst Nukleosom": {

                List<int[]> valueList = new ArrayList<int[]>();

                for (int i = 0; i < project.getHistoneNumber(); i++) {
                    int valueArray[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                    valueList.add(valueArray);
                }

                SunburstNukleosom nukl = new SunburstNukleosom(project, valueList, project.getSunburstWidth(), project.getSunburstHeight());

                double actWidth = (nukl.getPrefWidth() + 3);
                double actHeight = (nukl.getPrefHeight() + 3);

                System.err.println(screenWidth + " " + screenHeight);

                System.err.println(actWidth + " " + actHeight);

                maxX = (int) ((screenWidth / actWidth));
                maxY = (int) ((screenHeight / actHeight));

                System.err.println(maxX + " " + maxY);

                break;
            }
        }
    }

    public static void main(String[] args) {
        
        if(args[0]!=null)
            fileName = args[0];
        if(args.length > 1) {
             maxTimeSteps = Integer.parseInt(args[1]);
        }
        if(args.length > 2) {
             offset = Integer.parseInt(args[2]);
        }        
           
        
        launch(args);

    }
}
