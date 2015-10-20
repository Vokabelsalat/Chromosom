/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChromosomEditor;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Jakob
 */
public class EditorGridPane extends GridPane{
    
//    int maxX, maxY;
    
    public EditorGridPane(ArrayList<Node> nodeList, int maxX, int maxY) {
        super();
        
        int x = 0;
        int y = 0;
        
        for(int i = 0; i < nodeList.size(); i++) {
         
            add(nodeList.get(i), x ,y);
            
            if((i+1)%maxX == 0) {
                y++;
                x = 0;
            }
            else {
                x++;
            }
        } 
    }
    
}
