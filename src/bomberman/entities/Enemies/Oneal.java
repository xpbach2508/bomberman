package bomberman.entities.Enemies;

import bomberman.entities.Bomber;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import static bomberman.Board.player;

public class Oneal extends Enemies {

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        goThrough.add('*');
        entitySpeed = 2;
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
        super.setDirection(player);
    }
}
