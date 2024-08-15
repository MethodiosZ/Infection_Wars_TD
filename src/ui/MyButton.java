package ui;

import java.awt.*;

public class MyButton {

    private int x,y,width,height,id;
    private String text;
    private Rectangle bounds;
    private boolean mouseOver, mousePressed;

    public MyButton(String text,int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;
        initBounds();
    }

    public MyButton(String text,int x, int y, int width, int height,int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        initBounds();
    }

    public void resetBooleans(){
        this.mouseOver = false;
        this.mousePressed = false;
    }

    private void initBounds(){
        this.bounds = new Rectangle(x,y,width,height);
    }

    public void draw(Graphics g){
        //body
        drawBody(g);
        //border
        drawBorder(g);
        //text
        drawText(g);

    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        if(mousePressed){
            g.drawRect(x+1,y+1,width-2,height-2);
            g.drawRect(x+2,y+2,width-4,height-4);
        }
    }

    private void drawBody(Graphics g) {
        if(mouseOver)
            g.setColor(Color.gray);
        else
            g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height);
    }

    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text,x - w/2 + width/2,y+h/3+height/2);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getId(){return id;}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setText(String text) {
        this.text = text;
    }
}
