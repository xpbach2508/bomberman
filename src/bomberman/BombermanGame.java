package bomberman;

import bomberman.entities.Bomb;
import bomberman.entities.Enemies;
import bomberman.entities.buff.Buff;
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
import bomberman.entities.Bomber;
import bomberman.entities.Entity;
import bomberman.graphics.Sprite;
import bomberman.inPut.handleInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BombermanGame extends Application {
    // replace with getWidth and getHeight
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext graContext;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private static List<Entity> stillObjects = new ArrayList<>();
    private MapTiles map = new MapTiles();

    private static Bomber player = new Bomber(1,1,Sprite.player_right.getFxImage());
    private static List<Bomb> bombs = new ArrayList<Bomb>();

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
        player.dir = direction;
        scene.setOnKeyPressed(direction::handlePressed);

        scene.setOnKeyReleased(direction::handleReleased);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        List<Entity> all = new ArrayList<>();
        all.addAll(entities);
        all.addAll(stillObjects);

        AnimationTimer timer = new AnimationTimer() {
            long pre = 0;
            @Override
            public void handle(long l) {
                if ((l - pre) < 7500000) {
                    return;
                }
                pre = l;
                player.move(direction);
                putBomb();
                removeBombs();
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
        for (int i = 0; i < stillObjects.size(); i++) {
            Entity e = stillObjects.get(i);
            if (e.collide(player) && e instanceof Buff) {
                stillObjects.remove(i);
                i--;
            }
        }
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
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
        for (Bomb b : bombs) {
            b.render(graContext);
        }
    }

    public static Entity getStillEntityAt(double x, double y) {
        Collections.reverse(stillObjects);
        for (Entity e : stillObjects) {
            if (e instanceof Grass) continue;
            if (e.getTileX() == (int) x / Sprite.SCALED_SIZE && e.getTileY() == (int) y / Sprite.SCALED_SIZE) {
                if (e instanceof Portal) continue;
                return e;
            }
        }
        return null;
    }

    public void removeBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i).removed) {
                bombs.remove(i);
                player.bomberNow++;
                i--;
            }
        }
    }

    public static boolean canPutBomb() {
        int posX = player.getX() + 1;
        int posY = player.getY() + 1;
        Entity belowE = getStillEntityAt(posX, posY);
        if (belowE instanceof Portal || belowE instanceof Buff || belowE instanceof Bomb || belowE instanceof Enemies) return false;
        return true;
    }

    public static void putBomb() {
        if (player.dir.space && canPutBomb() && player.timerIntervalBomb < 0 && player.bomberNow > 0) {
            bombs.add(new Bomb((player.getX() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE, (player.getY() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage()));
            player.timerIntervalBomb = 30;
            player.bomberNow--;
        }
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

}
