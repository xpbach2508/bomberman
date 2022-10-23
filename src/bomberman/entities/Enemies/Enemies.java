package bomberman.entities.Enemies;

import bomberman.Collision;
import bomberman.entities.AnimatedEntity;
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import javafx.scene.image.Image;

import static bomberman.BombermanGame.player;

public class Enemies extends AnimatedEntity {
    public int direction;
    protected int timeAnimation = 70;
    protected int preX = 0, preY = 0;

    public Enemies(int x, int y, Image img) {
        super(x,y,img);
    }

    public void setChangedCoordinates(int x, int y) {
        this.preX = x;
        this.preY = y;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            if (Collision.checkCollision(this, e)) {
                player.removed = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
    }


}
