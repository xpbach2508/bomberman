package bomberman.entities.Enemies;

import bomberman.entities.AnimatedEntity;
import bomberman.entities.Bomb;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static bomberman.Board.getStillEntityAt;

public class Enemies extends AnimatedEntity {
    protected int preX = 0, preY = 0;
    protected int timeChangeDir = 10;

    protected int timeStop = 0;

    public List<Character> goThrough = new ArrayList<>() {{
        add('#');
    }};

    public Enemies(int x, int y, Image img) {
        super(x,y,img);
    }

    public void setChangedCoordinates(int x, int y) {
        this.preX = x;
        this.preY = y;
    }

    protected void chooseSprite() {
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    public void update() {
        if (removed) {
            animate();
            chooseSprite();
            this.img = sprite.getFxImage();
            if (timeAnimation > 0) timeAnimation--;
        } else {
            animate();
            move();
            x += moveX;
            y += moveY;
            chooseSprite();
            this.img = sprite.getFxImage();
        }
    }
    protected void move() {
        moveX = 0;
        moveY = 0;
        if (direct == 0) {
            if (canMove(x, y - entitySpeed)) moveY -= entitySpeed;
        }
        if (direct == 1) {
            if (canMove(x + entitySpeed, y)) moveX += entitySpeed;
        }
        if (direct == 2) {
            if (canMove(x, y + entitySpeed)) moveY += entitySpeed;
        }
        if (direct == 3) {
            if (canMove(x - entitySpeed, y)) moveX -= entitySpeed;
        }
    }
    public void setDirection(Bomber player) {
        boolean canMoveRight = canMove(x + 4, y);
        boolean canMoveLeft = canMove(x - 4, y);
        boolean canMoveDown = canMove(x, y + 4);
        boolean canMoveUp = canMove(x, y - 4);
        Random newRandom = new Random();
        if (direct == 1) {
            if (canMoveUp && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                direct = 0;
                timeChangeDir = 5;
            }
            else if (canMoveDown && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                direct = 2;
                timeChangeDir = 5;
            }
            else if (!canMoveRight) {
                direct = 3;
                timeChangeDir--;
            }
        }
        else if (direct == 2) {
            if (canMoveLeft && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                direct = 3;
                timeChangeDir = 5;
            }
            else if (canMoveRight && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                direct = 1;
                timeChangeDir = 5;
            }
            else if (!canMoveDown) {
                direct = 0;
                timeChangeDir--;
            }
        }
        else if (direct == 3) {
            if (canMoveUp && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                direct = 0;
                timeChangeDir = 5;
            }
            else if (canMoveDown && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                direct = 2;
                timeChangeDir = 5;
            }
            else if (!canMoveLeft) {
                direct = 1;
                timeChangeDir--;
            }
        }
        else if (direct == 0) {
            if (canMoveLeft && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                direct = 3;
                timeChangeDir = 5;
            }
            else if (canMoveRight && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                direct = 1;
                timeChangeDir = 5;
            }
            else if (!canMoveUp) {
                direct = 2;
                timeChangeDir--;
            }
        }
    }

    @Override
    public boolean canMove(int x, int y) {
        double topLeftX = (double) x + 1;
        double topLeftY = (double) y + 1;
        double topRightX = (double) x + (double) Sprite.SCALED_SIZE - 1;
        double topRightY = (double) y + 1;
        double botLeftX = (double) x + 1;
        double botLeftY = (double) y + (double) Sprite.SCALED_SIZE - 1;
        double botRightX = (double) x + (double) Sprite.SCALED_SIZE - 1;
        double botRightY = (double) y + (double) Sprite.SCALED_SIZE - 1;
        Entity topL = getStillEntityAt(topLeftX, topLeftY);
        Entity topR = getStillEntityAt(topRightX, topRightY);
        Entity botL = getStillEntityAt(botLeftX, botLeftY);
        Entity botR = getStillEntityAt(botRightX, botRightY);
        if (topL instanceof Wall || topL instanceof Brick || topL instanceof Bomb) {
            return false;
        }
        if (topR instanceof Wall || topR instanceof Brick || topR instanceof Bomb) {
            return false;
        }
        if (botL instanceof Wall || botL instanceof Brick || botL instanceof Bomb) {
            return false;
        }
        if (botR instanceof Wall || botR instanceof Brick || botR instanceof Bomb) {
            return false;
        }
        return true;
    }

}
