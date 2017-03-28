/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jakob
 */
public class AttributeRectangle extends Rectangle{
    
    int attributeValue;
    
    public AttributeRectangle(int width, int height, Paint color) {
        super(width, height, color);
    }
    
    public AttributeRectangle() {
        super();
    }
    
        
    public AttributeRectangle(int x, int y, double width, double height) {
        super(x, y, width, height);
    }
    
    public void setAttributeValue(int attributeValue) {
        this.attributeValue = attributeValue;
    }
    
    public int getAttributeValue() {
        return attributeValue;
    }

    
}
