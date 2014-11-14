package imperial.squadron;
import java.util.Random;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;

public class Tester {
    
    static Random rand = new Random();
    public static int randomInt( int min, int max ) {
        return rand.nextInt((max - min) + 1) + min; }
    
    public static int randomInt( double min, double max ) {
        return rand.nextInt((
                ((int) Math.round(max)) - (int) Math.round(min)) + 1) + (int) Math.round(min); }
    
    
    
    public static SS randomSS(Posn c, int range, int dir, int t, boolean imperial){
        return new SS(
                new Posn(randomInt(c.x-range/2, c.x+range/2),randomInt(c.y-range/2, c.y+range/2)),
                dir,
                t,
                imperial,        
                SS.Type.HUNT,
                100);
    }
    
    
    
    
    
    public static void testQuadrant(int trials){
        for(int i = 0; i < trials; i++){
        int dir = randomInt(0,3);
        int range = SS.range-2;
        Posn center = new Posn(randomInt(0, 1000),randomInt(0, 1000));
        Posn quad1 = new Posn(center.x + randomInt(0, (range/2))+1,center.y - randomInt(0, (range/2))-1);
        Posn quad2 = new Posn(center.x - randomInt(0, (range/2))-1,center.y - randomInt(0, (range/2))-1);
        Posn quad3 = new Posn(center.x - randomInt(0, (range/2))-1,center.y + randomInt(0, (range/2))+1);
        Posn quad4 = new Posn(center.x + randomInt(0, (range/2))+1,center.y + randomInt(0, (range/2))+1);
        SS base = randomSS(center, 0, 0, 1000000, true);
        Vector<SS> enemies = new Vector();
        enemies.add(new SS(quad1, 0, 1000, false, SS.Type.HUNT, 100));
        enemies.add(new SS(quad2, 0, 1000, false, SS.Type.HUNT, 100));
            enemies.add(new SS(quad2, 0, 1000, false, SS.Type.HUNT, 100));
        enemies.add(new SS(quad3, 0, 1000, false, SS.Type.HUNT, 100));
            enemies.add(new SS(quad3, 0, 1000, false, SS.Type.HUNT, 100));
            enemies.add(new SS(quad3, 0, 1000, false, SS.Type.HUNT, 100));
        enemies.add(new SS(quad4, 0, 1000, false, SS.Type.HUNT, 100));
            enemies.add(new SS(quad4, 0, 1000, false, SS.Type.HUNT, 100));
            enemies.add(new SS(quad4, 0, 1000, false, SS.Type.HUNT, 100));
            enemies.add(new SS(quad4, 0, 1000, false, SS.Type.HUNT, 100));
        int[] quad = base.quadrant(enemies);
        if(quad[1+(dir%4)]!=1+(dir%4)){
            System.out.println("Error in Quadrant 1");
            System.out.println((1+(dir)%4) + " count is " + quad[1+(dir%4)]);
        };
        if(quad[1+((dir+1)%1)] != 1+((dir+1)%1)){
            System.out.println("Error in Quadrant 2");
            System.out.println((1+(dir+1)%4) + " count is " + quad[1+(dir+1%4)]);
        };
        if(quad[1+((dir+2)%1)] != 1+((dir+2)%1)){
            System.out.println("Error in Quadrant 3");
            System.out.println((1+(dir+2)%4) + " count is " + quad[1+((dir+2)%4)]);
        };
        if(quad[1+((dir+3)%1)] != 1+((dir+3)%1)){
            System.out.println("Error in Quadrant 4");
            System.out.println((1+(dir+3)%4) + " count is " + quad[1+((dir+3)%4)]);
        };
                }
    }
}
