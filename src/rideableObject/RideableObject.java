package rideableObject;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import main.App;
import main.Sprite;
import utilities.Position;

/**
 * A class represents all the objects that can move automatically and can be ride on
 */
public class RideableObject extends Sprite {
	
	private static final String LOG_PATH = "assets/log.png";
	private static final float LOG_SPEED = 0.1f;
	private static final String LONGLOG_PATH = "assets/longlog.png";
	private static final float LONGLOG_SPEED = 0.07f;
	
	private float speed;
	private boolean moveRight;
	
	/**
	 * Create a new log Rideable Object with xy coordinate
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param moveRight The Direction of movement, true if moving right
	 * @return A rideable object that represents log
	 * @throws SlickException
	 */
	public static RideableObject createLog(float x, float y, 
			boolean moveRight) throws SlickException {
		
		return new RideableObject(LOG_PATH, x, y, LOG_SPEED, moveRight, 
				new String[] { Sprite.RIDEABLE });
	}
	
	/**
	 * Create a new longlog Rideable Object with xy coordinate 
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param moveRight The Direction of movement, true if moving right
	 * @return A rideable object that represents longlog
	 * @throws SlickException
	 */
	public static RideableObject createLongLog(float x, float y, 
			boolean moveRight) throws SlickException {
		
		return new RideableObject(LONGLOG_PATH, x, y, LONGLOG_SPEED, moveRight, 
				new String[] { Sprite.RIDEABLE });
	}
	
	/**
	 * Create a new Rideable Object with xy coordinate, move speed and direction
	 * @param imageSrc The path to the image represents the sprite
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param speed Speed of move
	 * @param moveRight Direction of move, true if going right, false if going left
	 * @param tags Tag of this rideable object
	 * @throws SlickException
	 */
	public RideableObject(String imageSrc, float x, float y, float speed, boolean moveRight, 
			String[] tags) throws SlickException {	
		
		super(imageSrc, x, y, tags);
		this.speed = speed;
		this.moveRight = moveRight;
	}
	
	@Override
	public void update(Input input, int delta) {
		float y = getPosition().getY();
		float width = getBox().getWidth();
		float left = getBox().getLeft();
			
		move(speed, delta, moveRight);
			
		//once they are off-screen, they re-appear at oppsite
		if (left > App.SCREEN_WIDTH) {
			setPosition(-(width / 2), y);
		}
		if ((left + width) < 0) {
			setPosition(App.SCREEN_WIDTH + width / 2, y);
		}
	}
	
	@Override
	public Position beRideBy(Sprite sprite, int delta) {
		float x = sprite.getPosition().getX();
		float y = sprite.getPosition().getY();
		Position newPos = new Position(x, y);
		
		if (this.getMoveRight()) {
			newPos.setX(x + delta * this.getSpeed());
		}
		else {
			newPos.setX(x - delta * this.getSpeed());
		}
	
		return newPos;
	}
	
	/**
	 * Get the direction of this rideable object (can only be right or left)
	 * @return True if the object is going right
	 */
	public boolean getMoveRight() {
		return moveRight;
	}
	/**
	 * Get the speed of this rideable object, in pixels per millisec.
	 * @return A float variable represents speed of this object in pixels per millisecond.
	 */
	public float getSpeed() {
		return speed;
	}
}
