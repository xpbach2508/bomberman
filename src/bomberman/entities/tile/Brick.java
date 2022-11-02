package bomberman.entities.tile;

import bomberman.entities.WrapperBox;
import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

public class Brick extends WrapperBox {

    protected int animate = 0;

    protected final int MAX_ANIMATE = 10000;
    public int timeRemoved = 30;
    public Brick(int x, int y, Image img) {
        super(x,y, img);
    }

    protected void animate() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }
    private void chooseSprite() {
        sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 30);
    }
    @Override
    public void update() {
        if (removed) {
            animate();
            chooseSprite();
            this.img = sprite.getFxImage();
            if (timeRemoved > 0) timeRemoved--;
        }
    }
}
