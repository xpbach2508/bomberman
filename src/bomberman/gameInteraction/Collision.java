package bomberman.gameInteraction;

import bomberman.entities.Bomb;
import bomberman.entities.Entity;
import bomberman.entities.Flame;

public class Collision {

    public static boolean checkCollision(Entity e1, Entity e2) {
        int lengthCut = 10;
        int realCondition = 0;
        if (e1 instanceof Bomb) {
            lengthCut = 0;
        }
        if (e1 instanceof Flame) realCondition = 5;
        int x1 = e1.getX();
        int y1 = e1.getY();
        int x2 = e2.getX();
        int y2 = e2.getY();
        int leftB = x2 + lengthCut;
        int topB = y2 + lengthCut;
        //lengthCut là khi check những player như bomber hay enemy sẽ ko tính 2 phần rìa 2 bên của nó, sẽ thật hơn. Chú ý e2 là player.
        int rightA = x1 + (int) e1.getImg().getWidth();
        int rightB = x2 + (int) e2.getImg().getWidth() - lengthCut - realCondition;
        int bottomA = y1 + (int) e1.getImg().getHeight();
        int bottomB = y2 + (int) e2.getImg().getHeight() - lengthCut;
        if ( bottomA <= topB)
        {
            return false;
        }

        if ( y1 >= bottomB)
        {
            return false;
        }

        if ( rightA <= leftB)
        {
            return false;
        }

        return x1 < rightB;
    }

}
