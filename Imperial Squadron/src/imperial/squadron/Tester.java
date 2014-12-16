package imperial.squadron;
import static imperial.squadron.Battlefield.imperialX;
import static imperial.squadron.Battlefield.lwall;
import static imperial.squadron.OverWorld.formations;
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
                100, 
                false,
                SS.Formation.IGNORE,
                SS.Make.REG, 
                SS.regRange);
    }
    
    
    
    public static SS superRandomSS(){
        return new SS(
                new Posn(randomInt(Battlefield.lwall, Battlefield.rwall), randomInt(Battlefield.uwall, Battlefield.dwall)),
                randomInt(0,3),
                0,
                randomInt(0,1)>.5,        
                SS.Type.HUNT,
                100, 
                false,
                SS.Formation.IGNORE,
                SS.Make.REG, 
                SS.regRange);
    }
    
    public static SS randRebel(Posn p){
        return new SS(
                p,
                randomInt(0,3),
                0,
                randomInt(0,1)>.5,        
                SS.Type.HUNT,
                100, 
                false,
                SS.Formation.IGNORE,
                SS.Make.REG, 
                SS.regRange);
    }
    
    
    
    
    public static void testRadarAndType(int trials){
        for(int i = 0; i < trials; i++){
        
        SS.Type rantype = SS.Type.HUNT;
        
        switch(randomInt(0,1)){
            case 0: rantype = SS.Type.EVADE;
            case 1: rantype = SS.Type.HUNT;
        }   
                
        
        SS testShip = new SS(
                new Posn(300,300),
                randomInt(0,3),
                0,
                true,        
                rantype,
                0, 
                true,
                SS.Formation.IGNORE,
                SS.Make.REG, 
                SS.regRange);
        
        int[] readout = new int[]{0, 1,0,0,1};
        int[] fReadout = new int[]{};
        switch(testShip.ty){
            case HUNT: 
                if(testShip.radar(readout, fReadout).dir != 
                    testShip.turn(1).dir){
                System.out.println("Evade turn/readout/type isn't working");
            }
            break;
            
            case EVADE: 
                if(testShip.radar(readout, fReadout).dir != 
                    testShip.turn(3).dir){
                System.out.println("Evade turn/readout/type isn't working");
            }
            break;
            
        }
        }
        
    }
    
      public static void testRadarAndFormation(int trials){
        for(int i = 0; i < trials; i++){
        
        SS.Formation ranform = SS.Formation.CLUSTER;
      
        switch(randomInt(0,1)){
            case 0: ranform = SS.Formation.CLUSTER;
            case 1: ranform = SS.Formation.SCATTER;
        }   
                
        
        SS testShip = new SS(
                new Posn(300,300),
                randomInt(0,3),
                0,
                true,        
                SS.Type.HUNT,
                0, 
                true,
                ranform,
                SS.Make.REG, 
                SS.regRange);
        
        int[] readout = new int[]{0, 0,0,0,0};
        int[] fReadout = new int[]{0, 100,0,0,100};
        switch(testShip.fo){
            case CLUSTER: 
                if(testShip.radar(readout, fReadout).dir != 
                    testShip.turn(1).dir){
                System.out.println("Evade turn/readout/formation isn't working");
            }
            break;
            
            case SCATTER: 
                if(testShip.radar(readout, fReadout).dir != 
                    testShip.turn(3).dir){
                System.out.println("Evade turn/readout/formation isn't working");
            }
            break;
            
        }
        }
        
    }
    
    
    
    
    
    
    public static void testQuadrantAndThrust(int trials){
        for(int i = 0; i < trials; i++){
        int dir = randomInt(0,3);
        int range = SS.regRange-2;
        Posn center = new Posn(randomInt(0, 1000),randomInt(0, 1000));
        Posn quad1 = new Posn(center.x + randomInt(0, (range/2))+1,center.y - randomInt(0, (range/2))-1);
        Posn quad2 = new Posn(center.x - randomInt(0, (range/2))-1,center.y - randomInt(0, (range/2))-1);
        Posn quad3 = new Posn(center.x - randomInt(0, (range/2))-1,center.y + randomInt(0, (range/2))+1);
        Posn quad4 = new Posn(center.x + randomInt(0, (range/2))+1,center.y + randomInt(0, (range/2))+1);
        SS base = randomSS(center, 0, 0, 1000000, true);
        Vector<SS> enemies = new Vector();
        enemies.add(new SS(quad1, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
        enemies.add(new SS(quad2, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
            enemies.add(new SS(quad2, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
        enemies.add(new SS(quad3, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
            enemies.add(new SS(quad3, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
            enemies.add(new SS(quad3, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
        enemies.add(new SS(quad4, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
            enemies.add(new SS(quad4, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
            enemies.add(new SS(quad4, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
            enemies.add(new SS(quad4, 0, 1000, false, SS.Type.HUNT, 100, false, SS.Formation.IGNORE, SS.Make.REG, SS.regRange));
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
        
        if(base.thrust(30).p.x == base.p.x &&
                base.thrust(30).p.y == base.p.y){
            System.out.println("Thrust isn't moving ships");
        }
        
                }
    }
    
    
    public static void testAI(){
        Vector<SS> rebs = new Vector();
        Vector<SS> emp = new Vector();
        for(int i = 0; i < 50; i++){
            SS srss = superRandomSS();
        if(srss.imperial){
            emp.add(srss);
        }
        else rebs.add(srss);
        }
        
        int initRSize = rebs.size();
        int initISize = emp.size();
        
        
        Battlefield b = new Battlefield(emp, rebs, new Vader(new Posn(imperialX, 300), 1, false, false, 0), 100000, OverWorld.formations[1],10, Battlefield.Res.START);
        for(int i = 0; i < 100; i++){
        b = b.onTick();
    }
        System.out.println() ;
        if(b.imperialFleet.size() == initISize && b.rebelFleet.size() == initRSize){
            System.out.println("Unlikely no ship would be lost");
        }
        for(int i = 0; i < b.imperialFleet.size(); i++){
            SS current = b.imperialFleet.elementAt(i);
            if(current.p.x > Battlefield.rwall ||
                    current.p.x < Battlefield.lwall ||
                    current.p.y > Battlefield.dwall ||
                    current.p.y < Battlefield.uwall){
                System.out.println("Error in justDon'tHitWalls");
            }
                
        }
        
        
    }
    
    public static void testOverWorldLose(){
        
        RSS[] initialEnems = new RSS[]{
            new RSS(new Posn(500, 400), true, 3),
            new RSS(new Posn(400, 550), true, 1),
            new RSS(new Posn(600, 200), true, 2),
            new RSS(new Posn(700, 300), true, 4),
            new RSS(new Posn(800, 100), true, 5)
        };
        
        OverWorld w = new OverWorld(false, new Battlefield(new Vector(), new Vector(), new Vader(new Posn(imperialX, 300), 1, false, false, 0), OverWorld.startingScrap, new SS[]{},0,Battlefield.Res.START),
                new ISS(new Posn (1000, 200), true, 1), 
                initialEnems, 
                OverWorld.startingScrap,
                0);
        
        for(int i = 0; i< 10000; i++){
            w = w.onTick();
        }
        
        boolean shipsCrossed = false;
        for(int i = 0; i<w.enemies.length; i++){
            if(w.enemies[i].p.x<0){
             shipsCrossed=true;   
            }
        }
        
        if(!shipsCrossed){
            System.out.println("Error in Game Over");
        }
        
        
    }
    
    
    public static void testDoDamage(int trials){
        for(int i = 0; i < trials; i++){
        SS testShip = new SS(
                new Posn(300,300),
                randomInt(0,3),
                0,
                true,        
                SS.Type.HUNT,
                0, 
                true,
                SS.Formation.CLUSTER,
                SS.Make.REG, 
                SS.regRange);
        if(testShip.doDamage(100000).health!=0){
        System.out.println("Error in doDamage");
    }
    }
    
    }
    
    
    
}
