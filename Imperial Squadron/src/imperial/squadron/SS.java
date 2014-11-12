package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

//SpaceShip
public abstract class SS{
    Posn p;
    int dir;
    int t;
    int speed;
    
    public SS(Posn p, int dir, int t){
      this.p = p;  
      this.dir = dir;
      this.t = t;
    }
    public SS turn(int times){
        return new SS(this.p, (this.dir+times)%4, this.t);
    }
    
    
    
    public SS thrust(int speed){
        switch(this.dir){
            case 1: return new SS(new Posn(this.p.x+speed, this.p.y), this.dir, this.t-1);
            case 2: return new SS(new Posn(this.p.x, this.p.y+speed), this.dir, this.t-1);
            case 3: return new SS(new Posn(this.p.x-speed, this.p.y), this.dir, this.t-1);
            case 0: return new SS(new Posn(this.p.x, this.p.y-speed), this.dir, this.t-1);
        }
        return this;
    }
    
    
}
