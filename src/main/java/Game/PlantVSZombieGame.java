package Game;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.SerializationUtils;

import Agent.Item.Projectile;
import Agent.Plant.Dragon;
import Agent.Plant.Plant;
import Agent.Plant.Pois;
import Agent.Zombie.Zombie;
import Agent.Zombie.ZombieAugmenter;
import Model.InputMap;
import ReseauNeurone.TrainExample;
import Strategie.Strategy;
import Utils.ParametreAffichage;
import Utils.ParametreJeux;
import Utils.Position;
import Utils.StockageTrainExemple;
import View.ViewCommand;

public class PlantVSZombieGame extends Game {

    public static String INPUTMAPCHANGED="INPUTMAPCHANGED";
    public static final String FIN_JEU="FINJEU";

    private static final long ARGENT_PAR_MANCHE=ParametreJeux.ARGENT_PAR_MANCHE;
    private static final long ARGENT_DEPART=ParametreJeux.ARGENT_DEPART;

    //si y'a pas eu de zombie pendant el delai j'en mais quelque un sur la map
    private static long  DELAI_VAGUE=ParametreJeux.DELAI_VAGUE;

    private static final int SCORE_TOUCHER_PROJECTILE=ParametreJeux.SCORE_TOUCHER_PROJECTILE;
    private static final int SCORE_TUER_ZOMBIE=ParametreJeux.SCORE_TUER_ZOMBIE;
    private static final int SCORE_FIN_DE_PARTIE=ParametreJeux.SCORE_FIN_DE_PARTIE;
    private static final int SCORE_PERDU=ParametreJeux.SCORE_PERDU;
    private static final int SCORE_TOUT_ZOMBIE_DEVANT_PLANT=ParametreJeux.SCORE_TOUT_ZOMBIE_DEVANT_PLANT;
    private static final int Score_Planter=ParametreJeux.Score_Planter;
    private static final int Score_Rien=ParametreJeux.Score_Rien;
    



    private int nombre_partie_lancer=0;

    private static final long  RECOMPENSE_MORT=50;

    public InputMap inputMapJeu;
    private InputMap inputMapDeBase;

    protected transient final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private long vague,delaiVague;

    private static final double PROBABILITER_VAGUE=ParametreJeux.PROBABILITER_VAGUE;
    private int NOMBRE_ZOMBIE_MAX_PLATEAU=ParametreJeux.NOMBRE_ZOMBIE_MAX_PLATEAU;
    private int NOMBRE_ZOMBIE_PAR_VAGUE=ParametreJeux.NOMBRE_ZOMBIE_PAR_VAGUE;

    public transient ArrayList<StockageTrainExemple> trainExamples;


    private  ArrayList<Projectile> listeProjectileASupprimer;

    private int scoreTemporaire;

    public int score;

    private  Random rand=new Random();


    private int Nombre_Tour_Rien_MAX=ParametreJeux.Nombre_Tour_Rien_MAX;

    private int nombreTourRienConsecutif;

    private transient Strategy strategie;
  

    private Boolean GameOver;

    public PlantVSZombieGame(int maxTurn,InputMap inputmap,Strategy strat) 
    {
        super(maxTurn);
        this.inputMapDeBase=inputmap;
        trainExamples=new ArrayList<StockageTrainExemple>();
        this.strategie=strat;
        listeProjectileASupprimer=new ArrayList<>();
        if(strat!=null)
        {
            setTime(0);
        }

    }

    @Override
    public void initializeGame() {
        ++nombre_partie_lancer;
        this.inputMapJeu=SerializationUtils.clone(this.inputMapDeBase);
        this.inputMapJeu.argent=ARGENT_DEPART;
        this.vague=0;
        this.GameOver=false;
        this.delaiVague=0;
        this.score=0;
        this.nombreTourRienConsecutif=0;

        NOMBRE_ZOMBIE_MAX_PLATEAU=ParametreJeux.NOMBRE_ZOMBIE_MAX_PLATEAU;
        NOMBRE_ZOMBIE_PAR_VAGUE=ParametreJeux.NOMBRE_ZOMBIE_PAR_VAGUE;

        Nombre_Tour_Rien_MAX=ParametreJeux.Nombre_Tour_Rien_MAX;
    }

