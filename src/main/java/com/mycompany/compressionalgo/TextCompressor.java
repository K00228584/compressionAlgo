package com.mycompany.compressionalgo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class TextCompressor {
    private final CompressedTable ct;
    private List<String> strings;
    private static LinkedHashMap<String, Integer> secondaryDictionary = new LinkedHashMap<String, Integer>();
    
    public TextCompressor(List<String> strings){
        this.strings = strings;
        ct = new CompressedTable();
    }
    
    public void setList(List<String> strings){
        this.strings = strings;
    }
    
    public void updateTable(){
        for(String s : strings)
            for(int i = 0; i < s.length(); i++)
                CompressedTable.add(String.valueOf(s.charAt(i)));
        initSecondaryTable();
        updateTableSecondary();
    }
    
    public void updateTableSecondary(){
        int i = 0;
        Set set = secondaryDictionary.entrySet();
        Iterator iterator = set.iterator();
        while(i < (256 - ct.size())){
            Map.Entry item = (Map.Entry) iterator.next();
            CompressedTable.add((String)item.getKey());
        }
    }
    
    public void initSecondaryTable(){
        for(String s : strings){
            for(int i = 0; i < s.length(); i++)
                for(int j = i+2; j < i+10 && j < s.length(); j++)
                    if(!secondaryDictionary.containsKey(s.substring(i, j)))
                        secondaryDictionary.put(s.substring(i, j), 1);
                    else
                        secondaryDictionary.put(s.substring(i, j), secondaryDictionary.get(s.substring(i, j))+1);
        }
        secondaryDictionary = sortByValue(secondaryDictionary, true);
    }
    
private static LinkedHashMap<String, Integer> sortByValue(LinkedHashMap<String, Integer> unsortMap, final boolean order)
    {
        List<Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) ->  { 
            
            Integer int2 = o2.getValue() * (o2.getKey().length() - 1);
            Integer int1 = o1.getValue() * (o1.getKey().length() - 1);
            
            return int2.compareTo(int1);
        
        });
        return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
    
    public void updateTable(List<String> string){
        this.setList(string);
        for(String s : strings)
            for(int i = 0; i < s.length(); i++)
                CompressedTable.add(String.valueOf(s.charAt(i)));
        initSecondaryTable();
        updateTableSecondary();
    }
    
    public HashMap getTable(){
        return ct.getTable();
    }
}
