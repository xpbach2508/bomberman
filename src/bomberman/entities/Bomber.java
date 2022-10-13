package bomberman.entities;

import bomberman.Collision;
import bomberman.entities.buff.SpeedBuff;
import bomberman.entities.tile.Wall;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import bomberman.MapTiles;
import bomberman.inPut.handleInput;
import bomberman.graphics.Sprite;
import bomberman.BombermanGame;

import java.util.ArrayList;
import java.util.List;

import static bomberman.BombermanGame.getStillEntityAt;

public class Bomber extends AnimatedEntity {

    public int moveX, moveY;

    protected int direct = -1;

    protected boolean moving = false;

    protected int bomberSpeed = 1;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        x += moveX;
        y += moveY;
        chooseSprite();
        this.img = sprite.getFxImage();
        //System.out.println(x + " " + y);
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
                if (canMove(x - bomberSpeed, y)) {
                    moveX += -bomberSpeed;
                    direct = 3;
                    //System.out.println("left");
                }
            }
            else if (dir.right) {
                moving = true;
                if (canMove(x + bomberSpeed, y)) {
                        moveX += bomberSpeed;
                        direct = 1;
                        //System.out.println("right");
                    }
            }
            else if (dir.up) {
                moving = true;
                if (canMove(x, y - bomberSpeed)) {
                        moveY += -bomberSpeed;
                        direct = 0;
                        //System.out.println("up");
                    }
            }
            else if (dir.down) {
                moving = true;
                if (canMove(x, y + bomberSpeed)) {
                        moveY += bomberSpeed;
                        direct = 2;
                        //System.out.println("down");
                    }
            }
            else moving = false;
    }

    @Override
    public boolean collide(Entity e) {
        Collision check = new Collision();
        return check.checkCollision(this, e);
    }

    public void setBomberSpeed() {
        this.bomberSpeed = this.bomberSpeed + 1;
    }
}
