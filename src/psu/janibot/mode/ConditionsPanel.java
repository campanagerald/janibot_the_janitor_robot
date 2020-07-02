package psu.janibot.mode;
import psu.janibot.Program;
import psu.janibot.util.ImageLoader;
import psu.janibot.view.GamePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConditionsPanel extends JPanel{

    private JButton conditions1Button;
    private JButton conditions2Button;
    private JButton conditions3Button;
    private JButton conditions4Button;
    private JButton backButton;

    public ConditionsPanel() {
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

        conditions1Button = new JButton(new ImageIcon(Program.class.getResource("/images/conditions/conditions1.png")));
        conditions1Button.setPreferredSize(new Dimension(conditions1Button.getIcon().getIconWidth(), conditions1Button.getIcon().getIconHeight()));

        conditions2Button = new JButton(new ImageIcon(Program.class.getResource("/images/conditions/conditions2.png")));
        conditions2Button.setPreferredSize(new Dimension(conditions2Button.getIcon().getIconWidth(), conditions2Button.getIcon().getIconHeight()));

        conditions3Button = new JButton(new ImageIcon(Program.class.getResource("/images/conditions/conditions3.png")));
        conditions3Button.setPreferredSize(new Dimension(conditions3Button.getIcon().getIconWidth(), conditions3Button.getIcon().getIconHeight()));

        conditions4Button = new JButton(new ImageIcon(Program.class.getResource("/images/conditions/conditions4.png")));
        conditions4Button.setPreferredSize(new Dimension(conditions4Button.getIcon().getIconWidth(), conditions4Button.getIcon().getIconHeight()));
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
        worldPanel.add(conditions1Button, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        worldPanel.add(conditions2Button, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        //worldPanel.add(conditions3Button, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        worldPanel.add(conditions4Button, constraints);

        add(backButtonPanel, BorderLayout.NORTH);
        add(worldPanel, BorderLayout.CENTER);
    }

    public void setConditions1ButtonActionListener(ActionListener conditions1ButtonActionListener){
        conditions1Button.addActionListener(conditions1ButtonActionListener);
    }

    public void setConditions2ButtonActionListener(ActionListener conditions2ButtonActionListener){
        conditions2Button.addActionListener(conditions2ButtonActionListener);
    }

    public void setConditions3ButtonActionListener(ActionListener conditions3ButtonActionListener){
        conditions3Button.addActionListener(conditions3ButtonActionListener);
    }

    public void setConditions4ButtonActionListener(ActionListener conditions4ButtonActionListener){
        conditions4Button.addActionListener(conditions4ButtonActionListener);
    }

    public void setBackButtonActionListener(ActionListener backButtonActionListener){
        backButton.addActionListener(backButtonActionListener);
    }
}

