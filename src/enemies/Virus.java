package enemies;

import managers.EnemyManager;

import static helpz.Constants.Enemies.VIRUS;

public class Virus extends Enemy{
    public Virus(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, VIRUS,enemyManager);
    }
}
