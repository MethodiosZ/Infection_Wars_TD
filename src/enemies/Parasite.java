package enemies;

import managers.EnemyManager;

import static helpz.Constants.Enemies.PARASITE;

public class Parasite extends Enemy{
    public Parasite(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, PARASITE,enemyManager);

    }
}
