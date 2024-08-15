package helpz;

import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class loadSave {
    public static String homePath = System.getProperty("user.home");
    public static String saveFolder = "InfectionWars";
    public static String levelFile = "level.txt";
    public static String filePath = homePath + File.separator +saveFolder +File.separator+levelFile;
    private static File lvlFile = new File(filePath);
    public static void CreateFolder(){
        File folder = new File(homePath+File.separator+saveFolder);
        if(!folder.exists())
            folder.mkdir();
    }
    public static BufferedImage getSpriteAtlas(){
        BufferedImage img = null;
        InputStream is = loadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    private static void WriteFile(int[] idArr, PathPoint start,PathPoint end){
        try {
            PrintWriter pw = new PrintWriter(lvlFile);
            for(int i:idArr)
                pw.println(i);
            pw.println(start.getxCord());
            pw.println(start.getyCord());
            pw.println(end.getxCord());
            pw.println(end.getyCord());
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static ArrayList<Integer> ReadFile(){
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner sc = new Scanner(lvlFile);
            while (sc.hasNextLine()){
                list.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void CreateLevel(int[] idArr){
        if(lvlFile.exists()){
            System.out.println("File: "+lvlFile+" already exists!");
        } else {
            try {
                lvlFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            WriteFile(idArr,new PathPoint(0,0),new PathPoint(0,0));
        }
    }

    public static int[][] GetLevelData(){
        if((lvlFile.exists())){
            ArrayList<Integer> list = ReadFile();
            return Utilz.ArrayListTo2DInt(list,20,20);
        } else System.out.println("File: "+lvlFile+" doesn't exist!");
        return null;
    }

    public static void SaveLevel(int[][] idArr, PathPoint start,PathPoint end){
        if((lvlFile.exists()))
            WriteFile(Utilz.Int2DTo1D(idArr),start,end);
        else
            System.out.println("File: "+lvlFile+" doesn't exist!");
    }

    public static ArrayList<PathPoint> GetLevelPathPoints(){
        if((lvlFile.exists())){
            ArrayList<Integer> list = ReadFile();
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));
            return points;
        } else System.out.println("File: "+lvlFile+" doesn't exist!");
        return null;
    }
}
