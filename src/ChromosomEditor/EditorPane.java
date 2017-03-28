/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.io.File;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Jakob
 */
public class EditorPane extends BorderPane{
    
    private File saveFile;
    String paneName = "ep";
    String paneFullName = "EditorPane";
    
    public boolean save() {
        return true;
    }
    
    public boolean next() {
        return true;
    }
    
    public void back() {
        
    }
    
    public void reset() {
        
    }
    
    public boolean writeToFile(File file) {
        return true;
    }
    
    public String toString() {
        return paneName;
    }
    
    /**
     * @return the saveFile
     */
    public File getSaveFile() {
        return saveFile;
    }

    /**
     * @param saveFile the saveFile to set
     */
    public void setSaveFile(File saveFile) {
        this.saveFile = saveFile;
    }
}
