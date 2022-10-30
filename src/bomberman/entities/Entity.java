package bomberman.entities;

import bomberman.gameInteraction.Collision;
import bomberman.graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Sprite sprite;

    protected Image img;

    public boolean removed = false;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Entity(int xUnit, int yUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
    }

    public Entity() {

    }

    public void render(GraphicsContext graContext) {
        graContext.drawImage(img, x, y);
    }

    public boolean collide(Entity e) {
        return Collision.checkCollision(this, e);
    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTileX() {
        return (int) (x + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE;
    }

    public int getTileY() {
        return (int) (y + Sprite.DEFAULT_SIZE) / Sprite.SCALED_SIZE;
    }


    public Sprite getSprite() {
        return sprite;
    }

    public Image getImg() {
        return img;
    }

}
