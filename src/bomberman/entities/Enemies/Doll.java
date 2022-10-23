package bomberman.entities.Enemies;

import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.player;

public class Doll extends Enemies{

    public Doll(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        x += moveX;
        y += moveY;
        move();
        chooseSprite();
        this.img = sprite.getFxImage();
        collide(player);
    }

    private void chooseSprite() {
        if (direct == 0 || direct == 1) {
            sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 20);
        }
        if (direct == 2 || direct == 3) {
            sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 20);
        }
    }

    private void move() {
        moveX = 0;
        moveY = 0;
        if (direct == 1) {
            if (canMove(x + 8, y)) {
                moveX = 1;
            } else {
                direct = 2;
            }
        }
        if (direct == 2) {
            if (canMove(x, y + 4)) {
                moveY = 1;
            } else {
                direct = 3;
            }
        }
        if (direct == 3) {
            if (canMove(x - 4, y)) {
                moveX = -1;
            } else {
                direct = 0;
            }
        }
        if (direct == 0) {
            if (canMove(x, y - 4)) {
                moveY = -1;
            } else {
                direct = 1;
            }
        }
    }
}
