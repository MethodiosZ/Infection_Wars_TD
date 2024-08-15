package enemies;

import helpz.Constants;
import managers.EnemyManager;

import javax.swing.*;
import java.awt.*;
import static helpz.Constants.Directions.*;

public abstract class Enemy {
    protected float x,y;
    protected Rectangle bounds;
    protected int health;
    protected int maxHealth;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive = true;
    protected int slowTickLimit=120;
    protected int slowTick=slowTickLimit;
    protected EnemyManager enemyManager;
    public Enemy(float x,float y,int ID,int enemyType,EnemyManager enemyManager){
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemyManager = enemyManager;
        bounds = new Rectangle((int)x,(int)y,32,32);
        lastDir = -1;
        setStartHealth();
    }

    private void setStartHealth(){
        health = Constants.Enemies.GetStartHealth(enemyType);
        maxHealth = health;
    }
    public void move(float speed, int dir){
        lastDir = dir;
        if(slowTick<slowTickLimit)
            slowTick++;
        switch (dir){
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }
        updateHitBox();
    }

    private void updateHitBox() {
        bounds.x = (int)x;
        bounds.y = (int)y;
    }

    public void setPos(int x,int y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHealthBarFloat(){
        return (float)health/maxHealth;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getID() {
        return ID;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDir() {
        return lastDir;
    }

    public void hurt(int dmg) {
        this.health -= dmg;
        if(health<=0) {
            alive = false;
            enemyManager.rewardPlayer(enemyType);
        }
    }

    public boolean isAlive(){
        return alive;
    }

    public void slow() {
        slowTick=0;
    }
    public boolean isSlowed(){
        return slowTick<slowTickLimit;
    }
    public void kill() {
        alive = false;
        health = 0;
    }
}
