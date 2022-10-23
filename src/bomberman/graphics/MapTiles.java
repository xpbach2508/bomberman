package bomberman.graphics;

import bomberman.BombermanGame;
import bomberman.entities.buff.*;
import bomberman.entities.tile.*;
import bomberman.entities.*;
import bomberman.entities.Enemies.*;

import java.io.*;
import static bomberman.BombermanGame.*;

public class MapTiles {
    private static int HEIGHT;
    private static int WIDTH;
    public static String[][] tiles = new String[13][31];

    public void getObject(int[] arr, String level) {
        try {
            File file = new File("./res/levels/" + level + ".txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] s = line.split(" ");
            HEIGHT = Integer.parseInt(s[1]);
            WIDTH = Integer.parseInt(s[2]);
            arr[0] = WIDTH;
            arr[1] = HEIGHT;

            for (int row = 0; row < HEIGHT; row++) {
                line = br.readLine();
                for (int i = 0; i < WIDTH; i++) {
                    Entity object = new Grass(i, row, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                    switch (line.charAt(i)) {
                        case '#' -> {
                            object = new Wall(i, row, Sprite.wall.getFxImage());
                            tiles[row][i] = "1";
                            stillObjects.add(object);
                        }
                        case '*' -> {
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            tiles[row][i] = "1";
                            stillObjects.add(object);
                        }
                        case 'x' -> {
                            object = new Portal(i, row, Sprite.portal.getFxImage());
                            tiles[row][i] = "1";
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                        }
                        case '1' -> {
                            object = new Balloom(i, row, Sprite.balloom_right1.getFxImage());
                            tiles[row][i] = "1";
                            entities.add(object);
                            BombermanGame.numberEnemies++;
                        }
                        case '2' -> {
                            object = new Oneal(i, row, Sprite.oneal_right1.getFxImage());
                            tiles[row][i] = "1";
                            entities.add(object);
                            BombermanGame.numberEnemies++;
                        }
                        case '3' -> {
                            object = new Doll(i, row, Sprite.doll_right1.getFxImage());
                            tiles[row][i] = "1";
                            entities.add(object);
                            BombermanGame.numberEnemies++;
                        }
                        case 'b' -> {
                            object = new BombBuff(i, row, Sprite.powerup_bombs.getFxImage());
                            tiles[row][i] = "1";
                            stillObjects.add(object);
                        }
                        case 'f' -> {
                            object = new FlameBuff(i, row, Sprite.powerup_flames.getFxImage());
                            tiles[row][i] = "1";
                            stillObjects.add(object);
                        }
                        case 's' -> {
                            object = new SpeedBuff(i, row, Sprite.powerup_speed.getFxImage());
                            tiles[row][i] = "1";
                            stillObjects.add(object);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
