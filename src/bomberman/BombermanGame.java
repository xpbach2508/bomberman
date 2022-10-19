package bomberman;

import bomberman.entities.Bomb;
import bomberman.entities.Bomber;
import bomberman.entities.Enemies.Enemies;
import bomberman.entities.Entity;
import bomberman.entities.buff.Buff;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BombermanGame extends Application {
    // replace with getWidth and getHeight
    public static int WIDTH;
    public static int HEIGHT;

    public static boolean running;

    private GraphicsContext graContext;
    private Canvas canvas;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    private MapTiles map = new MapTiles();

    private static Bomber player = new Bomber(1,1,Sprite.player_right.getFxImage());
    private static List<Bomb> bombs = new ArrayList<>();

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
        player.dir = direction;
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
                    putBomb();
                    removeBombs();
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
        for (int i = 0; i < stillObjects.size(); i++) {
            Entity e = stillObjects.get(i);
            if (e.collide(player) && e instanceof Buff) {
                stillObjects.remove(i);
                i--;
            }
        }
        for (Bomb bomb : bombs) {
            bomb.update();
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
        return !(belowE instanceof Portal) && !(belowE instanceof Buff) && !(belowE instanceof Bomb) && !(belowE instanceof Enemies);
    }

    public static void putBomb() {
        if (player.dir.space && player.timerIntervalBomb < 0 && player.bomberNow > 0) {
            bombs.add(new Bomb((player.getX() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE, (player.getY() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage()));
            player.timerIntervalBomb = 30;
            player.bomberNow--;
        }
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
}
