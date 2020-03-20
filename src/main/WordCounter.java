package main;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


class Sorter implements Comparator<Entry<String,Integer>> {
    // complete this method
    public int compare(Entry<String, Integer> word1, Entry<String,Integer> word2) {
        if (!word1.getValue().equals(word2.getValue())) {
            return word2.getValue() - word1.getValue();
        } else {
            return (word1.getKey()).compareTo(word2.getKey());
        }
    }
}


public class WordCounter {

    private boolean wordsLoaded;
    private boolean stopWordsLoaded;
    private HashSet<String> stopWords = new HashSet<>();
    private HashMap<String, Integer> wordMap = new HashMap<>();
    private ArrayList<Entry<String, Integer>> wordFreq = new ArrayList<>();

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
        this.SortWords();
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
                // make sure empty string is in stop word list
                stopWords.add("");
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

    /**
     * Converts wordMap into ArrayList of word-frequency entries,
     * then sorts that list so most frequent words are listed first
     */
    private void SortWords(){
        Set<Entry<String,Integer>> entries = this.getWordMap().entrySet();
        wordFreq = new ArrayList<>(entries);
        Sorter sorter = new Sorter();
        wordFreq.sort(sorter);
        for (int i = 0; i < 100; i++) {
            if (i >= wordFreq.size()){
                break;
            } else {
                System.out.println(wordFreq.get(i));
            }
        }
    }



}
