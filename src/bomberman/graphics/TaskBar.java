package bomberman.graphics;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static bomberman.Board.*;
import static bomberman.graphics.Menu.loadObject;

public class TaskBar {
    public static Text time, score, life, left;
    public static Image musicB = new Image("./textures/muteMusic.png", 32 * 1.7, 32 * 1.7, false, false);
    public static Image effectB = new Image("./textures/muteEffect.png", 32 * 1.7, 32 * 1.7, false, false);
    public static ImageView Music = new ImageView(musicB);
    public static ImageView effect = new ImageView(effectB);

    public static void create() {
        time = new Text("TIME: " + player.timeLeft);
        time.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 25));
        time.setFill(Color.WHITE);
        time.setX(40);
        time.setY(60);

        score = new Text("SCORES: " + player.score);
        score.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 25));
        score.setFill(Color.WHITE);
        score.setX(240);
        score.setY(60);

        life = new Text("LIVES: " + player.life);
        life.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 25));
        life.setFill(Color.WHITE);
        life.setX(480);
        life.setY(60);

        left = new Text(("ENEMIES: " + numberEnemies));
        left.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 25));
        left.setFill(Color.WHITE);
        left.setX(680);
        left.setY(60);

        Image playB = new Image("./textures/playButton.png", 32 * 4, 32 * 2, false, false);
        Image pauseB = new Image("./textures/pauseButton.png", 32 * 4, 32 * 2, false, false);
        Image replayB = new Image("./textures/replayButton.png", 32 * 4, 32 * 2, false, false);
        Image exitB = new Image("./textures/exitButton.png", 32 * 4, 32 * 2, false, false);

        ImageView pause = new ImageView(pauseB);
        ImageView replay = new ImageView(replayB);
        ImageView exit = new ImageView(exitB);

        pause.setX(1000);
        replay.setX(1000);
        exit.setX(1000);
        Music.setX(1000);
        effect.setX(1070);
        pause.setY(80);
        replay.setY(215);
        exit.setY(360);
        Music.setY(5);
        effect.setY(5);

        Music.setOnMouseClicked(mouseEvent -> {
            if (!music.pauseMusic) {
                Music.setImage(new Image("./textures/unmuteMusic.png", 32 * 1.7, 32 * 1.7, false, false));
                music.muteMusic();
            } else {
                Music.setImage(new Image("./textures/muteMusic.png", 32 * 1.7, 32 * 1.7, false, false));
                music.unmuteMusic();
            }
        });

        effect.setOnMouseClicked(mouseEvent -> {
            if (!music.pauseEffect) {
                effect.setImage(new Image("./textures/unmuteEffect.png", 32 * 1.7, 32 * 1.7, false, false));
                music.muteEffect();
            } else {
                effect.setImage(new Image("./textures/muteEffect.png", 32 * 1.7, 32 * 1.7, false, false));
                music.unmuteEffect();
            }
        });

        pause.setOnMouseClicked(mouseEvent -> {
            if (running) {
                running = false;
                pause.setImage(playB);
            } else {
                running = true;
                pause.setImage(pauseB);
            }
        });

        replay.setOnMouseClicked(mouseEvent -> {
            running = true;
            loadObject("Stage 1");
        });
        exit.setOnMouseClicked(mouseEvent -> Platform.exit());

        root.getChildren().add(time);
        root.getChildren().add(score);
        root.getChildren().add(life);
        root.getChildren().add(left);

        Pane layout = new Pane(pause, replay, exit, Music, effect);
        root.getChildren().add(layout);
    }

    public static void update() {
        time.setText("TIME: " + player.timeLeft);
        score.setText("SCORES: " + player.score);
        life.setText("LIVES: " + player.life);
        left.setText("ENEMIES: " + numberEnemies);
    }

    public static void clear() {
        root.getChildren().remove(time);
        root.getChildren().remove(score);
        root.getChildren().remove(life);
        root.getChildren().remove(left);
    }
}
