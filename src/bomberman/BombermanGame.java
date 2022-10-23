package bomberman;

import bomberman.entities.Bomb;
import bomberman.entities.Bomber;
import bomberman.entities.Enemies.Enemies;
import bomberman.entities.Entity;
import bomberman.entities.buff.Buff;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Grass;
import bomberman.graphics.Menu;
import bomberman.graphics.Sprite;
import bomberman.inPut.handleInput;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static bomberman.graphics.Menu.loadObject;

public class BombermanGame extends Application {
    // replace with getWidth and getHeight
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean running;

    protected static int numberEnemies = 0;
    private GraphicsContext graContext;
    private Canvas canvas;

    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static Pane root = new Pane();
    public static Scene scene = new Scene(root);
    public static Bomber player = new Bomber(1, 1, Sprite.player_right.getFxImage());
    public static int[] size = new int[2];
    private static List<Bomb> bombList = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        loadObject("Stage 1");
        WIDTH = size[0];
        HEIGHT = size[1];

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        graContext = canvas.getGraphicsContext2D();

        // Tao root container
        root.getChildren().add(canvas);
        Scale scale = new Scale(1, 1, 0, 0);
        Menu.create(scale);


        // Tao scene
        scene.getRoot().getTransforms().setAll(scale);


        //stillObjects.addAll(bombList);
        entities.add(player);

        handleInput direction = new handleInput();
        scene.setOnKeyPressed(direction::handlePressed);

        scene.setOnKeyReleased(direction::handleReleased);

        // Them scene vao stage
        stage.getIcons().add(new Image("./textures/icon.jfif"));
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.setResizable(true);
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
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        for (Bomb e : bombList) {
            e.update(entities, stillObjects);
        }
        for (int i = 0; i < stillObjects.size(); i++) {
            Entity e = stillObjects.get(i);
            e.update();
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
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(graContext);
        }
        for (Bomb b : bombList) {
            b.render(graContext);
        }
    }

    public static Entity getStillEntityAt(double x, double y) {
        int xTile = (int) x / Sprite.SCALED_SIZE;
        int yTile = (int) y / Sprite.SCALED_SIZE;
        for (int i = stillObjects.size() - 1; i >= 0; i--) {
            Entity e = stillObjects.get(i);
            if (e instanceof Grass) continue;
            if (e.getTileX() == (int) x / Sprite.SCALED_SIZE && e.getTileY() == (int) y / Sprite.SCALED_SIZE) {
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
