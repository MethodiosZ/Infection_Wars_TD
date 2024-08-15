package scenes;

import Main.Game;
import ui.MyButton;
import static Main.GameStates.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends GameScene implements SceneMethods{
    private Random random;
    private MyButton bPlay,bEdit,bSettings,bQuit;

    public Menu(Game game) {
        super(game);
        random = new Random();
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w/3;
        int x = 640/2 - w/2;
        int y = 150;
        int yOffset = 100;

        bPlay = new MyButton("Play",x,y,w,h);
        bEdit = new MyButton("Edit",x,y+yOffset,w,h);
        bSettings = new MyButton("Settings",x,y+yOffset*2,w,h);
        bQuit = new MyButton("Quit",x,y+yOffset*3,w,h);

    }

    @Override
    public void render(Graphics g) {

        drawButtons(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bPlay.getBounds().contains(x,y)){
            SetGameState(PLAYING);
        } else if(bEdit.getBounds().contains(x,y)){
            SetGameState(EDIT);
        }else if(bSettings.getBounds().contains(x,y)){
            SetGameState(SETTINGS);
        } else if(bQuit.getBounds().contains(x,y)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlay.setMouseOver(false);
        bEdit.setMouseOver(false);
        bSettings.setMouseOver(false);
        bQuit.setMouseOver(false);
        if(bPlay.getBounds().contains(x,y)){
            bPlay.setMouseOver(true);
        }else if(bEdit.getBounds().contains(x,y)){
            bEdit.setMouseOver(true);
        } else if (bSettings.getBounds().contains(x,y)) {
            bSettings.setMouseOver(true);
        } else if (bQuit.getBounds().contains(x,y)) {
            bQuit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bPlay.getBounds().contains(x,y)){
            bPlay.setMousePressed(true);
        } else if(bEdit.getBounds().contains(x,y)){
            bEdit.setMousePressed(true);
        }else if (bSettings.getBounds().contains(x,y)) {
            bSettings.setMousePressed(true);
        } else if (bQuit.getBounds().contains(x,y)) {
            bQuit.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        bPlay.resetBooleans();
        bEdit.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();
    }

    private void drawButtons(Graphics g) {
        g.setFont(new Font("LucidaSans",Font.BOLD,20));
        bPlay.draw(g);
        bEdit.draw(g);
        bSettings.draw(g);
        bQuit.draw(g);
    }

    private int getRandomInt(){
        return random.nextInt(10);
    }

}
