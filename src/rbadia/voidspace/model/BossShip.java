package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class BossShip extends Rectangle {
private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 2;
	
	private int bossShipWidth = 115;
	private int bossShipHeight = 85;
	private int bossSpeed = DEFAULT_SPEED;	
	
	private Random rand = new Random();
	
	/**
	 * Creates a new enemy ship at a random location at the top of the screen 
	 * @param screen the game screen
	 */
	public BossShip(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - bossShipWidth),
        		0);
		this.setSize(bossShipWidth, bossShipHeight);
	}
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getBossWidth() {
		return bossShipWidth;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getBossHeight() {
		return bossShipHeight;
	}
	
	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getSpeed() {
		return bossSpeed;
	}
	
	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.bossSpeed = speed;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
