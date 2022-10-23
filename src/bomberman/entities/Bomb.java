package bomberman.entities;

import bomberman.gameInteraction.Collision;
import bomberman.entities.Enemies.Enemies;
import bomberman.entities.tile.Brick;
import bomberman.entities.tile.Wall;
import bomberman.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.font.ImageGraphicAttribute;
import java.util.ArrayList;
import java.util.List;

import static bomberman.BombermanGame.getStillEntityAt;

public class Bomb extends AnimatedEntity {

    private boolean movedOut = false;
    private int timerExplode = 150;
    private int timerRemove = 20;
    private boolean explodedBomb = false;

    private int bombLength;

    private ArrayList<Flame>[] flameList = new ArrayList[4];

    public Bomb(int xUnit, int yUnit, Image img, int bombLength) {
        super(xUnit, yUnit, img);
        this.bombLength = bombLength;
        flameList[0] = new ArrayList<Flame>();
        flameList[1] = new ArrayList<Flame>();
        flameList[2] = new ArrayList<Flame>();
        flameList[3] = new ArrayList<Flame>();
    }

    private void createFlame(int direction, int length, ArrayList<Flame> flames) {
        boolean lastFlame = false;
        int limitLength = realDistance(direction, length);
        for (int i = 1; i <= limitLength; i++) {
            if (i == limitLength) {
                lastFlame = true;
            }
            Flame f;
            int xTile = this.getTileX();
            int yTile = this.getTileY();

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
                    System.out.println(i + "1");
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
                    System.out.println(i + "2");
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
                    System.out.println(i + "3");
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

    public void update(List<Entity> player, List<Entity> stillObj, List<Bomb> bombList) {
        if (timerExplode > 0) timerExplode--;
        else {
            if (!explodedBomb) {
                explodedBomb();
            }
            //Flame update
            else update();
            if (timerRemove > 0) timerRemove--;
            else removed = true;
        }
        for (Entity e : player) {
            if (e instanceof Bomber) {
                double topLeftX = (double) e.getX() + 2;
                double topLeftY = (double) e.getY() + 2;
                double topRightX = (double) e.getX() + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                double topRightY = (double) e.getY() + 2;
                double botLeftX = (double) e.getX() + 2;
                double botLeftY = (double) e.getY() + (double) Sprite.SCALED_SIZE - 2;
                double botRightX = (double) e.getX() + (double) Sprite.SCALED_SIZE * 3 / 4 - 2;
                double botRightY = (double) e.getY() + (double) Sprite.SCALED_SIZE - 2;
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
                    this.setMovedOut(true);
                }
            }
            if (explodedBomb && (e instanceof Bomber || e instanceof Enemies)) {
                if (deadByFlame(e)) e.removed = true;
            }
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
        if (this.collide(e)) return true;
        return false;
    }

    public boolean isMovedOut() {
        return movedOut;
    }

    public void setMovedOut(boolean movedOut) {
        this.movedOut = movedOut;
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
