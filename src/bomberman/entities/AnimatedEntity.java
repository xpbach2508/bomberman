package bomberman.entities;

import bomberman.entities.Enemies.Enemies;
import bomberman.entities.buff.Buff;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Portal;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import bomberman.inPut.handleInput;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static bomberman.BombermanGame.getStillEntityAt;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;

    protected int pre = 0;
    protected final int MAX_ANIMATE = 10000;

    public int moveX, moveY;

    protected int direct = random(new int[]{0, 1, 2, 3});

    protected int entitySpeed = 2;

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
        double topLeftX = (double) x + 2;
        double topLeftY = (double) y + 2;
        double topRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
        double topRightY = (double) y + 2;
        double botLeftX = (double) x + 2;
        double botLeftY = (double) y + (double) Sprite.SCALED_SIZE - 2;
        double botRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
        double botRightY = (double) y + (double) Sprite.SCALED_SIZE - 2;
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

    public void setEntitySpeed() {
        this.entitySpeed = this.entitySpeed + 1;
    }

}
