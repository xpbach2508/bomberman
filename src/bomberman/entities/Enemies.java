package bomberman.entities;

import javafx.scene.image.Image;

public class Enemies extends Entity {

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
