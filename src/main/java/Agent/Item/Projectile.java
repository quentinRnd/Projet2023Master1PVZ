package Agent.Item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import Game.EnumActionJeu;
import Game.PlantVSZombieGame;
import Utils.Position;


public abstract class Projectile implements Serializable {
    public Position position;
    public EnumActionJeu action;

    public Boolean relierAction;

    public static enum TypeProjectile {Pois,Dragon}

    public Projectile(Position p)
    {
        this.position=p;
        relierAction=false;
    }

    public Projectile(Position p,EnumActionJeu a)
    {
        this.position=p;
        this.action=a;
        relierAction=true;
    }

    public void rajouteAction(EnumActionJeu a)
    {
        relierAction=true;
        this.action=a;
    }


    public  abstract BufferedImage visit() throws IOException;

    public  abstract void takeTurn(PlantVSZombieGame pvz);

    public boolean appliqueDegat(PlantVSZombieGame pvz)
    {
        return pvz.ZombiePositionAttack(position, degatAppliquer()) ;
    }

    public abstract TypeProjectile type();

    public abstract long degatAppliquer();
}
