package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    private JPanel panelMain;
    JButton open;
    JTextPane welcomeText;
    String filepath;


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
                }


            }
        });
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
