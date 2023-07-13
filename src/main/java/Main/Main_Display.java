package Main;

import Controler.ControlerPlantVSZombie;
import Game.EnumActionJeu;
import Model.InputMap;
import Strategie.StrategieReseauNeurone;
import Utils.ParametreJeux;

public class Main_Display {
    public static void main(String[] args) throws Exception {

        double gamma = ParametreJeux.gamma;
		double epsilon = ParametreJeux.epsilon;
		double alpha = ParametreJeux.alpha;
		
        String Layout=ParametreJeux.Layout;

		InputMap inputDeBase= new InputMap(Layout);

		int nEpochs = ParametreJeux.nEpochs;
		int batchSize = ParametreJeux.batchSize;

        String reseauCharger="./log/Reseau_interressant/neuralNetwork_999";

        int nombreInput=(int)(inputDeBase.size_x*inputDeBase.size_y*StrategieReseauNeurone.nombre_type_encodage)+1;


        StrategieReseauNeurone s=new StrategieReseauNeurone(epsilon, alpha, gamma, nEpochs, batchSize, nombreInput, EnumActionJeu.values().length,reseauCharger);
        s.modeTest();
        ControlerPlantVSZombie pvz=new ControlerPlantVSZombie(s);
    }
}
