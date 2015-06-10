///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package chromosomtesting;
//
//import com.kitfox.svg.SVGCache;
//import com.kitfox.svg.SVGDiagram;
//import com.kitfox.svg.SVGRoot;
//import com.kitfox.svg.SVGUniverse;
//import com.kitfox.svg.animation.AnimationElement;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.image.BufferedImage;
//import java.awt.image.RenderedImage;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Map;
//import javax.imageio.ImageIO;
//import org.apache.xmlgraphics.image.loader.Image;
//import org.apache.xmlgraphics.image.loader.ImageException;
//import org.apache.xmlgraphics.image.loader.ImageFlavor;
//import org.apache.xmlgraphics.image.loader.spi.ImageConverter;
//
///**
//* Convert an SVG image to a raster file format
//* <p>Copyright (c) Xoetrope Ltd., 2001-2006<br>
//* License:      see license.txt
//*/
//public class SvgImageTranscoder implements ImageConverter
//{ 
//  protected String imageFormat;
// 
//  /**
//   * Creates a new instance of PngTranscoder
//   */
//  public SvgImageTranscoder()
//  {
//    imageFormat = "png";
//  }
// 
//  /**
//   * Set the image format output by this transcoder
//   * @param format the image format e.g. "gif", "jpg", or "png". Defaults to "png"
//   */
//  public void setImageFormat( String format )
//  {
//    imageFormat = format;
//  }
// 
//  /**
//   * Convert one image format to another
//   * @param is the input stream for the source image
//   * @param os the output  steam for the resulting/converted image
//   * @param width the desired image width
//   * @param height the desired image height
//   * @return true for successful conversion, false otherwise
//   */
//  public boolean convert( String name, InputStream is, OutputStream os, int width, int height )
//  { 
//    // Write generated image to a file
//    try {
//      // Create a buffered image in which to draw
//      BufferedImage bufferedImage = convert( name, is, width, height );
//      RenderedImage renderedImage = (RenderedImage)bufferedImage;
//     
//      // Save as PNG
//      ImageIO.write( renderedImage, imageFormat, os );
//    }
//    catch ( IOException e )
//    {
//      e.printStackTrace();
//      return false;
//    }
//    return true;
//  }
// 
//  /**
//   * Convert one image format to another
//   * @param is the input stream for the source image
//   * @param os the output  steam for the resulting/converted image
//   * @param width the desired image width
//   * @param height the desired image height
//   * @return true for successful conversion, false otherwise
//   */
//  public BufferedImage convert( String name, InputStream is, int width, int height )
//  { 
//    // Create a buffered image in which to draw
//    BufferedImage bufferedImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );
//
//    // Create a graphics contents on the buffered image
//    Graphics2D g2d = bufferedImage.createGraphics();
//    g2d.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
//    g2d.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR );
//    g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
//
//    // Draw graphics
//    try {
//      SVGUniverse universe = SVGCache.getSVGUniverse();
//      SVGDiagram diagram = universe.getDiagram( universe.loadSVG( is, name ));
//      if ( diagram != null ) {
//        SVGRoot root = diagram.getRoot();
//        root.setAttribute( "width", AnimationElement.AT_XML, Double.toString( width ));
//        root.setAttribute( "height", AnimationElement.AT_XML, Double.toString( height ));
//        root.build();  
//        diagram.setIgnoringClipHeuristic( true );
//        root.render( g2d );
//      }
//    }
//    catch ( Exception ex )
//    {
//      ex.printStackTrace();
//    }
//
//    // Graphics context no longer needed so dispose it
//    g2d.dispose();
//
//    return bufferedImage;
//  }
// 
//  /**
//   * Get the extension of the file type that this converter produces
//   */
//  public String getOutputExt()
//  {
//    return "." + imageFormat;
//  }
//
//    @Override
//    public Image convert(Image image, Map map) throws ImageException, IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ImageFlavor getTargetFlavor() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public ImageFlavor getSourceFlavor() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int getConversionPenalty() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//}