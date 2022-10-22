package bomberman.entities;

import bomberman.BombermanGame;
import bomberman.Collision;
import bomberman.entities.buff.Buff;
import bomberman.entities.tile.Portal;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Wall;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import bomberman.MapTiles;
import bomberman.inPut.handleInput;
import bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static bomberman.BombermanGame.getStillEntityAt;


public class Bomber extends AnimatedEntity {
    protected int direct = -1;

    protected boolean moving = false;

    public int bombNow = 1;
    public int timerIntervalBomb = 0;

    public int bombPower = 1;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        x += moveX;
        y += moveY;
        if (timerIntervalBomb < -2500) timerIntervalBomb = 0;
        else timerIntervalBomb--;
    }

    private void chooseSprite() {
        if (removed) {
            sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 20);
        }
        else switch (direct) {
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
                if (canMove(x - entitySpeed, y)) {
                    moveX += -entitySpeed;
                    direct = 3;
                } else {
                    double topLeftX = (double) x - entitySpeed + 2;
                    double topLeftY = (double) y + 2;
                    Entity topL = getStillEntityAt(topLeftX, topLeftY);
                    if (topL instanceof Wall || topL instanceof Brick) {
                        moveY += entitySpeed;
                        direct = 2;
                    }

                    double botLeftX = (double) x - entitySpeed + 2;
                    double botLeftY = (double) y + (double) Sprite.SCALED_SIZE - 2;
                    Entity botL = getStillEntityAt(botLeftX, botLeftY);
                    if (botL instanceof Wall || botL instanceof Brick) {
                        moveY += -entitySpeed;
                        direct = 0;
                    }

                }
            }
            else if (dir.right) {
                moving = true;
                if (canMove(x + entitySpeed, y)) {
                        moveX += entitySpeed;
                        direct = 1;
                } else {
                    double topRightX = (double) x + entitySpeed + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                    double topRightY = (double) y + 2;
                    Entity topR = getStillEntityAt(topRightX, topRightY);
                    if (topR instanceof Wall || topR instanceof Brick) {
                        moveY += entitySpeed;
                        direct = 2;
                    }

                    double botRightX = (double) x + entitySpeed + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                    double botRightY = (double) y + (double) Sprite.SCALED_SIZE - 2;
                    Entity botR = getStillEntityAt(botRightX, botRightY);
                    if (botR instanceof  Wall || botR instanceof Brick) {
                        moveY += -entitySpeed;
                        direct = 0;
                    }
                }
            }
            else if (dir.up) {
                moving = true;
                if (canMove(x, y - entitySpeed)) {
                        moveY += -entitySpeed;
                        direct = 0;
                } else {
                    double topLeftX = (double) x + 2;
                    double topLeftY = (double) y - entitySpeed + 2;
                    Entity topL = getStillEntityAt(topLeftX, topLeftY);
                    if (topL instanceof Wall || topL instanceof Brick) {
                        moveX += entitySpeed;
                        direct = 1;
                    }

                    double topRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                    double topRightY = (double) y - entitySpeed + 2;
                    Entity topR = getStillEntityAt(topRightX, topRightY);
                    if (topR instanceof Wall || topR instanceof Brick) {
                        moveX += -entitySpeed;
                        direct = 3;
                    }

                }
            }
            else if (dir.down) {
                moving = true;
                if (canMove(x, y + entitySpeed)) {
                        moveY += entitySpeed;
                        direct = 2;
                } else {
                    double botLeftX = (double) x + 2;
                    double botLeftY = (double) y + entitySpeed + (double) Sprite.SCALED_SIZE - 2;
                    Entity botL = getStillEntityAt(botLeftX, botLeftY);
                    if (botL instanceof Wall || botL instanceof Brick) {
                        moveX += entitySpeed;
                        direct = 1;
                    }
                    double botRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                    double botRightY = (double) y + entitySpeed + (double) Sprite.SCALED_SIZE - 2;
                    Entity botR = getStillEntityAt(botRightX, botRightY);
                    if (botR instanceof  Wall || botR instanceof Brick) {
                        moveX += -entitySpeed;
                        direct = 3;
                    }
                }
            }
            else {
                moving = false;
            }
    }

    @Override
    public boolean collide(Entity e) {
        return Collision.checkCollision(this, e);
    }

    public void render(GraphicsContext graContext) {
        chooseSprite();
        this.img = sprite.getFxImage();
        graContext.drawImage(img, x, y);
    }
}
