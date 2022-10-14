package bomberman.entities;

import bomberman.graphics.Sprite;
import javafx.scene.image.Image;

import java.awt.font.ImageGraphicAttribute;
import java.util.ArrayList;
import java.util.List;

public class Bomb extends AnimatedEntity {

    public boolean removed = false;
    private int timerExplode = 200;
    private int timerRemove = 20;
    private List<Flame> flameList = new ArrayList<Flame>();
    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    private boolean explodedBom = false;
    private void chooseSprite() {
        if (!explodedBom) sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 40);
        else {
            sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 20);
            flameRender();
        }
    }

    private void flameRender() {
    }

    @Override
    public void update() {
        if (timerExplode > 0) timerExplode--;
        else {
            explodedBom = true;
            if (timerRemove > 0) timerRemove--;
            else removed = true;
        }
        animate();
        chooseSprite();
        this.img = sprite.getFxImage();
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
