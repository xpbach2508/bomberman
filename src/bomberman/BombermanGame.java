package bomberman;

import bomberman.entities.AnimatedEntity;
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
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static bomberman.graphics.Menu.loadObject;

public class BombermanGame extends Application {
    // replace with getWidth and getHeight
    public static Sound music = new Sound();
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean running = false;
    public static int numberEnemies;
    private GraphicsContext graContext;
    private Canvas canvas;
    private long preTime;

    public static Pane root = new Pane();
    public static Bomber player = new Bomber(1, 1, Sprite.player_right.getFxImage());
    public static List<Entity> entities = new ArrayList<>(List.of(player));
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

        // Tao root container
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, Sprite.SCALED_SIZE * WIDTH + 160, Sprite.SCALED_SIZE * HEIGHT + 60, Color.SKYBLUE);
        Scale scale = new Scale(1,1,0,0);
        root.getTransforms().removeIf(e -> (e instanceof Scale));
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
        stage.setResizable(true);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running) {
                    player.move(direction);
                    if (direction.space && !player.removed) {
                        putBomb(player);
                    }
                    removeBombs(player);
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
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        //Bomb
        for (Bomb e : bombList) {
            e.update(entities, stillObjects, bombList);
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
                stillObjects.remove(i);
                i--;
            }
        }
        //Enemy and player collision
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.removed && (e instanceof Enemies) && ((AnimatedEntity) e).timeAnimation <= 0) {
                entities.remove(i);
                i--;
                numberEnemies--;
            }
            if (e instanceof Enemies) {
                if (player.collide(e) && !e.removed) player.removed = true;
            }
        }
    }

    public void render() {
        graContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity stillObject : stillObjects) {
            stillObject.render(graContext);
        }
        for (Entity entity : entities) {
            entity.render(graContext);
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

    public static Entity getMovingEntityAt(double x, double y) {
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity e = entities.get(i);
            if (e instanceof Enemies) if (e.getTileX() == (int) x / Sprite.SCALED_SIZE && e.getTileY() == (int) y / Sprite.SCALED_SIZE) {
                return e;
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
            int xTileMore = 0;
            int yTileMore = 0;
            if (!canPutBomb(e)) switch (e.direct) {
                case 0 -> yTileMore = 1;
                case 2 -> yTileMore = -1;
                case 3 -> xTileMore = 1;
                default -> xTileMore = -1;
            }
            Bomb b = new Bomb((e.getX() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE + xTileMore,
                    (e.getY() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE + yTileMore, Sprite.bomb.getFxImage(), e.bombPower);
            bombList.add(b);
            music.playPlantBomb();
            e.timerIntervalBomb = 30;
            e.bombNow--;
        }
    }

    public boolean canPutBomb(Bomber b) {
        Entity e = getMovingEntityAt((b.getX() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE,
                                                    ((b.getY() + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE));
        return e == null;
    }

    public void countTime() {
        long current = System.currentTimeMillis();
        if (current - preTime >= 1000) {
            preTime = System.currentTimeMillis();
            player.timeLeft--;
        }
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }
}
