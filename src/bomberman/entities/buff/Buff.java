package bomberman.entities.buff;

import bomberman.entities.WrapperBox;
import javafx.scene.image.Image;

public abstract class Buff extends WrapperBox {

    public Buff(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update(){};
}
