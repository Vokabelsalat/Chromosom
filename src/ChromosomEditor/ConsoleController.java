///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ChromosomEditor;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintStream;
//import java.net.URL;
//import java.util.ResourceBundle;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TextArea;
//import javax.swing.SwingUtilities;
//
//public class Activity extends OutputStream implements Initializable {
//
//TextArea textarea;    
//    
//public Activity(TextArea textarea) {
//    OutputStream out = new OutputStream() {
//        @Override
//        public void write(int b) throws IOException {
//            updateTextArea(String.valueOf((char) b));
//        }
//
//        @Override
//        public void write(byte[] b, int off, int len) throws IOException {
//            updateTextArea(new String(b, off, len));
//        }
//
//        @Override
//        public void write(byte[] b) throws IOException {
//            write(b, 0, b.length);
//        }
//    };
//
//    System.setOut(new PrintStream(out, true));
//    System.setErr(new PrintStream(out, true));
//}
//
//}
//
//private void updateTextArea(final String text) {
//    SwingUtilities.invokeLater(new Runnable() {
//        public void run() {
//            textarea.appendText(text);
//        }
//    });
//}
//
//@Override
//public void write(int arg0) throws IOException {
//    // TODO Auto-generated method stub
//
//}
//}