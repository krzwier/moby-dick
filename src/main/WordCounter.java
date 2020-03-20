package main;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class WordCounter {

    private boolean wordsLoaded;
    private boolean stopWordsLoaded;
    private HashSet<String> stopWords = new HashSet<>();
    private HashMap<String, Integer> wordMap = new HashMap<>();

    /**
     * Creates a new WordCounter object by loading stop words and
     * words from given file. Sets boolean fields to indicate success
     * of load methods.
     *
     * @param   filename    name of file to load
     */
    public WordCounter(String filename) {
        this.stopWordsLoaded = LoadStopWords("stop-words.txt");
        this.wordsLoaded = LoadWords(filename);
    }

    /**
     * @return boolean variable indicating if words have been loaded
     */
    public boolean getWordsLoaded(){
        return this.wordsLoaded;
    }

    /**
     * @return boolean variable indicating if stop words have been loaded
     */
    public boolean getStopWordsLoaded(){
        return this.stopWordsLoaded;
    }

    /**
     * @return WordMap with all words and counts
     */
    public HashMap<String, Integer> getWordMap() {
        return this.wordMap;
    }

    /**
     * Opens stop words file and reads each word, line by line.
     * Stores stop words in stopWords hashset
     *
     * @param   filename    name of file to read
     * @return              boolean indicating success
     */
    private boolean LoadStopWords(String filename) {
        boolean success = false;
        String word;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((word = br.readLine()) != null) {
                String lowercaseWord = word.toLowerCase();
                if (!lowercaseWord.isEmpty() && (lowercaseWord.charAt(0) != '#')){
                    stopWords.add(lowercaseWord);
                }
            }
            br.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
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
                    if (!stopWords.contains(lowercaseWord) &&
                            wordMap.putIfAbsent(lowercaseWord,1) != null){
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
