package bomberman.entities;

import bomberman.gameInteraction.Collision;
import bomberman.entities.Enemies.Enemies;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static bomberman.Board.*;
import static bomberman.graphics.MapTiles.tileMap;

public class Bomb extends AnimatedEntity {

    private boolean movedOut = false;
    private int timerExplode = 150;
    private int timerRemove = 20;
    public boolean detonate;
    public boolean explodedBomb = false;

    private int bombLength;

    private ArrayList<Flame>[] flameList = new ArrayList[4];

    public Bomb(int xUnit, int yUnit, Image img, int bombLength, boolean detonate) {
        super(xUnit, yUnit, img);
        this.bombLength = bombLength;
        this.detonate = detonate;
        flameList[0] = new ArrayList<>();
        flameList[1] = new ArrayList<>();
        flameList[2] = new ArrayList<>();
        flameList[3] = new ArrayList<>();
        setMapChar('f');
    }

    private void createFlame(int direction, int length, ArrayList<Flame> flames) {
        boolean lastFlame = false;
        int limitLength = realDistance(direction, length);
        for (int i = 1; i <= limitLength; i++) {
            int xTile = this.getTileX();
            int yTile = this.getTileY();
            if (i == limitLength) {
                lastFlame = true;
            }
            Flame f;

            if (direction == 0) {
                f = new Flame(xTile, yTile - i, direction, lastFlame);
            }
            else if (direction == 1) {
                f = new Flame(xTile + i, yTile, direction, lastFlame);
            }
            else if (direction == 2) {
                f = new Flame(xTile, yTile + i, direction, lastFlame);
            }
            else {
                f = new Flame(xTile - i, yTile, direction, lastFlame);
            }
            flames.add(f);
        }
    }

    public void setMapChar(char ch) {
        for (int i = 1; i <= checkAIDistance(0, bombLength); i++ ) {
            tileMap[this.getTileX()][this.getTileY() - i] = ch;
        }
        for (int i = 1; i <= checkAIDistance(1, bombLength); i++ ) {
            tileMap[this.getTileX() + i][this.getTileY()] = ch;
        }
        for (int i = 1; i <= checkAIDistance(2, bombLength); i++ ) {
            tileMap[this.getTileX()][this.getTileY() + 1] = ch;
        }
        for (int i = 1; i <= checkAIDistance(3, bombLength); i++ ) {
            tileMap[this.getTileX() - i][this.getTileY()] = ch;
        }
    }

    private int realDistance(int direction, int length) {
        if (direction == 0) {
            for (int i = 1; i <= length; i++) {
                Entity block = getStillEntityAt(x, y - i * Sprite.SCALED_SIZE);
                if (block instanceof Wall) {
                    return i - 1;
                }
                if (block instanceof Brick) {
                    block.removed = true;
                    return i - 1;
                }
            }
        }
        else if (direction == 1) {
            for (int i = 1; i <= length; i++) {
                Entity block = getStillEntityAt(x + i * Sprite.SCALED_SIZE, y);
                if (block instanceof Wall) return i - 1;
                if (block instanceof Brick) {
                    block.removed = true;
                    return i - 1;
                }
            }
        }
        else if (direction == 2) {
            for (int i = 1; i <= length; i++) {
                Entity block = getStillEntityAt(x, y + i * Sprite.SCALED_SIZE);
                if (block instanceof Wall) return i - 1;
                if (block instanceof Brick) {
                    block.removed = true;
                    return i - 1;
                }
            }
        }
        else {
            for (int i = 1; i <= length; i++) {
                Entity block = getStillEntityAt(x - i * Sprite.SCALED_SIZE, y);
                if (block instanceof Wall) return i - 1;
                if (block instanceof Brick) {
                    block.removed = true;
                    return i - 1;
                }
            }
        }
        return length;
    }
    private int checkAIDistance(int direction, int length) {
        if (direction == 0) {
            for (int i = 1; i <= length; i++) {
                Entity block = getStillEntityAt(x, y - i * Sprite.SCALED_SIZE);
                if (block instanceof Wall) {
                    return i - 1;
                }
                if (block instanceof Brick) {
                    return i - 1;
                }
            }
        }
        else if (direction == 1) {
            for (int i = 1; i <= length; i++) {
                Entity block = getStillEntityAt(x + i * Sprite.SCALED_SIZE, y);
                if (block instanceof Wall) return i - 1;
                if (block instanceof Brick) {
                    return i - 1;
                }
            }
        }
        else if (direction == 2) {
            for (int i = 1; i <= length; i++) {
                Entity block = getStillEntityAt(x, y + i * Sprite.SCALED_SIZE);
                if (block instanceof Wall) return i - 1;
                if (block instanceof Brick) {
                    return i - 1;
                }
            }
        }
        else {
            for (int i = 1; i <= length; i++) {
                Entity block = getStillEntityAt(x - i * Sprite.SCALED_SIZE, y);
                if (block instanceof Wall) return i - 1;
                if (block instanceof Brick) {
                    return i - 1;
                }
            }
        }
        return length;
    }