    @Override
    public synchronized void takeTurn() {
        if(ParametreAffichage.affichage)
        {
            System.out.println("taketurn");
            System.out.println("Argent "+this.inputMapJeu.argent);
        }

        if(super.turn% ParametreJeux.Intervale_Augmentation_Difficulter==0)
        {
            NOMBRE_ZOMBIE_MAX_PLATEAU+=ParametreJeux.Nombre_Zombie_Ajouter;
        }
        if(super.turn% ParametreJeux.Intervale_Augmentation_Zombie_Vague==0)
        {
            NOMBRE_ZOMBIE_PAR_VAGUE+=ParametreJeux.Augmentation_Zombie_Vague;
        }
        
        this.scoreTemporaire=0;
        PlantVSZombieGame state=SerializationUtils.clone(this);

        this.inputMapJeu.argent+=ARGENT_PAR_MANCHE;
        ++vague;
        ++this.delaiVague;
        EnumActionJeu action=EnumActionJeu.RIEN;
        if(this.strategie!=null)
        {
            action=this.strategie.chooseAction(state);
            if(action!=EnumActionJeu.RIEN)
            {
                nombreTourRienConsecutif=0;
                this.scoreTemporaire+=Score_Planter;
            }else{
                
                if(++nombreTourRienConsecutif<Nombre_Tour_Rien_MAX)
                {
                    this.scoreTemporaire+=Score_Rien;
                    
                }
                                
            }

            if(PlantePosition(action))
            {
                this.scoreTemporaire+=ParametreJeux.SCORE_PLANTE_SUR_POSITION;
            }

            joue(action);
        }
        //System.out.println(action);

        

        for(Plant p:this.inputMapJeu.Plantes)
        {
            ArrayList<Projectile> proj= p.takeTurn(this);
            this.inputMapJeu.Projectiles.addAll(proj);
        }
        Iterator<Projectile> ProjIte =  this.inputMapJeu.Projectiles.iterator();
        while (ProjIte.hasNext()) {
           Projectile p = ProjIte.next();
           
           if(p.appliqueDegat(this))
            {
                int scoreRajouter=SCORE_TOUCHER_PROJECTILE;
                if(p.relierAction)
                {
                    
                    Zombie z=Zombie_position_Retourner(p.position);
                    if(z!=null && !z.enVie())
                    {
                        switch(p.type())
                        {
                            case Dragon:
                            {
                                scoreRajouter+=ParametreJeux.SCORE_TOUCHER_PROJECTILE_DRAGON;
                            }break;
                            case Pois:
                            {
                                scoreRajouter+=ParametreJeux.SCORE_TOUCHER_PROJECTILE_POIS;
                            }break;
                        }
                        
                    }
                    //ajoutScore(p.action,scoreRajouter);
                    
                    
                }
                this.scoreTemporaire+=scoreRajouter;
                ProjIte.remove();

            }else{
                p.takeTurn(this); 

            }
            //System.out.println("zombie a l'emplacement "+Zombie_position(p.position));
        }
        for(Zombie z:this.inputMapJeu.Zombies)
        {
            z.takeTurn(this);
        }
        
        
        if(ParametreAffichage.affichePlante)
        {
            affichePlante();
        }
        

        suppressionProjectileHorsMap();
        suppretionAgentMort();
        VerifiactionFinJeu();
        gestionVague();
        updateObservateurJeu();
        if(strategie!=null)
        {
            if(this.turn==this.maxTurn)
            {
               
                this.scoreTemporaire+=SCORE_FIN_DE_PARTIE;
            }
            if(zombiePlanteComplet())
            {
                if(ParametreAffichage.affichage)
                {
                    System.out.println("tout les zombie sont devant une plante");    
                }
                this.scoreTemporaire+=SCORE_TOUT_ZOMBIE_DEVANT_PLANT;
            }else{
                if(ParametreAffichage.affichage)
                {
                    System.out.println("tout les zombie ne sont pas devant une plante");    
                }
            }
            PlantVSZombieGame new_state=SerializationUtils.clone(this);
            if(nombreTourRienConsecutif<Nombre_Tour_Rien_MAX)
            {
                strategie.update(state, action, new_state,this.scoreTemporaire, this);
            }else{
                strategie.update(state, action, new_state,this.scoreTemporaire, this);
            }
                        
        }
        this.score+=this.scoreTemporaire;
        if(ParametreAffichage.affichage)
        {
            System.out.println("\n");
        }
    }

    @Override
    public boolean gameContinue() {
        return !this.GameOver;
    }

