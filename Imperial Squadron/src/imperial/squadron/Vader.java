package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;


public class Vader{
    Posn p;
    int dir;
    int t;
    boolean attacking;
    boolean deployed;
    
    static int launcht = 20;
    static int range = 100;
    static int startHealth = 800;
    static int maxspeed = 12;
    static int regspeed = 4;
    static int damage = 6;
    static WorldImage blank = new RectangleImage(new Posn(0,0), 0, 0, new Black());
    WorldImage image; 
    
    public Vader(Posn p, int dir, boolean attacking, boolean deployed, int t){
        this.p = p;
        this.dir = dir;
        this.attacking = attacking;
        this.deployed = deployed;
        this.t = t;
        this.image = new FromFileImage(this.p, "vader/vader"+this.dir+".png");
    }
    
    public WorldImage image(){
        if(this.deployed){
        if(!this.attacking){
            return this.image;
        }
        else{
            return new FromFileImage(this.p, "vader/vaderlaser" + this.dir + ".png");
        }
        }
        else return new FromFileImage(new Posn(this.p.x*5, this.p.y), "aim.png");
    }
    
    public Vader thrust(int speed){
        switch(this.dir){
            case 1: return new Vader(new Posn(this.p.x+speed, this.p.y), this.dir, this.attacking, this.deployed, this.t+1);
            case 2: return new Vader(new Posn(this.p.x, this.p.y+speed), this.dir, this.attacking, this.deployed, this.t+1);
            case 3: return new Vader(new Posn(this.p.x-speed, this.p.y), this.dir, this.attacking, this.deployed, this.t+1);
            case 0: return new Vader(new Posn(this.p.x, this.p.y-speed), this.dir, this.attacking, this.deployed, this.t+1);
        }
        return this;
    
    }
    
    public Vader justDontHitWalls(){
        //have turn condition based on enemies/allies/behavior also
        //for now it's just one direction
        
        switch(this.dir){
            case 1: if(this.p.x+maxspeed*2>Battlefield.rwall){return new Vader(this.p, 3, this.attacking, this.deployed, this.t);} break;
            case 2: if(this.p.y+maxspeed*2>Battlefield.dwall){return new Vader(this.p, 0, this.attacking, this.deployed, this.t);} break;
            case 3: if(this.p.x-maxspeed*2<Battlefield.lwall){return new Vader(new Posn(Battlefield.imperialX, this.p.y), 1, this.attacking, false, 0);} break;
            case 0: if(this.p.y-maxspeed*2<Battlefield.uwall){return new Vader(this.p, 2, this.attacking, this.deployed, this.t);} break;
        }
        return this;
    }
    
    
    public Vader turn(String ke){
        switch(ke){
            case "right":if(this.dir == 0 || this.dir == 2) return new Vader(this.p, 1, this.attacking, this.deployed, this.t); break;
            case "down":if(this.dir == 1 || this.dir == 3) return new Vader(this.p, 2, this.attacking, this.deployed, this.t); break;
            case "left":if(this.dir == 0 || this.dir == 2) return new Vader(this.p, 3, this.attacking, this.deployed, this.t); break;
            case "up":if(this.dir == 1 || this.dir == 3) return new Vader(this.p, 0, this.attacking, this.deployed, this.t); break;
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
    
    
    public Object[] doDamageVec(Vector<SS> v){
        Vector<SS> newV = new Vector();
        int dam = damage;
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
    
    public Vader isAttacking(){
        return new Vader(this.p, this.dir, true, this.deployed, this.t);
    }
    
    public Vader isNotAttacking(){
        return new Vader(this.p, this.dir, false, this.deployed, this.t);
    }
    
}
