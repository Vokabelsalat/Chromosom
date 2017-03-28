///*
package Nukleosom;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import application.ChromosomProject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;


/**
 * Ein Grid bestehend aus den Einzelnen Nukleosomen
 * @author Jakob
 */
public class BigNukleosomRow extends GridPane { 

	ChromosomProject project;
	int height, width;
	BigNukleosomRow scaledRow;
        private ArrayList<BigNukleosomNew> nuklList;
        private ArrayList<Integer> enzymeWaitingIndex;
        private ArrayList<String> stepList;
        String y = "";
        int numberOfSteps;
        private Button up;
        private Button down;
        boolean header;
        private boolean foundFirst = false;
        private boolean foundLast = false;
        private boolean overlayed, firstOverlayed;
        private Group enzymeGroup;
        
        int maxTimeSteps, stepSize;
        
	public BigNukleosomRow(ChromosomProject project, int width, int height, int maxTimeSteps, int stepSize, boolean header) {
            
            this.header = header;
            project.count = 0;
            project.nukList = new ArrayList<>();
            project.copyList = new ArrayList<>();
            stepList = new ArrayList<>();
            this.enzymeGroup = new Group();
            
            
		this.project = project;
		this.height = height;
		this.width = width;
                this.nuklList = new ArrayList<BigNukleosomNew>();
                this.maxTimeSteps = maxTimeSteps;
                this.stepSize = stepSize;
		
               this.setStyle("-fx-background-color: white;");
                
		setAlignment(Pos.CENTER);
		setHgap(width / (6./3.));
		setVgap(height / (6./1.));
                
		HashMap<String, HashMap<String, HashMap<String,HashMap<String,String>>>> timeVector = project.getTimeVector();
		
                BigNukleosomNew nukl = null;
                numberOfSteps = 0;
                
                up = new Button("^");
                down = new Button("v");                
                
                for(int utz = 0; utz <= project.maxTimeSteps.peek(); utz = utz + stepSize) {
                    y = String.valueOf(utz + project.offset.peek());
                    
                    if(timeVector.containsKey(y)) {
                        
                        stepList.add(y);
                        
                        HashMap<String, HashMap<String,HashMap<String,String>>> nukleomList = timeVector.get(y);
                        for(String x : timeVector.get(y).keySet()) {
                            HashMap<String,HashMap<String,String>> histoneMap = nukleomList.get(x);
                                nukl = new BigNukleosomNew(project,timeVector.get(y).get(x), Integer.parseInt(x), Integer.parseInt(y), width, height, false, false);
                                nukl.setCache(true);
                                nukl.setCacheShape(true);
                                nukl.setCacheHint(CacheHint.SPEED);
                                if(header)
                                    add(nukl, Integer.parseInt(x)+1,numberOfSteps);
                                else
                                    add(nukl, Integer.parseInt(x),numberOfSteps);
                                
                                nuklList.add(nukl);
                                GridPane.setVgrow(nukl, Priority.ALWAYS);
                                GridPane.setHgrow(nukl, Priority.ALWAYS);
                        } 
                        PlusMinusLabel plusMinus = new PlusMinusLabel(y, project);
                        if(header)
                            add(plusMinus, 0, numberOfSteps);
                        else
                            add(plusMinus, timeVector.get(y).size(), numberOfSteps);
                        
                        numberOfSteps++;
                        
                        
                    }
		}
                
                if(project.reservoirStack.size() == 0 && !stepList.contains(y)) {
                    y = project.getLastItemInTimeMap();
                    stepList.add(y);
                    if(header)
                        add(new PlusMinusLabel(y, project), 0, numberOfSteps);
                    else
                        add(new PlusMinusLabel(y, project), timeVector.get(y).size(), numberOfSteps);
                   
                    HashMap<String, HashMap<String,HashMap<String,String>>> nukleomList = timeVector.get(y);
                    for(String x : timeVector.get(y).keySet()) {
                        HashMap<String,HashMap<String,String>> histoneMap = nukleomList.get(x);
                        nukl = new BigNukleosomNew(project,timeVector.get(y).get(x), Integer.parseInt(x), Integer.parseInt(y), width, height, false, false);
                        if(header)
                            add(nukl, Integer.parseInt(x)+1,numberOfSteps);
                        else
                            add(nukl, Integer.parseInt(x),numberOfSteps);
                        nuklList.add(nukl);
                    } 
                    
                    numberOfSteps++;
                }
                
                
            checkFirstAndLast();                
            this.setCache(true);
            this.setCacheShape(true);
            this.setCacheHint(CacheHint.SPEED);
            

	}
        
