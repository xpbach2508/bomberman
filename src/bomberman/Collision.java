package bomberman;

import bomberman.entities.Entity;
import bomberman.entities.buff.FlameBuff;

public class Collision {

    private static int leftA;
    private static int leftB;
    private static int rightA, rightB;
    private static int topA, topB;
    private static int bottomA, bottomB;

    public static boolean checkCollision(Entity e1, Entity e2) {
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
