package application;

import java.util.List;

import javafx.scene.paint.Color;
import NukleosomReader.NukleosomReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class ChromosomProject {

	int number = 10000, offset = 2,
		nukleosomWidth = 6, nukleosomHeight = 6,
		histoneNumber = 1,
		scale = 4;	
	String defaultFileName = "ES_segmentation_wholedata.data";
	List<String> dataList;
	private int sunburstHeight = 50;
	private int sunburstWidth = 50;
        private ArrayList fileLines;
        public  Vector<ArrayList< HashMap<String,HashMap<String,Integer>>>> timeVector;
        
        public String selectedTabName = "";
	
//	public static Color color0 = Color.rgb(237, 124, 36);
        public static Color color0 = Color.rgb(255, 255, 255);
	public static Color color1 = Color.rgb(60, 58, 245);
	public static Color color2 = Color.rgb(165, 164, 249);
	public static Color color3 = Color.rgb(255, 184, 122);
	public static Color color4 = Color.rgb(237, 74, 65);
	
	public ChromosomProject() {
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
        
        public Vector<ArrayList< HashMap<String,HashMap<String,Integer>>>> getTimeVector(){
            return timeVector;
        } 
        
        public void setTimeVector( Vector<ArrayList< HashMap<String,HashMap<String,Integer>>>> timeVector) {
            this.timeVector = timeVector;
        } 
        
        public ArrayList<String> getFileLines() {
            return fileLines;
        }
        
        public void setFileLines(ArrayList<String> fileLines) {
            this.fileLines = fileLines;
        }
}
