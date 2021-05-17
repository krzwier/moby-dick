package GUI;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.TickSettings;
import tech.tablesaw.plotly.traces.BarTrace;
import wordcounter.WordCounter;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;


public class Main extends Application {
    String filepath;
    String filename;
    WordCounter wordCounter;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Word Counter Application");

        GridPane mainGrid = new GridPane();
        mainGrid.setPadding(new Insets(10,0,0,0));
        mainGrid.setStyle("-fx-background-color: #FFFFFF");
        mainGrid.setMinSize(1280,800);
        mainGrid.setAlignment(Pos.TOP_CENTER);

        GridPane topGrid = new GridPane();
        topGrid.setMinSize(1280,300);
        topGrid.setStyle("-fx-background-color: #FFFFFF");

        Image image = new Image("/pile-of-words.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        topGrid.add(imageView,0,0);

        Label welcomeMessage = new Label("Welcome to the Word Counter program. " +
                "This program will generate a histogram of the 100 most frequent words " +
                "in any text file (excluding some commonly used words). Please choose a " +
                "plain text file to analyze.");
        welcomeMessage.setPrefWidth(600);
        welcomeMessage.setAlignment(Pos.TOP_LEFT);
        welcomeMessage.setWrapText(true);
        welcomeMessage.setFont(new Font("Courier", 20));
        welcomeMessage.setPadding(new Insets(0,60,0,0));
        topGrid.add(welcomeMessage,1,0);

        Button button = new Button("Choose file...");
        button.setAlignment(Pos.CENTER);
        button.setMinSize(100,25);
        topGrid.add(button, 2, 0);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        WebView histViewer = new WebView();
        WebEngine engine = histViewer.getEngine();

        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                filepath = selectedFile.getAbsolutePath();
                filename = selectedFile.getName();
                String loadingMessage = "Loading...";
                engine.loadContent(loadingMessage,"text/plain");

                wordCounter = new WordCounter(filepath);
                String js = showHistogram();
                String content = "<!DOCTYPE html>\n" +
                        "<html lang=\"en-US\">\n" + "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>100 Most Frequent Words in mobydick.txt</title>\n" +
                        "    <script src=\"https://cdn.plot.ly/plotly-latest.min.js\"></script>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <div id='target' ></div>\n" +
                        "        " +
                        js +
                        "\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>\n";

                engine.loadContent(content,"text/html");
            }


        });

        mainGrid.add(topGrid,0,0);
        mainGrid.add(histViewer,0,1);

        Scene scene = new Scene(mainGrid, 1280, 800);
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    private String showHistogram() {
        ArrayList<Map.Entry<String, Integer>> wordFreq = wordCounter.getWordList();

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
        String js = fig.asJavascript("target");
        return js;
    }

    public static void main(String[] args) {
        launch();
    }

}
