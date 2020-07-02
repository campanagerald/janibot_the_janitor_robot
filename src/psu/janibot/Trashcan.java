package psu.janibot;

import psu.janibot.util.ImageLoader;

import java.awt.*;

public class Trashcan {

    private static final Image image = new ImageLoader().loadImage("/images/game/trashcan.png");
    private Point location;

    public Trashcan(Point location) {
        this.location = location;
    }

    public void draw(Graphics g) {
        g.drawImage(image, location.x, location.y, null);
    }
}
