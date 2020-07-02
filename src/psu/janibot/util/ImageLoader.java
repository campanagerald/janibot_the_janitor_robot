package psu.janibot.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    public BufferedImage loadImage(String path){
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }


    public BufferedImage loadImage(String path, int col, int row, int width, int height) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int x = (col * width) - width;
        int y = (row * height) - height;

        if (image != null) {
            image = image.getSubimage(x, y, width, height);
        }
        return image;
    }
}