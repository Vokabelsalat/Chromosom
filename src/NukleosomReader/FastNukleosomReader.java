/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NukleosomReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author Jakob
 */
public class FastNukleosomReader {
    
    public static void main(String args[]) throws IOException {
//        FileInputStream fileInputStream = new FileInputStream(
//                                        new File("run2_state.txt"));
//        FileChannel fileChannel = fileInputStream.getChannel();
//        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//
//        while(fileChannel.read(byteBuffer) !=0) {
//            byteBuffer.flip();
//            int limit = byteBuffer.limit();
//            while(limit>0)
//            {
//                System.out.print((char)byteBuffer.get());
//                limit--;
//            }
//            byteBuffer.clear();
//        }
//
//        fileChannel.close();
        
        
        FileInputStream f = new FileInputStream("run2_state.txt");
        FileChannel ch = f.getChannel( );
        ByteBuffer bb = ByteBuffer.allocateDirect(1024);
        byte[] barray = new byte[1024];
        int nRead, nGet;
        
        String str = "";
        String strArray[];
        String akt = "";
        while ( (nRead=ch.read( bb )) != -1 )
        {
            if ( nRead == 0 )
                continue;
            bb.position( 0 );
            bb.limit( nRead );
            while( bb.hasRemaining( ) )
            {
                nGet = Math.min( bb.remaining( ), 1024 );
                bb.get( barray, 0, nGet );
                str += new String(barray,0 , nGet);
                
                if(str.endsWith("\n"))
                    akt = "\n";
                else 
                    akt = "";
                
                strArray = str.split("\n");
                
                for(int i = 0; i < strArray.length-1; i++) {
                    System.err.println("LINE: " + strArray[i]);
                }
                

                str = strArray[strArray.length-1] + akt;
                
                
            }
            bb.clear( );
        }
        bb.clear();
        ch.close();
    }
    
}
