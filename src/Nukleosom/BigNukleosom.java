package Nukleosom;

import java.util.List;

import javafx.scene.layout.GridPane;

public class BigNukleosom extends GridPane{
	
	int maxX = 2, maxY = 2;
	
	public BigNukleosom(List<int[]> valueList, int width, int height) {
		
		int x = 0;
		int y = 0;
		
//		setHgap(width / (7/3.));
//		setVgap(height / (7/3.));
				
		if(valueList.size() == 4) {
			maxX = 2;
		}
		else if(valueList.size() == 3) {
			maxX = 2;
		}
		else if(valueList.size() == 2) {
			maxX = 1;
		}
		else if(valueList.size() == 1) {
			maxX = 1;
			maxY = 1;
		}
		
//		setPrefColumns(2);
		
		for(int i = 0; i < valueList.size(); i++) {
			
			if((x) % maxX == 0) {
				y++;
				x = 0;
			}
			x++;
			
			getChildren().add(new Nukleosom(valueList.get(i), width, height));
		}
		 
		
		Nukleosom nukl = new Nukleosom(valueList.get(0), width, height);
		setPrefWidth(nukl.getPrefWidth()/* * maxX + (getHgap() * (maxX))*/);
		setPrefHeight(nukl.getPrefHeight()/* * maxY + (getVgap() * (maxY ))*/);
	}
	
	public int getMaxY() {
		return maxY;
	}

	public int getMaxX() {
		return maxX;
	}
	
}




//public class BigNukleosom extends JPanel{
//	
//	int maxX = 2, maxY = 2;	
//public BigNukleosom(List<double[]> valueList, int height, int width) {
//	this.setLayout(new GridLayout(maxX, maxY));
////	valueList.size()
//	for(int i = 0; i < 1; i++) {
//		add(new Nukleosom(valueList.get(i),height, width));
//	}
//	
//}
//
//public static void main(String args[]) {
//	JFrame frame = new JFrame("AWTTEST");
//	
//	JPanel pan = new JPanel();
//	pan.setLayout(new BorderLayout());
//	
//	ChromosomProject project = new ChromosomProject();
//	
//	
//	double array[] = {0.0,0.5,0.3,0.7,1.0,0.9,0.0,0.5,0.6};
//	
//	List<double[]> valueList = new ArrayList<double[]>();
//	valueList.add(array);
//	
//	pan.add(new BigNukleosom(valueList, 7, 7), BorderLayout.CENTER);
//	
//	frame.add(pan);
//	frame.setVisible(true);
//	frame.setSize(new Dimension(1280,800));
//	
//	ExportDialog export = new ExportDialog();
//    export.showExportDialog( frame, "Export view as ...", frame, "export" );
//}
//
//}
