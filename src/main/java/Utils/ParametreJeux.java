package Utils;

public final class ParametreJeux {
    //=================================================PARAMETRE PROJECTILE======================================================
    public final static long DEGATProjectileDragon=75;
    public final static long DEGATProjectilePois=50;
    //===========================================================================================================================

    //=================================================PARAMETRE PLANTE======================================================
    public static final long PVDragon=125;
    public static final long CoutDragon=150;
    public static final long intervaleTirDragon=3;


    public static final long PVPois=100;
    public static final long CoutPois=100;
    public static final long intervaleTirPois=3;
    //===========================================================================================================================
    //=================================================PARAMETRE ZOMBIE======================================================
    public static final long PVZombie=200;
    public static final long VitesseZombie=2;
    public static final long AttackZombie=20;
    //===========================================================================================================================

    //=================================================PARAMETRE ZOMBIE AUGMENTER======================================================
    public static final long PVZombieAugmenter=300;
    public static final long VitesseZombieAugmenter=2;
    public static final long AttackZombieAugmenter=30;
    //===========================================================================================================================


    //=================================================PARAMETRE SCORE======================================================
    public static final int SCORE_TOUCHER_PROJECTILE=10;
    public static final int SCORE_TUER_ZOMBIE=5;
    public static final int SCORE_FIN_DE_PARTIE=50;
    public static final int SCORE_PERDU=-50;
    public static final int SCORE_TOUT_ZOMBIE_DEVANT_PLANT=0;
    public static final int Score_Planter=0;
    public static final int Score_Rien=0;
    public static final int SCORE_TOUCHER_PROJECTILE_POIS=1;
    public static final int SCORE_TOUCHER_PROJECTILE_DRAGON=2;
    public static final int SCORE_PLANTE_SUR_POSITION=0;
    


    public static final int Nombre_Tour_Rien_MAX=10;
    //===========================================================================================================================

    //=================================================PARAMETRE PlantVSZombie======================================================
    public static final long ARGENT_PAR_MANCHE=25;
    public static final long ARGENT_DEPART=200;

    public static final double PROBABILITER_VAGUE=0.1;
    public static final int NOMBRE_ZOMBIE_MAX_PLATEAU=7;
    public static final int NOMBRE_ZOMBIE_PAR_VAGUE=2;

    public static final int Intervale_Augmentation_Difficulter=20;
    public static final int Nombre_Zombie_Ajouter=2;

    public static final int Intervale_Augmentation_Zombie_Vague=20;
    public static final int Augmentation_Zombie_Vague=1;

    public static final int Arriver_Des_Zombie_Augmenter =50;

    public static final int DELAI_VAGUE=4;
    //===========================================================================================================================

    //=================================================PARAMETRE STRATEGIE======================================================
    public static double gamma = 0.95;
    public static double epsilon = 0.3;
    public static double alpha = 0.001;
    public static double Tau= 0.005;

    public static int nEpochs = 10;
    public static int batchSize = 100;
    //===========================================================================================================================

    //=================================================PARAMETRE MAIN======================================================
    public static String Layout="./src/main/java/Layout/layoutbase.txt";
    //===========================================================================================================================

    //=================================================PARAMETRE LOG CSV======================================================
    public static String TEST_STRING="test";
    public static String TRAIN_STRING="train";
    public static String RANDOM_STRING="rand";
    //===========================================================================================================================
    
}
