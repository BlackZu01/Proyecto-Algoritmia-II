
package Lab;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Mapa extends JPanel implements ActionListener  {

    private Dimension dim; //Altura y ancho de la pnatalla
    // fuente de la letra en pantalla
    private Boolean inGame = false; //determina el incio y final de la partida
    private boolean dying = false; // determina cuando esta vivo o muerto el pacman
    
    private final int BLOCK_SIZE = 24; //tamaño del bloque
    private final int N_BLOCKS = 20; //cantidad de bloques
    private final int SCREEN_SIZE = BLOCK_SIZE*N_BLOCKS; //Tamaño de pantalla
    private final int MAX_GHOSTS = 6;
    private final int PACMAN_SPEED = 4;
    
    private int N_GHOST = 4;
    private int lives, score;
    private int [] dx, dy;
    private int [] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed; //direcciones de los fantasmas
    
    private Image heart, ghost;
    private Image up, down, left, right; //imagenes del pacman
    
    private int pacman_x, pacman_y, pacman_dx, pacman_dy; // direcciones del pacman
    private int req_dx, req_dy;
    
    private final int validSpeed[] = {1,2,3,4,5,6};
    private final int maxSpeed = 5;
    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;
    
    /*
    0 = pared
    1 = borde izquierdo
    2 = borde superior
    4 = borde derecho 
    8 = borde inferior
    16 = puntos blancos
    
    cada poscion se determina sumando estos diferentes puntos 
    ejemplo:
    el borde superior izquierdo tendra 
    16 + 1 + 2 = 19 
    puntos + borde izq + borde sup = numero en la matriz
    */
    private final short levelData[]= {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0,19,26,26,26,18,18,18,22, 0, 0,19,18,18,18,26,26,26,22, 0,
        0,21, 0, 0, 0,17,16,16,20, 0, 0,17,16,16,18, 0, 0, 0,21, 0,
        0,21, 0,19,18,16,16,16,20, 0, 0,17,16,16,16,18,22, 0,21, 0,
        0,21, 0,17,24,24,24,24,16,18,18,16,24,24,24,24,20, 0,21, 0,
        0,17,18,20, 0, 0, 0, 0,17,16,16,20, 0, 0, 0, 0,17,18,20, 0,
        0,17,16,16,18,18,18,18,16,16,16,16,18,18,18,18,16,16,20, 0,
        0,17,16,24,24,16,24,24,16,16,16,16,16,16,24,16,24,16,20, 0,
        0,25,20, 0, 0,21, 0, 0,17,24,16,16,24,20, 0,21, 0,17,28, 0,
        0, 0,21, 0, 0,21, 0, 0,21, 0,17,20, 0,21, 0,21, 0,21, 0, 0, 
        26,26,20, 0, 0,21, 0, 0,21, 0,25,28, 0,21, 0,21, 0,17,26,26,
        0, 0,21, 0, 0,21, 0, 0,21, 0, 0, 0, 0,21, 0,21, 0,21, 0, 0,
        0,19,20, 0, 0,21, 0, 0,17,18,18,18,18,20, 0,21, 0,17,22, 0,
        0,17,16,18,18,16,18,18,16,16,16,16,16,16,18,16,18,16,20, 0,
        0,17,24,16,24,24,24,24,16,16,16,16,24,24,24,24,16,24,20, 0,
        0,21, 0,21, 0, 0, 0, 0,17,24,24,20, 0, 0, 0, 0,21, 0,21, 0,
        0,21, 0,25,26,18,18,18,20, 0, 0,17,18,18,18,26,28, 0,21, 0,
        0,21, 0, 0, 0,17,16,16,20, 0, 0,17,16,16,20, 0, 0, 0,21, 0,
        0,25,26,26,26,24,24,24,28, 0, 0,25,24,24,24,26,26,26,28, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        
    };
    
    public Mapa(){
        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();   
    }
    
    private void loadImages(){
        down = new ImageIcon("/Fotos/pac-abajo.gif").getImage();
        up = new ImageIcon("/Fotos/pac-arriba.gif").getImage();
        left = new ImageIcon("/Fotos/pac-izquierda.gif").getImage();
        right = new ImageIcon("/Fotos/pac-derecha.gif").getImage();
        ghost = new ImageIcon("/Fotos/Fantasmita-Morado.gif").getImage();
    }
    
    private void initVariables(){
        screenData = new short[N_BLOCKS*N_BLOCKS];
        dim = new Dimension(500,500);
        ghost_x = new int [MAX_GHOSTS];
        ghost_dx = new int [MAX_GHOSTS];
        ghost_y = new int [MAX_GHOSTS];
        ghost_dy = new int [MAX_GHOSTS];
        ghostSpeed = new int [MAX_GHOSTS];
        dx = new int [4];
        dy = new int[4];
        
        timer = new Timer(40,this);
        timer.restart();
    }
    
    private void initGame(){
        lives = 3;
        score = 0;
        initLevel();
        N_GHOST = 4;
        currentSpeed = 3;        
    }
    
    private void initLevel(){
        for (int i = 0; i < (N_BLOCKS*N_BLOCKS); i++) {
            screenData[i] = levelData[i];
        }
    }
    
       
    
    private void continueLevel(){
        int dx=1;
        int random;
        
        for (int i = 0; i < N_GHOST; i++) {
            ghost_y[i] = 4*BLOCK_SIZE;
            ghost_x[i] = 4*BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random()* (currentSpeed+1));
            
            if (random > currentSpeed){
                random = currentSpeed;
            }
            
            ghostSpeed[i] = validSpeed[random];
            
        }
        
        pacman_x = 7*BLOCK_SIZE;
        pacman_y = 11*BLOCK_SIZE;
        pacman_dx = 0;
        pacman_dy = 0;
        req_dx = 0;
        req_dy = 0;
        dying = false;
    }
    
    class TAdapter extends KeyAdapter{
       public void keyPressed(KeyEvent e){
           int key = e.getKeyCode();
           if (inGame){
               if (key == KeyEvent.VK_LEFT) {
                  req_dx = -1;
                  req_dy = 0;
               }
               else if (key == KeyEvent.VK_RIGHT) {
                  req_dx = 1;
                  req_dy = 0;
               }
               else if (key == KeyEvent.VK_UP) {
                  req_dx = 0;
                  req_dy = -1;
               }
               else if (key == KeyEvent.VK_DOWN) {
                  req_dx = 0;
                  req_dy = 1;
               }
               else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                   inGame = false;
               }
               
           }else{
               if (key == KeyEvent.VK_SPACE) {
                   inGame = true;
                   initGame();
               }
           }
           
       } 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
