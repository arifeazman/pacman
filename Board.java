package pacman;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Pacman pacman;
    private List<Ghost> ghosts;  
    private int timeCount;
    private int points;
    private int pacLife;
    private final int DELAY = 70;
    private final int blockSize = 25;
    private boolean inGame;
    private boolean youWin;
    private String pacMoveDir; 

    private final String pac2left     = "src\\pacman\\PacMan2left.gif";
    private final String pac2right    = "src\\pacman\\PacMan2right.gif";
    private final String pac2up       = "src\\pacman\\PacMan2up.gif";
    private final String pac2down     = "src\\pacman\\PacMan2down.gif";
    private final String pac3left     = "src\\pacman\\PacMan3left.gif";
    private final String pac3right    = "src\\pacman\\PacMan3right.gif";
    private final String pac3up       = "src\\pacman\\PacMan3up.gif";
    private final String pac3down     = "src\\pacman\\PacMan3down.gif";
    private final String pac4left     = "src\\pacman\\PacMan4left.gif";
    private final String pac4right    = "src\\pacman\\PacMan4right.gif";
    private final String pac4up       = "src\\pacman\\PacMan4up.gif";
    private final String pac4down     = "src\\pacman\\PacMan4down.gif";    
          
    private int maze[][] = {
        {19, 26, 26, 18, 26, 26, 26, 22,  7, 19, 26, 26, 26, 18, 26, 26, 22},
        {21,  3,  6, 21,  3,  2,  6, 21,  5, 21,  3,  2,  6, 21,  3,  6, 21},
        {21,  9, 12, 21,  9,  8, 12, 21, 13, 21,  9,  8, 12, 21,  9, 12, 21},
        {17, 26, 26, 16, 26, 18, 26, 24, 26, 24, 26, 18, 26, 16, 26, 26, 20},
        {21, 11, 14, 21,  7, 21, 11, 10,  2, 10, 14, 21,  7, 21, 11, 14, 21},
        {25, 26, 26, 20,  5, 25, 26, 22,  5, 19, 26, 28,  5, 17, 26, 26, 28},
        { 3,  2,  6, 21,  1, 10, 14, 21, 13, 21, 11, 10,  4, 21,  3,  2,  6},
        { 9,  8, 12, 21, 13, 19, 26, 16, 18, 16, 26, 22, 13, 21,  9,  8, 12},
        {27, 26, 26, 16, 26, 20,  7, 25, 16, 28,  7, 17, 26, 16, 26, 26, 30},
        { 3,  2,  6, 21,  7, 21,  9, 14, 29, 11, 12, 21,  7, 21,  3,  2,  6},
        { 9,  8, 12, 21,  5, 17, 26, 26, 10, 26, 26, 20,  5, 21,  9,  8, 12},
        {19, 26, 26, 20, 13, 21, 11, 10,  2, 10, 14, 21, 13, 17, 26, 26, 22},
        {21, 11,  6, 17, 26, 24, 26, 22,  5, 19, 26, 24, 26, 20,  3, 14, 21},
        {25, 22, 13, 21,  3, 10, 14, 21, 13, 21, 11, 10,  6, 21, 13, 19, 28},
        {15, 17, 26, 28,  5, 19, 18, 24, 26, 24, 18, 22,  5, 25, 26, 20, 15},
        {19, 20, 11, 10, 12, 17, 20, 11,  2, 14, 17, 20,  9, 10, 14, 17, 22},
        {25, 24, 26, 26, 26, 24, 24, 30, 13, 27, 24, 24, 26, 26, 26, 24, 28}
    };
    
    public Board(){
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());	
        setFocusable(true);	
        setBackground(Color.BLACK);
        setSurfaceSize();
        timeCount = 0;
        inGame = true;
        youWin = false;
        pacMoveDir = "left";
        points = 0;
        pacLife = 3;
        pacman = new Pacman(8*blockSize, 10*blockSize);
        initGhosts();
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void setSurfaceSize() {	
        Dimension d = new Dimension();	
        d.width = 17 * blockSize + 1;
        d.height = 17 * blockSize + 30;
        setPreferredSize(d);
    }
    
    public void initGhosts() {		
        ghosts = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            ghosts.add(new Ghost(8*blockSize + 2, 8*blockSize + 2));
        }    
    }
    
    @Override 
    public void paintComponent(Graphics g) { 
        if(!inGame)			
            drawGameOver(g);
        else if(youWin)				
            drawYouWin(g);			
        else {
            super.paintComponent(g);
            drawObjects(g);
        }
    }
    public void drawObjects(Graphics g) {
        drawPacman(g);
        Graphics2D g2d = (Graphics2D) g;
        drawMaze(maze, g2d);
        drawGhosts(g2d);
        drawPoints(g2d);
        drawPacLife(g2d);
    }
    
    public void drawPacman(Graphics g) {	
        g.drawImage(pacman.getImage(), pacman.getX()+1, pacman.getY()+1, this);      
    }
    
    public void drawPoints(Graphics2D g2d) {
        g2d.setColor(Color.yellow);
        g2d.setFont(new Font("Calibri", Font.BOLD, 13));
        g2d.drawString("Puan: " + points, 20, 445);
    }
    
    public void drawPacLife(Graphics2D g2d) {	
        Image image;			
        ImageIcon ii = new ImageIcon(pac3right);
        image = ii.getImage();
        
        for(int i=0; i < pacLife; i++) {
            g2d.drawImage(image, 330+i*blockSize, 430, this);
        }
    }
    
    public void drawGameOver(Graphics g) {	
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.setFont(new Font("Calibri", Font.BOLD, 50));
        g2d.drawString("GAME OVER", 80, 230);
    }

    public void drawYouWin(Graphics g) {	
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.green);
        g2d.setFont(new Font("Calibri", Font.BOLD, 50));
        g2d.drawString("YOU WIN", 100, 230);
    }
    public void drawGhosts(Graphics2D g2d) {	    
        for(Ghost gh: ghosts)
            g2d.drawImage(gh.getImage(), gh.getX()+2, gh.getY()+2, this);         
    }
    
    public void drawMaze(int[][] mazeArray, Graphics2D g2d) {   
        for(int y = 0; y < 17; y++) {		
            for(int x = 0; x < 17; x++) {  	
                g2d.setPaint(Color.WHITE);	
                if((maze[y][x] & 16) != 0) {
                    //points  
                    g2d.fillOval(x*blockSize+7, y*blockSize+7, 9, 9);
                }
                
                if((maze[y][x] & 8) != 0){
                    //down
                    g2d.drawLine(x*blockSize, (y+1)*blockSize, (x+1)*blockSize, (y+1)*blockSize);
                }
                
                if((maze[y][x] & 4) != 0){
                    //right
                    g2d.drawLine((x+1)*blockSize, y*blockSize, (x+1)*blockSize, (y+1)*blockSize);
                }
                
                if((maze[y][x] & 2) != 0) {
                    //up
                    g2d.drawLine(x*blockSize, y*blockSize, (x+1)*blockSize, y*blockSize);
                }
                
                if((maze[y][x] & 1) != 0) {
                    //left
                    g2d.drawLine(x*blockSize, y*blockSize, x*blockSize, (y+1)*blockSize);
                }
            }
        }
    } // end of drawMaze
    
    public void moveGhost(Ghost gh){
        int xPos = gh.getX() / blockSize;	
        int yPos = gh.getY() / blockSize;	
        boolean pass = false;			 
        while(pass == false) {			
            if(maze[yPos][xPos] == 11 || maze[yPos][xPos] == 27) {
                gh.setCurrentDir("right");
                pass = true;
            } else if(maze[yPos][xPos] == 7 || maze[yPos][xPos] == 7 + 16) {
                gh.setCurrentDir("down");
                pass = true;
            } else if(maze[yPos][xPos] == 14 || maze[yPos][xPos] == 30) {
                gh.setCurrentDir("left");
                pass = true;
            } else if(maze[yPos][xPos] == 13 || maze[yPos][xPos] == 13 + 16) {
                gh.setCurrentDir("up");
                pass = true;
            } else {
                Random r = new Random();
                int value = r.nextInt(4);
            
                switch(value) {
                case 0:  
                    if((maze[yPos][xPos] & 1) == 0) {
                        if(gh.getPrevDir() == "right")
                            pass = false;
                        else {
                            gh.setCurrentDir("left");
                            pass = true;
                            }
                    } else
                        pass = false;
                    break;
                case 1:    
                    if((maze[yPos][xPos] & 2) == 0) {
                        if((maze[yPos][xPos] & 1) == 1 && (maze[yPos][xPos] & 4) == 1 && (maze[yPos][xPos] & 8) == 1) {
                            gh.setCurrentDir("down");
                            pass = true;
                        } else if(gh.getPrevDir() == "down") {
                            pass = false;
                        } else {
                            gh.setCurrentDir("up");
                            pass = true;
                        }
                    } else
                        pass = false;   
                    break;
                case 2:
                    if((maze[yPos][xPos] & 4) == 0) { 
                        if((maze[yPos][xPos] & 2) == 1 && (maze[yPos][xPos] & 1) == 1 && (maze[yPos][xPos] & 8) == 1) {
                            gh.setCurrentDir("left");
                            pass = true;
                        } else if(gh.getPrevDir() == "left") {
                            pass = false;
                        } else {
                            gh.setCurrentDir("right");
                            pass = true;
                        } 
                    
                    } else
                        pass = false;
                    break;
                default:
                    if((maze[yPos][xPos] & 8) == 0) {
                        if((maze[yPos][xPos] & 2) == 1 && (maze[yPos][xPos] & 1) == 1 && (maze[yPos][xPos] & 4) == 1) {
                            gh.setCurrentDir("up");
                            pass = true;
                        } else if(gh.getPrevDir() == "up")
                            pass = false;
                        else {
                            gh.setCurrentDir("down");
                            pass = true;
                        }
                    } else
                        pass = false;
                
                    break;
                } // end of switch case
            } // end of else  
        } // end of while
        
        if(gh.getCurrentDir() == "right") 
            xPos++;

        if(gh.getCurrentDir() == "left") 
            xPos--;
            
        if(gh.getCurrentDir() == "down")
            yPos++;
        
        if(gh.getCurrentDir() == "up")
            yPos--;
        
        gh.setX(xPos*blockSize);
        gh.setY(yPos*blockSize);
        gh.setPrevDir(gh.getCurrentDir());
        
    } // end of moveGhost
    
    public boolean pacmanCanMove() {
        int x = pacman.getX()/blockSize;
        int y = pacman.getY()/blockSize;
        switch(pacMoveDir) {   		
            case "left":	
                if(x == 0)	
                    return false;
                else {
                    pacman.loadImage(pac4left);
                    return (maze[y][x-1] & 4) == 0;
                }
            case "right":
                if(x==16)
                    return false;
                else {
                    pacman.loadImage(pac4right);
                    return (maze[y][x+1] & 1) == 0;
                }
   
            case "up":
                if(y==0)
                    return false;
                else {
                    pacman.loadImage(pac4up);
                    return (maze[y-1][x] & 8) == 0;
                }
            
            case "down":
                if(y==16)
                    return false;
                else {
                    pacman.loadImage(pac4down);
                    return (maze[y+1][x] & 2) == 0; 
                }
        }    
        return false;
    } // end of pacmanCanMove
    
    public void collectPoint(int[][] mazeArray, int x, int y) {
        if((mazeArray[y][x] & 16) != 0) {
           mazeArray[y][x] -= 16;
           points += 5;
        }
    } // end of collectPoint

     public int checkCollision(){
        int collisionNum = 0;
        for(Ghost gh: ghosts) {
            if(pacman.getX() == gh.getX() && pacman.getY() == gh.getY())
                collisionNum++;   
        }
        return collisionNum;
    } // end of checkCollision
  
    @Override
    public void actionPerformed(ActionEvent e) {
        if(timeCount %3  == 0) {
            switch(pacMoveDir) {
                case "left":
                    pacman.loadImage(pac2left);
                    break;
                case "right":
                    pacman.loadImage(pac2right);
                    break;
                case "up":
                    pacman.loadImage(pac2up);
                    break;
                default:
                    pacman.loadImage(pac2down);
                    break;
            }
        } else if(timeCount %3 == 1) {
            switch(pacMoveDir) {
                case "left":
                    pacman.loadImage(pac3left);
                    break;
                case "right":
                    pacman.loadImage(pac3right);
                    break;
                case "up":
                    pacman.loadImage(pac3up);
                    break;
                default:
                    pacman.loadImage(pac3down);
                    break;
            }
        } else { 
            int collNum = 0;
            if(pacLife < 1)
                inGame = false;
            else { 
                if(pacmanCanMove()) {
                    pacman.move(pacMoveDir);
                    collNum += checkCollision();
                    collectPoint(maze, pacman.getX()/blockSize, pacman.getY()/blockSize);
                    if(points == 865) {
                        youWin = true;
                    }
                } 
                for(Ghost gh: ghosts) {
                    moveGhost(gh);
                    if(!(pacman.getX() == pacman.getPrevX() && pacman.getY() == pacman.getPrevY()))
                        collNum += checkCollision();
                }
                if(collNum > 0)
                    pacLife--;
            }
        } 
        repaint();
        timeCount++;
    } // end of actionPerformed
    
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT ) {
                pacman.setDx(-blockSize);
                pacMoveDir = "left";
            }

            if(key == KeyEvent.VK_RIGHT) {
                pacman.setDx(blockSize);
                pacMoveDir = "right";
            }    
            
            if(key == KeyEvent.VK_UP) {
                pacman.setDy(-blockSize);
                pacMoveDir = "up";
            }
            
            if(key == KeyEvent.VK_DOWN) {
                pacman.setDy(blockSize);
                pacMoveDir = "down";
            }
        } // end of KeyPressed
    } // end of TAdapter
} // end of Board Class