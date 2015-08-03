/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NukleosomReader;

/**
 *
 * @author Jakob
 */
public class testreader {
    
    public static void main(String args[]) {
        String str = "hal\nlo wie geht es dir?mir geht es gut.\n";
        if(str.endsWith("\n"))
            System.err.println(str.substring(str.lastIndexOf("\n")));
        
    }
    
}
