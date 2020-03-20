package main;

import java.io.*;
import java.util.HashMap;

public class WordCounter {

    private boolean wordsLoaded;
    private HashMap<String, Integer> wordMap = new HashMap<>();

    /**
     * Creates a new WordCounter object by loading words from given file.
     *
     * @param   filename    name of file to load
     */
    public WordCounter(String filename) {
        this.wordsLoaded = LoadWords(filename);
    }

    /**
     * @return boolean variable indicating if words have been loaded
     */
    public boolean getWordsLoaded(){
        return this.wordsLoaded;
    }

    /**
     * @return WordMap with all words and counts
     */
    public HashMap<String, Integer> getWordMap() {
        return this.wordMap;
    }


    /**
     * Opens file and reads line by line, separating each line in to words.
     * Stores words in wordMap with word counts.
     *
     * @param   filename    name of file to read
     * @return              boolean indicating success
     */
    private boolean LoadWords(String filename) {
        boolean success = false;
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\W+");
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
