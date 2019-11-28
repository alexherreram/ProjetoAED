/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmissaovideo;

/**
 *
 * @author alexh
 */


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import lzw.Compress;

public class Streaming2 extends JFrame {

    private JPanel contentPane;
    private JProgressBar progressBar;
    private JPanel panel;
    private JButton btnIniciar;
    private JButton btnIniciarLZ;
    private JButton btnProcessa;
    private File selectedFile;
    private JTextArea txtSize;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Streaming2 frame = new Streaming2();
                    frame.setVisible(true);
           
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * Create the frame.
     */
    public Streaming2() {
        setTitle("Load file to server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);   
        txtSize = new JTextArea();
        contentPane.add(txtSize);
        contentPane.add(getPanel(), BorderLayout.SOUTH);
        contentPane.add(getProgressBar(), BorderLayout.NORTH);
    }

    private JProgressBar getProgressBar() {
        if (progressBar == null) {
            progressBar = new JProgressBar();
            progressBar.setStringPainted(true);
        }
        return progressBar;
    }
    
    public static Socket Inicia_secao(){
        Socket retorno = null;
        try{
            Socket socket = new Socket("localhost", 25000);  // vou conectar no server
            retorno = socket;

            return retorno;
    }     
     catch(Exception ex){
        System.err.println("Erro na conexão com o banco...");
        ex.printStackTrace();        
    } 
    return retorno;
}
    
    private JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel();
            panel.add(getBtnIniciar());
            panel.add(getBtnIniciarLZ());
            panel.add(Processa());
        }
        return panel;
    }
    
    private JButton Processa(){
        btnProcessa = new JButton("Abrir arquivo");
        
        btnProcessa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result;
                result = fileChooser.showOpenDialog(null);
                try
                {
                    if (result == JFileChooser.APPROVE_OPTION) {
                        //limpa o campo de texto
                        txtSize.selectAll();
                        txtSize.replaceSelection("");
                        
                        //Arquivo selecionado pelo user
                        selectedFile = fileChooser.getSelectedFile();
                        
                        txtSize.append("Selected file: " + selectedFile.getAbsolutePath());
                        
                        //Cria buffer do arquivo
                        inputstream = new FileInputStream(selectedFile);
                        totalBytes = selectedFile.length();
                        
                        txtSize.append("\n");
                        txtSize.append("Tamanho do arquivo: " + Long.toString(totalBytes));                        
                        
                        //Restart nas propriedades
                        aData = "";  
                        iaData = "";  
                        progressBar.setValue(0); 
                        parada = false;
                        btnIniciar.setEnabled(true);
                        btnIniciarLZ.setEnabled(true);
                    }
                }
                catch (Exception ex){}
            }
        });
        return btnProcessa;
    }
    private JButton getBtnIniciar() {
        btnIniciar = new JButton("Iniciar Huffman");
        try
        {                  
        btnIniciar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnIniciar.setEnabled(false);
                    porcentagem = 0;
                    timer = new Timer(0, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)  {                                
                              try{  
                                if (parada == false) {
                                    idata = inputstream.read();
                                    //String content = new String(Files.readAllBytes(selectedFile.toPath()));
                                    //int t = content.length();
                                    
                                    if (idata == -1 ){                                       
                                        parada = true;
                                        progressBar.setValue(100); 
                                        SendContent();
                                        btnIniciar.setEnabled(false);
                                        btnIniciarLZ.setEnabled(false);
                                        timer.stop();
                                    }
                                    else{
                                        iaData =  iaData + (char)idata;
                                        porcentagem = (float)(iaData.toString().length() * 100) / (float)totalBytes; 
                                        progressBar.setValue((int)porcentagem); 
                                        
                                    }
                                }
                              }
                              catch(Exception ex){
                                  System.out.printf(ex.getMessage());
                              }
                        }                        

                        private void SendContent() {
                            Socket retorno = Inicia_secao();
                            Requisicao req = new Requisicao(); 
                            Process p = new Process();
                            try{

                            //------------------   
                            long start = System.currentTimeMillis();
                            String conteudo = p.encode(iaData);  
                            long finish = System.currentTimeMillis();
                            
                            txtSize.append("\n");
                            txtSize.append("Compressão realizada em: " + Long.toString(finish - start));

                            req.setAlgoritmo("Huffman");
                            req.setData(conteudo);
                            req.setObject(p.root);

                            ObjectOutputStream out = new ObjectOutputStream(retorno.getOutputStream());
                            out.writeObject(req); // equivalente ao SEND
                            btnIniciar.setEnabled(true);
                            btnIniciarLZ.setEnabled(true);
                            }
                            catch(Exception ex){}
                        }
                    });
                    timer.start();
                }
            });
        }
        catch(Exception ex){}
        
        return btnIniciar;
    }
    
    private JButton getBtnIniciarLZ() {
        btnIniciarLZ = new JButton("Iniciar LZ");
        try
        {                  
        btnIniciarLZ.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnIniciarLZ.setEnabled(false);
                    porcentagem = 0;
                    timer = new Timer(0, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)  {                                
                              try{  
                                if (parada == false) {
                                    idata = inputstream.read();
                                    //String content = new String(Files.readAllBytes(selectedFile.toPath()));
                                    //int t = content.length();
                                    
                                    if (idata == -1 ){                                       
                                        parada = true;
                                        progressBar.setValue(100); 
                                        SendContentLZ();
                                        btnIniciarLZ.setEnabled(false);
                                        btnIniciar.setEnabled(false);
                                        timer.stop();
                                    }
                                    else{
                                        iaData = iaData + (char)idata; 
                                        porcentagem = (float)(iaData.length() * 100) / (float)totalBytes; 
                                        progressBar.setValue((int)porcentagem); 
                                        
                                    }
                                }
                              }
                              catch(Exception ex){
                                  System.out.printf(ex.getMessage());
                              }
                        }                        

                        private void SendContentLZ() {
                            Socket retorno = Inicia_secao();
                            Requisicao req = new Requisicao(); 
                            Process p = new Process();
                            try{

                                //------------------   
                                long start = System.currentTimeMillis();
                                
                                List<Integer> conteudo = Compress.comp(iaData);  
                                long finish = System.currentTimeMillis();
             
                                txtSize.append("\n");
                                txtSize.append("Compressão realizada em: " + Long.toString(finish - start));

                                req.setAlgoritmo("LZ");
                                req.setData(conteudo);                           

                                ObjectOutputStream out = new ObjectOutputStream(retorno.getOutputStream());
                                out.writeObject(req); // equivalente ao SEND
                                btnIniciarLZ.setEnabled(true);
                            }
                            catch(Exception ex){}
                        }
                    });
                    timer.start();
                }
            });
        }
        catch(Exception ex){}
        
        return btnIniciarLZ;
    }
    private static FileInputStream inputstream = null; 
    private String data;
    private int idata;
    private static boolean parada = false;
    private long totalBytes;    
    private static String aData ;
    private static String iaData ;
    private Timer timer;
    private float porcentagem;
}
