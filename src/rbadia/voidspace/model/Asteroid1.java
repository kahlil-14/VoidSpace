package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Asteroid1 extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
	
	private int asteroid1Width = 32;
	private int asteroid1Height = 32;
	private int speed = DEFAULT_SPEED;

	private Random rand = new Random();
	
	/**
	 * Crates a new asteroid at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Asteroid1(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - asteroid1Width),
        		0);
		this.setSize(asteroid1Width, asteroid1Height);
	}
	
	public int getAsteroid1Width() {
		return asteroid1Width;
	}
	public int getAsteroid1Height() {
		return asteroid1Height;
	}

	/**
	 * Returns the current asteroid speed
	 * @return the current asteroid speed
	 */
	public int getSpeed1() {
		return speed;
	}
	
	/**
	 * Set the current asteroid speed
	 * @param speed the speed to set
	 */
	public void setSpeed1(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default asteroid speed.
	 * @return the default asteroid speed
	 */
	public int getDefaultSpeed1(){
		return DEFAULT_SPEED;
	}
}
