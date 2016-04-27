package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Asteroid1;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.ReaperShip;
import rbadia.voidspace.model.BossShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int NEW_ASTEROID1_DELAY = 500;
	private static final int NEW_ENEMY_SHIP_DELAY = 500;
	private static final int NEW_REAPER_DELAY = 500;
	private static final int NEW_BOSS_DELAY = 500;

	private long lastShipTime;
	private long lastAsteroidTime;
	private long lastAsteroid1Time;
	private long lastEnemyTime;
	private long lastReaperTime;
	private long lastBossTime;

	private int initialLoc = 0;
	private int initialLoc1 = 0;
	private int initialEnemyLoc = 0;
	private int initialReaperLoc = 0;
	private int initialBossLoc = 0;

	private int asteroid1HP = 2;
	private int reaperHP = 3;
	private int bossHP = 20;


	private Rectangle asteroidExplosion;
	private Rectangle asteroid1Explosion;
	private Rectangle shipExplosion;
	private Rectangle reaperExplosion;
	private Rectangle bossExplosion;

	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;
	private JLabel scoreValueLabel;
	private JLabel levelValueLabel;
	private JLabel enemiesValueLabel;
	private JLabel reapersValueLabel;
	private JLabel bossesValueLabel;

	private Random rand;
	private Random rand1;

	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;

	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();
		rand1 = new Random();

		initialize();

		// init graphics manager
		graphicsMan = new GraphicsManager();

		// init back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current backbuffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's backbuffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();
		EnemyShip enemyShip = gameLogic.getEnemyShip();
		ReaperShip reaperShip = gameLogic.getReaperShip();
		BossShip bossShip = gameLogic.getBossShip();
		Asteroid asteroid = gameLogic.getAsteroid();
		Asteroid1 asteroid1 = gameLogic.getAsteroid1();
		List<Bullet> bullets = gameLogic.getBullets();
		List<Bullet> enemyBullets = gameLogic.getEnemyBullets();
		List<Bullet> reaperBullets = gameLogic.getReaperBullets();
		List<Bullet> bossBullets = gameLogic.getBossBullets();

		// set orignal font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 35 random stars
		drawStars(35);

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			if((currentTime - lastReaperTime) < NEW_REAPER_DELAY){
				graphicsMan.drawReaperExplosion(reaperExplosion, g2d, this);
			}
			if((currentTime - lastAsteroid1Time) < NEW_ASTEROID1_DELAY){
				graphicsMan.drawAsteroid1Explosion(asteroid1Explosion, g2d, this);
			}
			if((currentTime - lastBossTime) < NEW_BOSS_DELAY){
				graphicsMan.drawBossExplosion(bossExplosion, g2d, this);
			}

			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			return;
		}

		// draw asteroid
		if(!status.isNewAsteroid()){
			// draw the asteroid until it reaches the bottom of the screen
			if(asteroid.getY() + asteroid.getSpeed() < this.getHeight()){
				asteroid.translate(0, asteroid.getSpeed());
				graphicsMan.drawAsteroid(asteroid, g2d, this);
				if (status.getLevel()%2==0){
					if (this.initialLoc<this.getWidth()/2){
						asteroid.translate(1, 0);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					}
					else {
						asteroid.translate(-1, 0);
						graphicsMan.drawAsteroid(asteroid, g2d, this);
					}
				}
			}
			else{
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
				this.initialLoc = asteroid.x;
			}
		}
		else{
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
				// draw a new asteroid
				lastAsteroidTime = currentTime;
				status.setNewAsteroid(false);
				asteroid.setLocation(rand.nextInt(getWidth() - asteroid.width), 0);
				this.initialLoc = asteroid.x;
			}
			else{
				// draw explosion
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
		}



		// draw asteroid1
		if(status.getLevel() > 1){
			if(!status.isNewAsteroid1()){
				// draw the asteroid until it reaches the bottom of the screen
				if(asteroid1.getY() + asteroid1.getSpeed1() < this.getHeight()){
					asteroid1.translate(0, asteroid1.getSpeed1());
					graphicsMan.drawAsteroid1(asteroid1, g2d, this);
					if ((status.getLevel()-1)%2==0){
						if (this.initialLoc1<this.getWidth()/2){
							asteroid1.translate(1, 0);
							graphicsMan.drawAsteroid1(asteroid1, g2d, this);
						}
						else {
							asteroid1.translate(-1, 0);
							graphicsMan.drawAsteroid1(asteroid1, g2d, this);
						}
					}
				}
				else{
					asteroid1.setLocation(rand1.nextInt(getWidth() - asteroid1.width), 0);
					this.initialLoc1 = asteroid1.x;
					asteroid1HP = 2;
				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastAsteroid1Time) > NEW_ASTEROID1_DELAY){
					// draw a new asteroid
					lastAsteroid1Time = currentTime;
					status.setNewAsteroid1(false);
					asteroid1.setLocation(rand1.nextInt(getWidth() - asteroid1.width), 0);
					this.initialLoc1 = asteroid1.x;
					asteroid1HP = 2;
				}
				else{
					// draw explosion
					graphicsMan.drawAsteroid1Explosion(asteroid1Explosion, g2d, this);
				}
			}
		}


		// draw bullets
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			graphicsMan.drawBullet(bullet, g2d, this);

			boolean remove = gameLogic.moveBullet(bullet);
			if(remove){
				bullets.remove(i);
				i--;
			}
		}

		// draw enemy bullets 
		for(int i=0; i<enemyBullets.size(); i++){
			Bullet enemyBullet = enemyBullets.get(i);
			graphicsMan.drawEnemyBullet(enemyBullet, g2d, this);

			boolean remove1 = gameLogic.moveEnemyBullet(enemyBullet);
			if(remove1){
				enemyBullets.remove(i);
				i--;
			}
		}

		// draw Reaper bullets 
		for(int i=0; i<reaperBullets.size(); i++){
			Bullet reaperBullet = reaperBullets.get(i);
			graphicsMan.drawReaperBullet(reaperBullet, g2d, this);

			boolean remove2 = gameLogic.moveReaperBullet(reaperBullet);
			if(remove2){
				reaperBullets.remove(i);
				i--;
			}
		}
		
		 //draw Boss bullets 
				for(int i=0; i<bossBullets.size(); i++){
					Bullet bossBullet = bossBullets.get(i);
					graphicsMan.drawBossBullet(bossBullet, g2d, this);

					boolean remove2 = gameLogic.moveBossBullet(bossBullet);
					if(remove2){
						bossBullets.remove(i);
						i--;
					}
				}

		// check bullet-asteroid collisions
		for(int i=0; i<bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(asteroid.intersects(bullet)){
				// increase asteroids destroyed and score count
				status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
				status.setScore(status.getScore() + 100);

				//increase level if 10 asteroids or enemies are destroyed
				if ((status.getTotalAsteroidsDestroyed() + status.getEnemiesDestroyed() + status.getReapersDestroyed())%5 == 0){
					status.setLevel(status.getLevel() + 1);
				}

				// "remove" asteroid
				asteroidExplosion = new Rectangle(
						asteroid.x,
						asteroid.y,
						asteroid.width,
						asteroid.height);
				asteroid.setLocation(-asteroid.width, -asteroid.height);
				status.setNewAsteroid(true);
				lastAsteroidTime = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				// remove bullet
				bullets.remove(i);
				break;
			}else if(asteroid1.intersects(bullet)){
				asteroid1HP--;
				// remove bullet
				bullets.remove(i);

				if(asteroid1HP == 0){

					// increase asteroids destroyed and score count
					status.setAsteroids1Destroyed(status.getAsteroids1Destroyed() + 1);
					status.setScore(status.getScore() + 100);

					//increase level if 10 asteroids or enemies are destroyed
					if ((status.getTotalAsteroidsDestroyed() + status.getEnemiesDestroyed() + status.getReapersDestroyed())%5 == 0){
						status.setLevel(status.getLevel() + 1);
					}

					// "remove" asteroid1
					asteroid1Explosion = new Rectangle(
							asteroid1.x,
							asteroid1.y,
							asteroid1.width,
							asteroid1.height);
					asteroid1.setLocation(-asteroid1.width, -asteroid1.height);
					status.setNewAsteroid1(true);
					lastAsteroid1Time = System.currentTimeMillis();

					// play asteroid explosion sound
					soundMan.playAsteroid1ExplosionSound();
					break;
				}
			}
		} 

		// check bullet-fighter collisions
		if (status.getLevel()>2){
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				try{
					if(enemyShip.intersects(bullet)){
						// increase enemies destroyed and score count
						status.setEnemiesDestroyed(status.getEnemiesDestroyed() + 1);
						status.setScore(status.getScore() + 500);

						//increase level if 5 asteroids are destroyed
						if ((status.getEnemiesDestroyed() + status.getTotalAsteroidsDestroyed() + status.getReapersDestroyed())%5 == 0){
							status.setLevel(status.getLevel() + 1);
						}

						// "remove" fighter
						shipExplosion = new Rectangle(
								enemyShip.x,
								enemyShip.y,
								enemyShip.width,
								enemyShip.height);
						enemyShip.setLocation(-enemyShip.width, -enemyShip.height);
						status.setNewEnemyShip(true);
						lastEnemyTime = System.currentTimeMillis();

						// play asteroid explosion sound
						soundMan.playShipExplosionSound();

						// remove bullet
						bullets.remove(i);
						break;
					}
				}catch(Exception e){
					break;
				}
			} 
		}

		// check bullet-Reaper collisions
		if (status.getLevel()>3){
			for(int i=0; i<bullets.size(); i++){
				Bullet bullet = bullets.get(i);
				try{
					if(reaperShip.intersects(bullet)){
						reaperHP--;
						// remove bullet
						bullets.remove(i);
						if(reaperHP == 0){
							// increase enemies destroyed and score count
							status.setReapersDestroyed(status.getReapersDestroyed() + 1);
							status.setScore(status.getScore() + 1000);

							//increase level if 5 asteroids/Reapers/Fighters are destroyed
							if ((status.getEnemiesDestroyed() + status.getTotalAsteroidsDestroyed() + status.getReapersDestroyed())%5 == 0){
								status.setLevel(status.getLevel() + 1);
							}

							// "remove" Reaper
							reaperExplosion = new Rectangle(
									reaperShip.x,
									reaperShip.y,
									reaperShip.width,
									reaperShip.height);
							reaperShip.setLocation(-reaperShip.width, -reaperShip.height);
							status.setNewReaperShip(true);
							lastReaperTime = System.currentTimeMillis();

							// play ship explosion sound
							soundMan.playShipExplosionSound();
							break;
						}
					}
				}catch(Exception e){
					break;
				}
			} 
		}
		
		// check bullet-Boss collisions
				if (status.getLevel()%10 == 0){
					for(int i=0; i<bullets.size(); i++){
						Bullet bullet = bullets.get(i);
						try{
							if(bossShip.intersects(bullet)){
								bossHP--;
								// remove bullet
								bullets.remove(i);
								if(bossHP == 0){
									// increase enemies destroyed and score count
									status.setBossesDestroyed(status.getBossesDestroyed() + 1);
									status.setScore(status.getScore() + 1000);

									//increase level if 5 asteroids/Reapers/Fighters are destroyed
									if ((status.getEnemiesDestroyed() + status.getTotalAsteroidsDestroyed() + status.getReapersDestroyed()
									+ status.getBossesDestroyed())%5 == 0){
										status.setLevel(status.getLevel() + 1);
									}

									// "remove" Reaper
									bossExplosion = new Rectangle(
											bossShip.x,
											bossShip.y,
											bossShip.width,
											bossShip.height);
									bossShip.setLocation(-bossShip.width, -bossShip.height);
									status.setNewBossShip(true);
									lastBossTime = System.currentTimeMillis();

									// play boss ship explosion sound
									soundMan.playBossShipExplosionSound();
									break;
								}
							}
						}catch(Exception e){
							break;
						}
					} 
				}
		
		
		

		// check reaper bullets- ship collisions 
		if (status.getLevel()>3){
			for (int i=0; i<reaperBullets.size(); i++){
				Bullet reaperBullet = reaperBullets.get(i);
				if (reaperBullet.intersects(ship)){
					status.setShipsLeft(status.getShipsLeft() - 1);

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play ship explosion sound
					soundMan.playShipExplosionSound();
				}
			}
		}
		
		// check boss bullet- ship collisions 
				if (status.getLevel() %10== 0){
					for (int i=0; i<bossBullets.size(); i++){
						Bullet bossBullet = bossBullets.get(i);
						if (bossBullet.intersects(ship)){
							status.setShipsLeft(status.getShipsLeft() - 1);

							// "remove" ship
							shipExplosion = new Rectangle(
									ship.x,
									ship.y,
									ship.width,
									ship.height);
							ship.setLocation(this.getWidth() + ship.width, -ship.height);
							status.setNewShip(true);
							lastShipTime = System.currentTimeMillis();

							// play ship explosion sound
							soundMan.playShipExplosionSound();
						}
					}
				}

		// check enemy bullet- ship collisions 
		if (status.getLevel()>2){
			for (int i=0; i<enemyBullets.size(); i++){
				Bullet enemyBullet = enemyBullets.get(i);
				if (enemyBullet.intersects(ship)){
					status.setShipsLeft(status.getShipsLeft() - 1);

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play ship explosion sound
					soundMan.playShipExplosionSound();
				}
			}
		}

		// draw ship
		if(!status.isNewShip()){
			// draw it in its current location
			graphicsMan.drawShip(ship, g2d, this);

		}else{
			// draw a new one
			long currentTime = System.currentTimeMillis();
			if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
				lastShipTime = currentTime;
				status.setNewShip(false);
				ship = gameLogic.newShip(this);
			}
			else{
				// draw explosion
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
		}

		// draw enemy ship 
		if (status.getLevel()>2){
			if(!status.isNewEnemyShip()){
				// draw the enemy ship until it reaches the bottom of the screen
				if(enemyShip.getY() + enemyShip.getSpeed() < this.getHeight()){
					enemyShip.translate(0, enemyShip.getSpeed());
					if(enemyShip.x==ship.x){
						gameLogic.fireEnemyBullet();
					}
					graphicsMan.drawEnemyShip(enemyShip, g2d, this);
					if (status.getLevel()>3){
						if (this.initialEnemyLoc<this.getWidth()/2){
							enemyShip.translate(1, 0);
							graphicsMan.drawEnemyShip(enemyShip, g2d, this);
							if(enemyShip.x==ship.x){
								gameLogic.fireEnemyBullet();
							}
						}
						else {
							enemyShip.translate(-1, 0);
							graphicsMan.drawEnemyShip(enemyShip, g2d, this);
							if(enemyShip.x==ship.x){
								gameLogic.fireEnemyBullet();
							}
						}
					}
				}
				else{
					enemyShip.setLocation(rand.nextInt(getWidth() - enemyShip.width), 0);
					this.initialEnemyLoc = enemyShip.x;
				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastEnemyTime) > NEW_ENEMY_SHIP_DELAY){
					// draw a new enemy ship
					lastEnemyTime = currentTime;
					status.setNewEnemyShip(false);
					enemyShip = gameLogic.newEnemyShip(this);
					enemyShip.setLocation(rand.nextInt(getWidth() - enemyShip.width), 0);
					this.initialEnemyLoc = enemyShip.x;
				}
				else{
					// draw explosion
					graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
				}
			}
		}

		// draw Reaper ship 
		if (status.getLevel()>3){
			if(!status.isNewReaperShip()){
				// draw the enemy ship until it reaches the bottom of the screen
				if(reaperShip.getY() + reaperShip.getSpeed() < this.getHeight()){
					reaperShip.translate(0, reaperShip.getSpeed());
					if(reaperShip.x==ship.x){
						gameLogic.fireEnemyBullet();
					}
					graphicsMan.drawReaperShip(reaperShip, g2d, this);
					if (status.getLevel()>4){
						if (this.initialReaperLoc<this.getWidth()/2){
							reaperShip.translate(1, 0);
							graphicsMan.drawReaperShip(reaperShip, g2d, this);
							if(reaperShip.x==ship.x){
								gameLogic.fireReaperBullet();
							}
						}else {
							reaperShip.translate(-1, 0);
							graphicsMan.drawReaperShip(reaperShip, g2d, this);
							if(reaperShip.x==ship.x){
								gameLogic.fireReaperBullet();
							}
						}
					}
				}else{
					reaperHP = 3;
					reaperShip.setLocation(rand.nextInt(getWidth() - reaperShip.width), 0);
					this.initialReaperLoc = reaperShip.x;						
				}
			}
			else{
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastReaperTime) > NEW_REAPER_DELAY){
					reaperHP = 3;
					// draw a new reaper ship
					lastReaperTime = currentTime;
					status.setNewReaperShip(false);
					reaperShip = gameLogic.newReaperShip(this);
					reaperShip.setLocation(rand.nextInt(getWidth() - reaperShip.width), 0);
					this.initialReaperLoc = reaperShip.x;
				}
				else{
					// draw explosion
					graphicsMan.drawReaperExplosion(reaperExplosion, g2d, this);
				}
			}
		}
		
		// draw Boss ship 
				if (status.getLevel()%10 == 0){
					if(!status.isNewBossShip()){
						// draw the enemy ship until it reaches the bottom of the screen
						if(bossShip.getY() + bossShip.getSpeed() < this.getHeight()){
							bossShip.translate(0, bossShip.getSpeed());
							if(bossShip.x==ship.x){
								gameLogic.fireBossBullet();
							}
							graphicsMan.drawBossShip(bossShip, g2d, this);
							if (status.getLevel()>4){
								if (this.initialBossLoc<this.getWidth()/2){
									bossShip.translate(1, 0);
									graphicsMan.drawBossShip(bossShip, g2d, this);
									if(bossShip.x==ship.x){
										gameLogic.fireBossBullet();
									}
								}else {
									bossShip.translate(-1, 0);
									graphicsMan.drawBossShip(bossShip, g2d, this);
									if(bossShip.x==ship.x){
										gameLogic.fireBossBullet();
									}
								}
							}
						}else{
							bossHP = 20;
							bossShip.setLocation(rand.nextInt(getWidth() - bossShip.width), 0);
							this.initialBossLoc = bossShip.x;						
						}
					}
					else{
						long currentTime = System.currentTimeMillis();
						if((currentTime - lastBossTime) > NEW_BOSS_DELAY){
							bossHP = 20;
							// draw a new boss ship
							lastBossTime = currentTime;
							status.setNewBossShip(false);
							bossShip = gameLogic.newBossShip(this);
							bossShip.setLocation(rand.nextInt(getWidth() - bossShip.width), 0);
							this.initialBossLoc = bossShip.x;
						}
						else{
							// draw explosion
							graphicsMan.drawBossExplosion(bossExplosion, g2d, this);
						}
					}
				}

		// check ship-asteroid collisions
		if(asteroid.intersects(ship)){
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			status.setAsteroidsDestroyed(status.getAsteroidsDestroyed() + 1);
			status.setScore(status.getScore() + 50);

			//increase level if 5 asteroids are destroyed
			if ((status.getEnemiesDestroyed() + status.getTotalAsteroidsDestroyed() + status.getReapersDestroyed())%5 == 0){
				status.setLevel(status.getLevel() + 1);
			}

			// "remove" asteroid
			asteroidExplosion = new Rectangle(
					asteroid.x,
					asteroid.y,
					asteroid.width,
					asteroid.height);
			asteroid.setLocation(-asteroid.width, -asteroid.height);
			status.setNewAsteroid(true);
			lastAsteroidTime = System.currentTimeMillis();

			// "remove" ship
			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
			// play asteroid explosion sound
			soundMan.playAsteroidExplosionSound();
		}else if(asteroid1.intersects(ship)){
			asteroid1HP --;
			// decrease number of ships left
			status.setShipsLeft(status.getShipsLeft() - 1);
			status.setAsteroids1Destroyed(status.getAsteroids1Destroyed() + 1);
			status.setScore(status.getScore() + 50);

			//increase level if 5 asteroids are destroyed
			if ((status.getEnemiesDestroyed() + status.getTotalAsteroidsDestroyed() + status.getReapersDestroyed())%5 == 0){
				status.setLevel(status.getLevel() + 1);
			}

			if(asteroid1HP == 0){

				// "remove" asteroid1
				asteroid1Explosion = new Rectangle(
						asteroid1.x,
						asteroid1.y,
						asteroid1.width,
						asteroid1.height);
				asteroid1.setLocation(-asteroid1.width, -asteroid1.height);
				status.setNewAsteroid1(true);
				lastAsteroid1Time = System.currentTimeMillis();

				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();
			}

			// "remove" ship
			shipExplosion = new Rectangle(
					ship.x,
					ship.y,
					ship.width,
					ship.height);
			ship.setLocation(this.getWidth() + ship.width, -ship.height);
			status.setNewShip(true);
			lastShipTime = System.currentTimeMillis();

			// play ship explosion sound
			soundMan.playShipExplosionSound();
		}

		// check ship-enemy collisions
		if (status.getLevel()>2){
			try{
				if(enemyShip.intersects(ship)){
					// decrease number of ships left
					status.setShipsLeft(status.getShipsLeft() - 1);

					status.setEnemiesDestroyed(status.getEnemiesDestroyed() + 1);
					status.setScore(status.getScore() + 50);

					//increase level if 5 asteroids are destroyed
					if ((status.getEnemiesDestroyed() + status.getTotalAsteroidsDestroyed() + status.getReapersDestroyed())%5 == 0){
						status.setLevel(status.getLevel() + 1);
					}

					// "remove" asteroid
					shipExplosion = new Rectangle(
							enemyShip.x,
							enemyShip.y,
							enemyShip.width,
							enemyShip.height);
					enemyShip.setLocation(-enemyShip.width, -enemyShip.height);
					status.setNewEnemyShip(true);
					lastEnemyTime = System.currentTimeMillis();

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play ship explosion sound
					soundMan.playShipExplosionSound();
					// play enemy explosion sound
					soundMan.playShipExplosionSound();
				}
			}catch(Exception e){

			}

		}

		// check ship-reaper collisions
		if (status.getLevel()>3){
			try{
			if(reaperShip.intersects(ship)){
				reaperHP --;
				// decrease number of ships left
				if(reaperHP == 0){
					status.setShipsLeft(status.getShipsLeft() - 1);
					status.setEnemiesDestroyed(status.getEnemiesDestroyed() + 1);
					status.setScore(status.getScore() + 350);

					//increase level if 5 asteroids are destroyed
					if ((status.getEnemiesDestroyed() + status.getTotalAsteroidsDestroyed() + status.getReapersDestroyed())%5 == 0){
						status.setLevel(status.getLevel() + 1);
					}

					// "remove" reaper ship
					shipExplosion = new Rectangle(
							reaperShip.x,
							reaperShip.y,
							reaperShip.width,
							reaperShip.height);
					reaperShip.setLocation(-reaperShip.width, -reaperShip.height);
					status.setNewReaperShip(true);
					lastReaperTime = System.currentTimeMillis();

					// "remove" ship
					shipExplosion = new Rectangle(
							ship.x,
							ship.y,
							ship.width,
							ship.height);
					ship.setLocation(this.getWidth() + ship.width, -ship.height);
					status.setNewShip(true);
					lastShipTime = System.currentTimeMillis();

					// play ship explosion sound
					soundMan.playShipExplosionSound();
					// play enemy explosion sound
					soundMan.playShipExplosionSound();
				}
			}
			}catch(Exception e){
				
			}
		}
		
		// check ship-boss collisions
				if (status.getLevel()>3){
					if(bossShip.intersects(ship)){
						bossHP --;
						// decrease number of ships left
						if(bossHP == 0){
							status.setShipsLeft(status.getShipsLeft() - 1);
							status.setBossesDestroyed(status.getBossesDestroyed() + 1);
							status.setScore(status.getScore() + 350);

							if ((status.getEnemiesDestroyed() + status.getTotalAsteroidsDestroyed() + status.getReapersDestroyed() 
							+ status.getBossesDestroyed())%5 == 0){
								status.setLevel(status.getLevel() + 1);
							}

							// "remove" boss ship
							shipExplosion = new Rectangle(
									bossShip.x,
									bossShip.y,
									bossShip.width,
									bossShip.height);
							bossShip.setLocation(-bossShip.width, -bossShip.height);
							status.setNewBossShip(true);
							lastBossTime = System.currentTimeMillis();

							// "remove" ship
							shipExplosion = new Rectangle(
									ship.x,
									ship.y,
									ship.width,
									ship.height);
							ship.setLocation(this.getWidth() + ship.width, -ship.height);
							status.setNewShip(true);
							lastShipTime = System.currentTimeMillis();

							// play ship explosion sound
							soundMan.playShipExplosionSound();
							// play boss explosion sound
							soundMan.playBossShipExplosionSound();
						}
					}
				}

		// update asteroids destroyed label
		destroyedValueLabel.setText(Long.toString(status.getTotalAsteroidsDestroyed()));

		// update ships left label
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));

		//update score label
		scoreValueLabel.setText(Integer.toString(status.getScore()));

		//update level label
		levelValueLabel.setText(Integer.toString(status.getLevel()));

		//update enemies destroyed label
		enemiesValueLabel.setText(Integer.toString(status.getEnemiesDestroyed()));

		//update reapers destroyed label
		reapersValueLabel.setText(Integer.toString(status.getReapersDestroyed()));
		
		//update bosses destroyed label
		bossesValueLabel.setText(Integer.toString(status.getBossesDestroyed()));
	}

	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.WHITE);
		g2d.drawString(gameOverStr, strX, strY);
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {
		g2d.setColor(Color.WHITE);
		for(int i=0; i<numberOfStars; i++){
			int x = (int)(Math.random() * this.getWidth());
			int y = (int)(Math.random() * this.getHeight());
			g2d.drawLine(x, y, x, y);
		}
	}

	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
		String gameTitleStr = "Void Space";

		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.BOLD).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(gameTitleStr, strX, strY);

		g2d.setFont(originalFont);
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver(){
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void doNewGame(){		
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastAsteroid1Time = -NEW_ASTEROID1_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setForeground(Color.BLACK);
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getTotalAsteroidsDestroyed()));
		scoreValueLabel.setText(Integer.toString(status.getScore()));
		levelValueLabel.setText(Integer.toString(status.getLevel()));
		enemiesValueLabel.setText(Integer.toString(status.getEnemiesDestroyed()));
		reapersValueLabel.setText(Integer.toString(status.getReapersDestroyed()));
		bossesValueLabel.setText(Integer.toString(status.getBossesDestroyed()));
	}

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}

	/**
	 * Sets the label that displays the value for the score
	 * @param scoreValueLabel the label to set
	 */
	public void setScoreValueLabel(JLabel scoreValueLabel) {
		this.scoreValueLabel = scoreValueLabel;
	}

	/**
	 * Sets the label that displays the value for the score
	 * @param levelValueLabel the label to set
	 */
	public void setLevelValueLabel(JLabel levelValueLabel) {
		this.levelValueLabel = levelValueLabel;
	}

	/**
	 * Sets the label that displays the value for enemy ships destroyed
	 * @param enemiesValueLabel the label to set
	 */
	public void setEnemiesValueLabel(JLabel enemiesValueLabel) {
		this.enemiesValueLabel = enemiesValueLabel;
	}

	/**
	 * Sets the label that displays the value for Reaper ships destroyed
	 * @param enemiesValueLabel the label to set
	 */
	public void setReapersValueLabel(JLabel reapersValueLabel) {
		this.reapersValueLabel = reapersValueLabel;
	}
	
	public void setBossesValueLabel(JLabel bossesValueLabel) {
		this.bossesValueLabel = bossesValueLabel;
	}
}