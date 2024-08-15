package managers;

import enemies.Enemy;
import helpz.Constants;
import helpz.loadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Towers.*;
import static helpz.Constants.Projectiles.*;

public class ProjectileManager {
    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] projImgs;
    private int projId=0;
    public ProjectileManager(Playing playing){
        this.playing = playing;
        importImgs();
    }
    private void importImgs(){
        BufferedImage atlas = loadSave.getSpriteAtlas();
        projImgs = new BufferedImage[3];
        for(int i=0;i<projImgs.length;i++)
            projImgs[i]=atlas.getSubimage((3+i)*32,32,32,32);
    }
    public void newProjectile(Tower t, Enemy e){
        int Type = getProjType(t);
        int xDist = (int)(t.getX()-e.getX());
        int yDist = (int)(t.getY()-e.getY());
        int totalDist =  Math.abs(xDist) + Math.abs(yDist);
        float xPer = (float) Math.abs(xDist)/totalDist;
        float xSpeed = xPer * Constants.Projectiles.getSpeed(Type);
        float ySpeed = Constants.Projectiles.getSpeed(Type)-xSpeed;
        if(t.getX()>e.getX())
            xSpeed*=-1;
        if(t.getY()>e.getY())
            ySpeed*=-1;
        float arcValue = (float) Math.atan(yDist/(float)xDist);
        float rotate=(float) Math.toDegrees(arcValue);
        if(xDist<0)
            rotate+=180;
        for(Projectile p:projectiles)
            if(!p.isActive())
                if(p.getProjectileType()==Type){
                    p.reuse(t.getX()+16,t.getY()+16,xSpeed,ySpeed,t.getDmg(),rotate);
                    return;
                }
        projectiles.add(new Projectile(t.getX()+16,t.getY()+16,xSpeed,ySpeed,t.getDmg(),rotate,projId++,Type));
    }

    private int getProjType(Tower t) {
        switch (t.getTowerType()){
            case WBC:
                return EAT;
            case ANTISEPTIC:
                return ALCOHOL;
            case VACCINE:
                return ANTIBODIES;
        }
        return 0;
    }

    public void update(){
        for(Projectile p:projectiles)
            if(p.isActive()){
                p.move();
                if(isProjHits(p)){
                    p.setActive(false);
                    if(p.getProjectileType()==ANTIBODIES)
                        AoEDmg(p);
                } else if(isProjOutOfBounds(p)){
                    p.setActive(false);
                }
            }

    }

    private boolean isProjOutOfBounds(Projectile p) {
        if(p.getPos().x>=0)
            if(p.getPos().x<=640)
                if(p.getPos().y>=0)
                    if(p.getPos().y<=800)
                        return false;
        return true;
    }

    private void AoEDmg(Projectile p) {
        for (Enemy e:playing.getEnemyManager().getEnemies()) {
            if(e.isAlive()) {
                float radius = 32f;
                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());
                float realDist = (float) Math.hypot(xDist, yDist);
                if (realDist <= radius)
                    e.hurt(p.getDmg());
            }
        }
    }

    private boolean isProjHits(Projectile p) {
        for (Enemy e:playing.getEnemyManager().getEnemies()){
            if(e.isAlive()) {
                if (e.getBounds().contains(p.getPos())) {
                    e.hurt(p.getDmg());
                    if(p.getProjectileType() == ALCOHOL)
                        e.slow();
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for(Projectile p:projectiles)
            if(p.isActive()) {
                g2d.translate(p.getPos().x,p.getPos().y);
                g2d.rotate(Math.toRadians(90));
                g2d.drawImage(projImgs[p.getProjectileType()], -16, -16, null);
                g2d.rotate(-Math.toRadians(90));
                g2d.translate(-p.getPos().x,-p.getPos().y);
            }
    }

    public void reset() {
        projectiles.clear();
        projId=0;
    }
}