        public BigNukleosomRow(ChromosomProject project, HashMap<String, HashMap<String, HashMap<String,String>>> nucleosomeMap, int width, int height, int maxTimeSteps, int stepSize, boolean header) {
            
            this.header = header;
//            project.count = 0;
//            project.nukList = new ArrayList<>();
//            project.copyList = new ArrayList<>();
//            stepList = new ArrayList<>();
            
            
//		this.project = project;
//		this.height = height;
//		this.width = width;
//                this.nuklList = new ArrayList<BigNukleosomNew>();
//                this.maxTimeSteps = maxTimeSteps;
//                this.stepSize = stepSize;
		
               this.setStyle("-fx-background-color: white;");
                
		setAlignment(Pos.CENTER);
		setHgap(width / (6./3.));
		setVgap(height / (6./1.));
              
            BigNukleosomNew nukl;
                
            for(String nucleosomeKey : nucleosomeMap.keySet()) {
                    HashMap<String, HashMap<String,String>> nucleosome = nucleosomeMap.get(nucleosomeKey);
                    nukl = new BigNukleosomNew(project, nucleosome, Integer.parseInt(nucleosomeKey), 0, width, height, false, false);
                    nukl.setCache(true);
                    nukl.setCacheShape(true);
                    nukl.setCacheHint(CacheHint.SPEED);
                    if(header)
                        add(nukl, Integer.parseInt(nucleosomeKey)+1,numberOfSteps);
                    else
                        add(nukl, Integer.parseInt(nucleosomeKey),numberOfSteps);

                    GridPane.setVgrow(nukl, Priority.ALWAYS);
                    GridPane.setHgrow(nukl, Priority.ALWAYS);
            } 
                
                
            this.setCache(true);
            this.setCacheShape(true);
            this.setCacheHint(CacheHint.SPEED);
            

	}
        
        public ArrayList<BigNukleosomNew> getNuklList() {
            return nuklList;
        }
        
        public double getExportWidth() {

            Bounds bounds = this.getBoundsInLocal();
              
            return bounds.getMaxX() - bounds.getMinX();
        }
        
        public double getExportHeight() {
            
            Bounds bounds = this.getBoundsInLocal();
            
            return bounds.getMaxY() - bounds.getMinY();
        }
            
    public void goUp() {
        
        int add = 10;
        
        int firstRow = Integer.parseInt(getStepList().get(0));
        
        String testY = String.valueOf(firstRow - ((add) * project.stepSize.peek()));
        
        Vector<Node> slideVector = new Vector<>();
        
        int maxCol = 0;
        int maxRow = 0;
        int row = 0;
        int col = 0;

        for(Node node : getChildren()) {

            row = getRowIndex(node);
            col = getColumnIndex(node);

            if(row > maxRow) {
                maxRow = row;
            }

            if(col > maxCol) {
                maxCol = col;
            }
        }
        
        if(Integer.parseInt(testY) <= 0) {
            add = maxRow+1;
            y = String.valueOf(0);
        }
        else {
            y = testY;
        }
            
        for(Node node : getChildren()) {
            row = getRowIndex(node);
            
            if (row <= maxRow - add) {
                slideVector.add(node);
            }
        }
        
        if(add <= 0) {
            return;
        }
        
        Node[][] nodeArray = new Node[maxCol+1][maxRow+1];
            
        for(Node node : slideVector) {
            nodeArray[getColumnIndex(node)][getRowIndex(node) + add] = node;
        }
        
        getChildren().removeAll(getChildren());
        
        setNuklList(new ArrayList<>());
        setStepList(new ArrayList<>());
        
        numberOfSteps = 0;
        
        for(int f = 0; f < add; f++) {
            if(project.timeVector.containsKey(y)) {

                getStepList().add(y);
                
                if(header)
                    add(new PlusMinusLabel(y, project), 0, numberOfSteps);
                else {
                    PlusMinusLabel lab = new PlusMinusLabel(y, project);
                    add(lab, project.timeVector.get(String.valueOf(project.offset.peek())).size(), numberOfSteps);

                    lab.setVisible(false);
                    
                    HashMap<String, HashMap<String,HashMap<String,String>>> nukleomList = project.timeVector.get(y);
                    for(String x : project.timeVector.get(y).keySet()) {
                        HashMap<String,HashMap<String,String>> histoneMap = nukleomList.get(x);
                        for(String histoneNumber : histoneMap.keySet()) { 

                            BigNukleosomNew nukl = new BigNukleosomNew(project,project.timeVector.get(y).get(x), Integer.parseInt(x), Integer.parseInt(y), width, height, false, false);

                            if(header)
                                add(nukl, Integer.parseInt(x)+1,numberOfSteps);
                            else
                                add(nukl, Integer.parseInt(x),numberOfSteps);


                            getNuklList().add(nukl);
                        }
                    } 
                }
                numberOfSteps++;
                y = String.valueOf(Integer.parseInt(y) + project.stepSize.peek());
            }
        }  
        
        for(row = add; row <= maxRow; row++) {
            for(col = 0; col <= maxCol; col++) {
                if(nodeArray[col][row] instanceof BigNukleosomNew) {
                    BigNukleosomNew nukleosome = (BigNukleosomNew)nodeArray[col][row];
                    this.add(nukleosome, col, row);
                    getNuklList().add(nukleosome);
                }
                else {
                    if(nodeArray[col][row] instanceof PlusMinusLabel) {
                        PlusMinusLabel lab = (PlusMinusLabel)nodeArray[col][row];
                        getStepList().add(lab.getValueString());
                    }
                    this.add(nodeArray[col][row], col, row);
                }
            }
        } 
        checkFirstAndLast();
    }

