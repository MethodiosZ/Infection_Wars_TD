package enemies;

import managers.EnemyManager;

import static helpz.Constants.Enemies.BACTERIA;

public class Bacteria extends Enemy{

    public Bacteria(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, BACTERIA,enemyManager);
    }
}
