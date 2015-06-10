//package test;
//
//import java.awt.BorderLayout;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Polygon;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import org.freehep.util.export.ExportDialog;
//
//public class goHep extends JPanel {
//
//	   public static void main(String args[]) {
//		   
//		   JFrame frame = new JFrame();
//		   frame.setSize(400,300);
//		   
//		   JPanel pan = new JPanel();
//		   pan.setLayout(new BorderLayout());
//		   goHep hep = new goHep();
//
//		   pan.add(hep, BorderLayout.CENTER);
//		   
//		   frame.add(pan);
//		   frame.setVisible(true);
//		   
//		   ExportDialog export = new ExportDialog();
//	       export.showExportDialog( frame, "Export view as ...", frame, "export" );
//	   }
//
//	   public goHep() {
//	   }
//
//	   public void paint(Graphics g) {
//		   Graphics2D g2 = (Graphics2D) g;
//	     //Here is how we used to draw a square with width
//	     //of 200, height of 200, and starting at x=50, y=50.
////	     g2.setColor(Color.red);
////	     g2.draw(new Ellipse2D.Double(10, 10,
////                 100,
////                 100));
//////	     g.drawRect(50,50,200,200);
////	
////	     //Let's set the Color to blue and then use the Graphics2D
////	     //object to draw a rectangle, offset from the square.
////	     //So far, we've not done anything using Graphics2D that
////	     //we could not also do using Graphics.  (We are actually
////	     //using Graphics2D methods inherited from Graphics.)
//////	     Graphics2D g2d = (Graphics2D)g;
////	     g2.setColor(Color.blue);
////	     g2.drawRect(75,75,300,200);
//		   
//		    Polygon p = new Polygon();
//		      p.addPoint(10,10);
//		      p.addPoint(50,10);
//		      p.addPoint(30,30);
//
//		    g.fillPolygon(p);
//		   
//	   }
//	 }
//
