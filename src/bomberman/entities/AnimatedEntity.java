package bomberman.entities;

import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.getStillEntityAt;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;
    protected final int MAX_ANIMATE = 10000;

    public AnimatedEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    protected void animate() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }
    public boolean canMove(int x, int y) {
        double topLeftX = (double) x + 2;
        double topLeftY = (double) y + 4;
        double topRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 1;
        double topRightY = (double) y + 4;
        double botLeftX = (double) x + 2;
        double botLeftY = (double) y + (double) Sprite.SCALED_SIZE * 15 / 16 - 1;
        double botRightX = (double) x + (double) Sprite.SCALED_SIZE * 3 / 4 - 1;
        double botRightY = (double) y + (double) Sprite.SCALED_SIZE * 15 / 16 - 1;
        /*System.out.println(x + " " + y);
        System.out.println(topLeftX + " " + topLeftY + " " + topRightX + " " + topRightY);
        System.out.println(botLeftX + " " + botLeftY + " " + botRightX + " " + botRightY);*/
        Entity topL = getStillEntityAt(topLeftX, topLeftY);
        Entity topR = getStillEntityAt(topRightX, topRightY);
        Entity botL = getStillEntityAt(botLeftX, botLeftY);
        Entity botR = getStillEntityAt(botRightX, botRightY);
        if (topL instanceof Wall) {
            System.out.println("topL");
            return false;
        }
        if (topR instanceof Wall) {
            System.out.println("topR");
            return false;
        }
        if (botL instanceof Wall) {
            System.out.println("botL");
            return false;
        }
        if (botR instanceof Wall) {
            System.out.println("botR");
            return false;
        }
        return true;
    }

}
