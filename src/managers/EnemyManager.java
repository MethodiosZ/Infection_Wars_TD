package managers;

import enemies.*;
import helpz.loadSave;
import objects.PathPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static helpz.Constants.Directions.*;
import static helpz.Constants.Tiles.*;
import static helpz.Constants.Enemies.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] enemyImgs;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private PathPoint start,end;
    private int HPBarWidth = 24;
    private BufferedImage slowEffect;
    public EnemyManager(Playing playing, PathPoint start,PathPoint end){
        this.playing = playing;
        this.start = start;
        this.end = end;
        enemyImgs = new BufferedImage[4];
        loadEnemyImgs();
        loadEffectImg();
    }

    private void loadEffectImg() {
         slowEffect = loadSave.getSpriteAtlas().getSubimage(6*32,32,32,32);
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = loadSave.getSpriteAtlas();
        for(int i = 0;i<4;i++)
            enemyImgs[i] = atlas.getSubimage((i+6)*32,0,32,32);
    }

    public void addEnemy(int enemyType){
        int x=start.getxCord()*32;
        int y=start.getyCord()*32;
        switch (enemyType){
            case FUNGI:
                enemies.add(new Fungi(x,y,0,this));
                break;
            case BACTERIA:
                enemies.add(new Bacteria(x,y,0,this));
                break;
            case VIRUS:
                enemies.add(new Virus(x,y,0,this));
                break;
            case PARASITE:
                enemies.add(new Parasite(x,y,0,this));
                break;
        }

    }

    public void update(){
        for(Enemy e: enemies){
            if(e.isAlive())
                updateEnemyMove(e);
        }
    }
    private void updateEnemyMove(Enemy e) {
        if(e.getLastDir()==-1)
            setNewDir(e);
        int newX = (int)(e.getX() + getSpeedX(e.getLastDir(),e.getEnemyType()));
        int newY = (int)(e.getY() + getSpeedY(e.getLastDir(),e.getEnemyType()));
        if(getTileType(newX,newY)==VEIN_TILE){
            e.move(GetSpeed(e.getEnemyType()),e.getLastDir());
        } else if (isAtEnd(e)) {
            e.kill();
            playing.removeLife();
        } else {
            setNewDir(e);
        }
    }

    private void setNewDir(Enemy e) {
        int dir = e.getLastDir();
        int xCord = (int)(e.getX()/32);
        int yCord = (int)(e.getY()/32);
        fixEnemyOffset(e,dir,xCord,yCord);
        if(isAtEnd(e))
            return;
        if(dir==LEFT||dir==RIGHT){
            int newY = (int)(e.getY() + getSpeedY(UP,e.getEnemyType()));
            if(getTileType((int)e.getX(),newY)==VEIN_TILE){
                e.move(GetSpeed(e.getEnemyType()),UP);
            } else {
                e.move(GetSpeed(e.getEnemyType()),DOWN);
            }
        } else {
            int newX = (int)(e.getX() + getSpeedX(RIGHT,e.getEnemyType()));
            if(getTileType(newX,(int)e.getY())==VEIN_TILE){
                e.move(GetSpeed(e.getEnemyType()),RIGHT);
            } else {
                e.move(GetSpeed(e.getEnemyType()),LEFT);
            }
        }
    }

    private void fixEnemyOffset(Enemy e, int dir, int xCord, int yCord) {
        switch (dir){
            case RIGHT:
                if(xCord<19)
                    xCord++;
                break;
            case DOWN:
                if (yCord<19)
                    yCord++;
                break;
        }
        e.setPos(xCord*32,yCord*32);
    }

    private boolean isAtEnd(Enemy e) {
        if(e.getX()==end.getxCord()*32)
            if (e.getY()==end.getyCord()*32)
                return true;
        return false;
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x,y);
    }

    private float getSpeedY(int dir, int enemyType) {
        if(dir == UP)
            return -GetSpeed(enemyType);
        else if (dir == DOWN) {
            return GetSpeed(enemyType)+32;
        }
        return 0;
    }

    private float getSpeedX(int dir,int enemyType) {
        if(dir == LEFT)
            return -GetSpeed(enemyType);
        else if (dir == RIGHT) {
            return GetSpeed(enemyType)+32;
        }
        return 0;
    }

    public void draw(Graphics g){
        for(Enemy e: enemies) {
            if(e.isAlive()) {
                drawEnemy(g, e);
                drawHealthBar(g,e);
                drawEffects(g,e);
            }
        }
    }

    private void drawEffects(Graphics g, Enemy e) {
        if(e.isSlowed())
            g.drawImage(slowEffect,(int)e.getX(),(int)e.getY(),null);
    }

    private void drawHealthBar(Graphics g, Enemy e) {
        g.setColor(Color.green);
        g.fillRect((int) e.getX()+16-(getNewBarWidth(e)/2), (int)e.getY()-5,getNewBarWidth(e),3);
    }

    private int getNewBarWidth(Enemy e){
        return (int)(HPBarWidth * e.getHealthBarFloat());
    }

    private void drawEnemy(Graphics g, Enemy e) {
        g.drawImage(enemyImgs[e.getEnemyType()],(int)e.getX(),(int)e.getY(),null);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }
    public int getAliveEnemies() {
        int size=0;
        for(Enemy e: enemies)
            if(e.isAlive())
                size++;
        return size;
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }

    public void reset() {
        enemies.clear();
    }
}
