package bomberman.entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;
    protected final int MAX_ANIMATE = 10000;

    public AnimatedEntity(int x, int y, Image img) {
        super(x, y, img);
    }

    protected void animate() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }

}
