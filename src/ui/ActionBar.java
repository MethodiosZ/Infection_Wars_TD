package ui;

import helpz.Constants;
import objects.Tile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static Main.GameStates.*;

public class ActionBar extends Bar{
    private MyButton bMenu,bPause;
    private Playing playing;
    private MyButton[] towerButtons;
    public  MyButton bSellTower,bUpgTower;
    private Tower selectedTower;
    private Tower displayedTower;
    private DecimalFormat decimalFormat;
    private int gold=100;
    private boolean showTowerCost;
    private int towerCostType;
    private int lives=10;
    public ActionBar(int x, int y, int width, int height, Playing playing){
        super(x,y,width,height);
        this.playing = playing;
        decimalFormat = new DecimalFormat("0.0");
        initButtons();
    }
    private void initButtons() {
        bMenu = new MyButton("Menu",5,645,100,25);
        bPause = new MyButton("Pause",5,675,100,25);
        towerButtons = new MyButton[3];
        int w=50,h=50;
        int xStart=110,yStart=650;
        int xOffset=(int)(w*1.1);
        int yOffset=(int)(h*1.1);
        for(int i=0;i<towerButtons.length;i++){
            towerButtons[i] = new MyButton("",xStart+xOffset*i,yStart,w,h,i);
        }
        bSellTower = new MyButton("Sell",420,700,80,25);
        bUpgTower = new MyButton("Upgrade",540,700,80,25);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bPause.draw(g);
        for(MyButton b:towerButtons){
            g.setColor(Color.WHITE);
            g.fillRect(b.getX(),b.getY(),b.getWidth(),b.getHeight());
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()],b.getX(),b.getY(),b.getWidth(),b.getHeight(),null );
            drawButtonFeedback(g,b);
        }

    }

    public void draw(Graphics g){
        g.setColor(new Color(220,120,15));
        g.fillRect(x,y,width,height);
        drawButtons(g);
        drawDisplayedTower(g);
        drawWaveInfo(g);
        drawGoldAmount(g);
        if(showTowerCost)
            drawTowerCost(g);
        if(playing.isGamePaused()) {
            g.setColor(Color.BLACK);
            g.drawString("Game is Paused!", 110, 790);
        }
        g.setColor(Color.black);
        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        g.drawString("Lives: "+lives,110,750);
    }
    public void removeLife(){
        lives--;
        if(lives<=0)
            SetGameState(GAME_OVER);
    }
    public int getLives() {
        return lives;
    }
    private void drawTowerCost(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(280,650,120,50);
        g.setColor(Color.BLACK);
        g.drawRect(280,650,120,50);
        g.drawString(getTowerCostName(),285,670);
        g.drawString("Cost: "+getTowerCost()+"g",285,695);
        if(!isEnoughGold(towerCostType)) {
            g.setColor(Color.blue);
            g.setFont(new Font("LucidaSans", Font.PLAIN, 15));
            g.drawString("Not Enough Gold!", 285, 725);
        }
    }
    private int getTowerCost() {
        return Constants.Towers.getTowerCost(towerCostType);
    }
    private String getTowerCostName() {
        return Constants.Towers.getName(towerCostType);
    }
    private void drawGoldAmount(Graphics g) {
        g.drawString("Gold: "+gold,110,725);
    }

    private void drawWaveInfo(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("LucidaSans",Font.BOLD,20));
        drawWaveTimer(g);
        drawEnemiesLeft(g);
        drawWavesLeft(g);
    }

    private void drawWavesLeft(Graphics g) {
        int curr = playing.getWaveManager().getWaveIndex()+1;
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave "+curr+" / "+size,425,770);
    }

    private void drawEnemiesLeft(Graphics g) {
        int remaining = playing.getEnemyManager().getAliveEnemies();
        g.drawString("Enemies Left: "+remaining,425,790);
    }

    private void drawWaveTimer(Graphics g){
        if(playing.getWaveManager().isWaveTimerStarted()){
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = decimalFormat.format(timeLeft);
            g.drawString("Time Left: "+formattedText,425,750);
        }
    }
    private void drawDisplayedTower(Graphics g) {
        if(displayedTower!=null){
            g.setColor(Color.gray);
            g.fillRect(410,645,220,85);
            g.setColor(Color.BLACK);
            g.drawRect(410,645,220,85);
            g.drawRect(420,650,50,50);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],420,650,50,50,null );
            g.setFont(new Font("LucidaSans",Font.BOLD,15));
            g.drawString(Constants.Towers.getName(displayedTower.getTowerType()),480,660);
            g.drawString("ID: "+displayedTower.getId() ,480,675);
            g.drawString("Tier: "+displayedTower.getTier() ,560,660);
            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);
            bSellTower.draw(g);
            drawButtonFeedback(g,bSellTower);
            if(displayedTower.getTier()<3&& gold>=getUpgradeAmount()) {
                bUpgTower.draw(g);
                drawButtonFeedback(g, bUpgTower);
            }
            if(bSellTower.isMouseOver()){
                g.setColor(Color.red);
                g.drawString("Sell for: "+getSellAmount()+"g",480,690);
            } else if (bUpgTower.isMouseOver() && gold>=getUpgradeAmount()) {
                g.setColor(Color.blue);
                g.drawString("Upgrade for: "+getUpgradeAmount()+"g",480,690);
            }
        }
    }

    private int getUpgradeAmount() {
        return (int)(Constants.Towers.getTowerCost(displayedTower.getTowerType())*0.6f);
    }

    private int getSellAmount() {
        int upgradeCost= (displayedTower.getTier()-1)*getUpgradeAmount();
        upgradeCost *= 0.5f;
        return Constants.Towers.getTowerCost(displayedTower.getTowerType())/2+upgradeCost;
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.lightGray);
        g.drawOval(displayedTower.getX()+16- (int)displayedTower.getRange(),
                displayedTower.getY()+16- (int)displayedTower.getRange(),
                (int)displayedTower.getRange()*2,(int)displayedTower.getRange()*2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect(displayedTower.getX(), displayedTower.getY(),32,32);
    }

    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x,y))
            SetGameState(MENU);
        else if (bPause.getBounds().contains(x,y))
            pauseGame();
        else {
            if(displayedTower!=null){
                if (bSellTower.getBounds().contains(x, y)) {
                    sellTowerClicked();
                    return;
                } else if (bUpgTower.getBounds().contains(x, y) && displayedTower.getTier()<3 && gold>=getUpgradeAmount()) {
                    upgradeTowerClicked();
                    return;
                }
            }
            for(MyButton b : towerButtons){
                if(b.getBounds().contains(x,y)){
                    if(!isEnoughGold(b.getId()))
                        return;
                    selectedTower = new Tower(0,0,-1,b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    private void pauseGame() {
        playing.setGamePaused(!playing.isGamePaused());
        if(playing.isGamePaused())
            bPause.setText("Unpause");
        else
            bPause.setText("Pause");
    }

    private void upgradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold-=getUpgradeAmount();
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        addGold(getSellAmount());
        displayedTower = null;
    }

    private boolean isEnoughGold(int towerType) {
        return gold>=Constants.Towers.getTowerCost(towerType);
    }
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        showTowerCost=false;
        bSellTower.setMouseOver(false);
        bUpgTower.setMouseOver(false);
        for(MyButton b:towerButtons)
            b.setMouseOver(false);
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMouseOver(true);
        } else if (bPause.getBounds().contains(x,y)) {
            bPause.setMouseOver(true);
        } else {
            if(displayedTower!=null) {
                if (bSellTower.getBounds().contains(x, y)) {
                    bSellTower.setMouseOver(true);
                    return;
                } else if (bUpgTower.getBounds().contains(x, y) && displayedTower.getTier()<3) {
                    bUpgTower.setMouseOver(true);
                    return;
                }
            }
            for(MyButton b:towerButtons) {
                if(b.getBounds().contains(x,y)){
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
                    return;
                }
            }
        }
    }
    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMousePressed(true);
        }  else if (bPause.getBounds().contains(x,y)) {
            bPause.setMousePressed(true);
        }else {
            if(displayedTower!=null) {
                if (bSellTower.getBounds().contains(x, y)) {
                    bSellTower.setMousePressed(true);
                    return;
                } else if (bUpgTower.getBounds().contains(x, y) && displayedTower.getTier()<3) {
                    bUpgTower.setMousePressed(true);
                    return;
                }
            }
            for(MyButton b:towerButtons) {
                if(b.getBounds().contains(x,y)){
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bPause.resetBooleans();
        for(MyButton b:towerButtons)
            b.resetBooleans();
        bSellTower.resetBooleans();
        bUpgTower.resetBooleans();
    }
    public void displayTower(Tower t) {
        displayedTower = t;
    }
    public void payForTower(int towerType) {
        this.gold -= Constants.Towers.getTowerCost(towerType);
    }
    public void addGold(int reward) {
        this.gold += reward;
    }
    public void reset() {
        lives=20;
        towerCostType=0;
        showTowerCost=false;
        gold=100;
        selectedTower=null;
        displayedTower=null;
    }
}
