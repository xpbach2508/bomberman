package bomberman.entities.Enemies.AI;

import bomberman.entities.Bomber;
import bomberman.entities.Enemies.Enemies;
import bomberman.graphics.Sprite;

import java.util.*;

import static bomberman.graphics.MapTiles.tileMap;

public class EnemyAI {
    protected Random random = new Random();
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    int maxCol = Sprite.GameWidth;
    int maxRow = Sprite.GameHeight;
    Enemies enemy;
    public EnemyAI(Bomber player, Enemies e) {
        this.enemy = e;
        initNodes();
    }
    public void initNodes() {
        node = new Node[Sprite.GameWidth][Sprite.GameHeight];
        for (int i = 0; i < maxCol; i++)
            for (int j = 0; j < maxRow; j++) {
                node[i][j] = new Node(i, j);
            }
    }
    public void resetNodes() {
        for (int i = 0; i < maxCol; i++)
            for (int j = 0; j < maxRow; j++) {
                node[i][j].open = false;
                node[i][j].checked = false;
                node[i][j].solid = false;
            }
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();
        this.startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);
        for (int i = 0; i < maxCol; i++)
            for (int j = 0; j < maxRow; j++) {
                for (int x = 0; x < enemy.goThrough.size(); x++)
                if (tileMap[i][j] ==  enemy.goThrough.get(x)) node[i][j].solid = true;
                getCost(node[i][j]);
            }
    }
    public void getCost(Node node) {
        int xDis = Math.abs(node.col - startNode.col);
        int yDis = Math.abs(node.row - startNode.row);
        node.gCost = xDis + yDis;
        xDis = Math.abs(node.col - goalNode.col);
        yDis = Math.abs(node.row - goalNode.row);
        node.hCost = xDis + yDis;
        node.fCost = node.gCost + node.hCost;
    }
    public boolean search() {
        while(!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;
            currentNode.checked = true;
            openList.remove(currentNode);
            if (row - 1 >= 0) openNode(node[col][row - 1]);
            if (col - 1 >= 0) openNode(node[col-1][row]);
            if (row + 1 < maxRow) openNode(node[col][row + 1]);
            if (col + 1 < maxCol) openNode(node[col + 1][row]);
            int bestNodeId = 0;
            int bestNodefCost = 999;
            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeId = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeId).gCost) {
                        bestNodeId = i;
                    }
                }
            }
            if (openList.size() == 0) {
                break;
            }
            currentNode = openList.get(bestNodeId);
            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
            }
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    public void trackPath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);
            current = current.parent;
        }
    }
}
