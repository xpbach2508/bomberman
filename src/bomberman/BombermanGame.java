package bomberman;

import bomberman.entities.AnimatedEntity;
import bomberman.entities.Bomb;
import bomberman.entities.Bomber;
import bomberman.entities.Enemies.Enemies;
import bomberman.entities.Entity;
import bomberman.entities.buff.Buff;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Grass;
import bomberman.entities.tile.Portal;
import bomberman.graphics.MapTiles;
import bomberman.graphics.Sprite;
import bomberman.inPut.handleInput;
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
    public static int WIDTH;
    public static int HEIGHT;

    public static boolean running;

    public static int numberEnemies = 0;
    private GraphicsContext graContext;
    private Canvas canvas;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    private MapTiles map = new MapTiles();

    private static Bomber player = new Bomber(1,1,Sprite.player_right.getFxImage());
    private static List<Bomb> bombList = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        int[] size = new int[2];
        map.getObject(size, "Level1");
        WIDTH = size[0];
        HEIGHT = size[1];

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        graContext = canvas.getGraphicsContext2D();

        // Tao root container
        Pane root = new Pane();
        root.getChildren().add(canvas);
        Menu.create(root);

        // Tao scene
        Scene scene = new Scene(root);

        //stillObjects.addAll(bombs);
        entities.add(player);

        handleInput direction = new handleInput();
        scene.setOnKeyPressed(direction::handlePressed);

        scene.setOnKeyReleased(direction::handleReleased);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    player.move(direction);
                    if (direction.space) {
                        putBomb(player);
                    }
                    removeBombs(player);
                    update(player);
                    render();
                }
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
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.removed && (e instanceof Bomber || e instanceof Enemies) && ((AnimatedEntity) e).timeAnimation <= 0) {
                entities.remove(i);
                i--;
                numberEnemies--;
            }
            if (e instanceof Enemies) {
                if (player.collide(e)) System.out.println("Game over");
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
