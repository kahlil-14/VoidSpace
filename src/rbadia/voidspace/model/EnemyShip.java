package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class EnemyShip extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 3;
	
	private int enemyShipWidth = 25;
	private int enemyShipHeight = 25;
	private int enemySpeed = DEFAULT_SPEED;	
	
	private Random rand = new Random();
	
	/**
	 * Creates a new enemy ship at a random location at the top of the screen 
	 * @param screen the game screen
	 */
	public EnemyShip(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - enemyShipWidth),
        		0);
		this.setSize(enemyShipWidth, enemyShipHeight);
	}
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getShipWidth() {
		return enemyShipWidth;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getShipHeight() {
		return enemyShipHeight;
	}
	
	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getSpeed() {
		return enemySpeed;
	}
	
	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.enemySpeed = speed;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
