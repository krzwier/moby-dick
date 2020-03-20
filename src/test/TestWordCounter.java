package test;

import main.WordCounter;
import org.junit.jupiter.api.Test;

//import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestWordCounter {

    @Test
    void test_loadwordsforshorttextfile(){
        WordCounter wordCounter = new WordCounter();
        assertTrue(wordCounter.LoadWords("short-text.txt"));
    }

    /*
    @Test
    void test_wordlistforshorttextfile(){
        //WordCounter wordCounter = new WordCounter();
        HashMap<String,Integer> wordMap = new HashMap<>();
        wordMap.put("this", 1);
        wordMap.put("is", 1);
        wordMap.put("a", 1);
        wordMap.put("dummy", 1);
        wordMap.put("file", 1);
        wordMap.put("for", 1);
        wordMap.put("test", 1);
        wordMap.put("purposes", 1);

        //assertTrue(wordMap.equals(wordCounter.getWordMap()));


    }
    */

}
