package Utils;
/*
 * c'est une classe qui permet de stocker les score des train exemple meme après que ceux ci soit générer
 */

import Game.EnumActionJeu;
import ReseauNeurone.TrainExample;

public class StockageTrainExemple {
    public TrainExample exemple;
    public EnumActionJeu action;
    public StockageTrainExemple(TrainExample t,EnumActionJeu a)
    {
        this.action=a;
        this.exemple=t;
    }
    public void addReward(int reward)
    {
        this.exemple.getY()[this.action.ordinal()]+=reward;
    }
}
