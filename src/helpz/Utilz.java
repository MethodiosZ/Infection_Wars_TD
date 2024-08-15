package helpz;

import java.util.ArrayList;

public class Utilz {
    public static int[][] ArrayListTo2DInt(ArrayList<Integer>list,int ysize,int xsize){
        int[][] newArr = new int[ysize][xsize];
        for (int j=0;j< newArr.length;j++){
            for (int i =0;i<newArr[j].length;i++){
                int index = j*ysize+i;
                newArr[j][i] = list.get(index);
            }
        }
        return newArr;
    }

    public static int[] Int2DTo1D(int[][] twodarr){
        int[] newArr = new int[twodarr.length*twodarr[0].length];
        for (int j=0;j< twodarr.length;j++){
            for (int i =0;i<twodarr[j].length;i++){
                int index = j*twodarr.length+i;
                newArr[index] = twodarr[j][i];
            }
        }
        return newArr;
    }

    public static int GetHypoDist(float x1,float y1,float x2,float y2){
        float xDiff = Math.abs(x1-x2);
        float yDiff = Math.abs(y1-y2);
        return (int) Math.hypot(xDiff,yDiff);
    }
}
