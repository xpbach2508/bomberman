package bomberman.entities.Enemies;

import bomberman.entities.AnimatedEntity;
import bomberman.entities.Bomb;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.List;

import static bomberman.BombermanGame.getStillEntityAt;

public class Enemies extends AnimatedEntity {
    protected int preX = 0, preY = 0;

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
