package Model;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.util.ArrayList;

import Agent.Item.Projectile;
import Agent.Plant.Dragon;

import Agent.Plant.Plant;
import Agent.Plant.Pois;
import Agent.Zombie.Zombie;
import Utils.Position;





public class InputMap implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private String filename;
	public long size_x;
	public long size_y;

	
	public ArrayList<Plant> Plantes;
	public ArrayList<Zombie> Zombies;
	public ArrayList<Projectile> Projectiles;

	public long argent;
/* 
 * C -> Choux
 * D -> Dragon
 * M -> Mais
 * P -> Pois
 * Z -> Zombie
*/

	
	private transient BufferedReader buffer;
	
	

	public InputMap(String filename) throws Exception{
		
		this.filename = filename;
		
		try{
		
		InputStream flux =new FileInputStream(filename); 
		InputStreamReader lecture =new InputStreamReader(flux);
		buffer = new BufferedReader(lecture);
		
		String ligne;

		long nbX=0;
		long nbY=0;

		while ((ligne = buffer.readLine())!=null)
		{
			//ligne = ligne.trim();
			if (nbX==0) {nbX = ligne.length();}
			else if (nbX != ligne.length()) throw new Exception("Toutes les lignes doivent avoir la même longueur");
			nbY++;
		}			
		buffer.close(); 
			
		size_x = nbX;
		size_y = nbY;		
	
		flux = new FileInputStream(filename); 
		lecture = new InputStreamReader(flux);
		buffer = new BufferedReader(lecture);
		long y=0;
	
		
		this.Zombies= new ArrayList<Zombie>();
		this.Plantes=new ArrayList<Plant>();
		
		this.Projectiles=new ArrayList<Projectile>();		
		
		while ((ligne=buffer.readLine())!=null)
		{
			//ligne=ligne.trim();

			for(int x=0;x<ligne.length();x++)
			{	
				
				switch(ligne.charAt(x))
				{
					case 'D':
					{
						this.Plantes.add(new Dragon(new Position(x,y)));
					}break;
					case 'P':
					{
						this.Plantes.add(new Pois(new Position(x,y)));
					}break;
					case 'Z':
					{
						this.Zombies.add(new Zombie(new Position(x,y)));
					}break;
				}				
			}
			y++;
		}	
		
		buffer.close(); 
	
		}catch (Exception e){
			System.out.println("Erreur : "+e.getMessage());
		}
	}
	
	public long getSizeX() {return(size_x);}
	public long getSizeY() {return(size_y);}
	
	public String getFilename(){
		return filename;
	}	
}