    @Override
    public void gameOver() {
        if(ParametreAffichage.affichage)
        {
            System.out.println("GAME OVER ");
        }
        ++nombre_partie_lancer;
        UpdateFinJeu();
        super.pause();
        this.GameOver=true;
        
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        support.addPropertyChangeListener( property, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        support.removePropertyChangeListener(property, listener);
    }

    private void suppressionProjectileHorsMap()
    {
        Iterator<Projectile> i = this.inputMapJeu.Projectiles.iterator();
        while (i.hasNext()) {
           Projectile p = i.next();
           
           if(p.position.X>=this.inputMapJeu.size_x )
            {
                i.remove();
            }
        }
    }
/*
 * recherche si il y'a une plante a la position p
 */
    public boolean PlantPositionAttack(Position p,long pvRetirer)
    {
        for (Plant pl:this.inputMapJeu.Plantes)
        {
            if(pl.position.equals(p))
            {
                pl.toucher(pvRetirer);
                return true;
            }
        }
        return false;
    }

    public boolean ZombiePositionAttack(Position p ,long pvRetirer)
    {
        for (Zombie pl:this.inputMapJeu.Zombies)
        {
            if(pl.position.equals(p))
            {
                if(ParametreAffichage.affichage)
                {
                    System.out.println("j'ai toucher un truc a la position " + p);
                }
                pl.toucher(pvRetirer);
                //System.out.println("j'ai toucher un zombie");
                return true;
            }
        }
        return false;
    }
    
    public synchronized void deleteProjectile(Projectile p)  
    {
        this.listeProjectileASupprimer.add(p);
    }

    public void VerifiactionFinJeu()
    {
        for(Zombie z:this.inputMapJeu.Zombies)
        {
            if(z.position.X<0)
            {
                this.gameOver();
                UpdateFinJeu();
                this.scoreTemporaire+=SCORE_PERDU;
                return;
            }
        }
    }

    public void suppretionAgentMort()
    {
        Iterator<Zombie> i = this.inputMapJeu.Zombies.iterator();
        while (i.hasNext()) {
           Zombie p = i.next();
           
           if(!p.enVie())
            {
                i.remove();
                this.inputMapJeu.argent+= RECOMPENSE_MORT;
            }
        }
        Iterator<Plant> pi = this.inputMapJeu.Plantes.iterator();
        while (pi.hasNext()) {
           Plant p = pi.next();
           
           if(!p.enVie() )
            {
                pi.remove();
            }
        }

        for(Projectile p:this.listeProjectileASupprimer)
        {
            this.inputMapJeu.Projectiles.remove(p);
        }
        this.listeProjectileASupprimer.clear();
    }

    public void updateObservateurJeu()
    {
        if( this.support!=null)
        {

            this.support.firePropertyChange(INPUTMAPCHANGED, null, SerializationUtils.clone(this.inputMapJeu));
            this.support.firePropertyChange(ViewCommand.TEXTE_MESSAGE_ARGENT, null, this.inputMapJeu.argent);
        }
        
    }
    public void Planter(Position p,Plant.TypePlant t)
    {
        Plant pl=null;
        switch(t)
        {
            case Dragon:
            {
                pl=new Dragon(p);
            }break;
            case Pois:
            {
                pl=new Pois(p);
            }break;
            default:
            {
                return;
            }
        }
        if(positionValide(p) && this.inputMapJeu.argent>=pl.cout())
        {
            deplanter(p);
            this.inputMapJeu.Plantes.add(pl);
            this.inputMapJeu.argent-=pl.cout();
            updateObservateurJeu();
        }
    }

    public void Planter(Position p,Plant.TypePlant t,EnumActionJeu a)
    {
        Plant pl=null;
        
        deplanter(p);
        
        switch(t)
        {
            case Dragon:
            {
                pl=new Dragon(p,a);
            }break;
            case Pois:
            {
                pl=new Pois(p,a);
            }break;
            default:
            {
                return;
            }
        }
        if(positionValide(p) && this.inputMapJeu.argent>=pl.cout())
        {
            this.inputMapJeu.Plantes.add(pl);
            this.inputMapJeu.argent-=pl.cout();
            updateObservateurJeu();
        }
    }
    //check si y'a pas d'objet qui interdit la pose d'une plante
    public boolean positionValide(Position p)
    {
        if(p.X>=this.inputMapJeu.size_x || p.X<0)
        {
            return false;
        }
        if(p.Y>=this.inputMapJeu.size_y|| p.Y<0)
        {
            return false;
        }
        /*for(Plant pi:this.inputMapJeu.Plantes)
        {
            if(pi.position.equals(p))
            {
                return false;
            }
        }*/
        for(Zombie z:this.inputMapJeu.Zombies)
        {
            if(z.position.equals(p))
            {
                return false;
            }
        }
        
        return true;
    }

    public boolean positionValideZombie(Position p)
    {
        if(p.X>=this.inputMapJeu.size_x || p.X<0)
        {
            return false;
        }
        if(p.Y>=this.inputMapJeu.size_y|| p.Y<0)
        {
            return false;
        }
        for(Plant pi:this.inputMapJeu.Plantes)
        {
            if(pi.position.equals(p))
            {
                return false;
            }
        }
        
        return true;
    }

    private void gestionVague()
    {
        if((this.delaiVague>=DELAI_VAGUE || this.rand.nextDouble()<PROBABILITER_VAGUE || this.vague==1 )&& this.inputMapJeu.Zombies.size()<NOMBRE_ZOMBIE_MAX_PLATEAU)
        {
            this.delaiVague=0;
            int nombre_zombie=this.rand.nextInt(NOMBRE_ZOMBIE_PAR_VAGUE);
            ArrayList<Integer> array=new ArrayList<>();
            for(int i=0;i<this.inputMapJeu.size_y;++i)
            {
                array.add(i);
            }
            Collections.shuffle(array);
            
            while(nombre_zombie>0)
            {
                for(int i=0;i<array.size() && nombre_zombie>=0;++i)
                {
                    Position p=new Position(this.inputMapJeu.size_x-1,(long)array.get(i));
                    if(positionValideZombie(p))
                    {
                        --nombre_zombie;
                        if(super.turn>ParametreJeux.Arriver_Des_Zombie_Augmenter)
                        {
                            this.inputMapJeu.Zombies.add(new ZombieAugmenter(p));
                        }else{
                            this.inputMapJeu.Zombies.add(new Zombie(p));
                        }

                    }
                }
            }
            
        }
        
    }
    private void UpdateFinJeu()
    {
        if(this.support!=null)
        {
            Boolean aux=true;
            //System.out.println(FIN_JEU);
            this.support.firePropertyChange(FIN_JEU,null, aux);
        }
    }

    //regarde si y'a un zombie a la position 
    public boolean Zombie_position(Position p)
    {
        for(Zombie z :this.inputMapJeu.Zombies)
        {
            if(z.position.equals(p))
            {
                return true;
            }
        }
        return false;
    }

    public Zombie Zombie_position_Retourner(Position p)
    {
        for(Zombie z :this.inputMapJeu.Zombies)
        {
            if(z.position.equals(p))
            {
                return z;
            }
        }
        return null;
    }

    

    public ArrayList<EnumActionJeu> actionPossible()
    {
        ArrayList<EnumActionJeu> retour=new ArrayList<EnumActionJeu>();
        for(EnumActionJeu a:EnumActionJeu.values())
        {
            if(actionLegal(a))
            {
                retour.add(a);
            }
        }
        return retour;
    }

    public boolean actionLegal(EnumActionJeu a)
    {
        switch(a)
        {
             case D1:
            case D2:
            case D3:
            case D4:
            case D5:
            case D6:
            case D7:
            case D8:
            case D9:
            case D10:
            case D11:
            case D12:
            case D13:
            case D14:
            case D15:
            {
                return positionValide(Position.PositionActionJeu(a)) && Dragon.Cout<=this.inputMapJeu.argent;
            }

            case P1:
            case P2:
            case P3:
            case P4:
            case P5:
            case P6:
            case P7:
            case P8:
            case P9:
            case P10:
            case P11:
            case P12:
            case P13:
            case P14:
            case P15:
            {
                return positionValide(Position.PositionActionJeu(a)) && Pois.Cout<=this.inputMapJeu.argent;
            }
            case RIEN:  
            {
                return true;
            }
            default:
            {
                throw new UnsupportedOperationException("Erreur");
            }
        }
    }

    public void joue(EnumActionJeu action)
    {
        switch(action)
        {
            case D1:
            case D2:
            case D3:
            case D4:
            case D5:
            case D6:
            case D7:
            case D8:
            case D9:
            case D10:
            case D11:
            case D12:
            case D13:
            case D14:
            case D15:
            {
                if(actionLegal(action))
                {
                    Planter(Position.PositionActionJeu(action), Plant.TypePlant.Dragon,action);
                }
            }

            case P1:
            case P2:
            case P3:
            case P4:
            case P5:
            case P6:
            case P7:
            case P8:
            case P9:
            case P10:
            case P11:
            case P12:
            case P13:
            case P14:
            case P15:
            {
                if(actionLegal(action))
                {
                    Planter(Position.PositionActionJeu(action), Plant.TypePlant.Pois,action);
                }
            }
            case RIEN:  
            {
            } 
        }
    }
    public synchronized void deplanter(Position posSup) 
    {
        Iterator<Plant> pi = this.inputMapJeu.Plantes.iterator();
        while (pi.hasNext()) {
           Plant p = pi.next();
        
           if(posSup.equals(p.position))
            {
                pi.remove();
            }
        }
    }
    public void deplanter(EnumActionJeu action)
    {
        switch(action)
        {
             case D1:
            case D2:
            case D3:
            case D4:
            case D5:
            case D6:
            case D7:
            case D8:
            case D9:
            case D10:
            case D11:
            case D12:
            case D13:
            case D14:
            case D15:
            case P1:
            case P2:
            case P3:
            case P4:
            case P5:
            case P6:
            case P7:
            case P8:
            case P9:
            case P10:
            case P11:
            case P12:
            case P13:
            case P14:
            case P15:
            {
                Position pPlant=Position.PositionActionJeu(action);
                Iterator<Plant> pi = this.inputMapJeu.Plantes.iterator();
                while (pi.hasNext()) {
                   Plant p = pi.next();
                
                   if(pPlant.equals(p.position))
                    {
                        pi.remove();
                    }
                }
            }break;
            case RIEN:  
            {
            } 
        }
    }

    public Boolean gameFinish()
    {

        return this.GameOver || nombre_partie_lancer>4;
    }
    //verifier si tout les zombie sont associer a une plante 
    public boolean zombiePlanteComplet()
    {
        for(int i=0;i<this.inputMapJeu.Zombies.size();++i)
        {
            if(!plantAY(this.inputMapJeu.Zombies.get(i).position))
            {
                return false;
            }
        }
        return true;
    }
//verifie si y'a une plante a un y donner
    public boolean plantAY(Position p)
    {
        for(Plant pl:this.inputMapJeu.Plantes)
        {
            if(pl.position.Y==p.Y && pl.position.X<p.X)
            {
                return true;
            }
        }
        return false;
    }

    public synchronized void addTrainExemple(StockageTrainExemple t) 
    {
        trainExamples.add(t);
    }

    public void ajoutScore(EnumActionJeu a ,int score)
    {
        if(ParametreAffichage.affichage)
        {
            System.out.println("ajoute un score a l'action " + a);
        }
        for(int i=this.trainExamples.size()-1;i>=0;--i)
        {
            if(this.trainExamples.get(i).action==a)
            {
                if(ParametreAffichage.affichage)
                {
                    System.out.println(a +" je lui ajoute un score de " + score);
                }
                this.trainExamples.get(i).addReward(score);
            }
        }
    }

    public ArrayList<TrainExample> getTrainExample()
    {
        ArrayList<TrainExample> retour=new ArrayList<TrainExample>();
        for(StockageTrainExemple t:this.trainExamples)
        {
            retour.add(t.exemple);
        }
        return retour;
    }

    public void affichePlante()
    {
        for(Plant p:this.inputMapJeu.Plantes)
        {
            System.out.print(p.type() +"->" + p.position.toString() +" | ");
        }
        System.out.println();
    }

    public Boolean PlantePosition(EnumActionJeu a)
	{
        if(a!= EnumActionJeu.RIEN)
        {
            Position p =Position.PositionActionJeu(a);
		    for(Plant pl:this.inputMapJeu.Plantes)
            {
                if(p.equals(pl.position))
                {
                    return true;
                }
            }
        }
		
        return false; 
	}

    public Map<EnumActionJeu,Integer> getAction()
    {
        HashMap<EnumActionJeu,Integer> retour=new HashMap<>();
        
        Integer aux=0;

        for(StockageTrainExemple s:this.trainExamples)
        {
            aux=retour.getOrDefault(s.action,0);
            retour.put(s.action, aux+1);
        }

        return retour;
    }

    
}


