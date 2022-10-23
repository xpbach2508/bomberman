package bomberman.entities.Enemies;

import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.player;

public class Balloom extends Enemies {

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
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
        //animate();
        //x += moveX;
       // y += moveY;
       // move();
        //chooseSprite();
        //this.img = sprite.getFxImage();
        //collide(player);
    }

    private void chooseSprite() {
        if (removed) {
            if (timeAnimation <= 0) sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20);
            else {
                animate = 0;
                sprite = Sprite.balloom_dead;
            }
        }
        else switch (direct) {
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
            if (canMove(x + 8, y)) {
                moveX = 1;
                if (x - preX > 24) if (canMove(x, y - 4) || canMove(x, y + 4)) {
                    direct = random(new int[]{1, 0, 2});
                    setChangedCoordinates(x, y);
                }
            } else {
                direct = random(new int[]{0, 2, 3});
                setChangedCoordinates(x, y);
            }
        }
        if (direct == 2) {
            if (canMove(x, y + 4)) {
                moveY = 1;
                if (y - preY > 24) if (canMove(x + 8, y) || canMove(x - 8, y)) {
                    direct = random(new int[]{2, 1, 3});
                    setChangedCoordinates(x, y);
                }
            } else {
                direct = random(new int[]{0, 1, 3});
                setChangedCoordinates(x, y);
            }
        }
        if (direct == 3) {
            if (canMove(x - 4, y)) {
                moveX = -1;
                if (preX - x > 24) if (canMove(x+10, y - 4) || canMove(x+10, y + 4)) {
                    direct = random(new int[]{3, 0, 2});
                    setChangedCoordinates(x, y);
                }
            } else {
                direct = random(new int[]{0, 1, 2});
                setChangedCoordinates(x, y);
            }
        }
        if (direct == 0) {
            if (canMove(x, y - 4)) {
                moveY = -1;
                if (preY - y > 24) if (canMove(x + 8, y) || canMove(x - 8, y)) {
                    direct = random(new int[]{0, 1, 3});
                    setChangedCoordinates(x, y);
                }
            } else {
                direct = random(new int[]{1, 2, 3});
                setChangedCoordinates(x, y);
            }
        }
    }
}
