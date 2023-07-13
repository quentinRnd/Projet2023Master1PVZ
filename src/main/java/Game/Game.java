package Game;
import java.io.Serializable;

import org.deeplearning4j.optimize.listeners.FailureTestingListener.And;


public abstract class Game  implements Runnable, Serializable{

	
	int turn;
	int maxTurn;
	boolean isRunning;

	//en milliseconde
	private static int AttenteMax=100000;
	
	transient Thread thread=null;
	
	long time = 1000;
	
	public Game(int maxTurn) {
		
		this.maxTurn = maxTurn;
		
	}
	
	public void init() {
		this.turn = 0;
		
		isRunning = false;
		
		initializeGame();		
	}
	




	public void step() {

		if(this.gameContinue() && turn < maxTurn) {
			turn ++;
			takeTurn();
		
		} else {
			isRunning = false;
			
		
			gameOver();
		}
	}
	
	
	public void run() {

		while(isRunning) {
			step();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void pause() {
		isRunning = false;
	}
	
	public void join() throws InterruptedException {
		this.thread.join(AttenteMax);
	}
	
	
	public void launch() {
		isRunning = true;
		this.thread = new Thread(this);
		this.thread.start();
		
	}	
		
		
	public abstract void initializeGame();
	
	public  abstract  void  takeTurn()  ;
	
	public abstract boolean gameContinue();
	
	public abstract void gameOver();
	
	
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getTurn() {
		return turn;
	}
	
}
