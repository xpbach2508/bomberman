package bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import bomberman.MapTiles;
import bomberman.inPut.handleInput;
import bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends AnimatedEntity {

    public int moveX, moveY;

    protected int direct = -1;

    protected boolean moving = false;

    public Bomber(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    @Override
    public void update() {
        animate();
        x += moveX;
        y += moveY;
        chooseSprite();
        this.img = sprite.getFxImage();
    }

    private void chooseSprite() {
        switch (direct) {
            case 0 -> {
                sprite = Sprite.player_up;
                if (moving) sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
            }
            case 2 -> {
                sprite = Sprite.player_down;
                if (moving) sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
            }
            case 3 -> {
                sprite = Sprite.player_left;
                if (moving) sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
            }
            default -> {
                sprite = Sprite.player_right;
                if (moving) sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
            }
        }
    }

    public void move(handleInput dir, MapTiles map, List<Entity> all) {
        moveX = 0;
        moveY = 0;
            if (dir.left) {
                    moving = true;
                    moveX += -1;
                    direct = 3;
            }
            else if (dir.right) {
                    moving = true;
                    moveX += 1;
                    direct = 1;
            }
            else if (dir.up) {
                    moving = true;
                    moveY += -1;
                    direct = 0;
            }
            else if (dir.down) {
                    moving = true;
                    moveY += 1;
                    direct = 2;
            }
            else moving = false;
    }

    @Override
    public boolean collide(Entity e) {

        return false;
    }
}
