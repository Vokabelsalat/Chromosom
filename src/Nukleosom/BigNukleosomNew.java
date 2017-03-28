/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import javafx.scene.paint.Color;
import application.ChromosomProject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * Aktuelles Nukleosom mit einer Unterteilung für die verschiedenen Histone
 * @author Jakob
 */
public class BigNukleosomNew extends StackPane {

        public Rectangle firstRect;
        public Rectangle lastRect;
	
        private boolean showLabels;
        
	HashMap<String,Integer> attributeMap;
	double angleWidth;
	
        private HashMap<String,HashMap<String,String>> histoneMap;
        ChromosomProject project;
        int width; 
        int height;
        private int x;
        private int y;
//        Pane pan;
        int maxX = 0;
        int maxY = 0;
        
        public int nuklWidth = 0;
        public int nuklHeight = 0;
        Rectangle highRect;
        private boolean changed = false;
        private boolean enzymeWaiting;
        private Rectangle enzymeRectangle;
        
        Stage hoverStage;
        Popup hoverPopup;
        
	public BigNukleosomNew(ChromosomProject project, HashMap<String,HashMap<String,String>> histoneMap, int x, int y, int width, int height, boolean showLabels, boolean hover) {
            getStyleClass().add("BigNukleosomNew");
            this.showLabels = showLabels;
            this.histoneMap = histoneMap;
            this.project = project;
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
            this.setAlignment(Pos.TOP_LEFT);
            highRect = new Rectangle();
            
//            this.setPadding(new Insets(5,5,5,5));
//            setStyle("-fx-border: 1px solid; -fx-border-color: black;");
            
            LinkedHashMap<String, String[][]> histoneProperties = project.getHistoneMap();
            
//                maxX = project.getMaxX();
//                maxY = project.getMaxY();
                
//                //TODO ind die Höhe und Breite noch die Lücken zwischen den Histonen einberechnen (gaps)
//		this.width = maxX * width;
//		this.height = maxY * height;
                
            int gridX = 0;
            int gridY = 0;

            int attrMaxX = 0;
            int attrMaxY = 0;

            String histoneNumberString = "";

            GridPane grid = new GridPane();
            grid.setHgap(width / (6./2.));
            grid.setVgap(height / (6./2.));

            String value;
            Color color;

            for(String histone : histoneProperties.keySet()) {

                histoneNumberString = histone;

                boolean histoneHit = false;

                String[][] attributeArray = histoneProperties.get(histone);

                if(attributeArray != null) {

                    if(attrMaxX < attributeArray.length) {
                        attrMaxX = attributeArray.length;
                    }

                    if(attrMaxY < attributeArray[0].length) {
                        attrMaxY = attributeArray[0].length;
                    }

                    GridPane sitePane = new GridPane();
                    sitePane.setHgap(0);
                    sitePane.setVgap(0);

                    for(int innerY = 0; innerY < attributeArray[0].length; innerY++) {

                        for(int innerX = 0; innerX < attributeArray.length; innerX++) {

                            if(histoneMap.containsKey(histone)) {
                                
                                if(histoneMap.get(histone).containsKey(attributeArray[innerX][innerY])) {

                                    value = histoneMap.get(histone).get(attributeArray[innerX][innerY]);
                                    histoneHit = true;
                                    
                                    String siteName = project.getHistoneMap().get(histone)[innerX][innerY];
                                    String histSite = histone + siteName;

                                    color = Color.BLACK;
                                    for(String colorKey : project.getModificationColors().getColorMap().keySet()) {
                                        if(colorKey.contains(":")) {
                                            String split[] = colorKey.split(":");
                                            if(attributeArray[innerX][innerY].contains(split[0]) && value.contains(split[1])) {

                                                color = project.getModificationColors().getColorMap().get(colorKey);
                                                break;
                                            }

                                            if(histSite.contains(split[0]) && value.contains(split[1])) {
                                                color = project.getModificationColors().getColorMap().get(colorKey);
                                                break;
                                            }

                                        }
                                        else if(value.contains(colorKey)) {
                                            color = project.getModificationColors().getColorMap().get(value);
                                        }
                                    }

                                    try {
                                        color = Color.web(project.getSpecialColorMap().get(histone).get(attributeArray[innerX][innerY]).get("undefined"));
                                    }
                                    catch(Exception e) {
                                    }

                                    try {
                                        color = Color.web(project.getSpecialColorMap().get("undefined").get("undefined").get(value));
                                    }
                                    catch(Exception e) {
                                    }

                                    try {
                                        color = Color.web(project.getSpecialColorMap().get(histone).get("undefined").get(value));
                                    }
                                    catch(Exception e) {
                                    }

                                    try {
                                        color = Color.web(project.getSpecialColorMap().get("undefined").get(attributeArray[innerX][innerY]).get(value));
                                    }
                                    catch(Exception e) {
                                    }

                                    try {
                                        color = Color.web(project.getSpecialColorMap().get(histone).get(attributeArray[innerX][innerY]).get(value));
                                    }
                                    catch(Exception e) {
                                    }


                                    StackPane rectPane = new StackPane();
                                    Rectangle rect = new Rectangle(width, height , color); 
                                    rect.setOpacity(1.0);   

                                    rect.setStroke(new Color(0,0,0,0.6));
                                    rect.setStrokeWidth(0.3);

                                    if(firstRect == null) {
                                        firstRect = rect;
                                    }
                                    lastRect = rect;

                                    rectPane.getChildren().add(rect);
                                    rectPane.getStyleClass().add("rectPane");

                                    if(showLabels == true) {
                                        Label lab;
                                        if(hover == true) {
                                            lab = new Label(siteName);
                                        }
                                        else {
                                            lab = new Label(histone + "\n" + siteName + "\n" + String.valueOf(value));
                                        }

                                        lab.setId("fancytext");

                                        lab.setPadding(new Insets(0,2,0,2));

                                        lab.setAlignment(Pos.CENTER);
                                        rectPane.setAlignment(Pos.CENTER);

                                        rectPane.getChildren().add(lab);
                                    }

                                    sitePane.add(rectPane, innerX, innerY);

                                }
                                else if (project.isShowEmptySites()) {
                                    StackPane rectPane = new StackPane();
                                    Rectangle rect = new Rectangle(width, height , Color.WHITE); 
                                    rect.setOpacity(1);   

                                    rect.setStroke(new Color(0,0,0,0.6));
                                    rect.setStrokeWidth(0.3);
                                    rectPane.getChildren().add(rect);
                                    sitePane.add(rectPane, innerX, innerY);
                                    lastRect=rect;

                                    if(showLabels == true) {
                                        String siteName = project.getHistoneMap().get(histone)[innerX][innerY];
                                        Label lab;
                                        if(hover == true) {
                                            lab = new Label(siteName);
                                        }
                                        else {
                                            lab = new Label(histone + "\n" + siteName + "\nun");
                                        }

                                        lab.setId("fancytext");

                                        lab.setPadding(new Insets(0,2,0,2));

                                        lab.setAlignment(Pos.CENTER);
                                        rectPane.setAlignment(Pos.CENTER);

                                        rectPane.getChildren().add(lab);
                                    }
                                }
                                else {
                                    StackPane rectPane = new StackPane();
                                    Rectangle rect = new Rectangle(width, height , Color.WHITE); 

                                    rect.setStroke(new Color(0,0,0,0.6));
                                    rect.setStrokeWidth(0.3);
                                    rectPane.getChildren().add(rect);
                                    sitePane.add(rectPane, innerX, innerY);
                                    lastRect=rect;

                                    if(showLabels == true) {
                                        Label lab = new Label("un");

                                        lab.setId("fancytext");

                                        lab.setPadding(new Insets(0,2,0,2));

                                        lab.setAlignment(Pos.CENTER);
                                        rectPane.setAlignment(Pos.CENTER);

                                        rectPane.getChildren().add(lab);
                                    }
                                    else {
                                        // Damit die Rectangles nur in der Seitenansicht zu sehen sind
                                        rect.setOpacity(0); 
                                    }
                                }
                            }
                        }

                    }
                    grid.add(sitePane, gridX, gridY); 
                }

                if((gridX+1) % 2 == 0) {
                    if(project.maxY < gridY && histoneHit == true) {
                        project.maxY++;
                    }
                    gridX = 0;
                    gridY++;
                }
                else {
                    gridX++;                        
                    if(project.maxX < gridX && histoneHit == true) {
                        project.maxX++;
                    }
                }
            }

            if(showLabels == false) {
                BigNukleosomNew give = this;
                if(project.getChromosom().getOptions()!= null) {
                    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            int col = 1;
                            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                                if(null != project.getLeftNukleosom()) {
                                    project.getLeftNukleosom().deHighlight();
                                }
                                selectNukls(1, project.getLeftNukleosom());
                                col = 1;
                            }
                            else if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                                if(null != project.getRightNukleosom()) {
                                    project.getRightNukleosom().deHighlight();
                                }
                                selectNukls(2, project.getRightNukleosom());
                                col = 2;
                            }
                           project.chromosom.getOptions().addNukleosom(give, col);
                        }
                    });
                }
            }

            this.getChildren().add(grid);

            String str = String.valueOf(x) + " " + String.valueOf(y);

            if(!project.nukList.contains(str)) {
                project.nukList.add(str);
            }
            else {
                project.copyList.add(str);
            }

            project.count = project.count + 1;
            
            if(hover == true) {
                
                hoverPopup = new Popup();
                hoverPopup.getContent().add(new BigNukleosomNew(project, histoneMap, x, y, width * 2, height * 2, true, false));
                
                this.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        hoverPopup.show(project.chromosom.getPrimaryStage(), t.getScreenX(), t.getScreenY());
                    }
                });
                
                this.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        hoverPopup.hide();
                    }
                });

            }
            
            this.heightProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
