package character;

import org.newdawn.slick.SlickException;

import java.util.ArrayList;

import org.newdawn.slick.Input;

import main.App;
import main.Sprite;

/**
 * A class represents all the extra life objects in the game.
 * A extra life will choose a random show up time between 25s and 35s, after that it will appear
 * at a random log or longlog for 14s.
 * During the time on log or longlog, every 2 sec it will move one tile right, if it reach the edge
 * of log or longlog, it will move backwards to left.
 * The process above will repeat when extra life is disappear or make contact with player.
 */
public class ExtraLife extends Sprite {
	
	private static final String EXTRALIFE_PATH = "assets/extralife.png";
	/* the time duration for appearing, in millisecond */
	private static final int APPEAR_TIME = 14000;
	/* the time duration for each movement, in millisecond */
	private static final int MOVE_TIME = 2000;
	/* smallest time  of extra life showing, in millisecond */
	private static final float EXTRALIFE_SHOW_LEAST = 25000;
	/* most extra time may add to extra life show time, in millisecond */
	private static final float EXTRALIFE_SHOW_DURATION = 10000;
	
	// the index of object that extralife is riding, -1 indicates no object riding
	private int rideObject;
	//the x location different value between the extra life and the object it is riding on
	private float relativeLoca;
	//time duration before showing up
	private int showUpTime;
	//time count for moving on the ride object
	private int moveTime;
	private int existTime;
	private boolean moveRight;

	/**
	 * Create a new extra life object with xy coordinate
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @throws SlickException
	 */
	public ExtraLife(float x, float y) throws SlickException {
		super(EXTRALIFE_PATH, x, y);
		this.reset();
	}
	
	/**
	 * Update the state, time count of extra life for a frame.
	 * @param input A wrapped for all keyboard, mouse and controller input
	 * @param delta Time passed since last frame (milliseconds).
	 * @param sprites All the sprites instances in the current world
	 */
	public void update(Input input, int delta, ArrayList<Sprite> sprites) {
		if (existTime >= showUpTime) {
			moveTime += delta;
			
			//choose rideable object to ride on
			if (rideObject == -1) {
				rideObject = findRideObject(sprites);
			}
			//move along the object one tile every 2 sec
			if (moveTime > MOVE_TIME) {
				moveAlong(sprites.get(rideObject));
				
				moveTime = 0;
			}
		}
		if (existTime > showUpTime + APPEAR_TIME) {
			reset();
		}
		
		//if there is a object for extra life to ride on, let it ride
		if (rideObject != -1) {
			riding(delta, sprites.get(rideObject));
		}
		
		existTime += delta;
	}
	
	@Override
	public void render() throws SlickException {
		if (existTime >= showUpTime && rideObject != -1) {
			super.render();
		}
	}
	
	/**
	 * Find the index of rideable object the extra life is going to ride on.
	 * @param sprites A arrayList of sprite which exist in the current game
	 * @return The index of rideable object in arraylist that extra life is going to ride.
	 */
	public int findRideObject(ArrayList<Sprite> sprites) {
		//get the total number of rideable object
		int numRide = 0;
		for (Sprite i: sprites) {
			if (i.hasTag(Sprite.RIDEABLE)) {
				numRide++;
			}
		}
		
		int ride = (int) ((Math.random() * 10) * numRide) + 1;
		
		// get the index of rideable object in arraylist
		int n = 0;
		for (Sprite i: sprites) {
			if (i.hasTag(Sprite.RIDEABLE)) {
				ride--;
				if (ride == 0) {
					return n;
				}
			}
			n++;
		}
		
		return -1;
	}
	
	/**
	 * Making extra life riding a rideable object by changing its position in the 
	 * same direction and speed with the rideable object.
	 * @param delta Time passed since last frame (milliseconds).
	 * @param rideObject The rideable object that the extra life is riding now.
	 */
	public void riding(int delta, Sprite rideObject) {
		if (rideObject != null) {
			float newX = rideObject.getPosition().getX() + relativeLoca;
			float newY = rideObject.getPosition().getY();
			this.setPosition(newX, newY);
		}
	}
	
	/**
	 * Making the extra life object move along (one tile a time) the rideable object it is 
	 * riding on, first it will try to move right, if there is no space at right it will 
	 * move left.
	 * @param object The rideable object that the extra life is riding now.
	 */
	public void moveAlong(Sprite object) {
		if (object != null) {
			float objectX = object.getPosition().getX();
			float objectRight = object.getBox().getRight();
			float objectLeft = object.getBox().getLeft();
			
			if (moveRight) {
				if (objectX + relativeLoca + App.TILE_SIZE < objectRight) {
					relativeLoca += App.TILE_SIZE;
					return;
				}
				//if can't move right, immediately move left once
				else {
					moveRight = false;
					moveAlong(object);
				}
			}
			else {
				if (objectX + relativeLoca - App.TILE_SIZE > objectLeft) {
					relativeLoca -= App.TILE_SIZE;
					return;
				}
				//if can't move left, immediately move right once
				else {
					moveRight = true;
					moveAlong(object);
				}
			}
		}
	}
	
	/**
	 * Reset all the time count, state to initial one.
	 */
	public void reset() {
		moveRight = true;
		moveTime = 0;
		showUpTime = (int) ((Math.random() * EXTRALIFE_SHOW_DURATION) + EXTRALIFE_SHOW_LEAST);
		this.relativeLoca = 0;
		existTime = 0;
		rideObject = -1;
	}
	
	/**
	 * Check whether a extra life is exist (should show up on screen).
	 * @return True if extra life is exist.
	 */
	public boolean exist() {
		return (existTime >= showUpTime);
	}
}
