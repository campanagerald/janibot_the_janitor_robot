package psu.janibot.mode;

import psu.janibot.Program;
import psu.janibot.util.ImageLoader;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomCommandsPanel extends JPanel{

    private JButton customCommands1Button;
    private JButton customCommands2Button;
    private JButton customCommands3Button;
    private JButton customCommands4Button;
    private JButton customCommands5Button;
    private JButton customCommands6Button;
    private JButton customCommands7Button;
    private JButton customCommands8Button;
    private JButton customCommands9Button;
    private JButton customCommands10Button;
    private JButton backButton;

    public CustomCommandsPanel() {
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

        customCommands1Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands1.png")));
        customCommands1Button.setPreferredSize(new Dimension(customCommands1Button.getIcon().getIconWidth(), customCommands1Button.getIcon().getIconHeight()));

        customCommands2Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands2.png")));
        customCommands2Button.setPreferredSize(new Dimension(customCommands2Button.getIcon().getIconWidth(), customCommands2Button.getIcon().getIconHeight()));

        customCommands3Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands3.png")));
        customCommands3Button.setPreferredSize(new Dimension(customCommands3Button.getIcon().getIconWidth(), customCommands3Button.getIcon().getIconHeight()));

        customCommands4Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands4.png")));
        customCommands4Button.setPreferredSize(new Dimension(customCommands4Button.getIcon().getIconWidth(), customCommands4Button.getIcon().getIconHeight()));

        customCommands5Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands5.png")));
        customCommands5Button.setPreferredSize(new Dimension(customCommands5Button.getIcon().getIconWidth(), customCommands5Button.getIcon().getIconHeight()));

        customCommands6Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands6.png")));
        customCommands6Button.setPreferredSize(new Dimension(customCommands6Button.getIcon().getIconWidth(), customCommands6Button.getIcon().getIconHeight()));

        customCommands7Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands7.png")));
        customCommands7Button.setPreferredSize(new Dimension(customCommands7Button.getIcon().getIconWidth(), customCommands7Button.getIcon().getIconHeight()));

        customCommands8Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands8.png")));
        customCommands8Button.setPreferredSize(new Dimension(customCommands8Button.getIcon().getIconWidth(), customCommands8Button.getIcon().getIconHeight()));

        customCommands9Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands9.png")));
        customCommands9Button.setPreferredSize(new Dimension(customCommands9Button.getIcon().getIconWidth(), customCommands9Button.getIcon().getIconHeight()));

        customCommands10Button = new JButton(new ImageIcon(Program.class.getResource("/images/customCommands/customCommands10.png")));
        customCommands10Button.setPreferredSize(new Dimension(customCommands10Button.getIcon().getIconWidth(), customCommands10Button.getIcon().getIconHeight()));

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
        worldPanel.add(customCommands1Button, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        worldPanel.add(customCommands2Button, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        worldPanel.add(customCommands3Button, constraints);

        /*constraints.gridx = 3;
        constraints.gridy = 0;
        worldPanel.add(customCommands4Button, constraints);

        constraints.gridx = 4;
        constraints.gridy = 0;
        worldPanel.add(customCommands5Button, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        worldPanel.add(customCommands6Button, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        worldPanel.add(customCommands7Button, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        worldPanel.add(customCommands8Button, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        worldPanel.add(customCommands9Button, constraints);

        constraints.gridx = 4;
        constraints.gridy = 1;
        worldPanel.add(customCommands10Button, constraints);
        */
        add(backButtonPanel, BorderLayout.NORTH);
        add(worldPanel, BorderLayout.CENTER);
    }

    public void setCustomCommands1ButtonActionListener(ActionListener customCommands1ButtonActionListener){
        customCommands1Button.addActionListener(customCommands1ButtonActionListener);
    }

    public void setCustomCommands2ButtonActionListener(ActionListener customCommands2ButtonActionListener){
        customCommands2Button.addActionListener(customCommands2ButtonActionListener);
    }

    public void setCustomCommands3ButtonActionListener(ActionListener customCommands3ButtonActionListener){
        customCommands3Button.addActionListener(customCommands3ButtonActionListener);
    }

    public void setCustomCommands4ButtonActionListener(ActionListener customCommands4ButtonActionListener){
        customCommands4Button.addActionListener(customCommands4ButtonActionListener);
    }

    public void setCustomCommands5ButtonActionListener(ActionListener customCommands5ButtonActionListener){
        customCommands5Button.addActionListener(customCommands5ButtonActionListener);
    }

    public void setCustomCommands6ButtonActionListener(ActionListener customCommands6ButtonActionListener){
        customCommands6Button.addActionListener(customCommands6ButtonActionListener);
    }

    public void setCustomCommands7ButtonActionListener(ActionListener customCommands7ButtonActionListener){
        customCommands7Button.addActionListener(customCommands7ButtonActionListener);
    }

    public void setCustomCommands8ButtonActionListener(ActionListener customCommands8ButtonActionListener){
        customCommands8Button.addActionListener(customCommands8ButtonActionListener);
    }

    public void setCustomCommands9ButtonActionListener(ActionListener customCommands9ButtonActionListener){
        customCommands9Button.addActionListener(customCommands9ButtonActionListener);
    }

    public void setCustomCommands10ButtonActionListener(ActionListener customCommands10ButtonActionListener) {
        customCommands10Button.addActionListener(customCommands10ButtonActionListener);
    }

    public void setBackButtonActionListener(ActionListener backButtonActionListener){
        backButton.addActionListener(backButtonActionListener);
    }
}
