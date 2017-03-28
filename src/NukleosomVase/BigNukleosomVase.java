/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NukleosomVase;

import javafx.scene.layout.GridPane;

/**
 * NucleosomeVase mit einer Unterteilung für die Histone und die entsprechenden Sites
 * @author Jakob
 */
public class BigNukleosomVase extends GridPane{
    
    String exportString;
    
    public BigNukleosomVase(int valueArray[], boolean horizontal, String type, int width, int height) {
        
            exportString = "<g transform=\"translate(%X,%Y) rotate(%W " + width/2.0 + " " + height/2.0 + ")\">\n";
        
            int innerX, innerY;
        
            for(int mod = 0; mod < valueArray.length; mod++) {

                    if(horizontal) {
                            innerX = 0;
                            innerY = mod;
                    }
                    else {
                            innerX = mod;
                            innerY = 0;
                    }
                    NukleosomVase nuklVase = new NukleosomVase(valueArray[mod], horizontal, width, height);
                    add(nuklVase, innerX, innerY);
                    String str = nuklVase.getSVGExportString();
                    str = str.replaceAll("%X", Double.toString(nuklVase.localToScene(nuklVase.getBoundsInLocal()).getMinX()));
                    str = str.replaceAll("%Y", Double.toString(nuklVase.localToScene(nuklVase.getBoundsInLocal()).getMinY()));
                    exportString += str;
            }
            
            exportString += "</g>";
        
    }
    
    public String getExportString() {
        return exportString;
    }  
    
}
