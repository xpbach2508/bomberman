package bomberman.graphics;

import bomberman.entities.Bomber;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import static bomberman.BombermanGame.*;
import static bomberman.graphics.MapTiles.getObject;

public class Menu {
    public static void create(Scale scale) {
        scale.xProperty().bind(root.widthProperty().divide(32 * 31));     //must match with the one in the controller
        scale.yProperty().bind(root.heightProperty().divide(32 * 13));   //must match with the one in the controller
        root.getTransforms().add(scale);

        Image backgroundImg = new Image("./textures/background.png", 32 * 31, 32 * 13, false, false);
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
            rect[i].setX(390);
            rect[i].setFill(Color.DEEPSKYBLUE);
            rect[i].setArcHeight(40);
            rect[i].setArcWidth(40);
            rect[i].setStroke(Color.CYAN);
            rect[i].setStrokeWidth(2);
        }

        Pane layoutMenu = new Pane();
        layoutMenu.getChildren().addAll(background, rect[0], rect[1], rect[2], options[0], options[1], options[2]);
        root.getChildren().addAll(layoutMenu);

        rect[0].setOnMouseClicked(event -> {
            background.setX(-1000);
            background.setY(-1000);
            layoutMenu.setTranslateX(-1000);
            layoutMenu.setTranslateY(-1000);
            running = true;
            music.playGameMusic();
            player.life = 3;
            loadLevel("Stage 1");
        });
        options[0].setOnMouseClicked(event -> {
            background.setX(-1000);
            background.setY(-1000);
            layoutMenu.setTranslateX(-1000);
            layoutMenu.setTranslateY(-1000);
            running = true;
            music.playGameMusic();
            player.life = 3;
            loadLevel("Stage 1");
        });

        options[0].setText("New Game");
        options[0].setX(500 - 70);
        options[0].setY(280);

        options[1].setText("Options");
        options[1].setX(510 - 70);
        options[1].setY(340);

        options[2].setText("Exit");
        options[2].setX(530 - 70);
        options[2].setY(400);

        rect[0].setY(233);
        rect[1].setY(293);
        rect[2].setY(353);

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
        Image backgroundImg = new Image("./textures/scr2gif.gif", 32 * 31, 32 * 13, false, false);
        Image load = new Image("./textures/load.gif", 32 * 9, 32 * 4, false, false);
        Image bl = new Image("./textures/black.png", 32 * 31, 32 * 13, false, false);

        ImageView background = new ImageView(backgroundImg);
        ImageView loading = new ImageView(load);
        ImageView black = new ImageView(bl);

        loading.setX(367);
        loading.setY(200);

        root.getChildren().add(background);
        root.getChildren().add(loading);

        Text text = new Text(430,250,level);
        text.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 45));
        text.setFill(Color.WHITE);
        PauseTransition loadStage = new PauseTransition(Duration.seconds(1));
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
        });

        SequentialTransition sq = new SequentialTransition(loadStage, stage);
        sq.play();
    }

    public static void loadObject(String level) {
        entities.removeIf(n -> !(n instanceof Bomber));
        stillObjects.clear();
        player.reset();
        numberEnemies = 0;
        getObject(size, level);
    }

    public static void checkEnd(String message) {
        Image bl = new Image("./textures/black.png", 32 * 31, 32 * 13, false, false);
        ImageView black = new ImageView(bl);
        Image cat = new Image("./textures/cat.gif", 32 * 9, 32 * 4, false, false);
        ImageView meow = new ImageView(cat);
        meow.setX(367);
        meow.setY(200);

        Text text = new Text(410,250,message);
        text.setFont(Font.loadFont("file:./res/textures/pixel_font.ttf", 45));
        text.setFill(Color.WHITE);
        root.getChildren().add(black);
        root.getChildren().add(text);
        if (message.equals("You Win")) {
            root.getChildren().add(meow);
        }
        running = false;

        PauseTransition over = new PauseTransition(Duration.seconds(2));
        over.setOnFinished(e -> {
            root.getChildren().remove(meow);
            root.getChildren().remove(black);
            root.getChildren().remove(text);
            create(new Scale(1,1,0,0));
            loadObject("Stage 1");
            entities.add(player);
        });
        over.play();
    }
}