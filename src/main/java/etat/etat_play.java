package etat;

import View.ViewCommand;

public class etat_play extends etat_bouton {
	public etat_play(ViewCommand v)
	{
		super(v);
	}
	public void run(etat_bouton.etat_enum etat)
	{
		switch (etat) {
		case pause: {
			super.v.etat=new etat_pause(v);
			super.v.etat.run(etat);
			return;
		}
		case play:{
			
		}break;
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
		super.v.button_pause.setEnabled(true);
		super.v.button_play.setEnabled(false);
		super.v.button_restart.setEnabled(true);
		super.v.button_step.setEnabled(false);
		super.v.buttonChangeLayout.setEnabled(false);
		super.v.Controleur.play(); 
	}
}
