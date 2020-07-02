package psu.janibot.mode;
import psu.janibot.Program;
import psu.janibot.util.ImageLoader;
import psu.janibot.view.GamePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CountingLoopPanel extends JPanel{

    private JButton countingLoop1Button;
    private JButton countingLoop2Button;
    private JButton countingLoop3Button;
    private JButton countingLoop4Button;
    private JButton backButton;

    public CountingLoopPanel() {
        setBackground(GamePanel.backgroundColor);
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

        countingLoop1Button = new JButton(new ImageIcon(Program.class.getResource("/images/countingLoop/countingLoop1.png")));
        countingLoop1Button.setPreferredSize(new Dimension(countingLoop1Button.getIcon().getIconWidth(), countingLoop1Button.getIcon().getIconHeight()));

        countingLoop2Button = new JButton(new ImageIcon(Program.class.getResource("/images/countingLoop/countingLoop2.png")));
        countingLoop2Button.setPreferredSize(new Dimension(countingLoop2Button.getIcon().getIconWidth(), countingLoop2Button.getIcon().getIconHeight()));

        countingLoop3Button = new JButton(new ImageIcon(Program.class.getResource("/images/countingLoop/countingLoop3.png")));
        countingLoop3Button.setPreferredSize(new Dimension(countingLoop3Button.getIcon().getIconWidth(), countingLoop3Button.getIcon().getIconHeight()));

        countingLoop4Button = new JButton(new ImageIcon(Program.class.getResource("/images/countingLoop/countingLoop4.png")));
        countingLoop4Button.setPreferredSize(new Dimension(countingLoop4Button.getIcon().getIconWidth(), countingLoop4Button.getIcon().getIconHeight()));
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
        worldPanel.add(countingLoop1Button, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        worldPanel.add(countingLoop2Button, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        worldPanel.add(countingLoop3Button, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        worldPanel.add(countingLoop4Button, constraints);

        add(backButtonPanel, BorderLayout.NORTH);
        add(worldPanel, BorderLayout.CENTER);
    }

    public void setCountingLoop1ButtonActionListener(ActionListener countingLoop1ButtonActionListener){
        countingLoop1Button.addActionListener(countingLoop1ButtonActionListener);
    }

    public void setCountingLoop2ButtonActionListener(ActionListener countingLoop2ButtonActionListener){
        countingLoop2Button.addActionListener(countingLoop2ButtonActionListener);
    }

    public void setCountingLoop3ButtonActionListener(ActionListener countingLoop3ButtonActionListener){
        countingLoop3Button.addActionListener(countingLoop3ButtonActionListener);
    }

    public void setCountingLoop4ButtonActionListener(ActionListener countingLoop4ButtonActionListener){
        countingLoop4Button.addActionListener(countingLoop4ButtonActionListener);
    }

    public void setBackButtonActionListener(ActionListener backButtonActionListener){
        backButton.addActionListener(backButtonActionListener);
    }
}
