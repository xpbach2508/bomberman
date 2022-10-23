package bomberman.entities.buff;

import bomberman.entities.tile.Brick;
import bomberman.gameInteraction.Collision;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.getStillEntityAt;

//import static bomberman.Collision.checkCollision;

public class FlameBuff extends Buff {
    public FlameBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean collide(Entity e) {
        if (getStillEntityAt(x, y) instanceof Brick) return false;
        if (e instanceof Bomber) {
            if (Collision.checkCollision(this, e)) {
                ((Bomber) e).bombPower++;
                return true;
            }
        }
        return false;
    }
}
