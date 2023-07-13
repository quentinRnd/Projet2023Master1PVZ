package Agent.Plant;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


import Agent.Item.Projectile;
import Agent.Item.ProjectileDragon;
import Game.EnumActionJeu;
import Game.PlantVSZombieGame;
import Utils.ParametreJeux;
import Utils.Position;
import Visitor.VisitorAgentAffichage;

public class Dragon extends Plant {

    private static final long PVDragon=ParametreJeux.PVDragon;
    public static final long Cout=ParametreJeux.CoutDragon;
    private static final long intervaleTir=ParametreJeux.intervaleTirDragon;

    private static ArrayList<BufferedImage> IMAGE=null;

    private long intervaleTirJeu;

    private int imagecompteur=0;

    public Dragon(Position p) {
        super(PVDragon,p);
        //TODO Auto-generated constructor stub
        intervaleTirJeu=0;
    }
    public Dragon(Position p,EnumActionJeu a) {
        super(PVDragon,p,a);
        this.intervaleTirJeu=0;
        
    }

    @Override
    public ArrayList<Projectile> takeTurn(PlantVSZombieGame pvz) {
        ArrayList<Projectile> tirer =new ArrayList<Projectile>();
        ++imagecompteur;
        if(this.intervaleTirJeu%intervaleTir==0)
        {
            this.intervaleTirJeu=1;
            for(int i=1;i<3;++i)
            {
                tirer.add(new ProjectileDragon(new Position(this.position.X+i,this.position.Y+1)));
                tirer.add(new ProjectileDragon(new Position(this.position.X+i,this.position.Y-1)));
                tirer.add(new ProjectileDragon(new Position(this.position.X+i,this.position.Y)));
                if(super.relierAction)
                {
                    tirer.get(tirer.size()-1).rajouteAction(action);
                    tirer.get(tirer.size()-2).rajouteAction(action);
                    tirer.get(tirer.size()-3).rajouteAction(action);
                }
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
        // TODO Auto-generated method stub
        return Cout;

    }

    @Override
    public TypePlant type() {
        // TODO Auto-generated method stub
        return TypePlant.Dragon;
    }
}
