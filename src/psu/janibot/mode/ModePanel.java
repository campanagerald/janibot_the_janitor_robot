package psu.janibot.mode;

import psu.janibot.Program;
import psu.janibot.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ModePanel extends JPanel{

    private JButton simpleCommandsButton;
    private JLabel simpleCommandsLabel;
    private JButton countingLoopButton;
    private JLabel countingLoopLabel;
    private JButton conditionsButton;
    private JLabel conditionsLabel;
    private JButton conditionalLoopButton;
    private JLabel conditionalLoopLabel;
    private JButton customCommandsButton;
    private JLabel customCommandsLabel;

    public ModePanel() {
        initializeComponents();
        designComponents();
    }

    private void initializeComponents() {

        simpleCommandsButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/folder_unlock.png")));
        simpleCommandsButton.setFocusPainted(false);
        simpleCommandsButton.setPreferredSize(new Dimension(simpleCommandsButton.getIcon().getIconWidth(), simpleCommandsButton.getIcon().getIconHeight()));

        simpleCommandsLabel = new JLabel(new ImageIcon(Program.class.getResource("/images/simpleCommands/simpleCommandsLabel.png")));

        countingLoopButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/folder_unlock.png")));
        countingLoopButton.setFocusPainted(false);
        countingLoopButton.setPreferredSize(new Dimension(countingLoopButton.getIcon().getIconWidth(), countingLoopButton.getIcon().getIconHeight()));

        countingLoopLabel = new JLabel(new ImageIcon(Program.class.getResource("/images/countingLoop/countingLoopLabel.png")));

        conditionsButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/folder_unlock.png")));
        conditionsButton.setFocusPainted(false);
        conditionsButton.setPreferredSize(new Dimension(conditionsButton.getIcon().getIconWidth(), conditionsButton.getIcon().getIconHeight()));

        conditionsLabel = new JLabel(new ImageIcon(Program.class.getResource("/images/conditions/conditionsLabel.png")));


        conditionalLoopButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/folder_unlock.png")));
        conditionalLoopButton.setFocusPainted(false);
        conditionalLoopButton.setPreferredSize(new Dimension(conditionalLoopButton.getIcon().getIconWidth(), conditionalLoopButton.getIcon().getIconHeight()));

        conditionalLoopLabel = new JLabel(new ImageIcon(Program.class.getResource("/images/conditionalLoop/conditionalLoopLabel.png")));

        customCommandsButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/folder_unlock.png")));
        customCommandsButton.setFocusPainted(false);
        customCommandsButton.setPreferredSize(new Dimension(customCommandsButton.getIcon().getIconWidth(), customCommandsButton.getIcon().getIconHeight()));

        customCommandsLabel = new JLabel(new ImageIcon(Program.class.getResource("/images/customCommands/customCommandsLabel.png")));
    }

    private void designComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 3, 5);
        add(simpleCommandsButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(simpleCommandsLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        add(countingLoopButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(countingLoopLabel, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        add(conditionsButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        add(conditionsLabel, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        add(conditionalLoopButton, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        add(conditionalLoopLabel, constraints);

        constraints.gridx = 4;
        constraints.gridy = 0;
        add(customCommandsButton, constraints);

        constraints.gridx = 4;
        constraints.gridy = 1;
        add(customCommandsLabel, constraints);
    }

    public void setSimpleCommandsButtonActionListener(ActionListener simpleCommandsButtonActionListener){
        simpleCommandsButton.addActionListener(simpleCommandsButtonActionListener);
    }

    public void setCountingLoopButtonActionListener(ActionListener countingLoopButtonActionListener){
        countingLoopButton.addActionListener(countingLoopButtonActionListener);
    }

    public void setConditionsButtonActionListener(ActionListener conditionsButtonActionListener){
        conditionsButton.addActionListener(conditionsButtonActionListener);
    }

    public void setConditionalLoopButtonActionListener(ActionListener conditionalLoopButtonActionListener){
        conditionalLoopButton.addActionListener(conditionalLoopButtonActionListener);
    }

    public void setCustomCommandsButtonActionListener(ActionListener customCommandsButtonActionListener){
        customCommandsButton.addActionListener(customCommandsButtonActionListener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new ImageLoader().loadImage("/images/game/janibot_background.jpg");
        g.drawImage(image, 0, 0, null);
    }
}