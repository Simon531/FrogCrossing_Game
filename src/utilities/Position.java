package utilities;

/**
 * A class represents the position of each item (in xy coordinate).
 */
public class Position {
	private float x;
	private float y;
	
	/**
	 * Create a position instance with xy coordinates.
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Position(float x, float y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Get the x coordinate of this position
	 * @return X coordinate
	 */
	public float getX() {
		return this.x;
	}
	/**
	 * Get the y coordinate of this position
	 * @return Y coordinate
	 */
	public float getY() {
		return this.y;
	}
	
	/**
	 * Set the x coordinate of this position
	 * @param x New x coordinate
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * Set the y coordinate of this position
	 * @param y New y coordinate
	 */
	public void setY(float y) {
		this.y = y;
	}
}
