package objects;

import helpz.Constants;

import static helpz.Constants.Towers.*;

public class Tower {
    private int x,y,id,TowerType,cdTick=0,dmg;
    private float range,cd;
    private int tier;
    public Tower(int x,int y,int id,int TowerType){
        this.x = x;
        this.y = y;
        this.id = id;
        this.TowerType = TowerType;
        tier=1;
        setDefaultDmg();
        setDefaultRange();
        setDefaultCD();
    }

    public void update(){
        cdTick++;
    }
    public void upgradeTower(){
        this.tier++;
        switch (TowerType){
            case WBC:
                dmg+=2;
                range+=15;
                cd-=5;
                break;
            case ANTISEPTIC:
                dmg+=1;
                range+=10;
                cd-=5;
                break;
            case VACCINE:
                dmg+=10;
                range+=20;
                cd-=15;
                break;
        }
    }
    private void setDefaultCD() {
        cd = Constants.Towers.getStartCD(TowerType);
    }

    private void setDefaultRange() {
        range = Constants.Towers.getStartRange(TowerType);
    }

    private void setDefaultDmg() {
        dmg = Constants.Towers.getStartDmg(TowerType);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getTowerType() {
        return TowerType;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public float getCd() {
        return cd;
    }

    public boolean isCDOver() {
        return cdTick >= cd;
    }

    public void resetCD() {
        cdTick=0;
    }

    public int getTier() {
        return tier;
    }
}
