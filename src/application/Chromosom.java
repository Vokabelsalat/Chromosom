package application;

import HeatChromosom.HeatProject;
import Nukleosom.BigNukleosomNew;
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
import NukleosomVase.NukleosomVaseGrid;
import SunburstNukleosom.SunburstNukleosom;
import SunburstNukleosom.SunburstNukleosomRow;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import test.ChromosomTree;

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
    private Tab nukleosomeTab;
    private OptionsPanel options;
    public ScrollPane sb;
    public ChromosomTree tree;
    private BorderPane nukleosomBorderPane;
    private ScrollPane sp;
    
    public ArrayList<HeatProject> projectList;
    
    public boolean sameRow, sameColumns;
    
    @Override
    public void start(Stage primaryStage) {
        
//            startTwoHeatChromosom(primaryStage);
            startHeatChromosom(primaryStage);
            
            Scene scene = new Scene(getRootLayout());
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            show(primaryStage);
    }

    private void startChromosom(Stage primaryStage) {
//        this.project = new ChromosomProject(this);
//        
//        initGUI(primaryStage);
//        
//        MenuBar menuBar = createMenu(primaryStage);
//        rootLayout.setTop(menuBar);
//
//        options = new OptionsPanel(project);
//        rootLayout.setLeft(options);
//        
//        project.defaultFileName = fileName;
//        project.maxTimeSteps.push(maxTimeSteps);
//        project.offset.push(offset);
//         
//        NukleosomReader nukleosomReader = new NukleosomReader(project);
//        nukleosomReader.openFile(project.getDefaultFileName());
//        sb = new ScrollPane();
//        nukleosomeTab = createNuclosomeTab();
//        tabPane.getTabs().add(nukleosomeTab);
//        rootLayout.setCenter(tabPane);
//        
//        tree = new ChromosomTree(project); 
//        tree.fillTree();
//        
//        options.getChildren().add(tree);
    }

        private void startTwoHeatChromosom(Stage primaryStage) {
        initGUI(primaryStage);
        
        projectList = new ArrayList<>();
        
        HeatProject heatProject = new HeatProject(this, 0);
        HeatProject heatProject2 = new HeatProject(this, 1);
        
        projectList.add(heatProject);
        projectList.add(heatProject2);
        
        String first = heatProject.getHeatReader().getFirstItemInTimeMap();
        String first2 = heatProject2.getHeatReader().getFirstItemInTimeMap();
        heatProject.getHeatReader().readLogFile(first);
        heatProject2.getHeatReader().readLogFile(first2);
        
        sameRow = false;
        sameColumns = false;
        
        if(heatProject.getHeatReader().getTimeMap().get(first).size() == 
                heatProject2.getHeatReader().getTimeMap().get(first2).size()) {
                sameRow = true;
            if(heatProject.getHeatReader().getTimeMap().get(first).get(0).size() == 
                heatProject2.getHeatReader().getTimeMap().get(first2).get(0).size()) {
                sameColumns = true;
            }
        }
        
        SplitPane splitPane = new SplitPane();
        
        splitPane.setOrientation(Orientation.VERTICAL);
        
        splitPane.getItems().addAll(heatProject.createHeatMainPanel(), heatProject2.createHeatMainPanel());
        
        getRootLayout().setCenter(splitPane);
        
    }
    
    private void startHeatChromosom(Stage primaryStage) {
        initGUI(primaryStage);
        
        projectList = new ArrayList<>();
        
        HeatProject heatProject = new HeatProject(this, 0);
        
        projectList.add(heatProject);
        
        String first = heatProject.getHeatReader().getFirstItemInTimeMap();
        heatProject.getHeatReader().readLogFile(first);
        
        sameRow = false;
        sameColumns = false;
        
        SplitPane splitPane = new SplitPane();
        
        splitPane.setOrientation(Orientation.VERTICAL);
        
        splitPane.getItems().addAll(heatProject.createHeatMainPanel());

        
        getRootLayout().setCenter(splitPane);
        
    }
    
    private void show(Stage primaryStage) {
        primaryStage.show();
    }

    private Tab  createNuclosomeTab() {
        Tab nukleosomeTab = new Tab();
        nukleosomeTab.setText("Nukleosoms");
        
        row = new BigNukleosomRow(project, project.getNukleosomWidth(), project.getNukleosomHeight(), project.maxTimeSteps.peek(), project.stepSize.peek());

        nukleosomBorderPane = new BorderPane();
        
        nukleosomBorderPane.setCenter(row);
        
        Button up = new Button("^");
        
        up.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                row.goUp();
            }
        });
            

        Button down = new Button("v");
        
        down.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                row.goDown();
            }
        });
        
        nukleosomBorderPane.setTop(up);
        nukleosomBorderPane.setBottom(down);
        
        sb.setContent(nukleosomBorderPane);
