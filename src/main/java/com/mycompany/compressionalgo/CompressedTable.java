package com.mycompany.compressionalgo;
import java.util.HashMap;

public class CompressedTable {
    private static HashMap ht;
    public static int longestString = 0;
    
    
    public CompressedTable(){
        ht = new HashMap();
    }
    
    public static void add(String s){
        if(ht.containsValue(s))
            return;
        ht.put(ht.size(), s);
        if(s.length() > longestString)
            longestString = s.length();
    }
    
    public HashMap getTable(){
        return ht;
    }
    
    public int size(){
        return ht.size();
    }
}