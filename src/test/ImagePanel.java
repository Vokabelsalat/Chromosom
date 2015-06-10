//package test;
//
//import java.awt.Frame;
//import java.awt.Graphics;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//import javax.swing.JPanel;
//
//import org.freehep.util.export.ExportDialog;
//
//public class ImagePanel extends JPanel{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -3211628976481768493L;
//	private BufferedImage image;
//	
//	public ImagePanel(BufferedImage image) {
//	      this.image = image;
//	}
//	
//	@Override
//	protected void paintComponent(Graphics g) {
//	    super.paintComponent(g);
//	    g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
//	}
//
//	public static void main (String args[]) {
//		
//		BufferedImage img;
//		try {
//			img = ImageIO.read(new File("test.png"));
//			
//			ImagePanel pan = new ImagePanel(img);
//			
//			Frame frame = new Frame();
//			frame.add(pan);
//			frame.setVisible(true);
//			
//			ExportDialog export = new ExportDialog();
//		    export.showExportDialog( img, "Export view as ...", pan, "export" );
//		    
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
//	
//}