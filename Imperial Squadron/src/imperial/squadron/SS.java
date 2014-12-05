package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;
//if no support then vadar's shield goes down
//move like pacman
//LAUNCH MODE AND THEN AIM WHERE TO FIRE IT, ALSO 3 TYPES
//enemy has different behvaiors roward vadar

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
    Make m;
    int range;
    WorldImage image;
    
    public enum Make{
    REG,SPEEDER,TANK}
    public enum Type{
    HUNT,EVADE}
    enum Formation{
    SCATTER,CLUSTER, IGNORE}
    enum Order{
    IGNORE, SHIELD, PART}
    
    
    //radius of radar detection
    static int regRange = 130;
    static int speederRange = 160;
    static int tankRange = 100;
    
    static int startHealth = 800;
    static int speederStartHealth = 650;
    static int tankStartHealth = 1000;
    
    static int maxspeed = 5;
    static int regspeed = 3;
    static int speederMaxspeed = 8;
    static int speederRegspeed = 4;
    static int tankMaxspeed = 2;
    static int tankRegspeed = 1;
    static int launchSpeed = 8;
    
    static int laserDamage = 2;
    static int speederDamage = 1;
    static int tankDamage = 4;
    
    //max time before turn
    static int maxT = 50;
    static int minT = 40;
    static int speederMaxT = 40;
    static int speederMinT = 30;
    static int launchT = 80;
    static double chanceToTurn = .6;
    
    int cost;
    
    public SS(Posn p, int dir, int t, boolean i, Type ty, int health, boolean at, Formation fo, Make m, int r){
      this.p = p;  
      this.dir = dir;
      this.t = t;
      this.imperial = i;
      this.ty = ty;
      this.health = health;
      this.attacking = at;
      this.fo = fo;
      this.m = m;
      this.range = r;
      
     switch(this.m){
         case REG: this.cost = Battlefield.regCost; break;
         case SPEEDER: this.cost = Battlefield.speederCost; break;
         case TANK: this.cost = Battlefield.tankCost; break;
     }
      
    }
    
    public boolean iHuh(){
    return this.imperial;
    }
    
    public SS turn(int times){
        return new SS(this.p, (this.dir+times)%4, this.t,this.imperial, this.ty, this.health, false, this.fo, this.m, this.range);
    }
    
    public SS thrust(int speed){
        switch(this.dir){
            case 1: return new SS(new Posn(this.p.x+speed, this.p.y), this.dir, this.t-1,this.imperial, this.ty, this.health, this.attacking, this.fo, this.m, this.range);
            case 2: return new SS(new Posn(this.p.x, this.p.y+speed), this.dir, this.t-1,this.imperial, this.ty, this.health, this.attacking, this.fo, this.m, this.range);
            case 3: return new SS(new Posn(this.p.x-speed, this.p.y), this.dir, this.t-1,this.imperial, this.ty, this.health, this.attacking, this.fo, this.m, this.range);
            case 0: return new SS(new Posn(this.p.x, this.p.y-speed), this.dir, this.t-1,this.imperial, this.ty, this.health, this.attacking, this.fo, this.m, this.range);
        }
        return this;
    }
    
    public SS justDontHitWalls(){
        //have turn condition based on enemies/allies/behavior also
        //for now it's just one direction
        
        switch(this.dir){
            case 1: if(this.p.x+maxspeed*2>Battlefield.rwall){return this.turn(2);} break;
            case 2: if(this.p.y+maxspeed*2>Battlefield.dwall){return this.turn(2);} break;
            case 3: if(this.p.x-maxspeed*2<Battlefield.lwall){return this.turn(2);} break;
            case 0: if(this.p.y-maxspeed*2<Battlefield.uwall){return this.turn(2);} break;
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
                    //Not sure if sweetspot is worth keeping, i.e. when enemy is directly in front
                    //keep going forward
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
                Battlefield.debug? 
                    new FromFileImage(this.p, "tf/tfradar"+this.dir+".png"):
                    this.attacking?
                new FromFileImage(this.p, "tf/"+this.m+"tfa"+this.dir+".png"):
                new FromFileImage(this.p, "tf/"+this.m+"tf"+this.dir+".png"):
                Battlefield.debug? 
                    new FromFileImage(this.p, "xw/xwradar"+this.dir+".png"):
                    this.attacking?
                new FromFileImage(this.p, "xw/"+this.m+"xwa"+this.dir+".png"):
                new FromFileImage(this.p, "xw/"+this.m+"xw"+this.dir+".png");
    }
    
    public SS radar(int[] readout, int[] fReadout){
        //Decides how to turn based on enemy locations
        //Not implemented yet
        double turnRight = 0;
        double turnLeft = 0;
        SS newTF = new SS(this.p, this.dir, this.t,this.imperial, this.ty, this.health, this.attacking, this.fo, this.m, this.range);
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
                          turnLeft = turnLeft + fReadout[2]/2 + fReadout[3]/2; 
                          break;
               case SCATTER: turnRight = turnRight + fReadout[2]/2 + fReadout[3]/2;
                          turnLeft = turnLeft + fReadout[1]/2 + fReadout[4]/2; 
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
        
        SS newTF = new SS(this.p, this.dir, this.t,this.imperial, this.ty, this.health, this.attacking, this.fo, this.m, this.range);
        
        
        if(this.health<=0){
        return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health-1, this.attacking, this.fo, this.m, this.range); 
        }
        
        else{
            
        //Search
        int[] readout = this.quadrant(v);
        int[] fReadout = this.quadrant(f);
        
        if(this.t<=0){
            if(this.m.equals(Make.SPEEDER)){
            newTF.t = Tester.randomInt(speederMinT, speederMaxT);
            }
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
        switch(this.m){
            case REG: toThrust = regspeed; break;
            case SPEEDER: toThrust = speederRegspeed; break;
            case TANK: toThrust = tankRegspeed; break;
        } 
        if((this.ty.equals(Type.EVADE)&&(readout[4]+readout[3])>0)){
            switch(this.m){
            case REG: toThrust = maxspeed; break;
            case SPEEDER: toThrust = speederMaxspeed; break;
            case TANK: toThrust = tankMaxspeed; break;
        } 
        }
        if(this.t>this.maxT){
            toThrust = launchSpeed;
        }
        
        newTF = newTF.justDontHitWalls().thrust(toThrust);
        
        return newTF;
    }
    }
    
    public SS swapType(Type toMake){
        switch(toMake){
            case HUNT: return new SS(this.p, this.dir, this.t, this.imperial, Type.HUNT, this.health, this.attacking, this.fo, this.m, this.range);
            case EVADE: return new SS(this.p, this.dir, this.t, this.imperial, Type.EVADE, this.health, this.attacking, this.fo, this.m, this.range);
    }
        return this;
    }
    
    public SS doDamage(int dam){
        return this.health>0? 
                new SS(this.p, this.dir, this.t, this.imperial, this.ty, Math.max(this.health-dam, 0), this.attacking, this.fo, this.m, this.range)
                :this;
    }
    
    public Object[] doDamageVec(Vector<SS> v){
        Vector<SS> newV = new Vector();
        int dam = laserDamage;
        switch(this.m){
            case SPEEDER: dam = speederDamage; break;
            case TANK: dam = tankDamage; break;
        }
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
        return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, true, this.fo, this.m, this.range);
    }
    
    public SS isNotAttacking(){
        return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, false, this.fo, this.m, this.range);
    }
    
    public SS swapFormation(Formation form){
        switch(form){
            case CLUSTER: return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, this.attacking, Formation.CLUSTER, this.m, this.range);
            case SCATTER: return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, this.attacking, Formation.SCATTER, this.m, this.range);
            case IGNORE: return new SS(this.p, this.dir, this.t, this.imperial, this.ty, this.health, this.attacking, Formation.IGNORE, this.m, this.range);
        }
        return this;
    }
    
    
    
    
    
}
