package bomberman.entities.Enemies;

import bomberman.gameInteraction.Collision;
import bomberman.entities.AnimatedEntity;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.numberEnemies;

public class Enemies extends AnimatedEntity {
    protected int preX = 0, preY = 0;

    public Enemies(int x, int y, Image img) {
        super(x,y,img);
    }

    public void setChangedCoordinates(int x, int y) {
        this.preX = x;
        this.preY = y;
    }

    protected void chooseSprite(){
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber && !this.removed) {
            if (Collision.checkCollision(this, e)) {
                e.removed = true;
                numberEnemies--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if (removed) {
            animate();
            chooseSprite();
            this.img = sprite.getFxImage();
            if (timeAnimation > 0) timeAnimation--;
        } else {
            animate();
            x += moveX;
            y += moveY;
            move();
            chooseSprite();
            this.img = sprite.getFxImage();
        }
    }
    protected void move() {
    }

}
