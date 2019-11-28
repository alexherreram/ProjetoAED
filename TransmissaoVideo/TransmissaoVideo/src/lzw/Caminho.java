package lzw;

import java.io.File;
import javax.swing.JFileChooser;

public class Caminho {
    public File cami(){
        File selectedFile = null;
            
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result;
                    result = fileChooser.showOpenDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        selectedFile = fileChooser.getSelectedFile();
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    }
            return selectedFile;
    }
}
