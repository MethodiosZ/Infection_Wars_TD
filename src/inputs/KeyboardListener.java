package inputs;

import static Main.GameStates.*;
import Main.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

    private Game game;

    public KeyboardListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameState==EDIT)
            game.getEditing().KeyPressed(e);
        else if (gameState==PLAYING)
            game.getPlaying().KeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
