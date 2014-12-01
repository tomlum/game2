package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;


public class ISS extends BigShip{
    static int speed = 23;
    
    public ISS(Posn p, boolean hh, int d){
        super(p, hh, d);
    }
    
    public WorldImage image(){
        return new FromFileImage(this.p, "miniiss" + this.dir + ".png");
    }
    
    
    
    public ISS move(String ke){
        switch(ke){
            case "w": if(this.p.y-speed>Battlefield.uwall){return new ISS(new Posn(this.p.x, this.p.y - speed), this.hereHuh, 0);}
            break;
            case "d": if(this.p.x+speed<Battlefield.rwall){return new ISS(new Posn(this.p.x + speed, this.p.y), this.hereHuh, 1);}
            break;
            case "s": if(this.p.y+speed<Battlefield.dwall){return new ISS(new Posn(this.p.x, this.p.y + speed), this.hereHuh, 2);}
            break;
            case "a": if(this.p.x-speed>Battlefield.lwall){return new ISS(new Posn(this.p.x - speed, this.p.y), this.hereHuh, 3);}
            break;
        }
        return this;
    }
    
}
