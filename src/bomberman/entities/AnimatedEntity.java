package bomberman.entities;

public abstract class AnimatedEntity extends Entity {
    protected int animate = 0;
    protected final int MAX_ANIMATE = 10000;

    protected void animate() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }

}
