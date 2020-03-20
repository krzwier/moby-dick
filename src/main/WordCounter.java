package main;

import java.io.*;
import java.util.HashMap;
import java.util.HashMap;

public class WordCounter {

    private HashMap<String, Integer> wordMap = new HashMap<>();

    public HashMap<String, Integer> getWordMap() {
        return this.wordMap;
    }

    /**
     * @param   filename    name of file to read
     * @return              boolean indicating success
     */
    public boolean LoadWords(String filename) {
        boolean success = false;
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\W");
                for (String s : words) {
                    String lowercaseWord = s.toLowerCase();
                    if (wordMap.putIfAbsent(lowercaseWord,1) != null){
                        wordMap.replace(lowercaseWord,wordMap.get(lowercaseWord) + 1);
                    }
                }
            }
            br.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }


}
