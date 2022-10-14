package bomberman.entities;

import bomberman.BombermanGame;
import bomberman.Collision;
import bomberman.entities.buff.Buff;
import bomberman.entities.tile.Portal;
import javafx.scene.image.Image;
import bomberman.MapTiles;
import bomberman.inPut.handleInput;
import bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Bomber extends AnimatedEntity {

    public int moveX, moveY;

    protected int direct = -1;

    public handleInput dir;

    protected boolean moving = false;

    protected int bomberSpeed = 1;
    public int bomberNow = 1;
    public int timerIntervalBomb = 0;


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
        if (timerIntervalBomb < -7500) timerIntervalBomb = 0;
        else timerIntervalBomb--;
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

    public void move(MapTiles map, List<Entity> all) {
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
            else {
                moving = false;
            }
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
