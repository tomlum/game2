package imperial.squadron;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;
import java.awt.Color;


public class Battlefield extends World{
    static boolean debug = false;
    static int wallBuffer = 20;
    static SS.Type iType = SS.Type.HUNT;
    static SS.Type rType = SS.Type.HUNT;
    static SS.Formation iFormation = SS.Formation.IGNORE;
    static SS.Formation rFormation = SS.Formation.IGNORE;
    static SS.Make iMake = SS.Make.REG;
    static SS.Make rMake = SS.Make.TANK;
    static SS.Order iOrder = SS.Order.IGNORE;
    static int imperialX = 30;
    static int rebelX = 960 - imperialX;
    static int rwall = 960 - wallBuffer - imperialX;
    static int dwall = 600 - wallBuffer;
    static int lwall = wallBuffer + imperialX;
    static int uwall = wallBuffer;
    static int scrollRate = 30;
    Vector<SS> imperialFleet = new Vector();
    Vector<SS> rebelFleet = new Vector();
    Vader darth;
    int scrap;
    static int regCost = 50;
    static int speederCost = 25;
    static int tankCost = 100;
    SS[] rebelMagazine;
    int rebelBigShipNum;
    Res result;
    public enum Res{
    DONE,WIN,LOSE,DUNNO,START}
    
    //array of friendlies and array of enemies
    
    public Battlefield(Vector<SS> iF, Vector<SS> rF, Vader v, int scrap, SS[] rb, int rbsn, Res result) {
		super();
                this.imperialFleet = iF;
                this.rebelFleet = rF;
                this.darth = v;
                this.scrap = scrap;
                this.rebelMagazine = rb;
                this. rebelBigShipNum = rbsn;
                this.result = result;
	}
    
    
    
    public Battlefield onTick(){
        
        
        if(this.result.equals(Res.DUNNO)||this.result.equals(Res.START)){
            Res newResult = Res.DUNNO;
            
           if(rebelFleet.size()<=0 && this.rebelMagazine.length<=0){
               newResult = Res.WIN;
           }
           
           if(!this.result.equals(Res.START) && this.darth.deployed && this.imperialFleet.size() <= 0){
               newResult = Res.LOSE;
           }
           
           if(!this.result.equals(Res.START) && this.scrap<25 && this.imperialFleet.size()<=0){
               newResult = Res.LOSE;
           }
         
            
        Vector<SS> newIF = new Vector();
        Vector<SS> newRF = new Vector();
        Vector<SS> newNewIF = new Vector();
        Vector<SS> newNewRF = new Vector();
        Vader newDV = this.darth;
        int newScrap = this.scrap;
        
        
        
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
                switch(rebelFleet.elementAt(i).m){
                    case REG: newScrap += 20; break;
                    case SPEEDER: newScrap += 10; break;
                    case TANK: newScrap += 30; break;
                }
            }
            }
        
        for(int i = 0; i<newIF.size(); i++){
                Object[] theResults = newIF.elementAt(i).doDamageVec(newRF);
            newRF = (Vector<SS>) theResults[0];
                if(newRF.isEmpty()||(boolean)theResults[1]){
                newNewIF.add(newIF.elementAt(i).isNotAttacking());}
                else{newNewIF.add(newIF.elementAt(i).isAttacking());}
            }
        newIF = newNewIF;
            
        
        for(int i = 0; i<newRF.size(); i++){
                Object[] theResults = newRF.elementAt(i).doDamageVec(newIF);
            newIF = (Vector<SS>) theResults[0];
                if(newIF.isEmpty()||(boolean)theResults[1]){
                newNewRF.add(newRF.elementAt(i).isNotAttacking());}
                else{newNewRF.add(newRF.elementAt(i).isAttacking());}
            }
        newRF = newNewRF;
        
        
        if(darth.deployed){
        Object[] vaderReadout = darth.doDamageVec(newRF);
        newRF = (Vector<SS>)vaderReadout[0];
        newDV = (boolean)vaderReadout[1]?
                newDV.isNotAttacking():
                newDV.isAttacking();
        }
        
