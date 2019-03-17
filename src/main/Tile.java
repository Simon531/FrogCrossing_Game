package main;

import org.newdawn.slick.SlickException;

/**
 * A class represents all the motionless tile items in the game
 */
public class Tile extends Sprite {
	
	private static final String GRASS_PATH = "assets/grass.png";
	private static final String WATER_PATH = "assets/water.png";
	private static final String TREE_PATH = "assets/tree.png";
	private static final String ARRIVED_PATH = "assets/frog.png";
	private static final String LIVES_PATH = "assets/lives.png";
	
	/**
	 * Create a new grass Tile Object with xy coordinate
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @return A tile object that represents Grass tile
	 * @throws SlickException
	 */
	public static Tile createGrassTile(float x, float y) throws SlickException {
		return new Tile(GRASS_PATH, x, y);
	}
	
	/**
	 * Create a new lives Tile Object with xy coordinate
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @return A tile object that represents lives tile
	 * @throws SlickException
	 */
	public static Tile createLivesTile(float x, float y) throws SlickException {
		return new Tile(LIVES_PATH, x, y);
	}
	
	/**
	 * Create a new water Tile Object with xy coordinate, and make it hazard
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @return A tile object that represents water tile
	 */
	public static Tile createWaterTile(float x, float y) {
		return new Tile(WATER_PATH, x, y, new String[] { Sprite.HAZARD });
	}
	
	/**
	 * Create a new arrived player Tile Object with xy coordinate, and make it hazard
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @return A tile object that represents arrived player tile
	 */
	public static Tile createArrivedTile(float x, float y) {
		return new Tile(ARRIVED_PATH, x, y, new String[] { Sprite.HAZARD });
	}
	
	/**
	 * Create a new tree Tile Object with xy coordinate, and make it solid
	 * @param x X coordinate of the center
	 * @param y Y coordinate of the center
	 * @return A tile object that represents tree tile
	 */
	public static Tile createTreeTile(float x, float y) {
		return new Tile(TREE_PATH, x, y, new String[] { Sprite.SOLID });
	}
	
	private Tile(String imageSrc, float x, float y) throws SlickException {		
		super(imageSrc, x, y);
	}
	private Tile(String imageSrc, float x, float y, String[] tags) {		
		super(imageSrc, x, y, tags);
	}
}
