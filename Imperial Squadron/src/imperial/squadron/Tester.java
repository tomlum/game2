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
                imperial        
                );
    }
    
    
    
    
    
    public static void testQuadrant(int trials){
        for(int i = 0; i < trials; i++){
        int range = SS.range-2;
        Posn center = new Posn(randomInt(0, 1000),randomInt(0, 1000));
        Posn quad1 = new Posn(center.x + randomInt(0, (range/2))+1,center.y - randomInt(0, (range/2))-1);
        Posn quad2 = new Posn(center.x - randomInt(0, (range/2))-1,center.y - randomInt(0, (range/2))-1);
        Posn quad3 = new Posn(center.x - randomInt(0, (range/2))-1,center.y + randomInt(0, (range/2))+1);
        Posn quad4 = new Posn(center.x + randomInt(0, (range/2))+1,center.y + randomInt(0, (range/2))+1);
        SS base = randomSS(center, 0, 1, 1000000, true);
        Vector<SS> enemies = new Vector();
        enemies.add(new SS(quad1, 0, 1000, false));
        int[] quad = base.quadrant(enemies);
        if(quad[1]<=0){
            System.out.println(enemies.size());
            
            System.out.println(enemies.elementAt(0).p.x + " " + enemies.elementAt(0).p.y);
            System.out.println(base.p.x + " " + base.p.y);
            System.out.println(quad[0]);
            System.out.println(quad[1]);
            System.out.println(quad[2]);
            System.out.println(quad[3]);
            System.out.println(quad[4]);
            System.out.println("Error in Quadrant 1");
        };
                }
    }
}
