package bomberman.entities;

import bomberman.Collision;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Wall;
import javafx.scene.image.Image;
import bomberman.inPut.handleInput;
import bomberman.graphics.Sprite;

import static bomberman.BombermanGame.getStillEntityAt;


public class Bomber extends AnimatedEntity {

    public int moveX, moveY;

    protected int direct = -1;

    public handleInput dir;

    protected boolean moving = false;

    protected int bomberSpeed = 1;
    public int bomberNow = 1;
    public int timerIntervalBomb = 0;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        x += moveX;
        y += moveY;
        chooseSprite();
        this.img = sprite.getFxImage();
        if (timerIntervalBomb < -2500) timerIntervalBomb = 0;
        else timerIntervalBomb--;
    }

    private void chooseSprite() {
        switch (direct) {
            case 0 -> {
                sprite = Sprite.player_up;
                if (moving) sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
            }
            case 2 -> {
                sprite = Sprite.player_down;
                if (moving) sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
            }
            case 3 -> {
                sprite = Sprite.player_left;
                if (moving) sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
            }
            default -> {
                sprite = Sprite.player_right;
                if (moving) sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
            }
        }
    }

    public void move(handleInput dir) {
        moveX = 0;
        moveY = 0;
            if (dir.left) {
                moving = true;
                if (canMove(x - bomberSpeed, y)) {
                    moveX += -bomberSpeed;
                    direct = 3;
                } else {
                    double topLeftX = (double) x - bomberSpeed + 2;
                    double topLeftY = (double) y + 2;
                    Entity topL = getStillEntityAt(topLeftX, topLeftY);
                    if (topL instanceof Wall || topL instanceof Brick) {
                        moveY += bomberSpeed;
                        direct = 2;
                    }

                    double botLeftX = (double) x - bomberSpeed + 2;
                    double botLeftY = (double) y + (double) Sprite.SCALED_SIZE - 2;
                    Entity botL = getStillEntityAt(botLeftX, botLeftY);
                    if (botL instanceof Wall || botL instanceof Brick) {
                        moveY += -bomberSpeed;
                        direct = 0;
                    }
                }
                if (moveX == 0) direct = 3;
            }
            else if (dir.right) {
                moving = true;
                if (canMove(x + bomberSpeed, y)) {
                        moveX += bomberSpeed;
                        direct = 1;
                } else {
                    double topRightX = (double) x + bomberSpeed + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                    double topRightY = (double) y + 2;
                    Entity topR = getStillEntityAt(topRightX, topRightY);
                    if (topR instanceof Wall || topR instanceof Brick) {
                        moveY += bomberSpeed;
                        direct = 2;
                    }

                    double botRightX = (double) x + bomberSpeed + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                    double botRightY = (double) y + (double) Sprite.SCALED_SIZE - 2;
                    Entity botR = getStillEntityAt(botRightX, botRightY);
                    if (botR instanceof  Wall || botR instanceof Brick) {
                        moveY += -bomberSpeed;
                        direct = 0;
                    }
                }
                if (moveX == 0) direct = 1;
            }
            else if (dir.up) {
                moving = true;
                if (canMove(x, y - bomberSpeed)) {
                        moveY += -bomberSpeed;
                        direct = 0;
                } else {
                    double topLeftX = (double) x + 2;
                    double topLeftY = (double) y - bomberSpeed + 2;
                    Entity topL = getStillEntityAt(topLeftX, topLeftY);
                    if (topL instanceof Wall || topL instanceof Brick) {
                        moveX += bomberSpeed;
                        direct = 1;
                    }

                    double topRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                    double topRightY = (double) y - bomberSpeed + 2;
                    Entity topR = getStillEntityAt(topRightX, topRightY);
                    if (topR instanceof Wall || topR instanceof Brick) {
                        moveX += -bomberSpeed;
                        direct = 3;
                    }
                }
                if (moveY == 0) direct = 0;
            }
            else if (dir.down) {
                moving = true;
                if (canMove(x, y + bomberSpeed)) {
                        moveY += bomberSpeed;
                        direct = 2;
                } else {
                    double botLeftX = (double) x + 2;
                    double botLeftY = (double) y + bomberSpeed + (double) Sprite.SCALED_SIZE - 2;
                    Entity botL = getStillEntityAt(botLeftX, botLeftY);
                    if (botL instanceof Wall || botL instanceof Brick) {
                        moveX += bomberSpeed;
                        direct = 1;
                    }
                    double botRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                    double botRightY = (double) y + bomberSpeed + (double) Sprite.SCALED_SIZE - 2;
                    Entity botR = getStillEntityAt(botRightX, botRightY);
                    if (botR instanceof  Wall || botR instanceof Brick) {
                        moveX += -bomberSpeed;
                        direct = 3;
                    }
                }
                if (moveY == 0) direct = 2;
            }
            else {
                moving = false;
            }
    }

    @Override
    public boolean collide(Entity e) {
        Collision check = new Collision();
        return check.checkCollision(this, e);
    }

    public void setBomberSpeed() {
        this.bomberSpeed = this.bomberSpeed + 1;
    }
}
