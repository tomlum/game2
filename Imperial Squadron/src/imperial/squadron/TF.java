package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

public class TF{
    Posn p;
    int dir;
    int t;
    int speed;
    static int maxspeed = 20;
    //max time before turn
    static int maxT = 20;
    static int minT = 10;
    static double chanceToTurn = .66;
    
    public TF(Posn p, int dir, int t){
      this.p = p;  
      this.dir = dir;
      this.t = t;
    }
    
    public TF turn(int times){
        return new TF(this.p, (this.dir+times)%4, this.t);
    }
    
    public TF thrust(int speed){
        switch(this.dir){
            case 1: return new TF(new Posn(this.p.x+speed, this.p.y), this.dir, this.t-1);
            case 2: return new TF(new Posn(this.p.x, this.p.y+speed), this.dir, this.t-1);
            case 3: return new TF(new Posn(this.p.x-speed, this.p.y), this.dir, this.t-1);
            case 0: return new TF(new Posn(this.p.x, this.p.y-speed), this.dir, this.t-1);
        }
        return this;
    }
    
    public TF justDontHitWalls(){
        //have turn condition based on enemies/allies/behavior also
        //for now it's just one direction
        
        switch(this.dir){
            case 1: if(this.p.x+maxspeed*2>IS.rwall){return this.turn(1);} break;
            case 2: if(this.p.y+maxspeed*2>IS.dwall){return this.turn(1);} break;
            case 3: if(this.p.x-maxspeed*2<IS.lwall){return this.turn(1);} break;
            case 0: if(this.p.y-maxspeed*2<IS.uwall){return this.turn(1);} break;
        }
        return this;
    }
    
    
    /*public int[] quadrant(){
        switch(this.dir){
            case 1: 

        }
    }
    */
    
    public WorldImage image(){
        return IS.debug? new FromFileImage(this.p, "tfradar"+this.dir+".png"):
                new FromFileImage(this.p, "tf"+this.dir+".png");
    }
    
    public TF radar(){
        //Decides how to turn based on enemy locations
        //Not implemented yet
        TF newTF = new TF(this.p, this.dir, this.t);
        return this.justDontHitWalls();
    }
    
    public TF react(){
        TF newTF = new TF(this.p, this.dir, this.t);
        //Search
        if(this.t<=0){
            newTF = Math.random()>chanceToTurn? newTF:
                    Math.random()>.5? newTF.turn(1) : newTF.turn(3);
            newTF.t = maxT-Tester.randomInt(0, maxT-minT);
        }
        newTF = newTF.radar().thrust(18);
        return newTF;
    }
    
    
}
