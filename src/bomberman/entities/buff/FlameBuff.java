package bomberman.entities.buff;

import bomberman.Collision;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

//import static bomberman.Collision.checkCollision;

public class FlameBuff extends Buff {
    public FlameBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            if (Collision.checkCollision(this, e)) {
                return true;
            }
        }
        return false;
    }
}
