package pacman;
public final class Ghost extends MovingSprite {

    private String prevDir;
    private String currentDir;
    
    public Ghost(int x, int y) {
        super(x, y); 
        initGhost();
    }
    public void initGhost() { 
        loadImage("src\\pacman\\Ghost2.gif");
        prevDir = "up";  
    } 
    public String getCurrentDir() {
        return currentDir;
    }
    
    public void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }
    
    public String getPrevDir() {
        return prevDir;
    }
    
    public void setPrevDir(String prevDir) {
        this.prevDir = prevDir;
    }
}