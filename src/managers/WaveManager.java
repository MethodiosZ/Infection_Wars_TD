package managers;

import events.Wave;
import scenes.Playing;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {
    Playing playing;
    private ArrayList<Wave> waves = new ArrayList<>();
    private int enemySpawnTickLimit = 60 * 1;
    private int enemySpawnTick = enemySpawnTickLimit;
    private int enemyIndex,waveIndex=0;
    private int waveTickLimit = 60 * 5;
    private int waveTick = 0;
    private boolean waveStartTimer;
    public WaveManager(Playing playing){
        this.playing = playing;
        createWaves();
    }
    public void update(){
        if(enemySpawnTick < enemySpawnTickLimit)
            enemySpawnTick++;
        if(waveStartTimer){
            waveTick++;
        }
    }
    public void incrWaveIndex(){
        waveIndex++;
        waveTick=0;
        waveStartTimer=false;
    }
    public int getNextEnemy(){
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }
    private void createWaves() {
        waves.add(new Wave(new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(0,0,0,0,0,1,1,1,1,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(0,0,1,1,1,1,1,1,1,2,1,1,1,2,2))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1,2,2,2,0,0,1,1,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(1,1,1,2,2,2,2,2,2,2,2,2,3,0,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(1,2,2,2,2,2,2,2,3,3,2,2,2,3,3,0,0,1,1,1))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(2,2,2,2,2,2,3,3,3,3,2,2,2,3,3,3,3,3,2,2))));
        waves.add(new Wave(new ArrayList<>(Arrays.asList(3,3,2,1,2,3,2,0,3,2,2,2,2,2,2,3,3,3,1,1,2,2,2,3,2))));
    }
    public ArrayList<Wave> getWaves() {
        return waves;
    }
    public boolean isNextSpawnTime() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }
    public boolean isWaveActive(){
        return enemyIndex<waves.get(waveIndex).getEnemyList().size();
    }
    public boolean isMoreWaves() {
        return waveIndex+1 < waves.size();
    }
    public void startWaveTimer() {
        waveStartTimer = true;
    }
    public boolean isWaveTimerOver() {
       return waveTick>=waveTickLimit;
    }
    public void resetEnemyIndex() {
        enemyIndex=0;
    }
    public int getWaveIndex() {
        return waveIndex;
    }
    public float getTimeLeft(){
        float tickLeft = waveTickLimit-waveTick;
        return tickLeft/60.0f;
    }
    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }
    public void reset() {
        waves.clear();
        createWaves();
        enemyIndex=0;
        waveIndex=0;
        waveStartTimer=false;
        waveTick=0;
        enemySpawnTick=enemySpawnTickLimit;
    }
}
