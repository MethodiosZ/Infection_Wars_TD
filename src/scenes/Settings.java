package scenes;

import Main.Game;
import ui.MyButton;

import java.awt.*;

import static Main.GameStates.*;

public class Settings extends GameScene implements SceneMethods{
    
    private MyButton bMenu;

    public Settings(Game game) {
        
        super(game);
        initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w/3;
        int x = 0;
        int y = 0;
        int yOffset = 100;
        bMenu = new MyButton("Menu",x,y,w,h);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0,0,640,640);
        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            SetGameState(MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x,y)){
            bMenu.setMousePressed(true);
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
        bMenu.resetBooleans();
    }

}
