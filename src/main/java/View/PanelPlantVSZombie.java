package View;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.commons.lang3.SerializationUtils;

import Agent.Agent;
import Agent.Item.Projectile;
import Agent.Plant.Plant;
import Controler.ControlerPlantVSZombie;
import Game.PlantVSZombieGame;
import Model.InputMap;
import Utils.Position;
import Visitor.VisitorAgentAffichage;




/** 
 * Classe qui permet de charger d'afficher le panneau du jeu à partir d'une carte et de listes d'agents avec leurs positions.
 * 
 */


public class PanelPlantVSZombie extends JPanel implements PropertyChangeListener,MouseListener {

	private static final long serialVersionUID = 1L;
	
	protected Color ground_Color= new Color(0,0,0);


	private int fen_x;
	private int fen_y;
	
	private double stepx;
	private double stepy;
	
	
	float[] contraste = { 0, 0, 0, 1.0f };


	InputMap inputmap;

	ControlerPlantVSZombie controleur;

	
	
	int cpt;

	public PanelPlantVSZombie(InputMap in,ControlerPlantVSZombie c) {
		this.inputmap=in;	
		this.controleur=c;
		addMouseListener(this);	
	}

	public void paint(Graphics g){

		fen_x = getSize().width;
		fen_y = getSize().height;
		
		this.stepx = fen_x/(double)this.inputmap.size_x;
		this.stepy = fen_y/(double)this.inputmap.size_y;
		
		g.setColor(ground_Color);
		g.fillRect(0, 0,fen_x,fen_y);

		fondQuadrillage(g);

		for(Agent a :this.inputmap.Plantes)
		{
			paint_Agent(g, a);
		}

		for(Agent a :this.inputmap.Zombies)
		{
			paint_Agent(g, a);
		}

		for(Projectile p:this.inputmap.Projectiles)
		{
			paint_Projectile(g, p);
		}
			
		cpt++;
	}
	void fondQuadrillage(Graphics g)
	{
		for(int i=0;i<this.inputmap.size_x;++i)
		{
			for(int j=0;j<this.inputmap.size_y;++j)
			{
				double pos_x=i*stepx;
				double pos_y=j*stepy;

				try {
					Image img = ImageIO.read(new File(VisitorAgentAffichage.Chemin_Image+"Terrain.png"));;
					Image img_scaled = img.getScaledInstance((int)stepx, (int)stepy, Image.SCALE_DEFAULT);
					g.drawImage(img_scaled, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	void paint_Agent(Graphics g, Agent a){



		long x = a.position.X;
		long y = a.position.Y;

		double pos_x=x*stepx;
		double pos_y=y*stepy;

		try {
			Image img = a.visit();
			Image img_scaled = img.getScaledInstance((int)stepx, (int)stepy, Image.SCALE_DEFAULT);
			g.drawImage(img_scaled, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
		} catch (IOException e) {
			e.printStackTrace();
		}


		
		
	}

	void paint_Projectile(Graphics g, Projectile p){



		long x = p.position.X;
		long y = p.position.Y;

		double pos_x=x*stepx;
		double pos_y=y*stepy;

		try {
			Image img = p.visit();
			g.drawImage(img, (int)pos_x, (int)pos_y, (int)stepx, (int)stepy, this);
		} catch (IOException e) {
			e.printStackTrace();
		}


		
		
	}

	public long getSizeX() {
		return this.inputmap.getSizeX();
	}

	public long getSizeY() {
		return this.inputmap.getSizeY();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(PlantVSZombieGame.INPUTMAPCHANGED))
		{
			this.inputmap=(InputMap)evt.getNewValue();
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		long x=(long)(arg0.getX()/this.stepx);
		long y=(long)(arg0.getY()/this.stepy);
		String typeplante=this.controleur.vc.listTypeSnake.getSelectedValue();
		Plant.TypePlant tplante=null;
		for(Plant.TypePlant t:Plant.TypePlant.values())
		{
			if(t.toString()==typeplante)
			{
				tplante=t;
			}
		}
		if(tplante!=null)
		{
			this.controleur.Jeu.Planter(new Position(x, y),tplante);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	
}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	




}
