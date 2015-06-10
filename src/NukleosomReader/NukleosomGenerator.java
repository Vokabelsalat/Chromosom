package NukleosomReader;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class NukleosomGenerator {

	static int attr = 5;
	static int attributeNumber = 20;
	static int histoneNumber = 1;
	static int nukleosomNumber = 101;
	static int timeNumber = 1000;
	
	static int attributeArray[];
	static ArrayList<int[]> histonList;
	static ArrayList<ArrayList<int[]>> nukleosomList;
	static Vector<ArrayList<ArrayList<int[]>>> timeVector = new Vector<ArrayList<ArrayList<int[]>>>();
	
	public static Vector<ArrayList<ArrayList<int[]>>> getGeneratedData() {
		Random rnd = new Random();
		
		for(int time = 0; time < timeNumber; time++) {
			nukleosomList = new ArrayList<ArrayList<int[]>>();
			for(int nukleosom = 0; nukleosom < nukleosomNumber; nukleosom++) {
				histonList = new ArrayList<int[]>();
				for(int histone = 0; histone < histoneNumber; histone++) {
					attributeArray = new int[attributeNumber];
					for(int attribute = 0; attribute < attributeNumber; attribute++) {
						attributeArray[attribute] = rnd.nextInt(attr);
					}
					histonList.add(attributeArray);
				}
				nukleosomList.add(histonList);
			}
			timeVector.add(nukleosomList);
		}
		
		for(ArrayList<ArrayList<int[]>> list : timeVector) {
			for(ArrayList<int[]> list2 : list) {
				for(int[] array : list2) {
					for(int num : array) {
//						System.err.print(num);
					}
//					System.err.print(" ");
				}
			}
//			System.err.println();
		}
		
		return timeVector;
	}
}
