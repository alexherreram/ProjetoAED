package lzw;

import java.io.File;
import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JFileChooser;
 
public class LZW {

    public static void main(String[] args) {
        
        Process h = new Process();

        List<Integer> compressed = Compress.comp(h.LoadFile());
        System.out.println("Tamanho depois da compressao LZW: " + compressed.size());
        String decompressed = Decompress.decomp(compressed);
        System.out.println("Tamanho depois da descompressao LZW: " + decompressed.length());
        
        
        //output
        /*
        try {
            
                    Caminho hp = new Caminho();
            
            
                    OutputStream output = new FileOutputStream(hp.cami());
                    
                    StringBuilder strin  = new StringBuilder();
                    Iterator<Integer> iter = compressed.iterator();
                    
                    while(iter.hasNext())
                        strin.append(iter.next());
                    
                    String comp = strin.toString();
                    
                    
                    String s = comp;
                    int count = 0;
  
                    while (count < s.length()) {
  
                           output.write(s.charAt(count));
  
                           count++;
                    }
                    output.close();
             } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
             }
         */
        
        
    }
}