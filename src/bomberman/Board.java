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
import bomberman.sfx.Sound;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static bomberman.graphics.MapTiles.tileMap;
import static bomberman.graphics.Menu.loadObject;

public class Board extends Application {
    private GraphicsContext graContext;
    private Canvas canvas;
    private long preTime;

    public static Sound music = new Sound();
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean running = false;
    public static int numberEnemies;

    public static Pane root = new Pane();
    public static Bomber player = new Bomber(1, 1, Sprite.player_right.getFxImage());
    public static List<Enemies> enemies = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static int[] size = new int[2];
    public static List<Bomb> bombList = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        loadObject("Stage 1");
        WIDTH = size[0];
        HEIGHT = size[1];

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        graContext = canvas.getGraphicsContext2D();
        canvas.setTranslateY(60);

        // Tao root container va scale
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, Sprite.SCALED_SIZE * WIDTH + 160, Sprite.SCALED_SIZE * HEIGHT + 60, Color.SKYBLUE);
        Scale scale = new Scale(1,1,1,1);
        scale.xProperty().bind(root.widthProperty().divide(32 * size[0] + 160));
        scale.yProperty().bind(root.heightProperty().divide(32 * size[1] + 60));
        root.getTransforms().add(scale);
        scene.getRoot().getTransforms().setAll(scale);
        Menu.create();

        //Handle Input
        handleInput direction = new handleInput();
        scene.setOnKeyPressed(direction::handlePressed);
        scene.setOnKeyReleased(direction::handleReleased);

        //Scene vao stage
        stage.getIcons().add(new Image("./textures/icon.jfif"));
        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    player.move(direction);
                    if (direction.space && !player.removed) {
                        player.putBomb();
                    }
                    if (player.detonatorOn && direction.p) {
                        player.bombDetonation();
                    }
                    player.removeBombs();
                    update(player);
                    countTime();
                    render();
                }
            }
        };
        timer.start();
    }

    public void update(Bomber player) {
        //Enemy and player
        player.update();
        for (Enemies e : enemies) {
            e.setDirection(player);
            e.update();
        }
        //Bomb
        for (Bomb e : bombList) {
            e.update(player, enemies, stillObjects, bombList);
        }
        //Brick and wall
        for (int i = 0; i < stillObjects.size(); i++) {
            Entity e = stillObjects.get(i);
            e.update();
            if (e.collide(player) && e instanceof Buff) {
                stillObjects.remove(i);
                i--;
            }
            if (e instanceof Brick && ((Brick) e).timeRemoved <= 0) {
                tileMap[e.getTileX()][e.getTileY()] = ' ';
                stillObjects.remove(i);
                i--;
            }
        }
        //Enemy and player collision
        for (int i = 0; i < enemies.size(); i++) {
            Enemies e = enemies.get(i);
            if (e.removed &&  e.timeAnimation <= 0) {
                enemies.remove(i);
                i--;
                numberEnemies--;
            }
            if (player.collide(e) && !e.removed && !player.immortal) player.removed = true;
        }
    }

    public void render() {
        graContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity stillObject : stillObjects) {
            stillObject.render(graContext);
        }
        for (Entity entity : enemies) {
            entity.render(graContext);
        }
        for (Bomb b : bombList) {
            b.render(graContext);
        }
        player.render(graContext);
    }

    public static Entity getStillEntityAt(double x, double y) {
        int xTile = (int) x / Sprite.SCALED_SIZE;
        int yTile = (int) y / Sprite.SCALED_SIZE;
        for (int i = stillObjects.size() - 1; i >= 0; i--) {
            Entity e = stillObjects.get(i);
            if (e instanceof Grass) continue;
            if (e.getTileX() == xTile && e.getTileY() == yTile) {
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

    public static Entity getMovingEntityAt(double x, double y) {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemies e = enemies.get(i);
            if (e.getTileX() == (int) x / Sprite.SCALED_SIZE && e.getTileY() == (int) y / Sprite.SCALED_SIZE) {
                return e;
            }
        }
        return null;
    }

    public void countTime() {
        long current = System.currentTimeMillis();
        if (current - preTime >= 1000) {
            preTime = System.currentTimeMillis();
            player.timeLeft--;
        }
    }
}
