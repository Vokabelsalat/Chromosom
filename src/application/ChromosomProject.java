package application;

import ChromosomEditor.HistoneTable;
import Nukleosom.BigNukleosomNew;
import Nukleosom.ModificationColors;
import NukleosomReader.NukleosomReader;
import java.io.File;
import java.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Stack;

/**
 * Projektklasse welche alle wichtigen Einstellungen für einen Anwendungsfall hält und verwaltet
 * @author Jakob
 */
public class ChromosomProject {

	public int number = 10000,
		nukleosomMinWidth = 6, nukleosomMinHeight = 6,
                nukleosomWidth = 6, nukleosomHeight = 6,
		histoneNumber = 1,
		scale = 4;	
	private String defaultFileName = "outfile.txt";
	List<String> dataList;
	private int sunburstHeight = 50;
	private int sunburstWidth = 50;
        private List fileLines;
        public  HashMap<String, HashMap<String, HashMap<String,HashMap<String,String>>>> timeVector;
        private HashMap<String, String[]> metaInformations = new HashMap<>();
        private BigNukleosomNew leftNukleosom;
        private BigNukleosomNew rightNukleosom;
        private BigNukleosomNew oldNukleosom;
        public int count = 0;
        public ArrayList<String> nukList = new ArrayList<>(); 
        public ArrayList<String> copyList = new ArrayList<>(); 
        public int maxX = 0;
        public int maxY = 0;
        private File outputFile;
        
        public double maxNuclWidth, maxNuclHeight = 0;
        
        public double minNuclHeight = 0;
        
        private String initialStateText;
        
        private HashMap<String, HashMap<String, HashMap<String, String>>> specialColorMap;
        
        private String empty_nucleosome = "";
        
        private NukleosomReader nukleosomReader;
        
        public Stack<Integer> stepSize = new Stack<>();
        
        public Stack<Integer> offset = new Stack();
	
        public Stack<Integer> stepsToShow = new Stack();
        
        public Stack<Integer> maxTimeSteps = new Stack();
        
        public Stack<Integer> rootRow = new Stack();
        
        public Stack<Integer> reservoirStack = new Stack();
        
        public String selectedTabName = "";
        
        public Chromosom chromosom;
        
        public boolean showNukleosoms = false;
        
        private boolean cyclic = false;
        
        private boolean propMatrices = false;
        
        private boolean showEmptySites = false;
        
        private String simulationTimeType;
        
        private int simulationTime;
        
        private File outputDirectory;
        
        private HashMap<String, HistoneTable> histoneModificationMap;
        private LinkedHashMap<String, String[][]> histoneMap;
        private File chromosomProjectDirectory;
        private ModificationColors modificationColors;
        private File initialStateFile;
        private File parameterFile;
        private File histoneMapFile;
        private File rulesetFile;
        
	public ChromosomProject(Chromosom chromosom) {

            offset.push(0);
            stepsToShow.push(chromosom.getStepsToShowValue());
            rootRow.push(0);

            this.chromosom = chromosom; 
            
            modificationColors = new ModificationColors();
            
	}
	
	public String getDefaultFileName() {
		return defaultFileName;
	}

	public void setDefaultFileName(String defaultFileName) {
		this.defaultFileName = defaultFileName;
	}
	
	public int getNukleosomWidth() {
		return nukleosomWidth;
	}

	public void setNukleosomWidth(int nukleosomWidth) {
		this.nukleosomWidth = nukleosomWidth;
	}

	public int getNukleosomHeight() {
		return nukleosomHeight;
	}

	public void setNukleosomHeight(int nukleosomHeight) {
		this.nukleosomHeight = nukleosomHeight;
	}
	
	public int getHistoneNumber() {
		return histoneNumber;
	}

	public void setHistoneNumber(int histoneNumber) {
		this.histoneNumber = histoneNumber;
	}

	public int getSunburstWidth() {
		// TODO Auto-generated method stub
		return sunburstWidth;
	}

	public int getSunburstHeight() {
		// TODO Auto-generated method stub
		return sunburstHeight;
	}
	
	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
        
        public void setSelectedTabName(String selectedTabName) {
            this.selectedTabName = selectedTabName;
        }
        
        public String getSelectedTabName() {
            return selectedTabName;
        }
        
