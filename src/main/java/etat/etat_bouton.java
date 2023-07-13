package etat;

import View.ViewCommand;

public abstract class  etat_bouton {
	public enum etat_enum {
		pause,play,restart,step,begin
	}

	public ViewCommand v;
	etat_bouton (ViewCommand v)
	{
		this.v=v;
	}
	public  abstract void run(etat_enum etat);
}
