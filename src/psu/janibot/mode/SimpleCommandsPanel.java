package psu.janibot.mode;

import psu.janibot.Program;
import psu.janibot.util.ImageLoader;
import psu.janibot.view.GamePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimpleCommandsPanel extends JPanel {

    private JButton simpleCommands1Button;
    private JButton simpleCommands2Button;
    private JButton simpleCommands3Button;
    private JButton backButton;

    public SimpleCommandsPanel() {
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

        simpleCommands1Button = new JButton(new ImageIcon(Program.class.getResource("/images/simpleCommands/simpleCommands1.png")));
        simpleCommands1Button.setPreferredSize(new Dimension(simpleCommands1Button.getIcon().getIconWidth(), simpleCommands1Button.getIcon().getIconHeight()));

        simpleCommands2Button = new JButton(new ImageIcon(Program.class.getResource("/images/simpleCommands/simpleCommands2.png")));
        simpleCommands2Button.setPreferredSize(new Dimension(simpleCommands2Button.getIcon().getIconWidth(), simpleCommands2Button.getIcon().getIconHeight()));

        simpleCommands3Button = new JButton(new ImageIcon(Program.class.getResource("/images/simpleCommands/simpleCommands3.png")));
        simpleCommands3Button.setPreferredSize(new Dimension(simpleCommands3Button.getIcon().getIconWidth(), simpleCommands3Button.getIcon().getIconHeight()));
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
        worldPanel.add(simpleCommands1Button, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        worldPanel.add(simpleCommands2Button, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        worldPanel.add(simpleCommands3Button, constraints);

        add(backButtonPanel, BorderLayout.NORTH);
        add(worldPanel, BorderLayout.CENTER);
    }

    public void setSimpleCommands1ButtonActionListener(ActionListener simpleCommands1ButtonActionListener){
        simpleCommands1Button.addActionListener(simpleCommands1ButtonActionListener);
    }

    public void setSimpleCommands2ButtonActionListener(ActionListener simpleCommands2ButtonActionListener){
        simpleCommands2Button.addActionListener(simpleCommands2ButtonActionListener);
    }

    public void setSimpleCommands3ButtonActionListener(ActionListener simpleCommands3ButtonActionListener){
        simpleCommands3Button.addActionListener(simpleCommands3ButtonActionListener);
    }

    public void setBackButtonActionListener(ActionListener backButtonActionListener){
        backButton.addActionListener(backButtonActionListener);
    }
}