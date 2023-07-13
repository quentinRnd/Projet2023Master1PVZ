package Strategie;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang3.SerializationUtils;

import Agent.Item.Projectile;
import Agent.Plant.Plant;
import Agent.Zombie.Zombie;
import Game.EnumActionJeu;
import Game.PlantVSZombieGame;
import Model.InputMap;
import ReseauNeurone.NeuralNetWorkDL4J;
import ReseauNeurone.TrainExample;
import ReseauNeurone.NeuralNetWorkDL4J.NET_CHOOSE;
import Utils.ParametreAffichage;
import Utils.ParametreJeux;
import Utils.StockageTrainExemple;

public class StrategieReseauNeurone implements Strategy {

    public static final long nombre_type_encodage=5;

    private static long nombre_iteration_reseau=0;

    private int nombreInput;

    private NeuralNetWorkDL4J nn;

    private double alpha,gamma,epsilon,baseEpsilon;

    private Random random = new Random();

    private int nEpochs,batchSize;
// pour regarder ce qu'il fait permet de desactiver update et ne pas faire du random a false et a true de ce comporter normalement
    private boolean test;


    

    public StrategieReseauNeurone(double epsilon, double alpha, double gamma, int nEpochs, int batchSize,int nombreinput,int nombreoutput) {
		
		this.nn = new NeuralNetWorkDL4J(alpha, 0, nombreinput , nombreoutput);

		this.test=true;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.gamma = gamma;
		
		this.baseEpsilon = epsilon;
        this.nombreInput=nombreinput;
        
        this.nEpochs = nEpochs;
        this.batchSize = batchSize;        
		
	}

    public StrategieReseauNeurone(double epsilon, double alpha, double gamma, int nEpochs, int batchSize,int nombreinput,int nombreoutput,String loadSerial) {
		
		this.nn = new NeuralNetWorkDL4J(alpha, 0, nombreinput , nombreoutput,loadSerial);
		this.test=false;
		this.epsilon = epsilon;
		this.alpha = alpha;
		this.gamma = gamma;
		
		this.baseEpsilon = epsilon;
        this.nombreInput=nombreinput;
        
        this.nEpochs = nEpochs;
        this.batchSize = batchSize;
		
	}

    @Override
    public EnumActionJeu chooseAction(PlantVSZombieGame state) {
        
        if(ParametreAffichage.affichage)
        {
            System.out.println("CHOOSEACTION");
        }
        if(Math.random() < epsilon && test) {
            
			ArrayList<EnumActionJeu> actionpossible=state.actionPossible();
            if(ParametreAffichage.affichage)
            {
                System.out.println("j'ai fais du random");
            }
            return actionpossible.get(random.nextInt(actionpossible.size()));
		}else{
            if(ParametreAffichage.affichage)
            {
                System.out.println("j'ai pas fait du random");
            }

            double maxQvalue = -999;
			EnumActionJeu best_a = EnumActionJeu.P1;
			
			double[] encodedState = EncodeState(state.inputMapJeu);
			

	        
            double[] outPut = this.nn.predict(encodedState,NET_CHOOSE.policy_net);
            EnumActionJeu enumactuelle;

            for(int a=0; a < EnumActionJeu.values().length; a++) {
                enumactuelle=EnumActionJeu.values()[a];

                if(state.actionLegal(enumactuelle))
                {
                    if(outPut[a] > maxQvalue) {
                        maxQvalue = outPut[a];
                        best_a = enumactuelle ;
                        
                        
                    } else if(outPut[a]  == maxQvalue) {
                        
                        if(random.nextBoolean()) {
                            
                            maxQvalue = outPut[a];
                            best_a = enumactuelle;
                        } 
                    }
                }
				
				
			}
            if(ParametreAffichage.affichage)
            {
                System.out.println("action jouer -> "+ best_a);
            }
            return best_a;
        }
    }

