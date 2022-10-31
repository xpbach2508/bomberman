package bomberman.entities.Enemies;

import bomberman.entities.Bomber;
import bomberman.entities.Enemies.AI.EnemyAI;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

import static bomberman.BombermanGame.player;

public class Oneal extends Enemies {

    EnemyAI ai = new EnemyAI(player, this);
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        this.goThrough.add('*');
    }

    protected void chooseSprite() {
        if (removed) {
            if (timeAnimation <= 30) {
                sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20);
            }
            else {
                animate = 0;
                sprite = Sprite.oneal_dead;
                player.score += 2;
            }
        }
        else switch (direct) {
            case 0 -> sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_left2, Sprite.oneal_right2, animate, 40);
            case 2 -> sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_right2, Sprite.oneal_left2, animate, 40);
            case 3 -> sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 40);
            default -> sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 40);
        }
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
            boolean canMoveRight = canMove(x + 4, y);
            boolean canMoveLeft = canMove(x - 4, y);
            boolean canMoveDown = canMove(x, y + 4);
            boolean canMoveUp = canMove(x, y - 4);
            Random newRandom = new Random();
            if (direct == 1) {
                if (canMoveUp && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                    direct = 0;
                    timeChangeDir = 5;
                }
                else if (canMoveDown && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                    direct = 2;
                    timeChangeDir = 5;
                }
                else if (!canMoveRight) {
                    direct = 3;
                    timeChangeDir--;
                }
            }
            else if (direct == 2) {
                if (canMoveLeft && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                    direct = 3;
                    timeChangeDir = 5;
                }
                else if (canMoveRight && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                    direct = 1;
                    timeChangeDir = 5;
                }
                else if (!canMoveDown) {
                    direct = 0;
                    timeChangeDir--;
                }
            }
            else if (direct == 3) {
                if (canMoveUp && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                    direct = 0;
                    timeChangeDir = 5;
                }
                else if (canMoveDown && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                    direct = 2;
                    timeChangeDir = 5;
                }
                else if (!canMoveLeft) {
                    direct = 1;
                    timeChangeDir--;
                }
            }
            else if (direct == 0) {
                if (canMoveLeft && newRandom.nextInt() % 100 == 2 && timeChangeDir <= 0) {
                    direct = 3;
                    timeChangeDir = 5;
                }
                else if (canMoveRight && newRandom.nextInt() % 100 == 1 && timeChangeDir <= 0) {
                    direct = 1;
                    timeChangeDir = 5;
                }
                else if (!canMoveUp) {
                    direct = 2;
                    timeChangeDir--;
                }
            }
        }
    }
}
