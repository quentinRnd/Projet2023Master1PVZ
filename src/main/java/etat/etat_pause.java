package etat;

import View.ViewCommand;

public class etat_pause extends etat_bouton{
	public etat_pause(ViewCommand v)
	{
		super(v);
	}
	public void run(etat_bouton.etat_enum etat)
	{
		switch (etat) {
		case pause: {
			
		}break;
		case play:{
			super.v.etat=new etat_play(v);
			super.v.etat.run(etat);
			return;
		}
		case restart:{
			super.v.etat=new etat_restart(v);
			super.v.etat.run(etat);
			return;
		}
		case step:{
			super.v.etat=new etat_step(v);
			super.v.etat.run(etat);
			return;
		}
		case begin:
		{
			super.v.etat=new etat_begin(v);
			super.v.etat.run(etat);
			return;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + etat);
		}
		
		super.v.button_pause.setEnabled(false);
		super.v.button_play.setEnabled(true);
		super.v.button_restart.setEnabled(true);
		super.v.button_step.setEnabled(true);
		super.v.buttonChangeLayout.setEnabled(true);
		this.v.Controleur.pause();
		 
	}
}
