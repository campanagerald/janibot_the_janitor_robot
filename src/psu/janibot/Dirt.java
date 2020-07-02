package psu.janibot;

import psu.janibot.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Dirt implements ActionListener{

    private static final Image image = new ImageLoader().loadImage("/images/game/dirt.png");
    private ImageIcon dirtRotating = new ImageIcon(Program.class.getResource("/images/game/dirt.gif"));
    private boolean rotate = false;
    private Timer timer;
    private Point location;

    public Dirt(Point location) {
        this.location = location;
        timer = new Timer(100, this);
        timer.start();
    }

    public void draw(Graphics g) {
        if(rotate){
            dirtRotating.paintIcon(null, g, location.x, location.y);
        }
        else{
            g.drawImage(image, location.x, location.y, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        int delay = random.nextInt(5000);

        if(rotate){
            rotate = false;
            timer.setDelay(delay);
        }else{
            rotate = true;
        }
    }
}