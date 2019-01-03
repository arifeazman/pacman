package pacman;

public class Pacman extends MovingSprite {  
    
    private int dx;
    private int dy;
    private int prevX;
    private int prevY;
    
    public Pacman(int x, int y) {
        super(x, y); 
        initPacman();
    }
    
    private void initPacman() {
        prevX = getX();
        prevY = getY();
        loadImage("src\\pacman\\PacMan2left.gif");
    }
    
    public int getDx() {
        return dx;
    }
    
    public void setDx(int dx) {
        this.dx = dx;
    }
    
    public int getDy() {
        return dy;
    }
    
    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getPrevX() {
        return prevX;
    }
    
    public int getPrevY() {
        return prevY;
    }
    
    public void move(String dir) {
        if(dir == "left" || dir == "right") {
            prevX = getX();
            setX(getX() + dx);
        }
        if(dir == "up" || dir == "down"){
            prevY = getY();
            setY(getY() + dy);
        }
    }    
}