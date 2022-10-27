package bomberman.graphics;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static bomberman.BombermanGame.*;
import static bomberman.graphics.Menu.loadObject;

public class TaskBar {
    public static Text time, score, life, left;
    public static Image musicB = new Image("./textures/muteMusic.png", 32*1.7, 32*1.7, false, false);
    public static Image effectB = new Image("./textures/muteEffect.png", 32*1.7, 32*1.7, false, false);
    public static ImageView Music = new ImageView(musicB);
    public static ImageView effect = new ImageView(effectB);
    public static void create() {
        time = new Text("TIME: " + player.timeLeft);
        time.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf",25));
        time.setFill(Color.WHITE);
        time.setX(40);
        time.setY(60);

        score = new Text("SCORES: " + player.score);
        score.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf",25));
        score.setFill(Color.WHITE);
        score.setX(240);
        score.setY(60);

        life = new Text("LIVES: " + player.life);
        life.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf",25));
        life.setFill(Color.WHITE);
        life.setX(480);
        life.setY(60);

        left = new Text(("ENEMIES: " + numberEnemies));
        left.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf",25));
        left.setFill(Color.WHITE);
        left.setX(680);
        left.setY(60);

        Image playB = new Image("./textures/playButton.png", 32 * 4, 32 * 2, false, false);
        Image pauseB = new Image("./textures/pauseButton.png", 32 * 4, 32*2, false, false);
        Image replayB = new Image("./textures/replayButton.png", 32 * 4, 32*2, false, false);
        Image exitB = new Image("./textures/exitButton.png", 32 * 4, 32*2, false, false);


        ImageView play = new ImageView(playB);
        ImageView pause = new ImageView(pauseB);
        ImageView replay = new ImageView(replayB);
        ImageView exit = new ImageView(exitB);

        play.setX(1000);
        pause.setX(1000);
        replay.setX(1000);
        exit.setX(1000);
        Music.setX(930);
        effect.setX(1070);
        play.setY(80);
        pause.setY(180);
        replay.setY(270);
        exit.setY(360);
        Music.setY(5);
        effect.setY(5);

        Music.setOnMouseClicked(mouseEvent -> {
            if (!music.pauseMusic) {
                Music.setImage(new Image("./textures/unmuteMusic.png",32*1.7,32*1.7,false,false));
                music.muteMusic();
            } else {
                Music.setImage(new Image("./textures/muteMusic.png",32*1.7,32*1.7,false,false));
                music.unmuteMusic();
            }
        });

        effect.setOnMouseClicked(mouseEvent -> {
            if (!music.pauseEffect) {
                effect.setImage(new Image("./textures/unmuteEffect.png",32*1.7,32*1.7,false,false));
                music.muteEffect();
            } else {
                effect.setImage(new Image("./textures/muteEffect.png",32*1.7,32*1.7,false,false));
                music.unmuteEffect();
            }
        });

        final boolean[] ifPaused = {false};
        play.setOnMouseClicked(mouseEvent -> {
            if (ifPaused[0]) {
                running = true;
                ifPaused[0] = false;
            }
        });
        pause.setOnMouseClicked(mouseEvent -> {
            running = false;
            ifPaused[0] = true;
        });
        replay.setOnMouseClicked(mouseEvent -> loadObject("Stage 1"));
        exit.setOnMouseClicked(mouseEvent -> Platform.exit());

        Pane layout = new Pane(time,score,life,left,play,pause,replay,exit,Music,effect);
        root.getChildren().add(layout);
    }

    public static void update() {
        time.setText("TIME: " + player.timeLeft);
        score.setText("SCORES: " + player.score);
        life.setText("LIVES: " + player.life);
        left.setText("ENEMIES: " + numberEnemies);
    }
}