        public HashMap<String, HashMap<String, HashMap<String,HashMap<String,String>>>>  getTimeVector(){
            return timeVector;
        } 
        
        public void setTimeVector( HashMap<String, HashMap<String, HashMap<String,HashMap<String,String>>>> timeVector) {
            this.timeVector = timeVector;
        } 
        
        public List<String> getFileLines() {
            return fileLines;
        }
        
        public void setFileLines(ArrayList<String> fileLines) {
            this.fileLines = fileLines;
        }
        
    public int getMaxY() {
        //TODO Muss noch mit FUnktion gefüllt werden
        return 2;
    }

    public int getMaxX() {
        //TODO Muss noch mit FUnktion gefüllt werden
        return 2;
    }

    public void zoomIn(int newOffset) {
        chromosom.zoomIn(newOffset);
    }

    public void zoomOut() {
        chromosom.zoomOut();
    }

    public Chromosom getChromosom() {
        return chromosom;
    }

    public void setMetaInformations(HashMap<String, String[]> metaInformations) {
        this.metaInformations = metaInformations;
    }

    /**
     * @return the metaInformations
     */
    public HashMap<String, String[]> getMetaInformations() {
        return metaInformations;
    }

    /**
     * @return the leftNukleosom
     */
    public BigNukleosomNew getLeftNukleosom() {
        return leftNukleosom;
    }

    /**
     * @param leftNukleosom the leftNukleosom to set
     */
    public void setLeftNukleosom(BigNukleosomNew leftNukleosom) {
        this.leftNukleosom = leftNukleosom;
    }

    /**
     * @return the rightNukleosom
     */
    public BigNukleosomNew getRightNukleosom() {
        return rightNukleosom;
    }

    /**
     * @param rightNukleosom the rightNukleosom to set
     */
    public void setRightNukleosom(BigNukleosomNew rightNukleosom) {
        this.rightNukleosom = rightNukleosom;
    }

    public BigNukleosomNew getOldNukleosom() {
        return oldNukleosom;
    }

    /**
     * @param oldNukleosom the oldNukleosom to set
     */
    public void setOldNukleosom(BigNukleosomNew oldNukleosom) {
        this.oldNukleosom = oldNukleosom;
    }

    /**
     * @return the histoneModificationTable
     */
    public HashMap<String, HistoneTable> getHistoneModificationMap() {
        
        return histoneModificationMap;
    }

    /**
     * @param histoneModificationTable the histoneModificationTable to set
     */
    public void setHistoneModificationMap(HashMap<String, HistoneTable> histoneModificationTable) {
        this.histoneModificationMap = histoneModificationTable;
    }

    public void setHistoneMap(LinkedHashMap<String, String[][]> histoneMap) {
        this.histoneMap = histoneMap;
    }

    /**
     * @return the histoneMap
     */
    public LinkedHashMap<String, String[][]> getHistoneMap() {
        
        return histoneMap;
    }


    /**
     * @return the modificationColors
     */
    public ModificationColors getModificationColors() {
        return modificationColors;
    }

    /**
     * @param modificationColors the modificationColors to set
     */
    public void setModificationColors(ModificationColors modificationColors) {
        this.modificationColors = modificationColors;
    }

    /**
     * @return the nukleosomReader
     */
    public NukleosomReader getNukleosomReader() {
        return nukleosomReader;
    }

    /**
     * @param nukleosomReader the nukleosomReader to set
     */
    public void setNukleosomReader(NukleosomReader nukleosomReader) {
        this.nukleosomReader = nukleosomReader;
    }
    
    /**
     * Gibt das erste alphanumerische Element
     * @return 
     */
    public String getFirstItemInTimeMap() {
        String returnStr = "";
        for(int i = 0; i < 100000; i++) {
            returnStr = String.valueOf(i);
            if(timeVector.containsKey(returnStr) && timeVector != null) {
                break;
            }
        }
        return returnStr;
    }
    
    /**
     * GIbt das letzte alphanumerische Element zurück
     * @return 
     */
    public String getLastItemInTimeMap() {
        int max = 0;
        for(String key : timeVector.keySet()) {
            if(Integer.parseInt(key) > max) {
                max = Integer.parseInt(key);
            }
        }
        return String.valueOf(max);
    }

