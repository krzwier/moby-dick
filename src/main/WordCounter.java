package main;

import java.io.*;
//import java.util.HashMap;
//import java.util.Map;

public class WordCounter {

    /* private Map<String, Integer> wordMap = new HashMap<>(); */


    /**
     * @param   filename    name of file to read
     * @return              boolean indicating success
     */
    public boolean LoadWords(String filename) {
        boolean success = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            br.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

}
