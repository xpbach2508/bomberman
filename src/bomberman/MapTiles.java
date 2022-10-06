package bomberman;

import bomberman.graphics.Sprite;
import bomberman.entities.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MapTiles {

    public static int WIDTH ;
    public static int HEIGHT ;

    public static String[][] tiles = new String[13][31];

    public void getObject(List<Entity> entities, List<Entity> StillObject) {
        try {
            File file = new File("./res/levels/Level1.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] s = line.split(" ");
            HEIGHT = Integer.parseInt(s[1]);
            WIDTH = Integer.parseInt(s[2]);

            for (int row = 0; row < HEIGHT; row++) {
                line = br.readLine();
                for (int i = 0; i < WIDTH; i++) {
                    Entity object = new Grass(i, row, Sprite.grass.getFxImage());
                    switch (line.charAt(i)) {
                        case '#':
                            object = new Wall(i, row, Sprite.wall.getFxImage());
                            tiles[row][i] = "1";
                            StillObject.add(object);
                            break;
                        case '*':
                            object = new Wall(i, row, Sprite.brick.getFxImage());
                            tiles[row][i] = "1";
                            StillObject.add(object);
                            break;
                        case 'x':
                            object = new Portal(i, row, Sprite.portal.getFxImage());
                            tiles[row][i] = "1";
                            StillObject.add(object);
                            break;
                        case '1':
                            object = new Enemies(i, row, Sprite.balloom_right1.getFxImage());
                            tiles[row][i] = "1";
                            entities.add(object);
                            break;
                        case '2':
                            object = new Enemies(i, row, Sprite.oneal_right1.getFxImage());
                            tiles[row][i] = "1";
                            entities.add(object);
                            break;
                        case '3':
                            object = new Buff(i, row, Sprite.powerup_bombs.getFxImage());
                            tiles[row][i] = "1";
                            StillObject.add(object);
                            break;
                        default:
                            tiles[row][i] = "grass";
                            StillObject.add(object);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
