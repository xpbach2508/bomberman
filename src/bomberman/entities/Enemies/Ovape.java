package bomberman.entities.Enemies;

import bomberman.entities.Bomb;
import bomberman.entities.Bomber;
import bomberman.entities.Enemies.AI.EnemyAI;
import bomberman.entities.Entity;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

import static bomberman.BombermanGame.player;

public class Ovape extends Enemies {
    EnemyAI ai = new EnemyAI(player, this);
    public Ovape(int x, int y, Image img) {
        super(x, y, img);
        entitySpeed = 1;
    }

    protected void chooseSprite() {
        if (removed) {
            if (timeAnimation <= 30) {
                sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 20);
            }
            else {
                animate = 0;
                sprite = Sprite.ovape_dead;
                player.score += 2;
            }
        }
        else {
            if (distanceObject(this.x, this.y, player) < 220) switch (direct) {
                case 0 -> sprite = Sprite.movingSprite(Sprite.ovape_right1, Sprite.ovape_left2, Sprite.ovape_right2, animate, 40);
                case 2 -> sprite = Sprite.movingSprite(Sprite.ovape_left1, Sprite.ovape_right2, Sprite.ovape_left2, animate, 40);
                case 3 -> sprite = Sprite.movingSprite(Sprite.ovape_left1, Sprite.ovape_left2, Sprite.ovape_left3, animate, 40);
                default -> sprite = Sprite.movingSprite(Sprite.ovape_right1, Sprite.ovape_right2, Sprite.ovape_right3, animate, 40);
            }
            else {
                sprite = Sprite.ovape_invi;
            }
        }
    }

    @Override
    public void setDirection(Bomber player) {
        Random newRandom = new Random();
        if (newRandom.nextInt() % 100 == 5) entitySpeed = 0;
        else entitySpeed = 1;
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
