/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nukleosom;

import application.ChromosomProject;
import java.util.Stack;
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
    public Stack<Integer> done = new Stack<Integer>();
    
    public ChromosomTree(ChromosomProject project) {
        this.project = project;
        tree = new TreeView<>(rootItem);
        rootItem.setExpanded(true);
        getChildren().add(tree);
        tree.setPrefSize(150, 500);
   
    }
    
    public void fillTree() {
        
                String y;
                
                for(int utz = 0; utz < project.maxTimeSteps.peek(); utz = utz + project.stepSize.peek()) {
                    
                    y = String.valueOf(utz + project.offset.peek());
                    
                    if(project.getTimeVector().containsKey(y)) {
                        
                        int row = addItem(y, project.rootRow.peek(), project.stepSize.peek(), project.stepsToShow.peek(), project.maxTimeSteps.peek());
                        
//                        if(project.stepSize.peek()>10) {
//                        if(project.stepSize.peek()>10) {
                            
                        if(project.stepsToShow.peek() > 8) {
                            
                            project.maxTimeSteps.push(project.stepSize.peek());
                            
                            int test = project.stepsToShow.peek();
                            
                            project.stepsToShow.push(test/5);
                            
                            project.offset.push(Integer.parseInt(y));
                            
                            
                            int stepSize = project.maxTimeSteps.peek() / (project.stepsToShow.peek() - 1);
                            
                            if(stepSize < 1) {
                                stepSize = 1;
                            }                            
                            
                            project.stepSize.push(stepSize);
                            
                            
//                            if(!done.contains(project.stepSize.peek()));
//                                done.push(project.stepSize.peek());
                            
                            
                            project.rootRow.push(row);
                            
                            if(row != -1) {
                                tree.getTreeItem(row).setExpanded(true);

                                fillTree();

                                tree.getTreeItem(row).setExpanded(false);
                            }
                            
                            if(project.stepSize.size() > 1) {
                                project.stepSize.pop();
                            }
                            if(project.stepsToShow.size() > 1) {
                                project.stepsToShow.pop();
                            }
                            if(project.maxTimeSteps.size() > 1) {
                                project.maxTimeSteps.pop();
                            }
                            if(project.offset.size() > 1) {
                                project.offset.pop();
                            }   
                            if(project.rootRow.size() > 1) {
                                project.rootRow.pop();
                            }
                            
                        }
                        
                        
                    }
		}
    }
    
    public int addItem(String itemText, int rootRow, int oldStepSize, int stepsToShow, int maxTimeSteps) {
        
        int row = -1;
        if(tree.getTreeItem(rootRow) != null) {
            
            for(TreeItem<Label> item : tree.getTreeItem(rootRow).getChildren()) {
                if(item.getValue().getText().equals(itemText)) {
                    return row;
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

                            int i;

                            for(i = 0; i < tree.getTreeItem(rootRow).getChildren().size(); i++) {
                                if(tree.getTreeItem(rootRow).getChildren().get(i).getValue().getText().equals(itemText)) {
                                    break;
                                }
                            }
                            i++;
                            
                            project.rootRow.push(i); 
                            
                            int stepSize = oldStepSize / ((stepsToShow/5) - 1);
                            
                            if(stepSize < 1) {
                                stepSize = 1;
                            }                            
                            
                            project.getChromosom().showChromosoms(Integer.parseInt(itemText), stepSize, stepsToShow/4, oldStepSize);
                            project.rootRow.pop();
                        }
                    }
//                }
            }
        });
            
            TreeItem<Label> item = new TreeItem<>(lab);
            tree.getTreeItem(rootRow).getChildren().add(item);
            
            row = tree.getRow(item);
            
        }
        return row;
    }
    
    
    
    
    
//    public TreeView getTree() {
//        return tree;
//    }
}
