package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.MapTiles;
import uet.oop.bomberman.inPut.handleInput;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {

    public int moveX, moveY;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        x += moveX;
        y += moveY;
    }

    public void move(handleInput dir, MapTiles map, List<Entity> all) {
        moveX = 0;
        moveY = 0;
            if (dir.left) {
                //if (map.tiles[(int) Math.ceil(x*1.0/32)-1][y/32].equals("grass")) {
                    moveX += -1;

            }
            if (dir.right) {
                //if (map.tiles[(int) Math.floor(x*1.0/32)+1][y/32].equals("grass")) {
                    moveX += 1;

            }
            if (dir.up) {
                //if (map.tiles[x/32][(int) Math.ceil(y*1.0/32)-1].equals("grass")) {
                    moveY += -1;

            }
            if (dir.down) {
                //if (map.tiles[x/32][y/32+1].equals("grass")) {
                    moveY += 1;
            }
    }
}
