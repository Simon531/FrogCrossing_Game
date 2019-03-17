package vehicle;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import main.App;
import main.Sprite;

/**
 * A class represents all the bike objects in the game, which can move automatically in 
 * specific speed and direction, and will cause player dead if contact with player.
 * Also if a bike reach the edge of screen, it will change move direction to move backwards
 */
public class Bike extends Vehicle {

	private static final String BIKE_PATH = "assets/bike.png";
	private static final float BIKE_SPEED = 0.2f;
	
	/**
	 * Create a new bike Vehicle with xy coordinate and move direction
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param moveRight The direction of move, true if going right, false if going left
	 * @throws SlickException
	 */
	public Bike(float x, float y, boolean moveRight) throws SlickException {		
		super(BIKE_PATH, x, y, BIKE_SPEED, moveRight, new String[] { Sprite.HAZARD });
	}
	
	@Override
	public void update(Input input, int delta) {
		super.update(input, delta);
			
		//once they reach the edge of screen, they change their direction to move backwards
		if (this.getPosition().getX() <= App.TILE_SIZE / 2) {
			setMoveRight(true);
		}
		else if (this.getPosition().getX() >= App.SCREEN_WIDTH - App.TILE_SIZE / 2) {
			setMoveRight(false);
		}
	}
}
