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
    static SS.Formation iFormation = SS.Formation.IGNORE;
    static SS.Formation rFormation = SS.Formation.IGNORE;
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
        Vector<SS> newIF = new Vector();
        Vector<SS> newRF = new Vector();
        Vector<SS> newNewIF = new Vector();
        Vector<SS> newNewRF = new Vector();
        
        
        for(int i = 0; i<imperialFleet.size(); i++){
            //newIF.add(imperialFleet.elementAt(i));
            newIF.add(imperialFleet.elementAt(i).react(rebelFleet, imperialFleet));
        }
        for(int i = 0; i<rebelFleet.size(); i++){
            newRF.add(rebelFleet.elementAt(i).react(imperialFleet, rebelFleet));
        }
        
        
        int iRemoved = 0;
        for(int i = 0; i<imperialFleet.size(); i++){
            if(imperialFleet.elementAt(i).health < -4){
                newIF.remove(i-iRemoved);
                iRemoved++;
            }
            }
            
        int rRemoved = 0;
        for(int i = 0; i<rebelFleet.size(); i++){
            if(rebelFleet.elementAt(i).health < -4){
                newRF.remove(i-rRemoved);
                rRemoved++;
            }
            }
        
        for(int i = 0; i<newIF.size(); i++){
                Object[] theResults = newIF.elementAt(i).doDamageVec(newRF, SS.laserDamage);
            newRF = (Vector<SS>) theResults[0];
                if(newRF.isEmpty()||(boolean)theResults[1]){
                newNewIF.add(newIF.elementAt(i).isNotAttacking());}
                else{newNewIF.add(newIF.elementAt(i).isAttacking());}
            }
        newIF = newNewIF;
            
        
        for(int i = 0; i<newRF.size(); i++){
                Object[] theResults = newRF.elementAt(i).doDamageVec(newIF, SS.laserDamage);
            newIF = (Vector<SS>) theResults[0];
                if(newIF.isEmpty()||(boolean)theResults[1]){
                newNewRF.add(newRF.elementAt(i).isNotAttacking());}
                else{newNewRF.add(newRF.elementAt(i).isAttacking());}
            }
        newRF = newNewRF;
        
        return new IS(newIF, newRF);
    }
        
        
        
    public IS onKeyEvent(String ke) {
        Vector<SS> newIF = imperialFleet;
        Vector<SS> newRF = rebelFleet;
            if(ke.equals("a")){
                newIF.add(new SS(new Posn(lwall, Tester.randomInt(uwall, dwall)), 1, SS.maxT, true, iType, SS.startHealth, false, iFormation));
            }
            if(ke.equals("d")){
                newRF.add(new SS(new Posn(rwall, Tester.randomInt(uwall, dwall)), 3, SS.maxT, false, rType, SS.startHealth, false, rFormation));
            }
            if(ke.equals("1")){
                switch(iType){
                    case EVADE: iType = SS.Type.HUNT; break;
                    case HUNT: iType = SS.Type.EVADE; break;
                }
            }
            if(ke.equals("2")){
                switch(iType){
                    case EVADE: rType = SS.Type.HUNT; break;
                    case HUNT: rType = SS.Type.EVADE; break;
                }
            }
            
           
            if(ke.equals("3")){
                Vector newNewIF = new Vector();
                switch(iFormation){
                    case IGNORE: iFormation = SS.Formation.CLUSTER; break;
                    case CLUSTER: iFormation = SS.Formation.SCATTER; break;
                    case SCATTER: iFormation = SS.Formation.IGNORE; break;
                }
                for(int i = 0; i<imperialFleet.size(); i++){
            newNewIF.add(newIF.elementAt(i).swapFormation(iFormation));
            }
                newIF = newNewIF;
            }
                    
            
            return new IS(newIF, newRF);
    }
    
        
    public WorldImage makeImage(){
        WorldImage theShips = new FromFileImage(new Posn(960/2, 600/2), "stars.png");
        for(int i = 0; i<imperialFleet.size(); i++){
            SS current = imperialFleet.elementAt(i);
		theShips = new OverlayImages(theShips, current.image());
        }
        for(int i = 0; i<rebelFleet.size(); i++){
            SS current = rebelFleet.elementAt(i);
		theShips = new OverlayImages(theShips, current.image());
        }
        WorldImage HUD = new OverlayImages( 
                new TextImage(new Posn(400,80)," |Your Type is " + iType + "|  |Your Formation is " + iFormation + "|" + " |Their type is " + rType,10, new Red()),
                new TextImage(new Posn(400,40), imperialFleet.size() + " Tie Fighters and " + rebelFleet.size() + " X Wings",10, new Red()));
        return new OverlayImages(theShips, HUD);
    }

    public static void main(String[] args) {
        Tester.testQuadrant(100);
        IS w = new IS(new Vector(), new Vector());
        w.bigBang(960, 600, fr);
    }
    
}
