package vehicle;

import org.newdawn.slick.SlickException;

import main.Sprite;

/**
 * A class represents all the bulldozer objects in the game, which is solid, and
 * can move automatically in specific speed and direction, and will push player 
 * move as the same speed as bulldozer if player is in front of it.
 */
public class Bulldozer extends Vehicle {
	
	private static final String BULLDOZER_PATH = "assets/bulldozer.png";
	private static final float BULLDOZER_SPEED = 0.05f;
	
	/**
	 * Create a new bulldozer Vehicle with xy coordinate and move direction
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param moveRight The direction of move, true if going right, false if going left
	 * @throws SlickException
	 */
	public Bulldozer(float x, float y, boolean moveRight) throws SlickException {		
		super(BULLDOZER_PATH, x, y, BULLDOZER_SPEED , moveRight, new String[] { Sprite.SOLID, Sprite.PUSH });
	}
	
	@Override
	public void push(int delta, Sprite sprite) {
		float x = sprite.getPosition().getX();
		float y = sprite.getPosition().getY();
		
		if (getMoveRight()) {
			sprite.setPosition(x + getSpeed() * delta, y);
		}
		else {
			sprite.setPosition(x - getSpeed() * delta, y);
		}
	}
}
