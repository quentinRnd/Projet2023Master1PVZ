package Visitor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Agent.Plant.Dragon;
import Agent.Plant.Pois;
import Agent.Zombie.Zombie;
import Agent.Zombie.ZombieAugmenter;
import Agent.Item.Projectile;
import Agent.Item.ProjectileDragon;
import Agent.Item.ProjectilePois;

public class VisitorAgentAffichage {
    public static final String Chemin_Image="./src/main/java/images/";

    public static ArrayList<BufferedImage> visit(Dragon d) throws IOException
    {
        ArrayList<BufferedImage> retour =new ArrayList<BufferedImage>();
        
        retour.add(ImageIO.read(new File(Chemin_Image+"/PlantDragonAnimation/PlantDragonF1.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/PlantDragonAnimation/PlantDragonF2.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/PlantDragonAnimation/PlantDragonF3.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/PlantDragonAnimation/PlantDragonF4.png"))); 
        return retour;
    }
    public static ArrayList<BufferedImage>  visit(Pois p) throws IOException
    {
        ArrayList<BufferedImage> retour =new ArrayList<BufferedImage>();
        
        retour.add(ImageIO.read(new File(Chemin_Image+"/PlantePoisAnimation/PoisPlanteF1.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/PlantePoisAnimation/PoisPlanteF2.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/PlantePoisAnimation/PoisPlanteF3.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/PlantePoisAnimation/PoisPlanteF4.png"))); 
        return retour;
    }
    public static BufferedImage visit(Zombie z) throws IOException
    {
        return ImageIO.read(new File(Chemin_Image+"Zombie.png"));
    }

    public static ArrayList<BufferedImage> visit(ProjectilePois p) throws IOException
    {
        ArrayList<BufferedImage> retour =new ArrayList<BufferedImage>();
        
        retour.add(ImageIO.read(new File(Chemin_Image+"/Pois/PoisF1.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/Pois/PoisF2.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/Pois/PoisF3.png")));
        retour.add(ImageIO.read(new File(Chemin_Image+"/Pois/PoisF4.png"))); 
        return retour;
    }

    public static BufferedImage visit(ProjectileDragon d) throws IOException
    {
        return ImageIO.read(new File(Chemin_Image+"ProjectileDragon.png"));
    }
    public static BufferedImage visit(ZombieAugmenter z) throws IOException
    {
        return ImageIO.read(new File(Chemin_Image+"ZombiePlusFort.png"));
    }
}
