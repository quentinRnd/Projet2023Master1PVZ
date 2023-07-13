package Agent.Zombie;

import java.awt.image.BufferedImage;
import java.io.IOException;

import Agent.Agent;
import Utils.ParametreJeux;
import Utils.Position;
import Visitor.VisitorAgentAffichage;

public class ZombieAugmenter  extends Zombie {

    public ZombieAugmenter(Position p) {
        super(p);
        super.PV=ParametreJeux.PVZombieAugmenter; 
        super.Vitesse=ParametreJeux.VitesseZombieAugmenter;
        super.Attack=ParametreJeux.AttackZombieAugmenter;
    }

    @Override
    public BufferedImage visit() throws IOException {
        return VisitorAgentAffichage.visit(this);
    }

    @Override
    public TypeZombie typeZombie()
    {
        return TypeZombie.ZombieAugmenter;
    }
    
}
