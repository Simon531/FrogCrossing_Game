package main;

import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utilities.BoundingBox;
import utilities.Position;

/**
 * A abstract class represent every single item in the world of game
 * each item will has its (x, y) position, image, a boudingbox around it, and possibly
 * a special tag. 
 * Can update and render itself.
 */
public abstract class Sprite{
	
	/** A defined constant to avoid typos, means fatal to player */
	public final static String HAZARD = "hazard";
	/** A defined constant to avoid typos, means player can't move in */
	public final static String SOLID = "solid";
	/** A defined constant to avoid typos, means player can ride on it and avoid dead */
	public final static String RIDEABLE = "rideable";
	/** A defined constant to avoid typos, means ONLY player can ride on it and avoid dead */
	public final static String LIMITRIDEABLE = "limitRideable";
	/** A defined constant to avoid typos, means a sprite can push another sprite to move */
	public final static String PUSH = "push";
	
	private Image image;
	private Position position;
	private BoundingBox box;
	
	private String[] tags;
	
	/**
	 * Create a new sprite with image pathway and xy coordinate
	 * @param imageSrc The path to the image represents the sprite
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @throws SlickException
	 */
	public Sprite(String imageSrc, float x, float y) throws SlickException {
		setupSprite(imageSrc, x, y);
	}
	
	/**
	 * Create a new sprite with image pathway, xy coordinate and tags
	 * @param imageSrc The path to the image represents the sprite
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @param tags Tags of a kind a sprite
	 * @throws SlickException
	 */
	public Sprite(String imageSrc, float x, float y, String[] tags) {
		setupSprite(imageSrc, x, y);
		this.tags = tags;
	}
	
	/*
	 * Set up initial state of a sprite
	 */
	private void setupSprite(String imageSrc, float x, float y) {
		try {
			image = new Image(imageSrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		position = new Position(x, y);
		
		box = new BoundingBox(image, (int)x, (int)y);
		
		tags = new String[0];		
	}
	
	/**
	 * Update the state of sprite for a frame.
	 * @param input A wrapped for all keyboard, mouse and controller input
	 * @param delta Time passed since last frame (milliseconds).
	 */
	public void update(Input input, int delta) {
	}
	
	/**
	 * Render the sprite on screen.
	 * @throws SlickException
	 */
	public void render() throws SlickException {
		image.drawCentered(position.getX(), position.getY());
	}
	
	/**
	 * Determine whether a sprite is contacting with other sprite
	 * @param other The other sprite.
	 * @return True if this sprite is contacting with other sprite
	 */
	public boolean contactSprite(Sprite other) {
		return this.box.intersects (other.getBox());
	}
	
	/**
	 * Move the sprite according to the speed and direction.
	 * @param speed The speed of movement, pixels per millisec
	 * @param delta Time passed since last frame (milliseconds).
	 * @param moveRight Whether are going towards right
	 */
	public void move(float speed, int delta, boolean moveRight) {
		float x = moveRight ? getPosition().getX() + delta * speed : 
			getPosition().getX() - delta * speed;
		float y = getPosition().getY();
				
		setPosition(x, y);
	}
	
	/**
	 * Determine whether a sprite is just at the front of other sprite (no space between them)
	 * @param other The other sprite.
	 * @return True if this sprite is at the front of other sprite
	 */
	public boolean atFrontOf(Sprite other) {
		return this.box.atFrontOf(other.getBox());
	}
	
	/**
	 * Push the other sprite move as the same speed and direction as this sprite
	 * when the other sprite is in front of this sprite (no space between them)
	 * @param delta Time passed since last frame (milliseconds).
	 * @param sprite Other sprite of the game
	 */
	public void push(int delta, Sprite sprite) {
	}
	
	/**
	 * Determine whether a sprite contains a specific tag
	 * @param tag Specific tag a sprite may contain
	 * @return True if this sprite contains specific tag
	 */
	public boolean hasTag(String tag) {
		for (String test : tags) {
			if (tag.equals(test)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Let the other sprite moving with this sprite, update the position of this sprite
	 * @param sprite The other sprite is riding on this sprite
	 * @param delta Time passed since last frame (milliseconds).
	 * @return The new position of sprite
	 */
	public Position beRideBy(Sprite sprite, int delta) {
		return null;
	}
	
	/**
	 * Indicate whether a sprite should appear on screen
	 * @return True if it should appear on screen
	 */
	public boolean shouldAppear() {
		return true;
	}
	
	/**
	 * Get the current position of this sprite
	 * @return A position variable of Position class represents the current position of this sprite
	 */
	public Position getPosition () {
		return new Position(position.getX(), position.getY());
	}
	/**
	 * Get the bounding box of this sprite
	 * @return A box variable of BoundingBox class around this sprite.
	 */
	public BoundingBox getBox () {
		return new BoundingBox(this.box);
	}
	
	/**
	 * Set the position of sprite with x coordinate and y coordinate
	 * @param x: X coordinate
	 * @param y: Y coordinate
	 */
	public void setPosition (float x, float y) {
		position.setX(x);
		position.setY(y);
		box.setX(x);
		box.setY(y);
	}
	/**
	 * Set the position of sprite with another position variable
	 * @param other Another position variable
	 */
	public void setPosition (Position other) {
		position.setX(other.getX());
		position.setY(other.getY());
		box.setX(other.getX());
		box.setY(other.getY());
	}

}
