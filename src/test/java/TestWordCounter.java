import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestWordCounter {

    @Test
    void test_loadwordsforonelinetextfile(){
        WordCounter wordCounter = new WordCounter("short-text.txt");
        assertTrue(wordCounter.getWordsLoaded());
        assertTrue(wordCounter.getStopWordsLoaded());
    }

    @Test
    void test_wordlistforonelinetextfile(){
        WordCounter wordCounter = new WordCounter("short-text.txt");
        HashMap<String,Integer> wordMap = new HashMap<>();
        //wordMap.put("this", 1);
        //wordMap.put("is", 1);
        //wordMap.put("a", 1);
        wordMap.put("dummy", 1);
        wordMap.put("file", 1);
        //wordMap.put("for", 1);
        wordMap.put("test", 1);
        wordMap.put("purposes", 1);

        assertEquals(wordMap, wordCounter.getWordMap());
    }

    @Test
    void test_wordlistfortwolinetextfile(){
        WordCounter wordCounter = new WordCounter("two-line-text.txt");
        HashMap<String,Integer> wordMap = new HashMap<>();
        //wordMap.put("this", 2);
        //wordMap.put("is", 2);
        //wordMap.put("a", 2);
        wordMap.put("dummy", 1);
        wordMap.put("file", 1);
        //wordMap.put("for", 1);
        wordMap.put("test", 1);
        wordMap.put("purposes", 1);
        //wordMap.put("but", 1);
        wordMap.put("surprise", 1);
        //wordMap.put("there", 1);
        //wordMap.put("second", 1);
        wordMap.put("line", 1);
        wordMap.put("time", 1);

        assertEquals(wordMap, wordCounter.getWordMap());


    }

    @Test
    void test_loadmobydick(){
        WordCounter wordCounter = new WordCounter("mobydick.txt");
        assertTrue(wordCounter.getStopWordsLoaded());
        assertTrue(wordCounter.getWordsLoaded());
    }

}
