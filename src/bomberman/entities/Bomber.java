package bomberman.entities;

import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Portal;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import bomberman.graphics.TaskBar;
import bomberman.inPut.handleInput;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.*;
import static bomberman.graphics.MapTiles.tileMap;
import static bomberman.graphics.Menu.*;

public class Bomber extends AnimatedEntity {
    public int life = 3;
    public int score = 0;
    public int direct = -1;
    public boolean moving = false;
    public int bombNow = 1;

    public boolean detonatorOn = false;
    public int timerIntervalBomb = 0;
    public int bombPower = 1;
    public int level = 1;
    public int timerDead = 100;
    public int timeLeft = 200;
    public int timeImmortal = 4;
    public boolean immortal = true;
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
        if (timeImmortal > 0) timeImmortal--;
        else immortal = false;
        checkWin();
        TaskBar.update();
    }

    private void chooseSprite() {
        if (moveX != 0)
            music.playMoveX();
        else if (moveY != 0)
            music.playMoveY();
        else music.stopMove();

        if (removed || timeLeft == 0) {
            if (timeAnimation == 70) music.playDead();
            if (startDead && timeAnimation > 0) {
                sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 70);
                timeAnimation--;
            }
            if (timeAnimation == 0) {
                sprite = Sprite.player_dead;
                startDead = false;
                timerDead--;
                if (timerDead == 0) {
                    music.stopDead();
                    if (life > 0) {
                        life--;
                        reset();
                    } else {
                        level = 1;
                        removed = false;
                        checkEnd("Game Over");
                    }
                }
            }
        } else {
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
            if (immortal) sprite = Sprite.movingSprite(sprite, Sprite.player_dead, animate, 40);
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
                double topLeftX = (double) x - entitySpeed + 1;
                double topLeftY = (double) y + 1;
                Entity topL = getStillEntityAt(topLeftX, topLeftY);
                if (topL instanceof Wall || topL instanceof Brick) {
                    moveY += entitySpeed;
                    direct = 2;
                }

                double botLeftX = (double) x - entitySpeed + 1;
                double botLeftY = (double) y + (double) Sprite.SCALED_SIZE - 1;
                Entity botL = getStillEntityAt(botLeftX, botLeftY);
                if (botL instanceof Wall || botL instanceof Brick) {
                    moveY -= entitySpeed;
                    direct = 0;
                }
            }
            if (moveX == 0) direct = 3;
        } else if (dir.right) {
            moving = true;
            if (canMove(x + entitySpeed, y)) {
                moveX += entitySpeed;
                direct = 1;
            } else {
                double topRightX = (double) x + entitySpeed + (double) Sprite.SCALED_SIZE * 3 / 4 - 1;
                double topRightY = (double) y + 1;
                Entity topR = getStillEntityAt(topRightX, topRightY);
                if (topR instanceof Wall || topR instanceof Brick) {
                    moveY += entitySpeed;
                    direct = 2;
                }

                double botRightX = (double) x + entitySpeed + (double) Sprite.SCALED_SIZE * 3 / 4 - 1;
                double botRightY = (double) y + (double) Sprite.SCALED_SIZE - 1;
                Entity botR = getStillEntityAt(botRightX, botRightY);
                if (botR instanceof Wall || botR instanceof Brick) {
                    moveY -= entitySpeed;
                    direct = 0;
                }
            }
            if (moveX == 0) direct = 1;
        } else if (dir.up) {
            moving = true;
            if (canMove(x, y - entitySpeed)) {
                moveY -= entitySpeed;
                direct = 0;
            } else {
                double topLeftX = (double) x + 1;
                double topLeftY = (double) y - entitySpeed + 1;
                Entity topL = getStillEntityAt(topLeftX, topLeftY);
                if (topL instanceof Wall || topL instanceof Brick) {
                    moveX += entitySpeed;
                    direct = 1;
                }

                double topRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 1;
                double topRightY = (double) y - entitySpeed + 1;
                Entity topR = getStillEntityAt(topRightX, topRightY);
                if (topR instanceof Wall || topR instanceof Brick) {
                    moveX -= entitySpeed;
                    direct = 3;
                }
            }
            if (moveY == 0) direct = 0;
        } else if (dir.down) {
            moving = true;
            if (canMove(x, y + entitySpeed)) {
                moveY += entitySpeed;
                direct = 2;
            } else {
                double botLeftX = (double) x + 1;
                double botLeftY = (double) y + entitySpeed + (double) Sprite.SCALED_SIZE - 1;
                Entity botL = getStillEntityAt(botLeftX, botLeftY);
                if (botL instanceof Wall || botL instanceof Brick) {
                    moveX += entitySpeed;
                    direct = 1;
                }
                double botRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 1;
                double botRightY = (double) y + entitySpeed + (double) Sprite.SCALED_SIZE - 1;
                Entity botR = getStillEntityAt(botRightX, botRightY);
                if (botR instanceof Wall || botR instanceof Brick) {
                    moveX -= entitySpeed;
                    direct = 3;
                }
            }
            if (moveY == 0) direct = 2;
        } else {
            moving = false;
        }
    }

    public void removeBombs() {
        for (int i = 0; i < bombList.size(); i++) {
            Bomb b = bombList.get(i);
            if (bombList.get(i).removed) {
                tileMap[b.getTileX()][b.getTileY()] = ' ';
                b.setMapChar(' ');
                bombList.remove(i);
                bombNow++;
                i--;
            }
        }
    }

    public void putBomb() {
        if (timerIntervalBomb < 0 && bombNow > 0) {
            int xTileMore = 0;
            int yTileMore = 0;
            if (!canPutBomb()) switch (direct) {
                case 0 -> yTileMore = 1;
                case 2 -> yTileMore = -1;
                case 3 -> xTileMore = 1;
                default -> xTileMore = -1;
            }
            Bomb b = new Bomb((getX() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE + xTileMore,
                    (getY() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE + yTileMore, Sprite.bomb.getFxImage(), bombPower, detonatorOn);
            tileMap[b.getTileX()][b.getTileY()] = 'd';

            bombList.add(b);
            music.playPlantBomb();
            timerIntervalBomb = 30;
            bombNow--;
        }
    }

    public boolean canPutBomb() {
        Entity e = getMovingEntityAt((this.getX() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                ((this.getY() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE));
        return e == null;
    }
    public void bombDetonation() {
        for (Bomb b : bombList) {
            b.explodedBomb = true;
        }
    }

    public void checkWin() {
        for (Entity e : stillObjects) {
            if (e instanceof Portal &&
                    (x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE == e.x / Sprite.SCALED_SIZE &&
                    (y + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE == e.y / Sprite.SCALED_SIZE &&
                    !(getStillEntityAt(e.getX(), e.getY()) instanceof Brick) /*&&
                    numberEnemies == 0*/) {
                level++;
                music.playPortal();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException I) {
                    I.printStackTrace();
                }
                if (level < 5) {
                    loadObject("Stage " + level);
                    loadLevel("Stage " + level);
                } else {
                    level = 1;
                    reset();
                    checkEnd("You Win");
                }
                break;
            }
        }
    }

    public void reset() {
        x = 32;
        y = 32;
        bombNow = 1;
        bombPower = 1;
        entitySpeed = 1;
        timerDead = 100;
        timeAnimation = 70;
        startDead = true;
        removed = false;
        timeImmortal = 400;
        immortal = true;
        detonatorOn = false;
    }

    @Override
    public void render(GraphicsContext graContext) {
        graContext.drawImage(img, x, y);
    }
}
