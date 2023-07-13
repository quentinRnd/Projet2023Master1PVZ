package Agent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;



import Agent.Item.Projectile;
import Game.PlantVSZombieGame;
import Utils.Position;


public abstract class Agent implements Serializable{

    public long PV;
    public Position position;

    public Agent(long PV,Position p) 
    {
        this.PV=PV;
        this.position=p;
    }

    

    public  abstract  BufferedImage visit() throws IOException;
    public boolean enVie()
    {
        return this.PV>0;
    }
    public void toucher(long pvretire)
    {
        this.PV-=pvretire;
    }

}
