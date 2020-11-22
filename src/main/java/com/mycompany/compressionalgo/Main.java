/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.compressionalgo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
/**
 *
 * @author Dillo
 */
public class Main {
    
    static List<String> strings;
    static TextCompressor tc = new TextCompressor(strings);
    
    public static void main(String[] args) {
        String fileName = "C:\\tmp\\bible.txt";
        String compressedFile = "C:\\tmp\\export.cmp";
        Boolean exitFlag = false;
        while(exitFlag == false){
            switch(Menu()){
                case 1 -> strings = importText(fileName);
                case 2 -> tc.updateTable(strings);
                case 3 -> System.out.println(tc.getTable());
                case 4 -> exportBinaryFile(compressedFile);
                case 5 -> importBinaryFile(compressedFile);
                default -> {
                    exitFlag = true;
                }
            }
        }
    }
    
    public static int Menu(){
        System.out.println("\n------------Menu------------");
        System.out.println("    1.  Import Text");
        System.out.println("    2.  Compress Text");
        System.out.println("    3.  View Binary Table");
        System.out.println("    4.  Export Binary Table");
        System.out.println("    5.  Import Binary Table");
        
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(scanner.nextLine());
    }
    
    public static List<String> importText(String fileName){
        List<String> stringList = new ArrayList<>();
        try{
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String string;
            while ((string = br.readLine()) != null)
                stringList.add(string);
        }
        catch(IOException e){
            System.out.println(e);
        }
        return stringList;
	}
    
    public static void exportBinaryFile(String file){
        try{
        OutputStream outputStream = new FileOutputStream(new File(file));
        
        for(String s : strings){
            for(int i = 0; i < s.length(); i++)
                for(int j = i+10; j >= i; j--)
                    try{
                    if(tc.getTable().containsValue(s.substring(i, j))){
                        outputStream.write((int)getKeyByValue(tc.getTable(), s.substring(i, j)));
                        i=j-1;
                    }
                    
                    }
                    catch(Exception e){
                        j = s.length() + 1;
                    }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void importBinaryFile(String file){
        
        HashMap binaryTable = tc.getTable();
        
        try{
            FileInputStream fis=new FileInputStream(file);
            
            int ch = fis.read();
            while (ch != -1) {
                String s = (String)binaryTable.get(ch);
                System.out.print(s);
                
                ch = fis.read();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static int getKeyByValue(HashMap map, String searchString) {
    
    Iterator<Entry<Integer, String>> it = map.entrySet().iterator();
    
    while(it.hasNext()){
        Map.Entry<Integer, String> pair = (Map.Entry<Integer, String>) it.next();
        if(pair.getValue().equals(searchString))
            return pair.getKey();
    }
    
    return -1;
}
}
