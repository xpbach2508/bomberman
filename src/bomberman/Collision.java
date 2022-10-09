package bomberman;

import bomberman.entities.Entity;
import bomberman.entities.buff.FlameBuff;

public class Collision {

    private int leftA;
    private int leftB;
    private int rightA, rightB;
    private int topA, topB;
    private int bottomA, bottomB;

    public boolean checkCollision(Entity e1, Entity e2) {
        int x1 = e1.getX();
        int y1 = e1.getY();
        int x2 = e2.getX();
        int y2 = e2.getY();
        leftA = x1;
        leftB = x2;
        topA = y1;
        topB = y2;
        rightA = x1 + (int) e1.getImg().getWidth();
        rightB = x2 + (int) e2.getImg().getWidth();
        bottomA = y1 + (int) e1.getImg().getHeight();
        bottomB = y2 + (int) e2.getImg().getHeight();
        /*if (e1.getImg() == null) System.out.println("1");
        rightA = x1 + 16;
        rightB = x2 + 16;
        bottomA = y1 + 16;
        bottomB = y2 + 16;*/
        if ( bottomA <= topB )
        {
            return false;
        }

        if ( topA >= bottomB )
        {
            return false;
        }

        if ( rightA <= leftB )
        {
            return false;
        }

        if ( leftA >= rightB )
        {
            return false;
        }
        return true;
    }
}
