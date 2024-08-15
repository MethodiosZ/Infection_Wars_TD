package Main;

import helpz.loadSave;
import inputs.*;
import managers.TileManager;
import scenes.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame implements Runnable{

    private final double FPS__SET = 120.0;
    private final double UPS__SET = 60.0;
    private Thread gameThread;
    //Classes
    private GameScreen gameScreen;
    private TileManager tileManager;
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;
    private GameOver gameOver;

    public Game(){
        loadSave.CreateFolder();
        createDefaultLevel();
        initClasses();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Infection Wars TD");
        add(gameScreen);
        pack();
        setVisible(true);
    }

    private void initClasses() {
        tileManager = new TileManager();
        gameScreen = new GameScreen(this);
        render = new Render(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new Editing(this);
        gameOver = new GameOver(this);
    }
    private void createDefaultLevel() {
        int[] arr = new int[400];
        for(int i=0;i<arr.length;i++){
            arr[i]=0;
        }
        loadSave.CreateLevel(arr);
    }

    private void start(){
        gameThread = new Thread(this){};
        gameThread.start();
    }

    private void updateGame(){
        switch (GameStates.gameState){
            case PLAYING:
                playing.update();
                break;
            case MENU:
                break;
            case SETTINGS:
                break;
            case EDIT:
                break;
        }
    }

    public static void main(String[] args){
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS__SET;
        double timePerUpdate = 1000000000.0 / UPS__SET;
        long lastUpdate = System.nanoTime();
        long lastFrame = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;
        long now;

        while (true) {

            now = System.nanoTime();
            //Render
            if (now - lastFrame >= timePerFrame) {
                lastFrame = now;
                repaint();
                frames++;
            }
            //Update
            if(now - lastUpdate >= timePerUpdate){
                lastUpdate = now;
                updateGame();
                updates++;
            }
            //FPS and UPS counter
            if(System.currentTimeMillis() - lastTimeCheck >= 1000){
                System.out.println("FPS: "+frames+" | UPS: "+ updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }
    //Getters and Setters:
    public Render getRender(){
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Settings getSettings() {
        return settings;
    }

    public Editing getEditing(){return editing;}

    public TileManager getTileManager(){
        return tileManager;
    }

    public GameOver getGameOver() {
        return gameOver;
    }
}
