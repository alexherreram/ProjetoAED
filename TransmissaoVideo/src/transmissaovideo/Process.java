/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmissaovideo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javax.swing.JFileChooser;

/**
 *
 * @author alexh
 */
public class Process {
    
    private static Map<Character, String> charPrefixHashMap = new HashMap<>();
    static HuffmanNode root;

    public void LoadFile() {
                InputStream inputstream;
                File selectedFile = null;
                long start,finish;
                
                start = System.currentTimeMillis();
                
                try{
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result;
                    result = fileChooser.showOpenDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        selectedFile = fileChooser.getSelectedFile();
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    }

                    inputstream = new FileInputStream(selectedFile);
                    String aData = "" ;

                    int data = inputstream.read();
                    
                    while (data != -1) {
                           aData = aData + (char)data;
                           data = inputstream.read();
                    }        
                    finish = System.currentTimeMillis();

                    System.out.println("Leitura realizada em: " + Long.toString(finish - start));
                    inputstream.close();
                    
                    //------------------     
                    
                    start = System.currentTimeMillis();
                    System.out.println("Tamanho original = "+ aData.length());  
                  
                    String test = encode(aData);  
                    System.out.println("Tamanho comprimido: " + test.length());
                    StringBuilder saida = decode(test); 
                    
                    System.out.println("Tamanho depois da compressao = "+saida.toString().length());  
                                                                                             
                    finish = System.currentTimeMillis();
                    //---------------------
                    System.out.println("Time: " + Long.toString(finish - start));
                }
         catch (FileNotFoundException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
        } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
        }
}

    private static HuffmanNode buildTree(Map<Character, Integer> freq) {

                    PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
                    Set<Character> keySet = freq.keySet();
                    for (Character c : keySet) {

                            HuffmanNode huffmanNode = new HuffmanNode();
                            huffmanNode.data = c;
                            huffmanNode.frequency = freq.get(c);
                            huffmanNode.left = null;
                            huffmanNode.right = null;
                            priorityQueue.offer(huffmanNode);
                    }
                    assert priorityQueue.size() > 0;

                    while (priorityQueue.size() > 1) {

                            HuffmanNode x = priorityQueue.peek();
                            priorityQueue.poll();

                            HuffmanNode y = priorityQueue.peek();
                            priorityQueue.poll();

                            HuffmanNode sum = new HuffmanNode();

                            sum.frequency = x.frequency + y.frequency;
                            sum.data = '-';

                            sum.left = x;

                            sum.right = y;
                            root = sum;

                            priorityQueue.offer(sum);
                    }

                    return priorityQueue.poll();
            }

    private static void setPrefixCodes(HuffmanNode node, StringBuilder prefix) {

                    if (node != null) {
                            if (node.left == null && node.right == null) {
                                    charPrefixHashMap.put(node.data, prefix.toString());

                            } else {
                                    prefix.append('0');
                                    setPrefixCodes(node.left, prefix);
                                    prefix.deleteCharAt(prefix.length() - 1);

                                    prefix.append('1');
                                    setPrefixCodes(node.right, prefix);
                                    prefix.deleteCharAt(prefix.length() - 1);
                            }
                    }

            }

    private static String encode(String test) {
                    Map<Character, Integer> freq = new HashMap<>();
                    for (int i = 0; i < test.length(); i++) {
                            if (!freq.containsKey(test.charAt(i))) {
                                    freq.put(test.charAt(i), 0);
                            }
                            freq.put(test.charAt(i), freq.get(test.charAt(i)) + 1);
                    }

                    //System.out.println("Character Frequency Map = " + freq);
                    root = buildTree(freq);

                    setPrefixCodes(root, new StringBuilder());
                    //System.out.println("Character Prefix Map = " + charPrefixHashMap);
                    StringBuilder s = new StringBuilder();

                    for (int i = 0; i < test.length(); i++) {
                            char c = test.charAt(i);
                            s.append(charPrefixHashMap.get(c));
                    }

                    return s.toString();
            }

    private StringBuilder decode(String s) {

                    StringBuilder stringBuilder = new StringBuilder();

                    HuffmanNode temp = root;

                    //System.out.println("Encoded: " + s);

                    for (int i = 0; i < s.length(); i++) {
                            int j = Integer.parseInt(String.valueOf(s.charAt(i)));

                            if (j == 0) {
                                    temp = temp.left;
                                    if (temp.left == null && temp.right == null) {
                                            stringBuilder.append(temp.data);
                                            temp = root;
                                    }
                            }
                            if (j == 1) {
                                    temp = temp.right;
                                    if (temp.left == null && temp.right == null) {
                                            stringBuilder.append(temp.data);
                                            temp = root;
                                    }
                            }
                    }                   

        return stringBuilder;
        }
}
