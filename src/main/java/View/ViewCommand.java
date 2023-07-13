package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Agent.Plant.Plant;
import Controler.ControlerPlantVSZombie;
import Game.PlantVSZombieGame;
import etat.etat_begin;
import etat.etat_bouton;
import etat.etat_bouton.etat_enum;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewCommand implements PropertyChangeListener{
   
    //ATTRIBUT
    protected final PropertyChangeSupport supportChangementLayout = new PropertyChangeSupport(this);

    public JSlider slider;
    public JButton  button_restart,button_pause,button_step,button_play,buttonChangeLayout,buttonRefreshLayout,choix_joueureuse; 
    public JLabel labelslider,labelnumerotour; 
    public ImageIcon icone_restart,icone_pause,icone_step,icone_play,iconeChangeLayout,iconeRefreshIcon,icone_robot,icone_pas_robot;
    public JFileChooser layoutChoix;
    public JList listePannel;
    public JScrollPane listScrollPane;
    private boolean est_joeureuse=false;
    public ListeModelChoixLayout listModel;

    public static final String CHEMIN_ICONS="./src/main/icons";

    public static String CHEMINLAYOUT="./src/main/java/Layout";

    public static String CHANGEMENTTYPEPLANT="ChangementTypePlant";

    public static String DEVIENTJOUEUREUSE="devient joeureuse";

    public etat_bouton etat;

    public JList<String> listTypeSnake;
    
    public JPanel panelSlider,panelButtonSlider,panelButton,panelNombretourLayoutchoix,panelChoixLayout,pannelBoutonChoixLayout,pannelScoreNombreTour,panel_nombreTour_choixTypeSnake,pannelScore,pannel;
    

    public ControlerPlantVSZombie Controleur;

    private static final String TEXTE_Argent="Argent a disposition : ";
    public static final String TEXTE_MESSAGE_ARGENT="Argent";

    private void inscription_support_jeu()
    {
        this.Controleur.Jeu.addPropertyChangeListener(this);
    }

    //CONSTRUCTEUR
    public void changeJeux()
    {
        inscription_support_jeu();
        this.labelnumerotour.setText(TEXTE_Argent+0);
    }

    public ViewCommand(ControlerPlantVSZombie c)
   {
    
/*
 * changement du numero de tours du jeux
 */
        
    	
        this.Controleur=c;
        inscription_support_jeu();
        
        this.slider=new JSlider(JSlider.HORIZONTAL,1,10,3);
        this.slider.setMajorTickSpacing(1);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);            
        this.slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evenement) {
            Controleur.setSpeed(slider.getValue()*100);
            }
        });

        this.labelslider=new JLabel("Nombre de tour par seconde",JLabel.CENTER);

        this.panelSlider=new JPanel(new GridLayout(2,1)) ;
        this.panelSlider.add( this.labelslider);
        this.panelSlider.add(this.slider);
        

        this.labelnumerotour=new JLabel(TEXTE_Argent+0, JLabel.CENTER);

        File directory = new File(CHEMINLAYOUT);
        String layouts[]=directory.list();
    
        

        listModel = new ListeModelChoixLayout(layouts);
 
        JList<String> list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
         listScrollPane = new JScrollPane(list);

        this.buttonChangeLayout=new JButton();
        this.buttonRefreshLayout=new JButton();

        this.pannelBoutonChoixLayout=new JPanel(new GridLayout(1,2));
        this.pannelBoutonChoixLayout.add(this.buttonChangeLayout);
        this.pannelBoutonChoixLayout.add(this.buttonRefreshLayout);
        
        this.panelChoixLayout=new JPanel(new GridLayout(1,2));
        this.panelChoixLayout.add(listScrollPane);
        this.panelChoixLayout.add(this.pannelBoutonChoixLayout);

        //Create the list and put it in a scroll pane.


        this.panelNombretourLayoutchoix=new JPanel(new GridLayout(2,1));
        
        

        //ici pannel pour le choix de type de snake + nombre de tour
        this.panel_nombreTour_choixTypeSnake=new JPanel(new GridLayout(1,2));
        
        
        JPanel pannel_nombreTour_choix_joeureurse=new JPanel(new GridLayout(1,2));
        
        pannel_nombreTour_choix_joeureurse.add(this.labelnumerotour);
        

        this.icone_robot =new ImageIcon(CHEMIN_ICONS+"/round-robot_icon-icons.com_68359.png");
        this.icone_pas_robot =new ImageIcon(CHEMIN_ICONS+"/Football_2-32_icon-icons.com_72064.png");

        choix_joueureuse=new JButton();
        choix_joueureuse.setIcon(icone_robot);
        pannel_nombreTour_choix_joeureurse.add(choix_joueureuse);
        

        

        
        choix_joueureuse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
            est_joeureuse=!est_joeureuse;
            supportChangementLayout.firePropertyChange(DEVIENTJOUEUREUSE, null,est_joeureuse);
            if(est_joeureuse)
            {
                choix_joueureuse.setIcon(icone_pas_robot);
            }else{
                choix_joueureuse.setIcon(icone_robot);
            }
        }
        });

        this.panel_nombreTour_choixTypeSnake.add(pannel_nombreTour_choix_joeureurse);

        String type_Plant[]=new String[Plant.TypePlant.values().length];
        int iterateurtype_snake=0;
        for(Plant.TypePlant tp:Plant.TypePlant.values())
        {
            type_Plant[iterateurtype_snake++]=tp.toString();
        }
    
        

        ListeModelChoixLayout listModelTypeSnake = new ListeModelChoixLayout(type_Plant);
 
        listTypeSnake = new JList<String>(listModelTypeSnake);
        listTypeSnake.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTypeSnake.setSelectedIndex(0);
        listTypeSnake.setVisibleRowCount(5);
        JScrollPane listScrollPaneTypesnake = new JScrollPane(listTypeSnake);

        JPanel choix_type_snake=new JPanel(new GridLayout(2,1));
        JButton choix_type_snake_button=new JButton();

        choix_type_snake_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
            supportChangementLayout.firePropertyChange(CHANGEMENTTYPEPLANT, null,listTypeSnake.getSelectedValue());
        }
        });
        choix_type_snake.add(listScrollPaneTypesnake);
        choix_type_snake.add(choix_type_snake_button);

         


         this.panel_nombreTour_choixTypeSnake.add(choix_type_snake);

        this.panelNombretourLayoutchoix.add(this.panel_nombreTour_choixTypeSnake);

        this.panelNombretourLayoutchoix.add(this.panelChoixLayout);

        this.panelButtonSlider=new JPanel(new GridLayout(1,2));
        this.panelButtonSlider.add(this.panelSlider);
        this.panelButtonSlider.add(panelNombretourLayoutchoix);

        
        this.panelButton=new JPanel(new GridLayout(1,4));
        
        this.button_pause=new JButton();
        this.panelButton.add(this.button_pause);
        
        this.button_play=new JButton();
        this.panelButton.add(this.button_play);

        this.button_restart=new JButton();
        this.panelButton.add(this.button_restart);
        
        this.button_step=new JButton();
        this.panelButton.add(this.button_step);

        //bouton restart 
        this.button_restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
            
            etat.run(etat_enum.restart);
            }
        });
        //bouton pause
        this.button_pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
            
            etat.run(etat_enum.pause);
            }
            });
        //bouton step
        this.button_step.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
                
                etat.run(etat_enum.step);
                }
                });
        
        //bouton play
        this.button_play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
            
            etat.run(etat_enum.play);
            }
        });

        //changement de layout
        this.buttonChangeLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {
            supportChangementLayout.firePropertyChange("ChangementLayout", null,"layouts/"+list.getSelectedValue());
            supportChangementLayout.firePropertyChange("devient joeureuse", null,est_joeureuse);
            
        }
        });
        this.buttonRefreshLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evenement) {

                File directory = new File("./layouts");
                String layouts[]=directory.list();
                listModel.refresh(layouts);
        }
        });

        this.iconeChangeLayout =new ImageIcon(CHEMIN_ICONS+"/download.png");
        this.iconeRefreshIcon =new ImageIcon(CHEMIN_ICONS+"/refresh-ccw.png");
        this.icone_restart =new ImageIcon(CHEMIN_ICONS+"/icon_restart.png");
        this.icone_pause=new ImageIcon(CHEMIN_ICONS+"/icon_pause.png");
        this.icone_step=new ImageIcon(CHEMIN_ICONS+"/icon_step.png");
        this.icone_play=new ImageIcon(CHEMIN_ICONS+"/icon_play.png");

        
        choix_type_snake_button.setIcon(iconeChangeLayout);
        this.buttonChangeLayout.setIcon(this.iconeChangeLayout);
        this.buttonRefreshLayout.setIcon(this.iconeRefreshIcon);
        this.button_restart.setIcon(this.icone_restart);
        this.button_pause.setIcon(this.icone_pause);
        this.button_step.setIcon(this.icone_step);
        this.button_play.setIcon(this.icone_play);
        this.pannel=new JPanel(new GridLayout(2,1));
        
        this.pannel.add(this.panelButton);
        this.pannel.add(this.panelButtonSlider);
        
        this.etat= new etat_begin(this);
        this.etat.run(etat_enum.begin);
        

   }
   public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName()==TEXTE_MESSAGE_ARGENT)
        {
            this.labelnumerotour.setText(TEXTE_Argent+(long)evt.getNewValue());
        }
        if(evt.getPropertyName()==PlantVSZombieGame.FIN_JEU)
        {
            this.etat.run(etat_enum.begin);
        }
        

        
        
    }  

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        supportChangementLayout.addPropertyChangeListener( property, listener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        supportChangementLayout.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String property, PropertyChangeListener listener) {
        supportChangementLayout.removePropertyChangeListener(property, listener);
    }
}
