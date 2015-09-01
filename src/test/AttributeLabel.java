///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package test;
//
//import javafx.event.EventHandler;
//import javafx.scene.input.ClipboardContent;
//import javafx.scene.input.DragEvent;
//import javafx.scene.input.Dragboard;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.input.TransferMode;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//
///**
// *
// * @author Jakob
// */
//public class AttributeLabel extends BorderPane{
//    
//    Text label;
//    
//    public AttributeLabel(String text) {
//        this.label = new Text(text);
//        
//        label.setFill(Color.BLACK);
//        
//        this.setOnDragDetected(new EventHandler <MouseEvent>() {
//            public void handle(MouseEvent event) {
//                /* drag was detected, start drag-and-drop gesture*/
////                System.out.println("onDragDetected");
//
//                /* allow any transfer mode */
//                Dragboard db = label.startDragAndDrop(TransferMode.ANY);
//
//                /* put a string on dragboard */
//                ClipboardContent content = new ClipboardContent();
//                content.putString(label.getText());
//                db.setContent(content);
//
//                event.consume();
//            }
//        });
//        
//        this.setOnDragOver(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* data is dragged over the target */
////                System.out.println("onDragOver");
//                /* accept it only if it is  not dragged from the same node 
//                 * and if it has a string data */
//                if (event.getGestureSource() != text &&
//                        event.getDragboard().hasString()) {
//                    /* allow for both copying and moving, whatever user chooses */
//                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                }
//                
//                event.consume();
//            }
//        });
//        
//        this.setOnDragEntered(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* the drag-and-drop gesture entered the target */
//                
////                System.out.println("onDragEntered");
//                /* show to the user that it is an actual gesture target */
//                if (event.getGestureSource() != text &&
//                        event.getDragboard().hasString()) {
//                    label.setFill(Color.GREEN);
//                }
//                
//                event.consume();
//            }
//        });
//
//        this.setOnDragExited(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* mouse moved away, remove the graphical cues */
//                label.setFill(Color.BLACK);
//                
//                event.consume();
//            }
//        });
//        
//        this.setOnDragDropped(new EventHandler <DragEvent>() {
//            
//            
//            public void handle(DragEvent event) {
//                /* data dropped */
////                System.out.println("onDragDropped");
//                /* if there is a string data on dragboard, read it and use it */
//                Dragboard db = event.getDragboard();
//                boolean success = false;
//                if (db.hasString()) {
//                    
//                    
//                    HistoneSetter.zwischen = label.getText();
//                    label.setText(db.getString());
//                    
//                    success = true;
//                }
//                /* let the source know whether the string was successfully 
//                 * transferred and used */
//                event.setDropCompleted(success);
//                
//                event.consume();
//            }
//        });
//
//        this.setOnDragDone(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* the drag-and-drop gesture ended */
////                System.out.println("onDragDone");
//                /* if the data was successfully moved, clear it */
//                if (event.getTransferMode() == TransferMode.MOVE) {
//                    label.setText(HistoneSetter.zwischen);
//                }
//                
//                event.consume();
//            }
//        });
//     this.setPrefSize(30,30);
//     this.setCenter(label);
//        
//    }
//    
//    public String getText() {
//        return label.getText();
//    }
//    
//    public void setText(String labelText) {
//        label.setText(labelText);
//    }
//}
