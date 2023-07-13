package Agent.Plant;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


import Agent.Item.Projectile;
import Agent.Item.ProjectilePois;
import Game.EnumActionJeu;
import Game.PlantVSZombieGame;
import Utils.ParametreJeux;
import Utils.Position;
import Visitor.VisitorAgentAffichage;

public class Pois extends Plant {

    private static final long PVPois=ParametreJeux.PVPois;
    public static final long Cout=ParametreJeux.CoutPois;
    
    private static ArrayList<BufferedImage> IMAGE=null;
    //intervale de tir du pois en tour
    private static final long intervaleTir=ParametreJeux.intervaleTirPois;

    private long intervaleTirJeu;

    private int imagecompteur=0;

    public Pois(Position p) {
        super(PVPois,p);
        this.intervaleTirJeu=0;
        
    }
    public Pois(Position p,EnumActionJeu a) {
        super(PVPois,p,a);
        this.intervaleTirJeu=0;
        
    }

    @Override
    public ArrayList<Projectile> takeTurn(PlantVSZombieGame pvz) {
        ++imagecompteur;
        ArrayList<Projectile> tirer =new ArrayList<Projectile>();
        if(this.intervaleTirJeu%intervaleTir==0)
        {
            this.intervaleTirJeu=1;
            tirer.add(new ProjectilePois(new Position(this.position.X,this.position.Y)));
            if(super.relierAction)
            {
                tirer.get(tirer.size()-1).rajouteAction(action);
            }
        }else{
            ++this.intervaleTirJeu;
        }
        return tirer;
        
        
    }

    @Override
    public BufferedImage visit() throws IOException {
        BufferedImage retour;
        if(IMAGE==null)
        {
            IMAGE=VisitorAgentAffichage.visit(this);
        }
        imagecompteur=imagecompteur%IMAGE.size();
        retour=IMAGE.get(imagecompteur);
        return retour;
    }

    @Override
    public long cout() {
        return Cout;
    }

    @Override
    public TypePlant type() {
        // TODO Auto-generated method stub
        return TypePlant.Pois;
    }
    
}
