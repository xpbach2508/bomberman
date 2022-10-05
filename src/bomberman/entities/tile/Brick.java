package bomberman.entities.tile;

import bomberman.entities.WrapperBox;
import javafx.scene.image.Image;

public class Brick extends WrapperBox {
    public Brick(int x, int y, Image img) {
        super(x,y, img);
    }

    @Override
    public void update() {
    }
}
