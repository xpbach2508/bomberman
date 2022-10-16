package bomberman;

import bomberman.entities.Bomb;
import bomberman.entities.Entity;
import bomberman.entities.buff.FlameBuff;

public class Collision {

    private static int leftA;
    private static int leftB;
    private static int rightA, rightB;
    private static int topA, topB;
    private static int bottomA, bottomB;

    public static boolean checkCollision(Entity e1, Entity e2) {
        int distance = 0; int notDistance = 10;
        if (e1 instanceof Bomb) {
            distance = 10;
            notDistance = 0;
        }
        int x1 = e1.getX();
        int y1 = e1.getY();
        int x2 = e2.getX();
        int y2 = e2.getY();
        leftA = x1;
        leftB = x2;
        topA = y1;
        topB = y2;
        //notDistance là khi check những player như bomber hay enemy sẽ ko tính 2 phần rìa 2 bên của nó, sẽ thật hơn. Chú ý e2 là player.
        rightA = x1 + (int) e1.getImg().getWidth();
        rightB = x2 + (int) e2.getImg().getWidth() - notDistance;
        bottomA = y1 + (int) e1.getImg().getHeight();
        bottomB = y2 + (int) e2.getImg().getHeight() - notDistance;
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
