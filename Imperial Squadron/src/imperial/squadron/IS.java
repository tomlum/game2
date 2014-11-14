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
    Vector<SS> imperialFleet = new Vector();
    Vector<SS> rebelFleet = new Vector();
    static double fr = .01;
    //array of friendlies and array of enemies
    
    public IS(Vector<SS> iF, Vector<SS> rF) {
		super();
                this.imperialFleet = iF;
                this.rebelFleet = rF;
	}
    
    
    
    public IS onTick(){
        Vector newIF = new Vector();
        Vector newRF = new Vector();
        for(int i = 0; i<imperialFleet.size(); i++){
            //newIF.add(imperialFleet.elementAt(i));
            newIF.add(imperialFleet.elementAt(i).react(rebelFleet));
        }
        for(int i = 0; i<rebelFleet.size(); i++){
            newRF.add(rebelFleet.elementAt(i).react(imperialFleet));
        }
        return new IS(newIF, newRF);
    }
        
        
        
    public IS onKeyEvent(String ke) {
        Vector newIF = imperialFleet;
        Vector newRF = rebelFleet;
            if(ke.equals("a")){
                newIF.add(new SS(new Posn(400, 400), Tester.randomInt(0, 3), SS.maxT, true));
            }
            if(ke.equals("d")){
                newRF.add(new SS(new Posn(400, 400), Tester.randomInt(0, 3), SS.maxT, false));
            }
            return new IS(newIF, newRF);
    }
    
        
    public WorldImage makeImage(){
        WorldImage theShips = new RectangleImage(new Posn(1440/2,900/2), 1440, 900, new Black());
        for(int i = 0; i<imperialFleet.size(); i++){
            SS current = imperialFleet.elementAt(i);
		theShips = new OverlayImages(theShips, current.image());
        }
        for(int i = 0; i<rebelFleet.size(); i++){
            SS current = rebelFleet.elementAt(i);
		theShips = new OverlayImages(theShips, current.image());
        }
        return theShips;
    }

    public static void main(String[] args) {
        Tester.testQuadrant(100);
        IS w = new IS(new Vector(), new Vector());
        w.bigBang(960, 600, fr);
    }
    
}
