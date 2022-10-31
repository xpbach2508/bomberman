package bomberman.inPut;

import javafx.scene.input.KeyEvent;

public class handleInput {
    public boolean up, down, left, right;
    public boolean space;

    public void handlePressed (KeyEvent e) {
        switch (e.getCode()) {
            case W, UP -> up = true;
            case S, DOWN -> down = true;
            case A, LEFT -> left = true;
            case D, RIGHT -> right = true;
            case SPACE -> space = true;
        }
    }

    public void handleReleased (KeyEvent e) {
        switch (e.getCode()) {
            case W, UP -> up = false;
            case S, DOWN -> down = false;
            case A, LEFT -> left = false;
            case D, RIGHT -> right = false;
            case SPACE -> space = false;
        }
    }
}