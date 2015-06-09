package Nukleosom;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.freehep.util.export.ExportDialog;

import NukleosomReader.NukleosomReader;
import application.ChromosomProject;

public class AWTTest extends JPanel{

	public AWTTest(ChromosomProject project, int X, int Y, int height, int width) {
		
		this.setLayout(new GridLayout(X,Y));
		
		int 	maxX = X,
				number = maxX * Y,
				y = 0,  x = 0,
				offset = 2;
		
		List<String> returnList = project.getReadedNukleosoms();
		
		for(int i = 0; i < number; i++) {
			
			List<double[]> valueList = new ArrayList<double[]>();
			
			for(int u = 0; u < project.getHistoneNumber(); u++) {
				
				valueList.add(NukleosomReader.getValueArray(returnList.get(offset)));
				offset++;
			}
			
			this.add(new BigNukleosom(valueList, height, width));
		}
	}
	
	public static void main(String args[]) {
		
		JFrame frame = new JFrame("AWTTEST");
		
		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout());
		
		ChromosomProject project = new ChromosomProject();
		
		double array[] = {0.0,0.5,0.3,0.7,1.0,0.9,0.0,0.5,0.6};
		
		List<double[]> valueList = new ArrayList<double[]>();
		valueList.add(array);
		
		BigNukleosomRow row = new BigNukleosomRow(project,2,2,project.getNukleosomWidth(), project.getNukleosomHeight());
		
		//pan.add(row);
		
		frame.add(pan);
		frame.setVisible(true);
		frame.setSize(new Dimension(1280,800));
		
//		row.export();
	}
	
}
