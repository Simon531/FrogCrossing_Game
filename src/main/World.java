package main;

import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import character.ExtraLife;
import character.Player;
import rideableObject.RideableObject;
import rideableObject.Turtle;
import vehicle.Bike;
import vehicle.Bulldozer;
import vehicle.Vehicle;

/**
 * A class represents and handles everything in the World of the game
 */
public class World {
	
	/** start x coordinate of player */
	public static final float PLAYER_INIT_X = 512;
	/** start y coordinate of player */
	public static final float PLAYER_INIT_Y = 720;
	/** space of x coordinate between lives */
	public static final float[] ARRIVEDPLAYER_X = {120, 312, 504, 696, 888};
	/** space of x coordinate between lives */
	public static final float ARRIVEDPLAYER_Y = 48;
	
	/* max number of arrived player */
	private static final int NUM_ARRIVEDPLAYER = 5;
	/* pathway to game information of different level */
	private static final String LEVEL0 = "assets/levels/0.lvl";
	private static final String LEVEL1 = "assets/levels/1.lvl";
	
	private ArrayList<Sprite> spritesLvl0 = new ArrayList<>();
	private ArrayList<Sprite> spritesLvl1 = new ArrayList<>();
	
	private Player player;
	private ExtraLife extraLife;
	
	private int numArrivedPlayer;
	
	// whether the game is at level 0
	private boolean level0;
	
	/**
	 * Create a world by reading csv files and initializing all the objects in the game
	 * @throws SlickException
	 */
	public World() throws SlickException {
		readFile(LEVEL0, spritesLvl0);
		readFile(LEVEL1, spritesLvl1);
		
		player = new Player(PLAYER_INIT_X, PLAYER_INIT_Y);
		extraLife = new ExtraLife(0, 0);
		
		numArrivedPlayer = 0;
		
		level0 = true;
	}
	
	/**
	 * Update the state of a World for a frame.
	 * @param input A wrapped for all keyboard, mouse and controller input
	 * @param delta Time passed since last frame (milliseconds). 
	 * @throws SlickException 
	 */
	public void update(Input input, int delta) throws SlickException {
		
		//determine the current level
		ArrayList<Sprite> current = (level0 ? spritesLvl0 : spritesLvl1);
		
		for (Sprite sprite : current) {
			sprite.update(input, delta);
		}
		player.update(input, delta, current);
		extraLife.update(input, delta, current);
		
		//if player is not riding something, check if it contact haszard
		if (!player.riding(current, delta)) {
			player.isDead(current);
		}
		
		//if player being placed in front of push tag sprite, make it being push
		for (Sprite sprite : current) {
			if (player.atFrontOf(sprite) && sprite.hasTag(Sprite.PUSH)) {
				sprite.push(delta, player);
			}
		}
		
		//if player arrive one of the destinations
		if (player.arrive()) {
			current.add(Tile.createArrivedTile(player.getPosition().getX(), player.getPosition().getY()));
			
			numArrivedPlayer++;
			
			player.setPosition(PLAYER_INIT_X, PLAYER_INIT_Y);
						
			// if all the empty tiles are filled, end the world
			if (numArrivedPlayer == NUM_ARRIVEDPLAYER) {
				numArrivedPlayer = 0;
				if (level0) {
					level0 = false;
					extraLife.reset();
				}
				else {
					System.exit(0);
				}
			}
		}
		
		// if player contact with extra life, add lives, reset player's position and extra life
		if (player.contactSprite(extraLife) && extraLife.exist()) {
			player.addLive();		
			extraLife.reset();
		}
	}
	
	/**
	 * Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     * @throws SlickException
	 */
	public void render(Graphics g) throws SlickException {
		//determine the current level
		ArrayList<Sprite> current = (level0 ? spritesLvl0 : spritesLvl1);
		
		for (Sprite sprite : current) {
			sprite.render();
		}
		player.render();
		extraLife.render();
	}
	
	// read game object information file and store them in a specific arraylist
	private void readFile (String fileName, ArrayList<Sprite> sprites) {
		try (Scanner file = new Scanner(new FileReader(fileName))) {
			String text;
			
			while (file.hasNextLine()) {
				text = file.nextLine();
				String cells[] = text.split(",");
				String object = cells[0];
				float x = Float.parseFloat(cells[1]);
				float y = Float.parseFloat(cells[2]);
				
				switch (object) {
					case "water": 
						sprites.add(Tile.createWaterTile(x, y));
						break;
					case "grass": 
						sprites.add(Tile.createGrassTile(x, y));
						break;
					case "tree": 
						sprites.add(Tile.createTreeTile(x, y));
						break; 
					case "bus": 
						sprites.add(Vehicle.createBus(x, y, Boolean.parseBoolean(cells[3])));
						break;
					case "racecar": 
						sprites.add(Vehicle.createRacecar(x, y, Boolean.parseBoolean(cells[3])));
						break;
					case "bulldozer": 
						sprites.add(new Bulldozer(x, y, Boolean.parseBoolean(cells[3])));
						break;
					case "bike": 
						sprites.add(new Bike(x, y, Boolean.parseBoolean(cells[3])));
						break;
					case "log": 
						sprites.add(RideableObject.createLog(x, y, Boolean.parseBoolean(cells[3])));
						break;
					case "longLog": 
						sprites.add(RideableObject.createLongLog(x, y, Boolean.parseBoolean(cells[3])));
						break;
					case "turtle": 
						sprites.add(new Turtle(x, y, Boolean.parseBoolean(cells[3])));
						break;
					default:
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
