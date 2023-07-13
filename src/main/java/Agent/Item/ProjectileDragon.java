package Agent.Item;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Agent.Plant.Dragon;
import Game.PlantVSZombieGame;
import Utils.ParametreJeux;
import Utils.Position;
import Visitor.VisitorAgentAffichage;

public class ProjectileDragon extends Projectile {
    private static long DEGAT=ParametreJeux.DEGATProjectileDragon;
    public static final long EXISTANCE_MAX=1;

    private static BufferedImage IMAGE=null;
    
    public long existance;
    public ProjectileDragon(Position p) {
        super(p);
        this.existance=EXISTANCE_MAX;
        //TODO Auto-generated constructor stub
    }

    @Override
    public BufferedImage visit() throws IOException {
        // TODO Auto-generated method stub
        if(IMAGE==null)
        {
            IMAGE=VisitorAgentAffichage.visit(this);
        }
        return IMAGE;
        
    }

    @Override
    public void takeTurn(PlantVSZombieGame pvz) {
        --existance;
    }

    @Override
    public long degatAppliquer() {
        return DEGAT;
    }

    @Override
    public boolean appliqueDegat(PlantVSZombieGame pvz)
    {
        
        if(existance<=0)
        {
            boolean retour= pvz.ZombiePositionAttack(position, degatAppliquer()) ;
            pvz.deleteProjectile(this);
            return retour;
        }else{
            return pvz.ZombiePositionAttack(position, degatAppliquer());
        }
        
    }

    @Override
    public TypeProjectile type() {
        // TODO Auto-generated method stub
        return TypeProjectile.Dragon;
    }
    
    
}
