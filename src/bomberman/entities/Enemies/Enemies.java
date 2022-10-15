package bomberman.entities.Enemies;

import bomberman.entities.AnimatedEntity;
import bomberman.entities.Entity;
import javafx.scene.image.Image;

public class Enemies extends AnimatedEntity {

    public int direction;

    public Enemies(int x, int y, Image img) {
        super(x,y,img);
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void update() {
    }
}
