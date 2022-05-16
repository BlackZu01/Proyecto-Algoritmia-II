PImage fondo;
PImage pacman[] = new PImage[4];
int pacmanX = 0, pacmanY = 0;

void setup() {
  size(966, 720);
  smooth();
  fondo = loadImage("Images/background.png");
 
  for (int k= 0; k< pacman.length; k++) {
    pacman[k] = loadImage("ImagesJ/pacman"+(k+1)+".gif");
    pacman[k].resize(35, 35);
    frameRate(10);
  }
 
  //pacman[0].resize(35, 35);
  //pacman[1].resize(35, 35);
  //pacman[2].resize(35, 35);
  //pacman[3].resize(35, 35);
  //frameRate(10);
   
}

void draw() {
  background(fondo);
  image(pacman[frameCount%4], pacmanX, pacmanY);
}

void keyPressed() {
  if (keyCode == UP) {
    pacmanY -= 2;
  } else if(keyCode==DOWN) {
    pacmanY += 2;
  } else if (keyCode == RIGHT) {
    pacmanX += 2;
  } else if (keyCode == LEFT) {
    pacmanX -= 2;
  }
}
