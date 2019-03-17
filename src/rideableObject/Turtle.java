package rideableObject;

import org.newdawn.slick.SlickException;

import main.Sprite;

import org.newdawn.slick.Input;

/**
 * A class represents all the turtle objects in the game.
 * A turtle will appear on surface for 7s and then disappear for 2s
 * And a turtle can only be ride by player
 */
public class Turtle extends RideableObject {
	
	private static final String TURTLE_PATH = "assets/turtles.png";
	private static final float TURTLE_SPEED = 0.085f;
	
	/* turtle exist time, in milliseconds */
	private static final int TURTLE_EXIST = 7000;
	/* turtle disappear time, in milliseconds */
	private static final int TURTLE_DISAPPEAR = 2000;
	
	private boolean shouldAppear;
	private int TimeCount;

	/**
	 * Create a new Turtle with xy coordinate and direction
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param moveRight Direction of move, true if going right, false if going left
	 * @throws SlickException
	 */
	public Turtle(float x, float y, boolean moveRight) throws SlickException {
		super(TURTLE_PATH, x, y, TURTLE_SPEED, moveRight, new String[] { Sprite.LIMITRIDEABLE });
		TimeCount = 0;
		shouldAppear = true;
	}
	
	@Override
	public void update(Input input, int delta) {
		super.update(input, delta);

		if (shouldAppear && TimeCount > TURTLE_EXIST) {
			TimeCount = 0;
			shouldAppear = false;
		}
		
		if (!shouldAppear && TimeCount > TURTLE_DISAPPEAR) {
			TimeCount = 0;
			shouldAppear = true;
		}
		
		TimeCount += delta;
	}
	
	@Override
	public void render() throws SlickException {
		if (shouldAppear) {
			super.render();
		}
	}
	
	@Override
	public boolean shouldAppear() {
		return shouldAppear;
	}
}