        if(newDV.deployed){
            newDV = newDV.justDontHitWalls();
        }
        if(newDV.deployed){
            newDV = newDV.t<Vader.launcht?
            newDV.thrust(Vader.maxspeed):
            newDV.thrust(Vader.regspeed);
        }
        
        return new Battlefield(newIF, newRF, newDV, newScrap, this.rebelMagazine, this.rebelBigShipNum, newResult);
        }
        else return this;
        }
        
        
        
    public Battlefield onKeyEvent(String ke) {
        
        
        
        if((this.result.equals(Res.DUNNO)||this.result.equals(Res.START))){
        
        
        Vector<SS> newIF = imperialFleet;
        Vector<SS> newRF = rebelFleet;
        Vader newDV = this.darth;
        int newScrap = scrap;
        Res newRes = this.result;
        
        if(!darth.deployed){
            if(ke.equals("a")&&newScrap>=regCost){
                    newIF.add(new SS(new Posn(lwall, this.darth.p.y), 1, SS.launchT, true, iType, SS.startHealth, false, iFormation, SS.Make.REG, SS.regRange));
                newScrap -= regCost;
                if(this.result.equals(Res.START)){
                    newRes = Res.DUNNO;
                }
            }
            if(ke.equals("q")&&newScrap>=speederCost){
                    newIF.add(new SS(new Posn(lwall, this.darth.p.y), 1, SS.launchT, true, iType, SS.speederStartHealth, false, iFormation, SS.Make.SPEEDER, SS.speederRange));
                newScrap -= speederCost;
                if(this.result.equals(Res.START)){
                    newRes = Res.DUNNO;
                }
            }
            if(ke.equals("z")&&newScrap>=tankCost){
                    newIF.add(new SS(new Posn(lwall, this.darth.p.y), 1, SS.launchT, true, iType, SS.tankStartHealth, false, iFormation, SS.Make.TANK, SS.tankRange));
                newScrap -= tankCost;
                if(this.result.equals(Res.START)){
                    newRes = Res.DUNNO;
                }
            }
        }
            
            if(rebelMagazine.length>0){
                for(int i = 0; i<rebelMagazine.length; i++){
                    newRF.add(rebelMagazine[i]);
                }
            }
            
            
            if(ke.equals("1")){
                switch(iType){
                    case EVADE: iType = SS.Type.HUNT; break;
                    case HUNT: iType = SS.Type.EVADE; break;
                }
            /*Vector<SS> newNewIF = new Vector();
                for(int i = 0; i<imperialFleet.size(); i++){
            newNewIF.add(newIF.elementAt(i).swapType(iType));
            }
                newIF = newNewIF;
                */
            }
            if(ke.equals("8")){
                switch(rType){
                    case EVADE: rType = SS.Type.HUNT; break;
                    case HUNT: rType = SS.Type.EVADE; break;
                }
            }
            
            if(ke.equals("3")){
                switch(iMake){
                    case REG: iMake = SS.Make.SPEEDER; break;
                    case SPEEDER: iMake = SS.Make.TANK; break;
                    case TANK: iMake = SS.Make.REG; break;
                }
            }
            
           
            if(ke.equals("2")){
                Vector newNewIF = new Vector();
                switch(iFormation){
                    case IGNORE: iFormation = SS.Formation.CLUSTER; break;
                    case CLUSTER: iFormation = SS.Formation.SCATTER; break;
                    case SCATTER: iFormation = SS.Formation.IGNORE; break;
                }
                /*for(int i = 0; i<imperialFleet.size(); i++){
            newNewIF.add(newIF.elementAt(i).swapFormation(iFormation));
            }
                newIF = newNewIF;*/
            }
            
            if(ke.equals(" ") && this.imperialFleet.size()>0){
                newDV.deployed = true;
            }
            
            
            
            if(newDV.deployed){
                newDV = newDV.turn(ke);
            }
            else{
                if(ke.equals("up")&&this.darth.p.y-scrollRate > this.uwall){
                newDV.p= new Posn(this.darth.p.x, this.darth.p.y - scrollRate);
            }
            if(ke.equals("down")&&this.darth.p.y+scrollRate < this.dwall){
                newDV.p= new Posn(this.darth.p.x, this.darth.p.y + scrollRate);
            }
            }
            
                    
            
            return new Battlefield(newIF, newRF, newDV, newScrap, new SS[]{}, this.rebelBigShipNum, newRes);
    }
        else {
          if(ke.equals(" ")){
            return new Battlefield(this.imperialFleet, this.rebelFleet, this.darth, this.scrap, this.rebelMagazine, this.rebelBigShipNum, Res.DONE);
          }
          else return this;
    }
    }
    
        
    public WorldImage makeImage(){
        WorldImage background = new FromFileImage(new Posn(960/2, 600/2), "stars.png");
        WorldImage theShips = Vader.blank;
        for(int i = 0; i<imperialFleet.size(); i++){
            SS current = imperialFleet.elementAt(i);
		theShips = new OverlayImages(theShips, current.image());
        }
        for(int i = 0; i<rebelFleet.size(); i++){
            SS current = rebelFleet.elementAt(i);
		theShips = new OverlayImages(theShips, current.image());
        }
        WorldImage HUD = new OverlayImages( 
                new TextImage(new Posn(200, 25), "Type: " + iType,16, Color.yellow),
                new TextImage(new Posn(200, 50), "Formation: " + iFormation,16, Color.yellow));
              
        HUD = new OverlayImages(HUD, new TextImage(new Posn(400, 25), "Scrap: " + scrap,20, Color.orange));
        WorldImage bigShips = new OverlayImages(new FromFileImage(new Posn(imperialX, 600/2), "iss.png"), new FromFileImage(new Posn(rebelX, 600/2), "rss.png"));
        theShips = new OverlayImages(theShips, darth.image());
        theShips = new OverlayImages(bigShips, theShips);
        theShips = new OverlayImages(background, theShips);
        theShips = new OverlayImages(theShips, new FromFileImage(new Posn(75, 25), iFormation+".png"));
        theShips = new OverlayImages(theShips, new FromFileImage(new Posn(25, 25), iType+".png"));
        theShips = new OverlayImages(theShips, new FromFileImage(new Posn(25, 475-150), "q.png"));
        theShips = new OverlayImages(theShips, new FromFileImage(new Posn(25, 525-150), "a.png"));
        theShips = new OverlayImages(theShips, new FromFileImage(new Posn(25, 575-150), "z.png"));
        
        
        if(this.result.equals(Res.WIN)){
            
            int newScrap = 0;
                for(int i = 0; i<this.imperialFleet.size(); i++){
                    newScrap += this.imperialFleet.elementAt(i).cost/3;
            }
            
        WorldImage Res = new OverlayImages(theShips, new FromFileImage(new Posn(960/2,600/2), "shade.png"));
        Res = new OverlayImages(Res, new TextImage(new Posn(960/2, 100), "---"+this.result+"---", 30, Color.red));
        Res = new OverlayImages(Res, new TextImage(new Posn(960/2, 200), "-  "+OverWorld.formations[this.rebelBigShipNum-1].length+ " ships destroyed" +"  -", 20, Color.green));
        Res = new OverlayImages(Res, new TextImage(new Posn(960/2, 300), "-  "+this.scrap+"  -", 20, Color.yellow));
        Res = new OverlayImages(Res, new TextImage(new Posn(960/2, 400), "- + "+newScrap+"   -", 20, Color.red));
        Res = new OverlayImages(Res, new TextImage(new Posn(960/2, 500), "-  "+(newScrap+this.scrap)+"  -", 20, Color.orange));
        return Res;
        }
        
        
        return new OverlayImages(theShips, HUD);
    }

    
    
}
