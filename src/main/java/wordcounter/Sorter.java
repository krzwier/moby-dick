package wordcounter;

import java.util.Comparator;
import java.util.Map;

public class Sorter implements Comparator<Map.Entry<String,Integer>> {
    // complete this method
    public int compare(Map.Entry<String, Integer> word1, Map.Entry<String,Integer> word2) {
        if (!word1.getValue().equals(word2.getValue())) {
            return word2.getValue() - word1.getValue();
        } else {
            return (word1.getKey()).compareTo(word2.getKey());
        }
    }
}