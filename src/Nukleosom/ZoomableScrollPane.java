/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;

public class ZoomableScrollPane extends ScrollPane{
  private Group zoomGroup;
  Scale scaleTransform;
  Node content;
  public ZoomableScrollPane(Node content)
  {
    this.content = content;
    Group contentGroup = new Group();
    zoomGroup = new Group();
    contentGroup.getChildren().add(zoomGroup);
    zoomGroup.getChildren().add(content);
    setContent(contentGroup);
  }

    /**
     * @return the zoomGroup
     */
    public Group getZoomGroup() {
        return zoomGroup;
    }

    /**
     * @param zoomGroup the zoomGroup to set
     */
    public void setZoomGroup(Group zoomGroup) {
        this.zoomGroup = zoomGroup;
    }
}