//                        if(project.maxNuclHeight < new_val.doubleValue()) {
                            project.maxNuclHeight = new_val.doubleValue();
                            highRect.setHeight(new_val.doubleValue());
//                        }
                }
            });
            
            this.widthProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
//                        if(project.maxNuclWidth < new_val.doubleValue()) {
                            project.maxNuclWidth = new_val.doubleValue() ;
                            highRect.setWidth(new_val.doubleValue());
//                        }
                }
            });
            
        }
        
        public void highlight(int col, double width, double height) {
            
            width = this.getWidth();
            height = this.getHeight();
            
//            width = project.maxNuclWidth;
//            height = project.maxNuclHeight;
            
            Color color = Color.BLUE;
            highRect.setOpacity(0.15);
            switch (col) {
                case 0:
                    setChanged(true);
                    break;
                case 1:
                    color = Color.RED;
                    highRect.setOpacity(0.3);
                    break;
                case 2:
                    color = Color.GREEN;
                    highRect.setOpacity(0.3);
                    break;
                default:
                    break;
            }
            
            highRect.setWidth(width);
            highRect.setHeight(height);
            
            highRect.setFill(color);
            
            if(!this.getChildren().contains(highRect)) {
                this.getChildren().add(highRect);
            }
        }
        
        public void deHighlight() {
            if(this.getChildren().contains(highRect)) {
                this.getChildren().remove(highRect);
            }
            if(Integer.parseInt(project.getMetaInformations().get(String.valueOf(y))[1]) == x && project.stepSize.peek() == 1) {
                highlight(0, this.getWidth(), this.getHeight());
            }
        }
        
        public void selectNukls(int col, BigNukleosomNew oldNukl) {
            if(col == 1) {
                if(project.getLeftNukleosom() == this) {
                    this.deHighlight();
                    project.setLeftNukleosom(null);
                }
                else {
                    this.highlight(1, this.getWidth(), this.getHeight());
                    if(oldNukl != null) {
                        oldNukl.deHighlight();
                    }
                    project.setLeftNukleosom(this);
                }
                if(project.getRightNukleosom() != null && project.getLeftNukleosom() != project.getRightNukleosom()) {
                    project.getRightNukleosom().highlight(2, this.getWidth(), this.getHeight());
                }
            }
            else {
                if(project.getRightNukleosom() == this) {
                    this.deHighlight();
                    project.setRightNukleosom(null);
                }
                else {
                    this.highlight(2, this.getWidth(), this.getHeight());
                    if(oldNukl != null) {
                        oldNukl.deHighlight();
                    }
                    project.setRightNukleosom(this);
                }
                if(project.getLeftNukleosom() != null  && project.getLeftNukleosom() != project.getRightNukleosom()) {
                    project.getLeftNukleosom().highlight(1, this.getWidth(), this.getHeight());
                }
            }

//            project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(leftNode, 2, project.getHeatOptionsPanel().getStartRow());
//            project.getHeatOptionsPanel().addHeatNukleosomToOptionPanel(rightNode, 3, project.getHeatOptionsPanel().getStartRow());
        }
        
        public String getSVGExportString() {
            return ""; //svgExportString;
        }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the histoneMap
     */
    public HashMap<String,HashMap<String,String>> getHistoneMap() {
        return histoneMap;
    }

    /**
     * @param histoneMap the histoneMap to set
     */
    public void setHistoneMap(HashMap<String,HashMap<String,String>> histoneMap) {
        this.histoneMap = histoneMap;
    }

    /**
     * @return the showLabels
     */
    public boolean isShowLabels() {
        return showLabels;
    }

    /**
     * @param showLabels the showLabels to set
     */
    public void setShowLabels(boolean showLabels) {
        this.showLabels = showLabels;
    }

    /**
     * @return the changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * @param changed the changed to set
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * @return the enzymeWaiting
     */
    public boolean isEnzymeWaiting() {
        return enzymeWaiting;
    }

    /**
     * @param enzymeWaiting the enzymeWaiting to set
     */
    public void setEnzymeWaiting(boolean enzymeWaiting) {
        this.enzymeWaiting = enzymeWaiting;
    }

    void deHighlightEnzyme() {
        getEnzymeRectangle().setVisible(false);
    }

    void highlightEnzyme() {
        getEnzymeRectangle().setVisible(true);
    }
    
    /**
     * @return the enzymeRectangle
     */
    public Rectangle getEnzymeRectangle() {
        return enzymeRectangle;
    }

    /**
     * @param enzymeRectangle the enzymeRectangle to set
     */
    public void setEnzymeRectangle(Rectangle enzymeRectangle) {
        this.enzymeRectangle = enzymeRectangle;
    }
}

