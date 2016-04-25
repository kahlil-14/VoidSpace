package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class ReaperShip extends Rectangle {
private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 1;
	
	private int reaperShipWidth = 50;
	private int reaperShipHeight = 67;
	private int reaperSpeed = DEFAULT_SPEED;	
	
	private Random rand = new Random();
	
	/**
	 * Creates a new enemy ship at a random location at the top of the screen 
	 * @param screen the game screen
	 */
	public ReaperShip(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - reaperShipWidth),
        		0);
		this.setSize(reaperShipWidth, reaperShipHeight);
	}
	/**
	 * Get the default ship width
	 * @return the default ship width
	 */
	public int getReaperWidth() {
		return reaperShipWidth;
	}
	
	/**
	 * Get the default ship height
	 * @return the default ship height
	 */
	public int getReaperHeight() {
		return reaperShipHeight;
	}
	
	/**
	 * Returns the current ship speed
	 * @return the current ship speed
	 */
	public int getSpeed() {
		return reaperSpeed;
	}
	
	/**
	 * Set the current ship speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.reaperSpeed = speed;
	}
	
	/**
	 * Returns the default ship speed.
	 * @return the default ship speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}
