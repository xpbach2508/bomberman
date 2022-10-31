package bomberman.entities;

import bomberman.gameInteraction.Collision;
import bomberman.graphics.Sprite;

public class Flame extends AnimatedEntity {
    protected boolean last;
    protected int direction;
    public Flame(int x, int y) {
        super(x, y);

    }
    public Flame(int x, int y, int direction, boolean last) {
        super(x, y);
        this.last = last;
        this.direction = direction;
        chooseSprite(60);
        this.img = sprite.getFxImage();
    }

    @Override
    public boolean collide(Entity e) {
        return Collision.checkCollision(this, e);
    }

    public void chooseSprite(int time) {
        switch (direction) {
            case 0:
                if (!this.last) sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate, time);
                else sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, animate, time);
                break;
            case 1:
                if (!this.last) sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate, time);
                else sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, animate, time);
                break;
            case 2:
                if (!this.last) sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate, time);
                else sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, animate, time);
                break;
            case 3:
                if (!this.last) sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate, time);
                else sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, animate, time);
                break;
        }
    }

    @Override
    public void update() {
        animate();
        chooseSprite(20);
        this.img = sprite.getFxImage();
    }
}
