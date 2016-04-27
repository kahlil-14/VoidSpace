package rbadia.voidspace.main;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;
	
	// status variables
	private boolean newShip;
	private boolean newEnemyShip;
	private boolean newReaperShip;
	private boolean newBossShip;
	private boolean newAsteroid;
	private boolean newAsteroid1;
	private long asteroidsDestroyed = 0;
	private long asteroids1Destroyed = 0;
	private long totalAsteroidsDestroyed = 0;
	private int enemiesDestroyed = 0;
	private int reapersDestroyed = 0;
	private int bossesDestroyed = 0;
	private int shipsLeft;
	private int score = 0;
	private int level = 1;
	
	public GameStatus(){
		
	}
	
	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}
	
	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
	
	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}
	
	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}
	
	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}
	
	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}
	
	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}
	
	/**
	 * Indicates if a new  enemy ship should be created/drawn.
	 * @return if a new enemy ship should be created/drawn
	 */
	public synchronized boolean isNewEnemyShip() {
		return newEnemyShip;
	}
	
	public synchronized void setNewEnemyShip(boolean newEnemyShip) {
		this.newEnemyShip = newEnemyShip;
	}
	
	/**
	 * Indicates if a new Reaper ship should be created/drawn.
	 * @return if a new Reaper ship should be created/drawn
	 */
	public synchronized boolean isNewReaperShip() {
		return newReaperShip;
	}
	
	public synchronized void setNewReaperShip(boolean newReaperShip) {
		this.newReaperShip = newReaperShip;
	}
	
	/**
	 * Indicates if a new Boss ship should be created/drawn.
	 * @return if a new Boss ship should be created/drawn
	 */
	public synchronized boolean isNewBossShip() {
		return newBossShip;
	}
	
	public synchronized void setNewBossShip(boolean newBossShip) {
		this.newBossShip = newBossShip;
	}
	
	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}
	
	public synchronized boolean isNewAsteroid1() {
		return newAsteroid1;
	}

	public synchronized void setNewAsteroid1(boolean newAsteroid1) {
		this.newAsteroid1 = newAsteroid1;
	}

	/**
	 * Returns the number of asteroid destroyed. 
	 * @return the number of asteroid destroyed
	 */
	public synchronized long getAsteroidsDestroyed() {
		return asteroidsDestroyed;
	}

	public synchronized void setAsteroidsDestroyed(long asteroidsDestroyed) {
		this.asteroidsDestroyed = asteroidsDestroyed;
	}
	
	public synchronized long getAsteroids1Destroyed() {
		return asteroids1Destroyed;
	}

	public synchronized void setAsteroids1Destroyed(long asteroids1Destroyed) {
		this.asteroids1Destroyed = asteroids1Destroyed;
	}
	
	public synchronized long getTotalAsteroidsDestroyed() {
		return asteroidsDestroyed + asteroids1Destroyed;
	}

	public synchronized void setTotalAsteroidsDestroyed(long totalAsteroidsDestroyed) {
		this.totalAsteroidsDestroyed = totalAsteroidsDestroyed;
	}

	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}

	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}
	
	/**
	 * Returns the number of user's score
	 * @return the number of user's score
	 */
	public synchronized int getScore() {
		return score;
	}

	public synchronized void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns the player's level
	 * @return the player's level
	 */
	public synchronized  int getLevel() {
		return level;
	}
	
	public synchronized void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Returns the number of enemy ships destroyed
	 * @return the number of enemy ships destroyed
	 */
	public synchronized int getEnemiesDestroyed() {
		return enemiesDestroyed;
	}
	
	public synchronized void setEnemiesDestroyed(int enemiesDestroyed){
		this.enemiesDestroyed = enemiesDestroyed;
	}

	/**
	 * Returns the number of Reaper ships destroyed
	 * @return the number of Reaper ships destroyed
	 */
	public synchronized int getReapersDestroyed() {
		return reapersDestroyed;
	}
	
	public synchronized void setReapersDestroyed(int reapersDestroyed){
		this.reapersDestroyed = reapersDestroyed;
	}
	
	/**
	 * Returns the number of Boss ships destroyed
	 * @return the number of Boss ships destroyed
	 */
	public synchronized int getBossesDestroyed() {
		return bossesDestroyed;
	}
	
	public synchronized void setBossesDestroyed(int bossesDestroyed){
		this.bossesDestroyed = bossesDestroyed;
	}


}
