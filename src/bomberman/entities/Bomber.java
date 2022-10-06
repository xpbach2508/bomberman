package bomberman.entities;

import bomberman.MapTiles;
import javafx.scene.image.Image;
import bomberman.graphics.Sprite;

public class Bomber extends AnimatedEntity {

    public int aniX, aniY;

    public Bomber(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    @Override
    public void update() {
    }

    public void moveUp() {
        if (MapTiles.tiles[y/32 - 1][x/32] == "grass") {
            y -= 32;
            this.img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, y, 20).getFxImage();
        }
    }

    public void moveDown() {
        if (MapTiles.tiles[y/32 + 1][x/32] == "grass") {
            y += 32;
            this.img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, y, 20).getFxImage();
        }
    }

    public void moveLeft() {
            if (MapTiles.tiles[y/32][x/32 - 1] == "grass") {
                x -= 32;
                this.img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, x, 20).getFxImage();
            }
    }

    public void moveRight() {
            if (MapTiles.tiles[y/32][x/32 + 1] == "grass") {
                x += 32;
                this.img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, x, 20).getFxImage();
            }
    }
}
