package psu.janibot.mode;

import psu.janibot.Program;
import psu.janibot.util.ImageLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConditionalLoopPanel extends JPanel {

    private JButton conditionalLoop1Button;
    private JButton conditionalLoop2Button;
    private JButton conditionalLoop3Button;
    private JButton conditionalLoop4Button;
    private JButton backButton;

    public ConditionalLoopPanel() {
        initializeComponents();
        designComponents();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new ImageLoader().loadImage("/images/game/janibot_background_blur.jpg");
        g.drawImage(image, 0, 0, null);
    }

    private void initializeComponents() {
        backButton = new JButton(new ImageIcon(Program.class.getResource("/images/game/back_button.png")));
        backButton.setPreferredSize(new Dimension(52, 52));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setContentAreaFilled(false);

        conditionalLoop1Button = new JButton(new ImageIcon(Program.class.getResource("/images/conditionalLoop/conditionalLoop1.png")));
        conditionalLoop1Button.setPreferredSize(new Dimension(conditionalLoop1Button.getIcon().getIconWidth(), conditionalLoop1Button.getIcon().getIconHeight()));

        conditionalLoop2Button = new JButton(new ImageIcon(Program.class.getResource("/images/conditionalLoop/conditionalLoop2.png")));
        conditionalLoop2Button.setPreferredSize(new Dimension(conditionalLoop2Button.getIcon().getIconWidth(), conditionalLoop2Button.getIcon().getIconHeight()));

        conditionalLoop3Button = new JButton(new ImageIcon(Program.class.getResource("/images/conditionalLoop/conditionalLoop3.png")));
        conditionalLoop3Button.setPreferredSize(new Dimension(conditionalLoop3Button.getIcon().getIconWidth(), conditionalLoop3Button.getIcon().getIconHeight()));

        conditionalLoop4Button = new JButton(new ImageIcon(Program.class.getResource("/images/conditionalLoop/conditionalLoop4.png")));
        conditionalLoop4Button.setPreferredSize(new Dimension(conditionalLoop4Button.getIcon().getIconWidth(), conditionalLoop4Button.getIcon().getIconHeight()));
    }

    private void designComponents() {

        setLayout(new BorderLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);

        JPanel worldPanel = new JPanel(new GridBagLayout());
        worldPanel.setOpaque(false);
        worldPanel.setBorder(BorderFactory.createTitledBorder(new LineBorder(null), "Select Maze", TitledBorder.CENTER, TitledBorder.CENTER, new Font("", Font.BOLD, 18)));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(9, 9, 9, 9);
        worldPanel.add(conditionalLoop1Button, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        //worldPanel.add(conditionalLoop2Button, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        worldPanel.add(conditionalLoop3Button, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        worldPanel.add(conditionalLoop4Button, constraints);

        add(backButtonPanel, BorderLayout.NORTH);
        add(worldPanel, BorderLayout.CENTER);
    }

    public void setConditionalLoop1ButtonActionListener(ActionListener conditionalLoop1ButtonActionListener){
        conditionalLoop1Button.addActionListener(conditionalLoop1ButtonActionListener);
    }

    public void setConditionalLoop2ButtonActionListener(ActionListener conditionalLoop2ButtonActionListener){
        conditionalLoop2Button.addActionListener(conditionalLoop2ButtonActionListener);
    }

    public void setConditionalLoop3ButtonActionListener(ActionListener conditionalLoop3ButtonActionListener){
        conditionalLoop3Button.addActionListener(conditionalLoop3ButtonActionListener);
    }

    public void setConditionalLoop4ButtonActionListener(ActionListener conditionalLoop4ButtonActionListener){
        conditionalLoop4Button.addActionListener(conditionalLoop4ButtonActionListener);
    }

    public void setBackButtonActionListener(ActionListener backButtonActionListener){
        backButton.addActionListener(backButtonActionListener);
    }
}