    public void goDown() {
        int add = 10;
        
        int testY = Integer.parseInt(getStepList().get(getStepList().size()-1)) + (add * project.stepSize.peek());
        
        if(add == 0) {
            return;
        }
        
        y = getStepList().get(getStepList().size()-1);
        
        Vector<Node> slideVector = new Vector<>();
        
        int maxCol = 0;
        int maxRow = 0;
        int row = 0;
        int col = 0;

        for(Node node : getChildren()) {

            row = getRowIndex(node);
            col = getColumnIndex(node);

            if(row > maxRow) {
                maxRow = row;
            }

            if(col > maxCol) {
                maxCol = col;
            }

            if (row >= add) {
                slideVector.add(node);
            }
        }
            
        Node[][] nodeArray = new Node[maxCol+1][maxRow+1];
            
        for(Node node : slideVector) {
            nodeArray[getColumnIndex(node)][getRowIndex(node)-add] = node;
        }
        
        getChildren().removeAll(getChildren());
        
        if(testY >= Integer.parseInt(project.getLastItemInTimeMap())) {
            add = maxRow+1;
        }
        
        setNuklList(new ArrayList<>());
        setStepList(new ArrayList<>());
        numberOfSteps = 0;
        
        for(row = 0; row < maxRow-(add-1); row++) {
            for(col = 0; col <= maxCol; col++) {
                if(nodeArray[col][row] instanceof BigNukleosomNew) {
                    BigNukleosomNew nukleosome = (BigNukleosomNew)nodeArray[col][row];
                    this.add(nukleosome, col, row);
                    getNuklList().add(nukleosome);
                }
                else {
                    if(nodeArray[col][row] instanceof PlusMinusLabel) {
                        PlusMinusLabel lab = (PlusMinusLabel)nodeArray[col][row];
                        getStepList().add(lab.getValueString());
                    }
                    this.add(nodeArray[col][row], col, row);
                }
            }
            numberOfSteps++;
        } 
        
        if(testY >= Integer.parseInt(project.getLastItemInTimeMap())) {
            y = String.valueOf(Integer.parseInt(project.getLastItemInTimeMap()) - add * project.stepSize.peek());
        }
            
        
        for(int f = 0; f < add; f++) {

            y = String.valueOf(Integer.parseInt(y) + project.stepSize.peek());
            if(project.timeVector.containsKey(y)) {

                getStepList().add(y);
                
                if(header)
                    add(new PlusMinusLabel(y, project),0, numberOfSteps);
                else {
                    PlusMinusLabel lab = new PlusMinusLabel(y, project);
                    add(lab, project.timeVector.get(y).size(), numberOfSteps);
                    lab.setVisible(false);
                    
                    HashMap<String, HashMap<String,HashMap<String,String>>> nukleomList = project.timeVector.get(y);
                    for(String x : project.timeVector.get(y).keySet()) {
                        HashMap<String,HashMap<String,String>> histoneMap = nukleomList.get(x);
                        for(String histoneNumber : histoneMap.keySet()) { 

                            BigNukleosomNew nukl = new BigNukleosomNew(project,project.timeVector.get(y).get(x), Integer.parseInt(x), Integer.parseInt(y), width, height, false, false);

                            if(header)
                                add(nukl, Integer.parseInt(x)+1,numberOfSteps);
                            else
                                add(nukl, Integer.parseInt(x),numberOfSteps);

                            getNuklList().add(nukl);
                        }
                    } 
                }
                numberOfSteps++;
            }
        }   
        checkFirstAndLast();
    }

    /**
     * @param nuklList the nuklList to set
     */
    public void setNuklList(ArrayList<BigNukleosomNew> nuklList) {
        this.nuklList = nuklList;
    }

