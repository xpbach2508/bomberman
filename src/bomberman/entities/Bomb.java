package bomberman.entities;

public class Bomb extends Entity {
    @Override
    public void update() {
        return;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
