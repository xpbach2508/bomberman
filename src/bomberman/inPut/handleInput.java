package bomberman.inPut;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class handleInput {
    public boolean up, down, left, right;

    public void handlePressed (KeyEvent e) {
        switch (e.getCode()) {
            case W:
            case UP: {
                up = true;
                break;
            }
            case S:
            case DOWN: {
                down = true;
                break;
            }
            case A:
            case LEFT: {
                left = true;
                break;
            }
            case D:
            case RIGHT: {
                right = true;
                break;
            }
        }
    }

    public void handleReleased (KeyEvent e) {
        switch (e.getCode()) {
            case W:
            case UP: {
                up = false;
                break;
            }
            case S:
            case DOWN: {
                down = false;
                break;
            }
            case A:
            case LEFT: {
                left = false;
                break;
            }
            case D:
            case RIGHT: {
                right = false;
                break;
            }
        }
    }
}