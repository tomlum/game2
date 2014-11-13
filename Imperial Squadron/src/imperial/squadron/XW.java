package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

public class XW extends SS {
    
    public XW(Posn p, int dir, int t){
      super(p, dir, t);
    }
    
    
    public WorldImage image(){
        return IS.debug? new FromFileImage(this.p, "tfradar"+this.dir+".png"):
                new FromFileImage(this.p, "tf"+this.dir+".png");
    }
    
    
    
}
    

