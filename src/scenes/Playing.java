package scenes;

import Main.Game;
import enemies.Enemy;
import helpz.Constants;
import helpz.loadSave;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Tiles.*;

public class Playing extends GameScene implements SceneMethods{

    private int[][] lvl;
    private ActionBar actionBar;
    private int mouseX,mouseY;
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projectileManager;
    private WaveManager waveManager;
    private PathPoint start,end;
    private Tower selectedTower;
    private int goldTick;
    private boolean gamePaused;
    public Playing(Game game) {
        super(game);

        loadDefaultLevel();
        actionBar = new ActionBar(0,640,640,160,this);
        enemyManager = new EnemyManager(this,start,end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
    }

    private void loadDefaultLevel() {
        lvl = loadSave.GetLevelData();
        ArrayList<PathPoint> points = loadSave.GetLevelPathPoints();
        start = points.get(0);
        end = points.get(1);
    }

    @Override
    public void render(Graphics g) {
        drawLevel(g);
        actionBar.draw(g);
        enemyManager.draw(g);
        towerManager.draw(g);
        projectileManager.draw(g);
        drawSelectedTower(g);
        drawHighlight(g);
        drawWaveInfo(g);
    }
    private void drawWaveInfo(Graphics g) {

    }
    private void drawHighlight(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect(mouseX,mouseY,32,32);
    }

    private void drawSelectedTower(Graphics g) {
        if(selectedTower!=null)
            g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()],mouseX,mouseY,null );
    }

    public void update(){
        if(!gamePaused) {
            waveManager.update();
            goldTick++;
            if (goldTick % (60 * 4) == 0)
                actionBar.addGold(1);
            if (isWaveFinished()) {
                if (isMoreWaves()) {
                    waveManager.startWaveTimer();
                    if (isWaveTimerOver()) {
                        waveManager.incrWaveIndex();
                        enemyManager.getEnemies().clear();
                        waveManager.resetEnemyIndex();
                    }
                }
            }
            if (isNextEnemyTime()) {
                spawnEnemy();
            }
            enemyManager.update();
            towerManager.update();
            projectileManager.update();
        }
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimerOver();
    }

    private boolean isMoreWaves() {
        return waveManager.isMoreWaves();
    }

    private boolean isWaveFinished() {
        if(waveManager.isWaveActive())
            return false;
        for(Enemy e:enemyManager.getEnemies()){
            if(e.isAlive())
                return false;
        }
        return true;
    }

    private void spawnEnemy() {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
    }

    private boolean isNextEnemyTime() {
        if(waveManager.isNextSpawnTime()){
            if(waveManager.isWaveActive())
                return true;
        }
        return false;
    }
    public void setLevel(int[][] lvl){
        this.lvl = lvl;
    }
    private void drawLevel(Graphics g){
        for(int y=0;y< lvl.length;y++){
            for(int x=0;x< lvl.length;x++){
                int id = lvl[y][x];
                g.drawImage(getSprite(id),x*32,y*32,null);
            }
        }
    }
    @Override
    public void mouseClicked(int x, int y) {
        if(y>=640)
            actionBar.mouseClicked(x,y);
        else {
            if(selectedTower!=null){
                if(isTileGrass(mouseX,mouseY)){
                    if(getTowerAt(mouseX,mouseY)==null){
                        towerManager.addTower(selectedTower,mouseX,mouseY);
                        removeGold(selectedTower.getTowerType());
                        selectedTower = null;

                    }
                }
            } else {
                Tower t = getTowerAt(mouseX,mouseY);
                actionBar.displayTower(t);
            }
        }
    }

    private void removeGold(int towerType) {
        actionBar.payForTower(towerType);
    }
    public void rewardPlayer(int enemyType){
        actionBar.addGold(Constants.Enemies.GetGold(enemyType));
    }
    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x,y);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y/32][x/32];
        int tileType = getGame().getTileManager().getTile(id).getTileType();
        return tileType==TISSUE_TILE;
    }

    private BufferedImage getSprite(int spriteID){
        return getGame().getTileManager().getSprite(spriteID);
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y>=640){
            actionBar.mouseMoved(x,y);
        } else {
            mouseX=x/32*32;
            mouseY=y/32*32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y>=640){
            actionBar.mousePressed(x,y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x,y);
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
    public int getTileType(int x, int y) {
        int xCord = x/32;
        int yCord = y/32;
        if(xCord<0||xCord>19||yCord<0||yCord>19)
            return 0;
        int id = lvl[y/32][x/32];
        return getGame().getTileManager().getTile(id).getTileType();
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public void KeyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
            selectedTower =  null;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t,e);
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public void removeTower(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
    }

    public void upgradeTower(Tower displayedTower) {
        towerManager.upgradeTower(displayedTower);
    }
    public void removeLife() {
        actionBar.removeLife();
    }
    public void reset() {
        actionBar.reset();
        enemyManager.reset();
        towerManager.reset();
        projectileManager.reset();
        waveManager.reset();
        mouseX=0;
        mouseY=0;
        selectedTower=null;
        goldTick=0;
        gamePaused=false;
    }
}
