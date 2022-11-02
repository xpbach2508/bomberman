package bomberman.graphics;

import bomberman.entities.buff.*;
import bomberman.entities.tile.*;
import bomberman.entities.*;
import bomberman.entities.Enemies.*;

import java.io.*;

import static bomberman.Board.*;

public class MapTiles {
    public static char[][] tileMap;
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
            tileMap = new char[Sprite.GameWidth][Sprite.GameHeight];
            for (int row = 0; row < HEIGHT; row++) {
                line = br.readLine();
                for (int i = 0; i < WIDTH; i++) {
                    Entity object = new Grass(i, row, Sprite.grass.getFxImage());
                    tileMap[i][row] = ' ';
                    Enemies enemy;
                    stillObjects.add(object);
                    switch (line.charAt(i)) {
                        case '#' -> {
                            object = new Wall(i, row, Sprite.wall.getFxImage());
                            stillObjects.add(object);
                            tileMap[i][row] = '#';
                        }
                        case '*' -> {
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                            tileMap[i][row] = '*';
                        }
                        case 'x' -> {
                            object = new Portal(i, row, Sprite.portal.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                            tileMap[i][row] = '*';
                        }
                        case '1' -> {
                            enemy = new Balloom(i, row, Sprite.balloom_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                            tileMap[i][row] = '1';
                        }
                        case '2' -> {
                            enemy = new Oneal(i, row, Sprite.oneal_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                            tileMap[i][row] = '2';
                        }
                        case '3' -> {
                            enemy = new Doll(i, row, Sprite.doll_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                            tileMap[i][row] = '3';
                        }
                        case '4' -> {
                            enemy = new Minvo(i, row, Sprite.minvo_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                            tileMap[i][row] = '4';
                        }
                        case '5' -> {
                            enemy = new Kondoria(i, row, Sprite.kondoria_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                            tileMap[i][row] = '5';
                        }
                        case '6' -> {
                            enemy = new Pass(i, row, Sprite.pass_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                            tileMap[i][row] = '6';
                        }
                        case '7' -> {
                            enemy = new Ovape(i, row, Sprite.ovape_right1.getFxImage());
                            numberEnemies++;
                            enemies.add(enemy);
                            tileMap[i][row] = '7';
                        }
                        case 'b' -> {
                            object = new BombBuff(i, row, Sprite.powerup_bombs.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                            tileMap[i][row] = '*';
                        }
                        case 'f' -> {
                            object = new FlameBuff(i, row, Sprite.powerup_flames.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                            tileMap[i][row] = '*';
                        }
                        case 's' -> {
                            object = new SpeedBuff(i, row, Sprite.powerup_speed.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                            tileMap[i][row] = '*';
                        }
                        case 't' -> {
                            object = new DetonatorBuff(i, row, Sprite.powerup_detonator.getFxImage());
                            stillObjects.add(object);
                            object = new Brick(i, row, Sprite.brick.getFxImage());
                            stillObjects.add(object);
                            tileMap[i][row] = '*';
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
