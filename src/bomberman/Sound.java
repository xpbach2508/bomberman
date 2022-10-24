package bomberman;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
public class Sound {
    private final String playing = "D:\\bomberman\\res\\sounds\\game_sound.mp3";
    private final String menu = "D:\\bomberman\\res\\sounds\\menu_sound.mp3";
    private final String buff = "D:\\bomberman\\res\\sounds\\buff.mp3";
    private final String enter_portal = "D:\\bomberman\\res\\sounds\\enter_portal.mp3";
    private final String explode = "D:\\bomberman\\res\\sounds\\explode.mp3";
    private final String game_over = "D:\\bomberman\\res\\sounds\\game_over.mp3";
    private final String level_up = "D:\\bomberman\\res\\sounds\\level_up.mp3";
    private final String move_vertical = "D:\\bomberman\\res\\sounds\\move_right.mp3";
    private final String move_horizontal = "D:\\bomberman\\res\\sounds\\move_top.mp3";
    private final String plant_bomb = "D:\\bomberman\\res\\sounds\\plant_bomb.mp3";
    private final String dead = "D:\\bomberman\\res\\sounds\\player_death.mp3";
    private final String load_scene = "D:\\bomberman\\res\\sounds\\stage_scene.mp3";
    private final String youWin = "D:\\bomberman\\res\\sounds\\youWin.mp3";

    private void play(String sound) {
            Media media = new Media(new File(sound).toURI().toString());
            MediaPlayer med = new MediaPlayer(media);
            med.setVolume(1);
            med.setCycleCount(1);
            med.seek(Duration.ZERO);
            med.play();
    }

    public void playGameMusic() {
        play(playing);
    }

    public void playMenuMusic() {
        play(menu);
    }

    public void playBuff() {
        play(buff);
    }

    public void playPortal() {
        play(enter_portal);
    }

    public void playExplode() {
        play(explode);
    }

    public void playOver() {
        play(game_over);
    }

    public void playLevelUp() {
        play(level_up);
    }

    public void playPlantBomb() {
        play(plant_bomb);
    }

    public void playDead() {
        play(dead);
    }

    public void playLoadScene() {
        play(load_scene);
    }

    public void playWin() {
        play(youWin);
    }

    public void playMoveX() {
        play(move_horizontal);
    }

    public void playMoveY() {
        play(move_vertical);
    }

    public void stop() {
    }
}
