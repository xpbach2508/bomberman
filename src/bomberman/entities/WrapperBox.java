package bomberman.entities;

import javafx.scene.image.Image;

public abstract class WrapperBox extends Entity {
    public static final int TILE_SIZE = 16;
    public WrapperBox(int x, int y) {
        super(x, y);
    }

    public WrapperBox(int x, int y, Image img) {
        super(x, y ,img);
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void update() {
    }


}
