package bomberman.entities.buff;

import bomberman.Collision;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

//import static bomberman.Collision.checkCollision;

public class SpeedBuff extends Buff {
    public SpeedBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            if (Collision.checkCollision(this, e)) {
                ((Bomber) e).setEntitySpeed();
                return true;
            }
        }
        return false;
    }
}
