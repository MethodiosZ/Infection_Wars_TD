package ui;

import helpz.loadSave;
import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Main.GameStates.MENU;
import static Main.GameStates.SetGameState;

public class ToolBar extends Bar{
    private Editing editing;
    private MyButton bMenu,bSave;
    private MyButton bPathS,bPathE;
    private BufferedImage pathS,pathE;
    private Tile selectedTile;
    private Map<MyButton,ArrayList<Tile>> map = new HashMap<MyButton,ArrayList<Tile>>();

    private MyButton bSkin,bTissue,bVeinS,bVeinC,bTissueS,bTissueC;
    private MyButton currButton;
    private int currIndex=0;
    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        initImages();
        initButtons();
    }

    private void initImages() {
        pathS = loadSave.getSpriteAtlas().getSubimage(3*32,1*32,32,32);
        pathE = loadSave.getSpriteAtlas().getSubimage(4*32,1*32,32,32);
    }

    private void saveLevel() {

        editing.saveLevel();
    }
    private void initButtons() {
        bMenu = new MyButton("Menu",5,645,100,25);
        bSave = new MyButton("Save",5,675,100,25);
        int w=50,h=50;
        int xStart=110,yStart=650;
        int xOffset=(int)(w*1.1);
        int yOffset=(int)(h*1.1);
        int i=0;
        bTissue = new MyButton("Tissue",xStart,yStart,w,h,i++);
        bSkin = new MyButton("Skin",xStart+xOffset,yStart,w,h,i++);
        initMapButton(bVeinS,editing.getGame().getTileManager().getVeins(),xStart,yStart,xOffset,w,h,i++);
        initMapButton(bVeinC,editing.getGame().getTileManager().getVein_c(),xStart,yStart,xOffset,w,h,i++);
        initMapButton(bTissueC,editing.getGame().getTileManager().getTissue_c(),xStart,yStart,xOffset,w,h,i++);
        initMapButton(bTissueS,editing.getGame().getTileManager().getTissue_s(),xStart,yStart,xOffset,w,h,i++);
        bPathS = new MyButton("PathStart",xStart,yStart+yOffset,w,h,i++);
        bPathE = new MyButton("PathEnd",xStart+xOffset,yStart+yOffset,w,h,i++);
    }
    private void initMapButton(MyButton b, ArrayList<Tile> list, int x,int y,int xOff,int w,int h,int id){
        b= new MyButton("",x+xOff*id,y,w,h,id);
        map.put(b,list);
    }
    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bSave.draw(g);
        drawPathButtons(g,bPathS,pathS);
        drawPathButtons(g,bPathE,pathE);
        drawNormalButtons(g,bSkin);
        drawNormalButtons(g,bTissue);
        drawSelectedTile(g);
        drawMapButtons(g);
    }

    private void drawPathButtons(Graphics g, MyButton b, BufferedImage img) {
        g.drawImage(img,b.getX(),b.getY(),b.getWidth(),b.getHeight(),null);
        drawButtonFeedback(g,b);
    }

    private void drawNormalButtons(Graphics g, MyButton b) {
        g.drawImage(getButtImg(b.getId()),b.getX(),b.getY(),b.getWidth(),b.getHeight(),null);
        drawButtonFeedback(g,b);
    }

    private void drawMapButtons(Graphics g) {
        for(Map.Entry<MyButton,ArrayList<Tile>> entry: map.entrySet()){
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();
            g.drawImage(img,b.getX(),b.getY(),b.getWidth(),b.getHeight(),null);
            drawButtonFeedback(g,b);
        }
    }

    private void drawSelectedTile(Graphics g) {
        if(selectedTile != null){
            g.drawImage(selectedTile.getSprite(),550,650,50,50,null);
            g.setColor(Color.BLACK);
            g.drawRect(550,650,50,50);
        }
    }

    private BufferedImage getButtImg(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    public void draw(Graphics g){
        g.setColor(new Color(220,120,15));
        g.fillRect(x,y,width,height);
        drawButtons(g);
    }

    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            SetGameState(MENU);
        } else if (bSave.getBounds().contains(x,y)) {
            saveLevel();
        } else if (bSkin.getBounds().contains(x,y)) {
            selectedTile = editing.getGame().getTileManager().getTile(bSkin.getId());
            editing.setSelectedTile(selectedTile);
            currButton  = bSkin;
        } else if (bTissue.getBounds().contains(x,y)) {
            selectedTile = editing.getGame().getTileManager().getTile(bTissue.getId());
            editing.setSelectedTile(selectedTile);
            currButton = bTissue;
        } else if (bPathS.getBounds().contains(x,y)) {
            selectedTile = new Tile(pathS,-1,-1);
            editing.setSelectedTile(selectedTile);
        } else if (bPathE.getBounds().contains(x,y)) {
            selectedTile = new Tile(pathE,-2,-2);
            editing.setSelectedTile(selectedTile);
        }else {
            for(MyButton b: map.keySet()){
                if(b.getBounds().contains(x,y)){
                    selectedTile = map.get(b).get(0);
                    editing.setSelectedTile(selectedTile);
                    currButton =b;
                    currIndex=0;
                    return;
                }
            }
        }
    }



    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        bSkin.setMouseOver(false);
        bTissue.setMouseOver(false);
        bPathS.setMouseOver(false);
        bPathE.setMouseOver(false);
        for(MyButton b: map.keySet()){
          b.setMouseOver(false);
        }
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMouseOver(true);
        } else if (bSave.getBounds().contains(x,y)) {
            bSave.setMouseOver(true);
        } else if (bSkin.getBounds().contains(x,y)) {
            bSkin.setMouseOver(true);
        }else if (bTissue.getBounds().contains(x,y)) {
            bTissue.setMouseOver(true);
        }else if (bPathS.getBounds().contains(x,y)) {
            bPathS.setMouseOver(true);
        }else if (bPathE.getBounds().contains(x,y)) {
            bPathE.setMouseOver(true);
        }else {
           for(MyButton b: map.keySet()){
               if(b.getBounds().contains(x,y)) {
                   b.setMouseOver(true);
                   return;
               }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMousePressed(true);
        } else if (bSave.getBounds().contains(x,y)) {
            bSave.setMousePressed(true);
        } else if (bSkin.getBounds().contains(x,y)) {
            bSkin.setMousePressed(true);
        } else if (bTissue.getBounds().contains(x,y)) {
            bTissue.setMousePressed(true);
        } else if (bPathS.getBounds().contains(x,y)) {
            bPathS.setMousePressed(true);
        } else if (bPathE.getBounds().contains(x,y)) {
            bPathE.setMousePressed(true);
        } else {
            for(MyButton b: map.keySet()){
                if(b.getBounds().contains(x,y)) {
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        bSkin.resetBooleans();
        bTissue.resetBooleans();
        bPathS.resetBooleans();
        bPathE.resetBooleans();
        for(MyButton b: map.keySet()){
            b.setMousePressed(false);
        }
    }

    public void rotateSprite() {
        if(map.get(currButton)!=null){
            currIndex++;
            if(currIndex>=map.get(currButton).size()) currIndex=0;
            selectedTile = map.get(currButton).get(currIndex);
            editing.setSelectedTile(selectedTile);
        }
    }

    public BufferedImage getPathS() {
        return pathS;
    }

    public BufferedImage getPathE() {
        return pathE;
    }
}
