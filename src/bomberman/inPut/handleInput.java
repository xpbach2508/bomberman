package bomberman.inPut;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class handleInput {
    public boolean up, down, left, right;

    public void handlePressed (KeyEvent e) {
        switch (e.getCode()) {
            case UP: {
                up = true;
                break;
            }
            case DOWN: {
                down = true;
                break;
            }
            case LEFT: {
                left = true;
                break;
            }
            case RIGHT: {
                right = true;
                break;
            }
        }
    }

    public void handleReleased (KeyEvent e) {
        switch (e.getCode()) {
            case UP: {
                up = false;
                break;
            }
            case DOWN: {
                down = false;
                break;
            }
            case LEFT: {
                left = false;
                break;
            }
            case RIGHT: {
                right = false;
                break;
            }
        }
    }
}
