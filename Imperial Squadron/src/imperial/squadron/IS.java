package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;


public class IS extends World {
    static boolean debug = false;
    static int wallbuffer = 20;
    static int rwall = 960 - wallbuffer;
    static int dwall = 600 - wallbuffer;
    static int lwall = wallbuffer;
    static int uwall = wallbuffer;
    Vector<TF> v = new Vector();
    //array of friendlies and array of enemies
    
    public IS(Vector v) {
		super();
                this.v = v;
	}
    
    
    
    public IS onTick(){
        Vector newV = new Vector();
        for(int i = 0; i<v.size(); i++){
            newV.add(v.elementAt(i).react());
        }
        return new IS(newV);
        }
        
        
        
    public IS onKeyEvent(String ke) {
        Vector newV = v;
            if(ke.equals("a")){
                newV.add(new TF(new Posn(400, 400), 2, TF.maxT));
            }
            return new IS(newV);
    }
    
        
    public WorldImage makeImage(){
        WorldImage theTFs = new RectangleImage(new Posn(1440/2,900/2), 1440, 900, new Black());
        for(int i = 0; i<v.size(); i++){
            TF current = v.elementAt(i);
		theTFs = new OverlayImages(theTFs, current.image());
        }
        return theTFs;
    }

    public static void main(String[] args) {
        IS w = new IS(new Vector());
        w.bigBang(960, 600, .08);
    }
    
}
