package Lab;

import java.awt.event.KeyEvent;

public class Pacman {
    public int dx;
    public int x;
    public int dy;
    public int y;

    //constructor
    public Pacman() {
        x = 450;
    }

    //metodos
    //detectar que se presiona la tecla
    public void keyPressed(KeyEvent k) {
        int tecla = k.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT) {
            dx = -2;
        }
        if (tecla == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
        if (tecla == KeyEvent.VK_UP) {
            dy = -2;
        }
        if (tecla == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    //movimiento
    public void mov() {
        if (x + dx > 0 && x + dx < 600) {
            x = x + dx;
        }
        if (y + dy > 0 && y + dy < 600) {
            x = x + dx;
        }
    }

    //saber posicion de x
    public int coordx() {
        return x;
    }
    //saber posicion de y
    public int coordy() {
        return y;
    }
}
