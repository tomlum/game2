package imperial.squadron;
import static imperial.squadron.Battlefield.imperialX;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;
import java.util.Vector;
import java.awt.Color;
//corresponding array for magazines, access that when magazine bleh

public class OverWorld extends World{
    static int startingScrap = 700;
    static double fr = .02;
    static int shipSpeed = 20;
    static SS[][] formations = new SS[][] 
    {new SS[] {new SS(new Posn(Battlefield.rwall, 100), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall+5, 100), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-5, 100), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-10, 100), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 100), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-20, 100), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-25, 100), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    
    new SS(new Posn(Battlefield.rwall, 500), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall+5, 500), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-5, 500), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-10, 500), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 500), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-20, 500), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-25, 500), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange)},
            
            
            
    new SS[] {new SS(new Posn(Battlefield.rwall, 300), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.TANK, SS.regRange),
    new SS(new Posn(Battlefield.rwall+5, 300), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.TANK, SS.regRange),
    new SS(new Posn(Battlefield.rwall-5, 300), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.TANK, SS.regRange),
    new SS(new Posn(Battlefield.rwall-10, 300), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.TANK, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 300), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.TANK, SS.regRange),
    new SS(new Posn(Battlefield.rwall-20, 300), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.TANK, SS.regRange),
    new SS(new Posn(Battlefield.rwall-25, 300), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.TANK, SS.regRange)},
       
    
    
    new SS[] {new SS(new Posn(Battlefield.rwall, 300), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.TANK, SS.regRange),
    new SS(new Posn(Battlefield.rwall+5, 300), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.TANK, SS.regRange),
    new SS(new Posn(Battlefield.rwall-5, 300), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.TANK, SS.regRange),
    
    new SS(new Posn(Battlefield.rwall-10, 260), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 260), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-20, 440), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-25, 440), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.SPEEDER, SS.regRange)},
    
    
    
    
    new SS[] {new SS(new Posn(Battlefield.rwall, 300), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall+5, 300), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    
    new SS(new Posn(Battlefield.rwall-10, 100), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 500), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.SPEEDER, SS.regRange),
    
    new SS(new Posn(Battlefield.rwall-20, 200), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange),
    new SS(new Posn(Battlefield.rwall-25, 400), 3, SS.launchT, false, SS.Type.HUNT, SS.startHealth, false, SS.Formation.CLUSTER, SS.Make.REG, SS.regRange)},
    
    new SS[] {
    new SS(new Posn(Battlefield.rwall-10, 100), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 200), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange),
     new SS(new Posn(Battlefield.rwall-10, 300), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 400), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange),
     new SS(new Posn(Battlefield.rwall-10, 500), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 250), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange),
     new SS(new Posn(Battlefield.rwall-10, 350), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 450), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange),
    new SS(new Posn(Battlefield.rwall-15, 150), 3, SS.launchT, false, SS.Type.EVADE, SS.startHealth, false, SS.Formation.SCATTER, SS.Make.SPEEDER, SS.regRange)
    }
      
       
            
            
    };
    
    int t;
    
    boolean battleHuh;
    ISS impSquad;
    RSS[] enemies;
    Battlefield theBattle;
    int scrap;
    
    public OverWorld(boolean b, Battlefield tb, ISS is, RSS[] e, int s, int t) {
	this.battleHuh = b;
        this.theBattle = tb;
        this.impSquad = is;
        this.enemies = e;
        this.scrap = s;
        this.t = t;
	}
    
    
    
    public OverWorld onTick(){
        
        boolean newBattleHuh = false;
        Battlefield newBat = this.theBattle;
        
        if(battleHuh){
            return new OverWorld(this.battleHuh, this.theBattle.onTick(), this.impSquad, this.enemies, this.scrap, this.t+1);
        }
        else{
            
        for(int i = 0; i < enemies.length; i++){
        if(Math.abs(this.impSquad.p.x - this.enemies[i].p.x) < 35 &&
           Math.abs(this.impSquad.p.y - this.enemies[i].p.y) < 35){
            newBattleHuh = true;
            newBat = new Battlefield(new Vector(), new Vector(), new Vader(new Posn(imperialX, 300), 1, false, false, 0), startingScrap, formations[Tester.randomInt(0, formations.length)]);
        }
        }
            
        RSS[] newRS = new RSS[enemies.length];
        if(this.t%3==0){
        for(int i = 0; i<enemies.length; i++){
            newRS[i] = enemies[i].move();
        }
        }
        else{newRS = enemies;
        }
        return new OverWorld(newBattleHuh, newBat, this.impSquad, newRS, this.scrap, this.t+1);
        }
    }
        
        
        
    public OverWorld onKeyEvent(String ke){
        if(this.battleHuh){
            return new OverWorld(this.battleHuh, this.theBattle.onKeyEvent(ke), this.impSquad, this.enemies, this.scrap, this.t);
        }
        else{
        return new OverWorld(this.battleHuh, this.theBattle, this.impSquad.move(ke), this.enemies, this.scrap, this.t);
        }
    }
    
        
    public WorldImage makeImage(){
        if(this.battleHuh){
                return this.theBattle.makeImage();
        }
        else{
        WorldImage galaxy = new FromFileImage(new Posn(480, 300), "galaxy.png");
        WorldImage theImage = new OverlayImages(galaxy, impSquad.image());
        for(int i = 0; i<this.enemies.length; i++){
            theImage = new OverlayImages(theImage, this.enemies[i].image());
        }
        theImage = new OverlayImages(theImage, new TextImage(new Posn(480, 50), "Scrap : " + scrap, 30, Color.orange));
        return theImage;
    }
    }
    
    
    public static void main(String[] args) {
        Tester.testQuadrant(100);
        //w = new Battlefield(new Vector(), new Vector(), new Vader(new Posn(imperialX, 300), 1, false, false, 0), startingScrap)
        OverWorld w = new OverWorld(false, new Battlefield(new Vector(), new Vector(), new Vader(new Posn(imperialX, 300), 1, false, false, 0), startingScrap, new SS[]{}),
                new ISS(new Posn (200, 200), true, 1), 
                new RSS[] {new RSS(new Posn(400, 400), true, 3)}, 
                startingScrap,
                0);
        w.bigBang(960, 600, fr);
    }

}
