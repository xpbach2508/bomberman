package bomberman.entities.Enemies;

import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Balloom extends Enemies {

    public Balloom(int x, int y, Image img) {
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
    }

    private void chooseSprite() {
        switch (direct) {
            case 0 -> {
                sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_left2, Sprite.balloom_right3, animate, 20);
            }
            case 2 -> {
                sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_right2, Sprite.balloom_left3, animate, 20);
            }
            case 3 -> {
                sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 20);
            }
            default -> {
                sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 20);
            }
        }
    }

    private void move() {
        moveX = 0;
        moveY = 0;
        if (direct == 1) {
            if (canMove(x + 2, y)) {
                moveX = 1;
                /*if (canMove(x, y+2)) {
                    direct = random(new int[]{1, 2});
                    if (direct == 1) moveX = 1;
                }*/
            } else
                direct = random(new int[]{0, 2, 3});
        }
        if (direct == 2) {
            if (canMove(x, y + 2)) {
                moveY = 1;
            } else
                direct = random(new int[]{0, 1, 3});
        }
        if (direct == 3) {
            if (canMove(x - 2, y)) {
                moveX = -1;
            } else
                direct = random(new int[]{0, 1, 2});
        }
        if (direct == 0) {
            if (canMove(x, y - 2)) {
                moveY = -1;
            } else
                direct = random(new int[]{1, 2, 3});
        }
    }

}
