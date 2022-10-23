package bomberman.entities.buff;

import bomberman.gameInteraction.Collision;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import javafx.scene.image.Image;

//import static bomberman.Collision.checkCollision;

public class FlameBuff extends Buff {
    public FlameBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            if (Collision.checkCollision(this, e)) {
                ((Bomber) e).bombPower++;
                return true;
            }
        }
        return false;
    }
}
