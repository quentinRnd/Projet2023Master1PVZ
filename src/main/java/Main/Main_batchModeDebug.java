package Main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SerializationUtils;

import Controler.ControlerPlantVSZombie;
import Game.EnumActionJeu;
import Game.PlantVSZombieGame;
import Model.InputMap;
import ReseauNeurone.TrainExample;
import Strategie.StrategieReseauNeurone;
import Strategie.Strategy;
import Strategie.StrategyRandom;
import Utils.ParametreAffichage;
import Utils.ParametreJeux;




public class Main_batchModeDebug {
	private static final Boolean VISUAL=true;
	private static  Boolean TEST=false;
	private static PrintWriter PR_OUT;
	private static FileWriter myWriter;				
	private static BufferedWriter bw;

	private static int  nbSimulations = 200;
	private static int  nbTurns = 200;
	private static int nbStepMax = 1000;
	
	public static void main(String[] args) throws Exception {

		File file=new File(Main_batchMode.fichier_Log);
		if(file.exists())
		{
			file.delete();
		}

		try{
			myWriter = new FileWriter(Main_batchMode.fichier_Log,true);				
			 bw = new BufferedWriter(myWriter);
			 PR_OUT = new PrintWriter(bw);
			 PR_OUT.println("type;value");
			 PR_OUT.flush();
			  } catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			  }
        
		double gamma = ParametreJeux.gamma;
		double epsilon = ParametreJeux.epsilon;
		double alpha = ParametreJeux.alpha;
		
		int nEpochs = ParametreJeux.nEpochs;
		int batchSize = ParametreJeux.batchSize;

		String Layout=ParametreJeux.Layout;

		InputMap inputDeBase= new InputMap(Layout);
		
		int nombreInput=(int)(inputDeBase.size_x*inputDeBase.size_y*StrategieReseauNeurone.nombre_type_encodage)+1;
		
		//ApproximateQLearningWithNN strat  = new ApproximateQLearningWithNN(n, epsilon, alpha, gamma, nEpochs, batchSize );
		
		StrategieReseauNeurone strat  = new StrategieReseauNeurone(epsilon, alpha, gamma, nEpochs, batchSize,nombreInput,EnumActionJeu.values().length);
	
		Strategy stratRandom=new StrategyRandom();
		
		
		
		
		for(int T = 0;T < nbStepMax; T++) {
			System.out.println("Mode Train");
			TEST=false;
			strat.modeTrain();
			ArrayList<TrainExample> trainExamples = play(inputDeBase, nbSimulations, nbTurns, strat, TEST);
			
			System.out.println("Learning");
			strat.learn(trainExamples);
			System.out.println("Mode Test");
			TEST=true;
			strat.modeTest();
            play(inputDeBase, 1, nbTurns, strat, TEST);

			/*//RAND
			System.out.println("Mode Rand");
			play(inputDeBase, nbSimulations, nbTurns, stratRandom, false);
*/
		}
		
	}
	
	
	public static ArrayList<TrainExample> play( InputMap map, int nbSimulations, int nbTurns, Strategy strat, boolean isTest) {
		

		ArrayList<PlantVSZombieGame> listPlantVSZombieGames = new ArrayList<PlantVSZombieGame>();
		
		for(int i = 0; i < nbSimulations; i++ ) {
			InputMap map_Jeu=SerializationUtils.clone(map);
			PlantVSZombieGame PVZGame = new PlantVSZombieGame(nbTurns,map_Jeu,strat);	
			PVZGame.initializeGame();
			if(i==0 && VISUAL && isTest)
			{
				vizualize(PVZGame);
			}else{
				PVZGame.launch();
			}
			listPlantVSZombieGames.add(PVZGame);
		}
		

		System.out.println("Launch " + nbSimulations + " PLantVSZombie game");
		
		
		
		
		for(int i = 0; i < nbSimulations; i++ ) {
		
			while(!listPlantVSZombieGames.get(i).gameFinish())
            {
				
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
		}
		double avgScore = 0;
		
		for(int i = 0; i < nbSimulations; i++ ) {
			avgScore += listPlantVSZombieGames.get(i).score;
		}
		
		avgScore = avgScore/nbSimulations;
		
		System.out.println("avgScore : " + avgScore);
		PR_OUT.println((TEST?"test":"train")+";"+avgScore);
		PR_OUT.flush();

		
		
		ArrayList<TrainExample> trainExamples = new ArrayList<TrainExample>();

		for(int i = 0; i < nbSimulations; i++ ) {
			
			ArrayList<TrainExample> trainExamplesGame =  listPlantVSZombieGames.get(i).getTrainExample();

			for(int j = 0; j < trainExamplesGame.size(); j++) {
				
				trainExamples.add(trainExamplesGame.get(j));
				
			}
			
		}
		
		System.out.println("total number examples : " + trainExamples.size());
		
		
		return trainExamples;
	}

	public static void vizualize(int nbTurns, InputMap map,Strategy strat)
	{
		PlantVSZombieGame PVZGame = new PlantVSZombieGame(nbTurns,map,strat);	
		PVZGame.initializeGame();
		PVZGame.setTime(150);
		try {
			new ControlerPlantVSZombie(PVZGame,true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void vizualize(PlantVSZombieGame PVZGame)
	{
		PVZGame.initializeGame();
		PVZGame.setTime(150);
		try {
			new ControlerPlantVSZombie(PVZGame,true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
