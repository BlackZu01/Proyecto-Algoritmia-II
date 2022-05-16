int filas = 40;
int columnas = 40;
int squareSize = 20;
PImage fondo;

ArrayList<Integer> posX = new ArrayList<Integer>();
ArrayList<Integer> posY = new ArrayList<Integer>();

//Direcciones que puede tomar el pacman
int dir = 1;
int [] dx = {0, 0, -1, 1}; // En el eje X solo puede ir de izq a derecha
int [] dy = {-1, 1, 0, 0}; // En el eje Y solo puede ir de arriba a abajo

boolean gameOver = false;

void setup() {
    size(700, 700);
    fondo = loadImage("Images/Mapadenivelfinal.png");
    frameRate(5);
    posX.add(10);   // Indica que comenzara el pacman en la posicion <10, 10>
    posY.add(10);   
  }
  
  
void draw() {
    background(fondo);
    
    if (gameOver == true) {
      //Texto Inicio
    } 
    move();
    DrawPacman();
  }

void DrawPacman() {
    fill(248, 255, 13);
    for (int k=0 ; k<posX.size() ; k++) {
        ellipse(posX.get(k)*squareSize, posY.get(k)*squareSize, squareSize, squareSize);
      }
  }

void move() {
    posX.add(0, posX.get(0)+dx[dir]);
    posY.add(0, posY.get(0)+dy[dir]);
    posX.remove(posX.size() - 1);
    posY.remove(posY.size() - 1);
  }
  
void keyPressed(){
    if (key == 'w' || keyCode == UP) dir = 0;
    if (key == 's' || keyCode == DOWN) dir = 1;
    if (key == 'a' || keyCode == LEFT) dir = 2;
    if (key == 'd' || keyCode == RIGHT) dir = 3;
  }