    @Override
    public void update(PlantVSZombieGame state, EnumActionJeu action, PlantVSZombieGame new_state, int reward, PlantVSZombieGame game) {
        
        if(!test)
        {
            return;
        }
        if(ParametreAffichage.affichage)
        {
            System.out.println("UPDATE");
            System.out.println("action jouée ce tour -> "+action);
            System.out.println("reward de ce tour ->"+reward);
        }

    	
		
        
        double[] encodedState = EncodeState(new_state.inputMapJeu);
        double[] qValues_nextState = this.nn.predict(encodedState,NET_CHOOSE.target_net);
        
        if(ParametreAffichage.affichage_encode_state)
        {
            System.out.println("NEWSTATE ENCODEDSTATE");
            afficheEncodeState(encodedState, (int)state.inputMapJeu.size_x,(int) state.inputMapJeu.size_y);
        }

        double maxQvalue_nextState = qValues_nextState[0];
 
        for(EnumActionJeu actionInter:EnumActionJeu.values())
        {
            if(qValues_nextState[actionInter.ordinal()]>maxQvalue_nextState && state.actionLegal(actionInter))
            {
                maxQvalue_nextState=qValues_nextState[actionInter.ordinal()];
            }
        }
        
		
		
		
		
		encodedState = EncodeState(state.inputMapJeu);

        if(ParametreAffichage.affichage_encode_state)
        {
            System.out.println("state ENCODEDSTATE");
            afficheEncodeState(encodedState, (int)state.inputMapJeu.size_x,(int) state.inputMapJeu.size_y);
        }

		double[] targetQ = this.nn.predict(encodedState,NET_CHOOSE.policy_net);
		//a verifier
        targetQ[action.ordinal()] = reward + gamma*maxQvalue_nextState;
        
        
        
		
		TrainExample trainExample = new TrainExample(encodedState, targetQ);
		StockageTrainExemple st=new StockageTrainExemple(trainExample, action);
		game.addTrainExemple(st);

        this.nn.updateTarget(ParametreJeux.Tau);
    }
    

    public double[] EncodeState(InputMap map)
    {
        int nombredecalageCaracteristique=(int)(map.size_x*map.size_y);
        double encodedstate[]=new double[this.nombreInput];
        encodedstate[encodedstate.length-1]=map.argent;

        for(Plant p:map.Plantes)
        {
            switch(p.type())
            {
                case Dragon:
                {
                    encodedstate[(int)(p.position.X*map.size_y+p.position.Y)+nombredecalageCaracteristique+nombredecalageCaracteristique+nombredecalageCaracteristique+nombredecalageCaracteristique]=1;
                }break;
                case Pois:
                {
                    encodedstate[(int)(p.position.X*map.size_y+p.position.Y)+nombredecalageCaracteristique/*+nombredecalageCaracteristique*/]=1;
                }break;
            }
        }

        for(Projectile p:map.Projectiles)
        {
            encodedstate[(int)(p.position.X*map.size_y+p.position.Y)+nombredecalageCaracteristique+nombredecalageCaracteristique]=1;
        }

        for(Zombie z:map.Zombies)
        {
            switch(z.typeZombie())
            {
                case Zombie:
                {
                    if(z.position.correct())
                    {
                        encodedstate[(int)(z.position.X*map.size_y+z.position.Y)]=1;
                    }
                }break;
                
                case ZombieAugmenter:
                {
                    encodedstate[(int)(z.position.X*map.size_y+z.position.Y)+nombredecalageCaracteristique+nombredecalageCaracteristique+nombredecalageCaracteristique]=1;

                }break;
            }
        }


        
        return encodedstate;
    }

    public void learn(ArrayList<TrainExample> trainExamples) {
		
		this.nn.fit(trainExamples, this.nEpochs, this.batchSize);
        this.nn.save(nombre_iteration_reseau++);
	}

    public void modeTest() {
		
		this.epsilon = 0;
	}
	
	
	public void modeTrain() {
		
		this.epsilon = this.baseEpsilon;
		
	}
    
    public static void afficheEncodeState(double[] state,int size_x,int size_y)
    {
        for(int i=0;i<state.length;++i)
        {
            if(i%size_y==0)
            {
                System.out.println();
            }
            if(i%(size_y*size_x)==0)
            {
                System.out.println((i==0?"\nZombie":(i==size_y*size_x?"\nPlante":(i==size_y*size_x*2?"\nProjectile":""))));    
            }
            if(i==state.length-1)
            {
                System.out.print("argent ");
            }
            System.out.print(state[i] +" ");
        }
        System.out.println();
    }

    public void load(String NNSerial,boolean apprentissage,NET_CHOOSE reseau)
    {
        this.nn.load(NNSerial, apprentissage,reseau);
    }
    
}
