package bomberman.entities.Enemies;

import javafx.scene.image.Image;

public class Oneal extends Enemies{

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        this.x++;
    }
}
