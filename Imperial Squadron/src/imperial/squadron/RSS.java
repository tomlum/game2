package imperial.squadron;
import javalib.worldimages.FromFileImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

public class RSS extends BigShip{
    static int speed = 1;
    public RSS(Posn p, boolean hh, int d){
        super(p, hh, d);
    }
    
    public WorldImage image(){
        if(this.hereHuh){
        return new FromFileImage(this.p, "minirss.png");
        }
        else return new FromFileImage(this.p, "minirssdestroyed.png");
    }
    
    public RSS move(){
        if(this.hereHuh){
        return new RSS(new Posn(this.p.x-speed, this.p.y), this.hereHuh, dir);
        }
        else return this;
        
    }
    
    public RSS destroy(boolean b){
        return new RSS(new Posn(this.p.x-speed, this.p.y), !b, 0);
    }
    
}