//        sb.setFitToHeight(true);
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
        
        
        MenuItem saveAsPNG = new MenuItem("Save as PNG");
        saveAsPNG.setOnAction(actionEvent -> {

            Label label = new Label("Die Darstellung wird exportiert ...");
            Button but = new Button("Wollen Sie die Darstellung exportieren?");
            BorderPane pane = new BorderPane();
            but.setOnAction(actionEvent2 -> {
                pane.getChildren().remove(but);
                pane.setCenter(new Label("Bitte warten!"));
                this.saveAsPng();
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
        
        menu.getItems().add(saveAsPNG);
        
        menu.getItems().add(new SeparatorMenuItem());
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(actionEvent -> Platform.exit());
        menu.getItems().add(exit);
        menuBar.getMenus().add(menu);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        return menuBar;
    }
    
    public void saveAsPng() {
        
        int add = 4;
        
        zoomInNukleosoms(add);
        
        WritableImage image = row.snapshot(new SnapshotParameters(), null);
        
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

    void zoomIn(int newOffset) {
        int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
        project.stepsToShow.push(project.stepSize.peek());
        project.maxTimeSteps.push(project.stepSize.peek());
        project.stepSize.push(1);
        project.offset.push(newOffset);
        nukleosomeTab = createNuclosomeTab();
        tabPane.getTabs().remove(zahl);
        tabPane.getTabs().add(zahl, nukleosomeTab);
    }

    void zoomOut() {
        int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
        
        project.stepsToShow.pop();
        project.stepSize.pop();
        project.offset.pop();
        project.maxTimeSteps.pop();
        nukleosomeTab = createNuclosomeTab();
        tabPane.getTabs().remove(zahl);
        tabPane.getTabs().add(zahl, nukleosomeTab);        
    }

    void addNukleosomToOptions(BigNukleosomNew bigNukleosomNew) {
        options.addNukleosom(bigNukleosomNew);
    }

    public void zoomInNukleosoms(int add) {
        System.err.println(project.stepSize);
        int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
        
        int w = project.getNukleosomWidth();
        int h = project.getNukleosomHeight();
        
        project.nukleosomWidth = w + add;
        project.nukleosomHeight = h + add;
        
        nukleosomeTab = createNuclosomeTab();
        
        tabPane.getTabs().remove(zahl);
        tabPane.getTabs().add(zahl, nukleosomeTab);  
    }

    public void zoomOutNukleosoms(int add) {
        int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
        
        int w = project.getNukleosomWidth();
        int h = project.getNukleosomHeight();
        
        project.nukleosomWidth = w - add;
        project.nukleosomHeight = h - add;
        
        nukleosomeTab = createNuclosomeTab();
        
        tabPane.getTabs().remove(zahl);
        tabPane.getTabs().add(zahl, nukleosomeTab);  
    }
    
    public void showChromosoms(int newOffset, int newStepSize, int newStepsToShow, int newMaxTimeSteps) {
        
//        System.err.println(project.stepSize);
        
        int zahl = tabPane.getTabs().indexOf(nukleosomeTab);
        
        if(project.stepSize.size()>1) {
            project.stepSize.pop();
        }
        
        if(project.stepsToShow.size()>1)
            project.stepsToShow.pop();
                 
        if(project.offset.size()>1)  
            project.offset.pop();
        
        if(project.maxTimeSteps.size()>1)
            project.maxTimeSteps.pop();
        
        project.stepsToShow.push(newStepsToShow);
        project.maxTimeSteps.push(newMaxTimeSteps);
        project.stepSize.push(newStepSize);
        project.offset.push(newOffset);
        
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


    
}
