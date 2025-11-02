import java.awt.*;

public class Player {
    private double velocityY;
    private int posX;
    private double posY;
    private int width;
    private int height;
    private Image image;

    public Player(int posX, int posY, int width, int height, Image image){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
        this.velocityY = 0;
    }

    public int getPosX() { return posX; }
    public int getPosY() { return (int) posY; }
    public void setPosX(int posX) { this.posX = posX; }
    public void setPosY(double posY) { this.posY = posY; }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Image getImage() { return image; }

    public double getVelocityY() { return velocityY; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
}
