
package Lab;

import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class Fantasmas {
    public int dx;
    public int x;
    public int dy;
    public int y;
    Timer time = new Timer(100000, null);

    public Fantasmas() {
        dx = 0;
        x = 0;
        dy = 0;
        y = 0;
        time.start();
    }

    public void keyPressed(KeyEvent k) {
        int tecla = k.getKeyCode();

        if (tecla == KeyEvent.VK_LEFT) {
            dy = 3;
        }
        if (tecla == KeyEvent.VK_RIGHT) {
            dy = 3;
        }
        if (tecla == KeyEvent.VK_UP) {
            dy = 3;
        }
    }

    public void movF() {
        x = x + dx;
        y = y + dy;
    }

}
