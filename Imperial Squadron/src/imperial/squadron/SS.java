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
    Formation fo;
    int health;
    boolean attacking;
    
    //no use yet
    public enum Type{
    HUNT,EVADE}
    enum Formation{
    SCATTER,CLUSTER, IGNORE}
    enum Order{
    SHIELD, PART}
    
    
    //radius of radar detection
    static int range = 100;
    static int startHealth = 800;
    static int maxspeed = 8;
    static int regspeed = 5;
    static int laserDamage = 2;
    //max time before turn
    static int maxT = 30;
    static int minT = 20;
    static double chanceToTurn = .6;
    
    public SS(Posn p, int dir, int t, boolean i, Type ty, int health, boolean at, Formation fo){
      this.p = p;  
      this.dir = dir;
      this.t = t;
      this.imperial = i;
      this.ty = ty;
      this.health = health;
      this.attacking = at;
      this.fo = fo;
      
    }
    
    public boolean iHuh(){
    return this.imperial;
    }
    
    public SS turn(int times){
        return new SS(this.p, (this.dir+times)%4, this.t,this.imperial, this.ty, this.health, false, this.fo);
    }
    
    public SS thrust(int speed){
        switch(this.dir){
            case 1: return new SS(new Posn(this.p.x+speed, this.p.y), this.dir, this.t-1,this.imperial, this.ty, this.health, this.attacking, this.fo);
            case 2: return new SS(new Posn(this.p.x, this.p.y+speed), this.dir, this.t-1,this.imperial, this.ty, this.health, this.attacking, this.fo);
            case 3: return new SS(new Posn(this.p.x-speed, this.p.y), this.dir, this.t-1,this.imperial, this.ty, this.health, this.attacking, this.fo);
            case 0: return new SS(new Posn(this.p.x, this.p.y-speed), this.dir, this.t-1,this.imperial, this.ty, this.health, this.attacking, this.fo);
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
        Vector<SS> newV = new Vector();
        for(int i = 0; i < vec.size(); i++){
                    SS enemy = vec.elementAt(i);
                    
                    
                    if(enemy.p.x < this.p.x && enemy.p.x > this.p.x - range){
                        if(enemy.p.y < this.p.y && enemy.p.y > this.p.y-range){
                            q2++;
                        }
                        else if(enemy.p.y > this.p.y && enemy.p.y < this.p.y+range){
                            q3++;
                        }
                    }
                    else if(enemy.p.x > this.p.x && enemy.p.x < this.p.x + range){
                        if(enemy.p.y < this.p.y && enemy.p.y > this.p.y-range){
                            q1++;
                        }
                         else if(enemy.p.y > this.p.y && enemy.p.y < this.p.y+range){
                            q4++;
                        }
                    }
                    //Not sure if sweetspot is worth keeping
                    /*
                    switch(dir){
                        case 0: if(Math.abs(enemy.p.x-this.p.x) < 40 && enemy.p.y < this.p.y && enemy.p.y > this.p.y - range){
                                ss++; break;}
                        case 1: if(Math.abs(enemy.p.y-this.p.y) < 40 && enemy.p.x > this.p.x && enemy.p.x < this.p.x + range){
                                ss++; break;}
                        case 2: if(Math.abs(enemy.p.x-this.p.x) < 40 && enemy.p.y > this.p.y && enemy.p.y < this.p.y + range){
                                ss++; break;}
                        case 3: if(Math.abs(enemy.p.y-this.p.y) < 40 && enemy.p.x < this.p.x && enemy.p.x < this.p.x - range){
                                ss++; break;}
                    }
                            */
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
                    this.attacking?
                new FromFileImage(this.p, "tfa"+this.dir+".png"):
                new FromFileImage(this.p, "tf"+this.dir+".png"):
                IS.debug? 
                    new FromFileImage(this.p, "xwradar"+this.dir+".png"):
                    this.attacking?
                new FromFileImage(this.p, "xwa"+this.dir+".png"):
                new FromFileImage(this.p, "xw"+this.dir+".png");
    }
    
    public SS radar(int[] readout, int[] fReadout){
        //Decides how to turn based on enemy locations
        //Not implemented yet
        int turnRight = 0;
        int turnLeft = 0;
        SS newTF = new SS(this.p, this.dir, this.t,this.imperial, this.ty, this.health, this.attacking, this.fo);
        switch(this.ty){
               case HUNT: turnRight = turnRight + readout[1] + readout[4];
                          turnLeft = turnLeft + readout[2] + readout[3];
                          break;
               case EVADE: turnRight = turnRight + readout[2] + readout[3];
                          turnLeft = turnLeft + readout[1] + readout[4];  
                          break;}
        switch(this.fo){
               case IGNORE: break;
               case CLUSTER: turnRight = turnRight + fReadout[1] + fReadout[4];
                          turnLeft = turnLeft + fReadout[2] + fReadout[3]; 
                          break;
               case SCATTER: turnRight = turnRight + fReadout[2] + fReadout[3];
                          turnLeft = turnLeft + fReadout[1] + fReadout[4]; 
                          break;}
        return turnRight+turnLeft>0?
                (turnRight > turnLeft)?
                    newTF.turn(1):
                    newTF.turn(3):
                Math.random()>.5?
                    newTF.turn(1):
                    newTF.turn(3);
    }
    
    public SS react(Vector<SS> v, Vector<SS> f){
        
        SS newTF = new SS(this.p, this.dir, this.t,this.imperial, this.ty, this.health, this.attacking, this.fo);
        
        
        if(this.health<=0){
        return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health-1, this.attacking, this.fo); 
        }
        
        else{
            
        //Search
        int[] readout = this.quadrant(v);
        int[] fReadout = this.quadrant(f);
        
        if(this.t<=0){
            newTF.t = Tester.randomInt(minT, maxT);
        if(readout[0]+readout[1]+readout[2]+readout[3]+readout[4]<=0
                &&fReadout[0]+fReadout[1]+fReadout[2]+fReadout[3]+fReadout[4]<=0){
            if(Math.random()<chanceToTurn){
                    if(Math.random()>.5){ 
                        newTF = newTF.turn(3);
                    }
                    else{newTF = newTF.turn(1);}
                    
            }
        }
        else{
        newTF = newTF.radar(readout, fReadout);}
        }
        
        int toThrust = regspeed; 
        if(this.ty.equals(Type.EVADE)&&(readout[4]+readout[3])>0){
            toThrust = maxspeed;
        }
        
        newTF = newTF.justDontHitWalls().thrust(toThrust);
        
        return newTF;
    }
    }
    
    public SS swapType(Type toMake){
        switch(toMake){
            case HUNT: return new SS(this.p, this.dir, this.t, this.imperial, Type.HUNT, this.health, this.attacking, this.fo);
            case EVADE: return new SS(this.p, this.dir, this.t, this.imperial, Type.EVADE, this.health, this.attacking, this.fo);
    }
        return this;
    }
    
    public SS doDamage(int dam){
        return this.health>0? 
                new SS(this.p, this.dir, this.t, this.imperial, this.ty, Math.max(this.health-dam, 0), this.attacking, this.fo)
                :this;
    }
    
    public Object[] doDamageVec(Vector<SS> v, int dam){
        Vector<SS> newV = new Vector();
        boolean didNoDamage = true;
        for(int i = 0; i < v.size(); i++){
            SS enemy = v.elementAt(i);
                    switch(this.dir){
                        case 0: if(Math.abs(this.p.x - enemy.p.x) < range && enemy.p.y > this.p.y - range && enemy.p.y < this.p.y){
                            newV.add(enemy.doDamage(dam));
                        didNoDamage = false;
                        }
                        else{newV.add(enemy);}
                        break;
                        case 1: if(Math.abs(this.p.y - enemy.p.y) < range && enemy.p.x < this.p.x + range && enemy.p.x > this.p.x){
                            newV.add(enemy.doDamage(dam));
                        didNoDamage = false;
                        }
                        else{newV.add(enemy);}
                        break;
                        case 2: if(Math.abs(this.p.x - enemy.p.x) < range && enemy.p.y < this.p.y + range && enemy.p.y > this.p.y){
                            newV.add(enemy.doDamage(dam));
                        didNoDamage = false;
                        }
                        else{newV.add(enemy);}
                        break;  
                        case 3: if(Math.abs(this.p.y - enemy.p.y) < range && enemy.p.x > this.p.x - range && enemy.p.x < this.p.x){
                            newV.add(enemy.doDamage(dam));
                        didNoDamage = false;
                        }
                        else{newV.add(enemy);}
                        break;
                    }
        }
        return new Object[]{newV, didNoDamage};
    }
    
    public SS isAttacking(){
        return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, true, this.fo);
    }
    
    public SS isNotAttacking(){
        return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, false, this.fo);
    }
    
    public SS swapFormation(Formation form){
        switch(form){
            case CLUSTER: return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, this.attacking, Formation.CLUSTER);
            case SCATTER: return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, this.attacking, Formation.SCATTER);
            case IGNORE: return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, this.attacking, Formation.IGNORE);
        }
        return this;
    }
    
    
    
    
    
}
