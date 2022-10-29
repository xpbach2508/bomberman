package bomberman.graphics;

import bomberman.entities.buff.*;
import bomberman.entities.tile.*;
import bomberman.entities.*;
import bomberman.entities.Enemies.*;

import java.io.*;
import java.util.List;

import static bomberman.BombermanGame.*;

public class MapTiles {
    public static void getObject(int[] arr, String level) {
        try {
            File file = new File("./res/levels/" + level + ".txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] s = line.split(" ");
            int HEIGHT = Integer.parseInt(s[1]);
            int WIDTH = Integer.parseInt(s[2]);
            arr[0] = WIDTH;
            arr[1] = HEIGHT;
            Sprite.GameHeight = HEIGHT;
            Sprite.GameWidth = WIDTH;

            for (int row = 0; row < HEIGHT; row++) {
                line = br.readLine();
                for (int i = 0; i < WIDTH; i++) {
                    Entity object = new Grass(i, row, Sprite.grass.getFxImage());
                    Enemies enemy = new Enemies(i, row, Sprite.minvo_right2.getFxImage());
                    stillObjects.add(object);
                    switch (line.charAt(i)) {
                        case '#' -> {
                            object = new Wall(i, row, Sprite.wall.getFxImage());
                            stillObjects.add(object);
                        }
                        case '*' -> {
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                        }
                        case 'x' -> {
                            object = new Portal(i, row, Sprite.portal.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                        }
                        case '1' -> {
                            enemy = new Balloom(i, row, Sprite.balloom_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                        }
                        case '2' -> {
                            enemy = new Oneal(i, row, Sprite.oneal_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                        }
                        case '3' -> {
                            enemy = new Doll(i, row, Sprite.doll_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                        }
                        case '4' -> {
                            enemy = new Minvo(i, row, Sprite.minvo_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                        }
                        case 'b' -> {
                            object = new BombBuff(i, row, Sprite.powerup_bombs.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                        }
                        case 'f' -> {
                            object = new FlameBuff(i, row, Sprite.powerup_flames.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                        }
                        case 's' -> {
                            object = new SpeedBuff(i, row, Sprite.powerup_speed.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
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
