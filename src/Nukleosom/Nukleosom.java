package Nukleosom;

import java.util.Arrays;
import java.util.List;

import application.ChromosomProject;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Nukleosom extends GridPane {
	
	int[] array;
	
	public Nukleosom(int[] array, int inWidth, int inHeight) {
		
		this.array = array;
		
		int x = 0, y = 0, maxX = 3, leafCounter = 0, height = inHeight, width = inWidth;
		List<Integer> leafOut = null;
		
		if(array.length == 4) 
			maxX = 2;
		else if(array.length == 5)
			maxX = 5;
		else if(array.length >= 6 && array.length < 12)
			maxX = 3;
		else if(array.length >= 12 && array.length <= 20)
			maxX = 4;
		else if(array.length > 20)
			maxX = 5;
		
		switch (array.length) {
			case 8: leafOut = Arrays.asList(4); break;
			case 24: leafOut = Arrays.asList(12); break;
		}
		
		for(int i = 0; i < array.length; i++) {
			
			Rectangle rect = null;
			
			x=i;
			
//			if(array[i] == 0) {
//				rect = new Rectangle(height, width,Color.web("#FF9933")); rect.setOpacity(1.0);
//			}
//			else if(array[i] == 1) {
//				rect = new Rectangle(height, width,Color.web("#3333FF")); rect.setOpacity(1.0);
//			}
//			else if(array[i] == 2) {
//				rect = new Rectangle(height, width,Color.web("#3333FF")); rect.setOpacity(0.45);
//			}
//			else if(array[i] == 3) {
//				rect = new Rectangle(height, width,Color.web("#FF9933")); rect.setOpacity(0.30);
//			}
//			else if(array[i] == 4) {
//				rect = new Rectangle(height, width,Color.web("#E84C3C")); rect.setOpacity(1.0);
//			}
			
			Paint color = Color.BLACK;
				
				if(array[i] == 0) {
//					color = Color.web("#FF9933");
					color = ChromosomProject.color0;
				}
				else if(array[i] == 1) {
//					color = Color.web("#3333FF");
					color = ChromosomProject.color1;
				}
				else if(array[i] == 2) {
//					color = Color.web("#3333FF");
					color = ChromosomProject.color2;
				}
				else if(array[i] == 3) {
//					color = Color.web("#FF9933");
					color = ChromosomProject.color3;
				}
				else if(array[i] == 4) {
//					color = Color.web("#E84C3C");
					color = ChromosomProject.color4;
				}
			
			rect = new Rectangle(height, width,color); rect.setOpacity(1.0);
			
			
//			switch(array[i]) {
//				
//			
//			case 0: rect = new Rectangle(height, width,Color.web("#FF9933")); rect.setOpacity(1.0); break;
//			case 2: rect = new Rectangle(height, width,Color.web("#FF9933")); rect.setOpacity(0.40); break;
//			case 3: rect = new Rectangle(height, width,Color.web("#FF9933")); rect.setOpacity(0.0); break;
//			case 4: rect = new Rectangle(height, width,Color.web("#3333FF")); rect.setOpacity(0.40); break;
//			case 1: rect = new Rectangle(height, width,Color.web("#3333FF")); rect.setOpacity(1.0); break;
//			
//			}
			
			if(leafOut != null) {
				
//				if(leafOut.contains(x)) {
//					rect = new Rectangle(height, width,Color.web("#FF9933")); rect.setOpacity(0.0);
//				}	
				
				if(leafOut.contains(x)) {
					leafCounter = leafCounter + 1;
				}	
				
				if(leafCounter > 0) {
					if(i>=(leafOut.get(leafCounter-1))) {
						x = x + leafCounter;
					}
				}
				
				while(leafOut.contains(x)) {
					x = x + 1;
				}
			}
			rect.setX((x % maxX) * width);
                        rect.setY(y * height);
                        rect.setStroke(Color.BLACK);
			add(rect, (x % maxX),y);

			
			if( (x+1) % maxX == 0)
				y++;
		}
		
//		setGridLinesVisible(true);
		setPrefSize(maxX * width, y * height);
	}
}
	
//	public class Nukleosom extends JPanel {
//		
//		double[] array;	
//	public Nukleosom(double array[], int inWidth, int inHeight) {
//		this.setLayout(new GridLayout(3,3));
//		
//		for(double value : array) {
//			this.add(new Nukleos(value, inWidth, inHeight));
//		}
//		
//		this.setPreferredSize(new Dimension(21,21));
//	}
//	
//	public double[] getArray() {
//		return array;
//	}
//
//	
//	private class Nukleos extends JPanel{
//
//		int inWidth, inHeight;
//		double value;
//		
//		public Nukleos(double value, int inWidth, int inHeight) {
//			
//			this.inWidth = inWidth;
//			this.inHeight = inHeight;
//			this.value = value;
//			
//			this.setPreferredSize(new Dimension(inWidth, inHeight));
//		}
//	
//		 public void paint(Graphics g) {
//			 Graphics2D g2 = (Graphics2D) g;
//		    
//		    Color color = Color.WHITE;
//		    
//		    if(value == 0.0) {
//		    	color = Color.BLUE;
//		    }
//		    else if(value == 1.0) {
//		    	color = Color.RED;
//		    }
//		    else if(value <= 0.5) {
//		    	color = Color.CYAN;
//		    }
//		    else if(value >= 0.5) {
//		    	color = Color.ORANGE;
//		    }
//		    	
//		    g2.setColor(color);
//		    g2.fillRect (0, 0, inWidth, inHeight); 
//		}
//}
//
//	public static void main(String args[]) {
//		JFrame frame = new JFrame("AWTTEST");
//		
//		JPanel pan = new JPanel();
//		pan.setLayout(new BorderLayout());
//		
//		ChromosomProject project = new ChromosomProject();
//		
//		double array[] = {0.0,0.5,0.3,0.7,1.0,0.9,0.0,0.5,0.6};
//		
//		pan.add(new Nukleosom(array, 7, 7), BorderLayout.CENTER);
//		
//		frame.add(pan);
//		frame.setVisible(true);
//		frame.setSize(new Dimension(1280,800));
//		
//		ExportDialog export = new ExportDialog();
//	    export.showExportDialog( frame, "Export view as ...", frame, "export" );
//	}
//}