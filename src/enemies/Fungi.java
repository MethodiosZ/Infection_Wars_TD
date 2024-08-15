package enemies;

import managers.EnemyManager;

import static helpz.Constants.Enemies.FUNGI;

public class Fungi extends Enemy{
    public Fungi(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, FUNGI,enemyManager);
    }
}
