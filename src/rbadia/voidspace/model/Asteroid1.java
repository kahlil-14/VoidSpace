package rbadia.voidspace.model;

import rbadia.voidspace.main.GameScreen;

public class Asteroid1 extends Asteroid {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid1(GameScreen screen){
		super(screen);
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed1() {
		return DEFAULT_SPEED;
	}
}
