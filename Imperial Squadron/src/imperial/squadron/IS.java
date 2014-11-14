package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;


public class IS extends World {
    static boolean debug = false;
    static int wallBuffer = 20;
    static SS.Type iType = SS.Type.HUNT;
    static SS.Type rType = SS.Type.HUNT;
    static int rwall = 960 - wallBuffer;
    static int dwall = 600 - wallBuffer;
    static int lwall = wallBuffer;
    static int uwall = wallBuffer;
    Vector<SS> imperialFleet = new Vector();
    Vector<SS> rebelFleet = new Vector();
    static double fr = .02;
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
        
        
        for(int i = 0; i<imperialFleet.size(); i++){
                int removed = 0;
            if(imperialFleet.elementAt(i).health < -4){
                newIF.remove(i-removed);
                removed++;
            }
            }
            
            for(int i = 0; i<rebelFleet.size(); i++){
                int removed = 0;
            if(rebelFleet.elementAt(i).health < -4){
                newRF.remove(i-removed);
                removed++;
            }
            }
        
        for(int i = 0; i<imperialFleet.size(); i++){
            newRF = imperialFleet.elementAt(i).doDamageVec(newRF, SS.laserDamage);
            }
            
        for(int i = 0; i<rebelFleet.size(); i++){
            newIF = rebelFleet.elementAt(i).doDamageVec(newIF, SS.laserDamage);
            }
        
        
        
       
        return new IS(newIF, newRF);
    }
        
        
        
    public IS onKeyEvent(String ke) {
        Vector<SS> newIF = imperialFleet;
        Vector<SS> newRF = rebelFleet;
            if(ke.equals("a")){
                newIF.add(new SS(new Posn(lwall, Tester.randomInt(uwall, dwall)), 1, SS.maxT, true, iType, SS.startHealth));
            }
            if(ke.equals("d")){
                newRF.add(new SS(new Posn(rwall, Tester.randomInt(uwall, dwall)), 3, SS.maxT, false, rType, SS.startHealth));
            }
            if(ke.equals("1")){
                switch(iType){
                    case FLEE: iType = SS.Type.HUNT; break;
                    case HUNT: iType = SS.Type.FLEE; break;
                }
            }
            if(ke.equals("2")){
                switch(iType){
                    case FLEE: rType = SS.Type.HUNT; break;
                    case HUNT: rType = SS.Type.FLEE; break;
                }
            }
            
            //Formation
            /*
            if(ke.equals("3")){
                Vector newNewIF = new Vector();
                for(int i = 0; i<imperialFleet.size(); i++){
            newNewIF.add(newIF.elementAt(i).swapFormation(rFormation));
            }
                newIF = newNewIF;
            }
                    */
            
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
        return new OverlayImages(theShips, new TextImage(new Posn(400,40), imperialFleet.size() + " Tie Fighters and " + rebelFleet.size() + " X Wings" + " |====| You Type is " + iType,10, new Red()));
    }

    public static void main(String[] args) {
        Tester.testQuadrant(100);
        IS w = new IS(new Vector(), new Vector());
        w.bigBang(960, 600, fr);
    }
    
}
