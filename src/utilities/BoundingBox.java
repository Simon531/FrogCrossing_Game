/**
 * BoundingBox complete class for SWEN20003: Object Oriented Software Development 2018
 * by Eleanor McMurtry, University of Melbourne
 * Modify by Xiuge Chen, University of Melbourne
 */
package utilities;

import org.newdawn.slick.Image;

/**
 * A class represents surrounding edges of a item
 */
public class BoundingBox {
	// FUZZ help shrink the size of box from original item, so the edges is more realistic
	private static final float FUZZ = 0.9f;
	private float left;
	private float top;
	private float width;
	private float height;
	
	/**
	 * Create a BoundingBox instance with xy coordinate, and the width, height of a object
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param width Width of object
	 * @param height Height of object
	 */
	public BoundingBox(float x, float y, float width, float height) {
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
	}
	/**
	 * Create a BoundingBox instance with xy coordinate, and the image of a object
	 * @param img A instance of Image class represents the image of a object
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public BoundingBox(Image img, float x, float y) {
		setWidth(img.getWidth());
		setHeight(img.getHeight());
		setX(x);
		setY(y);
	}
	/**
	 * Create a BoundingBox instance with another BoundingBox
	 * @param bb Another BoundingBox variable
	 */
	public BoundingBox(BoundingBox bb) {
		width = bb.width;
		height = bb.height;
		left = bb.left;
		top = bb.top;
	}

	/**
	 * Set the position of BoundingBox with x coordinate
	 * @param x X coordinate
	 */
	public void setX(float x) {
		left = x - width / 2;
	}
	/**
	 * Set the position of BoundingBox with y coordinate
	 * @param y Y coordinate
	 */
	public void setY(float y) {
		top = y - height / 2;
	}
	/**
	 * Set the width of BoundingBox with new width
	 * @param w New width of BoundingBox
	 */
	public void setWidth(float w) {
		width = w * FUZZ;
	}
	/**
	 * Set the height of BoundingBox with new height
	 * @param h New height of BoundingBox
	 */
	public void setHeight(float h) {
		height = h * FUZZ;
	}
	
	/**
	 * Get the left edge of this BoundingBox
	 * @return A float variable represents the left edge
	 */ 
	public float getLeft() {
		return left;
	}
	/**
	 * Get the top edge of this BoundingBox
	 * @return A float variable represents the top edge
	 */
	public float getTop() {
		return top;
	}
	/**
	 * Get the right edge of this BoundingBox
	 * @return A float variable represents the right edge
	 */
	public float getRight() {
		return left + width;
	}
	/**
	 * Get the bottom edge of this BoundingBox
	 * @return A float variable represents the bottom edge
	 */
	public float getBottom() {
		return top + height;
	}
	/**
	 * Get the width of this BoundingBox
	 * @return A float variable represents the width
	 */
	public float getWidth() {
		return width;
	}
	/**
	 * Get the height of this BoundingBox
	 * @return A float variable represents the height
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Determine whether a box is intersect with other box
	 * @param other Another BoundingBox
	 * @return True if this box is intersect with other box
	 */
	public boolean intersects(BoundingBox other) {
		return !(other.left > getRight()
			  || other.getRight()  < left
			  || other.top > getBottom()
			  || other.getBottom() < top);
	}
	
	/**
	 * Determine whether a box is placed in front of other box and there's no space between them
	 * @param other Another BoundingBox
	 * @return True if this box is placed in front of other box and no space between them
	 */
	public boolean atFrontOf(BoundingBox other) {
		return (Math.abs(other.getRight() - left) < 1 && other.top == top);
	}
}
