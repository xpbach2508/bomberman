package bomberman.graphics;

import bomberman.entities.Bomber;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static bomberman.BombermanGame.*;
import static bomberman.graphics.MapTiles.getObject;

public class Menu {
    public static void create() {
        music.playMenuMusic();

        Image backgroundImg = new Image("./textures/background.png", 32 * size[0] + 160, 32 * size[1]+60, false, false);
        ImageView background = new ImageView(backgroundImg);
        background.setX(0);
        background.setY(0);

        Text[] options = new Text[3];
        Rectangle[] rect = new Rectangle[3];

        for(int i = 0; i < 3; i++) {
            options[i] = new Text();
            options[i].setFont(Font.loadFont("file:./res/textures/pixel_font.ttf",25));
            options[i].setFill(Color.WHITE);

            rect[i] = new Rectangle();
            rect[i].setWidth(200);
            rect[i].setHeight(40);
            rect[i].setX(490);
            rect[i].setFill(Color.DEEPSKYBLUE);
            rect[i].setArcHeight(40);
            rect[i].setArcWidth(40);
            rect[i].setStroke(Color.CYAN);
            rect[i].setStrokeWidth(2);
        }

        Pane layoutMenu = new Pane(background, rect[0], rect[1], rect[2], options[0], options[1], options[2]);
        root.getChildren().addAll(layoutMenu);

        options[0].setText("NEW GAME");
        options[0].setX(525);
        options[0].setY(290);

        options[1].setText("CREDITS");
        options[1].setX(535);
        options[1].setY(355);

        options[2].setText("EXIT");
        options[2].setX(565);
        options[2].setY(430);

        rect[0].setY(243);
        rect[1].setY(308);
        rect[2].setY(383);

        rect[0].setOnMouseClicked(event -> {
            background.setX(-1000);
            background.setY(-1000);
            layoutMenu.setTranslateX(-1000);
            layoutMenu.setTranslateY(-1000);
            player.life = 3;
            music.stopMenuMusic();
            music.playGameMusic();
            loadLevel("Stage 1");
        });
        options[0].setOnMouseClicked(event -> {
            background.setX(-1000);
            background.setY(-1000);
            layoutMenu.setTranslateX(-1000);
            layoutMenu.setTranslateY(-1000);
            player.life = 3;
            music.stopMenuMusic();
            music.playGameMusic();
            loadLevel("Stage 1");
        });

        Image bl = new Image("./textures/black.png", 32 * size[0] + 160, 32 * size[1]+60, false, false);
        ImageView black = new ImageView(bl);
        Text text = new Text(450,210,"  GAME MADE BY :  \nPHAM MINH HIEU\nPHAM XUAN BACH");
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 30));
        text.setFill(Color.WHITE);

        PauseTransition credit = new PauseTransition(Duration.seconds(3));
        credit.setOnFinished(e -> {
            root.getChildren().remove(black);
            root.getChildren().remove(text);
        });
        rect[1].setOnMouseClicked(mouseEvent -> {
            root.getChildren().add(black);
            root.getChildren().add(text);
            credit.play();
        });
        options[1].setOnMouseClicked(mouseEvent -> {
            root.getChildren().add(black);
            root.getChildren().add(text);
            credit.play();
        });


        rect[2].setOnMouseClicked(event-> Platform.exit());
        options[2].setOnMouseClicked(event-> Platform.exit());


        for(int i = 0; i < 3; i++) {
            int finalI = i;
            rect[i].setOnMouseEntered(event-> rect[finalI].setFill(Color.LIGHTGREEN));
            rect[i].setOnMouseExited(event-> rect[finalI].setFill(Color.DEEPSKYBLUE));
            options[i].setOnMouseEntered(event-> rect[finalI].setFill(Color.LIGHTGREEN));
            options[i].setOnMouseExited(event-> rect[finalI].setFill(Color.DEEPSKYBLUE));
        }
    }
    public static void loadLevel(String level) {
        if (level.equals("Stage 1")) {
            TaskBar.create();
        }
        Image backgroundImg = new Image("./textures/scr2gif.gif", 32 * size[0] + 160, 32 * size[1]+60, false, false);
        Image load = new Image("./textures/load.gif", 32 * 9, 32 * 4, false, false);
        Image bl = new Image("./textures/black.png", 32 * size[0] + 160, 32 * size[1]+60, false, false);

        music.stopGameMusic();
        music.stopMove();
        music.playLoadScene();

        ImageView background = new ImageView(backgroundImg);
        ImageView loading = new ImageView(load);
        ImageView black = new ImageView(bl);

        loading.setX(450);
        loading.setY(250);

        root.getChildren().add(background);
        root.getChildren().add(loading);

        Text text = new Text(490,270,level);
        text.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 45));
        text.setFill(Color.WHITE);


        PauseTransition loadStage = new PauseTransition(Duration.seconds(2));
        running = false;
        loadStage.setOnFinished(e -> {
            root.getChildren().add(black);
            root.getChildren().add(text);
            root.getChildren().remove(background);
            root.getChildren().remove(loading);
        });

        PauseTransition stage = new PauseTransition(Duration.seconds(1));
        stage.setOnFinished(m -> {
            root.getChildren().remove(text);
            running = true;
            root.getChildren().remove(black);
            music.stopLoadScene();
            music.playGameMusic();
        });

        SequentialTransition sq = new SequentialTransition(loadStage, stage);
        sq.play();
    }

    public static void loadObject(String level) {
        enemies.clear();
        stillObjects.clear();
        bombList.clear();
        player.reset();
        player.timeLeft = 200;
        player.life = 3;
        numberEnemies = 0;
        getObject(size, level);
    }

    public static void checkEnd(String message) {
        TaskBar.clear();
        Image bl = new Image("./textures/black.png", 32 * 31 + 200, 32 * 13+60, false, false);
        ImageView black = new ImageView(bl);
        Image cat = new Image("./textures/cat.gif", 32 * 9, 32 * 4, false, false);
        ImageView meow = new ImageView(cat);
        meow.setX(480);
        meow.setY(200);
        Duration time = Duration.seconds(6);
        music.stopGameMusic();
        music.stopMove();

        Text text = new Text(510,250,message);
        text.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 45));
        text.setFill(Color.WHITE);
        root.getChildren().add(black);
        root.getChildren().add(text);
        if (message.equals("You Win")) {
            time = Duration.seconds(13);
            music.playWin();
            root.getChildren().add(meow);
        } else music.playGameOver();
        running = false;

        PauseTransition over = new PauseTransition(time);
        over.setOnFinished(e -> {
            root.getChildren().remove(meow);
            root.getChildren().remove(black);
            root.getChildren().remove(text);
            music.stopGameOver();
            music.stopWin();
            create();
            loadObject("Stage 1");
        });
        over.play();
    }
}