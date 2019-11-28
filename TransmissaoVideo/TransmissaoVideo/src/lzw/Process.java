package lzw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author diogo
 */
public class Process {

    public String LoadFile() {
                InputStream inputstream;
                long start,finish;
                
                start = System.currentTimeMillis();
                
                try{
                    Caminho hp = new Caminho();
                    inputstream = new FileInputStream(hp.cami());
                    String aData = "" ;

                    int data = inputstream.read();
                    
                    while (data != -1) {
                           aData = aData + (char)data;
                           data = inputstream.read();
                    }        
                     
                    
                    finish = System.currentTimeMillis();

                    System.out.println("Leitura realizada em: " + Long.toString(finish - start));
                    inputstream.close();
                    System.out.println("Tamanho original = "+ aData.length());
                    
                    return aData;
                    
                }
         catch (FileNotFoundException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
        } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
        }
                return "ERRO";
}
   
}
