/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transmissaovideo;
//package com.journaldev.huffmancoding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Timer;
import javax.swing.JFileChooser;

/**
 *
 * @author alexh
 */
public class TransmissaoVideo {

    private static Map<Character, String> charPrefixHashMap = new HashMap<>();
    static Process root;
 
    public static void main(String[] args) {
         
        Process h = new Process();
                     
        h.LoadFile();
                
     }

}
