package managers;

import enemies.Enemy;
import helpz.Utilz;
import helpz.loadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Towers.*;

public class TowerManager {
    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;
    public TowerManager(Playing playing){
        this.playing = playing;
        loadTowerImgs();
    }

    private void loadTowerImgs() {
        BufferedImage atlas = loadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[3];
        for(int i=0;i<3;i++)
            towerImgs[i] = atlas.getSubimage(i*32,32,32,32);
    }

    public void draw(Graphics g){
        for(Tower t: towers)
            g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(),null );
    }
    public void update() {
        for(Tower t : towers) {
            t.update();
            attackEnemyInRange(t);
        }
    }

    private void attackEnemyInRange(Tower t) {
            for(Enemy e: playing.getEnemyManager().getEnemies()){
                if(e.isAlive()) {
                    if(isEnemyInRange(t,e)){
                        if(t.isCDOver()){
                            playing.shootEnemy(t,e);
                            t.resetCD();
                        }
                    }
                }
            }
    }

    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = Utilz.GetHypoDist(t.getX(),t.getY(),e.getX(),e.getY());
        return range < t.getRange();
    }
    public BufferedImage[] getTowerImgs() {
        return towerImgs;
    }

    public Tower getTowerAt(int x, int y) {
        for(Tower t:towers)
            if(t.getX()==x&&t.getY()==y)
                return t;
        return null;
    }

    public void addTower(Tower selectedTower, int xPos,int yPos) {
        towers.add(new Tower(xPos,yPos,towerAmount++,selectedTower.getTowerType()));
    }

    public void removeTower(Tower displayedTower) {
        for(int i=0;i<towers.size();i++)
            if(towers.get(i).getId()== displayedTower.getId())
                towers.remove(i);
    }

    public void upgradeTower(Tower displayedTower) {
        for(Tower t:towers)
            if(t.getId()== displayedTower.getId())
                t.upgradeTower();
    }

    public void reset() {
        towers.clear();
        towerAmount=0;
    }
}
