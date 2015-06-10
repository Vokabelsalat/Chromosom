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


import Nukleosom.BigNukleosomNew;
import Nukleosom.BigNukleosomRow;
import NukleosomVase.NukleosomVaseGrid;
import SunburstNukleosom.SunburstNukleosom;
import SunburstNukleosom.SunburstNukleosomRow;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

public class Chromosom extends Application {
	
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
		this.rootLayout = new BorderPane();
		
		primaryStage.setTitle("Chromosom");
	
		project = new ChromosomProject();
		
		screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		
		tabPane = new TabPane();
		Tab nukleosomTab = new Tab();
		nukleosomTab.setText("Nukleosoms");
		
		findNukleosomResulution(nukleosomTab.getText());
		
		row = new BigNukleosomRow(project,10,10,project.getNukleosomWidth(), project.getNukleosomHeight());
//                row = new BigNukleosomRow(project,maxX + 10, maxY + 10,project.getNukleosomWidth(), project.getNukleosomHeight());
		System.err.println();
		ScrollPane sb = new ScrollPane();
		sb.setContent(row);
		
		nukleosomTab.setContent(sb);
		nukleosomTab.setClosable(false);
		tabPane.getTabs().add(nukleosomTab);
		
		Tab sunburstTab = new Tab();
		sunburstTab.setText("SunburstNukleosom");
		
		findNukleosomResulution(sunburstTab.getText());
		
//		sun = new SunburstNukleosomRow(project, maxX + 10, maxY + 10, project.getSunburstWidth(), project.getSunburstHeight());
                sun = new SunburstNukleosomRow(project, 10, 10, project.getSunburstWidth(), project.getSunburstHeight());
                
		ScrollPane sb2 = new ScrollPane();
		sb2.setContent(sun);	
	    
		sunburstTab.setContent(sb2);
		sunburstTab.setClosable(false);
		tabPane.getTabs().add(sunburstTab);
		
		Tab vaseTab = new Tab();
		vaseTab.setText("Nukleosom Vase ROW");
		ScrollPane sb3 = new ScrollPane();
		vase = new NukleosomVaseGrid(project,"ROW",5,5,6,10);
		sb3.setContent(vase);
		vaseTab.setContent(sb3);
		vaseTab.setClosable(false);
		tabPane.getTabs().add(vaseTab);
		
		Tab vaseTab1 = new Tab();
		vaseTab1.setText("Nukleosom Vase BLOCK");
		ScrollPane sb4 = new ScrollPane();
		vase2 = new NukleosomVaseGrid(project,"BLOCK",5,5,6,10);
		sb4.setContent(vase2);
		vaseTab1.setContent(sb4);
		vaseTab1.setClosable(false);
		tabPane.getTabs().add(vaseTab1);
		
		Tab vaseTab2 = new Tab();
		vaseTab2.setText("Nukleosom Vase Zeit");
		ScrollPane sb5 = new ScrollPane();
		vase3 = new NukleosomVaseGrid(project,"ZEIT",5,5,6,10);
		sb5.setContent(vase3);
		vaseTab2.setContent(sb5);
		vaseTab2.setClosable(false);
		tabPane.getTabs().add(vaseTab2);
		
		Tab vaseTab3 = new Tab();
		vaseTab3.setText("Nukleosom Vase ZUSTAND");
		ScrollPane sb6 = new ScrollPane();
		vase4 = new NukleosomVaseGrid(project,"ZUSTAND",5,5,6,10);
		sb6.setContent(vase4);
		vaseTab3.setContent(sb6);
		vaseTab3.setClosable(false);
		tabPane.getTabs().add(vaseTab3);
		
		rootLayout.setCenter(tabPane);
                
                OptionsPanel options = new OptionsPanel();
                rootLayout.setLeft(options);
		
		Scene scene = new Scene(rootLayout,screenWidth, screenHeight);
		
        MenuBar menuBar = new MenuBar();
        
        Menu menu = new Menu("File");
        
        MenuItem exportToSVG = new MenuItem("Export to SVG");
        exportToSVG.setOnAction(actionEvent -> {
            
            Label label = new Label("Die Darstellung wird exportiert ...");
            Button but = new Button("Wollen Sie die Darstellung exportieren?");
            but.setOnAction(actionEvent2 -> { 
                exportToSVG("");
            });
            
            BorderPane pane = new BorderPane();
            pane.setCenter(but);
            Scene scene2 = new Scene(pane,200,200);
            newStage = new Stage();
            newStage.setScene(scene2);
            //tell stage it is meannt to pop-up (Modal)
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Export");
            
            newStage.show();
            
        });
        menu.getItems().add(exportToSVG); 
        
        MenuItem exportToPNG = new MenuItem("Export to PNG");
        exportToPNG.setOnAction(actionEvent -> {
            
            Label label = new Label("Die Darstellung wird exportiert ...");
            Button but = new Button("Wollen Sie die Darstellung exportieren?");
            but.setOnAction(actionEvent2 -> { 
                exportToPNG();
            });
            
            BorderPane pane = new BorderPane();
            pane.setCenter(but);
            Scene scene2 = new Scene(pane,200,200);
            newStage = new Stage();
            newStage.setScene(scene2);
            //tell stage it is meannt to pop-up (Modal)
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Export");
            
            newStage.show();
            
        });
        menu.getItems().add(exportToPNG); 
        
