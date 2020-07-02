package psu.janibot.view;

import javax.swing.*;
import java.awt.*;

public class LoadingPanel extends JPanel{

    private JProgressBar progressBar;

    public LoadingPanel() {

        setLayout(new GridBagLayout());
        setBackground(GamePanel.backgroundColor);
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBorder(BorderFactory.createTitledBorder("Loading..."));
        add(progressBar, constraints);
    }

    public void setProgressBar(int value) {
        if(value == 0){
            progressBar.setValue(value);
        }else {
            int currentValue = progressBar.getValue();
            int newValue = currentValue + value;
            progressBar.setValue(newValue);
        }
    }
}
