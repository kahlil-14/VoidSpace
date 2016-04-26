package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Asteroid1;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.ReaperShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;


/**
 * Handles general game logic and status.
 */
public class GameLogic {
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;
	
	private Ship ship;
	private EnemyShip enemyShip;
	private ReaperShip reaperShip;
	private Asteroid asteroid;
	private Asteroid1 asteroid1;
	private List<Bullet> bullets;
	private List<Bullet> enemyBullets;
	private List<Bullet> reaperBullets;
	
	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		
		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();
		
		// init some variables
		bullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
		reaperBullets = new ArrayList<Bullet>(5);
	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame(){
		status.setGameStarting(true);
		
		// init game variables
		bullets = new ArrayList<Bullet>();

		status.setShipsLeft(3);
		status.setGameOver(false);
		status.setAsteroidsDestroyed(0);
		status.setAsteroids1Destroyed(0);
		status.setTotalAsteroidsDestroyed(0);
		status.setEnemiesDestroyed(0);
		status.setReapersDestroyed(0);
		status.setNewAsteroid(false);
		status.setNewAsteroid1(false);
		status.setNewEnemyShip(true);
		status.setNewReaperShip(true);
		status.setScore(0);
		status.setLevel(1);
				
		// init the ship and the asteroid
        newShip(gameScreen);
        newAsteroid(gameScreen);
        newAsteroid1(gameScreen);
        
        // prepare game screen
        gameScreen.doNewGame();
        
        // delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
				gameOver();
			}
		}
	}
	
	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();
		
        // delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);
		soundMan.playBulletSound();
	}
	
	/**
	 * Fire a bullet from enemy ship.
	 */
	public void fireEnemyBullet(){
		Bullet enemyBullet = new Bullet(enemyShip);
		enemyBullets.add(enemyBullet);
		soundMan.playBulletSound();
	}
	
	/**
	 * Fire a bullet from enemy ship.
	 */
	public void fireReaperBullet(){		
		Bullet reaperBullet = new Bullet(reaperShip);
		reaperBullets.add(reaperBullet);
		soundMan.playBulletSound();
	}
	
	/**
	 * Move an enemy bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveEnemyBullet(Bullet enemyBullet){
		if(enemyBullet.getY() - enemyBullet.getSpeed() <= gameScreen.getHeight()){
			enemyBullet.translate(0, enemyBullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Move a Reaper bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveReaperBullet(Bullet reaperBullet){
		if(reaperBullet.getY() - reaperBullet.getSpeed() <= gameScreen.getHeight()){
			reaperBullet.translate(0, reaperBullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}
	
	/**
	 * Create a new enemy ship (and replace current one).
	 */
	public EnemyShip newEnemyShip(GameScreen screen){
		this.enemyShip = new EnemyShip(screen);
		return enemyShip;
	}
	
	/**
	 * Create a new enemy ship (and replace current one).
	 */
	public ReaperShip newReaperShip(GameScreen screen){
		this.reaperShip = new ReaperShip(screen);
		return reaperShip;
	}
	
	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(GameScreen screen){
		this.asteroid = new Asteroid(screen);
		return asteroid;
	}
	
	public Asteroid1 newAsteroid1(GameScreen screen){
		this.asteroid1 = new Asteroid1(screen);
		return asteroid1;
	}
	
	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Returns the Reaper ship.
	 * @return the Reaper ship
	 */
	public ReaperShip getReaperShip() {
		return reaperShip;
	}
	
	/**
	 * Returns the enemy ship.
	 * @return the enemy ship
	 */
	public EnemyShip getEnemyShip() {
		return enemyShip;
	}
	
	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public Asteroid getAsteroid() {
		return asteroid;
	}
	
	public Asteroid1 getAsteroid1() {
		return asteroid1;
	}

	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}
	
	/**
	 * Returns the list of enemy bullets.
	 * @return the list of enemy bullets
	 */
	public List<Bullet> getEnemyBullets() {
		return enemyBullets;
	}
	
	/**
	 * Returns the list of Reaper bullets.
	 * @return the list of Reaper bullets
	 */
	public List<Bullet> getReaperBullets() {
		return reaperBullets;
	}

}
