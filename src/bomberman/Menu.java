package bomberman;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static bomberman.BombermanGame.running;
public class Menu {
    public static Image backgroundImg;
    public static ImageView background;
    private static Text[] options = new Text[3];
    private static Rectangle[] rect = new Rectangle[3];
    public static Pane layoutMenu;

    public static void create(Pane root) {
        backgroundImg = new Image("./textures/background.png", 32 * 31, 32 * 13, false, false);
        background = new ImageView(backgroundImg);
        background.setX(0);
        background.setY(0);

        for(int i = 0; i < 3; i++) {
            options[i] = new Text();
            options[i].setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,25));
            options[i].setFill(Color.WHITE);
            options[i].setStroke(Color.BLACK);

            rect[i] = new Rectangle();
            rect[i].setWidth(200);
            rect[i].setHeight(40);
            rect[i].setX(400);
            rect[i].setFill(Color.ORANGE);
            rect[i].setArcHeight(30);
            rect[i].setArcWidth(30);
            rect[i].setStroke(Color.WHITE);
            rect[i].setStrokeWidth(3);
        }

        layoutMenu = new Pane();
        layoutMenu.getChildren().addAll(background, rect[0], rect[1], rect[2], options[0], options[1], options[2]);
        root.getChildren().addAll(layoutMenu);

        rect[0].setOnMouseClicked(event -> {
            background.setX(-1000);
            background.setY(-1000);
            layoutMenu.setTranslateX(-1000);
            layoutMenu.setTranslateY(-1000);
            running = true;
//            new Level1(root);
        });

        options[0].setText("New Game");
        options[0].setX(510 - 67);
        options[0].setY(280);

        options[1].setText("Options");
        options[1].setX(520 - 67);
        options[1].setY(340);

        options[2].setText("Exit");
        options[2].setX(540 - 67);
        options[2].setY(400);

        rect[0].setY(253);
        rect[1].setY(313);
        rect[2].setY(373);

        rect[2].setOnMouseClicked(event-> Platform.exit());

        for(Rectangle Rect:rect) {
            Rect.setOnMouseEntered(event-> Rect.setFill(Color.rgb(127,200,0)));
        }

        for(Rectangle Rect:rect) {
            Rect.setOnMouseExited(event-> Rect.setFill(Color.ORANGE));
        }
    }
}