    /**
     * @return the up
     */
    public Button getUp() {
        return up;
    }

    /**
     * @param up the up to set
     */
    public void setUp(Button up) {
        this.up = up;
    }

    /**
     * @return the down
     */
    public Button getDown() {
        return down;
    }

    /**
     * @param down the down to set
     */
    public void setDown(Button down) {
        this.down = down;
    }

    /**
     * @return the foundFirst
     */
    public boolean isFoundFirst() {
        return foundFirst;
    }

    /**
     * @param foundFirst the foundFirst to set
     */
    public void setFoundFirst(boolean foundFirst) {
        this.foundFirst = foundFirst;
    }

    /**
     * @return the foundLast
     */
    public boolean isFoundLast() {
        return foundLast;
    }

    /**
     * @param foundLast the foundLast to set
     */
    public void setFoundLast(boolean foundLast) {
        this.foundLast = foundLast;
    }
    
    public void checkFirstAndLast() {
        foundFirst = false;
        foundLast = false;
        for(String step : getStepList()) {
            if(step.equals(project.getFirstItemInTimeMap())){
                foundFirst = true;
            }
            if(step.equals(project.getLastItemInTimeMap())){
                foundLast = true;
            }
        }
    }

    /**
     * @return the stepList
     */
    public ArrayList<String> getStepList() {
        return stepList;
    }

    /**
     * @param stepList the stepList to set
     */
    public void setStepList(ArrayList<String> stepList) {
        this.stepList = stepList;
    }
    
//    public void createOverlayMap() {
//        overlayMap = new HashMap();
//        
//        ArrayList<String> tempStepList = getStepList();
//        
//        for(String keyString : tempStepList) {
//            HeatReader heatReader = project.getChromosom().getHeatProject().getHeatReader();
//            heatReader.readLogFile(keyString);
//
//            HashMap<String, ArrayList<ArrayList<Double>>> timeMap = heatReader.getTimeMap();
//            ArrayList<ArrayList<Double>> timeStep = timeMap.get(keyString);
//            
//            int[] overlayTimeStep = new int[timeStep.get(0).size()];
//            
//            for(int x = 0; x < timeStep.get(0).size(); x++) {
//                overlayTimeStep[x] = 0;
//                for(int y = 0; y < timeStep.size(); y++) {
//                    if(y%2 != 0 && timeStep.get(y).get(x) != 0.0) {
//                        overlayTimeStep[x] = 1;
//                        break;
//                    }
//                }
//            } 
//            
//            overlayMap.put(keyString, overlayTimeStep);
//        }
//    }

    public void highlightOverlay() {
        
        if(isOverlayed() == false) {
            
            
            if(project.getNukleosomReader().getOverlayMap() != null && !project.getNukleosomReader().getOverlayMap().isEmpty() && firstOverlayed) {
            }
            else {
//                createOverlayMap();
                
                for(int i = 0; i < nuklList.size();i++) {
                    BigNukleosomNew nukl = nuklList.get(i);
                    
                    if(project.getNukleosomReader().getOverlayMap().get(String.valueOf(nukl.getY()))[nukl.getX()] != -1) {
                           
                        Bounds nuklBounds = nukl.getBoundsInParent();
                        Rectangle rect = new Rectangle(nuklBounds.getMinX(), nuklBounds.getMinY(), nuklBounds.getWidth(), nuklBounds.getHeight());
                        rect.setFill(new Color(0.8,0.8,0,0.3));
                        rect.setMouseTransparent(true);

                        getEnzymeGroup().getChildren().add(rect);
                        
                    }
                }
                Bounds bounds = getEnzymeGroup().getBoundsInParent();
                Translate trans = new Translate(-(bounds.getMinX()), -(bounds.getMinY())   +10 );
                getEnzymeGroup().getTransforms().add(trans);
            }

            getEnzymeGroup().setVisible(true);
            setOverlayed(true);
            firstOverlayed = true;

        }
        else {
            getEnzymeGroup().setVisible(false);
            setOverlayed(false);   
        }
    }

    /**
     * @return the overlay
     */
    public boolean isOverlayed() {
        return overlayed;
    }

    /**
     * @param overlay the overlay to set
     */
    public void setOverlayed(boolean overlay) {
        this.overlayed = overlay;
    }

    /**
     * @return the enzymeGroup
     */
    public Group getEnzymeGroup() {
        return enzymeGroup;
    }

    /**
     * @param enzymeGroup the enzymeGroup to set
     */
    public void setEnzymeGroup(Group enzymeGroup) {
        this.enzymeGroup = enzymeGroup;
    }
}