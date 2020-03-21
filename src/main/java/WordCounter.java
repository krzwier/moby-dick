import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Histogram;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.TickSettings;
import tech.tablesaw.plotly.traces.BarTrace;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


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
                // make sure empty string is in stop word list
                stopWords.add("");
                // also add single quote to stop word list, because it's not a word and
                // single quote sometimes gets picked up alone when used right after punctuation
                stopWords.add("’");
            }
            br.close();
            success = true;
        } catch (Exception e) {
            System.out.println("Error loading stop word file. Please make sure that stop-words.txt " +
                    "is located in the current directory.");
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
                // if regex "\\W+" is used here, apostrophes and hyphens will be
                // considered delimiters.  I've opted instead to include hyphens and apostrophes
                // as word characters.
                String[] words = line.split("[^a-zA-Z’-]+");
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
            System.out.println(e.getMessage());
        }
        return success;
    }

    /**
     * Converts wordMap into ArrayList of word-frequency entries,
     * then sorts that list so most frequent words are listed first
     *
     * @return              ArrayList of String-Int Entries
     */
    private ArrayList<Entry<String, Integer>> SortWords(){
        Set<Entry<String,Integer>> entries = this.getWordMap().entrySet();
        ArrayList<Entry<String, Integer>> wordFreq = new ArrayList<>(entries);
        Sorter sorter = new Sorter();
        wordFreq.sort(sorter);
        return wordFreq;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Word Counter program. " +
                "This program will generate a histogram of the 100 most frequent words " +
                "in any text file, excluding some commonly used words. " +
                "Please enter the name of a text file.");

        String filename = sc.next();


        WordCounter wordCounter = new WordCounter(filename);
        ArrayList<Entry<String, Integer>> wordFreq = wordCounter.SortWords();

        String[] words = new String[100];
        int[] frequencies = new int[100];
        for (int i = 0; i < 100; i++) {
            if (i >= wordFreq.size()){
                break;
            } else {
                words[i] = (wordFreq.get(i)).getKey();
                frequencies[i] = (wordFreq.get(i)).getValue();
            }
        }

        Table wordFrequencies = Table.create("Word Frequencies").addColumns(
                                StringColumn.create("Word", words),
                                IntColumn.create("Frequency", frequencies));

        Axis.AxisBuilder xAxisBuilder = Axis.builder();
        TickSettings.TickSettingsBuilder ticks = TickSettings.builder();
        ticks.showTickLabels(true).build();
        TickSettings tickSettings = ticks.build();
        xAxisBuilder.tickSettings(tickSettings);
        Axis xAxis = xAxisBuilder.build();
        Axis.AxisBuilder yAxisBuilder = Axis.builder();
        yAxisBuilder.title("Number of Occurrences");
        Axis yAxis = yAxisBuilder.build();
        Layout.LayoutBuilder builder = Layout.builder();
        builder.title("100 Most Frequent Words in " + filename);
        builder.hoverMode(Layout.HoverMode.CLOSEST);
        builder.xAxis(xAxis);
        builder.yAxis(yAxis);
        builder.autosize(true);
        Layout layout = builder.build();
        BarTrace trace = BarTrace.builder(wordFrequencies.categoricalColumn("Word"),
                wordFrequencies.numberColumn("Frequency")).build();
        Figure fig = new Figure(layout, trace);

        Plot.show(fig);

        sc.close();


    }

}