        MenuItem exportToPDF = new MenuItem("Export to PDF");
        exportToPDF.setOnAction(actionEvent -> {
            
            Label label = new Label("Die Darstellung wird exportiert ...");
            Button but = new Button("Wollen Sie die Darstellung exportieren?");
            but.setOnAction(actionEvent2 -> { 
                exportToPDF();
            });
            
            BorderPane pane = new BorderPane();
            pane.setCenter(but);
            Scene scene2 = new Scene(pane,200,200);
            newStage = new Stage();
            newStage.setScene(scene2);
            //tell stage it is meannt to pop-up (Modal)
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Export");
            
            newStage.show();
            
        });
        menu.getItems().add(exportToPDF);      
        
        menu.getItems().add(new SeparatorMenuItem());
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(actionEvent -> Platform.exit());
        menu.getItems().add(exit); 
        
//        menuBar.setUseSystemMenuBar(true);
        menuBar.getMenus().add(menu);
        
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        
        rootLayout.setTop(menuBar); 
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void exportToSVG(String fileName) {
            
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            Node nod = selectedTab.getContent();
            GridPane grid = new GridPane();
            if(nod instanceof javafx.scene.control.ScrollPane) {
                ScrollPane scroll = (ScrollPane)nod;
                if(scroll.getContent() instanceof javafx.scene.layout.GridPane) {
                    grid = (GridPane)scroll.getContent();
                    String selectedTabName = tabPane.getSelectionModel().getSelectedItem().getText();
                    project.setSelectedTabName(selectedTabName);
                    ChromosomExport.exportNodeToSVG(grid);
                    if(fileName.equals("")) {
                        fileName = selectedTabName + ".svg";newStage.close();
                    }
                    ChromosomExport.writeToFile(fileName);
                    newStage.close();
                }
            }
            
            
	}
        
        public void exportToPDF() {
            
            String exportHelper = "exportHelper.svg";
            
            exportToSVG(exportHelper);
             try {
                Process proc = Runtime.getRuntime().exec("java -jar \"batik-1.7\\batik-rasterizer.jar\" -d " + (project.selectedTabName + ".pdf") +  " -m application/pdf -scripts text/ecmascript -onload " + exportHelper);
                System.err.println(project.selectedTabName);
                proc.waitFor();
//                Files.deleteIfExists(FileSystems.getDefault().getPath(exportHelper));  
                newStage.close();
             } catch (IOException ex) {
                 Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
             } catch (InterruptedException ex) {
                 Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
        public void exportToPNG() {
            
            String exportHelper = "exportHelper.svg";
            
            exportToSVG(exportHelper);
            try {
               Process proc = Runtime.getRuntime().exec("java -jar \"batik-1.7\\batik-rasterizer.jar\" -d " + (project.selectedTabName + ".png") +  " -m image/png -scripts text/ecmascript -onload " + exportHelper);
               proc.waitFor();
//               Files.delete(FileSystems.getDefault().getPath(exportHelper));  
               newStage.close();
            } catch (IOException ex) {
                Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                 Logger.getLogger(Chromosom.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
	
	public void findNukleosomResulution(String visName) {
		
		switch(visName) {
			case "Nukleosoms" : {
		
                                int valueArray[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
				      
				BigNukleosomNew nukl = new BigNukleosomNew(project, valueArray, project.getNukleosomWidth(), project.getNukleosomHeight());

				double actWidth = Math.ceil(nukl.getPrefWidth() + project.getNukleosomWidth() / (7./4.));
				double actHeight = Math.ceil(nukl.getPrefHeight() + project.getNukleosomHeight() / (7./6.));
				
//                                maxX = (int)(1124 / actWidth);
				maxX = (int)(screenWidth / actWidth);
				maxY = (int)(((screenHeight) / actHeight));
				
				System.err.println("Nukleosoms: " + maxX + " " + maxY);
				
				break;
			}
			
			case "Sunburst Nukleosom" : {
				
				List<int[]> valueList = new ArrayList<int[]>();
				
				for(int i = 0; i < project.getHistoneNumber();i++) {
					int valueArray[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
					valueList.add(valueArray);
				}
				      
				SunburstNukleosom nukl = new SunburstNukleosom(project,valueList, project.getSunburstWidth(), project.getSunburstHeight());

				double actWidth = (nukl.getPrefWidth() + 3);
				double actHeight = (nukl.getPrefHeight() + 3);
				
				System.err.println(screenWidth + " " + screenHeight);
				
				System.err.println(actWidth + " " + actHeight);
				
				maxX = (int)((screenWidth / actWidth));
				maxY = (int)((screenHeight / actHeight));
				
				System.err.println(maxX + " " + maxY);
				
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
