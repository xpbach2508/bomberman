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
            /*int xTile = (int) this.getX() / Sprite.SCALED_SIZE;
            int yTile = (int) this.getY() / Sprite.SCALED_SIZE;
            if (xTile == (int) e.getX() / Sprite.SCALED_SIZE && yTile == (int) e.getY() / Sprite.SCALED_SIZE) {
                System.out.println("An duoc range");
                return true;
            }*/
            Collision check = new Collision();
            if (check.checkCollision(this, e)) {
                System.out.println(e.getX() + " " + e.getY() + "flame " + this.getX() + " " + this.getY());
                System.out.println("flame");
                return true;
            }
        }
        return false;
    }
}
