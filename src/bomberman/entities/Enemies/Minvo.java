package bomberman.entities.Enemies;

import bomberman.entities.Bomber;
import bomberman.entities.Enemies.AI.EnemyAI;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static bomberman.Board.player;


public class Minvo extends Enemies {

    EnemyAI ai = new EnemyAI(player, this);
    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        entitySpeed = 2;
        goThrough.add('f');
        goThrough.add('*');
        goThrough.add('d');
    }

    @Override
    protected void chooseSprite() {
            if (removed) {
                if (timeAnimation <= 30) {
                    sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20);
                }
                else {
                    animate = 0;
                    sprite = Sprite.minvo_dead;
                    player.score += 3;
                }
            }
            else switch (direct) {
                case 0 -> sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_left2, Sprite.minvo_right2, animate, 40);
                case 2 -> sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_right2, Sprite.minvo_left2, animate, 40);
                case 3 -> sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate, 40);
                default -> sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate, 40);
            }
    }

    @Override
    public void setDirection(Bomber player) {
        if (distanceObject(this.getX(), this.getY(), player) < 150) {
            entitySpeed = 1;
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
                entitySpeed = 2;
                super.setDirection(player);
            }
        }
        else {
            entitySpeed = 2;
            super.setDirection(player);
        }
    }
}
