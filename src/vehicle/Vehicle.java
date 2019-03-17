package vehicle;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

import main.App;
import main.Sprite;

/**
 * A class represents all the vehicle objects that can move automatically
 */
public class Vehicle extends Sprite {

	
	private static final String BUS_PATH = "assets/bus.png";
	private static final float BUS_SPEED = 0.15f;
	private static final String RACECAR_PATH = "assets/racecar.png";
	private static final float RACECAR_SPEED = 0.5f;
	
	private float speed;
	private boolean moveRight;
	
	/**
	 * Create a new Bus Object with xy coordinate and move direction
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param moveRight The direction of move, true if going right, false if going left
	 * @return A vehicle object that represents bus
	 * @throws SlickException
	 */
	public static Vehicle createBus(float x, float y, boolean moveRight) throws SlickException {
		return new Vehicle(BUS_PATH, x, y, BUS_SPEED, moveRight, new String[] { Sprite.HAZARD });
	}
	
	/**
	 * Create a new racecar Object with xy coordinate and move direction
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param moveRight The direction of move, true if going right, false if going left
	 * @return A vehicle object that represents racecar
	 * @throws SlickException
	 */
	public static Vehicle createRacecar(float x, float y, boolean moveRight) throws SlickException {
		return new Vehicle(RACECAR_PATH, x, y, RACECAR_SPEED, moveRight, new String[] { Sprite.HAZARD });
	}
	
	/**
	 * Create a new Vehicle Object with xy coordinate, move speed and direction, tags
	 * @param imageSrc The path to the image represents the sprite
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param speed Speed of move
	 * @param moveRight Direction of move, true if going right, false if going left
	 * @param tags Tags of a kind a sprite
	 * @throws SlickException
	 */
	public Vehicle(String imageSrc, float x, float y, float speed, boolean moveRight, String[] tags) throws SlickException {		
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
	
	/**
	 * Get the direction of this vehicle (can only be right or left)
	 * @return True if the object is going right
	 */
	public boolean getMoveRight() {
		return moveRight;
	}
	
	/**
	 * Get the speed of this vehicle, in pixels per millisec.
	 * @return A float variable represents speed of this object in pixels per millisecond.
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * Set the move direction of this vehicle
	 * @param moveRight New direction, true means going right and false means going left
	 */
	public void setMoveRight(boolean moveRight) {
		this.moveRight = moveRight;
	}
}
