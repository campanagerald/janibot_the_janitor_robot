package psu.janibot;

import java.awt.*;

public class Block {

    private Point location;
    private Dirt dirt = null;
    private Trashcan trashcan = null;
    private boolean eastWall = false;
    private boolean southWall = false;

    public Block(Point location) {
        this.location = location;
        this.location.x = this.location.x * 40;
        this.location.y = this.location.y * 40;
    }

    public Point getLocation() {
        return location;
    }

    public void setDirt(Dirt dirt) {
        this.dirt = dirt;
    }

    public void setTrashcan(Trashcan trashcan) {
        this.trashcan = trashcan;
    }

    public boolean isEastWall() {
        return eastWall;
    }

    public void setEastWall(boolean eastWall) {
        this.eastWall = eastWall;
    }

    public boolean isSouthWall() {
        return southWall;
    }

    public void setSouthWall(boolean southWall) {
        this.southWall = southWall;
    }

    public void drawAll(Graphics g, Image eastWallImage, Image southWallImage){

        if (eastWallImage != null && southWallImage!= null) {
            if (eastWall) {
                g.drawImage(eastWallImage, (location.x + 38), (location.y + 2), null);
            }
            if (southWall) {
                g.drawImage(southWallImage, (location.x - 2), (location.y + 38), null);
            }
        }

        if (dirt != null) {
            dirt.draw(g);
        }

        if(trashcan != null){
            trashcan.draw(g);
        }
    }

    public void drawWall(Graphics g, Image eastWallImage, Image southWallImage){
        if(eastWall){
            g.drawImage(eastWallImage, (location.x + 38), (location.y + 2), null);
        }

        if(southWall){
            g.drawImage(southWallImage, (location.x - 2), (location.y + 38), null);
        }
    }

    public boolean hasTrashcan() {
        return trashcan != null;
    }

    public boolean hasDirt() {
        return dirt != null;
    }

    public void removeDirt() {
        if(dirt != null){
            dirt = null;
        }
    }
}