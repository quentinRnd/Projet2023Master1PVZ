package Agent.Item;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import Game.PlantVSZombieGame;
import Utils.ParametreJeux;
import Utils.Position;
import Visitor.VisitorAgentAffichage;

public class ProjectilePois extends Projectile {

    private final static long degatAppliquer=ParametreJeux.DEGATProjectilePois;
    private static ArrayList<BufferedImage> IMAGE=null;
    private int imagecompteur=0;

    public ProjectilePois(Position p) {
        super(p);
    }

    @Override
    public void takeTurn(PlantVSZombieGame pvz) {
        this.position.X+=1;
        ++imagecompteur;
    }

    @Override
    public BufferedImage visit() throws IOException {
        // TODO Auto-generated method stub
        
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
    public long degatAppliquer() {
        return degatAppliquer;
    }

    @Override
    public boolean appliqueDegat(PlantVSZombieGame pvz) {
        
        if(super.appliqueDegat(pvz))
        {
            return true;
        }else{
            if(pvz.Zombie_position(new Position(this.position.X+1,this.position.Y)))
            {
                takeTurn(pvz);
                return super.appliqueDegat(pvz);
            }else{
                return false;
            }
            
        }
        
    }

    @Override
    public TypeProjectile type() {
        // TODO Auto-generated method stub
        return TypeProjectile.Pois;
    }
    
}
