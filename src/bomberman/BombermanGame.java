package bomberman;

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

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    // replace with getWidth and getHeight
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private MapTiles map = new MapTiles();

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Pane root = new Pane();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        Bomber player = new Bomber(32,32,Sprite.player_right.getFxImage());
        entities.add(player);
        map.getObject(entities,stillObjects);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    player.moveUp();
                    break;
                case DOWN:
                    player.moveDown();
                    break;
                case LEFT:
                    player.moveLeft();
                    break;
                case RIGHT:
                    player.moveRight();
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();

        List<Entity> all = new ArrayList<>();
        all.addAll(entities);
        all.addAll(stillObjects);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
    }

    public void update() {
        for (Entity entity : entities) {
            entity.update();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity stillObject : stillObjects) {
            stillObject.render(gc);
        }
        for (Entity g : entities) {
            g.render(gc);
        }
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

}
