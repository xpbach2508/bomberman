package bomberman.sfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Sound {
    private final String playing_path = "D:\\bomberman\\res\\sounds\\game_sound.mp3";
    private final String menu_path = "D:\\bomberman\\res\\sounds\\menu_sound.mp3";
    private final String buff_path = "D:\\bomberman\\res\\sounds\\buff.wav";
    private final String enter_portal_path = "D:\\bomberman\\res\\sounds\\enter_portal.wav";
    private final String explode_path = "D:\\bomberman\\res\\sounds\\explode (2).wav";
    private final String game_over_path = "D:\\bomberman\\res\\sounds\\game_over.mp3";
    private final String move_vertical_path = "D:\\bomberman\\res\\sounds\\move_y.wav";
    private final String move_horizontal_path = "D:\\bomberman\\res\\sounds\\move_x.wav";
    private final String plant_bomb_path = "D:\\bomberman\\res\\sounds\\plant_bomb.wav";
    private final String dead_path = "D:\\bomberman\\res\\sounds\\player_death.wav";
    private final String load_scene_path = "D:\\bomberman\\res\\sounds\\stage_scene.wav";
    private final String youWin_path = "D:\\bomberman\\res\\sounds\\youWin.mp3";

    private final Media playing_media = new Media(new File(playing_path).toURI().toString());
    private final Media menu_media = new Media(new File(menu_path).toURI().toString());
    private final Media buff_media = new Media(new File(buff_path).toURI().toString());
    private final Media enter_portal_media = new Media(new File(enter_portal_path).toURI().toString());
    private final Media explode_media = new Media(new File(explode_path).toURI().toString());
    private final Media game_over_media = new Media(new File(game_over_path).toURI().toString());
    private final Media move_vertical_media = new Media(new File(move_vertical_path).toURI().toString());
    private final Media move_horizontal_media = new Media(new File(move_horizontal_path).toURI().toString());
    private final Media plant_bomb_media = new Media(new File(plant_bomb_path).toURI().toString());
    private final Media dead_media = new Media(new File(dead_path).toURI().toString());
    private final Media load_scene_media = new Media(new File(load_scene_path).toURI().toString());
    private final Media youWin_media = new Media(new File(youWin_path).toURI().toString());

    private final MediaPlayer youWin = new MediaPlayer(youWin_media);
    private final MediaPlayer playing = new MediaPlayer(playing_media);
    private final MediaPlayer menu = new MediaPlayer(menu_media);
    private final MediaPlayer buff = new MediaPlayer(buff_media);
    private final MediaPlayer enter_portal = new MediaPlayer(enter_portal_media);
    private final MediaPlayer plant_bomb = new MediaPlayer(plant_bomb_media);
    private final MediaPlayer explode = new MediaPlayer(explode_media);
    private final MediaPlayer move_vertical = new MediaPlayer(move_vertical_media);
    private final MediaPlayer move_horizontal = new MediaPlayer(move_horizontal_media);
    private final MediaPlayer load_scene = new MediaPlayer(load_scene_media);
    private final MediaPlayer game_over = new MediaPlayer(game_over_media);
    private final MediaPlayer dead = new MediaPlayer(dead_media);


    public boolean pauseMusic = false;
    public boolean pauseEffect = false;

    public void playGameMusic() {
            playing.setVolume(0.5);
            playing.seek(Duration.ZERO);
            playing.setCycleCount(-1);
            playing.play();
    }

    public void stopGameMusic() {
        playing.stop();
    }

    public void playMenuMusic() {
         {
            menu.seek(Duration.ZERO);
            menu.setCycleCount(-1);
            menu.play();
        }
    }

    public void stopMenuMusic() {
        menu.stop();
    }

    public void playBuff() {
         {
            buff.seek(Duration.ZERO);
            buff.play();
        }
    }

    public void playPlantBomb() {
         {
            plant_bomb.seek(Duration.ZERO);
            plant_bomb.play();
        }
    }

    public void playPortal() {
         {
            enter_portal.seek(Duration.ZERO);
            enter_portal.play();
        }
    }

    public void playExplode() {
         {
            explode.seek(Duration.ZERO);
            explode.play();
        }
    }

    public void playGameOver() {
         {
            game_over.seek(Duration.ZERO);
            game_over.play();
        }
    }

    public void stopGameOver() {
        game_over.stop();
    }

    public void playMoveY() {
         {
            move_horizontal.stop();
            move_vertical.setOnEndOfMedia(() -> move_vertical.seek(Duration.ZERO));
            move_vertical.play();
        }
    }

    public void playMoveX() {
         {
            move_vertical.stop();
            move_horizontal.setOnEndOfMedia(() -> move_horizontal.seek(Duration.ZERO));
            move_horizontal.play();
        }
    }

    public void stopMove() {
        move_horizontal.stop();
        move_vertical.stop();
    }

    public void playDead() {
         {
            dead.seek(Duration.ZERO);
            dead.play();
        }
    }

    public void stopDead() {
        dead.stop();
    }

    public void playLoadScene() {
         {
            load_scene.seek(Duration.ZERO);
            load_scene.play();
        }
    }

    public void stopLoadScene() {
        load_scene.stop();
    }

    public void playWin() {
         {
            youWin.seek(Duration.ZERO);
            youWin.play();
        }
    }

    public void stopWin() {
        youWin.stop();
    }

    public void muteMusic() {
        pauseMusic = true;

        playing.setMute(true);
        menu.setMute(true);
        load_scene.setMute(true);
        game_over.setMute(true);
        youWin.setMute(true);
    }

    public void unmuteMusic() {
        pauseMusic = false;

        playing.setMute(false);
        menu.setMute(false);
        load_scene.setMute(false);
        game_over.setMute(false);
        youWin.setMute(false);
    }

    public void muteEffect() {
        pauseEffect = true;

        buff.setMute(true);
        enter_portal.setMute(true);
        plant_bomb.setMute(true);
        explode.setMute(true);
        move_vertical.setMute(true);
        move_horizontal.setMute(true);
        dead.setMute(true);
    }

    public void unmuteEffect() {
        pauseEffect = false;

        buff.setMute(false);
        enter_portal.setMute(false);
        plant_bomb.setMute(false);
        explode.setMute(false);
        move_vertical.setMute(false);
        move_horizontal.setMute(false);
        dead.setMute(false);
    }
}
