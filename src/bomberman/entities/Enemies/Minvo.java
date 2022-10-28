package bomberman.entities.Enemies;

import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static bomberman.BombermanGame.player;


public class Minvo extends Enemies {

    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        entitySpeed = 2;
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
    public void setDirection(Bomber player, List<Entity> stillObj) {
        ArrayList<Integer> directionList = new ArrayList<>();
        int directionNow = direct;
        boolean canMoveRight = canMove(x + entitySpeed + Sprite.SCALED_SIZE / 4 + 3, y);
        boolean canMoveLeft = canMove(x - entitySpeed - 3, y);
        boolean canMoveDown = canMove(x, y + entitySpeed + 1);
        boolean canMoveUp = canMove(x, y - entitySpeed - 3);
        if (canMoveUp) directionList.add(0);
        if (canMoveRight) directionList.add(1);
        if (canMoveDown) directionList.add(2);
        if (canMoveLeft) directionList.add(3);
        if (directionList.size() == 0) {
            direct = 4;
            return;
        }
        if (directionList.size() == 2) {
            if (directionNow == 0 && directionList.contains(2) && canMoveUp
                    || directionNow == 2 && directionList.contains(0) && canMoveDown
                    || directionNow == 1 && directionList.contains(3) && canMoveRight
                    || directionNow == 3 && directionList.contains(1) && canMoveLeft) {
                direct = directionNow;
            } else {
                if (directionNow == directionList.get(0)) {
                    direct = directionList.get(1);
                } else {
                    direct = directionList.get(0);
                }
            }
        } else if (directionList.size() == 1) {
            direct = directionList.get(0);
        } else {
            if (directionNow == 0) {
                directionList.remove((Integer) 2);
                //if (!canMoveUp) directionList.remove((Integer) 0);
            } else if (directionNow == 1) {
                directionList.remove((Integer) 3);
                //if (!canMoveRight) directionList.remove((Integer) 1);
            } else if (directionNow == 2) {
                directionList.remove((Integer) 0);
                //if (!canMoveDown) directionList.remove((Integer) 2);
            } else if (directionNow == 3) {
                directionList.remove((Integer) 1);
                //if (!canMoveRight) directionList.remove((Integer) 3);
            }
            double min = 10000;
            ArrayList<Double> bestDir = new ArrayList<>();

            System.out.println(directionList.size());
            for (int i = 0; i < directionList.size(); i++) {
                System.out.print(directionList.get(i) + " ");
            }
            System.out.println("");
            for (int i = 0; i < directionList.size(); i++) {
                int dir = directionList.get(i);
                double distance = 0;
                if (dir == 1) {
                    distance = distanceObject(x + this.entitySpeed, y, player);
                }
                if (dir == 2) {
                    distance = distanceObject(x, y + this.entitySpeed, player);
                }
                if (dir == 3) {
                    distance = distanceObject(x - this.entitySpeed, y, player);
                }
                if (dir == 0) {
                    distance = distanceObject(x, y - this.entitySpeed, player);
                }
                min = Math.min(min, distance);
                bestDir.add(distance);
            }
            for (int i = 0; i < bestDir.size(); i++) {
                if (bestDir.get(i) == min) {
                    direct = directionList.get(i);
                    break;
                }
            }
        }
        directionList.clear();
    }
}
