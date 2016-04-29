package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Asteroid1;
import rbadia.voidspace.model.BossShip;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.ReaperShip;
import rbadia.voidspace.model.Ship;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
	private BufferedImage shipImg;
	private BufferedImage bulletImg;
	private BufferedImage asteroidImg;
	private BufferedImage asteroid1Img;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage asteroid1ExplosionImg;
	private BufferedImage shipExplosionImg;
	private BufferedImage enemyShipImg;
	private BufferedImage reaperShipImg;
	private BufferedImage reaperExplosionImg;
	private BufferedImage bossShipImg;
	private BufferedImage bossExplosionImg;
	private BufferedImage bossBulletImg;

	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
		// load images
		try {
			this.reaperExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/reaperExplosion.png"));
			this.reaperShipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Reaper.png"));
			this.enemyShipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShip.png"));
			this.shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.asteroid1Img = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid1.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.asteroid1ExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.shipExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			this.bossExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bossExplosion.png"));
			this.bossShipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/Boss.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.bossBulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bossBullet.png"));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param ship the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipImg, ship.x, ship.y, observer);
	}

	/**
	 * Draws a enemy Reaper image to the specified graphics canvas.
	 * @param enemy ship the enemy ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawReaperShip(ReaperShip reaper, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(reaperShipImg, reaper.x, reaper.y, observer);
	}

	/**
	 * Draws an enemy ship image to the specified graphics canvas.
	 * @param enemy ship the enemy ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawEnemyShip(EnemyShip enemyShip, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyShipImg, enemyShip.x, enemyShip.y, observer);
	}

	/**
	 * Draws the boss ship image to the specified graphics canvas.
	 * @param boss ship the boss ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBossShip(BossShip bossShip, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossShipImg, bossShip.x, bossShip.y, observer);
	}

	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}
	/**
	 * Draws an enemy bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawEnemyBullet(Bullet enemyBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, enemyBullet.x, enemyBullet.y, observer);
	}

	/**
	 * Draws a Reaper bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawReaperBullet(Bullet reaperBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, reaperBullet.x, reaperBullet.y, observer);
	}

	/**
	 * Draws a Boss bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBossBullet(Bullet bossBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossBulletImg, bossBullet.x, bossBullet.y, observer);
	}
	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}

	public void drawAsteroid1(Asteroid1 asteroid1, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroid1Img, asteroid1.x, asteroid1.y, observer);
	}

	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * @param shipExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}

	public void drawAsteroid1Explosion(Rectangle asteroid1Explosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroid1ExplosionImg, asteroid1Explosion.x, asteroid1Explosion.y, observer);
	}

	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawReaperExplosion(Rectangle reaperExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(reaperExplosionImg, reaperExplosion.x, reaperExplosion.y, observer);
	}

	public void drawBossExplosion(Rectangle bossExplosion, Graphics2D g2d, ImageObserver observer) {
	    g2d.drawImage(bossExplosionImg, bossExplosion.x, bossExplosion.y, observer);
	}


}
