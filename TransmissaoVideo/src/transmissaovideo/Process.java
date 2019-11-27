/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmissaovideo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import lzw.Compress;
import lzw.Decompress;

/**
 *
 * @author alexh
 */
public class Process {
    
    private static Map<Character, String> charPrefixHashMap = new HashMap<>();
    public HuffmanNode root;

    public String DecodeFile(String obj) {
               
                long start,finish;
                StringBuilder saida = null;
                try{        
                    start = System.currentTimeMillis();

                    saida = decode(obj); 
                                                                  
                    finish = System.currentTimeMillis();

                }

        catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
        }
                
        return saida.toString();
}

    public String DecodeFileLZ(List<Integer> obj) {
        String decompressed = "";
        
        try
        {
            decompressed = Decompress.decomp(obj);            
        }
        catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
        }
                
        return decompressed;
}
    
    private HuffmanNode buildTree(Map<Character, Integer> freq) {

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

    private  void setPrefixCodes(HuffmanNode node, StringBuilder prefix) {

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

    public String encode(String test) {
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
    
    public static List<Integer> encodeLz(String uncompressed){
        // Build the dictionary.
        int dictSize = 256;
        Map<String,Integer> dictionary = new HashMap<String,Integer>();
        for (int i = 0; i < 256; i++)
            dictionary.put("" + (char)i, i);
 
        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                result.add(dictionary.get(w));
                // Add wc to the dictionary.
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }
 
        // Output the code for w.
        if (!w.equals(""))
            result.add(dictionary.get(w));
        return result;
    }
    
    public static String decodeLz(List<Integer> compressed){
             // Build the dictionary.
        int dictSize = 256;
        Map<Integer,String> dictionary = new HashMap<Integer,String>();
        for (int i = 0; i < 256; i++)
            dictionary.put(i, "" + (char)i);
 
        String w = "" + (char)(int)compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        try{
        for (int k : compressed) {
            String entry;           
            
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
            
            result.append(entry);
 
            // Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));
 
            w = entry;
        }
        }
        catch (Exception ex){
          return "";
        }
        return result.toString();
     }

    private StringBuilder decode(String s) {

                    StringBuilder stringBuilder = new StringBuilder();

                    HuffmanNode temp = root;

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