    /**
     * @return the empty_nucleosome
     */
    public String getEmpty_nucleosome() {
        if(empty_nucleosome.equals("") && histoneMap != null) {
            for(String key : histoneMap.keySet()) {
                if(histoneMap.get(key) != null) {
                    String[][] array = histoneMap.get(key);
                    for(int y = 0; y < array[0].length; y++) {
                        for(int x = 0; x < array.length; x++) {
                            if(!array[x][y].equals("")) {
                                empty_nucleosome += key + "[" + array[x][y] + ".un]";
                            }
                        }
                    }
                }
            }
        }
        
        return empty_nucleosome;
    }
    
    /**
     * @param empty_nucleosome the empty_nucleosome to set
     */
    public void setEmpty_nucleosome(String empty_nucleosome) {
        this.empty_nucleosome = empty_nucleosome;
    }

    /**
     * @return the showEmptySites
     */
    public boolean isShowEmptySites() {
        return showEmptySites;
    }

    /**
     * @param showEmptySites the showEmptySites to set
     */
    public void setShowEmptySites(boolean showEmptySites) {
        this.showEmptySites = showEmptySites;
    }

    /**
     * @return the specialColorMap
     */
    public HashMap<String, HashMap<String, HashMap<String, String>>> getSpecialColorMap() {
        return specialColorMap;
    }

    /**
     * @param specialColorMap the specialColorMap to set
     */
    public void setSpecialColorMap(HashMap<String, HashMap<String, HashMap<String, String>>> specialColorMap) {
        this.specialColorMap = specialColorMap;
    }

    public void setInitialState(String text) {
        initialStateText = text;
    }
    
    public String getInitialState() {
        return initialStateText;
    }

    /**
     * @return the initialStateFile
     */
    public File getInitialStateFile() {
        return initialStateFile;
    }

    /**
     * @param initialStateFile the initialStateFile to set
     */
    public void setInitialStateFile(File initialStateFile) {
        this.initialStateFile = initialStateFile;
    }

    /**
     * @return the parameterFile
     */
    public File getParameterFile() {
        return parameterFile;
    }

    /**
     * @param parameterFile the parameterFile to set
     */
    public void setParameterFile(File parameterFile) {
        this.parameterFile = parameterFile;
    }

    /**
     * @return the histoneMapFile
     */
    public File getHistoneMapFile() {
        return histoneMapFile;
    }

    /**
     * @param histoneMapFile the histoneMapFile to set
     */
    public void setHistoneMapFile(File histoneMapFile) {
        this.histoneMapFile = histoneMapFile;
    }

    /**
     * @return the rulesetFile
     */
    public File getRulesetFile() {
        return rulesetFile;
    }

    /**
     * @param rulesetFile the rulesetFile to set
     */
    public void setRulesetFile(File rulesetFile) {
        this.rulesetFile = rulesetFile;
    }

    /**
     * @return the cyclic
     */
    public boolean isCyclic() {
        return cyclic;
    }

    /**
     * @param cyclic the cyclic to set
     */
    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
    }

    /**
     * @return the propMatrices
     */
    public boolean isPropMatrices() {
        return propMatrices;
    }

    /**
     * @param propMatrices the propMatrices to set
     */
    public void setPropMatrices(boolean propMatrices) {
        this.propMatrices = propMatrices;
    }

    /**
     * @return the outputDirectory
     */
    public File getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * @param outputDirectory the outputDirectory to set
     */
    public void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * @return the simulationTimeType
     */
    public String getSimulationTimeType() {
        return simulationTimeType;
    }

    /**
     * @param simulationTimeType the simulationTimeType to set
     */
    public void setSimulationTimeType(String simulationTimeType) {
        this.simulationTimeType = simulationTimeType;
    }

    /**
     * @return the simulationTime
     */
    public int getSimulationTime() {
        return simulationTime;
    }

    /**
     * @param simulationTime the simulationTime to set
     */
    public void setSimulationTime(int simulationTime) {
        this.simulationTime = simulationTime;
    }

    /**
     * @return the chromosomProjectDirectory
     */
    public File getChromosomProjectDirectory() {
        return chromosomProjectDirectory;
    }

    /**
     * @param chromosomProjectDirectory the chromosomProjectDirectory to set
     */
    public void setChromosomProjectDirectory(File chromosomProjectDirectory) {
        this.chromosomProjectDirectory = chromosomProjectDirectory;
    }

    /**
     * @return the outputFile
     */
    public File getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }
    
}
