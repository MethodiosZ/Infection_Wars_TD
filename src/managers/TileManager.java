package managers;

import helpz.ImgFix;
import helpz.loadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static helpz.Constants.Tiles.*;

public class TileManager {

    public Tile SKIN,TISSUE,VEIN,VEIN_VERT,BL_VEIN_CORNER,TL_VEIN_CORNER,TR_VEIN_CORNER,
            BR_VEIN_CORNER, BL_TISSUE_CORNER,TL_TISSUE_CORNER,TR_TISSUE_CORNER,
            BR_TISSUE_CORNER, B_TISSUE,L_TISSUE, T_TISSUE, R_TISSUE;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();
    public ArrayList<Tile> veins = new ArrayList<>();
    public ArrayList<Tile> vein_c = new ArrayList<>();
    public ArrayList<Tile> tissue_c = new ArrayList<>();
    public ArrayList<Tile> tissue_s = new ArrayList<>();

    public TileManager() {

        loadAtlas();
        createTiles();
    }

    private void createTiles() {
        int id=0;
        tiles.add(SKIN = new Tile(getSprite(0,0),id++,SKIN_TILE));
        tiles.add(TISSUE = new Tile(getSprite(1,0),id++,TISSUE_TILE));

        veins.add(VEIN = new Tile(getSprite(2,0),id++,VEIN_TILE));
        veins.add(VEIN_VERT = new Tile(ImgFix.getRotImg(getSprite(2,0),90),id++,VEIN_TILE));

        vein_c.add(BL_VEIN_CORNER = new Tile(getSprite(3,0),id++,VEIN_TILE));
        vein_c.add(TL_VEIN_CORNER = new Tile(ImgFix.getRotImg(getSprite(3,0),90),id++,VEIN_TILE));
        vein_c.add(TR_VEIN_CORNER = new Tile(ImgFix.getRotImg(getSprite(3,0),180),id++,VEIN_TILE));
        vein_c.add(BR_VEIN_CORNER = new Tile(ImgFix.getRotImg(getSprite(3,0),270),id++,VEIN_TILE));

        tissue_s.add(B_TISSUE = new Tile(getSprite(4,0),id++,TISSUE_TILE));
        tissue_s.add(L_TISSUE = new Tile(ImgFix.getRotImg(getSprite(4,0),90),id++,TISSUE_TILE));
        tissue_s.add(T_TISSUE = new Tile(ImgFix.getRotImg(getSprite(4,0),180),id++,TISSUE_TILE));
        tissue_s.add(R_TISSUE = new Tile(ImgFix.getRotImg(getSprite(4,0),270),id++,TISSUE_TILE));

        tissue_c.add(BL_TISSUE_CORNER = new Tile(getSprite(5,0),id++,TISSUE_TILE));
        tissue_c.add(TL_TISSUE_CORNER = new Tile(ImgFix.getRotImg(getSprite(5,0),90),id++,TISSUE_TILE));
        tissue_c.add(TR_TISSUE_CORNER = new Tile(ImgFix.getRotImg(getSprite(5,0),180),id++,TISSUE_TILE));
        tissue_c.add(BR_TISSUE_CORNER = new Tile(ImgFix.getRotImg(getSprite(5,0),270),id++,TISSUE_TILE));

        tiles.addAll(veins);
        tiles.addAll(vein_c);
        tiles.addAll(tissue_c);
        tiles.addAll(tissue_s);
    }

    private BufferedImage[] getImgs(int fX,int fY, int sX,int sY){
        return new BufferedImage[] {getSprite(fX,fY),getSprite(sX,sY)};
    }
    private void loadAtlas() {

        atlas = loadSave.getSpriteAtlas();

    }

    public BufferedImage getSprite(int id){
        return tiles.get(id).getSprite();
    }

    private BufferedImage getSprite(int x, int y){
        return atlas.getSubimage(x*32,y*32,32,32);
    }

    public Tile getTile(int id){
        return tiles.get(id);
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Tile> getVeins() {
        return veins;
    }

    public ArrayList<Tile> getVein_c() {
        return vein_c;
    }

    public ArrayList<Tile> getTissue_c() {
        return tissue_c;
    }

    public ArrayList<Tile> getTissue_s() {
        return tissue_s;
    }
}
