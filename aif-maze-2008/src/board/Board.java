package board;

import java.util.*;

/**
 * Board containing the maze.
 * 
 * 
 * @author £ukasz Barcikowski
 * @author Pawe³ Rendak
 *
 */
public class Board {

	/**
	 * Grid containing collection of blocks with 
	 * unique key for each block that corresponds 
	 * to its coordinates on the grid.
	 */
	private HashMap<Integer, Block> myGrid;

	/**
	 * Initializes the board by creating new grid
	 * and filling it with blocks.
	 * 
	 * @param width Width of the board.
	 * @param height Height of the board.
	 */
	public Board(int width, int height) {

		/*
		 * Initializes new grid for the board.
		 */
		this.myGrid = new HashMap<Integer, Block>();

		/*
		 * Filling the grid with new blocks.
		 */
		for (int col = 1; col <= width; col++) {
			for (int row = 1; row <= height; row++) {
				this.myGrid.put(this.generateKey(col, row), new Block());
			}
		}

	}
	
	/**
	 * Generates unique key that corresponds to some 
	 * coordinates. Key is of form XXXXYYYY where 
	 * XXXX is x coordinate and YYYY is y coordinate.
	 * 
	 * @param x X coordinate for the grid.
	 * @param y Y coordinate for the grid.
	 * @return Unique key.
	 */
	public int generateKey(int x, int y) {
		return x * 1000 + y;
	}
	
	/**
	 * Extracts coordinates from a unique key.
	 * Key should be of form XXXXYYYY, where
	 * XXXX is value of x coordinate and
	 * YYYY is value of y coordinate.
	 * 
	 * Example:
	 * 120099 corresponds to point (12,99)
	 * 
	 * Returns:
	 * [0] - X integer value
	 * [1] - Y integer value
	 * 
	 * @param key Unique key.
	 * @return Pair of coordinates [x,y]
	 */
	private int[] parseKey(int key) {
		int[] coordinates = new int[2];
		coordinates[1] = key % 10000;
		coordinates[0] = key - coordinates[1];
		return coordinates;
	}
}