    public void flameRender(GraphicsContext graContext) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < flameList[i].size(); j++) {
                flameList[i].get(j).render(graContext);
            }
        }
    }

    private void explodedBomb() {
        explodedBomb = true;
        music.playExplode();
        createFlame(0, bombLength, flameList[0]);
        createFlame(1, bombLength, flameList[1]);
        createFlame(2, bombLength, flameList[2]);
        createFlame(3, bombLength, flameList[3]);
    }

    @Override
    public void update() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < flameList[i].size(); j++)
                flameList[i].get(j).update();
        }
    }

    public void update(Bomber player, List<Enemies> enemy, List<Entity> stillObj, List<Bomb> bombList) {
        if (detonate) {
            if(explodedBomb) {
                explodedBomb();
                if (timerRemove > 0) {
                    timerRemove--;
                }
                else removed = true;
            }
        }
        else {
            if (timerExplode > 0) timerExplode--;
            else {
                if (!explodedBomb) {
                    explodedBomb();
                }
                //Flame update
                else update();
                if (timerRemove > 0) {
                    timerRemove--;
                }
                else removed = true;
            }
        }
        boolean isEnemyOut = true;
        for (Enemies e : enemy) {
            if(!checkMovedOut(e)) isEnemyOut = false;
        }
        if (isEnemyOut && checkMovedOut(player)) setMovedOut(true);
        if (explodedBomb) if (deadByFlame(player) && !player.immortal) player.removed = true;
        for(Enemies e : enemy)
            if (explodedBomb) {
                if (deadByFlame(e)) e.removed = true;
            }
        for (Bomb e : bombList) {
            if (e!= this && deadByFlame(e)) e.timerExplode = 0;
        }
        for (Entity e : stillObj) {
            if (e instanceof Brick && deadByFlame(e)) e.removed = true;
        }
        animate();
        chooseSprite();
        this.img = sprite.getFxImage();
    }

    private void chooseSprite() {
        if (!explodedBomb) sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 40);
        else {
            sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 25);
        }
    }

    private boolean deadByFlame(Entity e) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < flameList[i].size(); j++) {
                if (flameList[i].get(j).collide(e)) return true;
            }
        }
        return this.collide(e);
    }

    public boolean isMovedOut() {
        return movedOut;
    }

    public void setMovedOut(boolean movedOut) {
        this.movedOut = movedOut;
    }

    private boolean checkMovedOut(Entity e) {
        double topLeftX = (double) e.getX() + 1;
        double topLeftY = (double) e.getY() + 1;
        double topRightX = (double) e.getX() + (double) Sprite.SCALED_SIZE - 1;
        double topRightY = (double) e.getY() + 1;
        double botLeftX = (double) e.getX() + 1;
        double botLeftY = (double) e.getY() + (double) Sprite.SCALED_SIZE - 1;
        double botRightX = (double) e.getX() + (double) Sprite.SCALED_SIZE - 1;
        double botRightY = (double) e.getY() + (double) Sprite.SCALED_SIZE - 1;
        int tileTopLx = (int) topLeftX / Sprite.SCALED_SIZE;
        int tileTopLy = (int) topLeftY / Sprite.SCALED_SIZE;
        int tileTopRx = (int) topRightX / Sprite.SCALED_SIZE;
        int tileTopRy = (int) topRightY / Sprite.SCALED_SIZE;
        int tileBotLx = (int) botLeftX / Sprite.SCALED_SIZE;
        int tileBotLy = (int) botLeftY / Sprite.SCALED_SIZE;
        int tileBotRx = (int) botRightX / Sprite.SCALED_SIZE;
        int tileBotRy = (int) botRightY / Sprite.SCALED_SIZE;
        if ((tileTopLx != this.getTileX() || tileTopLy != this.getTileY())
                && (tileBotRx != this.getTileX() || tileBotRy != this.getTileY())
                && (tileTopRx != this.getTileX() || tileTopRy != this.getTileY())
                && (tileBotLx != this.getTileX() || tileBotLy != this.getTileY())) {
            return true;
        }
        return false;
    }

    @Override
    public void render(GraphicsContext graContext) {
        graContext.drawImage(this.img, this.x, this.y);
        flameRender(graContext);
    }

    @Override
    public boolean collide(Entity e) {
        return Collision.checkCollision(this, e);
    }
}
