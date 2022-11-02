package bomberman.entities.Enemies;

import bomberman.entities.Bomb;
import bomberman.entities.Bomber;
import bomberman.entities.Enemies.AI.EnemyAI;
import bomberman.entities.Entity;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static bomberman.Board.getStillEntityAt;
import static bomberman.Board.player;

public class Kondoria extends Enemies {
    EnemyAI ai = new EnemyAI(player, this);
    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        entitySpeed = 1;
        goThrough.add('d');
        goThrough.add('f');
    }

    protected void chooseSprite() {
        if (removed) {
            if (timeAnimation <= 30) {
                sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20);
            }
            else {
                animate = 0;
                sprite = Sprite.kondoria_dead;
                player.score += 2;
            }
        }
        else switch (direct) {
            case 0 -> sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_left2, Sprite.kondoria_right2, animate, 40);
            case 2 -> sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_right2, Sprite.kondoria_left2, animate, 40);
            case 3 -> sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, animate, 40);
            default -> sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, animate, 40);
        }
    }

    @Override
    public boolean canMove(int x, int y) {
        double topLeftX = (double) x + 1;
        double topLeftY = (double) y + 1;
        double topRightX = (double) x + (double) Sprite.SCALED_SIZE - 1;
        double topRightY = (double) y + 1;
        double botLeftX = (double) x + 1;
        double botLeftY = (double) y + (double) Sprite.SCALED_SIZE - 1;
        double botRightX = (double) x + (double) Sprite.SCALED_SIZE - 1;
        double botRightY = (double) y + (double) Sprite.SCALED_SIZE - 1;
        Entity topL = getStillEntityAt(topLeftX, topLeftY);
        Entity topR = getStillEntityAt(topRightX, topRightY);
        Entity botL = getStillEntityAt(botLeftX, botLeftY);
        Entity botR = getStillEntityAt(botRightX, botRightY);
        if (topL instanceof Wall || topL instanceof Bomb) {
            return false;
        }
        if (topR instanceof Wall || topR instanceof Bomb) {
            return false;
        }
        if (botL instanceof Wall || botL instanceof Bomb) {
            return false;
        }
        if (botR instanceof Wall || botR instanceof Bomb) {
            return false;
        }
        return true;
    }
    @Override
    public void setDirection(Bomber player) {
        int goalCol = (player.getTileX());
        int goalRow = player.getTileY();
        int startCol = this.getTileX();
        int startRow = this.getTileY();
        ai.setNodes(startCol, startRow, goalCol, goalRow);
        if (ai.search()) {
            int nextX = ai.pathList.get(0).col ;
            int nextY = ai.pathList.get(0).row ;
            int xTile = (x + 1) / Sprite.SCALED_SIZE;
            int yTile = (y + 1) / Sprite.SCALED_SIZE;
            int enRightX = (x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int enBotY = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            if (enRightX == xTile && enBotY == yTile) {
                if (nextX > xTile) direct = 1;
                if (nextX < xTile) direct = 3;
                if (nextY > yTile) direct = 2;
                if (nextY < yTile) direct = 0;
            }
        }
        else {
            super.setDirection(player);
        }
    }
}
