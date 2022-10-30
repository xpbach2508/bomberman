package bomberman.entities.Enemies;

import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

import static bomberman.BombermanGame.player;

public class Doll extends Enemies{
    private double acceleration = 0;
    public Doll(int x, int y, Image img) {
        super(x, y, img);
    }

    protected void chooseSprite() {
        if (removed) {
            if (timeAnimation <= 30) sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20);
            else {
                animate = 0;
                sprite = Sprite.doll_dead;
                player.score += 1;
            }
        }
        else if (direct == 0 || direct == 1) {
            sprite = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 40);
        }
        else if (direct == 2 || direct == 3) {
            sprite = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 40);
        }
    }

    protected void move() {
        moveX = 0;
        moveY = 0;
        boolean canMoveRight = canMove(x + 4, y);
        boolean canMoveLeft = canMove(x - 4, y);
        boolean canMoveDown = canMove(x, y + 4);
        boolean canMoveUp = canMove(x, y - 4);
        Random newRandom = new Random();
        if (direct == 1) {
            if (canMoveUp && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                direct = 0;
                timeChangeDir = 5;
                acceleration = 0;
            }
            else if (canMoveDown && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                direct = 2;
                timeChangeDir = 5;
                acceleration = 0;
            }
            else if (canMoveRight) {
                moveX = 1;
                if (acceleration > 0) {
                    acceleration--;
                    moveX += 1;
                }
            } else {
                acceleration = 30;
                direct = 3;
                timeChangeDir--;
            }
        }
        else if (direct == 2) {
            if (canMoveLeft && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                direct = 3;
                timeChangeDir = 5;
                acceleration = 0;
            }
            else if (canMoveRight && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                direct = 1;
                timeChangeDir = 5;
                acceleration = 0;
            }
            else if (canMoveDown) {
                moveY = 1;
                if (acceleration > 0) {
                    acceleration--;
                    moveY += 1;
                }
            } else {
                acceleration = 30;
                direct = 0;
                timeChangeDir--;
            }
        }
        else if (direct == 3) {
            if (canMoveUp && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                direct = 0;
                timeChangeDir = 5;
                acceleration = 0;
            }
            else if (canMoveDown && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                direct = 2;
                timeChangeDir = 5;
                acceleration = 0;
            }
            else if (canMoveLeft) {
                moveX = -1;
                if (acceleration > 0) {
                    acceleration--;
                    moveX -= 1;
                }
            } else {
                acceleration = 30;
                direct = 1;
                timeChangeDir--;
            }
        }
        else if (direct == 0) {
            if (canMoveLeft && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                direct = 3;
                timeChangeDir = 5;
                acceleration = 0;
            }
            else if (canMoveRight && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                direct = 1;
                timeChangeDir = 5;
                acceleration = 0;
            }
            else if (canMoveUp) {
                moveY = -1;
                if (acceleration > 0) {
                    acceleration--;
                    moveY -= 1;
                }
            } else {
                acceleration = 30;
                direct = 2;
                timeChangeDir--;
            }
        }
    }


}
