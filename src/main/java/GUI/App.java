package GUI;

import wordcounter.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.TickSettings;
import tech.tablesaw.plotly.traces.BarTrace;

public class App {

    private JPanel panelMain;
    JButton open;
    JTextPane welcomeText;
    private JEditorPane histViewer;
    String filepath;
    String filename;

    WordCounter wordCounter;


    public App() {
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Choose Plain Text File");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                fc.setFileFilter(filter);
                if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
                    filepath = fc.getSelectedFile().getAbsolutePath();
                    filename = fc.getSelectedFile().getName();
                }
                wordCounter = new WordCounter(filepath);
                showHistogram();
            }
        });


    }

    private void showHistogram() {
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
        String js = fig.asJavascript("mydiv");

        Plot.show(fig);
        histViewer.setText("<html lang=\"en-US\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>100 Most Frequent Words in mobydick.txt</title>" +
                "<script src=\"https://cdn.plot.ly/plotly-latest.min.js\"></script>" +
                "</head>" +
                "<body>" +
                "<div id='target' ></div>" +
                js +
                "</body>" +
                "</html>");
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Word Counter Application");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // this method display the JFrame to center position of a screen
        frame.setContentPane(new App().panelMain);
        frame.pack();
        frame.setVisible(true);

    }


}
