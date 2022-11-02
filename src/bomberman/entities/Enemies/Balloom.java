package bomberman.entities.Enemies;

import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static bomberman.Board.player;

public class Balloom extends Enemies {

    public Balloom(int x, int y, Image img) {
        super(x, y, img);
    }

    protected void chooseSprite() {
        if (removed) {
            if (timeAnimation <= 30) sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20);
            else {
                animate = 0;
                sprite = Sprite.balloom_dead;
                player.score += 1;
            }
        }
        else switch (direct) {
            case 0 -> sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_left2, Sprite.balloom_right3, animate, 40);
            case 2 -> sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_right2, Sprite.balloom_left3, animate, 40);
            case 3 -> sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 40);
            default -> sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 40);
        }
    }

    protected void move() {
        moveX = 0;
        moveY = 0;
        if (direct == 1) {
            if (canMove(x + 4, y)) {
                moveX = 1;
                if (x - preX > 24) if (canMove(x, y - 8) || canMove(x, y + 8)) {
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
                if (preX - x > 24) if (canMove(x, y - 8) || canMove(x, y + 8)) {
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