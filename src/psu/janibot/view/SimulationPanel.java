package psu.janibot.view;

import psu.janibot.Block;
import psu.janibot.Janibot;
import psu.janibot.util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class SimulationPanel extends Canvas implements Runnable{

    public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 480;

    public boolean running = false;
    private Thread thread;

    private BufferedImage canvasImage = null;
    private Image backgroundImage;

    public enum GameState {
        INITIALIZE, RUNNING, GAME_OVER
    }

    GameState gameState;

    //game object initialization
    private Janibot janibot;
    private Block[][] blocks;
    private Image eastWallImage;
    private Image southWallImage;

    public SimulationPanel() {

        canvasImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        gameState = GameState.INITIALIZE;

        this.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setMaximumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setMinimumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    }

    private void draw(Graphics g) {

        if (backgroundImage != null){
            g.drawImage(backgroundImage, 0, 0, null);
        }

        int pixel = 39;
        g.setColor(new Color(116, 89, 68));
        for (int i = 0; i < 14; i++){
            g.fillRect(pixel, 0, 1, CANVAS_HEIGHT);
            pixel += 40;
        }

        pixel = 39;
        g.setColor(new Color(116, 89, 68));
        for (int i = 0; i < 11; i++){
            g.fillRect(0, pixel, CANVAS_WIDTH, 1);
            pixel += 40;
        }

        g.setColor(new Color(170, 87, 55));
        g.fillRect(0, 0, CANVAS_WIDTH, 2);
        g.fillRect(0, 0, 2, CANVAS_HEIGHT);
        g.fillRect(CANVAS_WIDTH - 2, 0, 2, CANVAS_HEIGHT);
        g.fillRect(0, CANVAS_HEIGHT - 2, CANVAS_WIDTH, 2);

        switch (gameState){

            case RUNNING:{

                for (int i = 0; i < blocks.length; i++) {
                    for (int j = 0; j < blocks[0].length; j++) {
                        blocks[i][j].drawAll(g, eastWallImage, southWallImage);
                    }
                }

                janibot.draw(g);
            }

            case INITIALIZE:{
                for (int i = 0; i < blocks.length; i++) {
                    for (int j = 0; j < blocks[0].length; j++) {
                        blocks[i][j].drawWall(g, eastWallImage, southWallImage);
                    }
                }
                break;
            }

            case GAME_OVER:{
                for (int i = 0; i < blocks.length; i++) {
                    for (int j = 0; j < blocks[0].length; j++) {
                        blocks[i][j].drawAll(g, eastWallImage, southWallImage);
                    }
                }
                janibot.draw(g);
            }
        }
    }

    public void render() {

        BufferStrategy bufferStrategy = getBufferStrategy();

        if(bufferStrategy == null){
            createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(canvasImage, 0, 0, getWidth(), getHeight() , null);
        draw(graphics);
        graphics.dispose();
        bufferStrategy.show();
    }

    public void update() {

    }

    public void start() {
        if(running){
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
    }

    public void setBlocks(Block[][] blocks) {
        this.blocks = blocks;
    }

    public void setJanibot(Janibot janibot) {
        this.janibot = janibot;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setEastWallImage(Image eastWallImage) {
        this.eastWallImage = eastWallImage;
    }

    public void setSouthWallImage(Image southWallImage) {
        this.southWallImage = southWallImage;
    }

    @Override
    public void run() {
        // This is the template for the run method

        long lastTime = System.nanoTime();
        final double amountofTicks = 60.0;
        double ns = 1000000000 / amountofTicks;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                update();
                delta--;
            }
            render();
        }
        stop();
    }
}