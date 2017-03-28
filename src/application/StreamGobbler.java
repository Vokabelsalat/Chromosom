/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

/**
 *
 * @author Jakob
 */
public class StreamGobbler extends Thread{
    InputStream is;
    String type;
    TextArea textarea;
    String line;

    public StreamGobbler(InputStream is, TextArea textarea) {
        Task task = new Task<Void>() {
        @Override
        public Void call() throws Exception {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            line = null;
          while ((line = br.readLine()) != null) {
            final String newLine = line;
            Platform.runLater(new Runnable() {
              @Override
              public void run() {
                textarea.appendText(System.lineSeparator() + newLine);
                System.out.println(newLine);
              }
            });
//                Thread.sleep(1);
          }
          return null;
        }
        };
      Thread th = new Thread(task);
      th.setDaemon(true);
      th.start();
    }
}
    
