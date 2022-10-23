package bomberman.entities;

import bomberman.BombermanGame;
import bomberman.gameInteraction.Collision;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Portal;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import bomberman.inPut.handleInput;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.getStillEntityAt;
import static bomberman.graphics.Menu.*;


public class Bomber extends AnimatedEntity {
    protected int direct = -1;
    protected boolean moving = false;
    public int bombNow = 1;
    public int timerIntervalBomb = 0;
    public int bombPower = 1;
    public int level = 1;
    public int timerDead = 100;
    boolean startDead = true;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        chooseSprite();
        this.img = sprite.getFxImage();
        x += moveX;
        y += moveY;
        if (timerIntervalBomb < -2500) timerIntervalBomb = 0;
        else timerIntervalBomb--;
        checkWin();
    }

    private void chooseSprite() {
        if (removed) {
            //if (startDead) sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, Sprite.player_dead, animate, 80);
            if (startDead && timeAnimation > 0) {
                sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 70);
                timeAnimation--;
            }
            if (timeAnimation == 0) {
                sprite = Sprite.player_dead;
                startDead = false;
                timerDead--;
                if (timerDead == 0) {
                    level = 1;
                    removed = false;
                    checkEnd("Game Over");
                }
            }
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
            if (!removed) if (dir.left) {
                moving = true;
                if (canMove(x - entitySpeed, y)) {
                    moveX -= entitySpeed;
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
                        moveY -= entitySpeed;
                        direct = 0;
                    }
                }
                if (moveX == 0) direct = 3;
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
                        moveY -= entitySpeed;
                        direct = 0;
                    }
                }
                if (moveX == 0) direct = 1;
            }
            else if (dir.up) {
                moving = true;
                if (canMove(x, y - entitySpeed)) {
                    moveY -= entitySpeed;
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
                        moveX -= entitySpeed;
                        direct = 3;
                    }
                }
                if (moveY == 0) direct = 0;
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
                        moveX -= entitySpeed;
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
        return Collision.checkCollision(this, e);
    }

    public void checkWin() {
        if (getStillEntityAt(x,y) instanceof Portal) {
            level++;
            if (level < 3) {
                loadObject("Stage " + level);
                loadLevel("Stage " + level);
            } else {
                level = 1;
                checkEnd("You Win");
            }
        }
    }

    public void reset() {
        x = 32;
        y = 32;
        bombNow = 1;
        bombPower = 1;
        entitySpeed = 1;
        removed = false;
        timerDead = 100;
        startDead = true;
        timeAnimation = 70;
    }

    @Override
    public void render(GraphicsContext graContext) {
        graContext.drawImage(img, x, y);
    }
}
