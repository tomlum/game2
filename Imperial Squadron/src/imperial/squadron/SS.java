package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;

public class SS{
    Posn p;
    boolean imperial;
    int dir;
    int speed;
    int t;
    Type ty;
    int health;
    
    //no use yet
    public enum Type {
    HUNT,FLEE}
    enum Formation {
    scatter,cluster}
    enum Mode {
    wander, react}
    
    
    //radius of radar detection
    static int range = 200;
    static int startHealth = 800;
    static int maxspeed = 10;
    static int regspeed = 3;
    static int laserDamage = 1;
    //max time before turn
    static int maxT = 30;
    static int minT = 20;
    static double chanceToTurn = .66;
    
    public SS(Posn p, int dir, int t, boolean i, Type ty, int health){
      this.p = p;  
      this.dir = dir;
      this.t = t;
      this.imperial = i;
      this.ty = ty;
      this.health = health;
      
    }
    
    public boolean iHuh(){
    return this.imperial;
    }
    
    public SS turn(int times){
        return new SS(this.p, (this.dir+times)%4, this.t,this.imperial, this.ty, this.health);
    }
    
    public SS thrust(int speed){
        switch(this.dir){
            case 1: return new SS(new Posn(this.p.x+speed, this.p.y), this.dir, this.t-1,this.imperial, this.ty, this.health);
            case 2: return new SS(new Posn(this.p.x, this.p.y+speed), this.dir, this.t-1,this.imperial, this.ty, this.health);
            case 3: return new SS(new Posn(this.p.x-speed, this.p.y), this.dir, this.t-1,this.imperial, this.ty, this.health);
            case 0: return new SS(new Posn(this.p.x, this.p.y-speed), this.dir, this.t-1,this.imperial, this.ty, this.health);
        }
        return this;
    }
    
    public SS justDontHitWalls(){
        //have turn condition based on enemies/allies/behavior also
        //for now it's just one direction
        
        switch(this.dir){
            case 1: if(this.p.x+maxspeed*2>IS.rwall){return this.turn(2);} break;
            case 2: if(this.p.y+maxspeed*2>IS.dwall){return this.turn(2);} break;
            case 3: if(this.p.x-maxspeed*2<IS.lwall){return this.turn(2);} break;
            case 0: if(this.p.y-maxspeed*2<IS.uwall){return this.turn(2);} break;
        }
        return this;
    }
    
    
    public int[] quadrant(Vector<SS> vec){
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        int ss = 0;
        for(int i = 0; i < vec.size(); i++){
                    SS enemy = vec.elementAt(i);
                    
                    if(enemy.p.x <= this.p.x && enemy.p.x > this.p.x - range){
                        if(enemy.p.y < this.p.y && enemy.p.y > this.p.y-range){
                            q2++;
                        }
                        else q3++;
                    }
                    else if(enemy.p.x >= this.p.x && enemy.p.x < this.p.x + range){
                        if(enemy.p.y < this.p.y && enemy.p.y > this.p.y-range){
                            q1++;
                        }
                        else q4++;
                    }
        }
        switch(this.dir){
            case 0: return new int[] {ss, q1, q2, q3, q4};
            case 1: return new int[] {ss, q4, q1, q2, q3};
            case 2: return new int[] {ss, q3, q4, q1, q2};
            case 3: return new int[] {ss, q2, q3, q4, q1};
        }
        return new int[] {ss, q1, q2, q3, q4}; 
    }
    
    
    public WorldImage image(){
        return this.health<=0?
            new FromFileImage(this.p, "ex"+(1-(this.health))+".png"):    
            this.imperial? 
                IS.debug? 
                    new FromFileImage(this.p, "tfradar"+this.dir+".png"):
                    new FromFileImage(this.p, "tf"+this.dir+".png")
                :
                IS.debug? 
                    new FromFileImage(this.p, "xwradar"+this.dir+".png"):
                    new FromFileImage(this.p, "xw"+this.dir+".png");
    }
    
    public SS radar(Vector<SS> v){
        //Decides how to turn based on enemy locations
        //Not implemented yet
        SS newTF = new SS(this.p, this.dir, this.t,this.imperial, this.ty, this.health);
        int[] readout = this.quadrant(v);
        if(readout[1]+readout[4]>=readout[2]+readout[3]){
           switch(this.ty){
               case HUNT: newTF = newTF.turn(1); break;
               case FLEE: newTF = newTF.turn(3).justDontHitWalls().thrust(maxspeed-regspeed); break;
           }
        }
        else if(readout[1]+readout[4]<readout[2]+readout[3]){
            switch(this.ty){
               case HUNT: newTF = newTF.turn(3); break;
               case FLEE: newTF = newTF.turn(1).justDontHitWalls().thrust(maxspeed-regspeed); break;
           }
        }
        
        return newTF;
    }
    
    public SS react(Vector<SS> v){
        
        
        
        SS newTF = new SS(this.p, this.dir, this.t,this.imperial, this.ty, this.health);
        
        if(this.health<=0){
        return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health-1); 
        }
        else{
        //Search
        int[] readout = this.quadrant(v);
        
        if(this.t<=0){
            newTF.t = maxT-Tester.randomInt(0, maxT-minT);
        if(readout[0]+readout[1]+readout[2]+readout[3]+readout[4]<=0){
            newTF = Math.random()>chanceToTurn? newTF:
                    Math.random()>.5? newTF.turn(3) : newTF.turn(1);
        }
        else{newTF = newTF.radar(v);}
        }
        
        return newTF.justDontHitWalls().thrust(regspeed);
    }
    }
    
    public SS swapType(Type toMake){
        switch(toMake){
            case HUNT: return new SS(this.p, this.dir, this.t, this.imperial, Type.HUNT, this.health);
            case FLEE: return new SS(this.p, this.dir, this.t, this.imperial, Type.FLEE, this.health);
    }
        return this;
    }
    
    public SS doDamage(int dam){
        return this.health>0? 
                new SS(this.p, this.dir, this.t, this.imperial, this.ty, Math.max(this.health-dam, 0))
                :this;
    }
    
    public Vector doDamageVec(Vector<SS> v, int dam){
        Vector<SS> newV = new Vector();
        for(int i = 0; i < v.size(); i++){
            SS enemy = v.elementAt(i);
                    switch(this.dir){
                        case 0: if(Math.abs(this.p.x - enemy.p.x) < range && enemy.p.y > this.p.y - range && enemy.p.y < this.p.y){
                            newV.add(enemy.doDamage(dam));
                        }
                        else{newV.add(enemy);}
                        break;
                        case 1: if(Math.abs(this.p.y - enemy.p.y) < range && enemy.p.x < this.p.x + range && enemy.p.x > this.p.x){
                            newV.add(enemy.doDamage(dam));
                        }
                        else{newV.add(enemy);}
                        break;
                        case 2: if(Math.abs(this.p.x - enemy.p.x) < range && enemy.p.y < this.p.y + range && enemy.p.y > this.p.y){
                            newV.add(enemy.doDamage(dam));
                        }
                        else{newV.add(enemy);}
                        break;  
                        case 3: if(Math.abs(this.p.y - enemy.p.y) < range && enemy.p.x > this.p.x - range && enemy.p.x < this.p.x){
                            newV.add(enemy.doDamage(dam));
                        }
                        else{newV.add(enemy);}
                        break;
                         
                    }
                    
        }
        return newV;
    }
    
    
    
    
}
