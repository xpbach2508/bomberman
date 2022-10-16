package bomberman.entities;

import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.getStillEntityAt;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;

    protected int pre = 0;
    protected final int MAX_ANIMATE = 10000;

    public int moveX, moveY;

    protected int direct = random(new int[]{0, 1, 2, 3});

    protected int random(int[] arr) {
        return  arr[(int) ((Math.random() * (arr.length - 0))+ 0)];
    }

    public AnimatedEntity(int x, int y, Image img) {
        super(x, y, img);
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
        if (topL instanceof Wall || topL instanceof Brick) {
//            System.out.println("topL");
            return false;
        }
        if (topR instanceof Wall || topR instanceof Brick) {
//            System.out.println("topR");
            return false;
        }
        if (botL instanceof Wall || botL instanceof Brick) {
//            System.out.println("botL");
            return false;
        }
        if (botR instanceof Wall || botR instanceof Brick) {
//            System.out.println("botR");
            return false;
        }
        return true;
    }

}
