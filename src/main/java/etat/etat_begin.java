package etat;

import View.ViewCommand;;

public class etat_begin extends etat_bouton{
	public etat_begin(ViewCommand v)
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
		}break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + etat);
		}
		
		super.v.button_pause.setEnabled(false);
		super.v.button_play.setEnabled(true);
		super.v.button_restart.setEnabled(false);
		super.v.button_step.setEnabled(true);
		super.v.buttonChangeLayout.setEnabled(true);
		super.v.Controleur.Jeu.init();
		
	}
}
