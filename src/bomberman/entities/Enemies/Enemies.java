package bomberman.entities.Enemies;

import bomberman.entities.AnimatedEntity;
import bomberman.entities.Entity;
import javafx.scene.image.Image;

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
        return false;
    }

    @Override
    public void update() {
    }


}
