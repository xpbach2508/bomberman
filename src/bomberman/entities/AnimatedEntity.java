package bomberman.entities;

import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.List;

import static bomberman.BombermanGame.getStillEntityAt;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;

    protected final int MAX_ANIMATE = 10000;
    public int timeAnimation = 70;
    public int moveX, moveY;

    protected int direct = random(new int[]{0, 1, 2, 3});

    protected int entitySpeed = 1;

    protected int random(int[] arr) {
        return  arr[(int) ((Math.random() * (arr.length))+ 0)];
    }

    public AnimatedEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    public AnimatedEntity(int x, int y) {
        super(x,y);
    }

    protected void animate() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }

    public boolean canMove(int x, int y) {
        double topLeftX = (double) x + 1;
        double topLeftY = (double) y + 1;
        double topRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 1;
        double topRightY = (double) y + 1;
        double botLeftX = (double) x + 1;
        double botLeftY = (double) y + (double) Sprite.SCALED_SIZE - 1;
        double botRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 1;
        double botRightY = (double) y + (double) Sprite.SCALED_SIZE - 1;
        Entity topL = getStillEntityAt(topLeftX, topLeftY);
        Entity topR = getStillEntityAt(topRightX, topRightY);
        Entity botL = getStillEntityAt(botLeftX, botLeftY);
        Entity botR = getStillEntityAt(botRightX, botRightY);
        if (topL instanceof Wall || topL instanceof Brick || topL instanceof Bomb) {
            return false;
        }
        if (topR instanceof Wall || topR instanceof Brick || topR instanceof Bomb) {
            return false;
        }
        if (botL instanceof Wall || botL instanceof Brick || botL instanceof Bomb) {
            return false;
        }
        if (botR instanceof Wall || botR instanceof Brick || botR instanceof Bomb) {
            return false;
        }
        return true;
    }

    public void smootherMove() {
        if (direct == 0) {
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
        else if(direct == 1) {
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
        else if (direct == 2) {
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
        else {
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
    }

    public double distanceObject(int x, int y, Entity entity2) {
        return Math.sqrt(Math.pow(x - entity2.getX(), 2) + Math.pow(y - entity2.getY(), 2));
    }

    public int getEntitySpeed() {
        return entitySpeed;
    }

    public void setEntitySpeed(int x) {
        this.entitySpeed = this.entitySpeed + x;
    }


}
