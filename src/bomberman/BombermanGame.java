package bomberman;

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
        Bomber player = new Bomber(1,1,Sprite.player_right.getFxImage());
        entities.add(player);
        map.getObject(entities,stillObjects);

        handleInput direction = new handleInput();
        scene.setOnKeyPressed(direction::handlePressed);

        scene.setOnKeyReleased(direction::handleReleased);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        List<Entity> all = new ArrayList<>();
        all.addAll(entities);
        all.addAll(stillObjects);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                player.move(direction, map, stillObjects);
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
    }

    public void render() {
        graContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity stillObject : stillObjects) {
            stillObject.render(graContext);
        }
        for (Entity g : entities) {
            g.render(graContext);
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

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

}
