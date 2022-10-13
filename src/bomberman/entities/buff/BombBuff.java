package bomberman.entities.buff;

import bomberman.Collision;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

//import bomberman.Collision.checkCollision;

public class BombBuff extends Buff {
    public BombBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            /*int xTile = (int) this.getX() / Sprite.SCALED_SIZE;
            int yTile = (int) this.getY() / Sprite.SCALED_SIZE;
            if (xTile == (int) e.getX() / Sprite.SCALED_SIZE && yTile == (int) e.getY() / Sprite.SCALED_SIZE) {
                System.out.println("An duoc bomb");
                return true;
            }*/
            if (Collision.checkCollision(this, e)) {
                System.out.println("bomb");
                return true;
            }
        }
        return false;
    }
}
