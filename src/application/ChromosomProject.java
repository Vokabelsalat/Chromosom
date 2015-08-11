package application;

import java.util.List;

import javafx.scene.paint.Color;
import NukleosomReader.NukleosomReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ChromosomProject {

        Map<String,HashMap<String,int[]>> histoneProperties = new HashMap<String,HashMap<String,int[]>>();
    
	int number = 10000, offset = 0,
		nukleosomWidth = 6, nukleosomHeight = 6,
		histoneNumber = 1,
		scale = 4;	
	String defaultFileName = "ES_segmentation_wholedata.data";
	List<String> dataList;
	private int sunburstHeight = 50;
	private int sunburstWidth = 50;
        private List fileLines;
        public  HashMap<String, HashMap<String, HashMap<String,HashMap<String,Integer>>>> timeVector;
        
        public String selectedTabName = "";
	
        public static Color color0 = Color.rgb(255, 255, 255); //Weiß
	public static Color color1 = Color.rgb(60, 58, 245); //Blau
	public static Color color2 = Color.rgb(165, 164, 249); //Lila
	public static Color color3 = Color.rgb(255, 184, 122); //Orange
	public static Color color4 = Color.rgb(237, 74, 65); //Rot
        
        public static int maxTimeSteps = 500;
                
	public ChromosomProject() {

           int h34Array[] = {0,0};
           int h327Array[] = {1,0};

           int h44Array[] = {0,0};
           int h427Array[] = {1,0};

           Map<String, int[]> h3arrayMap = new HashMap<String, int[]>();
           Map<String, int[]> h4arrayMap = new HashMap<String, int[]>();

           h3arrayMap.put("4",h34Array);
           h3arrayMap.put("27",h327Array);
           
           h4arrayMap.put("4",h44Array);
           h4arrayMap.put("27",h327Array);
           
           histoneProperties.put("3", (HashMap<String, int[]>) h3arrayMap);
           histoneProperties.put("4", (HashMap<String, int[]>) h4arrayMap);
           
//           System.err.println("histone: " + histoneProperties);
           
		//readNukleosoms(defaultFileName, offset, number);
	}
	
//	public List<String> getReadedNukleosoms() {
//		if(dataList == null) { 
//			dataList = NukleosomReader.readNukleosoms(defaultFileName, offset, number);
//		}
//		return dataList;
//	}
//	
//	public List<String> readNukleosoms(String fileName, int offset,int number) {
//			dataList = NukleosomReader.readNukleosoms(fileName, offset, number);
//			return dataList;
//	}
	
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
        
        public HashMap<String, HashMap<String, HashMap<String,HashMap<String,Integer>>>>  getTimeVector(){
            return timeVector;
        } 
        
        public void setTimeVector( HashMap<String, HashMap<String, HashMap<String,HashMap<String,Integer>>>> timeVector) {
            this.timeVector = timeVector;
        } 
        
        public List<String> getFileLines() {
            return fileLines;
        }
        
        public void setFileLines(ArrayList<String> fileLines) {
            this.fileLines = fileLines;
        }
        
        public Map<String,HashMap<String,int[]>> getHistoneProperties() {
            return histoneProperties;
        }
        
        public void setMaxTimeSteps(int maxTimeSteps) {
            this.maxTimeSteps = maxTimeSteps;
        }

    public int getMaxY() {
        //TODO Muss noch mit FUnktion gefüllt werden
        return 2;
    }

    public int getMaxX() {
        //TODO Muss noch mit FUnktion gefüllt werden
        return 2;
    }

    public int getMaxTimeSteps() {
        return maxTimeSteps;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public int getOffset() {
        return offset;
    }
}
