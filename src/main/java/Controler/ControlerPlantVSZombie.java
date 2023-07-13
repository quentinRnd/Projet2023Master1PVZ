package Controler;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Game.PlantVSZombieGame;
import Model.InputMap;
import Strategie.Strategy;
import View.PanelPlantVSZombie;
import View.ViewCommand;
import etat.etat_bouton.etat_enum;

public class ControlerPlantVSZombie extends AbstractController implements PropertyChangeListener {
    private PanelPlantVSZombie Ppvz;
    public ViewCommand vc;
    
    public JPanel panel;
    private  JFrame f;
    public ControlerPlantVSZombie () throws Exception
    {
       this.f=new JFrame();
        

        Strategy strat=null;

        InputMap inputmap=new InputMap("./src/main/java/Layout/layoutbase.txt");
        Ppvz=new PanelPlantVSZombie(inputmap,this);
       super.Jeu=new PlantVSZombieGame(1000000, inputmap,strat);
        this.vc= new ViewCommand(this);

        f.setSize(new Dimension(500, 500));
        
        f.add(Ppvz);
        f.setVisible(true);
        
        super.Jeu.addPropertyChangeListener(Ppvz);

        this.panel=new JPanel(new GridLayout(2,1));
        this.panel.add(Ppvz);
        this.panel.add(vc.pannel);
        f.add(this.panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }

    public ControlerPlantVSZombie (Strategy s) throws Exception
    {
       this.f=new JFrame();
        



        InputMap inputmap=new InputMap("./src/main/java/Layout/layoutbase.txt");
        Ppvz=new PanelPlantVSZombie(inputmap,this);
       super.Jeu=new PlantVSZombieGame(1000000, inputmap,s);
        this.vc= new ViewCommand(this);

        f.setSize(new Dimension(500, 500));
        
        f.add(Ppvz);
        f.setVisible(true);
        
        super.Jeu.addPropertyChangeListener(Ppvz);

        this.panel=new JPanel(new GridLayout(2,1));
        this.panel.add(Ppvz);
        this.panel.add(vc.pannel);
        f.add(this.panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




    }

    public ControlerPlantVSZombie (PlantVSZombieGame pvz,Boolean debug) throws Exception
    {
        this.f=new JFrame();

        InputMap inputmap=pvz.inputMapJeu;
        Ppvz=new PanelPlantVSZombie(inputmap,this);
       super.Jeu=pvz;

        this.vc= new ViewCommand(this);
        if(debug)
        {
            this.vc.etat.run(etat_enum.begin);
        }else{
            this.vc.etat.run(etat_enum.play);

        }

        f.setSize(new Dimension(500, 500));
        
        f.add(Ppvz);
        f.setVisible(true);
        
        super.Jeu.addPropertyChangeListener(Ppvz);

        this.panel=new JPanel(new GridLayout(2,1));
        this.panel.add(Ppvz);
        this.panel.add(vc.pannel);
        f.add(this.panel);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        super.Jeu.addPropertyChangeListener(this);





    }

    

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        
        if(evt.getPropertyName()==PlantVSZombieGame.FIN_JEU)
        {
            
           this.f.dispatchEvent(new WindowEvent(this.f, WindowEvent.WINDOW_CLOSING));
        }
    }
}
