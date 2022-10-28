package bomberman.entities.Enemies;

import bomberman.entities.AnimatedEntity;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import javafx.scene.image.Image;

import java.util.List;

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
    public void setDirection(Bomber player, List<Entity> stillObj) {

    }

}
