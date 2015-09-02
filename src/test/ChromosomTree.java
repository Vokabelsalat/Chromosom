/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Nukleosom.BigNukleosomNew;
import application.ChromosomProject;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jakob
 */
public class ChromosomTree extends Pane{
    
    TreeItem<Label> rootItem = new TreeItem<>(new Label("ROOT"));
    public TreeView<Label> tree;
    ChromosomProject project;
    
    public ChromosomTree(ChromosomProject project) {
        this.project = project;
        tree = new TreeView<>(rootItem);
        rootItem.setExpanded(true);
        getChildren().add(tree);
   
    }
    
    public void fullFillTree() {
        
        for(int i = 0; i < tree.getRoot().getChildren().size(); i++) {
            
            String text = tree.getRoot().getChildren().get(i).getValue().getText();
            
                            int u = 0;

                            for(u = 0; u < tree.getTreeItem(project.rootRow.peek()).getChildren().size(); u++) {
                                if(tree.getTreeItem(project.rootRow.peek()).getChildren().get(u).getValue().getText().equals(text)) {
                                    break;
                                }
                            }
                            u++;
                            
                            project.rootRow.push(u); 
                            
                            if(project.stepSize.size()>1) {
                                project.stepSize.pop();
                            }

                            if(project.stepsToShow.size()>1)
                                project.stepsToShow.pop();

                            if(project.offset.size()>1)  
                                project.offset.pop();

                            if(project.maxTimeSteps.size()>1)
                                project.maxTimeSteps.pop();

                            project.stepsToShow.push(project.stepSize.peek());
                            project.maxTimeSteps.push(project.stepSize.peek());
                            project.stepSize.push(1);
                            project.offset.push(Integer.parseInt(text)); 
                            fillTree();
                            project.rootRow.pop(); 
        }
    }
    
    public void fillTree() {
                HashMap<String, HashMap<String, HashMap<String,HashMap<String,Integer>>>> timeVector = project.getTimeVector();
		
                String y = "";
                
                for(int utz = 0; utz < project.maxTimeSteps.peek(); utz = utz + project.stepSize.peek()) {
                    
                    y = String.valueOf(utz + project.offset.peek());
                    
                    if(timeVector.containsKey(y)) {
                        
                        project.getChromosom().addTreeItem(y, project.rootRow.peek());
                        
                        
//                        HashMap<String, HashMap<String,HashMap<String,Integer>>> nukleomList = timeVector.get(y);
//                        for(String x : timeVector.get(y).keySet()) {
//                            HashMap<String,HashMap<String,Integer>> histoneMap = nukleomList.get(x);
//                            for(String histoneNumber : histoneMap.keySet()) { 
//
//                            }
//                        } 
                    }
		}
                

                
    }
    
    public void addItem(String itemText, int rootRow) {
        if(tree.getTreeItem(rootRow) != null) {
            
            for(TreeItem<Label> item : tree.getTreeItem(rootRow).getChildren()) {
                if(item.getValue().getText().equals(itemText)) {
                    return;
                }
            }
            
            Label lab = new Label(itemText);
            lab.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                if(project.stepSize.peek() != 1) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){  
                        
                        if(mouseEvent.getClickCount() == 2){
                            if(project.stepSize.size() > 1) {
                                project.stepSize.pop();
                            }

                            int i = 0;

                            for(i = 0; i < tree.getTreeItem(project.rootRow.peek()).getChildren().size(); i++) {
                                if(tree.getTreeItem(project.rootRow.peek()).getChildren().get(i).getValue().getText().equals(itemText)) {
                                    break;
                                }
                            }
                            i++;
                            
                            project.rootRow.push(i); 
//                            System.err.println(i);
                            project.getChromosom().showChromosoms(Integer.parseInt(itemText), 1, project.stepSize.peek());
                            project.rootRow.pop();
                        }
                    }
//                }
            }
        });
            
            TreeItem<Label> item = new TreeItem<>(lab);
            tree.getTreeItem(rootRow).getChildren().add(item);
            
            int row = tree.getRow(item);
            
        }
    }
    
    
    
    
    
//    public TreeView getTree() {
//        return tree;
//    }
}
