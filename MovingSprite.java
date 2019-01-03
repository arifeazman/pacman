package pacman;
import java.awt.Image;
import javax.swing.ImageIcon;

public class MovingSprite {
   
    private int x;
    private int y;
    private Image image;
    
    public MovingSprite(int x, int y) {  
        this.x = x;
        this.y = y;
    }
 
    protected void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public Image getImage() {
        return image;
    }
}