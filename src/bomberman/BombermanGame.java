package bomberman;

import bomberman.entities.*;
import bomberman.entities.Enemies.Enemies;
import bomberman.entities.buff.Buff;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Grass;
import bomberman.entities.tile.Portal;
import bomberman.entities.tile.Wall;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import bomberman.graphics.Sprite;
import bomberman.inPut.handleInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BombermanGame extends Application {
    // replace with getWidth and getHeight
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    protected static int numberEnemies = 0;
    private GraphicsContext graContext;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();

    private static List<Bomb> bombList = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private MapTiles map = new MapTiles();

    private static Bomber player = new Bomber(1,1,Sprite.player_right.getFxImage());

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        graContext = canvas.getGraphicsContext2D();

        // Tao root container
        Pane root = new Pane();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        //stillObjects.addAll(bombs);
        entities.add(player);
        map.getObject(entities,stillObjects);

        handleInput direction = new handleInput();
        scene.setOnKeyPressed(direction::handlePressed);

        scene.setOnKeyReleased(direction::handleReleased);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            long pre = 0;
            @Override
            public void handle(long l) {
                if ((l - pre) < 7500000) {
                    return;
                }
                pre = l;
                player.move(direction);
                if (direction.space) {
                    putBomb(player);
                }
                removeBombs(player);
                update(player);
                render();
            }
        };
        timer.start();
    }

    public void update(Bomber player) {
        for (Entity entity : entities) {
            entity.update();
        }
        for (Bomb e : bombList) {
            e.update(entities, stillObjects);
        }
        for (Entity e : stillObjects) {
            e.update();
        }
        for (int i = 0; i < stillObjects.size(); i++) {
            Entity e = stillObjects.get(i);
            if (e.collide(player) && e instanceof Buff) {
                stillObjects.remove(i);
                i--;
            }
            if (e instanceof Brick && ((Brick) e).timeRemoved <= 0) {
                stillObjects.remove(i);
                i--;
            }
        }
    }

    public void render() {
        graContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity stillObject : stillObjects) {
            stillObject.render(graContext);
        }
        for (Entity g : entities) {
            g.render(graContext);
        }
        for (Bomb b : bombList) {
            b.render(graContext);
        }
    }

    public static Entity getStillEntityAt(double x, double y) {
        int xTile = (int) x / Sprite.SCALED_SIZE;
        int yTile = (int) y / Sprite.SCALED_SIZE;
        for (Entity e : stillObjects) {
            if (e instanceof Grass) continue;
            if (e.getTileX() == xTile && e.getTileY() == yTile) {
                if (e instanceof Portal) continue;
                return e;
            }
        }

        for (Bomb e : bombList) {
            if (e.getTileX() == xTile && e.getTileY() == yTile) {
                if (!e.isMovedOut()) {
                    return null;
                } else {
                    return e;
                }
            }
        }
        return null;
    }

    public void removeBombs(Bomber e) {
        for (int i = 0; i < bombList.size(); i++) {
            if (bombList.get(i).removed) {
                bombList.remove(i);
                e.bombNow++;
                i--;
            }
        }
    }

    public void putBomb(Bomber e) {
        if (e.timerIntervalBomb < 0 && e.bombNow > 0) {
            Bomb b = new Bomb((e.getX() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                    (e.getY() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage(), e.bombPower);
            bombList.add(b);
            e.timerIntervalBomb = 30;
            e.bombNow--;
        }
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

}
