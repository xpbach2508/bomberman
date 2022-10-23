package bomberman.entities.tile;

import bomberman.entities.WrapperBox;
import javafx.scene.image.Image;

public class Portal extends WrapperBox {

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {}
}
