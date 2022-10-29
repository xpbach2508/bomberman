package bomberman.entities.buff;

import bomberman.entities.tile.Brick;
import bomberman.gameInteraction.Collision;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.getStillEntityAt;
import static bomberman.BombermanGame.music;

public class SpeedBuff extends Buff {
    public SpeedBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean collide(Entity e) {
        if (getStillEntityAt(x, y) instanceof Brick) return false;
        if (e instanceof Bomber) {
            if (Collision.checkCollision(this, e)) {
                music.playBuff();
                ((Bomber) e).setEntitySpeed(1);
                return true;
            }
        }
        return false;
    }
}
