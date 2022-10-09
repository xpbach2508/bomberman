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
            /*int xTile = (int) this.getX() / Sprite.SCALED_SIZE;
            int yTile = (int) this.getY() / Sprite.SCALED_SIZE;
            if (xTile == (int) e.getX() / Sprite.SCALED_SIZE && yTile == (int) e.getY() / Sprite.SCALED_SIZE) {
                ((Bomber) e).setBomberSpeed();
                System.out.println("An duoc speed");
                return true;
            }*/
            Collision check = new Collision();
            if (check.checkCollision(this, e)) {
                ((Bomber) e).setBomberSpeed();
                System.out.println("speed");
                return true;
            }
        }
        return false;
    }
}
