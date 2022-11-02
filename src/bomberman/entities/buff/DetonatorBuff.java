package bomberman.entities.buff;


import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.entities.tile.Brick;
import bomberman.gameInteraction.Collision;
import javafx.scene.image.Image;

import static bomberman.Board.getStillEntityAt;
import static bomberman.Board.music;

public class DetonatorBuff extends Buff {
    public DetonatorBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public boolean collide(Entity e) {
        if (getStillEntityAt(x, y) instanceof Brick) return false;
        if (e instanceof Bomber) {
            if (Collision.checkCollision(this, e)) {
                music.playBuff();
                ((Bomber) e).detonatorOn = true;
                return true;
            }
        }
        return false;
    }
}
