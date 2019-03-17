package character;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

import java.util.ArrayList;

import main.App;
import main.Sprite;
import main.Tile;
import main.World;
import utilities.BoundingBox;
import utilities.Position;

/**
 * A class represents all the player object in the game, which can be controlled to move
 * based on stdin.
 */
public class Player extends Sprite {
	
	/** number of lives at start */
	public static final int NUM_INIT_LIVES = 3;
	
	private static final String PLAYER_PATH = "assets/frog.png";
	// position of player lives
	private static final float LIVES_INIT_X = 24;
	private static final float LIVES_X_SPACE = 32;
	private static final float LIVES_Y = 744;
	
	private ArrayList<Sprite> lives = new ArrayList<>();
	
	/**
	 * Create a new player object with xy coordinate
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @throws SlickException
	 */
	public Player(float x, float y) throws SlickException {
		super(PLAYER_PATH, x, y);
		
		for (int i = 0; i < NUM_INIT_LIVES; i++) {
			lives.add(Tile.createLivesTile(LIVES_INIT_X + LIVES_X_SPACE * i, LIVES_Y));
		}
	}
	
	/**
	 * Update the state of player for a frame.
	 * @param input A wrapped for all keyboard, mouse and controller input
	 * @param delta Time passed since last frame (milliseconds).
	 * @param sprites All the sprites in the current world
	 */
	public void update(Input input, int delta, ArrayList<Sprite> sprites) {
		Position newPos = control(input);
		
		//player can't move out of screen or move into a solid item
		if (!outOfScreen(newPos) && !moveToSolid(newPos, sprites)) {
			setPosition(newPos);
		}
	}
	
	@Override
	public void render() throws SlickException {
		super.render();
		
		for (Sprite live: lives) {
			live.render();
		}
	}
	
	/**
	 * Make the player arrive into the empty tile
	 * @return True if player reach the empty tiles at top
	 */
	public boolean arrive() {
		if (this.getPosition().getY() <= World.ARRIVEDPLAYER_Y) {
			float x = this.getPosition().getX();
			float y = this.getPosition().getY();
			for (float i: World.ARRIVEDPLAYER_X) {
				if (Math.abs(i - x) < App.TILE_SIZE * 2) {
					x = i;
				}
			}
			this.setPosition(x, y);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Check if the player is riding something rideable, if is make it move with it
	 * @param sprites List of sprites that in the same world with player
	 * @param delta Time passed since last frame (milliseconds).
	 * @return True If player is riding something rideable
	 */
	public boolean riding(ArrayList<Sprite> sprites, int delta) {
		for (Sprite sprite: sprites) {
			if (this.contactSprite(sprite) && ( sprite.hasTag(Sprite.RIDEABLE) || 
					sprite.hasTag(Sprite.LIMITRIDEABLE) ) && sprite.shouldAppear()) {
				
				Position position = sprite.beRideBy(this, delta);
				
				//player can't move out of screen when riding some thing
				if (!outOfScreen(position)) {
					this.setPosition(position);
				}
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Check if the player is dead, if is subtract a live
	 * if live count is 0 and player dead, game over
	 * @param sprites List of sprites that in the same world with player
	 */
	public void isDead(ArrayList<Sprite> sprites) {
		for (Sprite sprite: sprites) {
			if (this.contactSprite(sprite) && sprite.hasTag(Sprite.HAZARD) || 
					outOfScreen(this.getPosition())) {
				
				if (lives.size() > 0) {
					lives.remove(lives.size() - 1);
					this.setPosition(World.PLAYER_INIT_X, World.PLAYER_INIT_Y);
				}
				else {
					System.exit(0);
				}
			}
		}
	}
	
	/**
	 * Add a new live to player
	 * @throws SlickException 
	 */
	public void addLive() throws SlickException {
		lives.add(Tile.createLivesTile(LIVES_INIT_X + LIVES_X_SPACE * (lives.size()), LIVES_Y));
	}
	
	/*
	 * Control the player based on input, one tile a time
	 * return the new position the player is going to move in
	 */
	private Position control (Input input) {
		float x = getPosition().getX();
		float y = getPosition().getY();
		float newY = y;
		float newX = x;
		
		//set newX or newY based on the move
		if (input.isKeyPressed(Input.KEY_UP)) {
			newY = y - App.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			newY = y + App.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			newX = x - App.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			newX = x + App.TILE_SIZE;
		}
		
		Position position = new Position(newX, newY);
		
		return position;
	}
	
	/*
	 * Check if the position is moving out of screen
	 */
	private boolean outOfScreen(Position position) {
		if (position == null) {
			return false;
		}
		
		float left = position.getX() - App.TILE_SIZE / 2;
		float up = position.getY() - App.TILE_SIZE / 2;
		
		boolean movingOut = (up < 0 || (up + App.TILE_SIZE) > App.SCREEN_HEIGHT || 
				left < 0 || (left + App.TILE_SIZE) > App.SCREEN_WIDTH);
		
		return movingOut;
	}
	
	/*
	 * Check if the position is moving towards solid item
	 */
	private boolean moveToSolid (Position position, ArrayList<Sprite> sprites) {
		BoundingBox newBox = new BoundingBox(position.getX(), position.getY(), 
				App.TILE_SIZE, App.TILE_SIZE);
		
		for (Sprite sprite: sprites) {
			if (newBox.intersects(sprite.getBox()) && sprite.hasTag(Sprite.SOLID)) {
				return true;
			}
		}
		
		return false;
	}
}
