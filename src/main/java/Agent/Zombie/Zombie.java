package Agent.Zombie;

import java.awt.image.BufferedImage;
import java.io.IOException;

import Agent.Agent;
import Game.PlantVSZombieGame;
import Utils.ParametreJeux;
import Utils.Position;
import Visitor.VisitorAgentAffichage;

/* 
 * le zombie il a plusieur regle
 * 1- il avance tout droit parce que y'a personne sur la prochaine case
 * 2- il choute sur quelqu'un parce que il est sur une personne ou il va être sur une personne
*/

public class Zombie extends Agent {
    public static enum TypeZombie{
        Zombie,ZombieAugmenter
    }
    private static final long PVZombie =ParametreJeux.PVZombie;

    protected long Vitesse=ParametreJeux.VitesseZombie;

    protected long Attack=ParametreJeux.AttackZombie;
    
    private static final long VitesseBase=1;

    private long nbTourStun;

    private boolean stun;

    public Zombie(Position p) {
        super(PVZombie,p);
        stun=false;
        vitesse_actuelle=VitesseBase;
    }

    private long vitesse_actuelle;

    public void takeTurn(PlantVSZombieGame pvz) {
        if(stun && this.nbTourStun>0)
        {
            --nbTourStun;
        }else
        {
            Position paux=new Position(this.position.X-1,this.position.Y);
            
            if(pvz.PlantPositionAttack(paux,Attack))
            {
            }else{
                if(vitesse_actuelle%Vitesse==0)
                {
                    --this.position.X;
                    vitesse_actuelle=VitesseBase;
                }else{
                    ++vitesse_actuelle;
                }
            }
        }
        //System.out.println("vie "+ super.PV);
    }

    @Override
    public BufferedImage visit() throws IOException {
        return VisitorAgentAffichage.visit(this);
    }

    public void stun(long nbtour)
    {
        this.stun=true;
        this.nbTourStun=nbtour;
    }

    public TypeZombie typeZombie()
    {
        return TypeZombie.Zombie;
    }
    
}
